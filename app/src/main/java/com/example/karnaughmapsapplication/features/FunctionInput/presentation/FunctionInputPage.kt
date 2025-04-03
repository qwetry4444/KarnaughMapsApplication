import android.graphics.drawable.shapes.Shape
import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.karnaughmapsapplication.core.domain.model.getTableItemsList
import com.example.karnaughmapsapplication.features.FunctionInput.presentation.ErrorType
import com.example.karnaughmapsapplication.features.FunctionInput.presentation.FunctionInputUiState



@Composable
fun FunctionInputPage(
    navController: NavHostController
) {
    val viewModel: FunctionInputViewModel = viewModel()
    val state: FunctionInputUiState by viewModel.state.collectAsState()


    Column(modifier = Modifier.fillMaxSize().padding(42.dp).padding(top = 20.dp))
    {
        Spacer(modifier = Modifier.height(4.dp))

        VariablesCountInputForm(
            state.currentVariableCount,
            viewModel::processAction
        )

        Spacer(modifier = Modifier.height(32.dp))

        FunctionInputField(
            state.currentFunctionInput,
            state.errorType == ErrorType.WrongFunctionInput,
            viewModel::processAction
        )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonsGrid(
            state.currentVariableCount,
            viewModel::processAction
        )

        Spacer(modifier = Modifier.weight(1f))

        SubmitButton(
            navController,
            viewModel::processAction
        )
    }
}

@Composable
fun VariablesCountInputForm(
    currentVariableCount: Int,
    processAction: (functionInputAction: FunctionInputActions) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Количество переменных", fontSize = 17.sp)
        Spacer(modifier = Modifier.width(12.dp))
        TextField(
            value = TextFieldValue(text = currentVariableCount.toString(), selection = TextRange(1)),
            onValueChange = { processAction(FunctionInputActions.SetVariableCount(it.text)) },
            modifier = Modifier.width(55.dp),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun FunctionInputField(
    currentFunctionInput: TextFieldValue,
    isError: Boolean,
    processAction: (functionInputAction: FunctionInputActions) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = currentFunctionInput,
            onValueChange = { newValue -> processAction(FunctionInputActions.UpdateFunctionInputText(newValue)) },
            modifier = Modifier.weight(0.8f),
            isError = isError,
            supportingText = {
                if (isError){
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Неправильная функция!",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (isError)
                    Icon(Icons.Default.Warning,"error", tint = MaterialTheme.colorScheme.error)
            }
        )

        IconButton(
            onClick = {processAction(FunctionInputActions.PressClearButton)},
            modifier = Modifier.size(40.dp).weight(0.2f)
        ) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "clear")
        }
    }
}

@Composable
fun ButtonsGrid(currentVariableCount: Int, processAction: (functionInputAction: FunctionInputActions) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(getTableItemsList(currentVariableCount)) { item ->
            Button(
                onClick = { processAction(FunctionInputActions.PressElementButton(item)) },
                modifier = Modifier.width(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = item.tableItemToString())
            }
        }
    }
}

@Composable
fun SubmitButton(
    navController: NavHostController,
    processAction: (functionInputAction: FunctionInputActions) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = { processAction(FunctionInputActions.PressSubmitButton(navController)) },
            modifier = Modifier
                .height(52.dp)
                .width(112.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(152, 251, 152)
            ),
            shape = RoundedCornerShape(32.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Submit",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

