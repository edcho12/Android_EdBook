package com.be.edbook.service.model.kakao;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoBookApiService {
    public String BASE_URL = "https://dapi.kakao.com/";


    // ad19ed209cb83c5d50cfc1xxxxxxxxxx 부분은 본인의 카카오 개발자계정으로 할당받은 RestAPI Key를 지정하세요.
    public String APP_KEY = "KakaoAK ad19ed209cb83c5d50cfc1xxxxxxxxxx"; //My Rest api key of KakaoDev


    public int BOOKS_SIZE = 10;
    public String SORT_TYPE = "accuracy";
    public String TARGET_TYPE = "title";


    @GET("v3/search/book")
    Call<ResponseData> getBookSearchList(
            @Header("Authorization") String appKey,
            @Query("query") String query,
            @Query("sort") String sort,
            @Query("target") String target,
            @Query("size") int size,
            @Query("page") int page);



    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
