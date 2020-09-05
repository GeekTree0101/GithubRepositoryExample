package com.geektree0101.githubrepositoryexample.scene.feed

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.geektree0101.githubrepositoryexample.service.GithubService
import com.geektree0101.githubrepositoryexample.view.FeedItem
import com.geektree0101.githubrepositoryexample.view.FeedItemViewModel

interface FeedActionLogic {

    fun reload()
    fun next()
}

interface FeedStateLogic {

    var items: List<FeedItemViewModel>
}

@Composable
fun FeedView(action: FeedActionLogic?, state: FeedStateLogic?) {
    action?.reload()

    MaterialTheme {
        Surface(color = Color.White) {
            Column() {
                LazyColumnFor(
                    items = state?.items ?: emptyList()
                ) {
                    FeedItem(viewModel = it)
                }
                TextButton(
                    onClick = {
                    action?.next()
                }) {
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
        }
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