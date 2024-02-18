package by.grsu.guess_the_number;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView showMsgTextView;
    private TextView showHintTextView;
    private EditText editNumEditText;
    private Button btnGuess;
    private TextView attemptsLeftTextView;
    private Button btnRestart;
    int comp_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        showMsgTextView = findViewById(R.id.show_msg);
        showHintTextView = findViewById(R.id.show_hint);
        attemptsLeftTextView = findViewById(R.id.attempts_left);

        // Установка размера и цвета шрифта для showMsgTextView
        showMsgTextView.setTextSize(24);
        showMsgTextView.setTextColor(getResources().getColor(R.color.red));

        // Установка размера и цвета шрифта для showHintTextView
        showHintTextView.setTextSize(18);
        showHintTextView.setTextColor(getResources().getColor(R.color.blue));

        // Установка размера и цвета шрифта для attemptsLeftTextView
        attemptsLeftTextView.setTextSize(20);
        attemptsLeftTextView.setTextColor(getResources().getColor(R.color.green));


        editNumEditText = findViewById(R.id.edit_num);
        btnGuess = findViewById(R.id.btn_guess);
        btnRestart = findViewById(R.id.btn_restart);

        // Установка текста из строковых ресурсов
        showMsgTextView.setText(R.string.show_msg_label);
        showHintTextView.setText(R.string.show_hint_label);
        editNumEditText.setHint(R.string.edit_hint_label);
        btnGuess.setText(R.string.btn_guess_label);
        attemptsLeftTextView.setText(getString(R.string.attempts_left_label, 5));
        btnRestart.setText(R.string.btn_restart_label);

        comp_num = GuessNum.rndCompNum0();
        TextView compNumTextView = findViewById(R.id.comp_num_label);
        compNumTextView.setText(String.valueOf(comp_num));

//todo: написать restart
//        private void restart () {
//            showMsgTextView.setText(getString(R.string.show_msg_label));
//            showHintTextView.setText(getString(R.string.show_hint_label));
//            attemptsLeftTextView.setText(getString(R.string.attempts_left_label));
//        }

        View.OnClickListener btnGuessListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String guessedNumberStr = editNumEditText.getText().toString(); // Получаем введенное пользователем число из EditText

                try {
                    int guessedNumber = Integer.parseInt(guessedNumberStr); // Преобразуем строку в число

                    if (guessedNumber == comp_num) {
                        // Угадано
                        btnGuess.setText(getString(R.string.guessed_button_label));
                        btnGuess.setEnabled(false);
                        Toast.makeText(getApplicationContext(), getString(R.string.congrats_message), Toast.LENGTH_SHORT).show();
                    } else {
                        // Не угадано
                        Toast.makeText(getApplicationContext(), getString(R.string.try_again_message), Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    // Некорректный формат числа
                    Toast.makeText(getApplicationContext(), getString(R.string.invalid_number_message), Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnGuess.setOnClickListener(btnGuessListener);
    }


}

