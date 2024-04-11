package by.grsu.guess_the_number;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import by.grsu.guess_the_number.databinding.ActivityMainBinding;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    int digit = 2;
    int chosenMode = 0;
    int user_num = 0;
    int comp_num = 0;
    int left = 0;
    private static final int EDIT_PLAYER_NAME_REQUEST_CODE = 1;
    private static final String KEY_EDIT_NUM = "edit_num";
    private static final String KEY_ATTEMPTS_LEFT = "attempts_left";
    private static final String KEY_SHOW_MSG = "show_msg";
    private static final String KEY_SHOW_HINT = "show_hint";
    private static final String KEY_PLAYER_NAME = "player_name";
    private String originalName;
    boolean guessed = false;
    boolean lineIsEmpty;
    StringBuilder guessBuilder;
    Button btnSend;
    Button btnSave;
    Button btnEditName;
    Button btnRestart;
    TextView editNum;
    TextView attemptsLeft;
    TextView showMsg;
    TextView showHint;
    TextView playerName;
    TextView contextView;
    GridLayout numberPad;

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Log.i(LOG_TAG, "Угадай число.onCreate()");

        comp_num = GuessNum.rndCompNum(digit);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        originalName = getString(R.string.original_name);

        showMsg = binding.showMsg;
        playerName = binding.playerName;
        attemptsLeft = binding.attemptsLeft;
        showHint = binding.showHint;
        btnRestart = binding.btnRestart;
        btnSend = binding.btnSend;
        btnSave = binding.btnSave;
        btnEditName = binding.btnEditName;
        editNum = binding.editNum;
        numberPad = binding.numberPad;
        contextView = binding.contextView;

        guessBuilder = new StringBuilder();
        for (int i = 0; i < numberPad.getChildCount(); i++) {
            View child = numberPad.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String buttonText = button.getText().toString();
                        if (buttonText.equals(getString(R.string.btn_clear_label))) {
                            clearCache();
                        }
                        else if (buttonText.equals(getString(R.string.btn_guess_label))){
                            guess();
                        }
                        else {
                            appendToGuess(buttonText);
                        }
                    }
                });
            }
        }
        registerForContextMenu(contextView);
    }

    @Override
    protected void onStart() {
        Log.i(LOG_TAG, "Угадай число.onStart()");

        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(LOG_TAG, "Угадай число.onStop");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(LOG_TAG, "Угадай число.onDestroy()");

        super.onDestroy();
    }
    @Override
    protected void onResume() {
        Log.i(LOG_TAG, "Угадай число.onResume()");

        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.i(LOG_TAG, "Угадай число.onRestart()");

        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(LOG_TAG, "Угадай число.onPause()");

        super.onPause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        editNum.setText(savedInstanceState.getString(KEY_EDIT_NUM));
        attemptsLeft.setText(savedInstanceState.getString(KEY_ATTEMPTS_LEFT));
        showMsg.setText(savedInstanceState.getString(KEY_SHOW_MSG));
        showHint.setText(savedInstanceState.getString(KEY_SHOW_HINT));
        playerName.setText(savedInstanceState.getString(KEY_PLAYER_NAME));

        Log.i(LOG_TAG, "instance onRestore");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_EDIT_NUM, editNum.getText().toString());
        outState.putString(KEY_ATTEMPTS_LEFT, attemptsLeft.getText().toString());
        outState.putString(KEY_SHOW_MSG, showMsg.getText().toString());
        outState.putString(KEY_SHOW_HINT, showHint.getText().toString());
        outState.putString(KEY_PLAYER_NAME, playerName.getText().toString());

        Log.i(LOG_TAG, "instance onSave");
        super.onSaveInstanceState(outState);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.showCompNum) {
            Toast.makeText(this, "" + comp_num, Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.addAttempt) {
            left = Integer.parseInt(attemptsLeft.getText().toString());
            attemptsLeft.setText((Integer.toString(left + 1)));
        }
        return super.onContextItemSelected(item);
    }

    public void guess() {
        left = Integer.parseInt(attemptsLeft.getText().toString());

        lineIsEmpty = editNum.getText().toString().isEmpty();
        if (!lineIsEmpty) {
            user_num = Integer.parseInt(editNum.getText().toString());
            attemptsLeft.setText((Integer.toString(left - 1)));
        }
        else {
            showHint.setText(R.string.show_user_number_missing);
            return;
        }

        if (comp_num == user_num) {
            showMsg.setText(R.string.show_win_msg_label);
            disableNumberPad();
            enableSaveAndSend();
            showHint.setText(R.string.show_congrat_msg_label);
            Toast toast = Toast.makeText(getApplicationContext(),R.string.show_congrat_msg_label,Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            attemptsLeft.setText((Integer.toString(left - 1)));
            if (user_num > comp_num) {
                showHint.setText(R.string.show_user_number_greater);
            }
            else {
                showHint.setText(R.string.show_user_number_less);
            }
            if (left - 1 == 0){
                showMsg.setText(R.string.show_restart_msg_label);
                showHint.setText(String.format(getString(R.string.hidden_number) + comp_num));
                disableNumberPad();
                enableSaveAndSend();
                Toast toast = Toast.makeText(getApplicationContext(),R.string.show_lose_msg, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void clearCache() {
        guessBuilder.setLength(0);
        editNum.setText("");
    }

    public void appendToGuess(String buttonDigit) {
        if (guessBuilder.length() < digit) {
            guessBuilder.append(buttonDigit);
            editNum.setText(guessBuilder.toString());
        }
    }

    public void disableNumberPad() {
        GridLayout numberPad = findViewById(R.id.numberPad);
        for (int i = 0; i < numberPad.getChildCount(); i++) {
            View child = numberPad.getChildAt(i);
            if (child instanceof Button) {
                child.setEnabled(false);
            }
        }
    }

    public void enableNumberPad() {
        GridLayout numberPad = findViewById(R.id.numberPad);
        for (int i = 0; i < numberPad.getChildCount(); i++) {
            View child = numberPad.getChildAt(i);
            if (child instanceof Button) {
                child.setEnabled(true);
            }
        }
    }

    public void selectShow() {
        switch (digit){
            case 2:
                TextView hint2 = (TextView) findViewById(R.id.show_hint);
                hint2.setText(R.string.show_hint_label_2);
                TextView attempts2 = (TextView) findViewById(R.id.attempts_left);
                attempts2.setText(R.string.attempts_left_label_2);
                break;
            case 3:
                TextView hint3 = (TextView) findViewById(R.id.show_hint);
                hint3.setText(R.string.show_hint_label_3);
                TextView attempts3 = (TextView) findViewById(R.id.attempts_left);
                attempts3.setText(R.string.attempts_left_label_3);
                break;
            case 4:
                TextView hint4 = (TextView) findViewById(R.id.show_hint);
                hint4.setText(R.string.show_hint_label_4);
                TextView attempts4 = (TextView) findViewById(R.id.attempts_left);
                attempts4.setText(R.string.attempts_left_label_4);
                break;
        }
    }

    public void enableButtons () {
        btnSend.setEnabled(false);
        btnSave.setEnabled(false);
        enableNumberPad();
    }

    public void restart(View view) {
        notif();
        enableButtons();
        guessed = false;
        comp_num = GuessNum.rndCompNum(digit);
        clearCache();
        showMsg.setText(R.string.show_msg_label);
        selectShow();
    }

    public String getCurrentDateAndTime () {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);

        return dateText + " " + timeText;
    }

    public void enableSaveAndSend(){
        btnSave.setEnabled(true);
        btnSend.setEnabled(true);
    }

    public String getGameInformationToSend() {
        left = Integer.parseInt(attemptsLeft.getText().toString());

        if (guessed) {
            return getString(R.string.send_and_save_win_msg) + left + getString(R.string.send_and_save_win_number) + comp_num;
        }
        return getString(R.string.send_and_save_lose_number) + comp_num;
    }

    public String getGameInformationToSave() {
        left = Integer.parseInt(attemptsLeft.getText().toString());
        String currentDateTime = getCurrentDateAndTime();

        if (guessed){

            return getString(R.string.send_and_save_win_msg) + left + getString(R.string.save_date_and_time) + currentDateTime + getString(R.string.send_and_save_win_number) + comp_num;
        }
        return getString(R.string.send_and_save_lose_number) + comp_num + getString(R.string.save_date_and_time)  + currentDateTime;
    }

    public void send(View view) {
        String results = getGameInformationToSend();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.info_about_game));
        sendIntent.putExtra(Intent.EXTRA_TEXT, results);
        sendIntent.setType("text/plain");

        PackageManager packageManager = getPackageManager();
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.send_info)));
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.no_situable_apps, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @SuppressLint("MissingPermission")
    public void notif() {
        Intent guessIntent = new Intent(this, NotificationActivity.class);
        guessIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        guessIntent.putExtra("GUESS", true);
        PendingIntent guessPendingIntent = PendingIntent.getActivity(this, 1, guessIntent, PendingIntent.FLAG_IMMUTABLE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Guess the number!")
                .setContentText("You haven't been in for a long time, try to guess the number!")
                .setAutoCancel(true)
                .addAction(R.drawable.ic_guess_name, "Guess!", guessPendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, builder.build());
    }

    public void save(View view) {
        String gameResults = getGameInformationToSave();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.info_about_game));
        intent.putExtra(Intent.EXTRA_TEXT, gameResults);

        intent.setPackage("com.google.android.keep");
        intent.setClassName("com.google.android.keep", "com.google.android.keep.activities.ShareReceiverActivity");

        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, getString(R.string.google_keep_not_installed), Toast.LENGTH_SHORT).show();
        }
    }

    public void editName (View view) {
        Intent intent = new Intent(MainActivity.this, EditPlayerNameActivity.class);
        intent.putExtra("playerName", originalName);
        ((Activity) MainActivity.this).startActivityForResult(intent, EDIT_PLAYER_NAME_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PLAYER_NAME_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String newPlayerName = data.getStringExtra("newPlayerName");
                if (newPlayerName != null) {
                    originalName = newPlayerName;
                    playerName.setText(originalName);
                }
            }
        }
    }

    public void aboutDeveloperMenu(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AboutDeveloperActivity.class);
        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show();
        }
    }

    public void settingsMenu(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.btn_modes_label);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                digit = chosenMode + 2;
                guessed = false;
                comp_num = GuessNum.rndCompNum(digit);
                selectShow();
                clearCache();
                enableButtons();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setSingleChoiceItems(R.array.diaps_array, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                chosenMode = which;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        ListView listView = dialog.getListView();
        listView.setItemChecked(digit - 2, true);
    }
}
