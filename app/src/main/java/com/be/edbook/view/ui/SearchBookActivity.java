package com.be.edbook.view.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.be.edbook.R;
import com.be.edbook.databinding.SearchbookActivityBinding;
import com.be.edbook.service.model.kakao.Book;
import com.be.edbook.service.model.kakao.ResponseMeta;
import com.be.edbook.view.CustomDividerItemDecoration;
import com.be.edbook.view.adapter.BookPagedListAdapter;
import com.be.edbook.view.callback.BookClickListener;
import com.be.edbook.viewmodel.SearchBookViewModel;

import java.util.Locale;


public class SearchBookActivity extends AppCompatActivity {
    private TextView mTvPageableCount = null;

    private SearchBookViewModel searchBookViewModel = null;
    private SearchbookActivityBinding searchBookActivityBinding;

    private PagedList<Book> bookPagedList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbook_activity);

        Intent intent = getIntent();
        String searchString = intent.getStringExtra("queryString");

        setTitle(getString(R.string.search_book_activity_title) + " (" + searchString +  ")");



        searchBookActivityBinding = DataBindingUtil.setContentView(this, R.layout.searchbook_activity);
        searchBookViewModel = ViewModelProviders.of(this).get(SearchBookViewModel.class);
        searchBookViewModel.search(searchString);


        getBooks();



        mTvPageableCount = searchBookActivityBinding.tvPageableCount;


        searchBookViewModel.getMeta().observe(this, new Observer<ResponseMeta>() {
            @Override
            public void onChanged(ResponseMeta meta) {
                //SearchBooksViewModel 에서 mTotCount 가 변경되었을때 통보 받음.
                updateUI_PageableCount(meta.pageable_count);
            }
        });




    }

    public void getBooks(){
        searchBookViewModel.getUserPagedList().observe(this, new Observer<PagedList<Book>>() {
            @Override
            public void onChanged(PagedList<Book> booksFromLiveData) {
                bookPagedList = booksFromLiveData;
                showOnRecyclerView();
            }
        });
    }

    private void showOnRecyclerView(){
        RecyclerView recyclerView = searchBookActivityBinding.rvBooks;
        BookPagedListAdapter bookAdapter = new BookPagedListAdapter(this);
        bookAdapter.submitList(bookPagedList);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bookAdapter);


        //아이템 구분선 추가
        CustomDividerItemDecoration dividerItemDecoration = new CustomDividerItemDecoration(getResources());
        recyclerView.addItemDecoration(dividerItemDecoration);


        bookAdapter.notifyDataSetChanged();

        bookAdapter.setBookClickListener(new BookClickListener() {
            @Override
            public void recyclerViewListClicked(View view, int position, Book book) {
                //종료하고 FirstActivity 에 정보 전달
                Intent intent = new Intent();
                intent.putExtra("book", book);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void updateUI_PageableCount(int count){
        mTvPageableCount.setText(String.format(Locale.KOREA,"%,d", count));
    }


}
