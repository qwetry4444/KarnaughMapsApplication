import androidx.lifecycle.ViewModel
import com.example.karnaughmapsapplication.presentation.FunctionInputState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class FunctionInputViewModel: ViewModel() {
    private val _state = MutableStateFlow(FunctionInputState())
    var state = _state.asStateFlow()

}