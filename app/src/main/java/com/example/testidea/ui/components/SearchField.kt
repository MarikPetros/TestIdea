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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
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

@OptIn(ExperimentalMaterial3Api::class)
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
    val deletedBorderWidth = 120.dp

    val strokeWidth = with(LocalDensity.current) { borderWidth.toPx() }
    val deletedWidth = with(LocalDensity.current) { deletedBorderWidth.toPx() }
    val borderColor = MaterialTheme.colorScheme.primary


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
        finishedListener = { dp ->
        }
    )
    val animatedFontSize by animateFloatAsState(
        targetValue = if (isFocused) 0.8f else 1f
    )

    val textWidth = remember { mutableStateOf(130) }
//    var placeholderVisible by remember { mutableStateOf(true) }

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
                modifier = Modifier
                    .padding(2.dp)

//                    .drawWithContent {
//                        drawContent()
//                        drawContext.canvas.saveLayer(size.toRect(), Paint().apply {
//                           PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
//                        })
//                        // Draw the composable content first
//                        // Then draw the rounded rectangle on top
//                        drawRoundRect(
//                            color = Color.White,
//                            cornerRadius = CornerRadius(4.dp.toPx()),
//                            style = Stroke(width = 2.dp.toPx()),
////                            blendMode = BlendMode.Multiply
//                        )
//                        drawContext.canvas.restore()
//                    }
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

                    // Draw the top border (excluding the deleted part)
                    drawLine(
                        color = borderColor, // Or your desired color
                        start = Offset(deletedWidth, 0f),
                        end = Offset(size.width + strokeWidth, 0f),
                        strokeWidth = strokeWidth * 2
                    )

                    // Draw other borders (right, bottom, left)
                    drawLine(
                        color = borderColor,
                        start = Offset(size.width + strokeWidth/2, 0f),
                        end = Offset(size.width + strokeWidth, size.height + strokeWidth),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = borderColor,
                        start = Offset(0f - strokeWidth/2, size.height + strokeWidth/2),
                        end = Offset(size.width + strokeWidth/2, size.height + strokeWidth/2),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = borderColor,
                        start = Offset(0f - strokeWidth, 0f - strokeWidth),
                        end = Offset(0f - strokeWidth/2, size.height + strokeWidth),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = borderColor,
                        start = Offset(0f - strokeWidth/2, 0f - strokeWidth/2),
                        end = Offset(40f, 0f - strokeWidth/2),
                        strokeWidth = strokeWidth
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
//        decorationBox = { innerTextField ->
//            // Draw the border manually
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(4.dp) // Add padding if needed
//                    .drawBehind {
//                        val strokeWidth = 2.dp.toPx() // Set border width
//                        val cutOffStart = cutOffWidth.toPx()
//
//                        // Draw the top border with a cutout
//                        drawLine(
//                            color = Color.Black, // Set border color
//                            start = Offset(cutOffStart, 0f),
//                            end = Offset(size.width, 0f),
//                            strokeWidth = strokeWidth
//                        )
//
//                        // Draw the other borders
//                        drawLine(
//                            color = Color.Black,
//                            start = Offset(0f, 0f),
//                            end = Offset(0f, size.height),
//                            strokeWidth = strokeWidth
//                        )
//                        drawLine(
//                            color = Color.Black,
//                            start = Offset(0f, size.height),
//                            end = Offset(size.width, size.height),
//                            strokeWidth = strokeWidth
//                        )
//                        drawLine(
//                            color = Color.Black,
//                            start = Offset(size.width, 0f),
//                            end = Offset(size.width, size.height),
//                            strokeWidth = strokeWidth
//                        )
//                    }
//            ) {
//                innerTextField() // Place the actual text field inside
//            }
//        }

    )
}
