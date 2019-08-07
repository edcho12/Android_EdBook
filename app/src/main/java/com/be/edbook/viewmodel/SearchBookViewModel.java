package com.be.edbook.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.be.edbook.service.model.kakao.Book;
import com.be.edbook.service.model.kakao.ResponseMeta;
import com.be.edbook.service.repository.BookDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchBookViewModel extends AndroidViewModel {

    private final static String TAG = "SearchBookViewModel";
    private String mLastSearchString = "";

    BookDataSourceFactory dataSourceFactory;


    private LiveData<PagedList<Book>> booksPagedList;
    private Executor executor;


    private LiveData<ResponseMeta> responseMeta;
    public LiveData<ResponseMeta> getMeta(){
        return responseMeta;
    }


    public SearchBookViewModel(Application application) {
        super(application);


    }

    public LiveData<PagedList<Book>> getUserPagedList(){
        return booksPagedList;
    }


    public void search(String searchString){

        dataSourceFactory = new BookDataSourceFactory(searchString);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .setPrefetchDistance(10)
                .build();


        executor = Executors.newFixedThreadPool(5);
        booksPagedList = (new LivePagedListBuilder<Integer, Book>(dataSourceFactory, config))
                .setFetchExecutor(executor)
                .build();



        responseMeta = dataSourceFactory.getDataSource().getMeta();

    }
}
