package com.drdextersoft.app.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drdextersoft.app.myapplication.BuildConfig.*
import com.drdextersoft.app.myapplication.databinding.ActivityAboutBinding
import com.drdextersoft.app.myapplication.databinding.ActivityMainBinding
import com.drdextersoft.app.myapplication.databinding.ActivityOtrasAppBinding

//import kotlinx.android.synthetic.main.activity_otras_app.*


class Otras_app : AppCompatActivity() {
    private lateinit var binding: ActivityOtrasAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtrasAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
 //       setContentView(R.layout.activity_otras_app)

        val actionBar = supportActionBar
        actionBar!!.title = "OVUDEX $VERSION_NAME"
        actionBar.elevation = 4.0F
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setLogo(R.mipmap.ic_launcher_foreground)
        actionBar.setDisplayUseLogoEnabled(true)

        binding.cerrar.setOnClickListener { finish() }

        binding.botonDextograma.setOnClickListener{llamar_app("com.drdextersoft.app.myapplication")}
        binding.tituloDextograma.setOnClickListener{llamar_app("com.drdextersoft.app.myapplication")}
        binding.descripcionDextograma.setOnClickListener{llamar_app("com.drdextersoft.app.myapplication")}

        binding.botonNomenclador.setOnClickListener{llamar_app("com.dexterlabs.nomencladorspgo_cmq_cimap")}
        binding.tituloNomenclador.setOnClickListener{llamar_app("com.dexterlabs.nomencladorspgo_cmq_cimap")}
        binding.descripcionNomenclador.setOnClickListener{llamar_app("com.dexterlabs.nomencladorspgo_cmq_cimap")}

        binding.botonOvudex.setOnClickListener{llamar_app("com.dexterlabs.calculodeovulacion")}
        binding.tituloOvudex.setOnClickListener{llamar_app("com.dexterlabs.calculodeovulacion")}
        binding.descripcionOvudex.setOnClickListener {llamar_app("com.dexterlabs.calculodeovulacion")}

    }

    private fun llamar_app(aplicacion:String){
        this.startActivity(
            Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=$aplicacion")))}

}