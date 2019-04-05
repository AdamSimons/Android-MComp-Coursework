package com.example.u14077485.mcompcoursework;

import android.view.View;

import java.util.List;
/* This is used to click on the rows to load up the details fragment. Look in the Adapter class to
   see it being set and details fragment to it being used */
public interface CustomItemClickListener {
    // Receive the view, the position to determine what book it is and the books
    void onItemClick(View v, int position, List<Book> filteredData);
}
