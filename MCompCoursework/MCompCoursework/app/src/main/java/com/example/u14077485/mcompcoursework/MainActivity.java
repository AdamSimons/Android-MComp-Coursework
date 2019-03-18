package com.example.u14077485.mcompcoursework;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerFragment recyclerFragment;
    private List<Book> books = new ArrayList<>();
    private Button btnXML, btnJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnXML = (Button) findViewById(R.id.btnXML);
        btnJSON = (Button) findViewById(R.id.btnJSON);
    }

    public void onClickButton(View view) {

        Bundle bundle = new Bundle();
        bundle.putString("Extension",view.getTag().toString());
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        recyclerFragment = new RecyclerFragment();

        recyclerFragment.setArguments(bundle);
//        fragmentTransaction.add(R.id.recyclerFragment,recyclerFragment);
        fragmentTransaction.replace(R.id.recyclerFragment, recyclerFragment,"RECYCLER_FRAGMENT");
        fragmentTransaction.commitNow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}