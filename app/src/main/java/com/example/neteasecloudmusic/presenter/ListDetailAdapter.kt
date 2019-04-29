package com.example.neteasecloudmusic.presenter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.neteasecloudmusic.R
import com.example.neteasecloudmusic.model.PlayListDetailBean
import com.example.neteasecloudmusic.view.MusicPlayActivity
import com.squareup.picasso.Picasso

class ListDetailAdapter(var context: Context, mainBean: PlayListDetailBean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val BANNER_TYPE = 0
    private val ITEM_TYPE = 1
    var songListDetail = mainBean

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder =
        if (p1 == BANNER_TYPE)
            DetailHeaderHolder(LayoutInflater.from(context).inflate(R.layout.play_list_info_header, p0, false))
         else
            DetailItemHolder(context, LayoutInflater.from(context).inflate(R.layout.play_list_info_item, p0, false))


    override fun getItemViewType(position: Int): Int = if (position == 0) BANNER_TYPE else ITEM_TYPE

    override fun getItemCount(): Int  = if(songListDetail.playlist?.tracks == null)  1
        else songListDetail.playlist?.tracks!!.size + 1


    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when(p0) {
            is DetailHeaderHolder -> {
                Picasso.with(context)
                    .load(songListDetail.playlist?.coverImgUrl)
                    .fit()
                    .centerCrop()
                    .into(p0.listCover)
                p0.apply {
                    creator.text = songListDetail.playlist?.creator?.nickname
                    listName.text = songListDetail.playlist?.name
                    headerListName.text = songListDetail.playlist?.name
                }
            }
            is DetailItemHolder -> {
                if (songListDetail.playlist?.tracks?.indices == null) {
                } else {
                    if (songListDetail.playlist?.tracks?.get(p1 - 1)?.mv != 0) {
                        p0.mvButton.visibility = View.VISIBLE
                    }
                    p0.apply {
                        num.text = p1.toString()
                        songName.text = songListDetail.playlist?.tracks?.get(p1 - 1)?.name
                        singer.text = songListDetail.playlist?.tracks?.get(p1 - 1)?.ar?.get(0)?.name + " - " + songListDetail.playlist?.tracks?.get(p1 - 1)?.al?.name
                        playListDetail.setOnClickListener {
                            val intent = Intent(context, MusicPlayActivity::class.java)
                            val bundle = Bundle()
                            bundle.putString("id", songListDetail.playlist?.tracks?.get(p1 - 1)?.id.toString() )
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    inner class DetailHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var headerListName: TextView = itemView.findViewById(R.id.listName)
        var listCover: ImageView = itemView.findViewById(R.id.listCover)
        var creator: TextView = itemView.findViewById(R.id.creatorName)
        var listName:TextView = itemView.findViewById(R.id.songListName)
    }


    inner class DetailItemHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playListDetail: ConstraintLayout = itemView.findViewById(R.id.songsList)
        var mvButton: ImageView = itemView.findViewById(R.id.mvButton)
        var num: TextView = itemView.findViewById(R.id.songNum)
        var songName: TextView = itemView.findViewById(R.id.songName)
        var singer: TextView = itemView.findViewById(R.id.singer)
    }

}