package com.example.u14077485.mcompcoursework;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragment extends Fragment {
    private List<Book> books = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewCustomAdapter recyclerViewCustomAdapter;

    public RecyclerFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String text = getArguments().getString("Extension");
        new WebAsync().execute(getArguments().getString("Extension"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
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
            recyclerViewCustomAdapter = new RecyclerViewCustomAdapter(books);
            recyclerView.setAdapter(recyclerViewCustomAdapter);
//            recyclerViewCustomAdapter.notifyAll();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
