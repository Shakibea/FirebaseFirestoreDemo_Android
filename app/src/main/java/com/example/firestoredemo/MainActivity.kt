package com.example.firestoredemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), (Stories) -> Unit {

    private var adapter: StoryFirestoreRecyclerAdapter? = null

    private val TAG: String = "LOG_MAINACTIVITY"
    private val firebaseRepo: FirebaseRepo = FirebaseRepo()
    private var storyList: List<Stories> = ArrayList()
    private val storyListAdapter: StoryListAdapter = StoryListAdapter(storyList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // From StackOverFlow Code

//        readFirestoreData()
//        recycler_view.layoutManager = LinearLayoutManager(this)
//
//        adapter = StoryFirestoreRecyclerAdapter(createFirestore(), )
//        recycler_view.adapter = adapter


        // check user
        if (firebaseRepo.getUser() == null) {
            // create user
            firebaseRepo.createUser().addOnCompleteListener {
                if (it.isSuccessful) {
                    loadData()
                } else {
                    Log.d(TAG, "Error: ${it.exception!!.message}")
                }
            }
        } else {
            loadData()
        }

        // init recycler view
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = storyListAdapter

    }

    private fun loadData() {
        firebaseRepo.getStoryList().addOnCompleteListener {
            if (it.isSuccessful) {
                storyList = it.result!!.toObjects(Stories::class.java)
                storyListAdapter.storyListItems = storyList
                storyListAdapter.notifyDataSetChanged()
            } else {
                Log.d(TAG, "Error: ${it.exception!!.message}")
            }
        }
    }

    override fun invoke(p1: Stories) {
        Toast.makeText(this, "Clicked title: ${p1.name}", Toast.LENGTH_SHORT).show()
    }


    // From StackOverFlow Code

//    private inner class StoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//        fun onBind(name: String) {
//            val textView = view.findViewById<TextView>(R.id.text_view)
//            textView.text = name
//        }
//    }
//
//    private inner class StoryFirestoreRecyclerAdapter (options: FirestoreRecyclerOptions<Story>) : FirestoreRecyclerAdapter<Story, StoryViewHolder>(options) {
//        override fun onBindViewHolder(storyViewHolder: StoryViewHolder, position: Int, productModel: Story) {
//            storyViewHolder.onBind(productModel.name)
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.story_item_list, parent, false)
//            return StoryViewHolder(view)
//        }
//    }

//    private fun createFirestore(): FirestoreRecyclerOptions<Story>{
//        val db = FirebaseFirestore.getInstance()
//        val query = db.collection("stories").orderBy("ts", Query.Direction.DESCENDING)
//        val options =  FirestoreRecyclerOptions.Builder<Story>().setQuery(query, Story::class.java).build()
//
//        return options
//    }
//
//    private fun readFirestoreData(){
//        val db = FirebaseFirestore.getInstance()
//
//        db.collection("story")
//                .get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
//                        Log.d("FireTAG", "${document.id} => ${document.data}")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w("FireTAG", "Error getting documents.", exception)
//                }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        adapter!!.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//
//        if (adapter != null) {
//            adapter!!.stopListening()
//        }
//    }
}