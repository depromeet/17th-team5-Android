package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.R
import com.depromeet.team5.feature.reasons.model.PrincipleAdherence


@Composable
fun PrincipleAdherenceContainer(
    checkedPrincipleAdherence: PrincipleAdherence,
    modifier: Modifier = Modifier,
    onAdherenceChanged: (PrincipleAdherence) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrincipleAdherence(
            checked = checkedPrincipleAdherence == PrincipleAdherence.KEPT,
            painter = painterResource(R.drawable.ic_circle),
            label = stringResource(R.string.principle_followed),
            onClick = { onAdherenceChanged(PrincipleAdherence.KEPT) },
        )
        PrincipleAdherence(
            checked = checkedPrincipleAdherence == PrincipleAdherence.NEUTRAL,
            painter = painterResource(R.drawable.ic_triangle),
            label = stringResource(R.string.principle_neutral),
            onClick = { onAdherenceChanged(PrincipleAdherence.NEUTRAL) },
        )
        PrincipleAdherence(
            checked = checkedPrincipleAdherence == PrincipleAdherence.NOT_KEPT,
            painter = painterResource(R.drawable.ic_cross),
            label = stringResource(R.string.principle_not_followed),
            onClick = { onAdherenceChanged(PrincipleAdherence.NOT_KEPT) },
        )
    }
}


@Composable
fun RowScope.PrincipleAdherence(
    checked: Boolean,
    painter: Painter,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
            )
            .weight(1f)
            .border(
                width = if (checked) (1.5).dp else 1.dp,
                color = if (checked) HedgeColor.Brand.Primary else HedgeColor.Neutral.BackgroundSecondary,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = if (checked) HedgeColor.Brand.Secondary.copy(0.3f) else Color.Transparent
            )
            .padding(vertical = 15.dp, horizontal = 22.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painter,
            contentDescription = "check",
            tint = if (checked) HedgeColor.Brand.Primary else Color.Unspecified,
        )
        Text(
            text = label,
            style = HedgeTypography.Label1.SemiBold,
            color = if (checked) HedgeColor.Brand.Darken else HedgeColor.Text.Assistive,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PrincipleCheckPreview() {
    PrincipleAdherenceContainer(
        checkedPrincipleAdherence = PrincipleAdherence.KEPT,
        onAdherenceChanged = {}
    )
}
