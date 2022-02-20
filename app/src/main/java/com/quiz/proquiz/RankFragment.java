package com.quiz.proquiz;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RankFragment extends AppCompatActivity {
    private DatabaseReference dbRef;
            LeaderboardsAdapter adapter;
            LinearLayoutManager manager;
    private FirebaseStorage storage;
    private FirebaseDatabase db;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rank);
        BottomNavigationView bnv =findViewById(R.id.bottomBar);
        RecyclerView rc = (RecyclerView) findViewById(R.id.rankView);
        progressBar=(ProgressBar) findViewById(R.id.pb2);
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        progressBar.setVisibility(View.VISIBLE);


        rc.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);

        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        rc.setLayoutManager(manager);

        ArrayList<Users> users = new ArrayList<>();
        LeaderboardsAdapter adapter = new LeaderboardsAdapter(RankFragment.this,users);
        rc.setAdapter(adapter);


        dbRef=FirebaseDatabase.getInstance().getReference("Users");
        dbRef.orderByChild("coins").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users1 = dataSnapshot.getValue(Users.class);
                    users.add(users1);
                    progressBar.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);

            }
        });





        bnv.setSelectedItemId(R.id.rank);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.rank:

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
