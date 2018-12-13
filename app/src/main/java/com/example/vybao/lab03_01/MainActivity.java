package com.example.vybao.lab03_01;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvContact;
    private final List<Contact> listContact = new ArrayList<Contact>();
    private ArrayAdapter<Contact> adapter = null;
    private void deleteContact(Contact contact){
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.deleteContact(contact);
        this.listContact.remove(contact);
        this.adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = (ListView)findViewById(R.id.listContact);

        SQLiteDatabase eventsDB  = this.openOrCreateDatabase("contactsManager",MODE_PRIVATE,null);
        final MyDatabaseHelper db = new MyDatabaseHelper(this);
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        List<Contact> list = db.getAllContact();

        this.listContact.addAll(list);

        this.adapter = new ArrayAdapter<Contact>(this,android.R.layout.simple_list_item_1,this.listContact);

        lvContact.setAdapter(this.adapter);

        registerForContextMenu(this.lvContact);

        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                new AlertDialog.Builder(this)
//                        .setMessage(selectedNote.getNoteTitle()+". Are you sure you want to delete?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                deleteNote(selectedNote);
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
                 Contact selectedContact = listContact.get(position);
                 deleteContact(selectedContact);
                 return false;
            }
        });


        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = listContact.get(position);
                selectedContact.setContactPhone("0965579869");
                selectedContact.setContactName("Do Vy Bao");
                db.updateContact(selectedContact);
                adapter.notifyDataSetChanged();

            }
        });
        
    }
}
