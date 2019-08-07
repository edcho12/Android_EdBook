package com.be.edbook.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.be.edbook.R;
import com.be.edbook.databinding.BookItemBinding;
import com.be.edbook.service.model.kakao.Book;
import com.be.edbook.view.callback.BookClickListener;


public class BookPagedListAdapter extends PagedListAdapter<Book, BookPagedListAdapter.BookViewHolder> {

    private static final String TAG = "BookAdapter";
    private Context context;
    private BookClickListener bookClickListener = null;



    public BookPagedListAdapter(Context context){
        super(Book.CALLBACK);

        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookItemBinding bookItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.book_item, parent, false);

        return new BookViewHolder(bookItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = getItem(position);

        book.setPosition(position);


        holder.bookItemBinding.setBook(book);


    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        private BookItemBinding bookItemBinding;

        public BookViewHolder(@NonNull BookItemBinding bookItemBinding){
            super(bookItemBinding.getRoot());
            this.bookItemBinding = bookItemBinding;

            bookItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        Book selectedBook = getItem(position);

                        if(bookClickListener != null){
                            bookClickListener.recyclerViewListClicked(v, position, selectedBook);
                        }

                    }
                }
            });
        }
    }

    public void setBookClickListener(BookClickListener listener){
        this.bookClickListener = listener;
    }
}
