package com.example.films.data;

import com.example.films.data.entity.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {
    @GET("movie")
    Observable<Response> getFilmsResponse(@Query("api_key") String apiKey, @Query("page") String page, @Query("language") String lang, @Query("sort_by") String sort, @Query("vote_count.gte") String min_vote);
}
