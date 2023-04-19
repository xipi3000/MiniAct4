package udl.eps.manejoserviciokotlininc

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class ElServicio: Service() {

    private var playerSound: MediaPlayer? = null
    private var playerMusic: MediaPlayer? = null
    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, R.string.creaserv, Toast.LENGTH_LONG).show()
        playerSound = MediaPlayer.create(this,R.raw.train)
        playerSound!!.isLooping = true
        playerMusic = MediaPlayer.create(this,R.raw.PerfectGirl_Mureaux)
        playerMusic!!.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Toast.makeText(this, R.string.iniserv, Toast.LENGTH_LONG).show()
        if(intent?.getStringExtra("source")=="sound"){
            playerSound?.start()
        }
        else {
            playerMusic?.start()
        }
        return startId
    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, R.string.finaserv, Toast.LENGTH_LONG).show()
        if(playerSound!!.isPlaying) playerSound?.stop()
        if(playerMusic!!.isPlaying) playerMusic?.stop()
        playerSound?.release()
        playerMusic?.release()
    }
}