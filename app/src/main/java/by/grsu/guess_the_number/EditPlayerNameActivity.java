package by.grsu.guess_the_number;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import by.grsu.guess_the_number.databinding.ActivityEditPlayerNameBinding;

public class EditPlayerNameActivity extends AppCompatActivity {

    Button btnSaveEditName;
    EditText playerNameEdit;
    TextView newNameLabel;
    ActivityEditPlayerNameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_player_name);

        binding = ActivityEditPlayerNameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        btnSaveEditName = binding.btnSaveEditName;
        playerNameEdit = binding.playerNameEdit;
        newNameLabel = binding.newNameLabel;

        String playerName = getIntent().getStringExtra("playerName");
        if (playerName != null) {
            playerNameEdit.setText(playerName);
        }

        btnSaveEditName.setOnClickListener(this::saveEditName);
    }

    public void saveEditName (View view) {
        String newPlayerName = playerNameEdit.getText().toString();

        Intent resultIntent = getIntent();
        resultIntent.putExtra("newPlayerName", newPlayerName);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}