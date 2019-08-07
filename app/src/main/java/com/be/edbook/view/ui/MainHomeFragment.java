package com.be.edbook.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.be.edbook.R;
import com.be.edbook.service.model.MyBasket;
import com.be.edbook.service.model.kakao.Book;
import com.be.edbook.viewmodel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class MainHomeFragment extends Fragment implements View.OnClickListener{
    private static final int REQUEST_CODE = 1234;
    private MainViewModel mainViewModel = null;
    private EditText editSearch  = null;
    private ImageButton buttonSearch = null;
    private ImageButton buttonDelete = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //뷰모델 생성
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        mainViewModel.getMyBasket().observe(getViewLifecycleOwner(), new Observer<MyBasket>() {
            @Override
            public void onChanged(MyBasket basket) {
                //MainViewModel 에서 MyBasket 가 변경되었을때 통보 받음.
                updateUI_MyBasket(basket);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_home_fragment, container, false);



        editSearch = root.findViewById(R.id.et_search);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE || (event != null && event.getKeyCode() == KEYCODE_ENTER)) {
                    startSearchBookActivity();
                    return true;
                }
                return false;
            }
        });

        buttonSearch = root.findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(this);

        buttonDelete = root.findViewById(R.id.btn_delete);
        buttonDelete.setOnClickListener(this);

        return root;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            Book book = data.getParcelableExtra("book");

            if(!mainViewModel.containsMyBasket(book))
                mainViewModel.insertMyBasket(book);
            else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.already_existed_in_the_basket), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btn_delete:
                editSearch.setText("");
                break;

            case R.id.btn_search:
                startSearchBookActivity();
                break;
        }
    }

    private void startSearchBookActivity(){
        EditText et = getActivity().findViewById(R.id.et_search);
        String strQuery = et.getText().toString();
        if(strQuery.compareTo("") != 0){
            Intent intent = new Intent(getActivity(), SearchBookActivity.class);
            intent.putExtra("queryString", strQuery);
            startActivityForResult(intent, REQUEST_CODE);
        }else{
            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.search_book_keyword_empty), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


    private void updateUI_MyBasket(MyBasket basket){

        TextView tv = getActivity().findViewById(R.id.tv_balance);
        tv.setText(String.format(Locale.KOREA,"%,d원", basket.getTotSalePrice()));

        TextView tv_count = getActivity().findViewById(R.id.tv_balance_count);
        tv_count.setText(String.format(Locale.KOREA,"%,d건", basket.getTotCount()));
    }
}
