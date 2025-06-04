import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.karnaughmapsapplication.core.domain.model.getGroupedTableItems
import com.example.karnaughmapsapplication.features.FunctionInput.presentation.ErrorType
import com.example.karnaughmapsapplication.features.FunctionInput.presentation.FunctionInputUiState

@Composable
fun FunctionInputPage(
    navController: NavHostController
) {
    val viewModel: FunctionInputViewModel = viewModel()
    val state: FunctionInputUiState by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .background(Color(0xFFF9F9F9))
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        VariablesCountInputForm(
            currentVariableCount = state.currentVariableCount,
            processAction = viewModel::processAction
        )

        Spacer(modifier = Modifier.height(32.dp))

        FunctionInputField(
            currentFunctionInput = state.currentFunctionInput,
            isError = state.errorType == ErrorType.WrongFunctionInput,
            processAction = viewModel::processAction
        )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonsGrid(
            currentVariableCount = state.currentVariableCount,
            processAction = viewModel::processAction
        )

        Spacer(modifier = Modifier.weight(1f))

        SubmitButton(
            navController = navController,
            processAction = viewModel::processAction
        )
    }
}


@Composable
fun FunctionInputField(
    currentFunctionInput: TextFieldValue,
    isError: Boolean,
    processAction: (FunctionInputActions) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFFFFF))
                    .border(width = 1.dp, color = if (isError) Color(0xFFB00020) else Color(0xFFCCCCCC), shape = RoundedCornerShape(12.dp))
                    .height(56.dp)
            ) {
                BasicTextField(
                    value = currentFunctionInput,
                    onValueChange = { newValue ->
                        processAction(FunctionInputActions.UpdateFunctionInputText(newValue))
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEEEEEE))
            ) {
                IconButton(
                    onClick = { processAction(FunctionInputActions.PressClearButton) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "Clear",
                        tint = Color(0xFF666666),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        if (isError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Неправильная функция!",
                color = Color(0xFFB00020),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun ButtonsGrid(
    currentVariableCount: Int,
    processAction: (FunctionInputActions) -> Unit
) {
    val groups = getGroupedTableItems(currentVariableCount)

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        groups.forEach { group ->
            Column {
                Text(
                    text = group.title,
                    color = Color(0xFF333333),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    group.items.forEach { item ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(8.dp),
                                    clip = false
                                )
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF0F0F0))
                                .height(48.dp)
                                .widthIn(min = 48.dp)
                        ) {
                            Text(
                                text = item.tableItemToString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF000000)
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable {
                                        processAction(FunctionInputActions.PressElementButton(item))
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun VariablesCountInputForm(
    currentVariableCount: Int,
    processAction: (FunctionInputActions) -> Unit
) {
    val options = (1..6).toList()
    var expanded by remember { mutableStateOf(false) }

    val selectedText = if (currentVariableCount in options) {
        currentVariableCount.toString()
    } else {
        options.first().toString()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Количество переменных",
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFFFFFFF))
                .border(
                    width = 1.dp,
                    color = Color(0xFFCCCCCC),
                    shape = RoundedCornerShape(8.dp)
                )
                .width(72.dp)
                .height(48.dp)
                .clickable { expanded = true },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Open dropdown",
                    tint = Color(0xFF666666)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(72.dp)
                    .background(Color(0xFFFFFFFF))
                    .border(1.dp, Color(0xFFCCCCCC))
            ) {
                options.forEach { count ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = count.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        },
                        onClick = {
                            processAction(FunctionInputActions.SetVariableCount(count.toString()))
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SubmitButton(
    navController: NavHostController,
    processAction: (FunctionInputActions) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(32.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF98FB98))
                .width(112.dp)
                .height(52.dp)
        ) {
            IconButton(
                onClick = {
                    processAction(FunctionInputActions.PressSubmitButton(navController))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Submit",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
