package com.fm.fmlib.models.provider;

/**
 * Created by zhoufeng'an on 2015/9/15.
 */

import com.fm.fmlib.models.component.ApplicationStateComponent;
import com.fm.fmlib.models.component.DaggerApplicationStateComponent;
import com.fm.fmlib.state.ApplicationState;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.state.UserState;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StateProvider {
    @Inject
    ApplicationState applicationState;

    public StateProvider(){
        ApplicationStateComponent applicationStateComponent = DaggerApplicationStateComponent.builder().
                appStateProvider(new AppStateProvider()).build();
        applicationState = applicationStateComponent.provideApplicationState();
    }

    @Provides
    @Singleton
    InnState provideInnState(){
        return applicationState;
    }

    @Provides
    @Singleton
    UserState provideUserSate(){
        return applicationState;
    }
}
