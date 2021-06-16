package com.drdextersoft.app.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_otras_app.*

class Otras_app : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otras_app)

        val actionBar = supportActionBar
        actionBar!!.title = "OVUDEX" + " " + packageManager.getPackageInfo(packageName, 0).versionName
        actionBar.elevation = 4.0F
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setLogo(R.mipmap.ic_launcher_foreground)
        actionBar.setDisplayUseLogoEnabled(true)

        cerrar.setOnClickListener { finish() }

        boton_dextograma.setOnClickListener{llamar_app("com.drdextersoft.app.myapplication")}
        titulo_dextograma.setOnClickListener{llamar_app("com.drdextersoft.app.myapplication")}
        descripcion_dextograma.setOnClickListener{llamar_app("com.drdextersoft.app.myapplication")}

        boton_nomenclador.setOnClickListener{llamar_app("com.dexterlabs.nomencladorspgo_cmq_cimap")}
        titulo_nomenclador.setOnClickListener{llamar_app("com.dexterlabs.nomencladorspgo_cmq_cimap")}
        descripcion_nomenclador.setOnClickListener{llamar_app("com.dexterlabs.nomencladorspgo_cmq_cimap")}

        boton_ovudex.setOnClickListener{llamar_app("com.dexterlabs.calculodeovulacion")}
        titulo_ovudex.setOnClickListener{llamar_app("com.dexterlabs.calculodeovulacion")}
        descripcion_ovudex.setOnClickListener {llamar_app("com.dexterlabs.calculodeovulacion")}

    }

    fun llamar_app(aplicacion:String){
        this.startActivity(
            Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=$aplicacion")))}

}