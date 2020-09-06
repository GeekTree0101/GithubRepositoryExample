package com.geektree0101.githubrepositoryexample.view

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import com.geektree0101.githubrepositoryexample.model.Repository
import dev.chrisbanes.accompanist.coil.CoilImage

data class FeedItemViewModel(
    val title: String,
    val desc: String,
    val username: String,
    val imageURL: String?
) {

    constructor(repository: Repository): this(
        title = repository.fullName ?: "unknown",
        desc = repository.description ?: "-",
        username = repository.owner?.login ?: "unknown",
        imageURL = repository.owner?.avatarURL
    ) {

    }
}

@Composable
fun FeedItem(viewModel: FeedItemViewModel) {
    Row(modifier = Modifier.padding(16.dp).heightIn(maxHeight = 100.dp).fillMaxWidth(1.0f)) {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.preferredSize(100.dp),
            backgroundColor = Color.LightGray
        ) {
            CoilImage(
                data = viewModel.imageURL ?: "",
                modifier = Modifier.preferredSize(100.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        FeedItemContentView(viewModel)
    }
}


@Composable
private fun FeedItemContentView(viewModel: FeedItemViewModel) {
    Column() {
        Column() {
            Text(
                text = viewModel.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = viewModel.desc,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                ),
                maxLines = 2
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = viewModel.username,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            ),
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun FeedItemPreview() {
    Surface(color = Color.White) {
        FeedItem(
            viewModel = FeedItemViewModel(
                title = "iGoSpy",
                desc = "Clean Swift spy generator built on Go",
                username = "Geektree0101",
                imageURL = "https://avatars3.githubusercontent.com/u/19504988?s=460&u=c653be124a5a2e72f496f49597a899014cc6ac5b&v=4"
            )
        )
    }
}
