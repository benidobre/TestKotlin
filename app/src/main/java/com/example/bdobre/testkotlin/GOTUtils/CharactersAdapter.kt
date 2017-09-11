package com.example.bdobre.testkotlin.GOTUtils

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdobre.testkotlin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.got_character_item.view.*
import java.util.ArrayList
import java.util.List

/**
 * Created by bdobre on 08/09/2017.
 */

class CharactersAdapter(internal var clickHandler: CharacterOnClickHandler) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    var characters : List<Character> = ArrayList<Character>() as List<Character>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.got_character_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return characters?.size ?: 0
    }

    fun getData() : List<Character>? {
        return characters
    }

    fun setData(data: List<Character>) {
        characters = data
        notifyDataSetChanged()
    }

    interface CharacterOnClickHandler {
        fun onClick(character: Character?)
    }

    inner class CharactersViewHolder(internal var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var image = view.character_image
        var name = view.character_name

        fun bind(position: Int) {
            view.setOnClickListener(this)
            var character = characters?.get(position)
            if(character != null){
                name.text = character.name
                Picasso.with(name.context).load(Uri.parse("https://robohash.org/"+character.name)).into(image)
            }


        }

        override fun onClick(p0: View?) {
            clickHandler.onClick(characters?.get(adapterPosition))
        }

    }


}