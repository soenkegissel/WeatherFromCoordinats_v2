package com.example.maks2.weathertocoordinats.network;


import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request().newBuilder()
        .addHeader("Content-Type", "application/json")
        .addHeader("charset","UTF-8")
        .build();
    return chain.proceed(request);
  }
}
