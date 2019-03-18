package com.example.u14077485.mcompcoursework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {
    private Book book;
    public DetailsFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        book = (Book) getArguments().getParcelable("selectedBook");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        TextView title = view.findViewById(R.id.lbl_Title);
        TextView author = view.findViewById(R.id.lbl_Author);
        TextView year = view.findViewById(R.id.lbl_Year);
        TextView price = view.findViewById(R.id.lbl_Price);
        TextView publisher = view.findViewById(R.id.lbl_Publisher);

        title.setText(book.getTitle());
        year.setText(book.getYear());
        price.setText("£" + Double.toString(book.getPrice()));
        publisher.setText(book.getPublisher());

        List<Author> authors = new ArrayList<>(book.getAuthors());
        StringBuilder authorText = new StringBuilder();
        for (Author author1 : authors) {
            authorText.append(author1.getFullName()+" ");
        }
        author.setText(authorText);


        return view;
    }

}
