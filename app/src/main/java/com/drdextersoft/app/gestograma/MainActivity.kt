 package com.drdextersoft.app.gestograma

 import android.annotation.SuppressLint
 import android.app.AlertDialog
 import android.app.DatePickerDialog
 import android.content.Context
 import android.content.Intent
 import android.content.pm.PackageManager
 import android.net.Uri
 import android.os.Build
 import android.os.Bundle
 import android.text.Editable
 import android.text.TextUtils
 import android.text.TextWatcher
 import android.util.DisplayMetrics
 import android.view.Menu
 import android.view.MenuItem
 import android.view.View
 import android.view.ViewGroup.MarginLayoutParams
 import android.widget.EditText
 import android.widget.ImageView
 import androidx.annotation.RequiresApi
 import androidx.appcompat.app.AppCompatActivity
 import com.drdextersoft.app.myapplication.About
 import com.drdextersoft.app.myapplication.Otras_app
 import com.drdextersoft.app.myapplication.R
 import com.drdextersoft.app.myapplication.R.*
 import com.drdextersoft.app.myapplication.databinding.ActivityMainBinding
 //import kotlinx.android.synthetic.main.activity_main.*
 import java.text.SimpleDateFormat
 import java.util.*

 lateinit var binding: ActivityMainBinding

 @Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NAME_SHADOWING")
 class MainActivity : AppCompatActivity() {
     @SuppressLint("NewApi")
     @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
     override fun onCreate(savedInstanceState: Bundle?) {

         super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
         //setContentView(layout.activity_main)
         setContentView(binding.root)
         val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
         val currentDate = sdf.format(Date())
         binding.ParaFecha.setText(currentDate)

         val actionBar = supportActionBar
         actionBar!!.title = "DEXTOGRAMA"
         actionBar.elevation = 4.0F
         actionBar.setDisplayShowHomeEnabled(true)
         actionBar.setLogo(mipmap.logo_foreground)
         actionBar.setDisplayUseLogoEnabled(true)

         mostrarValoracion()

         val metrics = DisplayMetrics()
         windowManager.defaultDisplay.getMetrics(metrics)
         if(metrics.widthPixels<650){
             binding.SEMANAStxt.left=binding.SEMANAStxt.left+5
             binding.SEMANAStxt.text = resources.getString(string.semcorto)}
         else if (metrics.widthPixels>1080) {
             val param = binding.pantalla.layoutParams as MarginLayoutParams
             param.setMargins(50, 0, 0, 0)
             binding.pantalla.layoutParams = param}

         binding.FUMtxt.setOnClickListener{ if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.FUMtxt.text as String, resources.getString(string.FUMtxt))}}
         binding.PRIMERAECOtxt.setOnClickListener{ if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.PRIMERAECOtxt.text as String, resources.getString(
                 string.PRIMERAECOtxt
             ))}}
         binding.SEMANAStxt.setOnClickListener{ if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.SEMANAStxt.text as String, resources.getString(string.PRIMERAECOtxt))}}
         binding.CALCULARPARAFECHAtxt.setOnClickListener{ if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.CALCULARPARAFECHAtxt.text as String, resources.getString(string.CALCULARPARAtxt))}}
         binding.FUMCorregida.setOnClickListener{ if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.FUMCorregida.text as String, resources.getString(
                 string.FUMCORREGIDAtxt
             ))}}
         binding.EdadActual.setOnClickListener{if (binding.FUMtxt.tag==1){ this.mostrarayuda(binding.EdadActual.text as String, resources.getString(
                 string.EDADGESTACIONALtxt
             ))}}
         binding.Meses.setOnClickListener { if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.Meses.text as String, resources.getString(string.EDADMESEStxt))}}
         binding.S38L.setOnClickListener {if (binding.FUMtxt.tag==1){ this.mostrarayuda(binding.S38L.text as String, resources.getString(
                 string.S38txt
             ))}}
         binding.S39L.setOnClickListener{ if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.S39L.text as String,resources.getString(string.S39txt))}}
         binding.S40L.setOnClickListener{if (binding.FUMtxt.tag==1){ this.mostrarayuda(binding.S40L.text as String,resources.getString(string.S40txt))}}
         binding.S41L.setOnClickListener{ if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.S41L.text as String,resources.getString(string.S41txt))}}
         binding.S42L.setOnClickListener{ if (binding.FUMtxt.tag==1){this.mostrarayuda(binding.S42L.text as String,resources.getString(string.S42txt))}}
         binding.Cumple.setOnClickListener{if (binding.FUMtxt.tag==1){if (binding.FUMtxt.tag==1){ this.mostrarayuda(binding.Cumple.text as String, resources.getString(string.CUMPLE))}}}

         binding.RB1.setOnClickListener {if(binding.FUM.text.isNotEmpty()){calcularSemanas(binding.FUM)} else {fecha(binding.FUM)}}
         binding.RB2.setOnClickListener {if(binding.PEco.text.isNotEmpty()){calcularSemanas(binding.PEco)} else {fecha(binding.PEco)}}

         binding.Guardar.setOnClickListener{
            if (!binding.Guardar.isChecked) {
                grabarPreferenciastxt("fumval","")
                grabarPreferenciastxt("pecoval","")
                grabarPreferenciastxt("semval","")
            }
             grabarPreferenciasbool("guardar",binding.Guardar.isChecked)}

         binding.FUM.setOnClickListener { v -> fecha(v) }
         binding.PEco.setOnClickListener { v -> fecha(v) }
         binding.ParaFecha.setOnClickListener { v -> fecha(v) }
         binding.Reset.setOnClickListener{
             grabarPreferenciastxt("fumval","")
             grabarPreferenciastxt("pecoval","")
             grabarPreferenciastxt("semval","")
             val refresh = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
             startActivity(refresh)
         }

         binding.FUM.addTextChangedListener(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //val Fecha1:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(FUM.text.toString())
                grabarPreferenciastxt("fumval",binding.FUM.text.toString())
                binding.RB1.isChecked = true
                calcularSemanas(binding.FUM)
                binding.CalculoSemanas.text = binding.CalculoSemanas.text
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable) {}
        })

         binding.PEco.addTextChangedListener(object : TextWatcher {
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 //val Fecha1:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(P_Eco.text.toString())
                 grabarPreferenciastxt("pecoval",binding.PEco.text.toString())
                 binding.RB2.isChecked = true
                 binding.Semanas.isEnabled = true
                 if (binding.Semanas.text.isEmpty()) {
                     binding.Semanas.requestFocus()
                 } else {
                     calcularSemanas(binding.PEco)
                 }
                 binding.CalculoSemanas.text = binding.CalculoSemanas.text
             }

             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {}
         })

         binding.Semanas.addTextChangedListener(object : TextWatcher {
             @SuppressLint("SetTextI18n")
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 grabarPreferenciastxt("semval",binding.Semanas.text.toString())

                 if (binding.Semanas.length() > 0) {
                     if (binding.Semanas.text.substring(0, 1) != ".") {
                         if (binding.PEco.text.isNotEmpty()) {
                             if (binding.Semanas.text.isNotEmpty()) {
                                 if ((binding.Semanas.text.substring(binding.Semanas.text.length - 1)) != ".") {
                                     if (binding.Semanas.text.indexOf(".", 0) > 0) {
                                         if (binding.Semanas.text.split(".")[1].toInt() > 6) {
                                             binding.Semanas.setText(
                                                 (binding.Semanas.text.substring(
                                                     0, binding.Semanas.text.indexOf(
                                                         ".",
                                                         0
                                                     )
                                                 )) + ".6"
                                             )
                                             binding.Semanas.setSelection(binding.Semanas.length())
                                         }
                                     }

                                     calcularSemanas(binding.PEco)
                                 }
                             } else if (binding.Semanas.text.isEmpty()) {
                                 binding.EdadActual.text = resources.getString(string.Edad_actual)
                                 binding.FUMCorregida.text = ""
                             }
                         }
                     }
                 } else {
                     binding.EdadActual.text = "" + resources.getString(string.Edad_actual)
                     binding.FUMCorregida.text = ""
                     binding.Meses.text = ""
                     binding.S38.setText("");binding.S39.setText("");binding.S40.setText("");binding.S41.setText("");binding.S42.setText("")
                 }
             }

             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {
             }
         })

         binding.ParaFecha.addTextChangedListener(object : TextWatcher {
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if (binding.RB1.isChecked) {
                     if (binding.FUM.text.isNotEmpty()) {
                         calcularSemanas(binding.FUM)
                     }
                 } else {
                     if (binding.PEco.text.isNotEmpty()) {
                         calcularSemanas(binding.PEco)
                     }
                 }
             }

             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {}
         })

         binding.CalculoSemanas.addTextChangedListener(object : TextWatcher {
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if (binding.S38.text.isNotEmpty()) {
                     if (binding.CalculoSemanas.text.isNotEmpty()) {
                         val fecha2: Date =
                             SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(binding.S38.text.toString())
                         val dia: Int
                         val mes: Int
                         val anho: Int
                         val calendario = Calendar.getInstance()
                         calendario.time = fecha2
                         //dia = calendario.get(Calendar.DAY_OF_MONTH)
                         //mes = calendario.get(Calendar.MONTH) + 1
                         //anho = calendario.get(Calendar.YEAR)
                         calendario.add(Calendar.DAY_OF_YEAR, -266)
                         //dia = calendario.get(Calendar.DAY_OF_MONTH)
                         //mes = calendario.get(Calendar.MONTH) + 1
                         //anho = calendario.get(Calendar.YEAR)
                         calendario.add(
                             Calendar.DAY_OF_YEAR,
                             binding.CalculoSemanas.text.toString().toInt() * 7
                         )
                         dia = calendario.get(Calendar.DAY_OF_MONTH)
                         mes = calendario.get(Calendar.MONTH) + 1
                         anho = calendario.get(Calendar.YEAR)
                         binding.CumpleSemanas.text = (" " + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho)

                     } else
                         binding.CumpleSemanas.text = ""
                 }
             }

             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {}
         })
     }

     private fun fecha(view: View) {
         when (view.id) {
             id.FUM -> {
                 pedirFecha(binding.FUM)
             }
             id.P_Eco -> {
                 pedirFecha(binding.PEco)
             }
             id.ParaFecha -> {
                 pedirFecha(binding.ParaFecha)
             }}}



     @RequiresApi(Build.VERSION_CODES.N)
     fun calcularSemanas(Input: EditText){

         val fecha1:Date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(Input.text.toString())
         val fecha2:Date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(binding.ParaFecha.text.toString())
         if (fecha2<fecha1) {
             binding.FUMCorregida.text = ""
             binding.EdadActual.text = resources.getString(string.Edad_actual)
             binding.Meses.text = ""
             binding.S38.setText("");binding.S39.setText("");binding.S40.setText("");binding.S41.setText("");binding.S42.setText("")
             }
                 else{
             var difsemanas:Long=((fecha2.time -fecha1.time)/86400000/7)
             val difdias:Long=((fecha2.time-fecha1.time)/86400000)
             var diferencia:Long=(difdias)-(difsemanas*7)
             when (Input.id) {
                 id.P_Eco -> {
                     if (binding.Semanas.text.isNotEmpty()) {
                         val semanaseco = binding.Semanas.text.toString()
                         val entero: Int = semanaseco.substringBeforeLast(".").toInt()
                         val decimal = semanaseco.substringAfterLast(".").toInt()
                         val diaseco: Int
                         if (binding.Semanas.text.toString().indexOf(".") > 0) {
                             difsemanas += entero //difsemanas=difsemanas+entero
                             diferencia += decimal //diferencia=diferencia+decimal
                             diaseco = (entero * 7) + decimal
                             if (diferencia > 6) {
                                 difsemanas += 1 //difsemanas=difsemanas+1
                                 diferencia -= 7 //diferencia=diferencia-7
                             }
                         } else {
                             difsemanas += entero
                             diaseco = (entero * 7)
                         }
                         formatoSemanas(binding.PEco, difsemanas, diferencia, diaseco)
                     }
                 }
                 id.FUM -> {
                     formatoSemanas(binding.FUM, difsemanas, diferencia, 0)
                 }
             }
     }}

     @SuppressLint("SetTextI18n")
     @RequiresApi(Build.VERSION_CODES.N)
     fun formatoSemanas(Origen: EditText, difsemanas: Long, diferencia: Long, diaseco: Int){
         var dia: Int
         var mes: Int
         var anho: Int
         val calendario = Calendar.getInstance()
         binding.EdadActual.text = resources.getString(string.Edad_actual) + " " + difsemanas.toString()+","+diferencia.toString() + " " + resources.getString(
             string.semanas
         )

         val pf:Date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(Origen.text.toString())
         calendario.time = pf
         var diasmenor= Origen.text.substring(0, 2).toInt()
         var mesesmenor= Origen.text.substring(3, 5).toInt()
         var anhosmenor= Origen.text.substring(6, 10).toInt()

         if (diaseco==0)
            {
                binding.FUMCorregida.text = ""
            }
         else
            {calendario.add(Calendar.DAY_OF_YEAR, -diaseco)
                dia = calendario.get(Calendar.DAY_OF_MONTH)
                mes = calendario.get(Calendar.MONTH) + 1
                anho = calendario.get(Calendar.YEAR)
                binding.FUMCorregida.setText(resources.getString(string.FUM_Corregida) + " " + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()
                calendario.add(Calendar.DAY_OF_YEAR, +diaseco)
                diasmenor= dia
                mesesmenor= mes
                anhosmenor= anho
            }

         val diasmayor= binding.ParaFecha.text.substring(0, 2).toInt()
         val mesesmayor= binding.ParaFecha.text.substring(3, 5).toInt()
         var anhosmayor= binding.ParaFecha.text.substring(6, 10).toInt()

         var dias1=0
         var meses1 = mesesmayor - mesesmenor
         if (mesesmayor<mesesmenor){
             meses1 += 12
             if (mesesmenor<12) { anhosmayor=anhosmenor}
         }

         if (diasmenor < diasmayor ) {
            dias1=diasmayor-diasmenor
             }
         else if (diasmenor>diasmayor){
             meses1 -= 1
             var mesmenor=mesesmenor
             mesmenor = if (mesesmenor+1>12){
                 mesesmenor-11
             } else {
                 mesmenor+1
             }
             val fechamenor:Date=SimpleDateFormat("dd/MM/yyyy", Locale.US).parse("$diasmayor/$mesmenor/$anhosmayor")
             val fechamayor:Date=SimpleDateFormat("dd/MM/yyyy", Locale.US).parse("$diasmenor/$mesesmenor/$anhosmenor")
             dias1= ((fechamenor.time-fechamayor.time)/86400000).toInt()
         }

         val textomes:String = if (meses1>1){resources.getString(string.meses)}else{resources.getString(string.mes)}
         val textodias: String = if (dias1>1){resources.getString(string.dias)} else{resources.getString(string.dia)}
         binding.Meses.text = "$meses1 $textomes $dias1 $textodias"

         calendario.add(Calendar.DAY_OF_YEAR, (37 * 7) - diaseco)
         for (semanas in 1..5) {
             calendario.add(Calendar.DAY_OF_YEAR, 7)
             dia = calendario.get(Calendar.DAY_OF_MONTH)
             mes = calendario.get(Calendar.MONTH) + 1
             anho = calendario.get(Calendar.YEAR)
             when (semanas) {
                 1 -> {
                     binding.S38.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho)
                         .toString()
                 }
                 2 -> {
                     binding.S39.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho)
                         .toString()
                 }
                 3 -> {
                     binding.S40.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho)
                         .toString()
                 }
                 4 -> {
                     binding.S41.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho)
                         .toString()
                 }
                 else -> {binding.S42.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
             }
         }
     }

     @SuppressLint("SetTextI18n", "SuspiciousIndentation")
     fun pedirFecha(input: EditText) {
             val c = Calendar.getInstance()
             val year :Int
             val month:Int
             val day:Int
             if(TextUtils.isEmpty(input.text)) {
                 year = c.get(Calendar.YEAR)
                 month = c.get(Calendar.MONTH)
                 day = c.get(Calendar.DAY_OF_MONTH)
                 month+1
             } else {
                 val miFecha:Date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(input.text.toString())
                 c.time = miFecha
                 year = c.get(Calendar.YEAR)
                 month = c.get(Calendar.MONTH)
                 day = c.get(Calendar.DAY_OF_MONTH)
             }

             //Toast.makeText(input.context, Nombre, Toast.LENGTH_SHORT).show()

         val dpd = DatePickerDialog(input.context, { _, year, month, day ->
             val month = month + 1
             input.setText("""${day.twoDigits()}/${month.twoDigits()}/$year""")
         }, year, month, day)
         dpd.show()
     }

     fun Int.twoDigits() = if (this <= 9) "0$this" else this.toString()


     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         // Handle presses on the action bar menu items
         //val idioma=""; val idiomaName =""
         when (item.itemId) {
          /*   id.espanhol -> {
                 idioma = "es";idiomaName = "Español"
             }
             id.ingles -> {
                 idioma = "en";idiomaName = "English"
             }
             id.portugues -> {
                 idioma = "pt";idiomaName = "Português"
             }
             id.italiano -> {
                 idioma = "it";idiomaName = "Italiano"
             }
          */
             id.ayuda -> {
                 this.mostrarconfiguracionayuda(
                     resources.getString(string.ayuda1))
             }
             id.otras -> {
                 val otras = Intent(this, Otras_app::class.java)
                 startActivity(otras)
             }
             id.acercade -> {
                 val acercade = Intent(this, About::class.java)
                 startActivity(acercade)
             }
         }

         /*
         if (idioma!="") {
             setLocale(idioma)
             val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
             with(sharedPref.edit()) {
                 putString("Idioma", idioma)
                 commit()
             }
             Toast.makeText(this, idiomaName, Toast.LENGTH_SHORT).show()
             finish()}
         */
         return super.onOptionsItemSelected(item)
     }
/*
     private fun setLocale(localeName: String) {
         val config = Configuration()
         val locale = Locale(localeName)
         config.locale =locale
         resources.updateConfiguration(config, null)
         val refresh = Intent(this@MainActivity, MainActivity::class.java)
         startActivity(refresh)
         finish()
     }
*/

     override fun onCreateOptionsMenu(menu: Menu): Boolean {
         // Inflate the menu to use in the action bar
         val inflater = menuInflater
         inflater.inflate(R.menu.toolbar_menu, menu)
         return super.onCreateOptionsMenu(menu)
     }

     @RequiresApi(Build.VERSION_CODES.N)
     private fun mostrarValoracion() {
         val preferencias = getSharedPreferences("contador", Context.MODE_PRIVATE)


        val contador = preferencias.getInt("contador", 1)
         //grabarPreferencias("detenerpuntuar", 0)
         //FUMtxt.text=contador.toString()
         if (contador < 10) {grabarPreferencias("contador", contador + 1)}
         else{grabarPreferencias("contador", 0)}

         if (contador==5){
             val detenerovudex = getSharedPreferences("detenerovudex", Context.MODE_PRIVATE)
             val detener = detenerovudex.getInt("detenerovudex", 0)
             if (detener==0){showRateDialog("ovudex")}
         } else if (contador == 10) {
             val detenerpuntuar = getSharedPreferences("detenerpuntuar", Context.MODE_PRIVATE)
             val detener = detenerpuntuar.getInt("detenerpuntuar", 0)
             if (detener==0){showRateDialog("puntuar")}
         } else if (contador==20){
             grabarPreferencias("contador", 1)
             grabarPreferencias("detenerpuntuar", 1)
         }
         val ayudaval = getSharedPreferences("ayuda", Context.MODE_PRIVATE)
         //grabarPreferencias("ayuda",1)
         binding.FUMtxt.tag = ayudaval.getInt("ayuda", 1)
         val guardarval=getSharedPreferences("guardar",Context.MODE_PRIVATE)
         binding.Guardar.isChecked=guardarval.getBoolean("guardar",false)
         if(binding.Guardar.isChecked){
         val fumgrabada = getSharedPreferences("fumval", Context.MODE_PRIVATE)
         if (fumgrabada.getString("fumval", "") != "") {
             binding.FUM.setText(fumgrabada.getString("fumval", ""))
             calcularSemanas(binding.FUM)
         }
         val semgrabada = getSharedPreferences("semval", Context.MODE_PRIVATE)
         if (semgrabada.getString("semval", "") != "")
         {binding.Semanas.setText(semgrabada.getString("semval",""))
         if(binding.Semanas.text.isNotEmpty()){binding.Semanas.isEnabled=true}}
         val pecograbada = getSharedPreferences("pecoval", Context.MODE_PRIVATE)
         if (pecograbada.getString("pecoval", "") != "") {
             binding.PEco.setText(pecograbada.getString("pecoval", ""))
             binding.RB2.isChecked=true
         if (binding.Semanas.text.isEmpty()){binding.Semanas.setText("0")
             binding.Semanas.isEnabled=true}
         calcularSemanas(binding.PEco)
     }}
        val notificacion=getSharedPreferences("Notificacion",Context.MODE_PRIVATE)
         if (notificacion.getInt("Notificacion",0)==0)
         {this.mostrarayuda(resources.getString(
             string.Novedades_Titulo),resources.getString(string.Novedades_Texto))
         grabarPreferencias("Notificacion",1)
         }

     }

     private fun grabarPreferencias(campo:String,valor: Int){
         val preferencias = getSharedPreferences(campo, Context.MODE_PRIVATE)
         val editor = preferencias.edit()
         editor.putInt(campo, valor)
         editor.apply()}

     private fun grabarPreferenciastxt(campo:String,valor:String){
         val preferencias = getSharedPreferences(campo, Context.MODE_PRIVATE)
         val editor = preferencias.edit()
         editor.putString(campo, valor)
         editor.apply()}

     private fun grabarPreferenciasbool(campo:String,valor:Boolean){
         val preferencias = getSharedPreferences(campo, Context.MODE_PRIVATE)
         val editor = preferencias.edit()
         editor.putBoolean(campo, valor)
         editor.apply()}

     private fun showRateDialog(noticia:String) {
         val imagen = ImageView(this)
         var titulo=""; var mensaje=""; var botonpositivo=""; var botonnegativo=""; var botonneutral=""
         var aplicacion=""; var detener=""

         when (noticia){
             "puntuar" -> {
                 imagen.setImageResource(drawable.estrellas)
                 titulo=resources.getString(string.titulocalifica)
                 mensaje=resources.getString((string.subtitulocalifica))
                 botonpositivo=resources.getString(string.calificar)
                 botonnegativo=resources.getString(string.nunca)
                 botonneutral=resources.getString(string.mastarde)
                 aplicacion=this.packageName
                 detener="detenerpuntuar"
             }
             "ovudex" -> {
                 imagen.setImageResource(mipmap.ovudex)
                 titulo="Nueva aplicación:\n" + resources.getString(string.ovudex_titulo)
                 mensaje=resources.getString((string.ovudex_descripcion))
                 botonpositivo=resources.getString(string.positivoaplicacion)
                 botonnegativo=resources.getString(string.negativoaplicacion)
                 botonneutral=resources.getString(string.neutroaplicacion)
                 aplicacion="com.dexterlabs.calculodeovulacion"
                 detener="detenerovudex"
             }
         }
          val builder = AlertDialog.Builder(this)
             .setTitle(titulo)
             .setMessage(mensaje)
             .setView(imagen)
             .setPositiveButton(botonpositivo)
              { _, _ ->
                 grabarPreferencias(detener,1)
                  var link = "market://details?id="
                  try {
                      // play market available
                      this.packageManager
                          .getPackageInfo("com.Android.vending", 0)
                      // not available
                  } catch (e: PackageManager.NameNotFoundException) {
                      e.printStackTrace()
                      // should use browser
                      link = "https://play.google.com/store/apps/details?id="
                  }
                  // starts external action
                  this.startActivity(
                      Intent(
                          Intent.ACTION_VIEW,
                          Uri.parse(link + aplicacion)
                      )
                  )
              }
             .setNegativeButton(botonnegativo) {
                     _, _ -> grabarPreferencias(detener,1)}
             .setNeutralButton(botonneutral) { _, _ -> }
         builder.show()
     }

     private fun mostrarayuda(Mensaje1: String, Mensaje2: String){
         AlertDialog.Builder(this, style.CustomDialogTheme)
             .setTitle(Mensaje1)
             .setMessage(Mensaje2)
             //.setPositiveButton("Salir") { dialog, which -> Log.d("MainActivity", "") }
             .show()
     }

     private fun mostrarconfiguracionayuda(Mensaje: String){
         val multiChoiceItems = resources.getStringArray(array.dialog_multi_choice_array)
         var valor:Int; val valorB:Boolean = binding.FUMtxt.tag==1
         val checkedItems = booleanArrayOf(valorB)
         AlertDialog.Builder(this, style.CustomDialogTheme)
             .setTitle(Mensaje)
             .setMultiChoiceItems(
                 multiChoiceItems,
                 checkedItems
             ) { _, _, isChecked ->
                 valor = if (isChecked) {
                     1
                 } else {
                     0
                 }

                 grabarPreferencias("ayuda", valor)
                 binding.FUMtxt.tag = valor
             }
             .show()
     }
 }







