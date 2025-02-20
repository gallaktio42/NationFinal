package com.example.nationfinal

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nationfinal.screens.BottomBarForMenu
import com.example.nationfinal.screens.BucketScreen
import com.example.nationfinal.screens.CategoryScreen
import com.example.nationfinal.screens.CustomBottomBar
import com.example.nationfinal.screens.FavoriteScreen
import com.example.nationfinal.screens.ForgotPasswordScreen
import com.example.nationfinal.screens.OTPVerificationScreen
import com.example.nationfinal.screens.OnBoardScreen
import com.example.nationfinal.screens.PopularScreen
import com.example.nationfinal.screens.ProfileScreen
import com.example.nationfinal.screens.SearchScreen
import com.example.nationfinal.screens.SideMenuScreen
import com.example.nationfinal.screens.SignInScreen
import com.example.nationfinal.screens.SignUpScreen
import com.example.nationfinal.ui.theme.NationFinalTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            NationFinalTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Routes.SignIn.route) {
                    composable(Routes.SignIn.route) { SignInScreen(navController = navController) }
                    composable(Routes.SignUp.route) { SignUpScreen(navController = navController) }
                    composable(Routes.ForgotPass.route) { ForgotPasswordScreen(navController = navController) }
                    composable("${Routes.OTPVerify.route}/{email}") {
                        val email = it.arguments?.getString("email") ?: ""
                        OTPVerificationScreen(navController = navController, email = email)
                    }
                    composable(Routes.Home.route) {
                        CustomBottomBar(navController)
                    }
                    composable(Routes.Onboarding.route) { OnBoardScreen(navController) }
                    composable(Routes.Popular.route) { PopularScreen(navController) }
                    composable("${Routes.Category.route}/{category}") {
                        val category = it.arguments?.getString("category") ?: ""
                        CategoryScreen(navController, category)
                    }
                    composable(Routes.Bucket.route) {
                        BucketScreen(navController)
                    }
                    composable(Routes.Search.route) {
                        SearchScreen(navController)
                    }
                    composable(Routes.Menu.route) {
                        SideMenuScreen(navController)
                    }
                    composable(
                        "${Routes.BottomBar.route}/{index}",
                        arguments = listOf(
                            navArgument("index"){
                                type = NavType.IntType
                                nullable = false
                                defaultValue = 1
                            }
                        )
                        ) {
                        it.arguments?.getInt("index")?.let{ index->
                            BottomBarForMenu(navController, index)
                        }
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object SignIn : Routes("signin")
    object SignUp : Routes("signup")
    object ForgotPass : Routes("forgotpass")
    object OTPVerify : Routes("otp")
    object Onboarding : Routes("onboard")
    object Popular : Routes("popular")
    object Category : Routes("category")
    object Bucket : Routes("bucket")
    object Search : Routes("search")
    object Menu : Routes("menu")
    object BottomBar : Routes("bar")
    //object Test : Routes("test")
}
