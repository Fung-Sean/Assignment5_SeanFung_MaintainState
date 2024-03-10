package com.example.maintainstate

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editText: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private var currentImageResource: Int = 0 // Initialize with 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)
        val button = findViewById<Button>(R.id.button)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Load saved state
        loadImageFromSharedPreferences()
        loadTextFromSharedPreferences()

        button.setOnClickListener {
            // Load a random image
            val images = arrayOf(R.drawable.dog, R.drawable.donut, R.drawable.pico, R.drawable.random_car)
            val randomIndex = Random().nextInt(images.size)
            imageView.setImageResource(images[randomIndex])
            currentImageResource = images[randomIndex] // Update the currentImageResource
        }
        editText.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()

        // Save state
        val editor = sharedPreferences.edit()
        editor.putInt("image", currentImageResource) // Save the resource ID of the currently displayed image
        editor.putString("text", editText.text.toString())
        editor.apply()
    }

    private fun loadImageFromSharedPreferences() {
        val imageResource = sharedPreferences.getInt("image", 0)
        if (imageResource != 0) {
            imageView.setImageResource(imageResource)
            currentImageResource = imageResource // Update the currentImageResource
        }
    }

    private fun loadTextFromSharedPreferences() {
        val text = sharedPreferences.getString("text", "")
        editText.setText(text)
    }
}