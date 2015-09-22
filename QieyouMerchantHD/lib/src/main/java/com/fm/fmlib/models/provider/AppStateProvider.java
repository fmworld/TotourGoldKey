package com.fm.fmlib.models.provider;

import com.fm.fmlib.state.ApplicationState;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhoufeng'an on 2015/9/15.
 */
@Module
public class AppStateProvider {
    @Singleton
    @Provides
    ApplicationState provideApplicationState(){
        return new ApplicationState();
    }
}
