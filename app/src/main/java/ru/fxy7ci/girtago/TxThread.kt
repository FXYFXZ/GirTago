package ru.fxy7ci.girtago

class TxThread {
    private val theState = State.INIT

    fun getState(): State {
        return theState
    }
    enum class State {
        INIT,
        CHOICE,
        READY,
        ERROR
    }

}