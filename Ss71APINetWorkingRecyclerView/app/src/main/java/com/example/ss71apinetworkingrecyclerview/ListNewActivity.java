package com.example.ss71apinetworkingrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ss71apinetworkingrecyclerview.adapter.NewsAdapter;
import com.example.ss71apinetworkingrecyclerview.model.Item;
import com.example.ss71apinetworkingrecyclerview.network.APIManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListNewActivity extends AppCompatActivity {

    RecyclerView rvListViews ;
    List<Item>listData;
    NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        //B1 Datasource

        getListData();

        //B2 adapter
        listData = new ArrayList<>();
        adapter = new NewsAdapter(listData,ListNewActivity.this);


        //B3 layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);


        //B4 Recycler view
        rvListViews = findViewById(R.id.rvListViews);
        rvListViews.setLayoutManager(layoutManager);
        rvListViews.setAdapter(adapter);


    }
    private  void  getListData(){

        Retrofit retrofit  = new  Retrofit.Builder()
                .baseUrl(APIManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        service.getListData().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.body()!=null){
                    listData =response.body();
                    adapter.reloadData(listData);

                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(ListNewActivity.this, "Load fail", Toast.LENGTH_LONG).show();
            }
        });
    }
}