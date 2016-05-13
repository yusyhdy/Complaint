package com.smkpgri2.alaska.service;

import com.smkpgri2.alaska.entity.Category;
import com.smkpgri2.alaska.entity.Complaint;
import com.smkpgri2.alaska.entity.Page;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public interface CategoryClient {
    @GET("api/categories")
    Call<Page<Category>> getCategories();

    @GET("api/categories")
    Call<Page<Category>> getCategories(@Query("title") String title);

    @POST("api/categories")
    Call<Category> postCategory(@Body Category category);

    @PUT("api/categories/{id}")
    Call<Category> putCategory(@Path("id") int id, @Body Category category);

    @DELETE("api/categories/{id}")
    Call<Void> deleteCategory(@Path("id") int id);
}
