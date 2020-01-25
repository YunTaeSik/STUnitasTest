package com.example.stunitastest.domain.repository

import com.example.stunitastest.domain.response.SearchResponse
import io.reactivex.Observable


/**
 * To make an interaction between [AlbumRepositoryImp] & [GetAlbumsUseCase]
 * */
interface SearchRepository {

    fun getImages(query: String, sort: String, page: Int, size: Int): Observable<SearchResponse>
}