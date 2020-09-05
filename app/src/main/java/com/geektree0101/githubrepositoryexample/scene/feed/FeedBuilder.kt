package com.geektree0101.githubrepositoryexample.scene.feed

import androidx.compose.runtime.Composable
import com.geektree0101.githubrepositoryexample.service.GithubServiceLogic

class FeedBuilder {

    var repoService: GithubServiceLogic? = null
    var viewModel: FeedViewModelLogic? = null

    fun addService(service: GithubServiceLogic): FeedBuilder {
        this.repoService = service
        return this
    }

    fun addViewModel(viewModel: FeedViewModelLogic): FeedBuilder {
        this.viewModel = viewModel
        return this
    }

    @Composable
    fun build(): Unit {
        this.viewModel?.service = this.repoService
        return FeedView(action = this.viewModel, state = this.viewModel)
    }
}