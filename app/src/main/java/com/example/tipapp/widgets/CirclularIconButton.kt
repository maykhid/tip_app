package com.example.tipapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val IconSizeModifier = Modifier.padding(40.dp)
@Composable
fun CircularIconButton (
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colors.background,
    elevation: Dp = 4.dp,
) {
    Card(modifier = modifier
        .padding(all = 10.dp)
        .clickable { onClick.invoke() },
//        .then( // concatenating this modifier with another
//            IconSizeModifier // modifies the current padding on onClick
//        ),
        shape = CircleShape,
        backgroundColor = backgroundColor,
        elevation = elevation) {
        Icon(modifier = Modifier.size(35.dp),imageVector = icon, contentDescription = "Plus or Minus Icon", tint = tint)
    }
}