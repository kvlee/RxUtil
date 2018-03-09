/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.rxutildemo.fragment.rxbus;

import com.xuexiang.rxutil.rxbus.RxBusUtils;
import com.xuexiang.rxutil.rxbus.SubscribeInfo;
import com.xuexiang.rxutil.subsciber.SimpleSubscriber;
import com.xuexiang.rxutildemo.entity.Event;
import com.xuexiang.rxutildemo.entity.EventKey;
import com.xuexiang.rxutildemo.fragment.rxbus.base.BaseRxBusTestFragment;

import rx.functions.Action1;

/**
 * @author xuexiang
 * @date 2018/3/3 下午6:35
 */
public class RxBusTestFragment4 extends BaseRxBusTestFragment {

    private SubscribeInfo mOneByMore;

    @Override
    protected void initViews() {
        setBackgroundColor(android.R.color.holo_blue_bright);
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxBusUtils.get().on(EventKey.EVENT_BACK_NORMAL, new Action1<String>() {
            @Override
            public void call(String eventName) {
                final String msg = "事件Key:" + EventKey.EVENT_BACK_NORMAL + "\n   EventName:" + eventName + ", 当前线程状态： " + Event.getLooperStatus();
                showContent(msg);
            }
        });

        RxBusUtils.get().on(EventKey.EVENT_BACK_NORMAL, new SimpleSubscriber<String>(){
            @Override
            public void onNext(String eventName) {
                final String msg = "事件Key:" + EventKey.EVENT_BACK_NORMAL + "\n   EventName:" + eventName + ", 当前线程状态： " + Event.getLooperStatus();
                showContent(msg);
            }
        });

        mOneByMore = RxBusUtils.get().onMainThread(EventKey.EVENT_ONE_BY_MORE, new Action1<String>() {
            @Override
            public void call(String eventName) {
                showContent(EventKey.EVENT_ONE_BY_MORE, "   EventName:" + eventName);
            }
        });
    }

    @Override
    protected void onCancelEvent() {
        RxBusUtils.get().unregisterAll(EventKey.EVENT_BACK_NORMAL);
        RxBusUtils.get().unregister(EventKey.EVENT_ONE_BY_MORE, mOneByMore);
        RxBusUtils.get().unregister(EventKey.EVENT_CLEAR, mSubscribeInfo);
    }
}
