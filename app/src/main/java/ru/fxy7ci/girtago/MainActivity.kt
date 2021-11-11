package ru.fxy7ci.girtago

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGattCharacteristic
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.fxy7ci.girtago.BT.StoreVals

lateinit var clrCnt: ColorCont
lateinit var btnSlide: Button


//TODO  добавить сканирование как в master(c уровнями сигналов)


class MainActivity : AppCompatActivity() {

    val BT_REQUEST_PERMISSION : Int = 89
    private val mBluetoothAdapter: BluetoothAdapter? = null
    private lateinit var mDetector: GestureDetector
    private lateinit var txClass: TxThread
    private val mainHandler = Handler(Looper.getMainLooper())

    // все что относится к BT
    private var mBluetoothLeService: BluetoothLeService? = null
    private var mConnected = false
        //bindService(gattServiceIntent, mServiceConnection, android.content.Context.BIND_AUTO_CREATE)






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // получение всех разрешений
        getBtPermission()


        // инициализация классов
        txClass = TxThread()
        //  txClass.isDaemon = true

        val gattServiceIntent = Intent(this, BluetoothLeService::class.java)
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE)


        showTxState()
        btnSlide = findViewById(R.id.btnSlide)
        clrCnt   = ColorCont()
        btnSlide.setBackgroundColor(clrCnt.getColor())

        setGest()

        mainHandler.post(object : Runnable {
            override fun run() {
                showTxState()
                mainHandler.postDelayed(this, 1000)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        // TODO bluetooth enable
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter())
        //



        if (!txClass.toStop) txClass.start()
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacksAndMessages(null)
        txClass.getAppStopped()
        unregisterReceiver(mGattUpdateReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mServiceConnection)
        mBluetoothLeService = null
    }

    private fun showTxState(){
        val fldDtate : TextView = findViewById(R.id.fldState)
        when (txClass.theTXState()) {
            TxThread.State.INIT -> fldDtate.text = "Инициализация"
            TxThread.State.ERROR -> fldDtate.text = "Ошибка"
            TxThread.State.READY -> fldDtate.text = "Работа"
            else -> fldDtate.text = "???"
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setGest() {
        mDetector = GestureDetector (MyGestureListener())
        btnSlide.setOnTouchListener { v, event ->
            mDetector.onTouchEvent(event)
        }
    }


    // Get All permissions
    private fun getBtPermission() {
        //TODO заодно включение адаптора

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                BT_REQUEST_PERMISSION )
        } else {
            Log.d("MyLog", "Permision granted")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BT_REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
         //       isPermissionGranted = true
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
        }


    }


    // Code to manage Service lifecycle.
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
                (service as BluetoothLeService.LocalBinder).getService()
            if (!mBluetoothLeService!!.initialize()) {
                finish()
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService!!.connect(StoreVals.DeviceAddress)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBluetoothLeService = null
        }
    }


    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private val mGattUpdateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == BluetoothLeService.ACTION_GATT_CONNECTED) {
                mConnected = true
                //TODO updateConnectionState(R.string.connected)
                invalidateOptionsMenu()
            } else if (action == BluetoothLeService.ACTION_GATT_DISCONNECTED) {
                mConnected = false
                //TODO updateConnectionState(R.string.disconnected)
                //invalidateOptionsMenu()
            } else if (action == BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED) {
                // Show all the supported services and characteristics on the user interface.
                //TODO    displayGattServices(mBluetoothLeService!!.supportedGattServices)
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
               //TODO displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA))
                //val mBluetoothGattCharacteristic: BluetoothGattCharacteristic? = null
                // тут пришли данные
            }
        }
    }


    private fun makeGattUpdateIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED)
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED)
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE)
        return intentFilter
    }

}
// END CLASS








class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
    override fun onDown(event: MotionEvent): Boolean {
        //TODO запускаем отсчет
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        //TODO либо вставка либо toggle
        btnSlide.setBackgroundColor(Color.BLUE)
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        //TODO меню на компоненте
        btnSlide.setBackgroundColor(Color.RED)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        clrCnt.setDefault()
        btnSlide.setBackgroundColor(clrCnt.getColor())
        return true
    }

    override fun onScroll(
        e1: MotionEvent, e2: MotionEvent,
        distanceX: Float, distanceY: Float
    ): Boolean {
        clrCnt.moveHue(distanceX * (ColorCont.MAX_HUE/2)  / btnSlide.width )
        clrCnt.moveValue(distanceY / btnSlide.width )
        btnSlide.setBackgroundColor(clrCnt.getColor())
        return true
    }

    override fun onFling(
        event1: MotionEvent, event2: MotionEvent,
        velocityX: Float, velocityY: Float
    ): Boolean {
//        Log.d("MyLog", "onFling: ")
        return true
    }

}
