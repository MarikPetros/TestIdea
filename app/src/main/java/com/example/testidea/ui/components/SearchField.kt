package com.example.testidea.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.testidea.R
import kotlinx.coroutines.delay

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    var showCursor by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    var showClearIcon by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val borderWidth = 2.dp

    val strokeWidth = with(LocalDensity.current) { borderWidth.toPx() }
    val cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
    val borderStartSize = with(LocalDensity.current) { 16.dp.toPx() }
    val borderColor =
        if (!isFocused && value.isNotEmpty()) Color.Black else MaterialTheme.colorScheme.primary

    var textWidth by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = value) { // Trigger when text changes
        if (value.isNotEmpty()) {
            delay(100) // Add a small delay if needed
            showClearIcon = true
        }
    }

    val animatedHorizontalOffset by animateDpAsState(
        targetValue = if (isFocused || value.isNotEmpty()) (-47).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "animatedX"
    )
    val animatedVerticalOffset by animateDpAsState(
        targetValue = if (isFocused || value.isNotEmpty()) (-27).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "animatedY",
    )
    val animatedFontSize by animateFloatAsState(
        targetValue = if (isFocused || value.isNotEmpty()) 0.8f else 1f, label = "animatedFontScale"
    )


    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            showClearIcon = it.isNotEmpty()
            showCursor = true
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = showClearIcon
            ) {
                IconButton(onClick = {
                    onValueChange("")
                    showClearIcon = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        modifier = Modifier
            .onFocusChanged {
                isFocused = it.isFocused
                showCursor = isFocused
            }
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .drawBehind {
                if (isFocused || value.isNotEmpty()) {
                    val stroke = if (!isFocused) strokeWidth else strokeWidth * 2
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(textWidth.toFloat(), 0f),
                        size = Size(size.width - textWidth.toFloat(), strokeWidth),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(stroke)
                    )

                    // Draw other rounded borders (right, bottom, left)
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(size.width - strokeWidth, 0f),
                        size = Size(strokeWidth, size.height),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(stroke)
                    )
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(0f, size.height - strokeWidth),
                        size = Size(size.width, strokeWidth),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(stroke)
                    )
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(strokeWidth, size.height),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(stroke)
                    )
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(borderStartSize, strokeWidth),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(stroke)
                    )
                } else {
                    // Draw the default OutlinedTextField border
                    drawRoundRect(
                        color = Color.Black,
                        style = Stroke(width = borderWidth.toPx()),
                        cornerRadius = CornerRadius(8f)
                    )
                }
            },
        shape = RoundedCornerShape(4.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onValueChange(value)
            keyboardController?.hide()
            focusManager.clearFocus(true)
            showCursor = false // Hide cursor after search
            focusRequester.freeFocus()
        }),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = if (showCursor) Color.Black else Color.Transparent,
        ),
    )

    Text(
        text = stringResource(R.string.search_product),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f),
        onTextLayout = { textLayoutResult ->
            textWidth = textLayoutResult.size.width
        },
        modifier = Modifier
            .padding(start = 55.dp, top = 15.dp)
            .offset(x = animatedHorizontalOffset, y = animatedVerticalOffset)
            .scale(animatedFontSize)
    )
}



