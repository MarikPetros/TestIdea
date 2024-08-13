package com.example.testidea.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.testidea.R
import com.example.testidea.ui.theme.TestIdeaTheme

@Composable
fun EditAmountDialog(
    card: Int,
    initialAmount: Int,
    onAmountChange: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var amount by remember { mutableIntStateOf(initialAmount) }

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .padding(24.dp)
            ) {
                // Settings icon on top center
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier
                        .alpha(0.8f)
                        .size(28.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.product_amount),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { amount-- },
                        enabled = amount > 0
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_subtract_on_28),
                            contentDescription = "Decrease",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(30.dp))

                    Text(
                        text = amount.toString(),
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    IconButton(onClick = { amount++ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_add_circle_outline_28),
                            contentDescription = "Decrease",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            stringResource(R.string.cancel),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = { onAmountChange(card, amount); onDismiss() }) {
                        Text(
                            stringResource(R.string.admit),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun EditAmountDialogPreview() {
    TestIdeaTheme {
        EditAmountDialog(
            card = 1, initialAmount = 15,
            onAmountChange = { productId, newAmount -> },
        ) {}
    }
}