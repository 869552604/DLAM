package com.first.dlam.net;

import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
/**
 * okhttp的封装
 *
 * @author MKW
 */
public class OkHttp {
    /**
     * cache设置
     */
    private static final CacheControl noCache = new CacheControl.Builder().noCache().build();
    /**
     * text media type
     */
    public static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/text; charset=utf-8");
    /**
     * json media type
     */
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * steam media type
     */
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream; charset=utf-8");

    /**
     * okhttp客户端，默认只有一个
     */

    static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    private static final OkHttpClient oneClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).addInterceptor(new NetWorkInterceptor())
            .build();

    /**
     * 同步get
     *
     * @param url
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String get(String url) throws IOException, JSONException, OkHttpException {
        return get(url, new JSONObject());
    }

    /**
     * 同步get,带map参数
     *
     * @param url
     * @param mapData
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String get(String url, Map<String, Object> mapData)
            throws IOException, JSONException, OkHttpException {
        return get(url, new JSONObject(mapData));
    }

    /**
     * 同步get，带json参数
     *
     * @param url
     * @param jsonData
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String get(String url, JSONObject jsonData) throws IOException, JSONException, OkHttpException {
        return get(new JSONObject(), url, jsonData);
    }

    /**
     * 同步get，带map头
     *
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String get(Map<String, Object> mapHeader, String url)
            throws IOException, JSONException, OkHttpException {
        return get(new JSONObject(mapHeader), url);
    }

    /**
     * 同步get，带map参数和map头
     *
     * @param url
     * @param mapData
     * @param mapHeader
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String get(Map<String, Object> mapHeader, String url, Map<String, Object> mapData)
            throws IOException, JSONException, OkHttpException {
        return get(new JSONObject(mapHeader), url, new JSONObject(mapData));
    }

    /**
     * 同步get，带json头
     *
     * @param jsonHeader
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String get(JSONObject jsonHeader, String url) throws IOException, JSONException, OkHttpException {
        return get(jsonHeader, url, new JSONObject());
    }

    /**
     * 同步get,带json参数和json头
     *
     * @param url
     * @param jsonData
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String get(JSONObject jsonHeader, String url, JSONObject jsonData)
            throws IOException, JSONException, OkHttpException {
        try {
            Request request = buildRequestGet(jsonHeader, url, jsonData);
            Response response = oneClient.newBuilder().build().newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException(url + "请求失败" + response.code() + ":" + response.body().string());
            }
            String rslt = response.body().string();
            return rslt;
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 异步get
     *
     * @param url
     * @param responseCallback
     * @throws IOException
     * @throws JSONException
     */
    public static void get(String url, Callback responseCallback) throws IOException, JSONException, OkHttpException {
        get(url, new JSONObject(), responseCallback);
    }

    /**
     * 异步get，带map参数
     *
     * @param url
     * @param mapData
     * @param responseCallback
     * @throws IOException
     * @throws JSONException
     */
    public static void get(String url, Map<String, Object> mapData, Callback responseCallback)
            throws IOException, JSONException, OkHttpException {
        get(url, new JSONObject(mapData), responseCallback);
    }

    /**
     * 异步get，带json参数
     *
     * @param url
     * @param jsonData
     * @param responseCallback
     * @throws IOException
     * @throws JSONException
     */
    public static void get(String url, JSONObject jsonData, Callback responseCallback)
            throws JSONException, OkHttpException {
        get(new JSONObject(), url, jsonData, responseCallback);
    }

    /**
     * 异步get，带map头
     *
     * @param mapHeader
     * @param url
     * @param responseCallback
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void get(Map<String, Object> mapHeader, String url, Callback responseCallback)
            throws IOException, JSONException, OkHttpException {
        get(new JSONObject(mapHeader), url, responseCallback);
    }

    /**
     * 异步get，带map头、map数据
     *
     * @param mapHeader
     * @param url
     * @param mapData
     * @param responseCallback
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void get(Map<String, Object> mapHeader, String url, Map<String, Object> mapData,
                           Callback responseCallback) throws IOException, JSONException, OkHttpException {
        get(new JSONObject(mapHeader), url, new JSONObject(mapData), responseCallback);
    }

    /**
     * 异步get，带json头
     *
     * @param jsonHeader
     * @param url
     * @param responseCallback
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void get(JSONObject jsonHeader, String url, Callback responseCallback)
            throws IOException, JSONException, OkHttpException {
        get(jsonHeader, url, new JSONObject(), responseCallback);
    }

    /**
     * 异步get，带json头、json参数
     *
     * @param jsonHeader
     * @param url
     * @param jsonData
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void get(JSONObject jsonHeader, String url, JSONObject jsonData, Callback responseCallback)
            throws JSONException, OkHttpException {
        try {
            Request request = buildRequestGet(jsonHeader, url, jsonData);
            oneClient.newBuilder().build().newCall(request).enqueue(responseCallback);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 同步post
     *
     * @param url
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     */
    public static String post(String url) throws IOException, JSONException, OkHttpException {
        return post(url, "");
    }

    /**
     * 同步post，带body
     *
     * @param url
     * @param stringBody
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(String url, String stringBody) throws IOException, JSONException, OkHttpException {
        return post(new JSONObject(), url, stringBody);
    }

    /**
     * 同步post，带map头、body
     *
     * @param mapHeader
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(Map<String, Object> mapHeader, String url)
            throws IOException, JSONException, OkHttpException {
        return post(new JSONObject(mapHeader), url);
    }

    /**
     * 同步post，带map头、body
     *
     * @param mapHeader
     * @param url
     * @param stringBody
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(Map<String, Object> mapHeader, String url, String stringBody)
            throws IOException, JSONException, OkHttpException {
        return post(new JSONObject(mapHeader), url);
    }

    /**
     * 同步post，带json头
     *
     * @param jsonHeader
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(JSONObject jsonHeader, String url) throws IOException, JSONException, OkHttpException {
        return post(jsonHeader, url, "");
    }

    /**
     * 同步post，带json头、body
     *
     * @param url
     * @param stringBody
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(JSONObject jsonHeader, String url, String stringBody)
            throws IOException, JSONException, OkHttpException {
        try {
            Request request = buildRequestPost(jsonHeader, url, stringBody);
            Response response = oneClient.newBuilder().build().newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException(url + "请求失败" + response.code() + ":" + response.body().string());
            }
            String rslt = response.body().string();
            return rslt;
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    public static String post(String url, String stringBody, SSLSocketFactory sslSocketFactory)
            throws IOException, JSONException, OkHttpException {
        try {
            Request request = buildRequestPost(new JSONObject(), url, stringBody);
            Response response = oneClient.newBuilder().socketFactory(sslSocketFactory).build().newCall(request)
                    .execute();

            if (!response.isSuccessful()) {
                throw new IOException(url + "请求失败" + response.code() + ":" + response.body().string());
            }
            String rslt = response.body().string();
            return rslt;
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 同步post,带map数据
     *
     * @param url
     * @param mapData
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(String url, Map<String, Object> mapData)
            throws IOException, JSONException, OkHttpException {
        return post(url, new JSONObject(mapData));
    }

    /**
     * 同步post,带json数据
     *
     * @param url
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(String url, JSONObject jsonData) throws IOException, JSONException, OkHttpException {
        return post(new JSONObject(), url, jsonData);
    }

    /**
     * 同步post,带map头、map数据
     *
     * @param mapHeader
     * @param url
     * @param mapData
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(Map<String, Object> mapHeader, String url, Map<String, Object> mapData)
            throws IOException, JSONException, OkHttpException {
        return post(new JSONObject(mapHeader), url, new JSONObject(mapData));
    }

    /**
     * 同步post，带json头、json数据
     *
     * @param url
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     */
    public static String post(JSONObject jsonHeader, String url, JSONObject jsonData)
            throws IOException, JSONException, OkHttpException {
        try {
            Request request = buildRequestPost(jsonHeader, url, jsonData);
            Response response = oneClient.newBuilder().build().newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException(url + "请求失败" + response.code() + ":" + response.body().string());
            }
            String rslt = response.body().string();
            return rslt;
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 同步post，带map数据、map文件
     *
     * @param url
     * @param mapData
     * @param mapFile
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(String url, Map<String, Object> mapData, Map<String, Object> mapFile)
            throws IOException, JSONException, OkHttpException {
        return post(url, new JSONObject(mapData), new JSONObject(mapFile));
    }

    /**
     * 同步post，带json数据、json文件
     *
     * @param url
     * @param jsonData
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     * @throws OkHttpException
     */
    public static String post(String url, JSONObject jsonData, JSONObject jsonFile)
            throws IOException, JSONException, OkHttpException {
        return post(new JSONObject(), url, jsonData, jsonFile);
    }

    /**
     * 同步post,带map头、map数据、map文件
     *
     * @param mapHeader
     * @param url
     * @param mapData
     * @return
     */
    public static String post(Map<String, Object> mapHeader, String url, Map<String, Object> mapData,
                              Map<String, Object> mapFile) throws IOException, JSONException, OkHttpException {
        return post(new JSONObject(mapHeader), url, new JSONObject(mapData), new JSONObject(mapFile));
    }

    /**
     * 同步post
     *
     * @param url
     * @return 请求结果
     * @throws IOException
     * @throws JSONException
     */
    public static String post(JSONObject jsonHeader, String url, JSONObject jsonData, JSONObject jsonFile)
            throws IOException, JSONException, OkHttpException {
        try {
            Request request = buildRequestPost(jsonHeader, url, jsonData, jsonFile);
            Response response = oneClient.newBuilder().build().newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException(url + "请求失败" + response.code() + ":" + response.body().string());
            }
            String rslt = response.body().string();
            return rslt;
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 异步post
     *
     * @param url
     * @param responseCallback
     * @throws IOException
     * @throws JSONException
     */
    public static void post(String url, Callback responseCallback) throws JSONException, OkHttpException {
        post(url, "", responseCallback);
    }

    /**
     * 异步post，带 body
     *
     * @param url
     * @param stringBody
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(String url, String stringBody, Callback responseCallback)
            throws JSONException, OkHttpException {
        post(new JSONObject(), url, stringBody, responseCallback);
    }

    /**
     * 异步post，带map头
     *
     * @param mapHeader
     * @param url
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(Map<String, Object> mapHeader, String url, Callback responseCallback)
            throws JSONException, OkHttpException {
        post(new JSONObject(mapHeader), url, "", responseCallback);
    }

    /**
     * 异步post，带map头、body
     *
     * @param mapHeader
     * @param url
     * @param stringBody
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(Map<String, Object> mapHeader, String url, String stringBody, Callback responseCallback)
            throws JSONException, OkHttpException {
        post(new JSONObject(mapHeader), url, stringBody, responseCallback);
    }

    /**
     * 异步post，带json头
     *
     * @param jsonHeader
     * @param url
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(JSONObject jsonHeader, String url, Callback responseCallback)
            throws JSONException, OkHttpException {
        post(new JSONObject(), url, "", responseCallback);
    }

    /**
     * 异步post，带json头、body
     *
     * @param jsonHeader
     * @param url
     * @param stringBody
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(JSONObject jsonHeader, String url, String stringBody, Callback responseCallback)
            throws JSONException, OkHttpException {
        try {
            Request request = buildRequestPost(jsonHeader, url, stringBody);
            oneClient.newBuilder().build().newCall(request).enqueue(responseCallback);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 异步post，带map数据
     *
     * @param url
     * @param mapData
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(String url, Map<String, Object> mapData, Callback responseCallback)
            throws JSONException, OkHttpException {
        post(url, new JSONObject(mapData), responseCallback);
    }

    /**
     * 异步post，带json数据
     *
     * @param url
     * @param jsonData
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(String url, JSONObject jsonData, Callback responseCallback)
            throws JSONException, OkHttpException {
        post(new JSONObject(), url, jsonData, responseCallback);
    }

    /**
     * 异步post，带map头、map数据
     *
     * @param mapHeader
     * @param url
     * @param mapData
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(Map<String, Object> mapHeader, String url, Map<String, Object> mapData,
                            Callback responseCallback) throws JSONException, OkHttpException {
        post(new JSONObject(mapHeader), url, new JSONObject(mapData), responseCallback);
    }

    /**
     * 异步post，带json头、json数据
     *
     * @param jsonHeader
     * @param url
     * @param jsonData
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(JSONObject jsonHeader, String url, JSONObject jsonData, Callback responseCallback)
            throws JSONException, OkHttpException {
        try {
            Request request = buildRequestPost(jsonHeader, url, jsonData);
            oneClient.newBuilder().build().newCall(request).enqueue(responseCallback);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 异步post，带map数据、map文件
     *
     * @param url
     * @param mapData
     * @param mapFile
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(String url, Map<String, Object> mapData, Map<String, Object> mapFile,
                            Callback responseCallback) throws JSONException, OkHttpException {
        post(url, new JSONObject(mapData), new JSONObject(mapFile), responseCallback);
    }

    /**
     * 异步post，带json数据、json文件
     *
     * @param url
     * @param jsonData
     * @param jsonFile
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(String url, JSONObject jsonData, JSONObject jsonFile, Callback responseCallback)
            throws JSONException, OkHttpException {
        post(new JSONObject(), url, jsonData, jsonFile, responseCallback);
    }

    /**
     * 异步post，带map头、map数据、map文件
     *
     * @param mapHeader
     * @param url
     * @param mapData
     * @param mapFile
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(Map<String, Object> mapHeader, String url, Map<String, Object> mapData,
                            Map<String, Object> mapFile, Callback responseCallback) throws JSONException, OkHttpException {
        post(new JSONObject(mapHeader), url, new JSONObject(mapData), new JSONObject(mapFile), responseCallback);
    }

    /**
     * 异步post，带json头、json数据、json文件
     *
     * @param jsonHeader
     * @param url
     * @param jsonData
     * @param jsonFile
     * @param responseCallback
     * @throws JSONException
     * @throws OkHttpException
     */
    public static void post(JSONObject jsonHeader, String url, JSONObject jsonData, JSONObject jsonFile,
                            Callback responseCallback) throws JSONException, OkHttpException {
        try {
            Request request = buildRequestPost(jsonHeader, url, jsonData, jsonFile);
            oneClient.newBuilder().build().newCall(request).enqueue(responseCallback);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 同步下载
     *
     * @param url
     * @return response
     * @throws IOException
     * @throws JSONException
     */
    public static Response download(String url) throws IOException, JSONException, OkHttpException {
        Request request = buildRequestGet(url);
        Response response = oneClient.newBuilder().build().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException(url + "请求失败" + response.code() + ":" + response.body().string());
        }
        return response;
    }

    /**
     * 异步下载
     *
     * @param url
     * @param responseCallback
     * @throws IOException
     * @throws JSONException
     */
    public static void download(final String url, final Callback responseCallback)
            throws IOException, JSONException, OkHttpException {
        Request request = buildRequestGet(url);
        oneClient.newBuilder().build().newCall(request).enqueue(responseCallback);
    }

    /**
     * 构建request,get
     *
     * @param url
     * @return
     * @throws JSONException
     * @throws OkHttpException
     */
    public static Request buildRequestGet(String url) throws JSONException, OkHttpException {
        return buildRequestGet(url, new JSONObject());
    }

    /**
     * 构建request，get带json参数
     *
     * @param url
     * @param jsonData
     * @return
     * @throws JSONException
     * @throws OkHttpException
     */
    public static Request buildRequestGet(String url, JSONObject jsonData) throws JSONException, OkHttpException {
        return buildRequestGet(new JSONObject(), url, jsonData);
    }

    /**
     * 构建request，提交json头、json参数
     *
     * @param url
     * @param jsonData
     * @return Request
     * @throws JSONException
     */
    public static Request buildRequestGet(JSONObject jsonHeader, String url, JSONObject jsonData)
            throws JSONException, OkHttpException {
        try {
            Request.Builder requestBuilder = new Request.Builder();
            // 处理header
            for (String key : jsonHeader.keySet()) {
                requestBuilder.header(key, jsonHeader.getString(key));
            }
            String stringUrl = url;

            // 处理请求数据
            if (jsonData.size() > 0) {
                if (stringUrl.indexOf("?") == -1) {
                    stringUrl += "?";
                }
                for (String key : jsonData.keySet()) {
                    String value = jsonData.getString(key);
                    if (StringUtils.isEmpty(value)) {
                        stringUrl += "&" + key + "=";
                    } else {
                        stringUrl += "&" + key + "=" + value;
                    }
                }
            }
            requestBuilder.url(stringUrl);
            Log.d("Http", "buildRequestGet: " + stringUrl);
            return requestBuilder.build();
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 构建request，post body
     *
     * @param url
     * @param stringBody
     * @return Request
     * @throws JSONException
     * @throws OkHttpException
     */
    public static Request buildRequestPost(String url, String stringBody) throws JSONException, OkHttpException {
        return buildRequestPost(new JSONObject(), url, stringBody);
    }

    /**
     * 构建request，post body
     *
     * @param jsonHeader
     * @param url
     * @param stringBody
     * @return Request
     * @throws JSONException
     * @throws OkHttpException
     */
    public static Request buildRequestPost(JSONObject jsonHeader, String url, String stringBody)
            throws JSONException, OkHttpException {
        try {
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(url);
            // 处理header
            for (String key : jsonHeader.keySet()) {
                requestBuilder.header(key, jsonHeader.getString(key));
            }
            // 处理请求数据
            requestBuilder.post(RequestBody.create(MEDIA_TYPE_TEXT, stringBody));
            return requestBuilder.build();
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 构建request，提交数据
     *
     * @param url
     * @param jsonData
     * @return Request
     * @throws JSONException
     * @throws OkHttpException
     */
    public static Request buildRequestPost(String url, JSONObject jsonData) throws JSONException, OkHttpException {
        return buildRequestPost(new JSONObject(), url, jsonData);
    }

    /**
     * 构建request，提交头、数据
     *
     * @param jsonHeader
     * @param url
     * @param jsonData
     * @return
     * @throws JSONException
     * @throws OkHttpException
     */
    public static Request buildRequestPost(JSONObject jsonHeader, String url, JSONObject jsonData)
            throws JSONException, OkHttpException {
        try {
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(url);
            // 处理header
            for (String key : jsonHeader.keySet()) {
                requestBuilder.header(key, jsonHeader.getString(key));
            }
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            // 处理数据
            if (jsonData.size() > 0) {
                Set<String> keys = jsonData.keySet();
                for (String key : keys) {
                    String value = jsonData.getString(key);
                    if (StringUtils.isEmpty(value)) {
                        formBodyBuilder.add(key, "");
                    } else {
                        formBodyBuilder.add(key, jsonData.getString(key));
                    }
                }
            }
            requestBuilder.post(formBodyBuilder.build());
            return requestBuilder.build();
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 构建request，提交数据、附件
     *
     * @param url
     * @param jsonData
     * @param jsonFile
     * @return Request
     * @throws JSONException
     * @throws OkHttpException
     */
    public static Request buildRequestPost(String url, JSONObject jsonData, JSONObject jsonFile)
            throws JSONException, OkHttpException {
        return buildRequestPost(new JSONObject(), url, jsonData, jsonFile);
    }

    /**
     * 构建request，提交数据、附件、头
     *
     * @param url
     * @param jsonData
     * @return Request
     * @throws JSONException
     */
    public static Request buildRequestPost(JSONObject jsonHeader, String url, JSONObject jsonData, JSONObject jsonFile)
            throws JSONException, OkHttpException {
        try {
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(url);
            // 处理header
            for (String key : jsonHeader.keySet()) {
                requestBuilder.header(key, jsonHeader.getString(key));
            }
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
            multipartBuilder.setType(MultipartBody.FORM);
            // 处理数据
            if (jsonData.size() > 0) {
                Set<String> keys = jsonData.keySet();
                for (String key : keys) {
                    String value = jsonData.getString(key);
                    if (StringUtils.isEmpty(value)) {
                        multipartBuilder.addFormDataPart(key, "");
                    } else {
                        multipartBuilder.addFormDataPart(key, value);
                    }
                }
            }
            // 处理附件
            if (jsonFile.size() > 0) {
                Set<String> files = jsonFile.keySet();
                for (String key : files) {
                    String value = jsonFile.getString(key);
                    if (StringUtils.isNotEmpty(value)) {
                        File f = new File(jsonFile.getString(key));
                        multipartBuilder.addFormDataPart("file", value, RequestBody.create(MEDIA_TYPE_STREAM, f));
                    }
                }
            }
            // if (jsonData.size() > 0 || jsonFile.size() > 0) {
            requestBuilder.post(multipartBuilder.build());
            // }
            return requestBuilder.build();
        } catch (JSONException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new OkHttpException(e);
        }
    }

    public static JSONObject parseParam(String uri) {
        JSONObject juri = new JSONObject();
        if (uri.indexOf("?") > 0) {
            juri.put("uri", uri.substring(0, uri.indexOf("?")).trim());
            String ps = uri.substring(uri.indexOf("?") + 1).trim();
            String[] pss = ps.split("s");
            JSONObject params = new JSONObject();
            for (String p : pss) {
                if (StringUtils.isBlank(p)) {
                    continue;
                }
                if (p.indexOf("=") > 0) {
                    params.put(p.substring(0, p.indexOf("=")).trim(), p.substring(p.indexOf("=") + 1).trim());
                } else {
                    params.put(p.trim(), "");
                }
            }
            juri.put("params", params);
        } else {
            juri.put("uri", uri.trim());
        }
        return juri;
    }

    public static SSLSocketFactory getSSlSocketFactory(String certFile, String certPwd) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certFile));// 加载本地的证书进行https加密传输
        keyStore.load(instream, certPwd.toCharArray());// 设置证书密码

        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());

        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        return sslContext.getSocketFactory();
    }

    private static class HttpLogger implements HttpLoggingInterceptor.Logger {
        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = formatJson(decodeUnicode(message));
            }
            mMessage.append(message.concat("\n"));
            // 响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Log.i("TAG",mMessage.toString());
            }
        }
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return 格式化后的json串
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            //遇到{ [换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                //遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                //遇到,换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param theString
     * @return 转化后的结果.
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

}
