package com.geektree0101.githubrepositoryexample.scene.feed

import androidx.lifecycle.MutableLiveData
import com.geektree0101.githubrepositoryexample.service.GithubServiceLogic
import com.geektree0101.githubrepositoryexample.view.FeedItemViewModel

interface FeedViewModelLogic: FeedStateLogic, FeedActionLogic {

    var service: GithubServiceLogic?
}

class FeedViewModel: FeedViewModelLogic {

    var since: Int? = null

    override var items: MutableLiveData<List<FeedItemViewModel>> = MutableLiveData()
    override var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    override var service: GithubServiceLogic? = null

    override fun reload() {

        this.isLoading.postValue(true)

        this.service?.getRepositories(since = null)?.success {

            this.isLoading.postValue(false)

            if (it.isNotEmpty()) {
                this.since = it.size
            }

            this.items.postValue(
                it.map {
                    FeedItemViewModel(repository = it)
                }.toList()
            )
        }?.fail {
            // TODO: error handling
        }
    }

    override fun next() {

        if (this.since == null) {
            return
        }

        this.isLoading.postValue(true)

        this.service?.getRepositories(since = this.since)?.success {

            this.isLoading.postValue(false)

            if (it.isNotEmpty()) {
                this.since = it.size + (this.since ?: 0)
            } else {
                this.since = null
            }

            var updateItems = this.items.value ?: emptyList()
            updateItems += it.map {
                FeedItemViewModel(repository = it)
            }.toList()

            this.items.postValue(updateItems)
        }?.fail {
            // TODO: error handling
        }
    }
}