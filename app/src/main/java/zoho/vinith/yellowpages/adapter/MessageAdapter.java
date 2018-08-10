package zoho.vinith.yellowpages.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.model.ContactInfo;
import zoho.vinith.yellowpages.model.MessageInfo;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private ArrayList<MessageInfo> messageInfoList;

    public void setInboxList(ArrayList<MessageInfo> inboxList) {
        this.messageInfoList = inboxList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView contactName, contactNumber, textMessage, time;

        public MessageViewHolder(View view) {
            super(view);
            contactName = view.findViewById(R.id.tvContactName);
            contactNumber = view.findViewById(R.id.tvContactNumber);
            textMessage = view.findViewById(R.id.tvTextMessage);
            time = view.findViewById(R.id.tvMessageTime);
        }
    }

    public MessageAdapter(ArrayList<MessageInfo> contactClassList) {
        this.messageInfoList = contactClassList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messages_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MessageInfo messageInfo = messageInfoList.get(position);
        holder.contactName.setText(messageInfo.getName());
        holder.contactNumber.setText(messageInfo.getPhone_Number());
        holder.textMessage.setText(messageInfo.getText());
        holder.time.setText(messageInfo.getTime());
    }

    @Override
    public int getItemCount() {
        return messageInfoList.size();
    }
}