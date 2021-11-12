package ru.fxy7ci.girtago

import android.content.Context
import android.content.Intent
import android.util.Log

class TxThread: Thread() {
    var theTXState = State.INIT
    var toStop = false

    // все что отнгостится к BT


    override fun run() {
        super.run()
        if (setupConnection()) {

            toStop = false
            while (!toStop) {
                sleep(5000)
                theTXState = State.READY
              //  Log.d("MyLog", "OnReady")
            }
        }
    }

    fun theTXState(): State {
        return theTXState
    }

    private fun setupConnection(): Boolean{
        // вот здесь вот и соединяемся...


//        val gattServiceIntent = Intent(this, TxThread::class.java)
//        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)





        sleep(5000)
        return true

    }



    enum class State {
        INIT,
        CHOICE,
        READY,
        ERROR
    }

    fun getAppStopped() {
        toStop = true
    }



}