package com.windapp.profile.ui.all_apps

import android.app.Application
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.windapp.profile.ProfileApp
import com.windapp.profile.data.ProfileRepository
import com.windapp.profile.data.entities.InstalledApp
import com.windapp.profile.data.entities.SpecificTimeIntervals
import com.windapp.profile.data.entities.UsageLimitDaily
import com.windapp.profile.util.UiEvent
import com.windapp.profile.util.UtilUsageTimeline
import com.windapp.usageoverview.util.ApplicationFetcher
import com.windapp.usageoverview.util.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllAppsViewModel @Inject constructor(
     application: Application,
 private val  repository: ProfileRepository,
 savedStateHandle: SavedStateHandle

) :AndroidViewModel(application) {





    val allInstalledApp=repository.getAllApplicationName()

        private val _uiEvent= Channel<UiEvent> ()
        val uiEvent=_uiEvent.receiveAsFlow()

    init {

        insertAllInstalledApp(application)


    }

    fun getIcon(packageName:String):Drawable{
        var icon=UtilUsageTimeline.getIcon(getApplication(),packageName)
     if(icon==null){
         return ContextCompat.getDrawable(this.getApplication(), android.R.drawable.btn_star)!!

     }
        else{
         return icon

     }

    }

    fun getAppName(packageName: String):String{
        return UtilUsageTimeline.getAppname(getApplication(),packageName)?:"null"
    }

   private fun insertAllInstalledApp(application: Application){
        var packageNameList = ApplicationFetcher.getInstalledApps(application)

        if (packageNameList != null) {
            for(item in packageNameList){
                insertAppname(InstalledApp(item.getPackageName(),item.getApplicationName(),false))
            }
        }
    }


 private   fun insertAppname(installedApp:InstalledApp){
     viewModelScope.launch {
         repository.insertApplicationName(installedApp = installedApp)
     }
    }

     fun getUsageLimitDailyByPackageName(packageName: String): Flow<List<UsageLimitDaily>> {
   return   repository.getFlowUsageLimitDailyByPackageName(packageName)

    }

    fun getFlowSpecificTimeIntervalByPackageName(packageName: String): Flow<List<SpecificTimeIntervals>> {
        return   repository.getFlowSpecificTimeIntervalByPackageName(packageName)

    }

    fun onEvent(event: AllAppsEvent){
        when(event){
            is AllAppsEvent.OnAppClick->{

                sendUiEvent(UiEvent.Navigate(Routes.ADD_BLOCK_SCHEDULE+"?packageName=${event.packageName}"))
            }

            is AllAppsEvent.OnAppIconClick->{
                sendUiEvent(UiEvent.ShowBottomSheet(event.packageName))
            }

            is AllAppsEvent.OnProfileDeleteClick->{
                when(event.profileType){
                    is ProfileType.UsageLimeDaily->{
                        viewModelScope.launch {
                            repository.deleteUsageLimitDailyById(event.id)
                        }
                    }
                    is ProfileType.SpecificTimeInterval->{
                        viewModelScope.launch {
                            repository.deleteSpecificTimeIntervalById(event.id)
                        }
                    }
                }


            }

            is AllAppsEvent.OnProfileEditClick ->{

                when(event.porfileType){
                    is ProfileType.UsageLimeDaily->{
                        sendUiEvent(UiEvent.Navigate(Routes.DAILY_LIMIT+"?packageName=${event.packageName}"+"?id=${event.id}"+"?isUpdate=${true}"))

                    }
                    is ProfileType.SpecificTimeInterval ->{
                        sendUiEvent(UiEvent.Navigate(Routes.SPECIFIC_TIME_INTERVALS+"?packageName=${event.packageName}"+"?id=${event.id}"+"?isUpdate=${true}"))

                    }
                }

            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}