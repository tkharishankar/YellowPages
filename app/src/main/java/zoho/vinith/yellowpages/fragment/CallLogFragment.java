package zoho.vinith.yellowpages.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.RecyclerItemClickListener;
import zoho.vinith.yellowpages.adapter.CallLogAdapter;
import zoho.vinith.yellowpages.database.YellowPageDatabase;
import zoho.vinith.yellowpages.model.CallLogInfo;
import zoho.vinith.yellowpages.model.ContactInfo;

public class CallLogFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10;
    private static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 20;
    private String TAG = "Call Fragment";

    YellowPageDatabase dbHandler;
    private ArrayList<CallLogInfo> callLogInfoList;
    private RecyclerView recyclerView;
    private CallLogAdapter callLogAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate()");

        if (getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG},
                    MY_PERMISSIONS_REQUEST_READ_CALL_LOG);
            return;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"OnCreateView()");
        return inflater.inflate(R.layout.layout_call_logs, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG,"OnViewCreated");

        recyclerView = view.findViewById(R.id.call_recycler_view);
        dbHandler = new YellowPageDatabase(getActivity(), null, null, 1);
        callLogInfoList = new ArrayList<>();
        callLogAdapter = new CallLogAdapter(callLogInfoList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(callLogAdapter);
        callLogAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        CallLogInfo callLogInfo = callLogInfoList.get(position);
                        dialPhoneNumber(callLogInfo.getPhone_Number());
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        injectCallLogList(callLogAdapter);

    }

    private void injectCallLogList(CallLogAdapter callLogAdapter) {
        ArrayList<ContactInfo> contactInfoList = new ArrayList<>();
        contactInfoList = dbHandler.getContactListfromDB();
        Cursor managedCursor = getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            for(ContactInfo contactInfo : contactInfoList) {
                if(contactInfo.getPhone_Number().equals(phNumber)) {
                    String callType = managedCursor.getString(type);
                    String callDate = managedCursor.getString(date);
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dateOfCall = formatter.format(callDayTime);
                    String callDuration = managedCursor.getString(duration);
                    callDuration = timeConversion(Integer.parseInt(callDuration));
                    String dir = null;
                    int dircode = Integer.parseInt(callType);
                    switch (dircode) {
                        case CallLog.Calls.OUTGOING_TYPE:
                            dir = "OUTGOING";
                            break;

                        case CallLog.Calls.INCOMING_TYPE:
                            dir = "INCOMING";
                            break;

                        case CallLog.Calls.MISSED_TYPE:
                            dir = "MISSED";
                            break;
                    }
                    callLogInfoList.add(new CallLogInfo(contactInfo.getName(),phNumber,contactInfo.getPhoto(),dateOfCall,dir,callDuration));
                }
            }
        }
        //managedCursor.close();
        Collections.reverse(callLogInfoList);
        callLogAdapter.setCallLogInfoList(callLogInfoList);
        callLogAdapter.notifyDataSetChanged();

    }

    private static String timeConversion(int totalSeconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;

        return String.format("%02d:%02d:%02d", hours,minutes,seconds);
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

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! do the
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//            case MY_PERMISSIONS_REQUEST_READ_CALL_LOG:{
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//
//                }
//                return;
//            }
//        }
//    }
    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
