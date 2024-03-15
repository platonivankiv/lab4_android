package by.grsu.guess_the_number;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import by.grsu.guess_the_number.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        comp_num = GuessNum.rndCompNum();


        editNumEditText = binding.editNum;
        showMsgTextView = binding.showMsg;
        showHintTextView = binding.showHint;
        attemptsLeftTextView = binding.attemptsLeft;
        btnRestart = binding.btnRestart;
        btnGuess = binding.btnGuess;

        binding.btnRestart.setOnClickListener(this::restart);
        binding.btnGuess.setOnClickListener(this::guess);


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


    public void guess(View view) {
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
}