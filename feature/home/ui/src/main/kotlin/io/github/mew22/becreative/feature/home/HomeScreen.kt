package io.github.mew22.becreative.feature.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.skydoves.cloudy.cloudy
import io.github.mew22.becreative.core.ui.component.ErrorScreen
import io.github.mew22.becreative.core.ui.component.ErrorScreenDefaultActions
import io.github.mew22.becreative.core.ui.component.LoadingScreen
import io.github.mew22.becreative.core.ui.designsystem.LocalThemeColors
import io.github.mew22.becreative.core.ui.designsystem.LocalThemeDimens
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@Composable
fun HomeScreen(
    state: HomeState,
    toGuessMission: (missionId: String, imageUrl: String) -> Unit,
    toAchieveMission: (missionId: String) -> Unit,
) {
    val status = state.status
    when (status) {
        is HomeState.Status.Loading -> {
            LoadingScreen()
        }

        is HomeState.Status.Error -> {
            ErrorScreen(
                actions = {
                    ErrorScreenDefaultActions(
                        onPrimaryActionClick = { },
                        onSecondaryActionClick = { },
                    )
                }
            )
        }

        is HomeState.Status.Success -> {
            HomeView(
                successState = status,
                toGuessMission = toGuessMission,
                toAchieveMission = toAchieveMission
            )
        }
    }
}

@Suppress("ModifierMissing")
@Composable
fun HomeView(
    successState: HomeState.Status.Success,
    toGuessMission: (missionId: String, imageUrl: String) -> Unit,
    toAchieveMission: (missionId: String) -> Unit,
) {
    val mission = successState.mission
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(LocalThemeDimens.current.standard100),
            verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
        ) {
            if (mission != null && mission.status == MissionUi.MissionStatus.NOT_STARTED) {
                ShowMissionCard(mission = mission, onClick = toAchieveMission)
            }
            ShowFeeds(
                feeds = successState.feeds,
                isFeedVisible = successState.isFeedVisible,
                onClickChallengeFeed = toGuessMission,
            )
        }
    }
}

@Suppress("UnnecessaryEventHandlerParameter")
@Composable
private fun ShowMissionCard(
    mission: MissionUi,
    onClick: (missionId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = { onClick(mission.id) },
        shape = RoundedCornerShape(LocalThemeDimens.current.standard100),
        colors = CardDefaults.cardColors()
            .copy(containerColor = LocalThemeColors.current.oranges.orange300Pastel)
    ) {
        Text(
            text = "Hurry up! Discover your daily mission now!",
            modifier = Modifier.padding(LocalThemeDimens.current.standard100)
        )
    }
}

@Composable
private fun ShowFeeds(
    feeds: ImmutableList<FeedUi>,
    isFeedVisible: Boolean,
    modifier: Modifier = Modifier,
    onClickChallengeFeed: (missionId: String, imageUrl: String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
    ) {
        itemsIndexed(feeds, key = { _, item -> item.id }) { _, feed ->
            val onClick: () -> Unit = if (feed is FeedUi.ChallengeFeed && feed.canClick) {
                { onClickChallengeFeed(feed.mission.id, feed.imageUri) }
            } else {
                {}
            }
            FeedCard(
                feed = feed,
                isFeedVisible = isFeedVisible,
                modifier = Modifier.clickable(
                    enabled = isFeedVisible && feed.canClick,
                    onClick = onClick
                )
            )
        }
    }
}

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
private fun FeedCard(
    feed: FeedUi,
    isFeedVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalThemeDimens.current.standard100),
            verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val format = LocalTime.Format {
                    byUnicodePattern("HH:mm:ss")
                }
                Column {
                    Text(text = feed.username, style = MaterialTheme.typography.titleMedium)
                    Text(text = feed.postedTime.time.format(format))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = feed.feedIcon)
                    if (feed is FeedUi.PhotoChainedFeed) {
                        Text(text = feed.chainPosition)
                    }
                }
            }

            val cloudyModifier = Modifier.cloudy(
                radius = LocalThemeDimens.current.standard500.value.toInt(),
                enabled = isFeedVisible.not()
            )

            when (feed) {
                is FeedUi.ChallengeFeed -> {
                    ShowImageFeed(imageUri = feed.imageUri, modifier = cloudyModifier)
                    if (feed.mission.showMissionInFeed) {
                        Text(text = feed.mission.title, modifier = cloudyModifier)
                    }
                }

                is FeedUi.PhotoChainedFeed -> {
                    ShowImageFeed(imageUri = feed.imageUri, modifier = cloudyModifier)
                }

                is FeedUi.GuessFeed -> {
                    Text(text = feed.description, modifier = cloudyModifier)
                }
            }
        }
    }
}

@Composable
private fun ShowImageFeed(imageUri: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUri,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .fillMaxWidth()
            .border(
                LocalThemeDimens.current.standard25,
                LocalThemeColors.current.neutrals.grey400ViewBorder,
                RoundedCornerShape(LocalThemeDimens.current.standard200)
            )
            .clip(RoundedCornerShape(LocalThemeDimens.current.standard200))
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeViewPreview() {
    HomeView(
        successState = HomeState.Status.Success(
            feeds = persistentListOf(),
            mission = null,
        ),
        toGuessMission = { _, _ -> },
        toAchieveMission = {},
    )
}
