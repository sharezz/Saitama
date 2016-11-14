package com.saitama.transportation.mobile.android.server.facade;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.saitama.transportation.mobile.android.core.TokenKeeper;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by sharezzorama on 10/26/16.
 */
public class BaseFacade {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String HEADER_TOKEN = "Authorization";
    private static String FIELD_ERROR = "error";
    private static String FIELD_DATA = "data";

    private TokenKeeper mTokenKeeper;
    private OkHttpClient mClient;
    private String mBaseUrl;
    private Moshi mMoshi;

    public BaseFacade() {
        mMoshi = new Moshi.Builder().build();
    }

    public BaseFacade(String baseUrl, TokenKeeper tokenKeeper) {
        this();
        this.mBaseUrl = baseUrl;
        this.mTokenKeeper = tokenKeeper;
        this.mClient = new OkHttpClient();
    }

    private String getFacadeTag() {
        return getClass().getName();
    }

    protected <RESPONSE> void apiDelete(String url,
                                        Map<String, String> params,
                                        Class<RESPONSE> responseClass,
                                        FacadeCallback<RESPONSE> callback,
                                        boolean async,
                                        boolean needDelegate) {

        callback = needDelegate ? new FacadeCallbackDelegate<RESPONSE>(callback) : callback;

        HttpUrl absoluteUrl = HttpUrl.parse(getAbsoluteUrl(url));
        HttpUrl.Builder urlBuilder = absoluteUrl.newBuilder();

        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                urlBuilder.addEncodedQueryParameter(param.getKey(), param.getValue());
            }
        }

        Request.Builder builder = new Request.Builder();

        builder.url(urlBuilder.build());
        builder.delete();
        builder.tag(getFacadeTag());

        String token = getToken();
        if (token != null && !token.isEmpty()) {
            builder.header(HEADER_TOKEN, token);
        }

        final Request request = builder.build();

        doRequest(request, responseClass, callback, async);
    }

    protected <RESPONSE> void apiGet(String url,
                                     Map<String, String> params,
                                     Class<RESPONSE> responseClass,
                                     FacadeCallback<RESPONSE> callback,
                                     boolean async,
                                     boolean needDelegate) {
        apiGet(url, params, responseClass, callback, async, needDelegate, true);

    }

    protected <RESPONSE> void apiGet(String url,
                                     Map<String, String> params,
                                     Class<RESPONSE> responseClass,
                                     FacadeCallback<RESPONSE> callback,
                                     boolean async,
                                     boolean needDelegate,
                                     boolean needAuth) {

        callback = needDelegate ? new FacadeCallbackDelegate<RESPONSE>(callback) : callback;

        HttpUrl absoluteUrl = HttpUrl.parse(getAbsoluteUrl(url));
        HttpUrl.Builder urlBuilder = absoluteUrl.newBuilder();

        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                urlBuilder.addEncodedQueryParameter(param.getKey(), param.getValue());
            }
        }

        Request.Builder builder = new Request.Builder();

        builder.url(urlBuilder.build());
        builder.get();
        builder.tag(getFacadeTag());

        if (needAuth) {
            String token = getToken();
            if (token != null && !token.isEmpty()) {
                builder.header(HEADER_TOKEN, token);
            }
        }

        final Request request = builder.build();

        doRequest(request, responseClass, callback, async);
    }

    protected String getToken() {
        return mTokenKeeper.getToken();
    }

    protected <REQUEST, RESPONSE> void apiPost(String url,
                                               REQUEST requestObj,
                                               Class<RESPONSE> responseClass,
                                               FacadeCallback<RESPONSE> callback,
                                               boolean async) {
        callback = new FacadeCallbackDelegate<RESPONSE>(callback);

        String json = null;
        JsonAdapter<REQUEST> requestAdapter = (JsonAdapter<REQUEST>) mMoshi.adapter(requestObj.getClass());
        json = requestAdapter.toJson(requestObj);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);

        Request.Builder builder = new Request.Builder();
        builder.url(getAbsoluteUrl(url));
        builder.post(body);
        builder.tag(getFacadeTag());

        String token = getToken();
        if (token != null && !token.isEmpty()) {
            builder.header(HEADER_TOKEN, token);
        }

        final Request request = builder.build();
        doRequest(request, responseClass, callback, async);
    }

    protected <RESPONSE> void apiFormPost(String url, Map<String, String> fields, Class<RESPONSE> responseClass, FacadeCallback<RESPONSE> callback, boolean async) {
        apiFormPost(url, fields, responseClass, callback, async, true);
    }

    protected <RESPONSE> void apiFormPost(String url, Map<String, String> fields, Class<RESPONSE> responseClass, FacadeCallback<RESPONSE> callback, boolean async, boolean needAuth) {
        callback = new FacadeCallbackDelegate<RESPONSE>(callback);

        FormBody.Builder formBody = new FormBody.Builder();

        if (fields != null) {
            for (Map.Entry<String, String> field : fields.entrySet()) {
                formBody.add(field.getKey(), field.getValue());
            }
        }

        RequestBody body = formBody.build();

        Request.Builder builder = new Request.Builder();
        builder.url(getAbsoluteUrl(url));
        builder.tag(getFacadeTag());
        builder.post(body);

        if (needAuth) {
            String token = getToken();
            if (token != null && !token.isEmpty()) {
                builder.header(HEADER_TOKEN, token);
            }
        }

        final Request request = builder.build();
        doRequest(request, responseClass, callback, async);
    }

    private <RESPONSE> void doRequest(final Request request,
                                      final Class<RESPONSE> responseClass,
                                      final FacadeCallback<RESPONSE> callback,
                                      boolean async) {

        Callback responseCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    parseSuccessResult(response, responseClass, callback);
                } catch (Exception e) {
                    callback.onError(new FacadeException(e));
                }
            }

        };

        Call call = mClient.newCall(request);
        if (async) {
            call.enqueue(responseCallback);
        } else {
            try {
                Response response = call.execute();
                responseCallback.onResponse(call, response);
            } catch (IOException e) {
                responseCallback.onFailure(call, e);
            }
        }
    }

    protected <RESPONSE> RESPONSE apiGet(String url,
                                         Map<String, String> params,
                                         Class<RESPONSE> responseClass,
                                         boolean needAuth) throws Exception {

        HttpUrl absoluteUrl = HttpUrl.parse(getAbsoluteUrl(url));
        HttpUrl.Builder urlBuilder = absoluteUrl.newBuilder();

        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                urlBuilder.addEncodedQueryParameter(param.getKey(), param.getValue());
            }
        }

        Request.Builder builder = new Request.Builder();

        builder.url(urlBuilder.build());
        builder.get();
        builder.tag(getFacadeTag());

        if (needAuth) {
            String token = getToken();
            if (token != null && !token.isEmpty()) {
                builder.header(HEADER_TOKEN, token);
            }
        }

        final Request request = builder.build();

        return doRequest(request, responseClass);
    }

    private <RESPONSE> RESPONSE doRequest(final Request request,
                                          final Class<RESPONSE> responseClass) throws Exception {
        Response response = mClient.newCall(request).execute();
        String responseString = response.body().string();

        if (TextUtils.isEmpty(responseString) && response.isSuccessful()) {
            return null;
        }
        if (!response.isSuccessful()) {
            throw new FacadeException();
        }

        JSONObject fieldData = null;
        JSONObject fieldError = null;
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            fieldData = jsonObject.optJSONObject(FIELD_DATA);
            fieldError = jsonObject.optJSONObject(FIELD_ERROR);
        } catch (Exception e) {
            throw new FacadeException();
        }

        String dataString = fieldData != null ? fieldData.toString() : null;
        String errorString = fieldError != null ? fieldError.toString() : null;
        if (errorString == null) {
            JsonAdapter<RESPONSE> responseAdapter = mMoshi.adapter(responseClass);
            if (!TextUtils.isEmpty(dataString)) {
                return responseAdapter.fromJson(dataString);
            } else {
                return responseAdapter.fromJson(responseString);
            }
        } else {
            throw convertError(errorString);
        }
    }


    private <RESPONSE> void parseSuccessResult(Response response,
                                               Class<RESPONSE> responseClass,
                                               FacadeCallback<RESPONSE> callback)
            throws Exception {

        String responseString = response.body().string();
        if (TextUtils.isEmpty(responseString) && response.isSuccessful()) {
            callback.onSuccess(null);
            return;
        }
        if (!response.isSuccessful()) {
            FacadeException th = (FacadeException) convertError(responseString);
            th.setResponseCode(response.code());
            callback.onError(th);
            return;
        }

        JSONObject fieldData = null;
        try {
            fieldData = new JSONObject(responseString);
        } catch (Exception e) {
            callback.onError(new FacadeException());
            return;
        }
        String dataString = fieldData != null ? fieldData.toString() : null;
        JsonAdapter<RESPONSE> responseAdapter = mMoshi.adapter(responseClass);
        callback.onSuccess(responseAdapter.fromJson(dataString));
    }

    private Exception convertError(String errorString) {
        FacadeException facadeException = null;
        try {
            JsonAdapter<FacadeException> facadeExceptionAdapter = mMoshi.adapter(FacadeException.class);
            facadeException = facadeExceptionAdapter.fromJson(errorString);
        } catch (IOException e) {
        }
        if (facadeException != null) {
            return facadeException;
        }
        //todo
        return null;
    }

    private String getAbsoluteUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else {
            return mBaseUrl + url;
        }
    }

    /**
     * Class uses to call methods at main thread.
     *
     * @param <T> - type of callback result.
     */
    private class FacadeCallbackDelegate<T> implements FacadeCallback<T> {
        private Handler mHandler;
        private FacadeCallback<T> mCallback;

        FacadeCallbackDelegate(FacadeCallback<T> callback) {
            mCallback = callback;
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                mHandler = new Handler(mainLooper);
            }
        }

        @Override
        public void onSuccess(final T response) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onSuccess(response);
                    }
                });
            } else {
                mCallback.onSuccess(response);
            }
        }

        @Override
        public void onError(final Throwable th) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onError(th);
                    }
                });
            } else {
                mCallback.onError(th);
            }
        }
    }

    protected class ParamsBuilder {
        private Map<String, Object> values = new HashMap<>();

        public ParamsBuilder(String key, Object value) {
            values.put(key, value);
        }

        public ParamsBuilder addValue(String key, Object value) {
            values.put(key, value);
            return this;
        }

        public Map build() {
            return values;
        }
    }

}
