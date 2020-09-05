package com.geektree0101.githubrepositoryexample.scene.feed

import com.geektree0101.githubrepositoryexample.service.GithubServiceLogic
import com.geektree0101.githubrepositoryexample.view.FeedItemViewModel

interface FeedViewModelLogic: FeedStateLogic, FeedActionLogic {

    var service: GithubServiceLogic?
}

class FeedViewModel: FeedViewModelLogic {

    var since: Int? = null

    override var items: List<FeedItemViewModel> = emptyList()

    override var service: GithubServiceLogic? = null

    override fun reload() {

        this.service?.getRepositories(since = null)?.success {
            if (it.isNotEmpty()) {
                this.since = it.size
            }

            this.items = it.map {
                FeedItemViewModel(
                    title = it.fullName ?: "unknown",
                    desc = it.description ?: "-",
                    username = it.owner?.login ?: "unknown",
                    imageURL = null
                )
            }.toList()
        }?.fail {
            // TODO: error handling
        }
    }

    override fun next() {

        if (this.since == null) {
            return
        }

        this.service?.getRepositories(since = this.since)?.success {

            if (it.isNotEmpty()) {
                this.since = it.size + (this.since ?: 0)
            } else {
                this.since = null
            }

            this.items += it.map {
                FeedItemViewModel(
                    title = it.fullName ?: "unknown",
                    desc = it.description ?: "-",
                    username = it.owner?.login ?: "unknown",
                    imageURL = null
                )
            }.toList()
        }?.fail {
            // TODO: error handling
        }
    }
}