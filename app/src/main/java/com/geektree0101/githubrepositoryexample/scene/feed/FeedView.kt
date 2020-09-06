package com.geektree0101.githubrepositoryexample.scene.feed

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.State
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.ui.tooling.preview.Preview
import com.geektree0101.githubrepositoryexample.service.GithubService
import com.geektree0101.githubrepositoryexample.view.FeedItem
import com.geektree0101.githubrepositoryexample.view.FeedItemViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData

interface FeedActionLogic {

    fun reload()
    fun next()
}

interface FeedStateLogic {

    var items: MutableLiveData<List<FeedItemViewModel>>
}

@Composable
fun FeedView(action: FeedActionLogic?, state: FeedStateLogic?) {

    val items = state?.items?.observeAsState()

    action?.reload()

    MaterialTheme {
        Surface(color = Color.White) {
            Column() {
                LazyColumnForIndexed(items = items?.value ?: emptyList()) { index, item ->
                    FeedItem(viewModel = item)
                    if (index == (items?.value?.size ?: 0) - 1) {
                        PagerIndicator(onClick = {
                            action?.next()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(onClick: () -> Unit) {
    TextButton(
        onClick = onClick) {
        Text(
            text = "Load more",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(1.0f)
                .padding(8.dp),
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun FeedViewPreview() {
    FeedBuilder()
        .addService(GithubService())
        .addViewModel(FeedViewModel())
        .build()
}