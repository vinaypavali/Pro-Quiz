package com.quiz.proquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.quiz.proquiz.Adapters.CategoryAdapter;
import com.quiz.proquiz.Adapters.CategoryModel;
import com.quiz.proquiz.Auth.LoginActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private  ArrayList<CategoryModel> cat;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView bnv =findViewById(R.id.bottomBar);
        mauth = FirebaseAuth.getInstance();

        getData();
        getRecycler();

        bnv.setSelectedItemId(R.id.home);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.home:

                        return true;
                    case R.id.rank:
                        startActivity(new Intent(getApplicationContext(), RankFragment.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.wallet:
                        startActivity(new Intent(getApplicationContext(), WalletFragment.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileFragment.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                }
                return false;
            }
        });

    }

    public void getData(){
        cat = new ArrayList<>();

        cat.add(new CategoryModel("C",R.drawable.cpro));
        cat.add(new CategoryModel("C++",R.drawable.cplus));
        cat.add(new CategoryModel("Java",R.drawable.java));
        cat.add(new CategoryModel("Python",R.drawable.python));
        cat.add(new CategoryModel("HTML",R.drawable.html));
        cat.add(new CategoryModel("CSS",R.drawable.css));
        cat.add(new CategoryModel("MySQL",R.drawable.mysql));
        cat.add(new CategoryModel("Aptitude",R.drawable.math));

    }

    public void getRecycler()
    {
        recyclerView = findViewById(R.id.categoryList);

        gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

         adapter = new CategoryAdapter(MainActivity.this,cat);
         recyclerView.setAdapter(adapter);
         adapter.notifyDataSetChanged();

    }
    public void onStart() {
        super.onStart();
        FirebaseUser user = mauth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

}