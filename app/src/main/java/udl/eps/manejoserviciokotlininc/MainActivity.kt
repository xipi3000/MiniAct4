package udl.eps.manejoserviciokotlininc

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import udl.eps.manejoserviciokotlininc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
    private var receiver = ElBroadcastReceiver()

    var pickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: androidx.activity.result.ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent: Intent? = result.data
            Toast.makeText(this,intent?.data.toString(),Toast.LENGTH_LONG).show()
            val intentService = Intent(this,ElServicio::class.java)
            intentService.putExtra("uri",intent?.data.toString())
            startService(intentService)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        val mBroadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent) {
                val mAction = intent.action
                if (Intent.ACTION_HEADSET_PLUG == mAction) {
                    if (intent.getIntExtra("state", -1) == 0) {
                        Toast.makeText(applicationContext, "AUX not plugged in", Toast.LENGTH_LONG).show()
                    }
                    if (intent.getIntExtra("state", -1) == 1) {
                        Toast.makeText(applicationContext, "AUX plugged in", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Declaring a receiver filter for registering
        val mReceiverFilter = IntentFilter(Intent.ACTION_HEADSET_PLUG)

        // Registering both broadcast receiver with receiver filter
        registerReceiver(mBroadcastReceiver, mReceiverFilter)







        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        headsetRegister()
        setContentView(binding.root)
        binding.btnIn.setOnClickListener(this)
        binding.btnSong.setOnClickListener(this)
        binding.btnFin.setOnClickListener(this)
        binding.btnBroadFin.setOnClickListener(this)
        binding.btnBroadSong.setOnClickListener(this)
        binding.btnBroadIn.setOnClickListener(this)
        binding.btnUri.setOnClickListener(this)

    }

    override fun onClick(src: View) {
        intent = Intent(this,ElServicio::class.java)
        var intentBroad = Intent(this, ElBroadcastReceiver::class.java)
        var intentUri = Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        when(src.id) {
            R.id.btnIn ->  startService(intent.putExtra("source","sound"))
            R.id.btnSong -> startService(intent.putExtra("source","song"))
            R.id.btnFin -> stopService(intent)
            R.id.btnBroadIn -> sendBroadcast(intentBroad.putExtra("source","sound"))
            R.id.btnBroadSong -> sendBroadcast(intentBroad.putExtra("source","song"))
            R.id.btnBroadFin -> sendBroadcast(intentBroad)
            R.id.btnUri -> pickerLauncher.launch(intentUri)
        }
    }

    private fun headsetRegister() {
        registerReceiver(receiver,filter)
    }

    private fun headsetUnregister() {
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        headsetUnregister()
    }
}