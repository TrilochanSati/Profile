package com.windapp.profile

import android.app.AppOpsManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.windapp.profile.service.BlockAppService
import com.windapp.profile.ui.add_block_schedule.AddBlockScheduleScreen
import com.windapp.profile.ui.all_apps.AllAppsScreen
import com.windapp.profile.ui.daily_limit.DailyLimitScreen
import com.windapp.profile.ui.home.HomeScreen
import com.windapp.profile.ui.specific_time_intervals.SpecificTimeIntervalScreen
import com.windapp.profile.ui.theme.ProfileTheme
import com.windapp.usageoverview.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isAccessGrantedForUsageStats()) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }

        startService(
            Intent(
                applicationContext,
                BlockAppService::class.java
            ))

        setContent {
            ProfileTheme {

                val navController= rememberNavController( )
                NavHost(
                    navController=navController,
                    startDestination = Routes.HOME
                ){
                    composable(Routes.HOME){
                        HomeScreen(onNavigate = {
                            navController.navigate(it.route)
                        })
                    }
                    composable(Routes.ADD_BLOCK_SCHEDULE+"?packageName={packageName}",
                            arguments = listOf(
                                navArgument(name = "packageName"){
                                    type=NavType.StringType
                                    defaultValue="null"
                                }
                            )

                        ){
                        AddBlockScheduleScreen(onNavigate = {
                            navController.navigate(it.route)

                        })
                    }

                    composable(route = Routes.DAILY_LIMIT+"?packageName={packageName}"+"?id={id}"+"?isUpdate={isUpdate}",
                        arguments = listOf(
                            navArgument(name="packageName"){
                                type= NavType.StringType
                                defaultValue="null"

                            },
                            navArgument(name="id"){
                                type=NavType.IntType
                                defaultValue=-1
                            },
                            navArgument(name = "isUpdate"){
                                type=NavType.BoolType
                                defaultValue=false
                            }
                        )
                        ){
                        DailyLimitScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(route = Routes.SPECIFIC_TIME_INTERVALS+"?packageName={packageName}"+"?id={id}"+"?isUpdate={isUpdate}",
                        arguments = listOf(
                            navArgument(name="packageName"){
                                type= NavType.StringType
                                defaultValue="null"

                            },
                            navArgument(name="id"){
                                type=NavType.IntType
                                defaultValue=-1
                            },
                            navArgument(name = "isUpdate"){
                                type=NavType.BoolType
                                defaultValue=false
                            }
                        )
                    ){
                        SpecificTimeIntervalScreen(

                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(Routes.ALL_APPS){
                        AllAppsScreen(onNavigate ={
                            navController.navigate(it.route)
                        } )
                    }
                }



            }
        }
    }

    private fun isAccessGrantedForUsageStats(): Boolean {
        return try {
            val packageManager = packageManager
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            val appOpsManager = getSystemService(APP_OPS_SERVICE) as AppOpsManager
            var mode = 0
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    applicationInfo.uid, applicationInfo.packageName
                )
            }
            mode == AppOpsManager.MODE_ALLOWED
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
