package james.learning.rickandmortyapi

import james.learning.rickandmortyapi.api.App
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("character")
    fun getCharacters(): Call<App>
}