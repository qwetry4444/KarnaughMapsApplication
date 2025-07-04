import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.karnaughmapsapplication.core.domain.KarnaughMapLogic.KarnaughMapMinimizer
import com.example.karnaughmapsapplication.core.domain.parsing.LogicalFunction
import com.example.karnaughmapsapplication.features.FunctionInput.presentation.KarnaughCell
import com.example.karnaughmapsapplication.features.KarnaughMap.presentation.KarnaughMapUiState
import com.example.karnaughmapsapplication.features.KarnaughMap.presentation.KarnaughMapViewModel
import com.example.karnaughmapsapplication.features.KarnaughMap.presentation.KarnaughMapViewModelFactory

@Composable
fun KarnaughMapPage(
    logicalFunction: LogicalFunction,
    navController: NavHostController
) {
    val pageBackground = Color(0xFFF2F2F2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBackground)
    ) {
        MapTopAppBar(navController)

        Spacer(modifier = Modifier.height(16.dp))

        val karnaughMapViewModelFactory = KarnaughMapViewModelFactory(logicalFunction)
        val viewModel: KarnaughMapViewModel = viewModel(factory = karnaughMapViewModelFactory)
        val uiState: KarnaughMapUiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OriginalFunction(
                logicalExpression = uiState.logicalFunction.expression,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(12.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFFFFF))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Таблица Карно",
                        color = Color(0xFF212121),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    KarnaughMapTable(
                        data = uiState.karnaughMapTable.table,
                        colTitle = uiState.karnaughMapTable.colsTitle,
                        rowTitle = uiState.karnaughMapTable.rowsTitle,
                        groups = uiState.karnaughMapMinimizer.groups
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            FunctionMinimization(
                minimizedLogicalFunction = uiState.minimizedLogicalFunction,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopAppBar(navController: NavHostController){
    TopAppBar(
        title = {
            Text("Карта Карно")
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}



@Composable
fun OriginalFunction(
    logicalExpression: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = Color(0xFFFFFFFF)
    val shape = RoundedCornerShape(12.dp)
    val elevationDp: Dp = 6.dp

    val titleColor = Color(0xFF212121)
    val titleTextSize = 18.sp
    val titleFontWeight = FontWeight.Bold

    val codeBlockBackgroundColor = Color(0xFFF0F0F0)
    val codeTextColor = Color(0xFF000000)
    val codeTextSize = 16.sp
    val codeFontFamily = FontFamily.Monospace

    Column(
        modifier = modifier
            .shadow(elevationDp, shape = shape, clip = false)
            .clip(shape)
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(
            text = "Исходная функция",
            color = titleColor,
            fontSize = titleTextSize,
            fontWeight = titleFontWeight
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(6.dp), clip = false)
                .clip(RoundedCornerShape(6.dp))
                .background(codeBlockBackgroundColor)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(
                text = logicalExpression,
                color = codeTextColor,
                fontSize = codeTextSize,
                fontFamily = codeFontFamily
            )
        }
    }
}

@Composable
fun KarnaughMapTable(
    data: List<List<String>>,
    colTitle: String,
    rowTitle: String,
    groups: List<KarnaughMapMinimizer.Group> = listOf()
) {
    val cellSize = 40.dp
    val rowCount = data.size
    val colCount = data.firstOrNull()?.size ?: 0

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HeaderSection(cellSize, colCount, colTitle, data.first())
        RowHeadersAndData(cellSize, rowCount, colCount, data, rowTitle, groups)
    }
}

@Composable
private fun HeaderSection(cellSize: Dp, colCount: Int, colTitle: String, columnHeaders: List<String>) {
    Row {
        FBox(cellSize)
        Column(modifier = Modifier.border(2.dp, Color.Black).background(Color.LightGray)) {
            ColumnTitleBox(cellSize, colCount, colTitle)
            ColumnHeadersRow(cellSize, colCount, columnHeaders)
        }
    }
}

@Composable
private fun FBox(cellSize: Dp) {
    Box(
        modifier = Modifier
            .size(cellSize * 2)
            .border(2.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text("F", fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ColumnTitleBox(cellSize: Dp, colCount: Int, colTitle: String) {
    Box(
        modifier = Modifier
            .width(cellSize * (colCount - 1))
            .height(cellSize)
            .border(2.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(colTitle, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ColumnHeadersRow(cellSize: Dp, colCount: Int, columnHeaders: List<String>) {
    Row {
        for (col in 1 until colCount) {
            Box(
                modifier = Modifier
                    .size(cellSize)
                    .border(2.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(columnHeaders[col], fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun RowHeadersAndData(
    cellSize: Dp,
    rowCount: Int,
    colCount: Int,
    data: List<List<String>>,
    rowTitle: String,
    groups: List<KarnaughMapMinimizer.Group>
) {
    Row {
        RowHeaders(cellSize, rowCount, data, rowTitle)
        Column {
            for (i in 1 until rowCount) {
                Row {
                    for (j in 1 until colCount) {
                        KarnaughCell(
                            cellText = data[i][j],
                            row = i - 1,
                            col = j - 1,
                            groups = groups,
                            cellSize = cellSize
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RowHeaders(
    cellSize: Dp,
    rowCount: Int,
    data: List<List<String>>,
    rowTitle: String
) {
    Row(modifier = Modifier.border(2.dp, Color.Black).background(Color.LightGray)) {
        Box(
            modifier = Modifier
                .width(cellSize)
                .height(cellSize * (rowCount - 1))
                .border(1.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rowTitle,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Column {
            repeat(rowCount - 1) {
                Box(
                    modifier = Modifier
                        .size(cellSize)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(data[it + 1][0], fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun FunctionMinimization(
    minimizedLogicalFunction: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = Color(0xFFFFFFFF)
    val shape = RoundedCornerShape(12.dp)
    val elevationDp: Dp = 6.dp

    val titleColor = Color(0xFF212121)
    val titleTextSize = 18.sp
    val titleFontWeight = FontWeight.Bold

    val codeBlockBackgroundColor = Color(0xFFF0F0F0)
    val codeTextColor = Color(0xFF000000)
    val codeTextSize = 16.sp
    val codeFontFamily = FontFamily.Monospace

    Column(
        modifier = modifier
            .shadow(elevationDp, shape = shape, clip = false)
            .clip(shape)
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(
            text = "Минимизированная функция",
            color = titleColor,
            fontSize = titleTextSize,
            fontWeight = titleFontWeight
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, shape = RoundedCornerShape(6.dp), clip = false)
                .clip(RoundedCornerShape(6.dp))
                .background(codeBlockBackgroundColor)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Text(
                text = minimizedLogicalFunction,
                color = codeTextColor,
                fontSize = codeTextSize,
                fontFamily = codeFontFamily
            )
        }
    }
}
