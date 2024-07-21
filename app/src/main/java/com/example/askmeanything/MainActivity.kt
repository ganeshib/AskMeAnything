package com.example.askmeanything


import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.runBlocking
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etPrompt=findViewById<EditText>(R.id.editTextText)
        val btnSub=findViewById<Button>(R.id.button)
        val tvResult=findViewById<TextView>(R.id.result)
        val pgBar=findViewById<ProgressBar>(R.id.progressBar)
        pgBar.visibility=View.GONE
        btnSub.setOnClickListener {
            val prompt=etPrompt.text.toString()
            if(prompt.isEmpty()){
                Toast.makeText(this,"Enter Text To Search",Toast.LENGTH_SHORT).show()
            }else{
                pgBar.visibility=View.VISIBLE
                val generativeModel = GenerativeModel(
                    // The Gemini 1.5 models are versatile and work with both text-only and multimodal prompts
                    modelName = "gemini-1.5-flash",
                    // Access your API key as a Build Configuration variable (see "Set up your API key" above)
                    apiKey=""
                )

                runBlocking {
                    val response = generativeModel.generateContent(prompt)
                    tvResult.text=response.text.toString()
                }
                if(tvResult.text.isNotEmpty()) pgBar.visibility=View.GONE
            }
        }
    }
}