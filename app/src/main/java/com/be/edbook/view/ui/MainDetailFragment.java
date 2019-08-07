package com.be.edbook.view.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.be.edbook.R;
import com.be.edbook.service.model.MyBasket;
import com.be.edbook.service.model.kakao.Book;
import com.be.edbook.view.CustomDividerItemDecoration;
import com.be.edbook.view.adapter.BookPagedListAdapter;
import com.be.edbook.view.adapter.BookRecyclerViewAdapter;
import com.be.edbook.view.callback.BookClickListener;
import com.be.edbook.viewmodel.MainViewModel;

import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MainDetailFragment extends Fragment {
    private MainViewModel mainViewModel = null;
    private BookRecyclerViewAdapter recyclerViewAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();

        //뷰모델 생성
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        mainViewModel.getMyBasket().observe(getViewLifecycleOwner(), new Observer<MyBasket>() {
            @Override
            public void onChanged(MyBasket basket) {
                //MainViewModel 에서 MyBasket 가 변경되었을때 통보 받음.
                updateUI_MyBasketCount(basket);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_detail_fragment, container, false);


        RecyclerView recyclerView = root.findViewById(R.id.rv_books);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new BookRecyclerViewAdapter(null, null);

        //아이템 구분선 추가
        CustomDividerItemDecoration dividerItemDecoration = new CustomDividerItemDecoration(getResources());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(recyclerViewAdapter);


        return root;
    }

    private void initRecyclerView(){
    }


    private void updateUI_MyBasketCount(MyBasket basket){
        TextView tv = getActivity().findViewById(R.id.tv_basket_items_count);
        tv.setText(String.format(Locale.KOREA,"%,d", basket.getTotCount()));



        recyclerViewAdapter.updateList(basket.getList());
    }



}
