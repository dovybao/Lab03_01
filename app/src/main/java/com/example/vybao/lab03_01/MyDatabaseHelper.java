package com.example.vybao.lab03_01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    // Phiên bản
    private static final int DATABASE_VERSION = 1;
    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "contactsManager";
    // Tên bảng: Note.
    private static final String TABLE_CONTACT = "Contact";

    private static final String COLUMN_CONTACT_ID ="id";
    private static final String COLUMN_CONTACT_NAME ="name";
    private static final String COLUMN_CONTACT_PHONE = "phone_number";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       Log.i(TAG,"MyDatabaseHelper.onCreate ... ");
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_CONTACT + "("
                + COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY," + COLUMN_CONTACT_NAME + " TEXT,"
                + COLUMN_CONTACT_PHONE + " TEXT" + ")";
        // Chạy lệnh tạo bảng.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"MyDatabaseHelper.onUpdrage ... ");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
    }
    public void addContact(Contact contact){
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + contact.getContactName());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME,contact.getContactName());
        values.put(COLUMN_CONTACT_PHONE,contact.getContactPhone());

        db.insert(TABLE_CONTACT,null,values);

        db.close();
    }
    public Contact getContact(int contactId){
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + contactId);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACT, new String[] { COLUMN_CONTACT_ID,
                        COLUMN_CONTACT_PHONE, COLUMN_CONTACT_PHONE }, COLUMN_CONTACT_ID + "=?",
                new String[] { String.valueOf(contactId) }, null, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2));
        return contact;

    }
    public int updateContact(Contact contact){
        Log.i(TAG,"MyDatabaseHelper.updateNote ... "  + contact.getContactName());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME,contact.getContactName());
        values.put(COLUMN_CONTACT_PHONE,contact.getContactPhone());

        return db.update(TABLE_CONTACT,values,COLUMN_CONTACT_ID + "=?",
                new String[]{String.valueOf(contact.getContactId())});
    }
    public void deleteContact(Contact contact){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + contact.getContactName() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT,COLUMN_CONTACT_ID + "=?",
                new String[]{String.valueOf(contact.getContactId())});
        db.close();
    }
    public List<Contact> getAllContact(){
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );
        List<Contact> contactList = new ArrayList<Contact>();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setContactId(Integer.parseInt(cursor.getString(0)));
                contact.setContactName(cursor.getString(1));
                contact.setContactPhone(cursor.getString(2));

                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        return contactList;
    }
}

