package by.grsu.guess_the_number;



import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import by.grsu.guess_the_number.databinding.ActivityNotificationBinding;



public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();

        if (getIntent().hasExtra("GUESS!")) {
            // Toast.makeText(this, "Welcome, back!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void play(View view) {
        Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
