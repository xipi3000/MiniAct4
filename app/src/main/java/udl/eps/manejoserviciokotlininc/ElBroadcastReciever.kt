package udl.eps.manejoserviciokotlininc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ElBroadcastReciever : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0,R.string.broadcastOnRecieve,Toast.LENGTH_LONG).show()
        if(p1!!.hasExtra("source")){
            p0?.startService(p1)
        }
        else{
            p0?.stopService(p1)
        }

    }

}