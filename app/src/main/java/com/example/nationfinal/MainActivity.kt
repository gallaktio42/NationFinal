package com.example.nationfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nationfinal.screens.BucketScreen
import com.example.nationfinal.screens.CategoryScreen
import com.example.nationfinal.screens.CustomBottomBar
import com.example.nationfinal.screens.ForgotPasswordScreen
import com.example.nationfinal.screens.OTPVerificationScreen
import com.example.nationfinal.screens.OnBoardScreen
import com.example.nationfinal.screens.PopularScreen
import com.example.nationfinal.screens.SearchScreen
import com.example.nationfinal.screens.SignInScreen
import com.example.nationfinal.screens.SignUpScreen
import com.example.nationfinal.ui.theme.NationFinalTheme
import okhttp3.Route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NationFinalTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Routes.Home.route){
                    composable(Routes.SignIn.route) { SignInScreen(navController = navController) }
                    composable(Routes.SignUp.route) { SignUpScreen(navController = navController) }
                    composable(Routes.ForgotPass.route) { ForgotPasswordScreen(navController = navController) }
                    composable("${Routes.OTPVerify.route}/{email}") {
                        val email = it.arguments?.getString("email") ?: ""
                        OTPVerificationScreen(navController = navController, email = email) }
                    composable(Routes.Home.route) { CustomBottomBar(navController) }
                    composable(Routes.Onboarding.route) { OnBoardScreen(navController) }
                    composable(Routes.Popular.route) { PopularScreen(navController) }
                    composable("${Routes.Category.route}/{category}") {
                        val category = it.arguments?.getString("category") ?: ""
                        CategoryScreen(navController, category) }
                    composable(Routes.Bucket.route) {
                        BucketScreen(navController)
                    }
                    composable(Routes.Search.route) {
                        SearchScreen(navController)
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
}
