package com.be.edbook.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.be.edbook.R;
import com.be.edbook.databinding.BookItemBinding;
import com.be.edbook.service.model.kakao.Book;
import com.be.edbook.view.callback.BookClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BookViewHolder> {
    private final static String TAG = "BookRecyclerViewAdapter";

    private List<Book> mList;
    private final BookClickListener mClickListener;


    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final BookItemBinding mBinding;

        final private TextView tvIndex;
        final private ImageView ivThumbnail;
        final private ConstraintLayout layout;

        private BookViewHolder(View view){
            super(view);

            mBinding = DataBindingUtil.bind(view);

            this.tvIndex = view.findViewById(R.id.tv_position);
            this.ivThumbnail = view.findViewById(R.id.iv_thumbnail);

            this.layout = view.findViewById(R.id.layout_item);
            layout.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mClickListener != null){

                int position = this.getAdapterPosition();
                Book book = mList.get(position);
                mClickListener.recyclerViewListClicked(v, this.getAdapterPosition(), book);
            }
        }
    }


    public BookRecyclerViewAdapter(List<Book> list, BookClickListener clickListener){
        this.mList = list;
        this.mClickListener = clickListener;

        setHasStableIds(true);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_item, viewGroup, false);
        return new BookViewHolder(view);

    }


    @Override
    public long getItemId(int position) {
        Book book = this.mList.get(position);
        return book.hashCode();
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder viewHolder, int position) {
        Book book = mList.get(position);

        book.setPosition(position);

        String strUrl = mList.get(position).getThumbnail();
        if (strUrl != null) {
            if (strUrl.trim().length() != 0) {
                try {
                    Picasso.get()
                            .load(mList.get(position).getThumbnail())
                            .placeholder(R.mipmap.book_icon_default)
                            .error(R.mipmap.book_icon_default)
                            .into(viewHolder.ivThumbnail);
                } catch (IllegalArgumentException e) {
                    Log.d(TAG, e.getMessage());
                    viewHolder.ivThumbnail.setImageResource(R.mipmap.book_icon_default);
                }
            } else {
                viewHolder.ivThumbnail.setImageResource(R.mipmap.book_icon_default);
            }
        }


        //나머지 아이템들은 data binding 으로 업데이트
        viewHolder.mBinding.setBook(book);
    }

    @Override
    public int getItemCount() {
        return (mList != null ? mList.size():0);
    }

    public void updateList(List<Book> list){

        this.mList = list;
        notifyDataSetChanged();
    }


}
