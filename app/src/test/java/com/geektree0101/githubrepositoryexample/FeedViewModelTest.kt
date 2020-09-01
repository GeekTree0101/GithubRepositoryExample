package com.geektree0101.githubrepositoryexample

import com.geektree0101.githubrepositoryexample.model.Repository
import com.geektree0101.githubrepositoryexample.model.User
import com.geektree0101.githubrepositoryexample.scene.feed.FeedViewModel
import com.geektree0101.githubrepositoryexample.service.GithubServiceLogic
import nl.komponents.kovenant.Kovenant
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.testMode
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.Exception


class FeedViewModelTest {

    // MARK: - Test Double

    class GithubServiceSpy: GithubServiceLogic {

        var getRepositoriesCalled: Int = 0
        var getRepositoriesStub: Promise<Array<Repository>, Exception> = Promise.ofSuccess(emptyArray())
        override fun getRepositories(since: Int?): Promise<Array<Repository>, Exception> {
            this.getRepositoriesCalled += 1
            return this.getRepositoriesStub
        }
    }

    var sut: FeedViewModel = FeedViewModel()
    var service: GithubServiceSpy = GithubServiceSpy()

    @Before
    fun setupKovenant() {
        Kovenant.testMode { error ->
            Assert.fail(error.message)
        }
        this.sut = FeedViewModel()
        this.service = GithubServiceSpy()
        this.sut.service = this.service
    }

    // MARK: - Reload

    @Test
    fun test_reload() {
        // given
        this.service.getRepositoriesStub = Promise.ofSuccess(arrayOf(
            Repository(
                id = 100,
                owner = User(
                    login = "Geektree0101",
                    avatarURL = "https://dummy/image.jpg"
                ),
                fullName = "GithubRepositoryExample",
                description = "jetpack compose example",
                private = false,
                fork = false
            ),
            Repository(
                id = 200,
                owner = User(
                    login = "Geektree0101",
                    avatarURL = "https://dummy/image.jpg"
                ),
                fullName = "GithubRepositoryExample",
                description = "jetpack compose example",
                private = false,
                fork = false
            )
        ))

        // when
        this.sut.reload()


        // then: service
        Assert.assertEquals(service.getRepositoriesCalled, 1)

        // then: state
        Assert.assertEquals(this.sut.items.size, 2)
        Assert.assertEquals(this.sut.since, 2)
    }


    @Test
    fun test_reload_without_items() {
        // given
        this.service.getRepositoriesStub = Promise.ofSuccess(emptyArray())

        // when
        this.sut.reload()


        // then: service
        Assert.assertEquals(service.getRepositoriesCalled, 1)

        // then: state
        Assert.assertEquals(this.sut.items.size, 0)
        Assert.assertEquals(this.sut.since, null)
    }

    // MARK: - Next

    @Test
    fun test_next_with_since() {
        // given
        this.sut.since = 100

        this.service.getRepositoriesStub = Promise.ofSuccess(arrayOf(
            Repository(
                id = 100,
                owner = User(
                    login = "Geektree0101",
                    avatarURL = "https://dummy/image.jpg"
                ),
                fullName = "GithubRepositoryExample",
                description = "jetpack compose example",
                private = false,
                fork = false
            ),
            Repository(
                id = 200,
                owner = User(
                    login = "Geektree0101",
                    avatarURL = "https://dummy/image.jpg"
                ),
                fullName = "GithubRepositoryExample",
                description = "jetpack compose example",
                private = false,
                fork = false
            )
        ))

        // when
        this.sut.next()

        // then
        Assert.assertEquals(this.service.getRepositoriesCalled, 1)
        Assert.assertEquals(this.sut.since, 102)
    }

    @Test
    fun test_next_paging_ended() {
        // given
        this.sut.since = 100

        this.service.getRepositoriesStub = Promise.ofSuccess(emptyArray())

        // when
        this.sut.next()

        // then
        Assert.assertEquals(this.service.getRepositoriesCalled, 1)
        Assert.assertEquals(this.sut.since, null)
    }

    @Test
    fun test_next_cannot_paging_if_since_is_null() {
        // given
        this.sut.since = null

        this.service.getRepositoriesStub = Promise.ofSuccess(emptyArray())

        // when
        this.sut.next()

        // then
        Assert.assertEquals(this.service.getRepositoriesCalled, 0)
        Assert.assertEquals(this.sut.since, null)
    }
}