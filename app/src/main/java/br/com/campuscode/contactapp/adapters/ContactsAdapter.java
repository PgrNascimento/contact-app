package br.com.campuscode.contactapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.campuscode.contactapp.R;
import br.com.campuscode.contactapp.models.Contact;

/**
 * Created by alan_mimi on 14/08/17.
 */

public class ContactsAdapter extends BaseAdapter {
    List<Contact> model;
    Context context;

    public ContactsAdapter(Context context, List<Contact> model) {
        this.model = model;
        this.context = context;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Contact contact = model.get(i);
        View viewHolder = view;
        if(viewHolder == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            viewHolder =  inflater.inflate(R.layout.contacts_item_layout, null);
        }

        TextView name = viewHolder.findViewById(R.id.tv_contact_item_name);
        TextView phone = viewHolder.findViewById(R.id.tv_contact_item_phone);

        name.setText(contact.getName());
        phone.setText(contact.getPhone());

        return viewHolder;
    }
}
