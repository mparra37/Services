package parra.mario.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat

class RunningActivity : AppCompatActivity() {
    lateinit var btn_start: Button
    lateinit var btn_stop: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running)

        create_channel()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        btn_start = findViewById(R.id.btn_start)
        btn_stop = findViewById(R.id.btn_stop)

        btn_start.setOnClickListener{
            Intent(this, RunningService::class.java).also{
                it.action = RunningService.Actions.START.toString()
                startService(it)
            }
        }

        btn_stop.setOnClickListener {
            Intent(this, RunningService::class.java).also{
                it.action = RunningService.Actions.STOP.toString()
                startService(it)
            }
        }



    }


    private fun create_channel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "running_channel",
                "Running Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}