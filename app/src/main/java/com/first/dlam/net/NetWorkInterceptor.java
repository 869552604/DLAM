package com.first.dlam.net;




import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * OkHttp 这个工具类中 初始化 OkHttpClient 中使用到
 *
 * OkHttpClient oneClient = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.SECONDS)
 *             .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).addInterceptor(new NetWorkInterceptor())
 *             .build()
 */

public class NetWorkInterceptor implements Interceptor {

    public static String TAG = "NetWorkInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);
        return response;
    }

    /**
     * 读取参数
     *
     * @param requestBody
     * @return
     */
    private String getParam(RequestBody requestBody) {
        Buffer buffer = new Buffer();
        String logparm;
        try {
            requestBody.writeTo(buffer);
            logparm = buffer.readUtf8();
            logparm = URLDecoder.decode(logparm, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return logparm;
    }
}