package by.grsu.guess_the_number;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import by.grsu.guess_the_number.databinding.ActivityMainBinding;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int digit = 2;
    int chosenMode = 0;
    int user_num = 0;
    int comp_num = 0;
    int left = 0;
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
    GridLayout numberPad;

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        comp_num = GuessNum.rndCompNum(digit);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        showMsg = binding.showMsg;
//        playerName = binding.playerName;
        attemptsLeft = binding.attemptsLeft;
        showHint = binding.showHint;
        btnRestart = binding.btnRestart;
        btnSend = binding.btnSend;
        btnSave = binding.btnSave;
//        btnEditName = binding.btnEditName;
        editNum = binding.editNum;
        numberPad = binding.numberPad;

        guessBuilder = new StringBuilder();

        binding.compNumLabel.setText(String.valueOf(comp_num));

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
                        } else if (buttonText.equals(getString(R.string.btn_guess_label))) {
                            guess();
                        } else {
                            appendToGuess(buttonText);
                        }
                    }
                });
            }
        }
    }

    public void guess() {
        left = Integer.parseInt(attemptsLeft.getText().toString());

        lineIsEmpty = editNum.getText().toString().isEmpty();
        if (!lineIsEmpty) {
            user_num = Integer.parseInt(editNum.getText().toString());
            attemptsLeft.setText((Integer.toString(left - 1)));
        } else {
            showHint.setText(R.string.show_user_number_missing);
            return;
        }

        if (comp_num == user_num) {
            showMsg.setText(R.string.show_win_msg_label);
            disableNumberPad();
            enableSaveAndSend();
            showHint.setText(R.string.show_congrat_msg_label);
            Toast toast = Toast.makeText(getApplicationContext(), R.string.show_congrat_msg_label, Toast.LENGTH_LONG);
            toast.show();
        } else {
            attemptsLeft.setText((Integer.toString(left - 1)));
            if (user_num > comp_num) {
                showHint.setText(R.string.show_user_number_greater);
            } else {
                showHint.setText(R.string.show_user_number_less);
            }
            if (left - 1 == 0) {
                showMsg.setText(R.string.show_restart_msg_label);
                showHint.setText(String.format(getString(R.string.hidden_number) + comp_num));
                disableNumberPad();
                enableSaveAndSend();
                Toast toast = Toast.makeText(getApplicationContext(), R.string.show_lose_msg, Toast.LENGTH_LONG);
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
        switch (digit) {
            case 2:
                TextView hint2 = findViewById(R.id.show_hint);
                hint2.setText(R.string.show_hint_label_2);
                TextView attempts2 = findViewById(R.id.attempts_left);
                attempts2.setText(R.string.attempts_left_label_2);
                break;
            case 3:
                TextView hint3 = findViewById(R.id.show_hint);
                hint3.setText(R.string.show_hint_label_3);
                TextView attempts3 =  findViewById(R.id.attempts_left);
                attempts3.setText(R.string.attempts_left_label_3);
                break;
            case 4:
                TextView hint4 = findViewById(R.id.show_hint);
                hint4.setText(R.string.show_hint_label_4);
                TextView attempts4 = findViewById(R.id.attempts_left);
                attempts4.setText(R.string.attempts_left_label_4);
                break;
        }
    }

    public void enableButtons() {
        btnSend.setEnabled(false);
        btnSave.setEnabled(false);
        enableNumberPad();
    }

    public void restart(View view) {
        enableButtons();
        guessed = false;
        comp_num = GuessNum.rndCompNum(digit);
        clearCache();
        showMsg.setText(R.string.show_msg_label);
        selectShow();
    }

//    public String getCurrentDateAndTime() {
//        Date currentDate = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
//        String dateText = dateFormat.format(currentDate);
//        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//        String timeText = timeFormat.format(currentDate);
//
//        return dateText + " " + timeText;
//    }

    public void enableSaveAndSend() {
        btnSave.setEnabled(true);
        btnSend.setEnabled(true);
    }

    public void chooseMode(View view) {
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