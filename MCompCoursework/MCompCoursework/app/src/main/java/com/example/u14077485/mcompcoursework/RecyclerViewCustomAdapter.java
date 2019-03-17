package com.example.u14077485.mcompcoursework;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextClock;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> implements Filterable {
    private List<Book> books;
    private List<Book> filteredBooks;

    public RecyclerViewCustomAdapter(List<Book> books) {
        this.books = books;
        this.filteredBooks = new ArrayList<>(books);
    }

    @Override
    public RecyclerViewCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerViewCustomAdapter.ViewHolder holder, int position) {
        clearData(holder, position);
        setData(holder,position);

    }
    private void setData(ViewHolder holder, int position) {
        holder.txt_Title.setText(filteredBooks.get(position).getTitle());
        holder.txt_Price.setText(filteredBooks.get(position).getPrice().toString());
        holder.txt_Year.setText(filteredBooks.get(position).getYear());
        String text = null;
        if(filteredBooks.get(position).getAuthors().size() > 2) {
            text = filteredBooks.get(position).getAuthors().get(0).getFullName() + ", "+ filteredBooks.get(position).getAuthors().get(1).getFullName()+ " et al.";
        }
        else if (filteredBooks.get(position).getAuthors().size() == 2 ) {
            text = filteredBooks.get(position).getAuthors().get(0).getFullName()+", " + filteredBooks.get(position).getAuthors().get(1).getFullName();
        }
        else {
            text = filteredBooks.get(position).getAuthors().get(0).getFullName();
        }
        holder.txt_Author.setText(text);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_Title;
        private TextView txt_Year;
        private TextView txt_Author;
        private TextView txt_Price;
        ViewGroup viewGroup;
        ViewHolder(View view) {
            super(view);
            viewGroup = (ViewGroup)view;
            txt_Title = view.findViewById(R.id.lbl_Title);
            txt_Year = view.findViewById(R.id.lbl_Year);
            txt_Author = view.findViewById(R.id.lbl_Author);
            txt_Price = view.findViewById(R.id.lbl_Price);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchTerm = constraint.toString();
                if(searchTerm.isEmpty()) {
                    filteredBooks = new ArrayList<>(books);
                }
                else {
                    filteredBooks = new ArrayList<>(books);
                    List<Book> tempList = new ArrayList<>();
                    for (Book book : books) {
                        if(!book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())){
                            tempList.add(book);
                        }
                    }
                    filteredBooks.removeAll(tempList);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredBooks;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }
}
