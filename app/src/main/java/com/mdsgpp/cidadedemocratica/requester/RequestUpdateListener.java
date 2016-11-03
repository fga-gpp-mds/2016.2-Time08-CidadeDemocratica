package com.mdsgpp.cidadedemocratica.requester;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by luisresende on 18/10/16.
 */

public interface RequestUpdateListener {

    void afterSuccess(RequestResponseHandler handler, Object response);
    void afterError(RequestResponseHandler handler, String message);
}
