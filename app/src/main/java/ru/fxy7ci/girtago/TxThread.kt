package ru.fxy7ci.girtago

import android.os.CountDownTimer as CountDownTimer1

class TxThread: Thread() {
    var theTXState = State.INIT


    override fun run() {
        super.run()
        sleep(5000)
        theTXState = State.READY
    }
    init {
        val timer: CountDownTimer1(5000,1000){
            override fun onFinish() {
                theState = State.READY
            }
        }
        timer.start()

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

}