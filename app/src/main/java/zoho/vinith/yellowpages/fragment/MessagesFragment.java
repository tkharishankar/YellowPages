package zoho.vinith.yellowpages.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zoho.vinith.yellowpages.R;

public class MessagesFragment extends Fragment {

    private String TAG="Message Fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"OnCreateView()");
        return inflater.inflate(R.layout.layout_messages, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"OnViewCreated");

        FloatingActionButton fab = view.findViewById(R.id.message_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.setData(Uri.parse("sms:"));
//                startActivity(sendIntent);


//                sendIntent.putExtra("address", "12125551212");
//                sendIntent.putExtra("sms_body","Body of Message");
//                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
//                startActivityForResult(i, 100);
            }
        });
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
