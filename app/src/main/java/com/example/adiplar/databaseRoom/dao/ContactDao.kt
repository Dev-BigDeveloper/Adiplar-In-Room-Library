package com.example.adiplar.databaseRoom.dao

import androidx.room.*
import com.example.adiplar.databaseRoom.modelsRoom.Contact

@Dao
interface ContactDao {
    @Insert
    fun addContact(contact: Contact)

    @Insert
    fun addContactsList(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContactsListReplace(contact: Contact)

    @Update
    fun editContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("select * from contact")
    fun getAllContacts(): List<Contact>

    @Query("select * from contact where id = :id")
    fun getContactById(id:Int): Contact

    @Query("select * from contact where name like '%' || :name || '%'")
    fun getContactFilter(name:String):List<Contact>

}