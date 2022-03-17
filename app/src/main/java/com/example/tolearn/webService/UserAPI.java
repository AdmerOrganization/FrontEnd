package com.example.tolearn.webService;

import com.example.tolearn.Entity.User;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UserAPI {
    public static final String BASE_URL="https://amoozande.herokuapp.com/";
    @Headers({"Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("accounts/signup/")
    Call<JsonObject> CreateUser(@Header("Content-Type") String content_type, @Body User user);

    @POST("accounts/signin/")
    Call<JsonObject> Login(@Header("Content-Type") String content_type , @Body User user);

    @POST("accounts/user/")
    Call<User> showProfile(@Header("Authorization") String user_token);

    @PUT("accounts/edit-profile/")
    Call<User> editProfile(@Header("Authorization") String user_token , @Body User user);

    @Multipart
    @PUT("accounts/edit-profile/")
    Call<User> editProfile(@Header("Authorization") String user_token , @Part("email") RequestBody email,
                           @Part("first_name")RequestBody firstName,
                           @Part("last_name")RequestBody lastName,
                           @Part("phone_number")RequestBody phoneNumber,
                           @Part MultipartBody.Part image);

    @POST("accounts/password-reset/")
    Call<JsonObject> Password_reset(@Header("Content-Type") String content_type , @Body JsonObject email);

    @POST("accounts/password-reset/confirm/")
    Call<JsonObject> ConfirmationPassword_reset(@Header("Content-Type") String content_type , @Body JsonObject token_password);

}
