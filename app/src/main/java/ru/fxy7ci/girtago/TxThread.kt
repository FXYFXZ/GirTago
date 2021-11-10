package ru.fxy7ci.girtago

import android.util.Log

class TxThread: Thread() {
    var theTXState = State.INIT
    var toStop = false

    override fun run() {
        super.run()
        toStop = false
        Log.d("MyLog", "OnRun")
        while (!toStop) {
            sleep(5000)
            theTXState = State.READY
            Log.d("MyLog", "OnReady")
        }
    }

    fun theTXState(): State {
        return theTXState
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