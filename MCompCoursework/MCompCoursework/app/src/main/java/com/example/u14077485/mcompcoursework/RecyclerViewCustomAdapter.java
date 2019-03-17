package com.example.u14077485.mcompcoursework;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder> {
    private final List<Book> books;

    public RecyclerViewCustomAdapter(List<Book> books) {
        this.books = books;
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
        holder.txt_Title.setText(books.get(position).getTitle());
        holder.txt_Price.setText(books.get(position).getPrice().toString());
        holder.txt_Year.setText(books.get(position).getYear());
        String text = null;
        if(books.get(position).getAuthors().size() > 2) {
            text = books.get(position).getAuthors().get(0).getFullName() + ", "+ books.get(position).getAuthors().get(1).getFullName()+ " et al.";
        }
        else if (books.get(position).getAuthors().size() == 2 ) {
            text = books.get(position).getAuthors().get(0).getFullName()+", " + books.get(position).getAuthors().get(1).getFullName();
        }
        else {
            text = books.get(position).getAuthors().get(0).getFullName();
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
        return books.size();
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

}
