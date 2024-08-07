package com.example.testidea.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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
        targetValue = if (isFocused) (-28).dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedFontSize by animateFloatAsState(
        targetValue = if (isFocused) 0.7f else 1f
    )

    val textWidth = remember { mutableStateOf(0) }
    var placeholderVisible by remember { mutableStateOf(true) }

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
                    .offset(x = animatedHorizontalOffset, y = animatedVerticalOffset)
                    .scale(animatedFontSize)
//                    .drawWithContent {
//                        drawContent()
//            if (isFocused) {
//                val path = Path()
//                path.moveTo(0f, 0f)
//                path.lineTo(
//                    (textWidth.value + 60).toFloat(),
//                    0f
//                ) // Adjust 60 based on padding/offset
//                path.lineTo(size.width, 0f)
//                path.lineTo(size.width, size.height)
//                path.lineTo(0f, size.height)
//                path.close()
//
//                drawPath(
//                    path = path,
//                    color = Color.Black
//                )
//            }
//                    }
                ,
                fontSize =
                if (value.isEmpty()) MaterialTheme.typography.bodyLarge.fontSize
                else MaterialTheme.typography.bodySmall.fontSize
            )
        },
        modifier = Modifier
            .onFocusChanged { isFocused = it.isFocused }
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(4.dp)
            ),
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
//            cursorColor = if(isFocused) {Color.Black} else {Color.Transparent}
        )
    )
}
