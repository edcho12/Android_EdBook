package com.be.edbook.service.model.kakao;

import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.be.edbook.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Locale;

public class Book extends KakaoBook {

    private final static String TAG = "Book";

    private int position = 0;

    public Book(Parcel in) {
        super(in);
    }

    public String getTxtPrice(){
        return String.format(Locale.KOREA,"%,d원", this.getPrice());
    }

    public String getTxtSalePrice(){
        return String.format(Locale.KOREA,"%,d원", this.getSale_price());
    }

    public String getTxtAuthors(){
        String strAuthors = Arrays.toString(getAuthors())
                .replace("[","")
                .replace("]", "");


        return strAuthors;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public String getTxtPosition(){

        return String.format(Locale.KOREA,"[%04d]", this.position+1);
    }

    @BindingAdapter({"thumbnail"})
    public static void loadImage(ImageView imageView, String imageUrl){
        if (imageUrl.trim().length() != 0) {
            try {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.mipmap.book_icon_default)
                        .error(R.mipmap.book_icon_default)
                        .into(imageView);
            } catch (IllegalArgumentException e) {
                Log.d(TAG, e.getMessage());
                imageView.setImageResource(R.mipmap.book_icon_default);
            }
        } else {
            imageView.setImageResource(R.mipmap.book_icon_default);
        }

    }
}
