package com.example.testidea

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.testidea.ui.components.ProductCard
import com.example.testidea.ui.components.ProductsBottomBar
import com.example.testidea.ui.components.ProductsTopBar
import com.example.testidea.ui.components.SearchField
import com.example.testidea.ui.theme.TestIdeaTheme
import com.example.testidea.ui.view_models.ProductViewModel

@Composable
fun TestIdeaApp(
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier
) {
    val products by viewModel.productsStateFlow.collectAsState()
    val searchResults by viewModel.searchResultsStateFlow.collectAsState()
    var isInSearch by rememberSaveable {
        mutableStateOf(false)
    }

    TestIdeaTheme {
        Scaffold(
            topBar = {
                ProductsTopBar()
            },
            bottomBar = {
                ProductsBottomBar()
            },
        ) { innerPadding ->
            Surface(
                tonalElevation = 0.2.dp,
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                var searchText by rememberSaveable { mutableStateOf("") }
                val listState = rememberLazyListState()
                val configuration = LocalConfiguration.current
                val screenHeight = configuration.screenHeightDp

                val elevation = remember {
                    derivedStateOf {
                        val lastVisibleItemInfo =
                            listState.layoutInfo.visibleItemsInfo.lastOrNull()
                        if (lastVisibleItemInfo != null) {
                            val distanceFromBottom =
                                listState.layoutInfo.viewportEndOffset - (lastVisibleItemInfo.offset + lastVisibleItemInfo.size)
                            if (distanceFromBottom < screenHeight) {
                                (4 * (1 - distanceFromBottom / screenHeight)).dp
                            } else {
                                0.dp
                            }
                        } else {
                            0.dp
                        }
                    }
                }

                LaunchedEffect(listState) {
                    snapshotFlow { listState.firstVisibleItemIndex }
                        .collect {
                            // This will trigger recomposition when the scroll state changes
                        }
                }

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    state = listState,
                ) {
                    item {
                        Box(
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 16.dp
                            )
                        ) {
                            SearchField(
                                value = searchText,
                                onValueChange = {
                                    searchText = it
                                    viewModel.searchProducts(searchText)
                                    isInSearch = true
                                },
                            )
                        }

                    }
                    items(
                        if (!isInSearch) products else searchResults,
                        key = { it.id }
                    ) { product ->
                        Box(
                            modifier = Modifier.padding(
                                top = 4.dp,
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp
                            )
                        ) {
                            ProductCard(
                                product = product,
                                elevation = elevation.value
                            )
                        }
                    }
                }
            }
        }
    }
}