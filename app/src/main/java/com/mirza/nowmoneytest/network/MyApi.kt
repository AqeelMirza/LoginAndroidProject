package com.mirza.nowmoneytest.network

import com.mirza.nowmoneytest.db.entities.Receiver
import com.mirza.nowmoneytest.network.responses.LoginResponse
import com.mirza.nowmoneytest.network.responses.ReceiverResponse
import com.mirza.nowmoneytest.network.responses.UpdateReceiverResponse
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

    @GET("receivers")
    suspend fun getReceiver(
        @Header("auth") authHeader: String?
    ): Response<ReceiverResponse>

    @FormUrlEncoded
    @POST(" receivers/add")
    suspend fun addReceiver(
        @Header("auth") authHeader: String?,
        @Field("name") name: String,
        @Field("number") number: String,
        @Field("address") address: String
    ): Response<UpdateReceiverResponse>

    @POST("receivers/{id}/delete")
    suspend fun deleteReceiver(
        @Path("id") id: String,
        @Header("auth") authHeader: String
    ): Response<UpdateReceiverResponse>


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