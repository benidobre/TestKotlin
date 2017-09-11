package com.example.bdobre.testkotlin.GOTUtils

import android.support.v4.app.Fragment;
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.got_books.view.*
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

/**
 * Created by bdobre on 08/09/2017.
 */

class BooksFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var booksAdapter: BooksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var rootView = inflater.inflate(R.layout.got_books, container, false)

        recyclerView = rootView.recyclerview_got_books
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        booksAdapter = BooksAdapter(activity as BooksAdapter.BooksAdapterOnClickHandler)
        recyclerView.adapter = booksAdapter

        var bookCall = GOTService.Get()?.getBooks(12)
        bookCall?.enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>?, response: Response<List<Book>>?) {
                if(response != null && response.isSuccessful()){
                    booksAdapter.setData(response.body());
                }
            }

            override fun onFailure(call: Call<List<Book>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        return rootView

    }
}