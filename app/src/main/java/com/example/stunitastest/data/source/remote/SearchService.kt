package com.example.stunitastest.data.source.remote

import com.example.stunitastest.domain.response.SearchResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchService {
    //유저 정보 얻어오기

    @GET("search/image")
    fun getImages(
        @Header("Authorization") serverAuth: String,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
        ): Observable<SearchResponse>


    object Creator {
        private val URL = "https://dapi.kakao.com/v2/"

        fun create(): SearchService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
            return retrofit.create(SearchService::class.java)
        }
    }
}