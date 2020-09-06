package com.geektree0101.githubrepositoryexample.scene.feed

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.MutableLiveData

interface FeedActionLogic {

    fun reload()
    fun next()
}

interface FeedStateLogic {

    var items: MutableLiveData<List<FeedItemViewModel>>
    var isLoading: MutableLiveData<Boolean>
}

@Composable
fun FeedView(action: FeedActionLogic?, state: FeedStateLogic?) {

    val items = state?.items?.observeAsState()
    val isLoading = state?.isLoading?.observeAsState()

    action?.reload()

    MaterialTheme {
        Surface(color = Color.White) {
            Stack() {
                LazyColumnForIndexed(items = items?.value ?: emptyList()) { index, item ->
                    FeedItem(viewModel = item)
                    if (index == (items?.value?.size ?: 0) - 1) {
                        PagerIndicator(isLoading = isLoading?.value ?: false, onClick = {
                            action?.next()
                        })
                    }
                }
                if (items?.value?.size ?: 0 == 0) {
                    LoadingIndicaor(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        size = 50.dp
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingIndicaor(modifier: Modifier, size: Dp) {
    Column(
        modifier = modifier,
        horizontalGravity = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
        )
    }
}

@Composable
fun PagerIndicator(isLoading: Boolean, onClick: () -> Unit) {
    if (isLoading) {
        LoadingIndicaor(
            modifier = Modifier.fillMaxWidth(1.0f).height(40.dp),
            size = 24.dp
        )
    } else {
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
}

@Preview
@Composable
fun FeedViewPreview() {
    FeedBuilder()
        .addService(GithubService())
        .addViewModel(FeedViewModel())
        .build()
}