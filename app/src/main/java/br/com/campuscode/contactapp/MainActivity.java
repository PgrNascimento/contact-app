package br.com.campuscode.contactapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.campuscode.contactapp.adapters.ContactsAdapter;
import br.com.campuscode.contactapp.models.Contact;
import br.com.campuscode.contactapp.networking.ApiContact;
import br.com.campuscode.contactapp.providers.ContactModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lv_contacts;
    List<Contact> contacts;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_contacts = (ListView) findViewById(R.id.lv_contacts);
        button = (FloatingActionButton) findViewById(R.id.bt_save);
        button.setOnClickListener(this);

        contacts = new ArrayList<>();

        GetContacts task = new GetContacts();
        task.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listContacts();
        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        lv_contacts.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NewContactActivity.class);
        startActivityForResult(intent, 1);
    }



    private void listContacts() {
        contacts.clear();
        Cursor cursor = getContentResolver().query(ContactModel.CONTENT_URI, null, null, null, null);
        if(cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactModel._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactModel.NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactModel.PHONE));
                Contact contact = new Contact(id, name, phone);
                contacts.add(contact);
            }
            cursor.close();
        }
    }

    private class GetContacts extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... urls) {
            ApiContact api = new ApiContact();
            String result = null;
            try {
                result = api.getContacts();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            List<Contact> list = Arrays.asList(gson.fromJson(s, Contact[].class));
            for(int i = list.size() -1; i > list.size() - 3; i--) {
                Contact contact = list.get(i);
                ContentValues content = new ContentValues();
                content.put(ContactModel.NAME, contact.getName());
                content.put(ContactModel.PHONE, contact.getPhone());

                getContentResolver().insert(ContactModel.CONTENT_URI, content);
            }
            listContacts();
        }
    }
}
