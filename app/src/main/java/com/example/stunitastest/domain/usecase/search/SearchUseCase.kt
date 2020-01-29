package com.example.stunitastest.domain.usecase.search

import com.example.stunitastest.domain.response.SearchResponse
import com.example.stunitastest.domain.usecase.BaseUseCase
import io.reactivex.Observable

interface SearchUseCase : BaseUseCase {

    fun getImages(query: String, sort: String?, page: Int?, size: Int?): Observable<SearchResponse>
}