package com.example.recipesapp.ui.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipesapp.R
import com.example.recipesapp.ui.theme.BottomCardShape
import com.example.recipesapp.ui.theme.Poppins
import com.example.recipesapp.ui.theme.RecipesAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    pages: List<OnBoardingPage>,
    onFinishOnBoarding: () -> Unit
) {

    val pagerState = rememberPagerState(
        pageCount = pages.size,
        initialOffscreenLimit = 2
    )

    OnBoardingPager(
        modifier = modifier
            .fillMaxWidth(),
        pagerState = pagerState,
        pages = pages
    ) {
        onFinishOnBoarding()
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pages: List<OnBoardingPage>,
    onFinishOnBoarding: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val currentPage = pages[pagerState.currentPage]

    Box(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(state = pagerState) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(pages[page].backgroundColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Image(
                        painter = painterResource(id = pages[page].image),
                        contentDescription = pages[page].title,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                }
            }

        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                backgroundColor = Color.White,
                elevation = 0.dp,
                shape = BottomCardShape.large
            ) {
                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PagerIndicator(pages = pages, currentPage = pagerState.currentPage)
                        Text(
                            text = currentPage.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, end = 30.dp),
                            color = currentPage.mainColor,
                            fontFamily = Poppins,
                            textAlign = TextAlign.Right,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = currentPage.desc,
                            modifier = Modifier.padding(top = 20.dp, start = 30.dp, end = 30.dp),
                            color = Color.Gray.copy(alpha = 0.6f),
                            fontFamily = Poppins,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.ExtraLight
                        )

                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {


                            if (pagerState.currentPage != pages.lastIndex) {
                                TextButton(onClick = {
                                    onFinishOnBoarding()
                                }) {
                                    Text(
                                        text = stringResource(R.string.skip_now),
                                        color = Color(0xFF292D32),
                                        fontFamily = FontFamily.SansSerif,
                                        textAlign = TextAlign.Right,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }

                                OutlinedButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.scrollToPage(
                                                pagerState.currentPage + 1,
                                                pageOffset = 0f
                                            )
                                        }
                                    },
                                    elevation = ButtonDefaults.elevation(
                                        defaultElevation = 3.dp
                                    ),
                                    border = BorderStroke(
                                        18.dp,
                                        currentPage.mainColor
                                    ),
                                    shape = CircleShape,
                                    //or RoundedCornerShape(50)
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor
                                        = currentPage.mainColor
                                    ),
                                    modifier = Modifier.size(65.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right_arrow),
                                        contentDescription = "next button",
                                        tint = currentPage.mainColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                Button(
                                    onClick = {
                                        onFinishOnBoarding()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = pages[pagerState.currentPage].mainColor
                                    ),
                                    contentPadding = PaddingValues(vertical = 12.dp),
                                    elevation = ButtonDefaults.elevation(
                                        defaultElevation = 5.dp
                                    ),
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        text = stringResource(R.string.get_started),
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun PagerIndicator(currentPage: Int, pages: List<OnBoardingPage>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        repeat(pages.size) {
            Indicator(isSelected = it == currentPage, color = pages[it].mainColor)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean, color: Color) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 40.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) color else Color.Gray.copy(alpha = 0.5f)
            )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultOnBoardingScreenPreview() {
    RecipesAppTheme {
        //   OnBoardingScreen(pages = onBoardingDataList)
    }
}