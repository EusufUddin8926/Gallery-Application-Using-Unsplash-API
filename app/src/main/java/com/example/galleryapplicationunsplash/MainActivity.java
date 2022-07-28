package com.example.galleryapplicationunsplash;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.galleryapplicationunsplash.adapter.CustomAdapter;
import com.example.galleryapplicationunsplash.model.ErrorResponse;
import com.example.galleryapplicationunsplash.model.ImageSource;
import com.example.galleryapplicationunsplash.model.NewsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    ImageButton imageButton;
    private TextView mErrorView;
    private CustomAdapter mCustomAdapter;
    private GridLayoutManager manager;
    private ArrayList<ImageSource> list;
    private NewsModel mNewsModel = null;
    private int visibleItems, totalItemCount, pastVisibleItems, previousTotalItem = 0, viewThreshold = 10;
    private boolean isLoading;
    private int PAGE_COUNT = 1, PAGE_SIZE = 60;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsModel = new ViewModelProvider(this).get(NewsModel.class);
        mNewsModel.init(PAGE_COUNT, PAGE_SIZE);
        list = new ArrayList<>();
        mErrorView = (TextView)findViewById(R.id.textinput_error);
        imageButton = findViewById(R.id.backBtn);
        imageButton.setOnClickListener(view -> {
            goBack();
        });
        initRecyclerView();
        observeData();
    }

    private void goBack() {
        customdialog();
    }

    private void initRecyclerView(){
        mCustomAdapter = new CustomAdapter(list, getApplicationContext());
       // mLinearLayoutManager = new LinearLayoutManager(this);
         manager = new GridLayoutManager(this, 3);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mCustomAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                visibleItems = manager.getChildCount();
                totalItemCount = manager.getItemCount();
                pastVisibleItems = manager.findFirstVisibleItemPosition();

                if(dy>0){
                    if(isLoading && totalItemCount>previousTotalItem){
                        isLoading = false;
                        previousTotalItem = totalItemCount;
                    }
                    if(!isLoading && totalItemCount-visibleItems <= pastVisibleItems + viewThreshold){
                        isLoading = true;
                        PAGE_COUNT++;
                        mNewsModel.loadData(PAGE_COUNT, PAGE_SIZE);
                    }
                }
            }
        });
    }

    private void observeData(){
        mNewsModel.getData().observe(this, mCustomAdapter);
        mNewsModel.getError().observe(this, new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                mErrorView.setVisibility(View.VISIBLE);
                mErrorView.setText(errorResponse.getMessage());
                Toast.makeText(getApplicationContext(), errorResponse.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void customdialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.galleryapplicationunsplash.MainActivity.this);
        alertDialogBuilder.setMessage("Are you sure want to Exit ?");
        alertDialogBuilder.setTitle("Gallery Application");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                //  Toast.makeText(com.example.galleryapplicationunsplash.MainActivity.this, "Yes Comfirmed", Toast.LENGTH_SHORT).show();

                finishAffinity();
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }





    @Override
    public void onBackPressed() {
       customdialog();
    }

}

