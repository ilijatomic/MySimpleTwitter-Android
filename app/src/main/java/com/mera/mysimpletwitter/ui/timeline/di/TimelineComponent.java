package com.mera.mysimpletwitter.ui.timeline.di;

import com.mera.mysimpletwitter.core.di.scope.PerActivity;
import com.mera.mysimpletwitter.ui.timeline.view.TimelineActivity;

import dagger.Component;

/**
 * Created by ilija.tomic on 7/3/2017.
 * Dagger Component for {@link TimelineActivity}
 */
@PerActivity
@Component(modules = TimelineModule.class)
public interface TimelineComponent {

    void inject(TimelineActivity timelineActivity);
}
