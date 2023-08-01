package com.wang.knifepicture

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.wang.knifepicture.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.post {
            val url = R.drawable.ssx
            GlideManager.loadImg(url, binding.iv1)
            GlideManager.loadCircleCropImg(url, binding.iv2)
            GlideManager.loadBlurImg(url, binding.iv3, 10)
        }

    }

}