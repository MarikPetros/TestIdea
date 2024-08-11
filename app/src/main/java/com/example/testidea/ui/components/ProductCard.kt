package com.example.testidea.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.testidea.R
import com.example.testidea.core.model.Product
import com.example.testidea.ui.theme.TestIdeaTheme
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCard(
    product: Product,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier,
    elevation: Dp = Dp(1f),
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.card_horizontal_padding),
                    end = dimensionResource(id = R.dimen.card_horizontal_padding),
                    top = dimensionResource(id = R.dimen.card_vertical_padding),
                    bottom = dimensionResource(id = R.dimen.card_bottom_padding)
                )
        ) {
            val (name, edit, delete, chipsRow, storeLabel, amount, dateLabel, date) = createRefs()
            val guideline = createGuidelineFromStart(0.5f)

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
            IconButton(
                onClick = { onDelete() },
                modifier = Modifier.constrainAs(delete) {
                    top.linkTo(parent.top, margin = (-10).dp)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_item),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            IconButton(
                onClick = { onEdit() },
                modifier = Modifier.constrainAs(edit) {
                    top.linkTo(parent.top, margin = (-10).dp)
                    end.linkTo(delete.start)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_amount),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy((-11).dp),
                modifier = Modifier.constrainAs(chipsRow) {
                    start.linkTo(parent.start)
                    top.linkTo(name.bottom, margin = 8.dp)
                },
            ) {
                product.tags.forEach { chip ->
                    if (chip.isNotEmpty()) {
                        SuggestionChip(
                            onClick = {},
                            label = { Text(chip) }
                        )
                    }
                }
            }
            Text(
                text = stringResource(R.string.in_store),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(storeLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(chipsRow.bottom, margin = 2.dp)
                }
            )
            Text(
                text = product.amount.toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(amount) {
                    start.linkTo(parent.start)
                    top.linkTo(storeLabel.bottom, margin = 2.dp)
                }
            )
            Text(
                text = stringResource(R.string.adding_date),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(dateLabel) {
                    start.linkTo(guideline)
                    top.linkTo(chipsRow.bottom, margin = 2.dp)
                }
            )
            Text(
                text = convertMillisToDate(product.time),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(date) {
                    start.linkTo(guideline)
                    top.linkTo(dateLabel.bottom, margin = 2.dp)
                }
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun convertMillisToDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    return sdf.format(calendar.time)
}

@PreviewLightDark
@Composable
private fun ProductCardPreview() {
    TestIdeaTheme {
        ProductCard(
            product = Product(
                8,
                "Amazon Kindle Paperwhite",
                0,
                mutableListOf("Электронная книга", "Последний шанс", "Ограниченный"),
                18
            ),
            onEdit = {},
            onDelete = {}
        )
    }
}
