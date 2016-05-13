package com.smkpgri2.alaska.service;

import com.smkpgri2.alaska.entity.Complaint;
import com.smkpgri2.alaska.entity.Page;

import java.util.List;

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
public interface ComplaintClient {
    @GET("api/complaints")
    Call<Page<Complaint>> getComplaints();

    @GET("api/complaints")
    Call<Page<Complaint>> getComplaints(@Query("q") String q);

    @POST("api/complaints")
    Call<Complaint> postComplaints(@Body Complaint complaint);

    @PUT("api/complaints/{id}")
    Call<Complaint> putComplaints(@Path("id") int id, @Body Complaint complaint);

    @DELETE("api/complaints/{id}")
    Call<Void> deleteComplaints(@Path("id") int id);
}
