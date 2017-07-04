package com.mera.mysimpletwitter.ui.timeline.view.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mera.mysimpletwitter.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ilija.tomic on 7/4/2017.
 */
public class TweetImageFragment extends DialogFragment {

    private static final String IMAGE_URL = "tweet_image_url";

    String mMediaUrl;

    public static TweetImageFragment newInstance(String mediaUrl) {
        TweetImageFragment tweetImageFragment = new TweetImageFragment();

        Bundle args = new Bundle();
        args.putString(IMAGE_URL, mediaUrl);
        tweetImageFragment.setArguments(args);

        return tweetImageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaUrl = getArguments().getString(IMAGE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_tweet_image, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.tweet_image);

        Picasso.with(imageView.getContext())
                .load(mMediaUrl)
                .into(imageView);

        imageView.setVisibility(View.VISIBLE);

        return view;
    }
}
