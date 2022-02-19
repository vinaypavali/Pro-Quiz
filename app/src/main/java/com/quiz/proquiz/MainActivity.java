package com.quiz.proquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
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
    FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView bnv =findViewById(R.id.bottomBar);
        mauth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.categoryList);

        cat = new ArrayList<>();
        adapter = new CategoryAdapter(this,cat);
        database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        cat.clear();
                        for(DocumentSnapshot snapshot : value.getDocuments()){
                            CategoryModel model = snapshot.toObject(CategoryModel.class);
                            model.setCategoryId(snapshot.getId());
                            cat.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        bnv.setSelectedItemId(R.id.home);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.home:
                        finish();
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


    public void onStart() {
        super.onStart();
        FirebaseUser user = mauth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

}