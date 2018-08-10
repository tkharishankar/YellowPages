package zoho.vinith.yellowpages.fragment;

import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
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
import java.util.HashMap;

import zoho.vinith.yellowpages.RecyclerItemClickListener;
import zoho.vinith.yellowpages.adapter.ContactAdapter;
import zoho.vinith.yellowpages.database.YellowPageDatabase;
import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.model.CallLogInfo;
import zoho.vinith.yellowpages.model.ContactInfo;

import static android.content.ContentValues.TAG;

public class ContactListFragment extends Fragment {

    private String TAG = "Contact Fragment";

    YellowPageDatabase dbHandler;
    private ArrayList<ContactInfo> contactClassList;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"OnCreateView()");
        return inflater.inflate(R.layout.layout_contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG,"OnViewCreated");

        recyclerView = view.findViewById(R.id.contact_recycler_view);
        dbHandler = new YellowPageDatabase(getActivity(), null, null, 1);
        contactClassList = new ArrayList<>();
        contactClassList = dbHandler.getContactListfromDB();

        contactAdapter = new ContactAdapter(contactClassList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        ContactInfo contactInfo = contactClassList.get(position);
                        dialPhoneNumber(contactInfo.getPhone_Number());
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        final ContactInfo contactInfo = contactClassList.get(position);

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setMessage("Are you sure wanted to remove "+ contactInfo.getName() +" from list?" );
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                dbHandler.deleteFavContact(contactInfo.getPhone_Number());
                                                contactClassList = dbHandler.getContactListfromDB();
                                                contactAdapter.setContactList(contactClassList);
                                                contactAdapter.notifyDataSetChanged();
                                            }});
                                alertDialogBuilder.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                return;
                                            }});
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                })
        );

        FloatingActionButton fab = view.findViewById(R.id.contact_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 100);
            }
        });

    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Log.i(TAG + " Phone Number" , phoneNumber);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
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
                String photoUri;
                if (c.moveToFirst()) {
                    if(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI)) == null ||c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI)).equals("")){
                        photoUri = "none";
                    }
                    else{
                        photoUri = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));
                    }
                    dbHandler.addFavContact(new ContactInfo(
                            c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                            c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                            photoUri));
                }
            }
        }
        contactClassList = dbHandler.getContactListfromDB();
        contactAdapter.setContactList(contactClassList);
        contactAdapter.notifyDataSetChanged();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"OnPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"OnResume()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"OnDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"OnDestroy()");
    }
}
