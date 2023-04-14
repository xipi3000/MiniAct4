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

    }

    override fun onClick(src: View) {
        intent = Intent(this,ElServicio::class.java)
        when(src.id) {
            R.id.btnIn ->  startService(intent.putExtra("source","sound"))
            R.id.btnSong -> startService(intent.putExtra("source","song"))
            R.id.btnFin -> stopService(intent)
        }
    }
}