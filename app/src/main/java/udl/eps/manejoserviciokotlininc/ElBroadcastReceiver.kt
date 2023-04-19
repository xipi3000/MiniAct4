package udl.eps.manejoserviciokotlininc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast

class ElBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0?.applicationContext,R.string.broadcastOnRecieve,Toast.LENGTH_LONG).show()
        val intent = Intent(p0!!.applicationContext,ElServicio :: class.java)
        if(p1!!.action !=null){
            if(p1.action.equals("android.intent.action.HEADSET_PLUG")){
                Toast.makeText(p0?.applicationContext,R.string.headsetPluged,Toast.LENGTH_LONG).show()
                p0!!.startService(intent.putExtra("source","song"))
            }
            else{
                Toast.makeText(p0?.applicationContext,R.string.headsetUnpluged,Toast.LENGTH_LONG).show()
                p0?.stopService(intent)
            }
        }
        if(p1!!.hasExtra("source")){
            val source = p1!!.getStringExtra("source")
            if (source=="sound"){
                Toast.makeText(p0?.applicationContext,R.string.broadcastSound,Toast.LENGTH_LONG).show()
                p0!!.startService(intent.putExtra("source","sound"))
            }
            else if (source=="sound"){
                Toast.makeText(p0?.applicationContext,R.string.broadcastSong,Toast.LENGTH_LONG).show()
                p0!!.startService(intent.putExtra("source","song"))
            }

        }
        else{
            Toast.makeText(p0?.applicationContext,R.string.broadcastStop,Toast.LENGTH_LONG).show()

            p0?.stopService(intent)
        }


    }

}