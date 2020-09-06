package com.geektree0101.githubrepositoryexample.service

import com.geektree0101.githubrepositoryexample.model.Repository
import com.google.gson.Gson
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.deferred
import okhttp3.*
import java.io.IOException
import java.lang.Exception

interface GithubServiceLogic {
    fun getRepositories(since: Int?): Promise<List<Repository>, Exception>
}

class GithubService: GithubServiceLogic {

    override fun getRepositories(since: Int?): Promise<List<Repository>, Exception> {

        val seal = deferred<List<Repository>, Exception>()

        NetworkService()
            .addHTTPURL(
                HttpUrl
                    .Builder()
                    .scheme("https")
                    .host("api.github.com")
                    .addPathSegment("repositories")
                    .addQueryParameter("since", since?.toString() ?: "0")
                    .build()
            )
            .onSuccess {
                try {
                    val jsonString = it.body?.string() ?: ""
                    val repos = Gson().fromJson(jsonString, Array<Repository>::class.java)
                    seal.resolve(repos.toList().subList(0, 4)) // FIXME: 50~200 items occur perfoermance drop issue -_-..
                } catch(e: Exception) {
                    seal.reject(e)
                }
            }
            .onError {
                seal.reject(it)
            }
            .execute()

        return seal.promise
    }
}