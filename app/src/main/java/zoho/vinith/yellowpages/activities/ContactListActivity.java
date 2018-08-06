package zoho.vinith.yellowpages.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import zoho.vinith.yellowpages.adapter.ContactAdapter;
import zoho.vinith.yellowpages.database.YellowPageDatabase;
import zoho.vinith.yellowpages.R;
import zoho.vinith.yellowpages.model.ContactInfo;

public class ContactListActivity extends Fragment {

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

        recyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view);
        dbHandler = new YellowPageDatabase(getContext(),null,null,1);
        contactClassList = new ArrayList<ContactInfo>();
        contactClassList = dbHandler.databaseToString();

        contactAdapter = new ContactAdapter(contactClassList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);

        contactAdapter.notifyDataSetChanged();
    }
}
