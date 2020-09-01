package com.geektree0101.githubrepositoryexample.view

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.sp
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.surface.Card
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight

data class FeedItemViewModel(
    val title: String,
    val desc: String,
    val username: String
) {

}

@Composable
fun FeedItem(viewModel: FeedItemViewModel) {
    Padding(16.0.dp) {
        FlexRow {
            inflexible {
                Card(shape = RoundedCornerShape(5.0.dp), color = Color.Gray) {
                    Container(width = 80.0.dp, height = 80.0.dp) {
                        // TODO: fetching image
                    }
                }
                WidthSpacer(16.0.dp)
            }
            expanded(flex = 1f) {
                FeedItemContentView(viewModel)
            }
        }
    }
}


@Composable
private fun FeedItemContentView(viewModel: FeedItemViewModel) {
    Container(height = 80.dp, alignment = Alignment.TopLeft) {
        FlexColumn(mainAxisAlignment = MainAxisAlignment.SpaceBetween) {
            inflexible {
                Column {
                    Text(
                        text = viewModel.title,
                        style = TextStyle(
                            fontSize = 16.0.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    HeightSpacer(4.dp)
                    Text(
                        text = viewModel.desc,
                        style = TextStyle(
                            fontSize = 14.0.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray
                        )
                    )
                }
            }
            inflexible {
                Text(
                    text = viewModel.username,
                    style = TextStyle(
                        fontSize = 14.0.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.DarkGray
                    )
                )
            }
        }
    }
}
