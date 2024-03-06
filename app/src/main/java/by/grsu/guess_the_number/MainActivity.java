 package by.grsu.guess_the_number;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView showMsgTextView;
    private TextView showHintTextView;
    private EditText editNumEditText;
    private TextView attemptsLeftTextView;
    private Button btnRestart;
    private Button btnGuess;

    private TextView compNumTextView;
    int comp_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        comp_num = GuessNum.rndCompNum();
        btnGuess = findViewById(R.id.btn_guess);

        editNumEditText = findViewById(R.id.edit_num);
        showMsgTextView = findViewById(R.id.show_msg);
        showHintTextView = findViewById(R.id.show_hint);
        attemptsLeftTextView = findViewById(R.id.attempts_left);
        btnRestart = findViewById(R.id.btn_restart);


        // Установка размера и цвета шрифта
        showMsgTextView.setTextSize(24);
        showMsgTextView.setTextColor(getResources().getColor(R.color.red));

        showHintTextView.setTextSize(18);
        showHintTextView.setTextColor(getResources().getColor(R.color.blue));

        attemptsLeftTextView.setTextSize(20);
        attemptsLeftTextView.setTextColor(getResources().getColor(R.color.green));

        // Загаданное число
        compNumTextView = findViewById(R.id.comp_num_label);
        compNumTextView.setText(String.valueOf(comp_num));


        View.OnClickListener btnGuessListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int user_num = Integer.parseInt(editNumEditText.getText().toString());

                    if (user_num == comp_num) {
                        btnGuess.setText(R.string.guessed_msg_label);
                        view.setEnabled(false);
                        Toast.makeText(getApplicationContext(), getString(R.string.congrats_msg_label), Toast.LENGTH_SHORT).show();
                    } else {

                        int left = Integer.parseInt(attemptsLeftTextView.getText().toString());

                        if (user_num > comp_num) {
                            showHintTextView.setText(R.string.show_user_number_greater);

                        } else {
                            showHintTextView.setText(R.string.show_user_number_less);
                        }
                        left--;

                        if (left == 0) {
                            showMsgTextView.setText(R.string.restart_msg_label);
                            view.setEnabled(false);
                            Toast.makeText(getApplicationContext(), getString(R.string.game_over_msg_label), Toast.LENGTH_SHORT).show();
                        }

                        attemptsLeftTextView.setText(String.valueOf(left));
                        editNumEditText.setText("");
                    }
                } catch (NumberFormatException e) {
                    // Некорректный формат числа
                    Toast.makeText(getApplicationContext(), getString(R.string.invalid_number_message), Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnGuess.setOnClickListener(btnGuessListener);
    }
    public void restart(View view) {
        comp_num = GuessNum.rndCompNum();
        compNumTextView.setText(String.valueOf(comp_num));
        btnGuess.setEnabled(true);
        btnGuess.setText(R.string.btn_guess_label);
        editNumEditText.setText("");
        showMsgTextView.setText(getString(R.string.show_msg_label));
        showHintTextView.setText(getString(R.string.show_hint_label));
        attemptsLeftTextView.setText(R.string.attempts_left_label);

    }
}