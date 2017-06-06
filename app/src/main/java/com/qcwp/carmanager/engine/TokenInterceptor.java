package com.qcwp.carmanager.engine;



import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by qyh on 2016/12/23.
 */

public class TokenInterceptor implements Interceptor {

    public TokenInterceptor(){


    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
////        Response response = chain.proceed(request);
////
////        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
////            //同步请求方式，获取最新的Token
////            String newSession = getNewToken();
////            //使用新的Token，创建新的请求
//        RequestBody body=request.body();

//        body.
//            Request newRequest = chain.request()
//                    .newBuilder()
//                    .header("did",deviceId).method(request.method(),request.body())
//                    .build();
//            //重新请求
//            return chain.proceed(newRequest);
////        }



        Request original = chain.request();
        //请求定制：添加请求头
        Request.Builder requestBuilder = original.newBuilder() /*.header("APIKEY", Constant.API_KEY)*/;


        //请求体定制：统一添加token参数
        if(original.body() instanceof FormBody)/*带参数*/{
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) original.body();
            for (int i = 0;i<oidFormBody.size();i++){
                newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
            }
            requestBuilder.method(original.method(),newFormBody.build());
        }
        else if (original.body() instanceof RequestBody)/*不带参数*/{
            FormBody.Builder newFormBody = new FormBody.Builder();
            requestBuilder.method(original.method(),newFormBody.build());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);

    }



    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == 404) {
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求

        return null;
    }

}
