package com.mera.mysimpletwitter.ui.timeline.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mera.mysimpletwitter.R;
import com.mera.mysimpletwitter.core.db.TweetEntity;
import com.mera.mysimpletwitter.ui.utils.TimelineConverter;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ilija.tomic on 7/3/2017.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private PublishSubject<String> mViewClickSubject = PublishSubject.create();
    private List<Object> mTimelineTweets;

    public void setItems(List<Object> timelineTweets) {
        this.mTimelineTweets = timelineTweets;
        notifyDataSetChanged();
    }

    public PublishSubject<String> getViewClickSubject() {
        return mViewClickSubject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tweet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object tweet = mTimelineTweets.get(position);
        String userName = null;
        String userScreenName = null;
        String userProfileImgUrl = null;
        String createdAt = null;
        String text = null;
        String mediaUrl = null;

        if (tweet instanceof Tweet) {
            Tweet tweetTemp = (Tweet) tweet;
            userName = tweetTemp.user.name;
            userScreenName = tweetTemp.user.screenName;
            userProfileImgUrl = tweetTemp.user.profileImageUrl;
            createdAt = tweetTemp.createdAt;
            text = tweetTemp.text;
            if (tweetTemp.entities.media != null) {
                mediaUrl = tweetTemp.entities.media.get(0).mediaUrl;
            }
        } else if (tweet instanceof TweetEntity) {
            TweetEntity tweetTemp = (TweetEntity) tweet;
            userName = tweetTemp.getUserName();
            userScreenName = tweetTemp.getUserScreenName();
            userProfileImgUrl = tweetTemp.getUserProfileImgUrl();
            createdAt = tweetTemp.getCreatedAt();
            text = tweetTemp.getText();
            if (tweetTemp.getMediaUrl() != null) {
                mediaUrl = tweetTemp.getMediaUrl();
            }
        }
        Picasso.with(holder.avatarImageView.getContext())
                .load(userProfileImgUrl)
                .fit()
                .into(holder.avatarImageView);
        holder.nameView.setText(userName);
        holder.handleView.setText(String.format("@%s", userScreenName));
        holder.timeView.setText(TimelineConverter.dateToAge(createdAt, DateTime.now()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            //noinspection deprecation
            holder.textView.setText(Html.fromHtml(text));
        }

        if (mediaUrl != null) {
            Picasso.with(holder.mediaImageView.getContext())
                    .load(mediaUrl)
                    .fit()
                    .centerCrop()
                    .into(holder.mediaImageView);
        } else {
            holder.mediaImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTimelineTweets != null ? mTimelineTweets.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView avatarImageView;
        @BindView(R.id.media)
        ImageView mediaImageView;
        @BindView(R.id.name)
        TextView nameView;
        @BindView(R.id.handle)
        TextView handleView;
        @BindView(R.id.tweet_time)
        TextView timeView;
        @BindView(R.id.tweet_text)
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mediaImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    String mediaUrl = null;
                    Object tweet = mTimelineTweets.get(position);
                    if (tweet instanceof Tweet) {
                        mediaUrl = ((Tweet) tweet).entities.media.get(0).url;
                    } else if (tweet instanceof TweetEntity) {
                        mediaUrl = ((TweetEntity) tweet).getMediaUrl();
                    }

                    mViewClickSubject.onNext(mediaUrl);
                }
            });
        }
    }
}
