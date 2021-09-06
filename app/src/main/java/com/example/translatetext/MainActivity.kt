package com.example.translatetext

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {
    lateinit var btnTranslateNow: Button
    lateinit var etOriginalLanguage:EditText
    lateinit var tvResult: TextView
    var originalText:String=""
    lateinit var englishHindiTranslator: Translator
    lateinit var pDialog: SweetAlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTranslateNow=findViewById(R.id.btnTranslateNow)
        etOriginalLanguage=findViewById(R.id.etOriginalLanguage)
        tvResult=findViewById(R.id.tvResult)

        setUpProgressDialog()

        btnTranslateNow.setOnClickListener{
            originalText=etOriginalLanguage.text.toString()
            prepareTranslateModel();
    }
    }

    private fun setUpProgressDialog() {
        pDialog = SweetAlertDialog(this@MainActivity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86");
        pDialog.titleText = "Loading";
        pDialog.setCancelable(false);

    }

    private fun prepareTranslateModel() {
        val options:TranslatorOptions=TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()

        englishHindiTranslator= Translation.getClient(options)

        //Downloading model

        pDialog.titleText="Model Downloading...."
        pDialog.show()

        englishHindiTranslator.downloadModelIfNeeded().addOnSuccessListener{
         //Now Model downloaded Run Task
            pDialog.dismissWithAnimation()
            translateText()

        }.addOnFailureListener{
            pDialog.dismissWithAnimation()
            tvResult.text="Error ${it.message}"

































        }
    }

    private fun translateText(){
        pDialog.titleText="Translate Text"
        pDialog.show()
        englishHindiTranslator.translate(originalText).addOnSuccessListener{
            pDialog.dismissWithAnimation()
            tvResult.text=it
        }.addOnFailureListener {
            pDialog.dismissWithAnimation()
            tvResult.text="Error ${it.message}"
        }
    }

}