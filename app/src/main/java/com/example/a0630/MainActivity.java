package com.example.a0630;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView a;
    private ProgressBar mProgressBar;
    private TextView mPercentage;
    int x;

    Context context=this;
    @Override
    protected  void onPause(){
        super.onPause();
        if(x>=90) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent PI = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId("ID")
                    .setContentTitle("賣場垃圾桶")
                    .setContentText("垃圾已滿,請前往清理")
                    .setContentIntent(PI);

            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel Channel = new NotificationChannel("ID", "a0524", NotificationManager.IMPORTANCE_HIGH);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(Channel);
            notificationManager.notify(0, notification);
        }
        else
        {
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = (TextView) findViewById(R.id.textView);




        DatabaseReference reff;

        reff= FirebaseDatabase.getInstance().getReference();
        reff.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                String capacity = dataSnapshot.child("capacity").getValue().toString();
                String msg = "垃圾量 : ";
                a.setText(msg + capacity + "%");
                x = Integer.parseInt(capacity);
                mProgressBar.setProgress(x);


                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent PI = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification.Builder builder = new Notification.Builder(context);
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setChannelId("ID")
                            .setContentTitle("賣場垃圾桶")
                            .setContentText("垃圾已滿,請前往清理")
                            .setContentIntent(PI);

                    Notification notification = builder.build();
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    NotificationChannel Channel = new NotificationChannel("ID", "a0524", NotificationManager.IMPORTANCE_HIGH);
                    assert notificationManager != null;
                    notificationManager.createNotificationChannel(Channel);
                    notificationManager.notify(0, notification);
                }

            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
