import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun FunctionInputPage() {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp))
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Количество переменных")
            Spacer(modifier = Modifier.width(12.dp))
            TextField(value = "", onValueChange = {}, modifier = Modifier.size(40.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()
        ) {
            items(getTableItemsList(3)) { item ->
                Button(onClick = {})
                {
                    Text(text = item.value)
                }
            }
        }
    }
}

data class TableItem(
    val value: String
)

fun getTableItemsList(variablesCount: Int): List<TableItem>{
    return listOf(
        TableItem("v"), TableItem("∧"), TableItem("¬"),
        TableItem("⊕"), TableItem("→"), TableItem("≡"),
        TableItem("0"), TableItem("1"), TableItem("("),
        TableItem(")")
    ) +
    (1..variablesCount).map{ item -> TableItem("x$item") }
}