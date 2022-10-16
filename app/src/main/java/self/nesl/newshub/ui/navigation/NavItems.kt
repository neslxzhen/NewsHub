package self.nesl.newshub.ui.navigation

import self.nesl.newshub.R

open class NavItems(
    open val resourceId: Int? = null,
    open val icon: Int,
    open val route: String,
    open val title: String? = null,
)

sealed class DrawerNavItems(
    override val resourceId: Int,
    override val icon: Int,
    override val route: String,
): NavItems(resourceId, icon, route) {
    object Home : NavItems(R.string.home, R.drawable.ic_outline_home_24, "home")
    object History : NavItems(R.string.history, R.drawable.ic_outline_history_24, "history")
}

fun drawerNavItems() = listOf(
    DrawerNavItems.Home,
    DrawerNavItems.History,
)

sealed class BottomNavItems(
    override val resourceId: Int,
    override val icon: Int,
    override val route: String
): NavItems(resourceId, icon, route) {
    object Default : NavItems(R.string.preset, R.drawable.ic_outline_globe_24, "preset")
    object Newest : NavItems(R.string.newest, R.drawable.ic_outline_flash_on_24, "newest")
    object Hot : NavItems(R.string.hot, R.drawable.ic_outline_whatshot_24, "hot")
}

fun bottomNavItems() = listOf(
    BottomNavItems.Default,
    BottomNavItems.Newest,
    BottomNavItems.Hot,
)