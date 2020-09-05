package com.geektree0101.githubrepositoryexample.service

import com.geektree0101.githubrepositoryexample.model.Repository
import com.geektree0101.githubrepositoryexample.model.User
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.deferred
import java.lang.Exception

interface GithubServiceLogic {
    fun getRepositories(since: Int?): Promise<List<Repository>, Exception>
}

class GithubService: GithubServiceLogic {

    override fun getRepositories(since: Int?): Promise<List<Repository>, Exception> {

        // TODO: GET https://api.github.com/repositories?since=364
        val seal = deferred<List<Repository>, Exception>()

        seal.resolve(listOf(
            Repository(
                id = 100,
                owner = User(
                    login = "1",
                    avatarURL = ""
                ),
                fullName = "first",
                description = "desc",
                private = false,
                fork = false
            ),
            Repository(
                id = 100,
                owner = User(
                    login = "1",
                    avatarURL = ""
                ),
                fullName = "first",
                description = "desc",
                private = false,
                fork = false
            ),
            Repository(
                id = 100,
                owner = User(
                    login = "1",
                    avatarURL = ""
                ),
                fullName = "first",
                description = "desc",
                private = false,
                fork = false
            ),
            Repository(
                id = 100,
                owner = User(
                    login = "1",
                    avatarURL = ""
                ),
                fullName = "first",
                description = "desc",
                private = false,
                fork = false
            )
        ))

        return seal.promise
    }
}