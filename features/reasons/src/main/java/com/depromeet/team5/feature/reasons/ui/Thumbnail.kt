package com.depromeet.team5.feature.reasons.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.Article
import com.depromeet.team5.feature.reasons.R

@Composable
fun ImageThumbnailContainer(
    images: List<Uri>,
    modifier: Modifier = Modifier,
    onClickDeleteImage: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = if (images.isNotEmpty()) 16.dp else 0.dp,
                bottom = if (images.isNotEmpty()) 24.dp else 0.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        images.forEachIndexed { idx, uri ->
            ImageThumbnail(
                uri = uri,
                onClickDeleteImage = { onClickDeleteImage(idx) }
            )
        }
    }
}

@Composable
fun LinkThumbnailContainer(
    articles: List<Article>,
    onClickDeleteLink: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = if (articles.isNotEmpty()) 16.dp else 0.dp,
                bottom = if (articles.isNotEmpty()) 24.dp else 0.dp
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        articles.forEachIndexed { idx, article ->
            LinkThumbnail(
                modifier = Modifier,
                article = article,
                onClickDeleteLink = { onClickDeleteLink(idx) }
            )
        }
    }
}

@Composable
private fun ImageThumbnail(
    uri: Uri,
    modifier: Modifier = Modifier,
    onClickDeleteImage: () -> Unit,
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = HedgeColor.Neutral.BackgroundSecondary)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context)
                    .data(uri)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .aspectRatio(1f),
        )
        Icon(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .clickable(
                    onClick = onClickDeleteImage,
                    interactionSource = remember { MutableInteractionSource() },
                ),
            painter = painterResource(R.drawable.ic_close_fill),
            contentDescription = "close",
            tint = Color.Unspecified,
        )
    }
}

@Composable
private fun LinkThumbnail(
    article: Article,
    onClickDeleteLink: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(width = (1.2).dp, color = HedgeColor.Neutral.BackgroundSecondary, shape = RoundedCornerShape(16.dp)),
    ) {
        article.thumbnail?.let {
            AsyncImage(
                model = article.thumbnail,
                contentDescription = article.title,
                modifier = Modifier
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        } ?: Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = HedgeColor.Neutral.BackgroundSecondary),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = HedgeIcon.Link,
                contentDescription = "link",
                tint = HedgeColor.Text.Assistive,
            )
        }

        Spacer(Modifier.size(16.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = article.title ?: "",
                style = HedgeTypography.Label2.SemiBold,
                color = HedgeColor.Text.Primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.size(6.dp))
            Text(
                text = article.source ?: "",
                style = HedgeTypography.Caption1.Medium,
                color = HedgeColor.Text.Alternative,
            )
        }
        Icon(
            modifier = Modifier
                .padding(top = 5.dp, end = 4.dp)
                .clip(CircleShape)
                .clickable(
                    onClick = onClickDeleteLink,
                    interactionSource = remember { MutableInteractionSource() },
                ),
            imageVector = HedgeIcon.CloseFill,
            contentDescription = "close",
            tint = HedgeColor.Text.Assistive,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LinkThumbnailPreview() {
    LinkThumbnail(
        article = Article(
            originUrl = "abc",
            title = "titledaifojdioafj\ndiojafidjiaog\nadijfiajdfioa\nadijojfiaod",
            thumbnail = null,
            source = "sourcefdajifdjoiajiofd\n"
        ),
        onClickDeleteLink = {}
    )
}