 package com.drdextersoft.app.Gestograma

 import android.app.DatePickerDialog
 import android.content.Context
 import android.content.Intent
 import android.os.Build
 import android.os.Bundle
 import android.text.Editable
 import android.text.TextUtils
 import android.text.TextWatcher
 import android.util.DisplayMetrics
 import android.view.Menu
 import android.view.MenuItem
 import android.view.View
 import android.view.ViewGroup
 import android.view.inputmethod.InputMethodManager
 import android.widget.EditText
 import androidx.annotation.RequiresApi
 import androidx.appcompat.app.AppCompatActivity
 import com.drdextersoft.app.myapplication.About
 import com.drdextersoft.app.myapplication.R
 import kotlinx.android.synthetic.main.activity_main.*
 import java.text.SimpleDateFormat
 import java.util.*

 @Suppress("DEPRECATION")
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

         val metrics = DisplayMetrics()
         windowManager.defaultDisplay.getMetrics(metrics)
         if(metrics.widthPixels<650){
             TextView12.left=TextView12.left+5
             TextView12.setText(""+getResources().getString(R.string.semcorto) )}
         else if (metrics.widthPixels>1080) {
             val param = pantalla.layoutParams as ViewGroup.MarginLayoutParams
             param.setMargins(50,0,0,0)
             pantalla.layoutParams = param}

         FUM.setOnClickListener { v -> Fecha(v) }
         P_Eco.setOnClickListener { v -> Fecha(v) }
         ParaFecha.setOnClickListener { v -> Fecha(v) }
         Reset.setOnClickListener{
             val refresh = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
             startActivity(refresh)}
         val sdf = SimpleDateFormat("dd/MM/yyyy")
         val currentDate = sdf.format(Date())
         ParaFecha.setText(currentDate)

        FUM.addTextChangedListener(object : TextWatcher {
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 val Fecha1:Date = SimpleDateFormat("dd/MM/yyyy").parse(FUM.text.toString())
                 RB1.isChecked=true
                 CalcularSemanas(FUM,Fecha1)
                 CalculoSemanas.setText(CalculoSemanas.text)
             }
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {}})

         P_Eco.addTextChangedListener(object : TextWatcher {
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 val Fecha1:Date = SimpleDateFormat("dd/MM/yyyy").parse(P_Eco.text.toString())
                 RB2.isChecked=true
                 Semanas.isEnabled=true
                 if (Semanas.text.isEmpty()){
                     Semanas.requestFocus()
                 }
                 else {CalcularSemanas(P_Eco,Fecha1)}
                 CalculoSemanas.setText(CalculoSemanas.text)
             }
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {} })

         Semanas.addTextChangedListener(object : TextWatcher {
             @RequiresApi(Build.VERSION_CODES.N)
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if (Semanas.length()>0){
                if (Semanas.text.substring(0,1)!=".") {
                     if (P_Eco.text.isEmpty()){}
                     else if (Semanas.text.isNotEmpty()){
                         val Fecha2:Date = SimpleDateFormat("dd/MM/yyyy").parse(P_Eco.text.toString())
                         if ((Semanas.text.substring(Semanas.text.length - 1))!="."){
                             if (Semanas.text.indexOf(".",0)>0){
                                    if(Semanas.text.split(".")[1].toInt()>6){
                                        Semanas.setText((Semanas.text.substring(0,Semanas.text.indexOf(".",0)))+".6")
                                        Semanas.setSelection(Semanas.length())
                                    }}

                             CalcularSemanas(P_Eco,Fecha2)}}
                     else if (Semanas.text.isEmpty()) {
                         Edad_Actual.setText(""+getResources().getString(R.string.Edad_actual) )
                         FUM_Corregida.setText("")
                     }}}
             else {
                    Edad_Actual.setText(""+getResources().getString(R.string.Edad_actual))
                     FUM_Corregida.setText("")
                     Meses.setText("")
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
                 val Fecha1:Date = SimpleDateFormat("dd/MM/yyyy").parse(ParaFecha.text.toString())
                 if (RB1.isChecked){
                     if (FUM.text.isNotEmpty()){CalcularSemanas(FUM,Fecha1)}}
                 else{
                     if (P_Eco.text.isNotEmpty()){CalcularSemanas(P_Eco,Fecha1)}}
             }
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {} })

         CalculoSemanas.addTextChangedListener(object : TextWatcher {
             override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 if (S38.text.isNotEmpty()){
                     if (CalculoSemanas.text.isNotEmpty()){
                     val Fecha2:Date = SimpleDateFormat("dd/MM/yyyy").parse(S38.text.toString())
                     var dia: Int;
                     var mes: Int;
                     var anho: Int
                     val calendario = Calendar.getInstance()
                     calendario.setTime(Fecha2)
                         dia = calendario.get(Calendar.DAY_OF_MONTH)
                         mes = calendario.get(Calendar.MONTH) + 1
                         anho = calendario.get(Calendar.YEAR)
                         calendario.add(Calendar.DAY_OF_YEAR,-266);
                         dia = calendario.get(Calendar.DAY_OF_MONTH)
                         mes = calendario.get(Calendar.MONTH) + 1
                         anho = calendario.get(Calendar.YEAR)
                         calendario.add(Calendar.DAY_OF_YEAR,CalculoSemanas.text.toString().toInt()*7);
                     dia = calendario.get(Calendar.DAY_OF_MONTH)
                     mes = calendario.get(Calendar.MONTH) + 1
                     anho = calendario.get(Calendar.YEAR)
                     CumpleSemanas.setText(" " + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()

                 }
                 else
                    CumpleSemanas.setText("")
                 }}
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
             override fun afterTextChanged(p0: Editable) {} })
     }

     private fun Fecha(view: View) {
         when (view?.id) {
             R.id.FUM -> {PedirFecha(FUM, ParaFecha, "Ingresar fecha de última menstruación")}
             R.id.P_Eco -> {PedirFecha(P_Eco, ParaFecha,"Ingresar fecha de Primera ecografía")}
             R.id.ParaFecha -> {PedirFecha(ParaFecha, ParaFecha,"Calcular para fecha:")}}}

     @RequiresApi(Build.VERSION_CODES.N)
     fun RadioButton_click(view: View) {
         if (RB1.isChecked)
            {if(FUM.text.isNotEmpty()){
               val Fecha2:Date = SimpleDateFormat("dd/MM/yyyy").parse(FUM.text.toString())
               CalcularSemanas(FUM,Fecha2)}
            else
            {Fecha(FUM)}}
         else
             {if(P_Eco.text.isNotEmpty()){
                 val Fecha2:Date = SimpleDateFormat("dd/MM/yyyy").parse(P_Eco.text.toString())
                 CalcularSemanas(P_Eco,Fecha2)}
             else
             {Fecha(P_Eco)}}
     }

     @RequiresApi(Build.VERSION_CODES.N)
     fun CalcularSemanas (Input:EditText, Fecha1:Date){

         val Fecha2:Date = SimpleDateFormat("dd/MM/yyyy").parse(ParaFecha.text.toString())
             if (Fecha2<Fecha1) {
                 FUM_Corregida.setText("")
                 Edad_Actual.setText(""+getResources().getString(R.string.Edad_actual))
                 Meses.setText("")
                 S38.setText("")
                 S39.setText("")
                 S40.setText("")
                 S41.setText("")
                 S42.setText("")
             }
                 else{
             var difsemanas:Long=((Fecha2.getTime()-Fecha1.getTime())/86400000/7)
             val difdias:Long=((Fecha2.getTime()-Fecha1.getTime())/86400000)
             var diferencia:Long=(difdias)-(difsemanas*7)
             when (Input?.id) {
                 R.id.P_Eco -> {
                     if (Semanas.text.isNotEmpty()) {
                         val semanaseco = Semanas.text.toString()
                         val entero:Int=semanaseco.substringBeforeLast(".").toInt()
                         val decimal= semanaseco.substringAfterLast(".").toInt()
                         var diaseco=0
                         if(Semanas.text.toString().indexOf(".")>0)
                         {
                                  difsemanas=difsemanas+entero
                                  diferencia=diferencia+decimal
                                  diaseco=(entero*7)+decimal
                            if (diferencia>6){
                                difsemanas=difsemanas+1
                                diferencia=diferencia-7
                            }
                         }
                         else {
                                  difsemanas=difsemanas+entero
                                  diaseco=(entero*7)
                         }
                         Formato_Semanas(P_Eco,difsemanas,diferencia,diaseco)
                     }
                  }
                 R.id.FUM ->
                 {Formato_Semanas(FUM,difsemanas,diferencia,0)}
             }
     }}

     @RequiresApi(Build.VERSION_CODES.N)
     fun Formato_Semanas (Origen:EditText, difsemanas:Long, diferencia:Long, diaseco:Int){
        var dia: Int;
         var mes: Int;
         var anho: Int
         val calendario = Calendar.getInstance()
         Edad_Actual.setText(""+getResources().getString(R.string.Edad_actual) + " " + difsemanas.toString()+","+diferencia.toString() + " semanas")

         val PF:Date = SimpleDateFormat("dd/MM/yyyy").parse(Origen.text.toString())
         calendario.setTime(PF)
         var diasmenor= Origen.text.substring(0,2).toInt()
         var mesesmenor= Origen.text.substring(3,5).toInt()
         var anhosmenor= Origen.text.substring(6,10).toInt()

         if (diaseco==0)
            {FUM_Corregida.setText("")}
         else
            {calendario.add(Calendar.DAY_OF_YEAR,-diaseco)
                dia = calendario.get(Calendar.DAY_OF_MONTH)
                mes = calendario.get(Calendar.MONTH) + 1
                anho = calendario.get(Calendar.YEAR)
                FUM_Corregida.setText(""+getResources().getString(R.string.FUM_Corregida) + " " + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()
                calendario.add(Calendar.DAY_OF_YEAR,+diaseco)
                diasmenor= dia
                mesesmenor= mes
                anhosmenor= anho
            }

         var diasmayor= ParaFecha.text.substring(0,2).toInt()
         var mesesmayor= ParaFecha.text.substring(3,5).toInt()
         var anhosmayor= ParaFecha.text.substring(6,10).toInt()

         var dias1:Int=0
         var meses1 = mesesmayor - mesesmenor
         if (mesesmayor<mesesmenor){
             meses1=meses1+12
             if (mesesmenor<12) { anhosmayor=anhosmenor}
         }

         if (diasmenor < diasmayor ) {
            dias1=diasmayor-diasmenor
             }
         else if (diasmenor>diasmayor){
             meses1=meses1-1
             var mesmenor=mesesmenor
             if (mesesmenor+1>12){
                 mesmenor=mesesmenor-11}
             else
             {mesmenor=mesmenor+1}
             val fechamenor:Date=SimpleDateFormat("dd/MM/yyyy").parse(""+diasmayor + "/"+mesmenor+"/"+anhosmayor)
             val fechamayor:Date=SimpleDateFormat("dd/MM/yyyy").parse(""+diasmenor + "/"+mesesmenor+"/"+anhosmenor)
             dias1= ((fechamenor.time-fechamayor.time)/86400000).toInt()
         }

         var textomes:String="";var textodias:String=""
         if (meses1>1){textomes= getResources().getString(R.string.meses) }else{textomes=getResources().getString(R.string.mes)  }
         if (dias1>1){textodias=getResources().getString(R.string.dias) } else{textodias=getResources().getString(R.string.dia) }
         Meses.setText( ""+ meses1 + " " + textomes+ " " + dias1 + " " + textodias)

         calendario.add(Calendar.DAY_OF_YEAR,(37 * 7)-diaseco)
         for (semanas in 1..5) {
             calendario.add(Calendar.DAY_OF_YEAR,7);
             dia = calendario.get(Calendar.DAY_OF_MONTH)
             mes = calendario.get(Calendar.MONTH) + 1
             anho = calendario.get(Calendar.YEAR)
             if(semanas==1) {S38.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
             else if(semanas==2) {S39.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
             else if(semanas==3) {S40.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
             else if(semanas==4) {S41.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
             else {S42.setText("" + dia.twoDigits() + "/" + mes.twoDigits() + "/" + anho).toString()}
         }
     }

     fun PedirFecha(input: EditText,  parainput: EditText, Nombre:String) {
             val c = Calendar.getInstance()
             val year :Int
             val month:Int
             val day:Int
             if(TextUtils.isEmpty(input.text)) {
                 year = c.get(Calendar.YEAR)
                 month = c.get(Calendar.MONTH)
                 day = c.get(Calendar.DAY_OF_MONTH)
                 val month=month+1
             } else {
                 val miFecha:Date = SimpleDateFormat("dd/MM/yyyy").parse(input.text.toString())
                 c.setTime(miFecha)
                 year = c.get(Calendar.YEAR)
                 month = c.get(Calendar.MONTH)
                 day = c.get(Calendar.DAY_OF_MONTH)
             }

             //Toast.makeText(input.context, Nombre, Toast.LENGTH_SHORT).show()

         val dpd = DatePickerDialog(input.context, DatePickerDialog.OnDateSetListener {
                 view, year, month, day ->
                 val month=month+1
                 input.setText("" + day.twoDigits() + "/" + month.twoDigits() + "/" + year)
             }, year, month, day)
             dpd.show()
     }

     fun Int.twoDigits() = if (this <= 9) "0$this" else this.toString()


     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         // Handle presses on the action bar menu items
         var Idioma:String="";var IdiomaName:String=""
         when (item.itemId) {
             R.id.espanhol -> {
                 Idioma="es";IdiomaName="Español"
             }
             R.id.ingles -> {
                 Idioma="en";IdiomaName="English"
             }
             R.id.portugues -> {
                 Idioma="pt";IdiomaName="Português"
             }
             R.id.acercade -> {
                 val acercade = Intent(this, About::class.java)
                 startActivity(acercade)
             }
          }
 //        if (Idioma!="") {
 //            setLocale(Idioma)
 //            val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
 //            with (sharedPref.edit()) {
 //                putString( "Idioma", Idioma)
 //                commit()
 //            }
 //       Toast.makeText(this, IdiomaName,Toast.LENGTH_SHORT).show()
 //        finish()}
         return super.onOptionsItemSelected(item)
     }

//     fun setLocale(localeName: String) {
//         val config = Configuration()
//         val locale = Locale(localeName);
//         config.locale =locale;
//         resources.updateConfiguration(config, null)
 //        val refresh = Intent(this@MainActivity, MainActivity::class.java)
 //        startActivity(refresh)
 //        finish()
 //          }

     override fun onCreateOptionsMenu(menu: Menu): Boolean {
         // Inflate the menu to use in the action bar
         val inflater = menuInflater
         inflater.inflate(R.menu.toolbar_menu, menu)
         return super.onCreateOptionsMenu(menu)
     }
 }







