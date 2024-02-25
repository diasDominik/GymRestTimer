package de.dominikdias.gymresttimer.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import de.dominikdias.gymresttimer.R

@Immutable
enum class Screens {
    HOME,
    ADD_TIMER,
    SETTINGS,
}

@Immutable
sealed class ScreenNavigation(
    val route: Screens,
    @StringRes val name: Int,
    val icon: ImageVector,
) {
    data object Home : ScreenNavigation(Screens.HOME, R.string.home, Icons.Rounded.Home)

    data object AddTimer : ScreenNavigation(Screens.ADD_TIMER, R.string.add_timer, Icons.Rounded.Add)

    data object Settings : ScreenNavigation(Screens.SETTINGS, R.string.settings, Icons.Rounded.Settings)
}
