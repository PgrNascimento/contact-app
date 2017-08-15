package br.com.campuscode.contactapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.campuscode.contactapp.adapters.ContactsAdapter;
import br.com.campuscode.contactapp.models.Contact;

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

        contacts = new ArrayList<Contact>();

        Contact contact = new Contact("alan", "11951342149");


        contacts.add(contact);

        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts);
        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        lv_contacts.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NewContactActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle teste = data.getBundleExtra("data");
        lv_contacts = (ListView) findViewById(R.id.lv_contacts);
        String name = teste.getString("name");
        String phone = teste.getString("phone");

        Contact contact = new Contact(name, phone);
        contacts.add(contact);

        ContactsAdapter adapter = new ContactsAdapter(this, contacts);
        lv_contacts.setAdapter(adapter);
    }
}
