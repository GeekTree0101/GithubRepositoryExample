package com.geektree0101.githubrepositoryexample.scene.feed

import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.sp
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import com.geektree0101.githubrepositoryexample.view.FeedItem
import com.geektree0101.githubrepositoryexample.view.FeedItemViewModel

interface FeedActionLogic {

    fun reload()
    fun next()
}

@Model
interface FeedStateLogic {

    var items: Array<FeedItemViewModel>
}

@Composable
fun FeedView(action: FeedActionLogic?, state: FeedStateLogic?) {

    action?.reload()

    MaterialTheme {
        Surface(color = Color.White) {
            VerticalScroller {
                // TODO: feed view
                Column {
                    (state?.items ?: emptyArray()).forEach {
                        FeedItem(viewModel = it)
                    }
                }
            }
        }
    }
}