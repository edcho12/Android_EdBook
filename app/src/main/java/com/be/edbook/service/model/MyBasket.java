package com.be.edbook.service.model;

import com.be.edbook.service.model.kakao.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MyBasket {

    private LinkedHashMap<String, Book> map = null;
    private long totPrice = 0;
    private long totSalePrice = 0;

    public MyBasket(){
        map = new LinkedHashMap<>();
    }


    public void insert(Book book){
        String key = book.getIsbn();

        if(!containsBook(book)){
            map.put(key, book);

            //총 금액 계산
            totPrice += book.getPrice();
            totSalePrice += book.getSale_price();
        }
    }

    public boolean containsBook(Book book){
        return map.containsKey(book.getIsbn());
    }

    public long getTotPrice(){
        return this.totPrice;
    }

    public long getTotSalePrice(){
        return this.totSalePrice;
    }

    public int getTotCount(){
        return map.size();
    }

    public List<Book> getList(){
        List<Book> list = new ArrayList<Book>(map.values());

        return list;
    }
}
