package com.be.edbook.service.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.be.edbook.service.model.kakao.Book;
import com.be.edbook.service.model.kakao.ResponseMeta;
import com.be.edbook.service.model.kakao.ResponseData;
import com.be.edbook.service.model.kakao.KakaoBookApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookDataSource extends PageKeyedDataSource<Integer, Book> {
    private final static String TAG = "BookDataSource";
    private String mSearchString;
    private int mLastLoadedPage;
    private KakaoBookApiService kakaoBookApiService;

    private ResponseMeta mMeta;
    private MutableLiveData<ResponseMeta> mMetaLiveData;
    public LiveData<ResponseMeta> getMeta(){
        return mMetaLiveData;
    }

    private Retrofit retrofit = null;
    private KakaoBookApiService service = null;



    public BookDataSource(String searchString) {
        super();

        this.retrofit = KakaoBookApiService.retrofit;
        this.service = retrofit.create(KakaoBookApiService.class);


        init(searchString);
    }

    private void init(String searchString){
        this.mSearchString = searchString;
        mLastLoadedPage = 0;

        mMeta = new ResponseMeta();
        mMetaLiveData = new MutableLiveData<>();
        mMetaLiveData.setValue(mMeta);
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Book> callback) {

        final int firstPage = 1;

        Call<ResponseData> call = service.getBookSearchList(
                service.APP_KEY,
                mSearchString,
                service.SORT_TYPE,
                service.TARGET_TYPE,
                service.BOOKS_SIZE,
                firstPage);


        call.enqueue(new Callback<ResponseData>(){

            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if(response.body() != null && response.isSuccessful()){

                    ResponseData books = response.body();

                    mMeta.total_count = books.meta.total_count;
                    mMeta.pageable_count = books.meta.pageable_count;
                    mMeta.is_end = books.meta.is_end;
                    mMetaLiveData.postValue(mMeta);

                    callback.onResult(books.documents, null,firstPage + 1);

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Log.d(TAG, "@loadInitial : onResponse >> fail");
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Book> callback) {
        final int prevPage = params.key;

        Call<ResponseData> call = service.getBookSearchList(
                service.APP_KEY,
                mSearchString,
                service.SORT_TYPE,
                service.TARGET_TYPE,
                service.BOOKS_SIZE,
                prevPage);


        call.enqueue(new Callback<ResponseData>(){

            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if(response.body() != null && response.isSuccessful()){

                    ResponseData info = response.body();

                    mMeta.total_count = info.meta.total_count;
                    mMeta.pageable_count = info.meta.pageable_count;
                    mMeta.is_end = info.meta.is_end;
                    mMetaLiveData.postValue(mMeta);

                    //처음이면 null 넘겨준다.
                    Integer prevKey = (prevPage - 1 >= 1) ? prevPage - 1 : null;

                    callback.onResult(info.documents, prevKey);

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Log.d(TAG, "@loadBefore : onResponse >> fail");
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Book> callback) {

        final int nextPage = params.key;

        Call<ResponseData> call = service.getBookSearchList(
                service.APP_KEY,
                mSearchString,
                service.SORT_TYPE,
                service.TARGET_TYPE,
                service.BOOKS_SIZE,
                nextPage);


        call.enqueue(new Callback<ResponseData>(){

            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if(response.body() != null && response.isSuccessful()){

                    ResponseData info = response.body();

                    mMeta.total_count = info.meta.total_count;
                    mMeta.pageable_count = info.meta.pageable_count;
                    mMeta.is_end = info.meta.is_end;
                    mMetaLiveData.postValue(mMeta);

                    //마지막이면 null 넘겨준다.
                    Integer nextKey = mMeta.is_end == false ? nextPage + 1 : null;

                    callback.onResult(info.documents, nextKey);

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

                Log.d(TAG, "@loadAfter : onResponse >> fail");
            }
        });
    }
}
