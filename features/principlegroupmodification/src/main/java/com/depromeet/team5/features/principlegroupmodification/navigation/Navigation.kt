package com.depromeet.team5.features.principlegroupmodification.navigation

import android.os.Build
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.savedstate.SavedState
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.navigation.IS_PRINCIPLE_UPDATED
import com.depromeet.team5.features.principlegroupmodification.screen.PrincipleGroupModificationRoute
import kotlinx.serialization.Serializable


@Serializable
class PrincipleGroupModification(
    val groupId: Int?,
    val orderType: OrderType
)

fun NavController.navigateToPrincipleGroupModification(
    groupId: Int?,
    orderType: OrderType,
    navOptions: NavOptions? = null
) {
    navigate(
        route = PrincipleGroupModification(groupId, orderType),
        navOptions = navOptions
    )
}

fun NavController.navigateToPrincipleGroupModificationDialog(
    orderType: OrderType,
    navOptions: NavOptions? = null
) {
    //null 값으로 전달할 시, 제대로 DeSerialization을 못하는 이슈가 생겨 defaultValue값을 리턴함.
    val groupId = -1

    navigate(
        route = "principle_group_modification_dialog/$groupId/$orderType",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.principleGroupModification(
    navController: NavController,
    onShowToast: (String) -> Unit,
    onShowNoIconToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
) {
    composable<PrincipleGroupModification> {
        PrincipleGroupModificationRoute(
            modifier = Modifier.systemBarsPadding(),
            onShowToast = onShowToast,
            onShowNoIconToast = onShowNoIconToast,
            onShowErrorToast = onShowErrorToast,
            onBackPressed = { isUpdated ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(IS_PRINCIPLE_UPDATED, isUpdated)

                navController.popBackStack()
            }
        )
    }
}

fun NavGraphBuilder.principleGroupModificationDialog(
    navController: NavController,
    onShowToast: (String) -> Unit,
    onShowNoIconToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
) {
    dialog(
        route = "principle_group_modification_dialog/{groupId}/{orderType}",
        arguments = listOf(
            navArgument( //반드시 모든 arguments가 전달되어야 하므로 Serializer로 등록되지 않아도 모두 추가해야 함.
                name = "groupId",
                builder = {
                    type = NavType.IntType
                }
            ),
            navArgument(
                name = "orderType",
                builder = {
                    type = OrderTypeSerializable
                }
            )
        ),
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        PrincipleGroupModificationRoute(
            modifier = Modifier.systemBarsPadding(),
            onShowToast = onShowToast,
            onShowNoIconToast = onShowNoIconToast,
            onShowErrorToast = onShowErrorToast,
            onBackPressed = { isUpdated ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(IS_PRINCIPLE_UPDATED, isUpdated)

                navController.popBackStack()
            }
        )
    }
}

private val OrderTypeSerializable = object : NavType<OrderType>(isNullableAllowed = false) {
    override fun parseValue(value: String): OrderType = try {
        OrderType.valueOf(value)
    } catch (e: Exception) {
        OrderType.NONE
    }

    override fun put(
        bundle: SavedState,
        key: String,
        value: OrderType
    ) {
        bundle.putSerializable(key, value)
    }

    override fun get(
        bundle: SavedState,
        key: String
    ): OrderType? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getSerializable(key, OrderType::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getSerializable(key) as? OrderType
        }
    }
}