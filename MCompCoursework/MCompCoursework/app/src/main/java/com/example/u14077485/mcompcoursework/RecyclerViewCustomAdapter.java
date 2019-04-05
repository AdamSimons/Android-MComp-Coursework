package com.example.u14077485.mcompcoursework;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> implements Filterable {
    private List<Book> books;
    private List<Book> filteredBooks;
    CustomItemClickListener itemClickListener;
    private Boolean searchTitle;
    private Context context;

    public RecyclerViewCustomAdapter(Context context, List<Book> books, CustomItemClickListener listener) {
        this.books = books;
        this.filteredBooks = new ArrayList<>(books);
        itemClickListener = listener; // reeive the listener and apply the on item click event
        searchTitle = true; // Default to title search
        this.context = context;
    }

    @Override
    public RecyclerViewCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        // set the on click for the data. Gets the right data and passes it back to the implementation of onItemClick in RecyclerFragment.java
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, viewHolder.getAdapterPosition(), filteredBooks);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewCustomAdapter.ViewHolder holder, int position) {
        // Wipe data and reset if the get buttons are clicked again
        clearData(holder, position);
        setData(holder,position);

    }
    private void setData(ViewHolder holder, int position) {
        // Set all UI values
        holder.txt_Title.setText(filteredBooks.get(position).getTitle());
        holder.txt_Price.setText("£" + filteredBooks.get(position).getPrice().toString());
        holder.txt_Year.setText(filteredBooks.get(position).getYear());
        String text = "NA";
        // Et al checks
        if(filteredBooks.get(position).getAuthors().size() > 2) {
            text = filteredBooks.get(position).getAuthors().get(0).getFullName() + ", "+ filteredBooks.get(position).getAuthors().get(1).getFullName()+ " et al.";
        }
        else if (filteredBooks.get(position).getAuthors().size() == 2 ) {
            text = filteredBooks.get(position).getAuthors().get(0).getFullName() + ", " + filteredBooks.get(position).getAuthors().get(1).getFullName();
        }
        else if (filteredBooks.get(position).getAuthors().size() > 0){
            text = filteredBooks.get(position).getAuthors().get(0).getFullName();
        }
        holder.txt_Author.setText(text);
        // Glide API image loader
        Glide.with(context).load(filteredBooks.get(position).getImageURL())
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(holder.img_Image);

    }
    private void clearData(ViewHolder holder, int position) {
        holder.txt_Title.setText("");
        holder.txt_Price.setText("");
        holder.txt_Year.setText("");
        holder.txt_Author.setText("");
    }

    @Override
    public int getItemCount() {
        return filteredBooks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // UI elements setup
        private TextView txt_Title;
        private TextView txt_Year;
        private TextView txt_Author;
        private TextView txt_Price;
        private ImageView img_Image;
        ViewHolder(View view) {
            super(view);
            txt_Title = view.findViewById(R.id.lbl_Title);
            txt_Year = view.findViewById(R.id.lbl_Year);
            txt_Author = view.findViewById(R.id.lbl_Author);
            txt_Price = view.findViewById(R.id.lbl_Price);
            img_Image = view.findViewById(R.id.img_Image);
        }
    }
    // Setter for search
    public void setSearch(Boolean titleSearch) {
        searchTitle = titleSearch;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchTerm = constraint.toString(); // get whats in the search bar
                if(searchTerm.isEmpty()) {
                    filteredBooks = new ArrayList<>(books); // All books
                }
                else {
                    filteredBooks = new ArrayList<>(books);
                    List<Book> tempList = new ArrayList<>();
                    for (Book book : books) {
                        // Title search
                        if(searchTitle) {
                            if(book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                                tempList.add(book); // If the any part of the search term is contained in the title
                            }
                        }
                        else if(!searchTitle) {
                            if (book.getYear().equals(searchTerm)) { // Year search
                                tempList.add(book);
                            }
                        }
                    }
                    filteredBooks = tempList;
                }
                // set results up for publishResults function
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredBooks;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged(); // Update data in Recycler
            }
        };
    }
}
