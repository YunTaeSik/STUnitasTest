package com.example.stunitastest.domain.response

public class SearchResponse {
    val meta : ResultMeta? = null
    val documents : List<ResultDocument>? = null

   public class ResultMeta{
        val total_count : Int? = null
        val pageable_count : Int? = null
        val is_end :Boolean? = null
    }
    public class ResultDocument{
        val collection :String? = null
        val thumbnail_url :String? = null
        val image_url :String? = null
        val width :Int? = null
        val height :Int? = null
        val display_sitename :String? = null
        val doc_url :String? = null
        val datetime :String? = null


    }
}