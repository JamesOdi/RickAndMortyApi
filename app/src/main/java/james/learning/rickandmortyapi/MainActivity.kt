package james.learning.rickandmortyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import james.learning.rickandmortyapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var view: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        getItems()
    }

    private fun getItems() {
        val api = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        CoroutineScope(IO).launch {
            val characters = api.getCharacters().awaitResponse()
            if (characters.isSuccessful) {
                val body = characters.body()
                if (body != null) {
                    val results = body.results
                    withContext(Main) {
                        view.progressBar.visibility = GONE
                        view.itemsRecyclerView.adapter = ItemsAdapter(applicationContext,results)
                    }
                }
            } else {
                withContext(Main) {
                    Toast.makeText(applicationContext, "Error loading!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}