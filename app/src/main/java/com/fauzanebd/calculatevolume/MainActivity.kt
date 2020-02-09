package com.fauzanebd.calculatevolume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtWidth: EditText
    private lateinit var edtHeight: EditText
    private lateinit var edtLength: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView

    companion object{
        private const val STATE_RESULT = "state_result"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBar("Aplikasi Hitung Volume")

        edtWidth = findViewById(R.id.edt_width)
        edtHeight = findViewById(R.id.edt_height)
        edtLength = findViewById(R.id.edt_length)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)
        tvResult.visibility = View.GONE

        btnCalculate.setOnClickListener(this)

        if(savedInstanceState != null){
            val result = savedInstanceState.getString(STATE_RESULT) as String
            tvResult.text = result
        }
    }

    override fun onClick(v: View){
        if(v.id == R.id.btn_calculate){
            val inputLength = edtLength.text.toString().trim()
            val inputWidth = edtWidth.text.toString().trim()
            val inputHeight = edtHeight.text.toString().trim()

            var isEmptyFields = false
            var isInvalidDouble = false

            if(inputLength.isEmpty()){
                isEmptyFields = true
                edtLength.error = "Kolom panjang harus diisi"
            }

            if(inputWidth.isEmpty()){
                isEmptyFields = true
                edtWidth.error = "Kolom lebar harus diisi"
            }

            if(inputHeight.isEmpty()){
                isEmptyFields = true
                edtHeight.error = "Kolom tinggi harus diisi"
            }

            val length = inputLength.keDouble()
            val width = inputWidth.keDouble()
            val height = inputHeight.keDouble()

            when {
                length == null -> {
                    isInvalidDouble = true
                    edtLength.error = "Field ini harus berupa nomor yang valid"
                }

                width == null -> {
                    isInvalidDouble = true
                    edtWidth.error = "Field ini harus berupa nomor yang valid"
                }

                height == null -> {
                    isInvalidDouble = true
                    edtHeight.error = "Field ini harus berupa nomor yang valid"
                }
            }

            if(!isEmptyFields && !isInvalidDouble){
                val volume = length as Double * width as Double * height as Double
                tvResult.text = volume.toString()
                tvResult.visibility=View.VISIBLE
            }
        }
    }

    private fun String.keDouble(): Double?{
        return try{
            this.toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }

    private fun setActionBar(title: String){
        if(supportActionBar != null){
            (supportActionBar as ActionBar).title=title
        }
    }

}

