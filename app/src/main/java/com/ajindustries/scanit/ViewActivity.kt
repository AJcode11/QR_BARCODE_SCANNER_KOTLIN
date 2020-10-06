package com.ajindustries.scanit

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    lateinit var recyclerdata: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: DataRecyclerAdapter
    lateinit var btn_delete : Button
    var dbBookList = listOf<BookEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        btn_delete =findViewById(R.id.btn_delete)

        recyclerdata = findViewById(R.id.recyclerData)
        layoutManager = LinearLayoutManager(this@ViewActivity)

        dbBookList = RetriveData(this@ViewActivity as Context).execute().get()

        btn_delete.setOnClickListener{
            delete_data()
        }

        recyclerAdapter= DataRecyclerAdapter(this@ViewActivity as Context, dbBookList )
        recyclerdata.adapter=recyclerAdapter
        recyclerdata.layoutManager=layoutManager
        recyclerdata.addItemDecoration(
            DividerItemDecoration(
                recyclerdata.context,
                (layoutManager as LinearLayoutManager).orientation
            )
        )



    }

    class RetriveData(val context: Context): AsyncTask<Void,Void,List<BookEntity>>(){
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context,BookDatabase::class.java, "books-db").build()

            return db.bookDao().getAllBooks()
        }

    }

    fun delete_data(){
        AsyncTask.execute{
        val db = Room.databaseBuilder(this@ViewActivity as Context,BookDatabase::class.java,"books-db").build()

         db.bookDao().deleteBook()

    }
        val intent =Intent(this@ViewActivity,MainActivity::class.java)
            startActivity(intent )
    }
}