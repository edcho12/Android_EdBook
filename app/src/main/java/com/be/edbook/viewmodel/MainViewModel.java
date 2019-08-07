package com.be.edbook.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.be.edbook.service.model.kakao.Book;
import com.be.edbook.service.model.MyBasket;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<MyBasket> myBasket = null;

    public MainViewModel(Application application){
        super(application);


        myBasket = new MutableLiveData<>();

        MyBasket basket = new MyBasket();
        myBasket.setValue(basket);
    }

    public final LiveData<MyBasket> getMyBasket(){
        return myBasket;
    }


    public void insertMyBasket(Book book){

        MyBasket basket = myBasket.getValue();

        if(!basket.containsBook(book)){
            basket.insert(book);

            myBasket.setValue(basket);
        }
    }

    public boolean containsMyBasket(Book book){
        return myBasket.getValue().containsBook(book);
    }
}
