package com.mirza.nowmoneytest.receiver

import com.mirza.nowmoneytest.network.MyApi
import com.mirza.nowmoneytest.network.responses.LoginResponse
import com.mirza.nowmoneytest.network.responses.UpdateReceiverResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit

class ReceiverActivityUnitTest {

    @Test
    fun addReceiverCheck() {
        val (mockWebServer, service: MyApi) = networkSetup()

        //With your service created you can now call its method that should
        //consume the MockResponse above. You can then use the desired
        //assertion to check if the result is as expected. For example:
        GlobalScope.launch(Dispatchers.IO) {
            val response: Response<UpdateReceiverResponse> =
                service.addReceiver("auth", "Test123", "1234567890", "AddressTest")
            if (response.isSuccessful) {
                assert(true)
            }
        }

        //Finish web server
        mockWebServer.shutdown()
    }

    @Test
    fun deleteReceiverCheck() {
        val (mockWebServer, service: MyApi) = networkSetup()

        //With your service created you can now call its method that should
        //consume the MockResponse above. You can then use the desired
        //assertion to check if the result is as expected. For example:
        GlobalScope.launch(Dispatchers.IO) {
            val response: Response<UpdateReceiverResponse> =
                service.deleteReceiver("auth", "1")
            if (response.isSuccessful) {
                assert(true)
            }
        }

        //Finish web server
        mockWebServer.shutdown()
    }


    private fun networkSetup(): Pair<MockWebServer, MyApi> {
        val mockWebServer = MockWebServer()
        val retrofit = Retrofit.Builder()
            .baseUrl(
                mockWebServer.url("https://nowmoney-test.herokuapp.com/api/v1/receivers/add/")
                    .toString()
            )
            .build()

        //Set a response for retrofit to handle. You can copy a sample
        //response from your server to simulate a correct result or an error.
        //MockResponse can also be customized with different parameters
        //to match your test needs
        mockWebServer.enqueue(MockResponse())
        val service: MyApi =
            retrofit.create<MyApi>(MyApi::class.java)
        return Pair(mockWebServer, service)
    }
}