package zoho.vinith.yellowpages.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.adapter.CallLogAdapter;
import zoho.vinith.yellowpages.database.YellowPageDatabase;
import zoho.vinith.yellowpages.model.CallLogInfo;
import zoho.vinith.yellowpages.model.ContactInfo;

public class CallLogFragment extends Fragment {

    private String TAG = "Call Fragment";

    YellowPageDatabase dbHandler;
    private ArrayList<CallLogInfo> callLogInfoList;
    private RecyclerView recyclerView;
    private CallLogAdapter callLogAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate()");
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

        injectCallLogList(callLogAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(callLogAdapter);
        callLogAdapter.notifyDataSetChanged();
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
                    String callDuration = managedCursor.getString(duration);
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
                    callLogInfoList.add(new CallLogInfo(contactInfo.getName(),phNumber,contactInfo.getPhoto(),dir,callDuration +" sec"));
                }
            }
        }
        //managedCursor.close();
        callLogAdapter.setCallLogInfoList(callLogInfoList);
        callLogAdapter.notifyDataSetChanged();

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
