 package com.drdextersoft.app.gestograma

 import android.annotation.SuppressLint
 import android.app.AlertDialog
 import android.app.DatePickerDialog
 import android.content.Context
 import android.content.Intent
 import android.content.pm.PackageManager
 import android.content.res.Configuration
 import android.net.Uri
 import android.os.Build
 import android.os.Bundle
 import android.text.Editable
 import android.text.TextUtils
 import android.text.TextWatcher
 import android.util.DisplayMetrics
 import android.util.Log
 import android.view.Menu
 import android.view.MenuItem
 import android.view.View
 import android.view.ViewGroup
 import android.widget.EditText
 import android.widget.ImageView
 import android.widget.Toast
 import androidx.annotation.RequiresApi
 import androidx.appcompat.app.AppCompatActivity
 import com.drdextersoft.app.myapplication.About
 import com.drdextersoft.app.myapplication.R
 import kotlinx.android.synthetic.main.activity_main.*
 import java.text.SimpleDateFormat
 import java.util.*

 @Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NAME_SHADOWING")
 class MainActivity : AppCompatActivity() {
     @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
     override fun onCreate(savedInstanceState: Bundle?) {

         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

         val actionBar = supportActionBar
         actionBar!!.title = "DEXTOGRAMA"
         actionBar.elevation = 4.0F
         actionBar.setDisplayShowHomeEnabled(true)
         actionBar.setLogo(R.mipmap.logo_foreground)
         actionBar.setDisplayUseLogoEnabled(true)

         mostrarValoracion()

         val metrics = DisplayMetrics()
         windowManager.defaultDisplay.getMetrics(metrics)
         if(metrics.widthPixels<650){
             SEMANAStxt.left=SEMANAStxt.left+5
             SEMANAStxt.text = resources.getString(R.string.semcorto)}
         else if (metrics.widthPixels>1080) {
             val param = pantalla.layoutParams as ViewGroup.MarginLayoutParams
             param.setMargins(50,0,0,0)
             pantalla.layoutParams = param}

         FUMtxt.setOnClickListener(){
             Mostrar_Ayuda(FUMtxt.text as String, "Ayuda de FUM")
         }
         FUM.setOnClickListener { v -> fecha(v) }
         P_Eco.setOnClickListener { v -> fecha(v) }
         ParaFecha.setOnClickListener { v -> fecha(v) }
         Reset.setOnClickListener{
             val refresh = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
             startActivity(refresh)}
         val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.US)
         val currentDate = sdf.format(Date())
         ParaFecha.setText(currentDate)

        FUM.addTextChangedListener(object : TextWatcher {
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 //val Fecha1:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(FUM.text.toString())
                 RB1.isChecked=true
                 calcularSemanas(FUM)
                 CalculoSemanas.text = CalculoSemanas.text
             }
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {}})

         P_Eco.addTextChangedListener(object : TextWatcher {
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 //val Fecha1:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(P_Eco.text.toString())
                 RB2.isChecked=true
                 Semanas.isEnabled=true
                 if (Semanas.text.isEmpty()){
                     Semanas.requestFocus()
                 }
                 else {calcularSemanas(P_Eco)}
                 CalculoSemanas.text = CalculoSemanas.text
             }
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {} })

         Semanas.addTextChangedListener(object : TextWatcher {
             @SuppressLint("SetTextI18n")
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if (Semanas.length()>0){
                if (Semanas.text.substring(0,1)!=".") {
                     if (P_Eco.text.isNotEmpty()){
                     if (Semanas.text.isNotEmpty()){
                         if ((Semanas.text.substring(Semanas.text.length - 1))!="."){
                             if (Semanas.text.indexOf(".",0)>0){
                                    if(Semanas.text.split(".")[1].toInt()>6){
                                        Semanas.setText((Semanas.text.substring(0,Semanas.text.indexOf(".",0)))+".6")
                                        Semanas.setSelection(Semanas.length())
                                    }}

                             calcularSemanas(P_Eco)}}
                     else if (Semanas.text.isEmpty()) {
                         Edad_Actual.text = resources.getString(R.string.Edad_actual)
                         FUM_Corregida.text = ""
                     }}}}
             else {
                     Edad_Actual.text = ""+ resources.getString(R.string.Edad_actual)
                     FUM_Corregida.text = ""
                     Meses.text = ""
                     S38.setText("")
                     S39.setText("")
                     S40.setText("")
                     S41.setText("")
                     S42.setText("")
                 }
             }
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {
             } })

         ParaFecha.addTextChangedListener(object : TextWatcher {
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if (RB1.isChecked){
                     if (FUM.text.isNotEmpty()){calcularSemanas(FUM)}}
                 else{
                     if (P_Eco.text.isNotEmpty()){calcularSemanas(P_Eco)}}
             }
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {} })

         CalculoSemanas.addTextChangedListener(object : TextWatcher {
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if (S38.text.isNotEmpty()){
                     if (CalculoSemanas.text.isNotEmpty()){
                     val fecha2:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(S38.text.toString())
                     val dia: Int
                     val mes: Int
                     val anho: Int
                     val calendario = Calendar.getInstance()
                         calendario.time = fecha2
                         //dia = calendario.get(Calendar.DAY_OF_MONTH)
                         //mes = calendario.get(Calendar.MONTH) + 1
                         //anho = calendario.get(Calendar.YEAR)
                         calendario.add(Calendar.DAY_OF_YEAR,-266)
                         //dia = calendario.get(Calendar.DAY_OF_MONTH)
                         //mes = calendario.get(Calendar.MONTH) + 1
                         //anho = calendario.get(Calendar.YEAR)
                         calendario.add(Calendar.DAY_OF_YEAR,CalculoSemanas.text.toString().toInt()*7)
                     dia = calendario.get(Calendar.DAY_OF_MONTH)
                     mes = calendario.get(Calendar.MONTH) + 1
                     anho = calendario.get(Calendar.YEAR)
                     CumpleSemanas.text=(" " + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho)

                 }
                 else
                         CumpleSemanas.text = ""
                 }}
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {} })
     }

     private fun fecha(view: View) {
         when (view.id) {
             R.id.FUM -> {pedirFecha(FUM)}
             R.id.P_Eco -> {pedirFecha(P_Eco)}
             R.id.ParaFecha -> {pedirFecha(ParaFecha)}}}

     @RequiresApi(Build.VERSION_CODES.N)
     fun radiobuttonClick(view: View) {
         if (RB1.isChecked)
            {if(FUM.text.isNotEmpty()){
               calcularSemanas(FUM)}
            else
            {fecha(FUM)}}
         else
             {if(P_Eco.text.isNotEmpty()){
                 calcularSemanas(P_Eco)}
             else
             {fecha(P_Eco)}}
     }

     @RequiresApi(Build.VERSION_CODES.N)
     fun calcularSemanas (Input:EditText){

         val fecha1:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(Input.text.toString())
         val fecha2:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(ParaFecha.text.toString())
         if (fecha2<fecha1) {
             FUM_Corregida.text = ""
             Edad_Actual.text = resources.getString(R.string.Edad_actual)
             Meses.text = ""
                 S38.setText("")
                 S39.setText("")
                 S40.setText("")
                 S41.setText("")
                 S42.setText("")
             }
                 else{
             var difsemanas:Long=((fecha2.time -fecha1.time)/86400000/7)
             val difdias:Long=((fecha2.time-fecha1.time)/86400000)
             var diferencia:Long=(difdias)-(difsemanas*7)
             when (Input.id) {
                 R.id.P_Eco -> {
                     if (Semanas.text.isNotEmpty()) {
                         val semanaseco = Semanas.text.toString()
                         val entero:Int=semanaseco.substringBeforeLast(".").toInt()
                         val decimal= semanaseco.substringAfterLast(".").toInt()
                         val diaseco: Int
                         if(Semanas.text.toString().indexOf(".")>0)
                         {
                             difsemanas += entero //difsemanas=difsemanas+entero
                             diferencia += decimal //diferencia=diferencia+decimal
                                  diaseco=(entero*7)+decimal
                            if (diferencia>6){
                                difsemanas += 1 //difsemanas=difsemanas+1
                                diferencia -= 7 //diferencia=diferencia-7
                            }
                         }
                         else {
                             difsemanas += entero
                                  diaseco=(entero*7)
                         }
                         formatoSemanas(P_Eco,difsemanas,diferencia,diaseco)
                     }
                  }
                 R.id.FUM ->
                 {formatoSemanas(FUM,difsemanas,diferencia,0)}
             }
     }}

     @SuppressLint("SetTextI18n")
     @RequiresApi(Build.VERSION_CODES.N)
     fun formatoSemanas (Origen:EditText, difsemanas:Long, diferencia:Long, diaseco:Int){
         var dia: Int
         var mes: Int
         var anho: Int
         val calendario = Calendar.getInstance()
         Edad_Actual.text = resources.getString(R.string.Edad_actual) + " " + difsemanas.toString()+","+diferencia.toString() + " " + resources.getString(R.string.semanas)

         val pf:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(Origen.text.toString())
         calendario.time = pf
         var diasmenor= Origen.text.substring(0,2).toInt()
         var mesesmenor= Origen.text.substring(3,5).toInt()
         var anhosmenor= Origen.text.substring(6,10).toInt()

         if (diaseco==0)
            {
                FUM_Corregida.text = ""
            }
         else
            {calendario.add(Calendar.DAY_OF_YEAR,-diaseco)
                dia = calendario.get(Calendar.DAY_OF_MONTH)
                mes = calendario.get(Calendar.MONTH) + 1
                anho = calendario.get(Calendar.YEAR)
                FUM_Corregida.setText(resources.getString(R.string.FUM_Corregida) + " " + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()
                calendario.add(Calendar.DAY_OF_YEAR,+diaseco)
                diasmenor= dia
                mesesmenor= mes
                anhosmenor= anho
            }

         val diasmayor= ParaFecha.text.substring(0,2).toInt()
         val mesesmayor= ParaFecha.text.substring(3,5).toInt()
         var anhosmayor= ParaFecha.text.substring(6,10).toInt()

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
             val fechamenor:Date=SimpleDateFormat("dd/MM/yyyy",Locale.US).parse("$diasmayor/$mesmenor/$anhosmayor")
             val fechamayor:Date=SimpleDateFormat("dd/MM/yyyy",Locale.US).parse("$diasmenor/$mesesmenor/$anhosmenor")
             dias1= ((fechamenor.time-fechamayor.time)/86400000).toInt()
         }

         val textomes:String;val textodias: String
         textomes = if (meses1>1){resources.getString(R.string.meses)}else{resources.getString(R.string.mes)}
         textodias = if (dias1>1){resources.getString(R.string.dias)} else{resources.getString(R.string.dia)}
         Meses.text = "$meses1 $textomes $dias1 $textodias"

         calendario.add(Calendar.DAY_OF_YEAR,(37 * 7)-diaseco)
         for (semanas in 1..5) {
             calendario.add(Calendar.DAY_OF_YEAR,7)
             dia = calendario.get(Calendar.DAY_OF_MONTH)
             mes = calendario.get(Calendar.MONTH) + 1
             anho = calendario.get(Calendar.YEAR)
             when (semanas) {
                 1 -> {S38.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
                 2 -> {S39.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
                 3 -> {S40.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
                 4 -> {S41.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
                 else -> {S42.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
             }
         }
     }

     @SuppressLint("SetTextI18n")
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
                 val miFecha:Date = SimpleDateFormat("dd/MM/yyyy",Locale.US).parse(input.text.toString())
                 c.time = miFecha
                 year = c.get(Calendar.YEAR)
                 month = c.get(Calendar.MONTH)
                 day = c.get(Calendar.DAY_OF_MONTH)
             }

             //Toast.makeText(input.context, Nombre, Toast.LENGTH_SHORT).show()

         val dpd = DatePickerDialog(input.context, DatePickerDialog.OnDateSetListener {
                 _, year, month, day ->
                 val month=month+1
                 input.setText("""${day.twoDigits()}/${month.twoDigits()}/$year""")
             }, year, month, day)
             dpd.show()
     }

     fun Int.twoDigits() = if (this <= 9) "0$this" else this.toString()


     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         // Handle presses on the action bar menu items
         var idioma="";var idiomaName =""
         when (item.itemId) {
             R.id.espanhol -> {
                 idioma="es";idiomaName="Español"
             }
             R.id.ingles -> {
                 idioma="en";idiomaName="English"
             }
             R.id.portugues -> {
                 idioma="pt";idiomaName="Português"
             }
             R.id.italiano -> {
                 idioma="it";idiomaName="Italiano"
             }
             R.id.acercade -> {
                 val acercade = Intent(this, About::class.java)
                 startActivity(acercade)
             }
         }

         if (idioma!="") {
             setLocale(idioma)
             val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
             with (sharedPref.edit()) {
                 putString( "Idioma", idioma)
                 commit()
             }
             Toast.makeText(this, idiomaName,Toast.LENGTH_SHORT).show()
             finish()}
         return super.onOptionsItemSelected(item)
     }

     private fun setLocale(localeName: String) {
         val config = Configuration()
         val locale = Locale(localeName)
         config.locale =locale
         resources.updateConfiguration(config, null)
         val refresh = Intent(this@MainActivity, MainActivity::class.java)
         startActivity(refresh)
         finish()
     }


     override fun onCreateOptionsMenu(menu: Menu): Boolean {
         // Inflate the menu to use in the action bar
         val inflater = menuInflater
         inflater.inflate(R.menu.toolbar_menu, menu)
         return super.onCreateOptionsMenu(menu)
     }

     private fun mostrarValoracion(){
         val preferencias = getSharedPreferences("contador", Context.MODE_PRIVATE)
         //grabarPreferencias(1)
         val contador=preferencias.getInt("contador", 1)
         if (contador<10){
             grabarPreferencias(contador+1)
        }
         else if (contador==10){
             showRateDialog(this)
         }
     }

     private fun grabarPreferencias(valor:Int){
         val preferencias = getSharedPreferences("contador", Context.MODE_PRIVATE)
         val editor = preferencias.edit()
         editor.putInt("contador", valor)
         editor.apply()}

     private fun showRateDialog(context: Context?) {
         val imagen = ImageView  (this)
         imagen.setImageResource(R.drawable.estrellas)
         val builder = android.app.AlertDialog.Builder(context)
             .setTitle(resources.getString(R.string.titulocalifica))
             .setMessage(resources.getString((R.string.subtitulocalifica)))
             .setView(imagen)
             .setPositiveButton(resources.getString(R.string.calificar)
             ) { _, _ ->
                 grabarPreferencias(20)
                 if (context != null) {
                     var link = "market://details?id="
                     try {
                         // play market available
                         context.packageManager
                             .getPackageInfo("com.Android.vending", 0)
                         // not available
                     } catch (e: PackageManager.NameNotFoundException) {
                         e.printStackTrace()
                         // should use browser
                         link = "https://play.google.com/store/apps/details?id="
                     }
                     // starts external action
                     context.startActivity(
                         Intent(
                             Intent.ACTION_VIEW,
                             Uri.parse(link + context.packageName)
                         )
                     )
                 }
             }
             .setNegativeButton(resources.getString(R.string.nunca)
             ) { _, _ -> grabarPreferencias(20)}
             .setNeutralButton(resources.getString(R.string.mastarde)
             ) { _, _ -> grabarPreferencias(1)}
         builder.show()
     }

     private fun Mostrar_Ayuda (Mensaje1:String,Mensaje2:String){
         AlertDialog.Builder(this,R.style.CustomDialogTheme)
             .setTitle(Mensaje1)
             .setMessage(Mensaje2)
             .setPositiveButton("Salir") { dialog, which -> Log.d("MainActivity", "") }
             .show()

     }
 }









