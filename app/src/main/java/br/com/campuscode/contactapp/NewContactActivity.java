package br.com.campuscode.contactapp;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.net.URL;

import br.com.campuscode.contactapp.networking.ApiContact;
import br.com.campuscode.contactapp.providers.ContactModel;

public class NewContactActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    EditText et_name;
    EditText et_phone_number;
    String name;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        button = (Button) findViewById(R.id.bt_save);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone_number = (EditText) findViewById(R.id.et_phonenumber);
        name = et_name.getText().toString();
        phone = et_phone_number.getText().toString();
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);

        if(!("".equals(name) || "".equals(phone))) {
            ContentValues content = new ContentValues();
            content.put(ContactModel.NAME, name);
            content.put(ContactModel.PHONE, phone);
            getContentResolver().insert(ContactModel.CONTENT_URI, content);
            UploadContact task = new UploadContact();
            task.execute();
            finish();
        } else if ("".equals(name)) {
            et_name.setError("Preencha o campo nome");
            et_name.startAnimation(animation);
        } else if ("".equals(phone)) {
            et_phone_number.setError("Preencha o campo telefone");
            et_phone_number.startAnimation(animation);
        }
    }

    private class UploadContact extends AsyncTask<URL, Integer, Integer> {
        @Override
        protected Integer doInBackground(URL... urls) {
            ApiContact api = new ApiContact();
            try {
                api.postContact(name, phone);
                return 1;
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}