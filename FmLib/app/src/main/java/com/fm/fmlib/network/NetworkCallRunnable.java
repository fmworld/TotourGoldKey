/*
 * Copyright 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fm.fmlib.network;



import com.fm.fmlib.FmApplication;
import com.squareup.otto.Bus;

import retrofit.RetrofitError;

public abstract class NetworkCallRunnable<R> {
    public static final String TAG ="tour0888";
    public Bus getBus(){
        return FmApplication.instance().getmBus();
    }
    public void onPreTourCall() {}

    public abstract R doBackgroundCall() throws RetrofitError;

    public abstract void onSuccess(R result);

    public abstract void onError(RetrofitError be);

    public void onFinished() {}

 }
