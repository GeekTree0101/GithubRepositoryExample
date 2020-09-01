package com.geektree0101.githubrepositoryexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.ui.core.setContent
import com.geektree0101.githubrepositoryexample.scene.feed.FeedBuilder
import com.geektree0101.githubrepositoryexample.scene.feed.FeedViewModel
import com.geektree0101.githubrepositoryexample.service.GithubService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeedBuilder()
                .addService(GithubService())
                .addViewModel(FeedViewModel())
                .build()
        }
    }
}
