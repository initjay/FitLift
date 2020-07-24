package com.example.fitlift.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitlift.databinding.ActivityCreateUserBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateUserActivity extends AppCompatActivity {

    public static final String TAG = "CreateUserActivity";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // final needed for getText().toString()
        final ActivityCreateUserBinding binding = ActivityCreateUserBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick sign up");
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                String email = binding.etEmail.getText().toString();

                signUpUser(username, password, email);
            }
        });
    }

    private void signUpUser(String username, String password, String email) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
// Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
// Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Toast.makeText(CreateUserActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "Issue with login ", e);
                    // implement better error handling
                    // if (e == ParseException.USERNAME_TAKEN)
                    Toast.makeText(CreateUserActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
