package cat.dam.andy.retrofit_graph_compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.dam.andy.retrofit_graph_compose.ui.screens.HomeScreen
import cat.dam.andy.retrofit_graph_compose.viewmodel.MyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val myViewModel: MyViewModel = viewModel()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MyTopAppBar(scrollBehavior, myViewModel, Modifier.fillMaxWidth()) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                myUiState = myViewModel.myUiState,
                contentPadding = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: MyViewModel,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {},
        modifier = modifier,
        actions = {
            // Add a Checkbox to switch between text mode and graphic mode
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Mode Text")
                Checkbox(
                    checked = viewModel.isTextMode,
                    onCheckedChange = { isChecked ->
                        viewModel.setIsTextMode(isChecked)
                    }
                )
            }
        }
    )
}

