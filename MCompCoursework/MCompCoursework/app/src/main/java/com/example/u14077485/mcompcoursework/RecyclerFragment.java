package com.example.u14077485.mcompcoursework;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragment extends Fragment implements SearchView.OnQueryTextListener {
    // Local Variables
    private List<Book> books = new ArrayList<>();
    private Book bookTouched;
    private RecyclerView recyclerView;
    private RecyclerViewCustomAdapter recyclerViewCustomAdapter;

    public RecyclerFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load data using async task
        new WebAsync().execute(getArguments().getString("ExtensionTAG"));
        setHasOptionsMenu(true);
    }

    // Places Search on the action bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bar,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.recylcerview_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.RView);
        // Add lines between rows
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        RadioGroup radioGroup;
        radioGroup = (RadioGroup) view.findViewById(R.id.radioLayout);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                // Find what radio button is checked
                if(radioButton != null && recyclerViewCustomAdapter != null) {
                    if(radioButton.getText() == getString(R.string.radioTitle)) {
                        recyclerViewCustomAdapter.setSearch(true); // true for title search
                    }
                    else {
                        recyclerViewCustomAdapter.setSearch(false); // false for year search
                    }
                }
            }
        });
        return view;
    }

    // Required used to set layout
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
    // When search button is clicked
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(recyclerViewCustomAdapter != null) {
            recyclerViewCustomAdapter.getFilter().filter(query);
        }
        return false;
    }
    // Event triggered when any text is changed
    @Override
    public boolean onQueryTextChange(String newText) {
        if(recyclerViewCustomAdapter != null) {
            recyclerViewCustomAdapter.getFilter().filter(newText);
        }
        return false;
    }

    // Get the relevant data on a background thread
    private class WebAsync extends AsyncTask<String, Void, String> {
        ConnectionClass cc = new ConnectionClass();

        @Override
        protected String doInBackground(String... url) {
            // Send in URL from the main activity
            return cc.connectionCode(url[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // Parse text to the lists depending on choice (XML or JSON)
            try {
                books = cc.parseText(result);
            } catch (Exception e) {
                Log.e("RecyclerFragment", "Exception", e);
            }
            // Create new Adapter with new interface listener
            recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(getActivity().getApplicationContext(), books, new CustomItemClickListener() {
                // implement interface onItemClick function
                @Override
                public void onItemClick(View v, int position, List<Book> filteredData) {
                    Log.e("Onclick", Integer.toString(position) + filteredData.get(position).getTitle());
                    // Bundle data to send to new fragment
                    Bundle bundle = new Bundle();
                    // Position of data got from the adapter
                    bookTouched = filteredData.get(position);
                    bundle.putParcelable("selectedBook", bookTouched);
                    // Swap fragment
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    DetailsFragment df = new DetailsFragment();
                    df.setArguments(bundle);
                    ft.replace(R.id.recyclerFragment, df,"DETAILS_FRAGMENT");
                    ft.commitNow();

                }
            });
            // Load RView with data
            recyclerView.setAdapter(recyclerViewCustomAdapter);
        }
    }
}

