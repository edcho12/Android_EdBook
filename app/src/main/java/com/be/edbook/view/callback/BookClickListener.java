package com.be.edbook.view.callback;

import android.view.View;

import com.be.edbook.service.model.kakao.Book;

public interface BookClickListener {
    void recyclerViewListClicked(View view, int position, Book book);
}
