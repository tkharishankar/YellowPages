package zoho.vinith.yellowpages.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.RecyclerItemClickListener;
import zoho.vinith.yellowpages.activities.MessageActivity;
import zoho.vinith.yellowpages.adapter.MessageAdapter;
import zoho.vinith.yellowpages.database.YellowPageDatabase;
import zoho.vinith.yellowpages.model.ContactInfo;
import zoho.vinith.yellowpages.model.MessageInfo;

public class MessagesFragment extends Fragment {

    private String TAG="Message Fragment";
    YellowPageDatabase dbHandler;
    private ArrayList<MessageInfo> messageInfoList;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;

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

        recyclerView = view.findViewById(R.id.message_recycler_view);
        dbHandler = new YellowPageDatabase(getActivity(), null, null, 1);
        messageInfoList = new ArrayList<>();
        messageInfoList = dbHandler.getMessagesListfromDB();

        messageAdapter = new MessageAdapter(messageInfoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        MessageInfo messageInfo = messageInfoList.get(position);

                        Log.i(TAG,"Address is  + " + messageInfo.getPhone_Number());
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.setData(Uri.parse("sms:"));
                        sendIntent.putExtra("address", messageInfo.getPhone_Number());
                        startActivity(sendIntent);


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        final MessageInfo messageInfo= messageInfoList.get(position);

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setMessage("Are you sure wanted to remove "+ messageInfo.getName() +" from list?" );
                        alertDialogBuilder.setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Log.i(TAG , " Id in the db: " +messageInfo.getID()+"");
                                        dbHandler.deleteFavMessage(messageInfo.getID());
                                        messageInfoList = dbHandler.getMessagesListfromDB();
                                        messageAdapter.setInboxList(messageInfoList);
                                        messageAdapter.notifyDataSetChanged();
                                    }});
                        alertDialogBuilder.setNegativeButton("Read",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getContext(),messageInfo.getText(),Toast.LENGTH_LONG).show();
                                    }});
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                })
        );



        FloatingActionButton fab = view.findViewById(R.id.message_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(getActivity(), MessageActivity.class),200);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==200)
        {
            try {
                String name = data.getStringExtra("name");
                String message = data.getStringExtra("text");
                String number = data.getStringExtra("number");
                String date = data.getStringExtra("date");
                String id = data.getStringExtra("id");

                Log.i(TAG,"Id while inserting " + id);

                dbHandler.addFavMessages(new MessageInfo(Integer.parseInt(id),name,number,message,date));

            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(),"No message is selected",Toast.LENGTH_LONG).show();
            }
            messageInfoList = dbHandler.getMessagesListfromDB();
            messageAdapter.setInboxList(messageInfoList);
            messageAdapter.notifyDataSetChanged();
        }
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
