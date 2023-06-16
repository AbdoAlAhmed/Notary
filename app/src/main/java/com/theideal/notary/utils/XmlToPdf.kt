package com.theideal.notary.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import com.theideal.data.model.BillContact
import com.theideal.notary.R


fun pdf(context: Context, view: View, bill: BillContact) {
    PdfGenerator
        .getBuilder()
        .setContext(context as ComponentActivity)
        .fromViewSource()
        .fromViewList(mutableListOf(view))
        .setFileName(bill.billId)
        .setFolderNameOrPath(bill.userId + "/" + bill.contactId)
        .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE)
        .build(object : PdfGeneratorListener() {
            override fun onFailure(failureResponse: FailureResponse) {
                super.onFailure(failureResponse)
                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
            }

            override fun showLog(log: String) {
                super.showLog(log)
                Toast.makeText(context, log, Toast.LENGTH_SHORT).show()
            }

            override fun onStartPDFGeneration() {
                Toast.makeText(context, "Start", Toast.LENGTH_SHORT).show()
            }

            override fun onFinishPDFGeneration() {
                Toast.makeText(context, "Finish", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(response: SuccessResponse?) {
                super.onSuccess(response)
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }
        })
}