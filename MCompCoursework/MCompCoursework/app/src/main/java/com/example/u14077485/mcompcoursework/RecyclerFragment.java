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

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragment extends Fragment implements SearchView.OnQueryTextListener {
    private List<Book> books = new ArrayList<>();
    private Book bookTouched;
    private RecyclerView recyclerView;
    private RecyclerViewCustomAdapter recyclerViewCustomAdapter;

    public RecyclerFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new WebAsync().execute(getArguments().getString("Extension"));
        setHasOptionsMenu(true);
    }

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
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(recyclerViewCustomAdapter != null) {
            recyclerViewCustomAdapter.getFilter().filter(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(recyclerViewCustomAdapter != null) {
            recyclerViewCustomAdapter.getFilter().filter(newText);
        }
        return false;
    }



    private class WebAsync extends AsyncTask<String, Integer, String> {
        ConnectionClass cc = new ConnectionClass();
        @Override
        protected String doInBackground(String... url) {
            return cc.connectionCode(url[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                books = cc.parseText(result);
            } catch (Exception e) {
                Log.e("RecyclerFragment", "Exception", e);
            }
            Log.e("Data", books.get(0).getTitle());
            recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(books, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position, List<Book> filteredData) {
                    Log.e("Onclick", Integer.toString(position) + filteredData.get(position).getTitle());
                    Bundle bundle = new Bundle();
                    bookTouched = filteredData.get(position);
                    bundle.putParcelable("selectedBook", bookTouched);

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    DetailsFragment df = new DetailsFragment();
                    df.setArguments(bundle);
                    ft.replace(R.id.recyclerFragment, df,"DETAILS_FRAGMENT");
//                    ft.addToBackStack(null);
                    ft.commitNow();

                }
            });
            recyclerView.setAdapter(recyclerViewCustomAdapter);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}

