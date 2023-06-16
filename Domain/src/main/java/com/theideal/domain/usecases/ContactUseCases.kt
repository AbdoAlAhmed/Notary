package com.theideal.domain.usecases

import android.util.Log
import com.theideal.data.model.User
import com.theideal.domain.repository.BillClientRepository
import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.repository.UserRepository

class ContactUseCases(
    private val userRepo: UserRepository,
    private val clientRepo: ClientRepository,
) {

    private suspend fun getUserInfo(): User {
        return try {
            userRepo.getUserInfo()!!
        } catch (e: Exception) {
            User()
        }

    }


    suspend fun checkUserInfoToCreateContact(): String {
        return try {
            val userInfo = getUserInfo()
            if (userInfo.companyId.isNotEmpty() && userInfo.name.isNotEmpty()) {
                "Success"
            } else if (userInfo.companyId.isEmpty()) {
                "NoCompany"
            } else if (userInfo.name.isEmpty()) {
                "NoUser"
            } else {
                "Something went wrong1"
            }
        } catch (e: Exception) {
            "Something went wrong $e"
        }
    }


    suspend fun checkTheNumberToCreateContact(phone: String): Boolean {
        return clientRepo.clientPhoneExists(phone)
    }
}