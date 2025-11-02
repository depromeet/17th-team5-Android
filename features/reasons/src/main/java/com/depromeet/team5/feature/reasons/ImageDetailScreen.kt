package com.depromeet.team5.feature.reasons

import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.ui.ZoomableContainer
import kotlinx.coroutines.launch

@Composable
fun ImageDetailRoute(
    principleIndex: Int,
    imageIndex: Int,
    onClickBack: () -> Unit,
    viewModel: ReasonViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val principleTemplate by viewModel.principleTemplate.collectAsStateWithLifecycle()
    val images = principleTemplate.principles[principleIndex].images
    val pagerState = rememberPagerState(initialPage = imageIndex) { images.size }
    val coroutineScope = rememberCoroutineScope()

    ImageDetailScreen(
        images = images,
        pagerState = pagerState,
        onPageChanged = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(it)
            }
        },
        onClickBack = onClickBack,
        modifier = modifier,
    )
}

@Composable
private fun ImageDetailScreen(
    images: List<String>,
    pagerState: PagerState,
    onPageChanged: (Int) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFullScreen by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            isFullScreen = isFullScreen.not()
                        }
                    )
                },
        ) { page ->
            ZoomableContainer(
                modifier = Modifier
                    .fillMaxSize(),
                onSwipe = { onPageChanged(pagerState.currentPage + it) }
            ) { modifier ->
                AsyncImage(
                    model = images[page],
                    modifier = modifier,
                    contentDescription = "Zoomable image",
                    contentScale = ContentScale.Fit,
                )
            }
        }
        FullScreenController(
            isFullScreen = isFullScreen,
            topOverlays = {
                TopOverlay(
                    text = "${pagerState.currentPage + 1}/${images.size}",
                    onClickBack = onClickBack,
                )
            },
        )
    }
}

@Composable
private fun TopOverlay(
    text: String,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onClickBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = text,
            color = HedgeColor.Text.Primary,
            style = HedgeTypography.Body3.SemiBold
        )
    }
}

@Composable
fun BoxScope.FullScreenController(
    isFullScreen: Boolean,
    topOverlays: @Composable () -> Unit,
) {
    val statusBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    val activity = LocalActivity.current ?: error("no activity")

    SideEffect {
        if (isFullScreen) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                activity.window.insetsController?.run {
                    hide(WindowInsetsCompat.Type.systemBars())
                    hide(WindowInsetsCompat.Type.navigationBars())
                }
            } else {
                ViewCompat.getWindowInsetsController(activity.window.decorView)?.run {
                    hide(WindowInsetsCompat.Type.systemBars())
                    hide(WindowInsetsCompat.Type.navigationBars())
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                activity.window.insetsController?.run {
                    show(WindowInsetsCompat.Type.systemBars())
                    show(WindowInsetsCompat.Type.navigationBars())
                }
            } else {
                ViewCompat.getWindowInsetsController(activity.window.decorView)?.run {
                    show(WindowInsetsCompat.Type.systemBars())
                    show(WindowInsetsCompat.Type.navigationBars())
                }
            }
        }
    }

    AnimatedVisibility(
        visible = isFullScreen.not(),
        enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }),
        modifier = Modifier
            .align(Alignment.TopStart)
            .background(color = HedgeColor.Neutral.BackgroundDefault.copy(alpha = 0.6f))
            .padding(top = statusBarHeight)
    ) {
        topOverlays()
    }
}