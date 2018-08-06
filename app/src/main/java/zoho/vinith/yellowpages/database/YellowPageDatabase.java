package zoho.vinith.yellowpages.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import zoho.vinith.yellowpages.model.ContactInfo;

public class YellowPageDatabase extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "yellow_page.db";
    public static final String TABLE_CONTACTS = "fav_contacts";
    public static final String COLUMN_ID = "contact_id";
    public static final String COLUMN_NAME = "contact_name";
    public static final String COLUMN_NUMBER = "contact_number";
    public static final String COLUMN_PHOTO_URI= "contact_photo";

    public YellowPageDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_NUMBER  + " TEXT," +
                COLUMN_PHOTO_URI + " TEXT " +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(sqLiteDatabase);
    }

    public void addFavContact(ContactInfo contactInfo){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,Integer.parseInt(contactInfo.getId()));
        values.put(COLUMN_NAME, contactInfo.getName());
        values.put(COLUMN_NUMBER, contactInfo.getPhone_Number());
        values.put(COLUMN_PHOTO_URI, contactInfo.getPhoto());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTACTS,null,values);
    }

    public void deleteFavContact(Integer contact_id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CONTACTS + " WHERE "+ COLUMN_ID + "=\"" + contact_id + "\";");
    }

    public ArrayList<ContactInfo> databaseToString(){
        ArrayList<ContactInfo> al = new ArrayList<ContactInfo>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor c = db.rawQuery(query,null);

        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("contact_id"))!= null) {
                al.add(new ContactInfo(c.getString(c.getColumnIndex("contact_id")),
                                        c.getString(c.getColumnIndex("contact_name")),
                                        c.getString(c.getColumnIndex("contact_number")),
                                        c.getString(c.getColumnIndex("contact_photo"))));
            }
        }
        return al;
    }

}