package dev.irving.spacexlaunches.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.irving.spacexlaunches.SpaceXSdk
import dev.irving.spacexlaunches.cache.DatabaseDriverFactory
import dev.irving.spacexlaunches.entity.Links
import dev.irving.spacexlaunches.entity.Patch
import dev.irving.spacexlaunches.entity.RocketLaunch

class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel(SpaceXSdk(DatabaseDriverFactory(this)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceXLaunchesApp(viewModel)
        }
    }
}

// TODO 17: create Android UI
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpaceXLaunchesApp(
    viewModel: MainViewModel
) {
    val refreshing by viewModel.isRefreshing.collectAsState()
    val rocketLaunches by viewModel.items.collectAsState()
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = { viewModel.fetchData(true) }
    )

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SpaceXLaunchesView(
                rocketLaunches,
                refreshing,
                state
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpaceXLaunchesView(
    rocketLaunches: List<RocketLaunch>,
    refreshing: Boolean,
    state: PullRefreshState
) {
    Box(modifier = Modifier.pullRefresh(state)) {
        LazyColumn {
            items(rocketLaunches) { rocketLaunch ->
                LaunchItemView(
                    rocketLaunch = rocketLaunch,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun LaunchItemView(
    rocketLaunch: RocketLaunch,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = stringResource(id = R.string.launch_name_field, rocketLaunch.missionName))
            Text(
                text = stringResource(
                    id = when (rocketLaunch.launchSuccess) {
                        null -> R.string.no_data
                        true -> R.string.successful
                        else -> R.string.unsuccessful
                    }
                ),
                color = when (rocketLaunch.launchSuccess) {
                    true -> Successful
                    false -> Unsuccessful
                    else -> NoData
                }
            )
            Text(
                text = stringResource(
                    id = R.string.launch_year_field,
                    rocketLaunch.launchYear.toString()
                )
            )
            Text(
                text = stringResource(
                    id = R.string.launch_details_field,
                    rocketLaunch.details ?: ""
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LaunchItemPreview() {
    MyApplicationTheme {
        LaunchItemView(
            rocketLaunch = RocketLaunch(
                100, "Platzi Space", "2023-06-20T21:45:51Z", "Never stop learning", true, Links(
                    Patch("", ""), ""
                )
            )
        )
    }
}
