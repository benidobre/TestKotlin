package com.example.bdobre.testkotlin.GOTUtils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.bike_list_item.view.*


/**
 * Created by bdobre on 08/09/2017.
 */

class BooksAdapter(internal  var clickHandler: BooksAdapterOnClickHandler) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    var books: List<Book>? = null

    override fun onBindViewHolder(holder: BooksAdapter.BooksViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.bike_list_item, parent, false)
        return BooksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return books?.size ?: 0
    }

    fun setData(data: List<Book>) {
        books = data
        notifyDataSetChanged()
    }

    interface BooksAdapterOnClickHandler{
        fun onClick(book : Book?)
    }

    inner class BooksViewHolder(internal var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var bookTextView = view.tv_bike

        fun bind(position: Int) {
            view.setOnClickListener(this)
            bookTextView.text = books?.get(position)?.name

        }

        override fun onClick(p0: View) {
            clickHandler.onClick(books?.get(adapterPosition))
        }

    }


}