package cn.edu.whu.unsc.audioinfo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tbruyelle.rxpermissions2.RxPermissions



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePermissions()
    }

    private fun initializePermissions() {

    }
}
