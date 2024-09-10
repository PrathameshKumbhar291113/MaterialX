package com.prathameshkumbhar.materialx

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.prathameshkumbhar.materialx.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SplashActivity, R.layout.activity_splash)

        window.statusBarColor = resources.getColor(R.color.heliotrope1)
        navigateToHome()

    }

    private fun navigateToHome(){
        lifecycleScope.launch {
            delay(3000)
            val homeIntent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }
    }
}