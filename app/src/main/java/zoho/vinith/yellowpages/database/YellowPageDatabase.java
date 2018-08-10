package zoho.vinith.yellowpages.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import zoho.vinith.yellowpages.model.ContactInfo;
import zoho.vinith.yellowpages.model.MessageInfo;

public class YellowPageDatabase extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "yellow_page.db";

    private static final String TABLE_CONTACTS = "fav_contacts";
    private static final String CONTACT_ID = "contact_id";
    private static final String CONTACT_NAME = "contact_name";
    private static final String CONTACT_NUMBER = "contact_number";
    private static final String CONTACT_PHOTO = "contact_photo";

    private static final String TABLE_MESSAGES = "fav_messages";
    private static final String MESSAGE_ID= "message_id";
    private static final String MESSAGE_NAME = "message_name";
    private static final String MESSAGE_NUMBER = "message_number";
    private static final String MESSAGE_TEXT = "message_text";
    private static final String MESSAGE_TIME = "message_time";

    public YellowPageDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String contactTableQuery = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                CONTACT_NAME + " TEXT," +
                CONTACT_NUMBER + " TEXT," +
                CONTACT_PHOTO + " TEXT " +
                ");";
        String messageTableQuery = "CREATE TABLE " + TABLE_MESSAGES + "(" +
                MESSAGE_ID + " INTEGER PRIMARY KEY NOT NULL," +
                MESSAGE_NAME+ " TEXT," +
                MESSAGE_NUMBER + " TEXT," +
                MESSAGE_TEXT + " TEXT, " +
                MESSAGE_TIME + " TEXT " +
                ");";
        sqLiteDatabase.execSQL(contactTableQuery);
        sqLiteDatabase.execSQL(messageTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(sqLiteDatabase);
    }

    public void addFavContact(ContactInfo contactInfo) {
        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, contactInfo.getName());
        values.put(CONTACT_NUMBER, contactInfo.getPhone_Number());
        values.put(CONTACT_PHOTO, contactInfo.getPhoto());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTACTS, null, values);
    }

    public void deleteFavContact(String phoneNumber) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CONTACTS + " WHERE " + CONTACT_NUMBER + "=\"" + phoneNumber + "\";");
    }
    public void addFavMessages(MessageInfo messageInfo) {
        ContentValues values = new ContentValues();
        values.put(MESSAGE_ID, messageInfo.getID());
        values.put(MESSAGE_NAME, messageInfo.getName());
        values.put(MESSAGE_NUMBER, messageInfo.getPhone_Number());
        values.put(MESSAGE_TEXT, messageInfo.getText());
        values.put(MESSAGE_TIME, messageInfo.getTime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MESSAGES, null, values);
    }

    public void deleteFavMessage(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MESSAGES + " WHERE " + MESSAGE_ID + "=\"" + id + "\";");
    }

    public ArrayList<ContactInfo> getContactListfromDB() {
        ArrayList<ContactInfo> allContact = new ArrayList<ContactInfo>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                allContact.add(new ContactInfo(
                        c.getString(c.getColumnIndex("contact_name")),
                        c.getString(c.getColumnIndex("contact_number")),
                        c.getString(c.getColumnIndex("contact_photo"))));
            } while (c.moveToNext());
        }
        return allContact;
    }
    public ArrayList<MessageInfo> getMessagesListfromDB() {
        ArrayList<MessageInfo> allMessages = new ArrayList<MessageInfo>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGES;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                allMessages.add(new MessageInfo(Integer.parseInt(c.getString(c.getColumnIndex("message_id"))),
                        c.getString(c.getColumnIndex("message_name")),
                        c.getString(c.getColumnIndex("message_number")),
                        c.getString(c.getColumnIndex("message_text")),
                        c.getString(c.getColumnIndex("message_time"))));
            } while (c.moveToNext());
        }
        return allMessages;
    }
}
