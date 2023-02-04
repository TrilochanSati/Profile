package com.windapp.profile.data

import com.windapp.profile.data.entities.InstalledApp
import com.windapp.profile.data.entities.SpecificTimeIntervals
import com.windapp.profile.data.entities.UsageLimitDaily
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val dao:ProfileDao
):ProfileRepository {

    // UsageLimit

    override suspend fun insertUsageLimitDaily(usageLimitDaily: UsageLimitDaily) {
        dao.insertUsageLimitDaily(usageLimitDaily)
    }

    override     suspend fun deleteUsageLimitDailyById(id: Int)
    {
    dao.deleteUsageLimitDailyById(id)
    }

    override fun getUsageLimitDaily(): Flow<List<UsageLimitDaily>> {
    return dao.getUsageLimitDaily()
    }

    override suspend fun updateUsageLimitDailyById(
        id: Int,
        blockApp: Boolean,
        blockNotification: Boolean,
        onDays: String,
        timeLimit: Int,
        motiText: String
    ) {
        return dao.updateUsageLimitDailyById(id,blockApp,blockNotification,onDays,timeLimit,motiText)
    }

    override suspend fun getUsageLimitDailyById(id: Int): UsageLimitDaily {
        return dao.getUsageLimitDailyById(id)
    }

    override suspend fun getUsageLimitDailyByPackageName(packageName: String): List<UsageLimitDaily> {
        return dao.getUsageLimitDailyByPackageName(packageName)
    }

    override fun getFlowUsageLimitDailyByPackageName(packageName: String): Flow<List<UsageLimitDaily>> {
        return dao.getFlowUsageLimitDailyByPackageName(packageName)
    }

    // Specific Time Intervals


    override suspend fun insertSpecificTimeInterval(specificTimeIntervals: SpecificTimeIntervals) {
        dao.insertSpecificTimeInterval(specificTimeIntervals)
    }

    override suspend fun deleteSpecificTimeIntervalById(id: Int) {
        dao.deleteSpecificTimeIntervalById(id)
    }

    override fun getAllUsageSpecificTimeInterval(): Flow<List<SpecificTimeIntervals>> {
        return dao.getAllUsageSpecificTimeInterval()
    }

    override suspend fun getSpecificTimeIntervalById(id: Int): SpecificTimeIntervals {
        return dao.getSpecificTimeIntervalById(id)
    }

    override suspend fun getSpecificTimeIntervalByPackageName(packageName: String): List<SpecificTimeIntervals> {
        return dao.getSpecificTimeIntervalByPackageName(packageName)
    }

    override fun getFlowSpecificTimeIntervalByPackageName(packageName: String): Flow<List<SpecificTimeIntervals>> {
        return dao.getFlowSpecificTimeIntervalByPackageName(packageName)
    }

    override  suspend  fun updateSpecificTimeIntervalById(id:Int, blockApp:Boolean, blockNotification:Boolean, onDays:String, timeStart:String,timeEnd:String, motiText:String)
   {
      dao.updateSpecificTimeIntervalById(id,blockApp,blockNotification,onDays,timeStart,timeEnd,motiText)
    }



// Installed App

    override suspend fun insertApplicationName(installedApp: InstalledApp) {
        dao.insertApplicationName(installedApp)
    }

    override suspend fun findApplicationName(name: String): List<InstalledApp> {
        return dao.findApplicationName(name)
    }

    override suspend fun applicationNameExits(name: String): Boolean {
        return dao.applicationNameExits(name)
    }

    override suspend fun enableSelected(packName: String, flag: Boolean) {
        dao.enableSelected(packName,flag)
    }

    override suspend fun isSelected(packName: String): Boolean {
        return dao.isSelected(packName)
    }

    override suspend fun deleteApplicationName(name: String) {
        dao.deleteApplicationName(name)
    }

    override fun getAllApplicationName(): Flow<List<InstalledApp>> {
        return dao.getAllApplicationName()
        }
}