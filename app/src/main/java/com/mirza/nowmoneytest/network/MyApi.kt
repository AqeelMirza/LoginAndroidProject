package com.mirza.nowmoneytest.network

import com.mirza.nowmoneytest.network.responses.LoginResponse
import com.mirza.nowmoneytest.network.responses.ReceiverResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface MyApi {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    /*  @GET("api/Profiles/GetProfile?id={id}")
      fun getUser(
          @Path("id") id: String?,
          @Header("auth") authHeader: String?
      ): Call<UserProfile?>?*/

    @GET("/api/v1/receivers")
    suspend fun getReceiver(
        @Header("auth") authHeader: String?
    ): Response<List<ReceiverResponse>>


    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {
            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://nowmoney-test.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}