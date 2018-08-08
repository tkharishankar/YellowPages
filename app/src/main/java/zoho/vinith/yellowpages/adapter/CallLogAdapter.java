package zoho.vinith.yellowpages.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.model.CallLogInfo;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ContactViewHolder> {

    private ArrayList<CallLogInfo> callLogInfoList;


    public void setCallLogInfoList(ArrayList<CallLogInfo> callLogInfoList) {
        this.callLogInfoList = callLogInfoList;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName, contactNumber, callType, callDate, callDuration;
        ImageView imageView;

        public ContactViewHolder(View view) {
            super(view);
            contactName = view.findViewById(R.id.tvContactName);
            contactNumber = view.findViewById(R.id.tvContactNumber);
            imageView = view.findViewById(R.id.contact_image);
            callType = view.findViewById(R.id.tvCallType);
            callDate = view.findViewById(R.id.tvCallDate);
            callDuration = view.findViewById(R.id.tvCallDuration);
        }
    }

    public CallLogAdapter(ArrayList<CallLogInfo> callLogInfoList) {
        this.callLogInfoList = callLogInfoList;
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
        holder.callDate.setText(callLogInfo.getDateOfCall());
        holder.callDuration.setText(callLogInfo.getCall_Duration());
        if (holder.callType.getText().equals("MISSED")) {
            holder.contactName.setTextColor(Color.parseColor("#FF6347"));
            holder.contactNumber.setTextColor(Color.parseColor("#FF6347"));
            holder.callType.setTextColor(Color.parseColor("#FF6347"));
            holder.callDate.setTextColor(Color.parseColor("#FF6347"));
            holder.callDuration.setTextColor(Color.parseColor("#FF6347"));
            holder.callType.setTypeface(null, Typeface.BOLD);
        } else {
            holder.contactName.setTextColor(Color.parseColor("#000000"));
            holder.contactNumber.setTextColor(Color.parseColor("#000000"));
            holder.callType.setTextColor(Color.parseColor("#000000"));
            holder.callDate.setTextColor(Color.parseColor("#000000"));
            holder.callDuration.setTextColor(Color.parseColor("#000000"));
            holder.callType.setTypeface(null, Typeface.NORMAL);
        }
//        if (callLogInfo.getPhoto().isEmpty() && callLogInfo.getPhoto() == null) {
//            holder.imageView.setImageResource(R.drawable.contact_icon_480);
//        } else {
//            try {
//                Uri imageUri = Uri.parse(callLogInfo.getPhoto());
//                holder.imageView.setImageURI(imageUri);
//            } catch (Exception e) {
//                holder.imageView.setImageResource(R.drawable.contact_icon_480);
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return callLogInfoList.size();
    }
}