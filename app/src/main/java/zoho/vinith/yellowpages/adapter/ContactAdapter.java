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
import zoho.vinith.yellowpages.model.ContactInfo;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private ArrayList<ContactInfo> contactClassList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contactName, contactNumber;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            contactName = (TextView) view.findViewById(R.id.tvContactName);
            contactNumber = (TextView) view.findViewById(R.id.tvContactNumber);
            imageView = (ImageView) view.findViewById(R.id.contact_image);
        }
    }


    public ContactAdapter(ArrayList<ContactInfo> contactClassList) {
        this.contactClassList= contactClassList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactInfo contactClass= contactClassList.get(position);
        holder.contactName.setText(contactClass.getName());
        holder.contactNumber.setText(contactClass.getPhone_Number());
        Uri imageUri = Uri.parse(contactClass.getPhoto());
        holder.imageView.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return contactClassList.size();
    }
}