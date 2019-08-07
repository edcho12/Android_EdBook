package com.be.edbook.service.model.kakao;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.recyclerview.widget.DiffUtil;

public class KakaoBook implements Parcelable {
    private static final String TAG = "KakaoBook";

    private String title = null;
    private int sale_price = 0;
    private int price = 0;
    private String thumbnail = null;
    private String datetime = null;
    private String isbn = null;
    private int authorsCount = 0;
    private String authors[] = null;


    KakaoBook(Parcel in) {

        if(in instanceof Parcel) {
            title = in.readString();
            sale_price = in.readInt();
            price = in.readInt();
            thumbnail = in.readString();
            datetime = in.readString();
            isbn = in.readString();
            authorsCount = in.readInt();

            authors = new String[authorsCount];
            in.readStringArray(authors);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(sale_price);
        dest.writeInt(price);
        dest.writeString(thumbnail);
        dest.writeString(datetime);
        dest.writeString(isbn);
        authorsCount = authors.length;
        dest.writeInt(authorsCount);
        dest.writeStringArray(authors);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public String [] getAuthors() {
        return authors;
    }

    public void setAuthors(String []authors) {

        this.authorsCount = authors.length;
        this.authors = authors;
    }

    public static final DiffUtil.ItemCallback<Book> CALLBACK = new DiffUtil.ItemCallback<Book>() {
        @Override
        public boolean areItemsTheSame(Book oldItem, Book newItem) {
            return oldItem.getIsbn().equals(newItem.getIsbn());
        }

        @Override
        public boolean areContentsTheSame(Book oldItem, Book newItem) {
            return true;
        }
    };

}
