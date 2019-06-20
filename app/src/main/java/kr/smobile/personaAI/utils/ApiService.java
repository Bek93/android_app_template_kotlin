package kr.smobile.personaAI.utils;

import android.content.Context;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import kr.smobile.personaAI.base.PreferencesManager;
import kr.smobile.personaAI.model.AuthResponse;
import kr.smobile.personaAI.BuildConfig;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Bek on 05/07/2017.
 */

public class ApiService {
    private static Retrofit mRetrofit;

    public static int connectionTime = 1;

    public static Retrofit provideRetrofit(Context context) {
        if (mRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(provideOkHttpClient(context))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit;
    }

    private static OkHttpClient provideOkHttpClient(Context context) {

        return new OkHttpClient.Builder()
                .addInterceptor(provideAuthInterceptor(context))
                .addInterceptor(provideHttpLoggingInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private static Interceptor provideApiKeyInterceptor(Context context) {
        return chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl.Builder urlBuilder = originalHttpUrl.newBuilder();


            HttpUrl url = urlBuilder.build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);
            if (context != null) {
                AuthResponse accessToken = PreferencesManager
                        .getInstance(context).getAccessToken();

                if (accessToken != null) {
                    //requestBuilder.addHeader("Authorization", "Bearer " + accessToken.getAccessToken());
                }
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    private static Interceptor provideAuthInterceptor(Context context) {
        return chain -> {

            Request original = chain.request();

            HttpUrl originalHttpUrl = original.url();

            HttpUrl.Builder urlBuilder = originalHttpUrl.newBuilder();


            HttpUrl url = urlBuilder.build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            if (context != null) {
                AuthResponse accessToken = PreferencesManager.getInstance(context).getAccessToken();

                /*
                if (accessToken != null) {
                    if (!url.url().toString().endsWith("refresh")) {
                        if (url.url().toString().contains(BuildConfig.BASE_URL_FOR_CHECK)) {
                            requestBuilder.addHeader("Authorization", "Bearer " + accessToken.getAccessToken());
                        }
                    }
                }*/
            }


            // try the request
            Response response = chain.proceed(requestBuilder.build());
            return createWellDoneResponse(response, chain, requestBuilder, context);
        };
    }

    private static Response createWellDoneResponse(Response response, Interceptor.Chain chain, Request.Builder requestBuilder, Context context) {

        if (response.code() == 401) {
            connectionTime++;
            // get a new token (I use a synchronous Retrofit call)
            if (connectionTime < 10) {

                /*AuthResponse token = PreferencesManager.getInstance(context).getAccessToken();


                // create a new request and modify it accordingly using the new token
                token.setUser(null);
                retrofit2.Response<AuthResponse> refreshToken = null;
                try {
                    refreshToken = ApiService.provideApi(OnboardingApi.class, context).refreshToken(token).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (refreshToken.code() == 200) {
                    connectionTime = 1;
                    AuthResponse newtoken = refreshToken.body();
                    assert newtoken != null;
                    if (newtoken.getAccessToken() != null) {
                        token.setAccessToken(newtoken.getAccessToken());
                    }
                    if (newtoken.getRefreshToken() != null) {
                        token.setRefreshToken(newtoken.getRefreshToken());
                    }
                    PreferencesManager.getInstance(context).setAccessToken(new Gson().toJson(token));
                    requestBuilder.removeHeader("Authorization");
                    if (newtoken.getAccessToken() != null) {
                        requestBuilder.addHeader("Authorization", "Bearer " + newtoken.getAccessToken());
                    }

                    Request request = requestBuilder.build();

                    try {
                        return chain.proceed(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Function.logoutFromToken(context);
                    //return createWellDoneResponse(response, chain, requestBuilder, context);
                }*/
            } else {
                //Function.logoutFromToken(context, PreferencesManager.getInstance(context).getUser().getUserSeq());
                return response;
            }
            return response;
        }
        return response;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor.Level logLevel = (BuildConfig.DEBUG) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BODY;
        interceptor.setLevel(logLevel);
        return interceptor;
    }

    public static <T> T provideApi(Class<T> service, Context context) {
        return provideRetrofit(context).create(service);
    }
}

