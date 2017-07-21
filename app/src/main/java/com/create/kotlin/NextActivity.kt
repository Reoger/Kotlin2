package com.create.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        var data = intent.getStringExtra("param")

        Glide.with(this).load(data).into(imageView)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
             MotionEvent.ACTION_UP->{
                 finish()
                 return false
             }

        }
        return super.onTouchEvent(event)
    }
}
