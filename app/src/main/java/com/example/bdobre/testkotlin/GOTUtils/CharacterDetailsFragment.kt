package com.example.bdobre.testkotlin.GOTUtils

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.*
import com.example.bdobre.testkotlin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.got_characters_details.view.*

/**
 * Created by bdobre on 08/09/2017.
 */

class CharacterDetailsFragment : Fragment() {
    var idd = 0
    lateinit var prefs: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        var rootView = inflater.inflate(R.layout.got_characters_details, container, false)

        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        editor = prefs.edit()

        var image = rootView.character_details_image
        var nameTv = rootView.character_details_name
        var bornTv = rootView.character_details_born
        var aliasesTv = rootView.character_details_aliases
        var bundle = arguments

        Picasso.with(context).load(Uri.parse("https://robohash.org/"+bundle.get("name"))).into(image)
        nameTv.text = bundle.getString("name")
        bornTv.text = bundle.getString("born")

        idd = bundle.getInt("id")

        var aliases = bundle.getStringArrayList("aliases")
        var alias = ""
        for(s in aliases) {
            alias += s + ", "
        }
        aliasesTv.text = alias
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_menu, menu)

        var fav = prefs.getStringSet("fav", HashSet<String>())
        if(!fav.isEmpty()) {
            if(fav.contains("$idd")) {
                menu.getItem(0).icon = resources.getDrawable(R.drawable.favorite_active)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.favorite_item){
            var fav = prefs.getStringSet("fav", HashSet<String>())
            var inn = HashSet<String>(fav)
            if(fav.contains(idd.toString())) {
                item.icon = resources.getDrawable(R.drawable.favorite_inactive)
                inn.remove(idd.toString())
            } else {
                inn.add(idd.toString())
                item.icon = resources.getDrawable(R.drawable.favorite_active)
            }

            editor.putStringSet("fav",inn).commit()
            return true

        }
        return false
    }
}