package com.example.nationfinal.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
){
    object Popular: BottomBarScreen(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )
    object Favorite: BottomBarScreen(
        title = "Favorite",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
    )
    object Add: BottomBarScreen(
        title = "Add",
        selectedIcon = Icons.Default.AddCircle,
        unselectedIcon = Icons.Outlined.AddCircle,
    )
    object Notifications: BottomBarScreen(
        title = "Profile",
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications,
    )
    object Profile: BottomBarScreen(
        title = "Profile",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
    )
}
