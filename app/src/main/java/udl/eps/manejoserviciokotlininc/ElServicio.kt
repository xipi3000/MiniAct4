package udl.eps.manejoserviciokotlininc

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class ElServicio: Service() {

    private var player: MediaPlayer? = null

    override fun onBind(p0: Intent?): IBinder? {
        val type = p0?.getStringExtra("type")
        if(type=="sound"){
            player = MediaPlayer.create(this, R.raw.train);
        }
        else{
            val url = "https://www.youtube.com/watch?v=V1l6kxQNq54&pp=ygUTbXVyZWF4IHBlcmZlY3QgZ2lybA%3D%3D"
            player = MediaPlayer().apply {
                setDataSource(url)
                prepare() // might take long! (for buffering, etc)
                start()
            }
            //player = MediaPlayer.create(this, R.raw.PerfectGirl);
        }

        return null
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, R.string.creaserv, Toast.LENGTH_LONG).show()

        player!!.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Toast.makeText(this, R.string.iniserv, Toast.LENGTH_LONG).show()

        return startId
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, R.string.finaserv, Toast.LENGTH_LONG).show()
        player?.release()

    }
}