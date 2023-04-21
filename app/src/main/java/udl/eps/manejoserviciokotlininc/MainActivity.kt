package udl.eps.manejoserviciokotlininc

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import udl.eps.manejoserviciokotlininc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , View.OnClickListener {

    private lateinit var binding: ActivityMainBinding


    var pickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: androidx.activity.result.ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(this, intent?.data!!)
            mediaPlayer.prepare()
            //mediaPlayer.start()

            val intentService = Intent(this,ElServicio::class.java)
            println(intent.toString())
            startService(intentService.putExtra("uri",intent.data.toString()))
        }
    }
    private lateinit var readContactsReqPermLaunc: ActivityResultLauncher<String>
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
        binding.btnUri.setOnClickListener(this)
        readContactsReqPermLaunc = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                var intentUri = Intent(Intent.ACTION_PICK,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
                pickerLauncher.launch(intentUri)
            } else {

            }
        }

    }
    private fun requestPermissionsReadContacts() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.

            Log.i(ContentValues.TAG, "Requesting permission")
            // Request permission.
            readContactsReqPermLaunc.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

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
            R.id.btnUri -> requestPermissionsReadContacts()
        }
    }


    private fun headsetRegister() {
        var filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        var receiver = ElBroadcastReceiver()
        registerReceiver(receiver,filter)
    }

    private fun headsetUnregister() {
        var receiver = ElBroadcastReceiver()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        headsetUnregister()
    }
}