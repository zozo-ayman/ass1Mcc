package com.example.ass1mcc

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.ass1mcc.Adapter.AdapterCon
import com.example.ass1mcc.modle.ContactUser
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var contactName: EditText? = null
    var contactNumber: EditText? = null
    var contactAddress: EditText? = null
    var buttonSave: Button? = null
    var listContact: ListView? = null

    private var progressDialog: ProgressDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactNumber = findViewById(R.id.etNumber)
        contactName = findViewById(R.id.etName)
        contactAddress = findViewById(R.id.etAddress)
        buttonSave = findViewById(R.id.btnSave)
        listContact = findViewById(R.id.listContact)


        listContact!!.visibility = View.GONE

        buttonSave!!.setOnClickListener() {
            showDialog()
            save()
            contactName!!.text.clear()
            contactNumber!!.text.clear()
            contactAddress!!.text.clear()

        }

        getAllContact()

           }

    private fun save() {
        val name: String = contactName!!.text.toString()
        val number: String = contactNumber!!.text.toString()
        val address: String = contactAddress!!.text.toString()

        val datContact: MutableMap<String, Any> = HashMap()
        datContact["contact_name"] = name
        datContact["contact_number"] = number
        datContact["contact_address"] = address

        db.collection("contacts")
                .add(datContact)
                .addOnSuccessListener { reference ->
                    Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG)
                            .show()
                    hiddenDialog()
                     listContact!!.refreshDrawableState()
                    listContact!!.invalidateViews()

                }
                .addOnFailureListener { e -> Log.w("TAG", "Error adding ", e)
                    hiddenDialog()
                }

    }

    @SuppressLint("SetTextI18n")
    private fun getAllContact() {
        var contacts = ArrayList<ContactUser>()

        db.collection(
                "contacts"
        ).get().addOnSuccessListener { snapshot ->
            for (document in snapshot) {
                contacts.add(
                    ContactUser(
                        "${document.getString("contact_name")}",
                        "${document.getString("contact_number")}",
                        "${document.getString("contact_address")}"
                    )
                )
                val contactAdapter =
                    AdapterCon(this, contacts)
                listContact!!.adapter = contactAdapter
            }

        }
        listContact!!.visibility = View.VISIBLE

    }


    private fun showDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Adding contact ...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    private fun hiddenDialog() {
        if (progressDialog!!.isShowing) {
            progressDialog!!.dismiss()

        }
    }
}