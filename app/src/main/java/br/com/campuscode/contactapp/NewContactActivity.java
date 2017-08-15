package br.com.campuscode.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewContactActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    EditText et_name;
    EditText et_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        button = (Button) findViewById(R.id.bt_save);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        Bundle data = new Bundle();

        et_name = (EditText) findViewById(R.id.et_name);
        et_phone_number = (EditText) findViewById(R.id.et_phonenumber);
        data.putString("name", et_name.getText().toString());
        data.putString("phone", et_phone_number.getText().toString());

        intent.putExtra("data", data);

        setResult(1, intent);
        finish();
    }
}
