package udl.eps.manejoserviciokotlininc

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import udl.eps.manejoserviciokotlininc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

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
        }
    }

    private fun headsetRegister() {
        var filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        var receiver = ElBroadcastReceiver()
        registerReceiver(receiver,filter)
    }
}