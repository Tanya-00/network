package com.example.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit: Retrofit =  Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val gitAPI: GitApi = retrofit.create(GitApi::class.java)

        gitAPI.getUser("Tanya-00").enqueue(object: Callback<GitApi.GitUser> {
            override fun onFailure(call: Call<GitApi.GitUser>, t: Throwable) {

            }

            override fun onResponse(call: Call<GitApi.GitUser>, response: Response<GitApi.GitUser>) {
                val user: GitApi.GitUser? = response.body()

                Log.d("AvatarURL", "${user?.avatarURL}")
                Log.d("Login", "${user?.login}")

                val imageView: ImageView = findViewById<View>(R.id.imageView) as ImageView

                Picasso.get()
                    .load(user?.avatarURL)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
            }
        })
    }
}