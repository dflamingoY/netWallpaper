package com.mingo.baselibrary.net;

import android.annotation.SuppressLint;
import android.content.Context;

import com.mingo.baselibrary.net.interceptor.CustomCookJar;
import com.mingo.baselibrary.net.interceptor.ReceiveIntercept;
import com.google.gson.GsonBuilder;
import com.mingo.baselibrary.net.interceptor.LoggerIntercept;
import com.mingo.baselibrary.net.interceptor.RequestHeadInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpServer {
    @SuppressLint("StaticFieldLeak")
    private static HttpServer instance;

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    private Retrofit retrofit;

    public static HttpServer getInstance() {
        synchronized (HttpServer.class) {
            if (null == instance) {
                synchronized (HttpServer.class) {
                    instance = new HttpServer();
                }
            }
        }
        return instance;
    }

    private HttpServer() {
        retrofit = new Retrofit.Builder().baseUrl(HttpApi.PORT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava   RxJava2CallAdapterFactory 会一直报错, 不明白????
                .client(generator())
                .build();
    }

    public OkHttpClient generator() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .addInterceptor(new ReceiveIntercept(mContext))
                    .addInterceptor(new RequestHeadInterceptor(mContext))
                    .addInterceptor(new LoggerIntercept())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .cookieJar(new CustomCookJar())
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getApiServer(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    public String calcSign(String url) {
        StringBuilder sb = new StringBuilder();
        return null;
    }

}
