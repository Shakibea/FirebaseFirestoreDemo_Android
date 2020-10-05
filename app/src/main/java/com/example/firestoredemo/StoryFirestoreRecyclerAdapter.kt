package com.example.firestoredemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class StoryFirestoreRecyclerAdapter(option: FirestoreRecyclerOptions<Stories>): FirestoreRecyclerAdapter<Stories, StoryFirestoreRecyclerAdapter.StoryViewHolder>(option) {

    inner class StoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun onBind(story: Stories){
            val text = itemView.findViewById<TextView>(R.id.image_post_title)
            text.text = story.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.story_item_list, parent, false)
        return StoryViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int, story: Stories) {
        holder.onBind(story)
    }
}