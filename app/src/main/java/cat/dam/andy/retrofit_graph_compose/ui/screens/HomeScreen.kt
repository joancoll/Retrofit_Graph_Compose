package cat.dam.andy.retrofit_graph_compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.dam.andy.retrofit_graph_compose.R
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.LegendLabel
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

@Composable
fun HomeScreen(
    myUiState: MyUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    when (myUiState) {
        is MyUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MyUiState.Success -> ResultScreen(
            myUiState.data,
            myUiState.totalEvolution,
            myUiState.totalFemaleEvolution,
            myUiState.totalMaleEvolution,
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        )

        is MyUiState.Error -> ErrorScreen(myUiState.errorMessage, modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(errorMessage: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(
            text = errorMessage ?: stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * ResultScreen displaying number of data retrieved.
 */
@Composable
fun ResultScreen(
    datasetNumber: Int,
    totalEvolution: List<Pair<String, Int>>,
    totalFemaleEvolution: List<Pair<String, Int>>,
    totalMaleEvolution: List<Pair<String, Int>>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    )
    {
        val myViewModel: MyViewModel = viewModel()
        Text(text = "CENS HABITANTS BANYOLES",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        if (myViewModel.isTextMode) {
            TextModeScreen(
                datasetNumber,
                totalEvolution,
                totalFemaleEvolution,
                totalMaleEvolution,
                modifier
            )
        } else {
            GraphicModeScreen(
                datasetNumber,
                totalEvolution,
                totalFemaleEvolution,
                totalMaleEvolution,
                modifier
            )
        }
    }
}

@Composable
fun GraphicModeScreen(
    datasetNumber: Int,
    totalEvolution: List<Pair<String, Int>>,
    totalFemaleEvolution: List<Pair<String, Int>>,
    totalMaleEvolution: List<Pair<String, Int>>,
    modifier: Modifier,
) {
    Box(modifier = modifier) {
        val pointsDataTotalEvolution: List<Point> = totalEvolution.map { (label, value) ->
            Point(
                label.toFloat(),
                value.toFloat()
            )
        }
        val pointsDataTotalFemaleEvolution: List<Point> =
            totalFemaleEvolution.map { (label, value) ->
                Point(
                    label.toFloat(),
                    value.toFloat()
                )
            }
        val pointsDataTotalMaleEvolution: List<Point> = totalMaleEvolution.map { (label, value) ->
            Point(
                label.toFloat(),
                value.toFloat()
            )
        }
        val maxRange = totalEvolution.maxOf { it.second }
        val roundedMaxRange = when {
            // Arrodonim el valor màxim a la desena/centena/... més propera segons el seu valor
            maxRange <= 1000 -> ((maxRange + 9) / 10) * 10
            maxRange <= 10000 -> ((maxRange + 99) / 100) * 100
            maxRange <= 100000 -> ((maxRange + 999) / 1000) * 1000
            maxRange <= 1000000 -> ((maxRange + 9999) / 10000) * 10000
            else -> ((maxRange + 99999) / 1000000) * 1000000
        }
        val targetSteps = 10
        val yStepSize = roundedMaxRange / targetSteps

        val valuesList = (0 until roundedMaxRange + yStepSize step yStepSize).toList()

        val xAxisData = AxisData.Builder()
            .axisStepSize(30.dp)
            .startDrawPadding(30.dp)
            .steps(pointsDataTotalEvolution.size - 1)
            .bottomPadding(20.dp)
            .startPadding(40.dp)
            .endPadding(40.dp)
            .axisLabelAngle(20f)
            .labelData { i -> totalEvolution[i].first }
            .build()

        val yAxisData = AxisData.Builder()
            .steps(valuesList.size - 1)
            .backgroundColor(Color.White)
            .labelAndAxisLinePadding(20.dp)
            .axisOffset(30.dp)
            .labelData { i -> valuesList[i].toString() }
            .build()

        fun Float.formatToSinglePrecision(): String {
            return "%.1f".format(this)
        }

        val lineChartTotalEvolutionData = LineChartData(
            linePlotData = LinePlotData(
                plotType = PlotType.Wave,
                lines = listOf(
                    Line(
                        dataPoints = pointsDataTotalFemaleEvolution,
                        LineStyle(color = Color.Yellow),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp(),
                    ),
                    Line(
                        dataPoints = pointsDataTotalMaleEvolution,
                        LineStyle(color = Color.Green),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp()
                    ),
//                    Line(
//                        dataPoints = pointsDataTotalEvolution,
//                        LineStyle(),
//                        IntersectionPoint(),
//                        SelectionHighlightPoint(),
//                        ShadowUnderLine(),
//                        SelectionHighlightPopUp()
//                    ),
                ),
            ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(),
            backgroundColor = Color.White
        )

        val legendsConfig = LegendsConfig(
            legendLabelList = listOf(
                LegendLabel(Color.Yellow, "Female"),
                LegendLabel(Color.Green, "Male"),
            ),
            textSize = 20.sp,
            colorBoxSize = 20.dp,
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ),
            gridColumnCount = 2,
            spaceBWLabelAndColorBox = 5.dp,
        )

        Column() {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartTotalEvolutionData
            )
            Legends(legendsConfig = legendsConfig)
        }
    }
}

@Composable
fun TextModeScreen(
    datasetNumber: Int,
    totalEvolution: List<Pair<String, Int>>,
    totalFemaleEvolution: List<Pair<String, Int>>,
    totalMaleEvolution: List<Pair<String, Int>>,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = "Número registres anuals: ${datasetNumber}",
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        }
        item {
            Text(
                text = "Evolució cens: ${totalEvolution}",
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        }
        item {
            Text(
                text = "Evolució nombre dones: ${totalFemaleEvolution}",
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        }
        item {
            Text(
                text = "Evolució nombre homes: ${totalMaleEvolution}",
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        }
    }
}
