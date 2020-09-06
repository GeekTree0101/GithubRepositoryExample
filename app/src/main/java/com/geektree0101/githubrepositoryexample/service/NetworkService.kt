package com.geektree0101.githubrepositoryexample.service

import okhttp3.*
import java.io.IOException

class NetworkService: Callback {

    var httpUrl: HttpUrl? = null
    var onSuccessHandler: ((Response) -> Unit)? = null
    var onErrorHandler: ((IOException) -> Unit)? = null

    fun addHTTPURL(httpUrl: HttpUrl): NetworkService {
        this.httpUrl = httpUrl
        return this
    }

    fun execute() {
        val client = OkHttpClient()

        this.httpUrl?.let {
            val request = Request.Builder()
                .url(it)
                .get()
                .build()

            client.newCall(request).enqueue(this)
        }
    }

    fun onSuccess(handler: (Response) -> Unit): NetworkService {
        this.onSuccessHandler = handler
        return this
    }

    fun onError(handler: (IOException) -> Unit): NetworkService {
        this.onErrorHandler = handler
        return this
    }

    override fun onResponse(call: Call, response: Response) {
        this.onSuccessHandler?.invoke(response)
    }

    override fun onFailure(call: Call, e: IOException) {
        this.onErrorHandler?.invoke(e)
    }
}