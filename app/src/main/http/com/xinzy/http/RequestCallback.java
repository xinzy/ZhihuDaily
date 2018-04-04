package com.xinzy.http;

import java.util.Map;

/**
 * Created by Xinzy on 2017/6/12.
 *
 */
public interface RequestCallback<T> {

    void onSuccess(T t);

    void onFailure(SmartHttpException e);
}
