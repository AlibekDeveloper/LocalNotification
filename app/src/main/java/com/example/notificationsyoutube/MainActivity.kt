package com.example.notificationsyoutube

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationsyoutube.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIF_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotifChannel()

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

            val notif = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Samle Title")
                .setContentText("This is sample body notification")
                .setSmallIcon(R.drawable.ic_baseline_info_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

        val notifManeger = NotificationManagerCompat.from(this)

        binding.btn.setOnClickListener {
            notifManeger.notify(NOTIF_ID, notif)
        }
    }

    private fun createNotifChannel() {
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           val channel = NotificationChannel(
               CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
           ).apply {
               lightColor = Color.GREEN
               enableLights(true)
           }
           val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
           manager.createNotificationChannel(channel)
       }
    }
}