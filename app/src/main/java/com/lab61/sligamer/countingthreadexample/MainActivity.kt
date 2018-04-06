package com.lab61.sligamer.countingthreadexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * Created by Justin Freres on 3/31/2018.
 * Lab 6-1 Counting Thread Example
 * Plugin Support with kotlin_version = '1.2.31'
 */
class MainActivity : AppCompatActivity() {

    // DECLARE VARIALBES
    private lateinit var countTextView: TextView
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private var count: Int  = 0
    @Volatile internal var runningThread: Boolean = false
    internal var thread: Thread? = null

    companion object{
        const val DELAY = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // REF TO THE TEXTVIEW UI ELEMENT ON THE LAYOUT
        countTextView = findViewById(R.id.textView)
        startBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)

        // INIT COUNTER
        count  = 0

    }

    // INIT COUNTER ZERO APP START
    override fun onStart() {
        super.onStart()
        count = 0
    }

    private val countNumbers: Runnable = Runnable {
        try {
            while (true) {
                count++
                Thread.sleep(DELAY.toLong())
                threadHandler.sendEmptyMessage(0)
            }
        } catch (e: InterruptedException){
            e.printStackTrace()
        }
    }

    private val threadHandler: Handler = object: Handler() {
        override fun handleMessage(msg: Message?) {
            countTextView.text = count.toString()
        }
    }

    fun startThread(view: View){
        // CREATE A THREAD AND START IT
        runningThread = true
        thread = Thread(countNumbers)
        thread!!.start()

    }

    fun stopThread(view: View){
        when {
            runningThread -> {
                runningThread = false
                thread!!.interrupt()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId
        if(id == R.string.action_settings){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
