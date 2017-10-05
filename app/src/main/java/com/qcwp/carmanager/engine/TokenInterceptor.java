package com.qcwp.carmanager.engine;



import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.litesuits.common.io.IOUtils;
import com.qcwp.carmanager.APP;
import com.qcwp.carmanager.model.UserData;
import com.qcwp.carmanager.model.sqLiteModel.CarInfoModel;
import com.qcwp.carmanager.utils.Print;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.http.HTTP;


/**
 * Created by qyh on 2016/12/23.
 */

public class TokenInterceptor implements Interceptor {

    private List<String>needTokenAPIs;
    public TokenInterceptor(List<String> needTokenAPIs){
                this.needTokenAPIs=needTokenAPIs;
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
        String url=original.url().toString();
        String token = null;

        String method=url.replace(Engine.QiCheWangPing,"");
        for (String API:needTokenAPIs) {
            if (method.contains(API)){
                token=this.getNewToken(API);
                //请求体定制：统一添加token参数
                if(original.body() instanceof FormBody)/*带参数*/{
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    FormBody oldFormBody = (FormBody) original.body();
                    for (int i = 0;i<oldFormBody.size();i++){
                        Print.d("getNewToken",oldFormBody.encodedName(i)+"---"+oldFormBody.encodedValue(i));
                        newFormBody.addEncoded(oldFormBody.encodedName(i),oldFormBody.encodedValue(i));
                    }
                    Print.d("getNewToken","token---"+token);
                    if (token!=null) {
                        newFormBody.add("token", token);
                    }
                    requestBuilder.method(original.method(),newFormBody.build());
                }
                else if (original.body() instanceof RequestBody)/*不带参数*/{


                    FormBody.Builder newFormBody = new FormBody.Builder();
                    RequestBody oldFormBody = original.body();


                    Buffer buffer = new Buffer();
                    oldFormBody.writeTo(buffer);
                    Charset charset =  Charset.forName("UTF-8");
                    MediaType contentType = oldFormBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(charset);
                    }

                    String bodyString = buffer.clone().readString(charset);
                    Print.d("getNewToken","bodyString---"+bodyString);
                    try {
                        JSONObject josn=new JSONObject(bodyString);
                        Print.d("getNewToken","josn---"+josn);

                        Iterator<String> iterator=josn.keys();
                        for (Iterator iter = iterator; iter.hasNext();) {
                            String str = (String)iter.next();
                            String value = josn.optString(str);
                            newFormBody.add(str, value);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Print.d("getNewToken","token---"+token);


                    if (token!=null) {
                        newFormBody.add("token", token);
                    }
                    requestBuilder.method(original.method(),newFormBody.build());

                }

                break;
            }
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
        return response.code() == 404;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken(String method) throws IOException {
        Print.d("getNewToken",method+"------");
        String path =Engine.QiCheWangPing+"doAppToken_getToken";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String data = "username=" +UserData.getInstance().getUserName() + "&password=" + UserData.getInstance().getPassword()+"&url="+method;
            conn.setRequestProperty("Content-Length", data.length() + "");
            //允许向外面写数据
            conn.setDoOutput(true);
            //获取输出流
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String result =  IOUtils.toString(is, "UTF-8");
                JSONObject jSONObject=new JSONObject(result);
                Print.d("getNewToken",method+"------"+result);
                return jSONObject.optString("token");
            } else {
                Print.d("getNewToken",code+"------");
                return null;
            }
        } catch (Exception e) {
            Print.d("getNewToken","错误"+"------");
            return null;
        }
    }

}
