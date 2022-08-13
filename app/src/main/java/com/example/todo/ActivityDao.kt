package com.example.todo

import androidx.room.*

@Dao
interface ActivityDao {
    @Query("SELECT * FROM Activity")
    fun getAll(): List<Activity>

    @Query("SELECT * FROM Activity WHERE date IN (:date_c)")
    fun getBasedOnDate(date_c: String): List<Activity>

    @Query("SELECT * FROM Activity WHERE date IN (:date_c) ORDER BY hour")
    fun getBasedOnDateOrdered(date_c: String): List<Activity>

    @Query("SELECT * FROM Activity WHERE activityID IN (:activityIds)")
    fun loadAllByIds(activityIds: IntArray): List<Activity>

    @Query("SELECT * FROM Activity WHERE activityID IN (:activityId)")
    fun loadById(activityId: Int): Activity

    @Query("UPDATE Activity SET title = :a_title, content= :a_content, hour= :a_hour, date= :a_date WHERE activityID = :a_id")
    fun updateActivity(a_id: Int,a_title: String, a_content: String, a_hour: String, a_date: String)

    @Query("UPDATE Activity SET title = :a_title WHERE activityID = :a_id")
    fun updateTitle(a_id: Int,a_title: String)

    @Query("UPDATE Activity SET content = :a_content WHERE activityID = :a_id")
    fun updateContent(a_id: Int,a_content: String)

    @Query("UPDATE Activity SET hour = :a_hour WHERE activityID = :a_id")
    fun updateHour(a_id: Int,a_hour: String)

    @Query("UPDATE Activity SET date = :a_date WHERE activityID = :a_id")
    fun updateDate(a_id: Int,a_date: String)

    /*
    @Query("UPDATE Activity SET title = :title_v WHERE id = :tid")
    int updateTour(long tid, String end_address);
    fun updateActivity(Int a_id,String a_title, String a_content, String a_hour, String a_date)
    */
    @Insert
    fun insertAll(vararg users: Activity)

    @Delete
    fun delete(user: Activity)

    @Update
    fun updateActivity(user: Activity)




}

