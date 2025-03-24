import androidx.lifecycle.ViewModel
import com.example.karnaughmapsapplication.presentation.FunctionInputUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


sealed class FunctionInputActions {
    data class SetVariableCount(val count: String) : FunctionInputActions()
    data class PressElementButton(val element: TableItem) : FunctionInputActions()
    data object PressClearButton : FunctionInputActions()
    data object PressEqualsButton : FunctionInputActions()
}

class FunctionInputViewModel: ViewModel() {
    private val _state = MutableStateFlow(FunctionInputUiState())
    var state = _state.asStateFlow()

    fun processAction(action: FunctionInputActions){
        when(action){
            is FunctionInputActions.SetVariableCount -> handleSetVariableCount(action.count)
            is FunctionInputActions.PressElementButton -> handlePressElementButton(action.element)
            is FunctionInputActions.PressClearButton -> handlePressClearButton()
            else -> null
        }
    }

    private fun handleSetVariableCount(count: String) {
        _state.update { currentState ->
            currentState.copy(
                currentVariableCountString = count,
                error = if (count.isBlank()) "Count cannot be empty" else null
            )
        }
    }

    private fun handlePressElementButton(tableItem: TableItem){
        _state.update { currentState ->
            currentState.copy(currentFunctionInput = currentState.currentFunctionInput + tableItem.value)
        }
    }

    private fun handlePressClearButton(){
        _state.update { currentState ->
            currentState.copy(currentFunctionInput = "")
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