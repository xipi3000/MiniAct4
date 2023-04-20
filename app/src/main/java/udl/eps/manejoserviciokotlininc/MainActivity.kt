package udl.eps.manejoserviciokotlininc

import android.app.Activity
import android.app.Instrumentation.ActivityResult
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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

    }

    override fun onClick(src: View) {
        intent = Intent(this,ElServicio::class.java)
        var intentBroad = Intent(this, ElBroadcastReceiver::class.java)

        when(src.id) {
            R.id.btnIn ->  startService(intent.putExtra("source","sound"))
            R.id.btnSong -> startService(intent.putExtra("source","song"))
            R.id.btnFin -> stopService(intent)
            R.id.btnBroadIn -> sendBroadcast(intentBroad.putExtra("source","sound"))
            R.id.btnBroadSong -> sendBroadcast(intentBroad.putExtra("source","song"))
            R.id.btnBroadFin -> sendBroadcast(intentBroad)
            R.id.btnUri -> pickerLauncher.launch(
                Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            )
            )
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