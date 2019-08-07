package com.be.edbook.service.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.be.edbook.service.model.kakao.ResponseMeta;

public class BookDataSourceFactory extends DataSource.Factory{
    private static final String TAG = "BookDataSourceFactory";
    BookDataSource booksDataSource;
    MutableLiveData<BookDataSource> mutableLiveData;
    String strSearch;

    public BookDataSourceFactory(String strSearch){
        this.strSearch = strSearch;
        mutableLiveData = new MutableLiveData<>();

        booksDataSource = new BookDataSource(strSearch);

    }

    @Override
    public DataSource create() {
        mutableLiveData.postValue(booksDataSource);

        return booksDataSource;
    }


    public MutableLiveData<BookDataSource> getMutableLiveData(){
        return mutableLiveData;
    }

    public LiveData<ResponseMeta> getMeta(){
        return booksDataSource.getMeta();
    }

    public BookDataSource getDataSource(){
        return booksDataSource;
    }
}
