package com.adorastudios.composeanimations.screens.screenLazyListWithDragAndDrop

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.adorastudios.composeanimations.composables.ScreenLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun LazyListWithDragAndDropScreen(
    navController: NavController,
    viewModel: LazyListWithDragAndDropViewModel = viewModel(),
) {
    val state = viewModel.state

    val context = LocalContext.current
    ScreenLayout(
        title = "LazyListWithDragAndDropScreen",
        onBack = { navController.popBackStack() },
    ) {
        val lazyListState = rememberLazyListState()
        var currentOffset by remember { mutableFloatStateOf(0f) }

        var initialElement by remember { mutableStateOf<LazyListItemInfo?>(null) }
        var currentElementAbsoluteIndex by remember { mutableStateOf<Int?>(null) }

        val scope = rememberCoroutineScope()
        var overscrollJob by remember { mutableStateOf<Job?>(null) }

        fun checkForOverScroll(): Float {
            return initialElement?.let {
                val startOffset = it.offset + currentOffset
                val endOffset = it.offsetEnd + currentOffset
                val viewPortStart = lazyListState.layoutInfo.viewportStartOffset
                val viewPortEnd = lazyListState.layoutInfo.viewportEndOffset

                when {
                    currentOffset > 0 -> (endOffset - viewPortEnd).takeIf { diff -> diff > 0 }
                    currentOffset < 0 -> (startOffset - viewPortStart).takeIf { diff -> diff < 0 }
                    else -> null
                }
            } ?: 0f
        }

        fun getCurrentPositionOffset(itemIndex: Int): Float {
            if (itemIndex == currentElementAbsoluteIndex) {
                val currentItemOffset =
                    lazyListState.getVisibleItemInfoAtAbsolute(currentElementAbsoluteIndex)?.offset
                        ?: return 0f
                val initialItemOffset = initialElement?.offset?.toFloat() ?: return 0f

                return initialItemOffset + currentOffset - currentItemOffset
            } else {
                return 0f
            }
        }

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .weight(1f)
                .pointerInput(true) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { offset ->
                            lazyListState.layoutInfo.visibleItemsInfo
                                .firstOrNull { item ->
                                    offset.y.toInt() in item.offset..item.offsetEnd
                                }
                                ?.also {
                                    initialElement = it
                                    currentElementAbsoluteIndex = it.index
                                }
                        },
                        onDragEnd = {
                            currentOffset = 0f
                            currentElementAbsoluteIndex = null
                            initialElement = null
                        },
                        onDragCancel = {
                            currentOffset = 0f
                            currentElementAbsoluteIndex = null
                            initialElement = null
                        },
                        onDrag = { change: PointerInputChange, dragAmount: Offset ->
                            change.consume()
                            currentOffset += dragAmount.y

                            initialElement?.let {
                                val startOffset = it.offset + currentOffset
                                val endOffset = it.offsetEnd + currentOffset

                                val current =
                                    lazyListState.getVisibleItemInfoAtAbsolute(
                                        currentElementAbsoluteIndex ?: return@let,
                                    )
                                current?.let { hovered ->
                                    lazyListState.layoutInfo.visibleItemsInfo
                                        .filterNot { item ->
                                            item.offsetEnd < startOffset || item.offset > endOffset
                                        }
                                        .firstOrNull { item ->
                                            when {
                                                startOffset > hovered.offset -> (endOffset > item.offsetEnd)
                                                else -> (startOffset < item.offset)
                                            }
                                        }
                                        ?.also { item ->
                                            if (currentElementAbsoluteIndex != item.index) {
                                                currentElementAbsoluteIndex?.let { current ->
                                                    viewModel.move(
                                                        current,
                                                        item.index,
                                                    )
                                                }
                                                currentElementAbsoluteIndex = item.index
                                            }
                                        }
                                }
                            }

                            if (overscrollJob?.isActive != true) {
                                checkForOverScroll()
                                    .takeIf { offset -> offset != 0f }
                                    ?.let { offset ->
                                        overscrollJob =
                                            scope.launch { lazyListState.scrollBy(offset) }
                                    }
                                    ?: run { overscrollJob?.cancel() }
                            }
                        },
                    )
                },
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            itemsIndexed(
                items = state.value.list,
                // can not use keys
                // if keys != null LazyColumn will maintain scroll position based on it when array of items is changed
                // https://issuetracker.google.com/issues/209652366
                // key = { _, it -> it },
            ) { index, it ->
                Row(
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = getCurrentPositionOffset(index)
                        }
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            Toast.makeText(context, "Item $it selected", Toast.LENGTH_SHORT).show()
                        }
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(8.dp),
                ) {
                    Text(text = "Item #$it")
                }
            }
        }
    }
}

fun LazyListState.getVisibleItemInfoAtAbsolute(index: Int?): LazyListItemInfo? {
    if (index == null) return null
    return this.layoutInfo.visibleItemsInfo.getOrNull(index - this.layoutInfo.visibleItemsInfo.first().index)
}

val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size
