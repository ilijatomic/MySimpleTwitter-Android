package com.mera.mysimpletwitter.core.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by ikac on 7/2/17.
 * DI activity scope
 */
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface PerActivity {}
