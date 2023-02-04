package com.windapp.profile.service

import android.app.*
import android.app.usage.UsageEvents
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleService
import com.windapp.profile.BlockedActivity
import com.windapp.profile.MainActivity
import com.windapp.profile.ProfileApp
import com.windapp.profile.R
import com.windapp.profile.data.ProfileRepository
import com.windapp.profile.util.UtilUsageStats
import com.windapp.profile.util.datetime
import com.windapp.usageoverview.util.ApplicationFetcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class BlockAppService:LifecycleService() {

    @Inject
    lateinit var repository:ProfileRepository


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        Thread {

            while (true) {

                Thread.sleep(2000)



                GlobalScope.launch(Dispatchers.IO){
                    Log.d("activeapp",
                        ApplicationFetcher.printForegroundTask(applicationContext)?:"null")



                    val  currentTime=getCurrenteTime()
                    val specificTimeIntervalsList=repository.getSpecificTimeIntervalByPackageName(printForegroundTask(applicationContext)?:"null")

                    specificTimeIntervalsList.forEach {


                            if(it.blockApp) {
                                Log.d("tti",it.timeStart)
                             if (checkBetweenDays(it.onDays)) {
                                 if (checkBetweenTime(it.timeStart, it.timeEnd, currentTime)) {

                                     launchBlockedActivity()

                                 }
                                }
                            }



                    }


                }


                GlobalScope.launch(Dispatchers.IO) {

            var appList=        UtilUsageStats.getApps("",0)
                    appList.forEach {
                        appList->

                        if (appList.pkg.equals(printForegroundTask(applicationContext)?:"null")){
                         val list=   repository.getUsageLimitDailyByPackageName(appList.pkg)

                            list.forEach {
                                usageLimitDaily ->

                                if(checkBetweenDays(usageLimitDaily.onDays)){
                                    if(appList.usedtime>usageLimitDaily.timeLimit){
                                        launchBlockedActivity()
                                    }
                                }




                            }


                        }

                    }






                }

            }

        }.start()
        generateForegroundNotification()
        return  START_STICKY
    }

    fun launchBlockedActivity(){
        var i = Intent(this, BlockedActivity::class.java)

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i)
    }


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    //Notififcation for ON-going
    private var iconNotification: Bitmap? = null
    private var notification: Notification? = null
    var mNotificationManager: NotificationManager? = null
    private val mNotificationId = 123

    private fun generateForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentMainLanding = Intent(this, MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intentMainLanding, PendingIntent.FLAG_MUTABLE)
            iconNotification = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            if (mNotificationManager == null) {
                mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert(mNotificationManager != null)
                mNotificationManager?.createNotificationChannelGroup(
                    NotificationChannelGroup("chats_group", "Chats")
                )
                val notificationChannel =
                    NotificationChannel("service_channel", "Service Notifications",
                        NotificationManager.IMPORTANCE_MIN)
                notificationChannel.enableLights(false)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                mNotificationManager?.createNotificationChannel(notificationChannel)
            }
            val builder = NotificationCompat.Builder(this, "service_channel")

            builder.setContentTitle(StringBuilder(resources.getString(R.string.app_name)).append(" service is running").toString())
                .setTicker(StringBuilder(resources.getString(R.string.app_name)).append("service is running").toString())
                .setContentText("Touch to open") //                    , swipe down for more options.
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setWhen(0)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
            if (iconNotification != null) {
                builder.setLargeIcon(Bitmap.createScaledBitmap(iconNotification!!, 128, 128, false))
            }
            builder.color = resources.getColor(R.color.purple_200)
            notification = builder.build()
            startForeground(mNotificationId, notification)
        }

    }

    private fun getCurrenteTime():String{
        val sdf = SimpleDateFormat("HH:mm")
        val str: String = sdf.format(Date())
        return str
    }

    private fun getCurrentDay():Char{

        // MTWHFSU

        val calendar=Calendar.getInstance()
        val day=calendar.get(Calendar.DAY_OF_WEEK)

        when(day){
            Calendar.MONDAY ->{
                return 'M'
            }
            Calendar.TUESDAY ->{
                return 'T'
            }
            Calendar.WEDNESDAY ->{
                return 'W'
            }
            Calendar.THURSDAY ->{
                return 'H'
            }
            Calendar.FRIDAY ->{
                return 'F'
            }
            Calendar.SATURDAY ->{
                return 'S'
            }
            Calendar.SUNDAY ->{
                return 'U'
            }

        }
        return 'E'

    }

/*    private fun getHourToMinute(time:String):Int{

        val units = time.split(":").toTypedArray() //will break the string up into an array

        val hours = units[0].toInt() //first element

        val minutes = units[1].toInt() //second element

        return 60 * hours + minutes //add up our values

    }*/

    private fun checkBetweenTime(startTime: String, endTime: String, checkTime: String):Boolean {
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US)
        val startLocalTime = LocalTime.parse(startTime, formatter)
        val endLocalTime = LocalTime.parse(endTime, formatter)
        val checkLocalTime = LocalTime.parse(checkTime, formatter)
        var isInBetween = false
        if (endLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                isInBetween = true
            }
        } else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
            isInBetween = true
        }
        if (isInBetween) {
            println("Is in between!")
            return true
        } else {
            println("Is not in between!")
            return false
        }
    }

    private fun checkBetweenDays(days:String):Boolean{

        val curDay=getCurrentDay()

      return  days.contains(curDay,true)


    }

    suspend fun printForegroundTask(context: Context): String? {
        var currentApp = "NULL"
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val time = System.currentTimeMillis()
        val appList =
            usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time)
        if (appList != null && appList.size > 0) {
            val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
            for (usageStats in appList) {
                mySortedMap[usageStats.lastTimeUsed] = usageStats
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                currentApp = mySortedMap[mySortedMap.lastKey()]!!.packageName
                //    usageStats=mySortedMap[mySortedMap.lastKey()]!!
            }
        }

        //      Log.e("adapter", "Current App in foreground is: $currentApp")
        return currentApp
    }

    private fun getCurrentDate():String{
        var   calendar = Calendar. getInstance();
        var  dateFormat =  SimpleDateFormat("dd/MM/yyyy")
        var   date = dateFormat.format(calendar.getTime())
        return date
    }

    private suspend fun getUsedTimeByPackName(pakageName:String){
        val date=getCurrentDate()

     val   timeRange = longArrayOf(
            java.lang.Long.valueOf(datetime.daystart(date, "dd/MM/yyyy")).toLong() * 1000,
            java.lang.Long.valueOf(datetime.dayend(date, "dd/MM/yyyy")).toLong() * 1000
        )
        val usm=ProfileApp.APP.context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val usageEvents=usm.queryEvents(timeRange[0],timeRange[1])

        var event=UsageEvents.Event()

        usageEvents.getNextEvent(event)

    }




}