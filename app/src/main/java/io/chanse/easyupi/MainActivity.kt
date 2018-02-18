package io.chanse.easyupi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var payeeAddress = "susrisaswati@okhdfcbank"
    private var payeeName = "Susri Saswati"
    private var transactionNote = "Test for Deeplinking"
    private var amount = "1"
    private var currencyUnit = "INR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onPayment.setOnClickListener {
            val uri = Uri.parse(
                "upi://pay?pa=" + payeeAddress + "&pn=" + payeeName + "&tn=" + transactionNote +
                        "&am=" + amount + "&cu=" + currencyUnit
            )
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.let {
            val status = it.getStringExtra("Status") ?: ""
            when(status) {
                "SUCCESS" -> onSuccess()
                "SUBMITTED" -> onSubmitted()
                "FAILURE" -> onFailure()
                "" -> {
                    val res = it.getStringExtra("response")
                    val search = "SUCCESS"
                    if (res.toLowerCase().contains(search.toLowerCase())) {
                        onSuccess()
                    } else {
                        onFailure()
                    }
                }
            }
        }
    }

    private fun onSuccess() {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
    }

    private fun onSubmitted() {
        Toast.makeText(this, "Payment Submitted", Toast.LENGTH_SHORT).show()
    }

    private fun onFailure() {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }
}
