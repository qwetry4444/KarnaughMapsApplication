import android.widget.ImageButton
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.karnaughmapsapplication.presentation.FunctionInputUiState


@Preview(showBackground = true)
@Composable
fun FunctionInputPage() {
    val viewModel: FunctionInputViewModel = viewModel()
    val state: FunctionInputUiState by viewModel.state.collectAsState()


    Column(modifier = Modifier.fillMaxSize().padding(42.dp).padding(top = 20.dp))
    {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Количество переменных")
            Spacer(modifier = Modifier.width(12.dp))
            TextField(
                value = TextFieldValue(text = state.currentVariableCount.toString(), selection = TextRange(1)),
                onValueChange = { viewModel.processAction(FunctionInputActions.SetVariableCount(it.text)) },
                modifier = Modifier.width(45.dp),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = state.currentFunctionInput,
                onValueChange = { },
                modifier = Modifier
            )

            IconButton(
                onClick = {viewModel.processAction(FunctionInputActions.PressClearButton)},
                modifier = Modifier.size(40.dp)
            )
            {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "clear")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        ButtonsGrid(state.currentVariableCount, viewModel::processAction)
    }


}


@Composable
fun ButtonsGrid(currentVariableCount: Int, processAction: (functionInputAction: FunctionInputActions) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize()
    ) {
        items(getTableItemsList(currentVariableCount)) { item ->
            Button(onClick = { processAction(FunctionInputActions.PressElementButton(item)) })
            {
                Text(text = item.value)
            }
        }
    }
}
