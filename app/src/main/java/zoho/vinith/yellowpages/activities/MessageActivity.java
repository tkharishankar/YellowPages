package zoho.vinith.yellowpages.activities;
import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.RecyclerItemClickListener;
import zoho.vinith.yellowpages.adapter.MessageAdapter;
import zoho.vinith.yellowpages.model.MessageInfo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    ArrayList<MessageInfo> smsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.activity_message_recycler_view);

        smsList = new ArrayList<MessageInfo>();

        messageAdapter = new MessageAdapter(smsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setHasFixedSize(true);
        messageAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent();
                        intent.putExtra("name",smsList.get(position).getName());
                        intent.putExtra("text",smsList.get(position).getText());
                        intent.putExtra("number",smsList.get(position).getPhone_Number());
                        intent.putExtra("date",smsList.get(position).getTime());
                        intent.putExtra("id",smsList.get(position).getID()+"");
                        setResult(200,intent);
                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        readMessages();

    }

    private void readMessages() {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = getContentResolver().query(uri, null, null ,null,null);
        startManagingCursor(c);

        // Read the sms data and store it in the list
        if(c.moveToFirst()) {
            for(int i=0; i < c.getCount(); i++) {
                MessageInfo sms = new MessageInfo();
                sms.setText(c.getString(c.getColumnIndexOrThrow("body")).toString());
                sms.setPhone_Number(c.getString(c.getColumnIndexOrThrow("address")).toString());
                sms.setID(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("_id")).toString()));

                Date callDayTime = new Date(Long.valueOf(c.getString(c.getColumnIndexOrThrow("date")).toString()));
                Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateOfCall = formatter.format(callDayTime);
                sms.setTime(dateOfCall);

                if(sms.getName()==null || sms.getName().equals("")){
                    sms.setName(sms.getPhone_Number());
                }
                smsList.add(sms);
                c.moveToNext();
            }
        }
        c.close();
        messageAdapter.setInboxList(smsList);
        messageAdapter.notifyDataSetChanged();
        // Set smsList in the ListAdapter
        //setListAdapter(new ListAdapter(this, smsList));
    }

    public String getContactName(final String phoneNumber, Context context)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName="";
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }

}
