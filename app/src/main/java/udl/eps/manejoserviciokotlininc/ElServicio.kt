package udl.eps.manejoserviciokotlininc

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.provider.MediaStore
import android.widget.Toast

class ElServicio: Service() {

    private var playerSound: MediaPlayer? = null
    private var playerMusic: MediaPlayer? = null
    private var playerUri: MediaPlayer? = null
    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, R.string.creaserv, Toast.LENGTH_LONG).show()
        playerSound = MediaPlayer.create(this,R.raw.train)
        playerSound!!.isLooping = true
        playerMusic = MediaPlayer.create(this,R.raw.perfect_girl)
        playerMusic!!.isLooping = true
        playerUri = MediaPlayer()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Toast.makeText(this, R.string.iniserv, Toast.LENGTH_LONG).show()
        if(intent?.getStringExtra("source")=="sound"){
            playerSound?.start()
        }
        else if(intent?.getStringExtra("source")=="song") {
            playerMusic?.start()

        }
        else if(intent!!.hasExtra("uri")){
            println("--->"+intent.getStringExtra("uri"))
            Toast.makeText(this,"Opening uri",Toast.LENGTH_LONG).show()
            if(playerUri!!.isPlaying){
                playerUri?.stop()
            }
            playerUri?.reset()
            playerUri?.setDataSource(this, Uri.parse(intent.getStringExtra("uri")))
            playerUri?.prepare()
            playerUri?.start()
        }
        return startId
    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, R.string.finaserv, Toast.LENGTH_LONG).show()
        if(playerSound!!.isPlaying)playerSound?.stop()
        if(playerMusic!!.isPlaying)playerMusic?.stop()
        if(playerUri!!.isPlaying)playerUri?.stop()


    }
}