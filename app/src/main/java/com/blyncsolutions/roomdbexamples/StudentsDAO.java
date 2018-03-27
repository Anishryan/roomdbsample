package com.blyncsolutions.roomdbexamples;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by laptopzone on 17-01-2018.
 */
@Dao
public interface StudentsDAO {

    @Query("Select * from worldpopulation")
    List<WorldPopulation> getAllUsers();

    @Query("delete FROM worldpopulation where id LIKE  :id")
    int DelByID(int id);

    @Query("Select * from worldpopulation where id LIKE  :id")
    int findById(int id);

    @Insert
    void insertAll(WorldPopulation...worldPopulationb);

    @Query("update worldpopulation set student_name = :student_name,student_email = :student_email,student_age = :student_age,student_blood = :student_blood,student_address = :student_address,student_img = :img where id LIKE  :id")
    int updatebyId(int id,String student_name,String student_email,String student_age,String student_blood,String student_address,byte[] img);





}
