package com.example.firestoredemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_with_desc.view.*
import kotlinx.android.synthetic.main.item_with_image.view.*

private const val STORY_TYPE_DESC = 0
private const val STORY_TYPE_IMAGE = 1

class StoryListAdapter(var storyListItems: List<Stories>, val clickListener: (Stories) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // view holders for all types of items
    inner class DescViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(stories: Stories, clickListener: (Stories) -> Unit){
            itemView.story_title.text = stories.name
            itemView.story_desc.text = stories.desc

            itemView.setOnClickListener {
                clickListener(stories)
            }
        }
    }
    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(stories: Stories, clickListener: (Stories) -> Unit){
            itemView.image_post_title.text = stories.name
            Glide.with(itemView.context).load(stories.image).into(itemView.image_post_view)

            // clip image
//            itemView.image_post_view.clipToOutline = true

            itemView.setOnClickListener {
                clickListener(stories)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == STORY_TYPE_DESC){
            val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_with_desc, parent, false)
            DescViewHolder(layoutInflater)
        }else{
            // STORY_TYPE_IMAGE
            val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_with_image, parent, false)
            ImageViewHolder(layoutInflater)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == STORY_TYPE_DESC){
            (holder as DescViewHolder).onBind(storyListItems[position], clickListener)
        }else{
            (holder as ImageViewHolder).onBind(storyListItems[position], clickListener)
        }
    }

    // checking the view element that it's image or desc
    override fun getItemViewType(position: Int): Int {
        return if(storyListItems[position].post_type == 0L){
            STORY_TYPE_DESC
        }else{
            STORY_TYPE_IMAGE
        }
    }

    override fun getItemCount(): Int {
        return storyListItems.size
    }
}