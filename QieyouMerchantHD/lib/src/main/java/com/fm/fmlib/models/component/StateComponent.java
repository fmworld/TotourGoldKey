package com.fm.fmlib.models.component;

import com.fm.fmlib.models.provider.StateProvider;
import com.fm.fmlib.state.UserState;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhoufeng'an on 2015/9/15.
 */
@Component(modules = StateProvider.class)
@Singleton
public interface StateComponent {
    UserState provideUserState();
}
