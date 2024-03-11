package cat.dam.andy.retrofit_graph_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cat.dam.andy.retrofit_graph_compose.ui.MyApp
import cat.dam.andy.retrofit_graph_compose.ui.theme.Retrofit_Graph_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Retrofit_Graph_ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MyApp()
                }
            }
        }
    }
}
