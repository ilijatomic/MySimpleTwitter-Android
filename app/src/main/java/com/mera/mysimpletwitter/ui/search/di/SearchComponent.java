package com.mera.mysimpletwitter.ui.search.di;

import com.mera.mysimpletwitter.core.di.scope.PerActivity;
import com.mera.mysimpletwitter.ui.search.view.SearchActivity;

import dagger.Component;

/**
 * Created by ilija.tomic on 7/4/2017.
 * Dagger component for {@link SearchActivity}
 */
@PerActivity
@Component(modules = SearchModule.class)
public interface SearchComponent {

    void inject(SearchActivity searchActivity);
}
