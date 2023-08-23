package com.drdextersoft.app.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.drdextersoft.app.myapplication.BuildConfig.*
import com.drdextersoft.app.myapplication.databinding.ActivityAboutBinding
//import com.drdextersoft.app.myapplication.databinding.ActivityMainBinding

//import kotlinx.android.synthetic.main.activity_about.*

class About : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_about)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        //actionBar!!.title = "DEXTOGRAMA" + " " + getPackageManager().getPackageInfo(packageName, 0).versionName
        actionBar!!.title = "DEXTOGRAMA $VERSION_NAME"
        actionBar.elevation = 4.0F
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setLogo(R.mipmap.logo_foreground)
        actionBar.setDisplayUseLogoEnabled(true)
        binding.button.setOnClickListener { finish() }
        binding.Correo.setOnClickListener {

            val to = "drvictoroviedo@gmail.com"
            val subject = "Gestograma app"
            val message = binding.TextoCorreo.text.toString()

            val mailintent =
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            val addressees = arrayOf(to)
            mailintent.putExtra(Intent.EXTRA_EMAIL, addressees)
            mailintent.putExtra(Intent.EXTRA_SUBJECT, subject)
            mailintent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(mailintent, ""))
        }
        }

    }
