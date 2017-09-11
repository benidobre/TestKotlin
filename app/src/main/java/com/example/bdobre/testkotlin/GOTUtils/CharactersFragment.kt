package com.example.bdobre.testkotlin.GOTUtils

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdobre.testkotlin.R
import kotlinx.android.synthetic.main.got_characters.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.List

/**
 * Created by bdobre on 08/09/2017.
 */

class CharactersFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var charactersAdapter : CharactersAdapter
    lateinit var scrollListener : EndlessRecyclerViewScrollListener
    var hasMore = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.got_characters, container, false)

        val bundle = arguments
        val charactersIds = bundle.getIntArray("characters")
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = rootView.recyclerview_got_characters
        recyclerView.layoutManager = layoutManager
        charactersAdapter = CharactersAdapter(activity as CharactersAdapter.CharacterOnClickHandler)
        recyclerView.adapter = charactersAdapter
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view:RecyclerView) {
                fetch(charactersIds,(page)*10,10);
            }
        }
        fetch(charactersIds, 0, 10)


        return rootView
    }

    fun fetch(ids : IntArray, start: Int,  nrCharacters: Int) {
        if(nrCharacters == 0 || start >= ids.size) {
            if(!hasMore) {
                recyclerView.addOnScrollListener(scrollListener)
                hasMore = true
            }
            return
        }
        var characterCall = GOTService.Get()?.getCharacter(ids[start])
        characterCall?.enqueue(object : Callback<Character> {

            override fun onResponse(call: Call<Character>?, response: Response<Character>?) {
                if(response != null && response.isSuccessful) {
                    var list : List<Character>? = charactersAdapter.getData()
                    var c = response.body()
                    if(list != null) {
                        list.add(c)
                        charactersAdapter.setData(list)
                    }
                    fetch(ids, start + 1, nrCharacters -1)

                }
            }

            override fun onFailure(call: Call<Character>?, t: Throwable?) {

            }


        })

    }
}