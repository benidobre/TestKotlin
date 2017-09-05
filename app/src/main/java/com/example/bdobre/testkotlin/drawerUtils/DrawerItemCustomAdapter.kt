package com.example.bdobre.testkotlin.drawerUtils

/**
 * Created by bdobre on 05/09/2017.
 */

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.my_list_view_item_row.view.*


/**
 * Created by bdobre on 20/07/2017.
 */

class DrawerItemCustomAdapter(internal var mContext: Context, internal var layoutResourceId: Int, internal var data: Array<DataModel>) : ArrayAdapter<DataModel>(mContext, layoutResourceId, data) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listItem = convertView ?: (mContext as Activity).layoutInflater
                                        .inflate(layoutResourceId, parent, false)

        val imageViewIcon = listItem.imageViewIcon as ImageView
        val textViewName = listItem.textViewName as TextView

        val folder = data[position]


        imageViewIcon.setImageResource(folder.icon)
        textViewName.setText(folder.name)

        return listItem
    }
}
