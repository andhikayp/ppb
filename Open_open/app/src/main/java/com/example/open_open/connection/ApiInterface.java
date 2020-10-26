package com.example.open_open.connection;

import com.example.open_open.pojo.SubmitData;

//import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("predict")
    Call<SubmitData> update_data(@Part MultipartBody.Part foto, @Part("nama_usaha") RequestBody nama_usaha);
}
