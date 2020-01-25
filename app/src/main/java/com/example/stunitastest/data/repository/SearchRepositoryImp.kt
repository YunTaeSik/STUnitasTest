package com.example.stunitastest.data.repository

import com.example.stunitastest.data.source.remote.SearchService
import com.example.stunitastest.domain.repository.SearchRepository
import com.example.stunitastest.domain.response.SearchResponse
import io.reactivex.Observable

object SearchRepositoryImp : SearchRepository {
    private var searchService = SearchService.Creator.create()

    override fun getImages(query: String,sort: String,page: Int,size: Int): Observable<SearchResponse> {
    return searchService.getImages("KakaoAK f1328266d7ef1949f7cd02c8ba212a72",query,sort,page,size)
    }



}