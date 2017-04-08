package com.dsppa.AddListView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.library.AddListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AddListView addListView;
    private List<String> addListDatas = new ArrayList<>();
    private int ni = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListView = (AddListView) findViewById(R.id.commentListView);

        addListView.setOnItemDelBtnClickListener(new AddListView.OnItemDelBtnClickListener() {
            @Override
            public void onItemDelClick(int position) {
                if (!addListDatas.isEmpty()){
                    if (addListDatas.size() > position){
                        addListDatas.remove(position);
                        addListView.setDatas(addListDatas);
                    }
                }
            }
        });

        addListView.setOnItemAddBtnClickListener(new AddListView.OnItemAddBtnClickListener() {
            @Override
            public void onItemAddClick() {
                addListDatas.add("item"+ (++ni));
                addListView.setDatas(addListDatas);
            }
        });

        addListView.setOnItemClickListener(new AddListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
            }
        });

        addListDatas.add("item0");
        addListView.setDatas(addListDatas);
    }
}
