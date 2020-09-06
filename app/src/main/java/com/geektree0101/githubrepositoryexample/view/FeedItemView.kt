package com.geektree0101.githubrepositoryexample.view

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        title = repository.name ?: "unknown",
        desc = repository.description ?: "-",
        username = repository.owner?.login ?: "unknown",
        imageURL = repository.owner?.avatar_url
    )
}

@Composable
fun FeedItem(viewModel: FeedItemViewModel) {
    Column(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(1.0f)
    ) {
        FeedContentView(viewModel = viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        BottomLineView()
    }
}

@Composable
fun FeedContentView(viewModel: FeedItemViewModel) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        GithubInfoView(viewModel = viewModel)
        Spacer(modifier = Modifier.weight(1.0f))
        ProfileInfoView(viewModel = viewModel)
    }
}


@Composable
fun BottomLineView() {
    Box(
        modifier = Modifier
            .height(0.5.dp)
            .fillMaxWidth(1.0f)
            .background(color = Color.Gray, shape = CircleShape)
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun GithubInfoView(viewModel: FeedItemViewModel) {
    Column() {
        Text(
            text = viewModel.title,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = viewModel.desc,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            ),
            maxLines = 2
        )
    }
}

@Composable
fun ProfileInfoView(viewModel: FeedItemViewModel) {
    Row(verticalGravity = Alignment.CenterVertically) {
        CoilImage(
            data = viewModel.imageURL ?: "",
            modifier = Modifier
                .preferredSize(24.dp)
                .clip(CircleShape)
                .background(color = Color.LightGray, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = viewModel.username,
            style = TextStyle(
                fontSize = 12.sp,
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
