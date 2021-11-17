package com.jetpack.animatedlists

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.animatedlists.ui.theme.AnimatedListsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimatedListsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .background(MaterialTheme.colors.primary),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Animation Lists",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        AnimateLists()
                    }
                }
            }
        }
    }
}

@Composable
fun AnimateLists() {
    val sampleLists = sampleList
    val animations = listOf(
        "Fade",
        "Scale",
        "Slide",
        "Fade+Slide",
        "Slide Up",
        "RotateX"
    )
    Column {
        var animationIndex by remember { mutableStateOf(0) }
        VerticalGrid(
            columns = 3,
            modifier = Modifier
                .padding(vertical = 12.dp)
        ) {
            animations.forEachIndexed { index, title ->
                AnimateListChip(
                    selected = index == animationIndex,
                    text = title,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = {
                            animationIndex = index
                        })
                )
            }
        }
        LazyColumn {
            itemsIndexed(
                items = sampleLists,
                itemContent = { index, lists ->
                    AnimatedListItem(lists, animationIndex)
                }
            )
        }
    }
}

@Composable
fun AnimatedListItem(lists: SampleModel, animationIndex: Int) {
    val animatedModifier = when (animationIndex) {
        0 -> {
            val animatedProgress = remember { Animatable(initialValue = 0f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(600)
                )
            }
            Modifier
                .padding(8.dp)
                .alpha(animatedProgress.value)
        }
        1 -> {
            val animatedProgress = remember { Animatable(initialValue = 0.8f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(300, easing = LinearEasing)
                )
            }
            Modifier
                .padding(8.dp)
                .graphicsLayer(scaleY = animatedProgress.value, scaleX = animatedProgress.value)
        }
        2 -> {
            val animatedProgress = remember { Animatable(initialValue = 300f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                )
            }
            Modifier
                .padding(8.dp)
                .graphicsLayer(translationX = animatedProgress.value)
        }
        3 -> {
            val animatedProgress = remember { Animatable(initialValue = -300f) }
            val opacityProgress = remember { Animatable(initialValue = 0f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(300, easing = LinearEasing)
                )
                opacityProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(600)
                )
            }
            Modifier
                .padding(8.dp)
                .graphicsLayer(translationX = animatedProgress.value)
                .alpha(opacityProgress.value)
        }
        4 -> {
            val animatedProgress = remember { Animatable(initialValue = 300f) }
            val opacityProgress = remember { Animatable(initialValue = 0f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(300, easing = LinearEasing)
                )
                opacityProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(600)
                )
            }
            Modifier
                .padding(8.dp)
                .graphicsLayer(translationY = animatedProgress.value)
                .alpha(opacityProgress.value)
        }
        5 -> {
            val animatedProgress = remember { Animatable(initialValue = 0f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
            Modifier
                .padding(8.dp)
                .graphicsLayer(rotationX = animatedProgress.value)
        }
        else -> {
            val animatedProgress = remember { Animatable(initialValue = 0.8f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(300)
                )
            }
            Modifier
                .padding(8.dp)
                .alpha(animatedProgress.value)
        }
    }

    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = animatedModifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = lists.image),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(55.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            ) {
                Text(
                    text = lists.title,
                    style = typography.h6.copy(fontSize = 16.sp),
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = lists.desc,
                    style = typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }
            Icon(
               imageVector = Icons.Default.MoreVert,
               contentDescription = "Menu",
               tint = Color.LightGray,
               modifier = Modifier.padding(4.dp)
            )
        }
    }
}






















