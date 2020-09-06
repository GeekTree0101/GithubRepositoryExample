package com.geektree0101.githubrepositoryexample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.geektree0101.githubrepositoryexample.model.Repository
import com.geektree0101.githubrepositoryexample.model.User
import com.geektree0101.githubrepositoryexample.scene.feed.FeedViewModel
import com.geektree0101.githubrepositoryexample.service.GithubServiceLogic
import nl.komponents.kovenant.Kovenant
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.testMode
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class FeedViewModelTest {

    // MARK: - Test Double

    class GithubServiceSpy: GithubServiceLogic {

        var getRepositoriesCalled: Int = 0
        var getRepositoriesStub: Promise<List<Repository>, Exception> = Promise.ofSuccess(emptyList())
        override fun getRepositories(since: Int?): Promise<List<Repository>, Exception> {
            this.getRepositoriesCalled += 1
            return this.getRepositoriesStub
        }
    }

    var sut: FeedViewModel = FeedViewModel()
    var service: GithubServiceSpy = GithubServiceSpy()


    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun beforeEach() {
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
        this.service.getRepositoriesStub = Promise.ofSuccess(listOf(
            Repository(
                id = 100,
                owner = User(
                    login = "Geektree0101",
                    avatar_url = "https://dummy/image.jpg"
                ),
                name = "GithubRepositoryExample",
                description = "jetpack compose example",
                private = false,
                fork = false
            ),
            Repository(
                id = 200,
                owner = User(
                    login = "Geektree0101",
                    avatar_url = "https://dummy/image.jpg"
                ),
                name = "GithubRepositoryExample",
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

        Assert.assertEquals(this.sut.items.getOrAwaitValue().size, 2)
        Assert.assertEquals(this.sut.since, 2)
    }


    @Test
    fun test_reload_without_items() {
        // given
        this.service.getRepositoriesStub = Promise.ofSuccess(emptyList())

        // when
        this.sut.reload()

        // then: service
        Assert.assertEquals(service.getRepositoriesCalled, 1)

        // then: state
        Assert.assertEquals(this.sut.items.getOrAwaitValue().size, 0)
        Assert.assertEquals(this.sut.since, null)
    }

    // MARK: - Next

    @Test
    fun test_next_with_since() {
        // given
        this.sut.since = 100

        this.service.getRepositoriesStub = Promise.ofSuccess(listOf(
            Repository(
                id = 100,
                owner = User(
                    login = "Geektree0101",
                    avatar_url = "https://dummy/image.jpg"
                ),
                name = "GithubRepositoryExample",
                description = "jetpack compose example",
                private = false,
                fork = false
            ),
            Repository(
                id = 200,
                owner = User(
                    login = "Geektree0101",
                    avatar_url = "https://dummy/image.jpg"
                ),
                name = "GithubRepositoryExample",
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

        this.service.getRepositoriesStub = Promise.ofSuccess(emptyList())

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

        this.service.getRepositoriesStub = Promise.ofSuccess(emptyList())

        // when
        this.sut.next()

        // then
        Assert.assertEquals(this.service.getRepositoriesCalled, 0)
        Assert.assertEquals(this.sut.since, null)
    }
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}