package com.fm.fmlib.models.component;

import com.fm.fmlib.models.provider.AppStateProvider;
import com.fm.fmlib.state.ApplicationState;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhoufeng'an on 2015/9/15.
 */
@Component(modules = AppStateProvider.class)
@Singleton
public interface ApplicationStateComponent {
    ApplicationState provideApplicationState();
}
