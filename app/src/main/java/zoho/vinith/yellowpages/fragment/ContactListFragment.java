package zoho.vinith.yellowpages.fragment;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import zoho.vinith.yellowpages.adapter.ContactAdapter;
import zoho.vinith.yellowpages.database.YellowPageDatabase;
import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.model.ContactInfo;

import static android.content.ContentValues.TAG;

public class ContactListFragment extends Fragment {

    YellowPageDatabase dbHandler;
    private ArrayList<ContactInfo> contactClassList;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.contact_recycler_view);
        dbHandler = new YellowPageDatabase(getActivity(), null, null, 1);
        contactClassList = new ArrayList<>();
        contactClassList = dbHandler.getContactListfromDB();

        contactAdapter = new ContactAdapter(contactClassList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, 100);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult()");
        if (requestCode == 100) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                Uri contactsData = data.getData();
                CursorLoader loader = new CursorLoader(getActivity(), contactsData, null, null, null, null);
                Cursor c = loader.loadInBackground();
                if (c.moveToFirst()) {
                    Log.i(TAG, "Contacts ID: " + c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)));
                    Log.i(TAG, "Contacts Name: " + c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    Log.i(TAG, "Contacts Phone Number: " + c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    Log.i(TAG, "Contacts Photo Uri: " + c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI)));
                    dbHandler.addFavContact(new ContactInfo(
                            c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                            c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                            c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI))
                    ));
                }
            }
        }
        contactClassList = dbHandler.getContactListfromDB();
        contactAdapter.setContectList(contactClassList);
        contactAdapter.notifyDataSetChanged();

    }
}
