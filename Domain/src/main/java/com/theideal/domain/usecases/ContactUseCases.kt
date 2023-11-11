package com.theideal.domain.usecases

import com.theideal.domain.repository.ClientRepository
import com.theideal.domain.repository.UserRepository

class ContactUseCases(
    private val userRepo: UserRepository,
    private val clientRepo: ClientRepository,
) {

     suspend fun getUserInfo() =
        userRepo.getUserInfo()!!


    suspend fun checkUserInfoToCreateContact(): String {
        return try {
            val userInfo = getUserInfo()
            if (userInfo.companyId.isNotEmpty() && userInfo.name.isNotEmpty()) {
                "Success"
            } else if (userInfo.name.isEmpty()) {
                "NoUser"
            } else if (userInfo.companyId.isEmpty()) {
                "NoCompany"
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

     fun logout() {
        userRepo.logout()
    }
}