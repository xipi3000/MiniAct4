package udl.eps.manejoserviciokotlininc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import udl.eps.manejoserviciokotlininc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        var intentBroad = Intent(this, ElBroadcastReciever::class.java)
        when(src.id) {
            R.id.btnIn ->  startService(intent.putExtra("source","sound"))
            R.id.btnSong -> startService(intent.putExtra("source","song"))
            R.id.btnBroadIn -> startService(intentBroad.putExtra("source","sound"))
            R.id.btnBroadSong -> startService(intentBroad.putExtra("source","song"))
            R.id.btnFin -> stopService(intent)
            R.id.btnBroadFin -> stopService(intentBroad)
        }
    }
}