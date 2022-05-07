package com.example.colorwheelpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Created by Phillip Truong
 * date 07/05/2022.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachFragment()
    }

    private fun attachFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ColorWheelPickersFragment.newInstance())
            .commit()
    }
}