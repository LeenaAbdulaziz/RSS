package com.example.rss
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    var questions = mutableListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.rv)
        FetchTopSongs().execute()
    }

    private inner class FetchTopSongs : AsyncTask<Void, Void, MutableList<Question>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<Question> {

            val url = URL("https://stackoverflow.com/feeds")

            val urlConnection = url.openConnection() as HttpURLConnection

            questions = urlConnection.getInputStream()?.let {

                    parser.parse(it)
                }
                        as MutableList<Question>
            return questions
        }

        override fun onPostExecute(result: MutableList<Question>?) {
            super.onPostExecute(result)
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, questions)
            recyclerView.adapter = RVAdapter(questions)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    fun details(view: View) {

    }


}

