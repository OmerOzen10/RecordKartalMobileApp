@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.kartalrecord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kartalrecord.ui.theme.KartalRecordTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    Color.Transparent.toArgb(),
                    Color.Transparent.toArgb()
                )
            )
            KartalRecordTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    newsApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun NewsCard(
    imagePainter: Painter = painterResource(id = R.drawable.news1),
    category: String = "FUTBOL",
    title: String = "Beşiktaş'ta flaş transfer iddiasi!",
) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(450.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = imagePainter,
                contentDescription = "Al Musrati",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.align(Alignment.BottomStart)) {

                Text(
                    text = category,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier

                        .padding(start = 15.dp, end = 15.dp),

                    )
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 15.dp, bottom = 13.dp, end = 15.dp),
                )
                Divider(
                    color = Color.White, // Set the color of the divider
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .height(0.2.dp) // Set the width of the divider to fill the parent
                )
                Text(
                    text = "DEVAMINI OKU",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier
                        .padding(start = 15.dp, bottom = 13.dp, end = 15.dp),
                )

            }

        }
    }
}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

val items = listOf(
    NavigationItem(
        title = "Ana Sayfa",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    NavigationItem(
        title = "Futbol",
        selectedIcon = Icons.Filled.AddCircle,
        unselectedIcon = Icons.Outlined.AddCircle
    ),
    NavigationItem(
        title = "Basketbol",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle
    ),
    NavigationItem(
        title = "Puan Durumu", selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List
    )
)

@Composable
fun newsList(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(viewModel.returnNews()) {
            NewsCard(painterResource(it.image), it.category, it.title)
        }
    }
}


@Composable
fun newsApp(
    viewModel: NewsViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        DismissibleNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet {
                    Spacer(modifier = Modifier.height(32.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            badge = {
                                item.badgeCount?.let {
                                    Text(text = item.badgeCount.toString())
                                }
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding),
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Image(
                                painterResource(R.drawable.kartalrecord),
                                contentDescription = "Icon",
                                modifier = Modifier.size(90.dp),
                                alignment = Alignment.CenterEnd
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu button",
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
//                     colors = TopAppBarDefaults.topAppBarColors(Color.Gray)
                    )

                }
            ) { values ->
                newsList(
                    modifier = Modifier
                        .padding(values),
                    viewModel
                )
            }
        }
    }
}
