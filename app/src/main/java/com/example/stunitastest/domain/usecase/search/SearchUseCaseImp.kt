package com.example.stunitastest.domain.usecase.search

import com.example.stunitastest.data.repository.SearchRepositoryImp
import com.example.stunitastest.domain.repository.SearchRepository
import com.example.stunitastest.domain.response.SearchResponse
import io.reactivex.Observable

object SearchUseCaseImp : SearchUseCase {
    private val searchRepository: SearchRepository by lazy {
        SearchRepositoryImp
    }

    override fun getImages(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Observable<SearchResponse> {
        return searchRepository.getImages(query, sort, page, size)
    }

}