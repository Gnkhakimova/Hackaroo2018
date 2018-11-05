package com.example.gulnoza.doctorist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button log = (Button) findViewById(R.id.btnLogin);
        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToMainPage(v);
            }
        });
    }

    public void goToMainPage(View v) {
        EditText fullName = (EditText)findViewById(R.id.fullName);
        EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        final String fName = fullName.getText().toString();
        final String pNumber = phoneNumber.getText().toString();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("fullName",fName);
        intent.putExtra("phoneNumber",pNumber);
        startActivity(intent);
    }
}
