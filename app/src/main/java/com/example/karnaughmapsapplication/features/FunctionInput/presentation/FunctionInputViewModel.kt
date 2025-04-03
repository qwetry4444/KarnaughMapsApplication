import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.karnaughmapsapplication.core.domain.model.LogicalFunction
import com.example.karnaughmapsapplication.core.domain.model.TableItem
import com.example.karnaughmapsapplication.core.presentation.navigation.Screen
import com.example.karnaughmapsapplication.features.FunctionInput.presentation.ErrorType
import com.example.karnaughmapsapplication.features.FunctionInput.presentation.FunctionInputUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


sealed class FunctionInputActions {
    data class SetVariableCount(val count: String) : FunctionInputActions()
    data class PressElementButton(val element: TableItem) : FunctionInputActions()
    data object PressClearButton : FunctionInputActions()
    data class UpdateFunctionInputText(val newText: TextFieldValue) : FunctionInputActions()
    data class PressSubmitButton(val navController: NavHostController): FunctionInputActions()
}

class FunctionInputViewModel: ViewModel() {
    private val _state = MutableStateFlow(FunctionInputUiState())
    var state = _state.asStateFlow()

    val logicalFunction = LogicalFunction()

    fun processAction(action: FunctionInputActions){
        _state.update { currentState ->
            currentState.copy(errorType = null)
        }
        when(action){
            is FunctionInputActions.SetVariableCount -> handleSetVariableCount(action.count)
            is FunctionInputActions.PressElementButton -> handlePressElementButton(action.element)
            is FunctionInputActions.PressClearButton -> handlePressClearButton()
            is FunctionInputActions.PressSubmitButton -> handlePressSubmitButton(action.navController)
            is FunctionInputActions.UpdateFunctionInputText -> handleUpdateText(action.newText)
            else -> null
        }
    }

    private fun handleSetVariableCount(count: String) {
        when {
            count.isBlank() -> {
                _state.update { it.copy(error = "Count cannot be empty", currentVariableCount = 0) }
            }
            !count.all { it.isDigit() } -> {
                _state.update { it.copy(error = "Only digits allowed") }
            }
            else -> {
                val countInt = count.toInt().coerceIn(1, 8)
                if (countInt < _state.value.currentVariableCount)
                    _state.update { currentState ->
                        currentState.copy(currentFunctionInput = TextFieldValue())
                    }
                _state.update {
                    it.copy(
                        currentVariableCount = countInt,
                        currentVariableCountString = countInt.toString(),
                        error = null
                    )
                }
            }
        }
    }

    private fun handlePressElementButton(tableItem: TableItem) {
        _state.update { currentState ->
            val text = currentState.currentFunctionInput.text
            val selection = currentState.currentFunctionInput.selection.start

            val newText = text.substring(0, selection) +
                    tableItem.tableItemToString() +
                    text.substring(selection)

            val newCursorPos = selection + tableItem.tableItemToString().length

            currentState.copy(
                currentFunctionInput = currentState.currentFunctionInput.copy(
                    text = newText,
                    selection = TextRange(newCursorPos)
                )
            )
        }
    }

    private fun handleUpdateText(newText: TextFieldValue) {
        _state.update { it.copy(currentFunctionInput = newText) }
    }

    private fun handlePressClearButton(){
        _state.update { currentState ->
            currentState.copy(currentFunctionInput = TextFieldValue())
        }
    }

    private fun handlePressSubmitButton(navController: NavHostController) {
        logicalFunction.expression = _state.value.currentFunctionInput.text
        logicalFunction.variablesCount = _state.value.currentVariableCount

        if (!logicalFunction.isValid()){
            _state.update { currentState ->
                currentState.copy(errorType = ErrorType.WrongFunctionInput)
            }
        } else {
            navController.navigate(
                "karnaugh_map/${logicalFunction.expression}/${logicalFunction.variablesCount}"
            )
        }
    }

    enum class FunctionInputState{
        Default, AfterOperation
    }
}





//open class Operand : Expression(){
//
//}
//open class Expression{
//    val operation: OperationType = OperationType.Or
//}
//class UnaryExpression : Expression(){
//    val operand: Boolean = false
//}
//class BinaryExpression : Expression(){
//    val operand1: Boolean = false
//    val operand2: Boolean = false
//}
