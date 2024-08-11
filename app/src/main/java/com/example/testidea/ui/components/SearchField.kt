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
    var showClearIcon by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val borderWidth = 2.dp

    val strokeWidth = with(LocalDensity.current) { borderWidth.toPx() }
    val cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
    val borderStartSize = with(LocalDensity.current) { 16.dp.toPx() }
    val borderColor = MaterialTheme.colorScheme.primary

    var textWidth by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = value) { // Trigger when text changes
        if (value.isNotEmpty()) {
            delay(100) // Add a small delay if needed
            showClearIcon = true
        }
    }

    val animatedHorizontalOffset by animateDpAsState(
        targetValue = if (isFocused) (-45).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedVerticalOffset by animateDpAsState(
        targetValue = if (isFocused) (-30).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
    )
    val animatedFontSize by animateFloatAsState(
        targetValue = if (isFocused) 0.8f else 1f
    )


    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            showClearIcon = it.isNotEmpty()
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
        placeholder = {
            Text(
                stringResource(R.string.search_product),
                onTextLayout = { textLayoutResult ->
                    textWidth = textLayoutResult.size.width
                },
                modifier = Modifier
                    .padding(2.dp)
                    .offset(x = animatedHorizontalOffset, y = animatedVerticalOffset)
                    .scale(animatedFontSize),
                fontSize =
                if (value.isEmpty()) MaterialTheme.typography.bodyLarge.fontSize
                else MaterialTheme.typography.bodySmall.fontSize

            )
        },
        modifier = Modifier
            .onFocusChanged { isFocused = it.isFocused }
            .fillMaxWidth()
            .drawBehind {
                if (isFocused) {
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(textWidth.toFloat(), 0f),
                        size = Size(size.width - textWidth.toFloat(), strokeWidth),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(strokeWidth * 2)
                    )

                    // Draw other rounded borders (right, bottom, left)
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(size.width - strokeWidth, 0f),
                        size = Size(strokeWidth, size.height),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(strokeWidth * 2)
                    )
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(0f, size.height - strokeWidth),
                        size = Size(size.width, strokeWidth),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(strokeWidth * 2)
                    )
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(strokeWidth, size.height),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(strokeWidth * 2)
                    )
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(0f, 0f),
                        size = Size(borderStartSize, strokeWidth),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                        style = Stroke(strokeWidth * 2)
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
            keyboardController?.hide()
            focusManager.clearFocus(true)
        }),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        )
    )
}
