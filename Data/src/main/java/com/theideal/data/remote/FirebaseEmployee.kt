package com.theideal.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.theideal.data.model.Contact

class FirebaseEmployee {
    private val db = FirebaseFirestore.getInstance()
    private val employeeRef = db.collection("Employee")

    fun createEmployee(employee: Contact) {
        employeeRef.add(employee)
    }

    fun getEmployee(employeeId: Contact) {
        employeeRef.document(employeeId.contactId).get()
    }

    fun updateEmployee(employee: Contact) {
        employeeRef.document(employee.contactId).set(employee)
    }

    fun deleteEmployee(employee: Contact) {
        employeeRef.document(employee.contactId).delete()
    }
}