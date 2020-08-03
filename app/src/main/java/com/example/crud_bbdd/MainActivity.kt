package com.example.crud_bbdd

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.set
import com.example.crud_bbdd.Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA2
import com.example.crud_bbdd.Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA3
import com.example.crud_bbdd.Estructura_BBDD.FeedEntry.TABLE_NAME
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bInsertar: Button
    private lateinit var bActualizar: Button
    private lateinit var bBorrar: Button
    private lateinit var bBuscar: Button

    private lateinit var txtId: TextView
    private lateinit var txtNombre: TextView
    private lateinit var txtApellido: TextView
    private lateinit var miHelper: BBDD_Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bInsertar = findViewById(R.id.btnInsertar)
        bActualizar = findViewById(R.id.btnActualizar)
        bBorrar = findViewById(R.id.btnBorrar)
        bBuscar = findViewById(R.id.btnInsertar)

        txtId = findViewById(R.id.txtID)
        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)



        miHelper = BBDD_Helper(applicationContext)

        btnInsertar.setOnClickListener{ onClick(it) }
        btnActualizar.setOnClickListener{ onClick(it) }
        btnBuscar.setOnClickListener{ onClick(it) }
        btnBorrar.setOnClickListener{ onClick(it) }

    }

    override fun onClick(v: View?) {
        when (v)  {
            btnInsertar -> insertar()
            btnActualizar -> Actualizar()
            btnBuscar -> Buscar()
            btnBorrar -> Borrar()
        }


    }

    fun insertar(){
        val db = miHelper.writableDatabase
        val values = ContentValues().apply {
            put(BaseColumns._ID,txtId.text.toString())
            put(Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA1,txtId.text.toString())
            put(Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA2,txtNombre.text.toString())
            put(Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA3,txtApellido.text.toString())

        }

        var newRowId = db.insert(TABLE_NAME, null, values)
        Toast.makeText(this,"Columna Insertada! valor: $newRowId",Toast.LENGTH_LONG).show()

    }

    fun Actualizar(){
        var db: SQLiteDatabase = miHelper.writableDatabase

        var title = txtNombre.text.toString()
        var title2 = txtApellido.text.toString()

        val values = ContentValues().apply {
            put(Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA2,title)
            put(Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA3,title2)
        }

        val selection = "${Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA1} LIKE ?"
        val selectionArgs = arrayOf(txtId.text.toString())

        val count = db.update(
            Estructura_BBDD.FeedEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs)

        if (count > 0){
            Toast.makeText(this,"Registro Actualizados: $count \n Id Actualizado: ${txtId.text.toString()}",Toast.LENGTH_LONG).show()
            txtId.text=""
            txtNombre.text=""
            txtApellido.text=""
        }


    }

    fun Borrar(){

        var db: SQLiteDatabase = miHelper.writableDatabase
        val selection = "${Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA1} LIKE ?"
        val selectionArgs = arrayOf(txtId.text.toString())
        val deleteRows = db.delete(Estructura_BBDD.FeedEntry.TABLE_NAME,selection,selectionArgs)

        if (deleteRows > 0){
            Toast.makeText(this,"Borrado Exito de $deleteRows Fila(s). Valor id: ${txtId.text.toString()}", Toast.LENGTH_LONG).show()
            txtId.text = ""
            txtNombre.text = ""
            txtApellido.text = ""
        }

    }

    fun Buscar(){

        val db = miHelper.readableDatabase

        //aquí indica las columnas que deseas  retornar
        val projection = arrayOf(NOMBRE_COLUMNA2, NOMBRE_COLUMNA3)

        //aquí le decimos que haga la busqueda de este valor en la columna indicada
        val selection = "${Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA1} = ?"
        val selectionArgs = arrayOf(txtId.text.toString())

        //para ordenar el resultado segun la columna indicada
        //val sortOrder = "${Estructura_BBDD.FeedEntry.NOMBRE_COLUMNA2} DESC"

        val cursor = db.query(
            Estructura_BBDD.FeedEntry.TABLE_NAME,   // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            selection,                              // The columns for the WHERE clause
            selectionArgs,                          // The values for the WHERE clause
            null,                          // don't group the rows
            null,                           // don't filter by row groups
            null  //sortOrder               // The sort order
        )

       // println(cursor)

        //Dado que el cursor comienza en la posición -1, la llamada a moveToNext() coloca la "posición de lectura" en la primera entrada de los resultados y muestra si el cursor ya pasó la última entrada del conjunto de resultados
        //cursor.moveToFirst()

        try{
            cursor.moveToNext()
            txtNombre.text = cursor.getString(0)
            txtApellido.text = cursor.getString(1)
            cursor.close()
        }catch (e: Exception){
            Toast.makeText(this,"No se encontró Registro",Toast.LENGTH_SHORT).show()
        }




    }

    override fun onDestroy() {
        super.onDestroy()
        miHelper.close()
    }


}