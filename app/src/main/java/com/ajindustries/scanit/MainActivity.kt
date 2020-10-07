package com.ajindustries.scanit


import android.content.Context
import android.content.Entity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import kotlinx.android.synthetic.main.activity_main.*

private const val CAMERA_REQUEST_CODE =101
 var rollnum = "ATTENDENCE "

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)



        button.setOnClickListener {

            val main = Intent(this@MainActivity, ViewActivity::class.java)
            startActivity(main)

        }
            //yaha par daal vapas
      

        setupPermissions()
        codeScanner()


    }
          class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
              AsyncTask<Void, Void, Boolean>() {

              val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()

              override fun doInBackground(vararg p0: Void?): Boolean {

                  when (mode) {
                      1 -> {
                          val book: BookEntity? =
                              db.bookDao().getBookById(bookEntity.enrollno)
                          db.close()
                          return book != null
                      }

                      2 -> {
                          db.bookDao().insertBook(bookEntity)
                          db.close()
                          return true
                      }


                  }


                  return false
              }

          }




    private fun codeScanner() {
        codeScanner= CodeScanner(this, scanner_view)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode= AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback= DecodeCallback {
                //rollnum =it.text.toString()
                send_data(it.text.toString())
                runOnUiThread{

                    tv_textView.text=it.text
                   // send_data(no)
                   // send_data(it.text.toString())


                    Toast.makeText(this@MainActivity,it.text, Toast.LENGTH_LONG).show()

                }
            }

            errorCallback= ErrorCallback {
                runOnUiThread{
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }

       // return no
    }
//ssasv
    fun send_data( data: String ) {
      var bookEntity = BookEntity(rollnum)
        val checkFav = DBAsyncTask(applicationContext, bookEntity, 1).execute()
        val isFav = checkFav.get()
        if (!DBAsyncTask(applicationContext, bookEntity, 1).execute().get()) {
            val async = DBAsyncTask(applicationContext, bookEntity, 2).execute()
            val result = async.get()
        }

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
    private fun setupPermissions(){
        val permission= ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        if (permission!= PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            this,
                            "You need the camera permission to be able to use",
                            Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //sucess
                }
            }
        }
    }
}
