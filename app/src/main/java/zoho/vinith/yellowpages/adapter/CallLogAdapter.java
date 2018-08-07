package zoho.vinith.yellowpages.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.model.CallLogInfo;
import zoho.vinith.yellowpages.model.ContactInfo;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ContactViewHolder> {

    private ArrayList<CallLogInfo> callLogInfoList;

    public void setCallLogInfoList(ArrayList<CallLogInfo> callLogInfoList) {
        this.callLogInfoList = callLogInfoList;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName, contactNumber, callType, callDuration;
        ImageView imageView;

        public ContactViewHolder(View view) {
            super(view);
            contactName = view.findViewById(R.id.tvContactName);
            contactNumber = view.findViewById(R.id.tvContactNumber);
            imageView = view.findViewById(R.id.contact_image);
            callType = view.findViewById(R.id.tvCallType);
            callDuration = view.findViewById(R.id.tvCallDuration);
        }
    }

    public CallLogAdapter(ArrayList<CallLogInfo> callLogInfoList) {
        this.callLogInfoList= callLogInfoList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.call_logs_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        CallLogInfo callLogInfo = callLogInfoList.get(position);
        holder.contactName.setText(callLogInfo.getName());
        holder.contactNumber.setText(callLogInfo.getPhone_Number());
        holder.callType.setText(callLogInfo.getCall_Type());
        holder.callDuration.setText(callLogInfo.getCall_Duration());
        if (callLogInfo.getPhoto() != null) {
            Uri imageUri = Uri.parse(callLogInfo.getPhoto());
            holder.imageView.setImageURI(imageUri);
        }
    }

    @Override
    public int getItemCount() {
        return callLogInfoList.size();
    }
}