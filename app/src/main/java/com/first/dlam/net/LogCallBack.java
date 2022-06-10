package com.first.dlam.net;


import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Author: Parawayne
 * @CreateDate: 2020/4/2 10:12
 * @Description: 描述:
 */
public abstract class LogCallBack implements Callback {

    @Override
    public abstract void onFailure(Call call, IOException e);

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String string = response.body().string();
        if (response.code() == 200) {
            onResponse(call, response.code(), string);
        } else {

            try {
                onFailure(call,new IOException(JSONObject.parseObject(string).getString("message")));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public abstract void onResponse(Call call, int code, String result);
}
