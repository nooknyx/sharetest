package com.example.work.ui.booklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.work.data.Bookdata
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.work.Adapter.BookAdapter
import com.example.work.R
import com.google.firebase.firestore.*

class popular : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var popularlist : ArrayList<Bookdata>
    private lateinit var popularadapter : BookAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular)

        recyclerView = findViewById(R.id.poppage)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        popularlist = arrayListOf()
        popularadapter = BookAdapter(popularlist)
        recyclerView.adapter = popularadapter

        EventChangeListener()
    }

    private fun EventChangeListener()
    {
        db = FirebaseFirestore.getInstance()
        db.collection("booktest").
        addSnapshotListener(object : EventListener<QuerySnapshot>
        {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?)
            {
                if (error!=null)
                {
                    Log.e("Firestore error", error.message.toString())
                    return
                }

                for (dc : DocumentChange in value?.documentChanges!!)
                {
                    if(dc.type == DocumentChange.Type.ADDED)
                    {
                        popularlist.add(dc.document.toObject(Bookdata::class.java))
                    }
                }
                popularadapter.notifyDataSetChanged()
            }
        }
        )



    }

}