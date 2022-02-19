package com.quiz.proquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class WalletFragment extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth mauth;
    private DatabaseReference dbRef;
    private FirebaseStorage storage;
    private FirebaseDatabase db;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet);
        getSupportActionBar().hide();
        BottomNavigationView bnv =findViewById(R.id.bottomBar);
        TextView tv1 = (TextView) findViewById(R.id.textView1);
        TextView tv2 = (TextView) findViewById(R.id.TotalCoins);

        mauth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        user =FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userId=user.getUid();

        dbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);

                if(users !=null){
                    tv2.setText(String.valueOf(users.getCoins()));
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(WalletFragment.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
            }
        });

        bnv.setSelectedItemId(R.id.wallet);

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
                        startActivity(new Intent(getApplicationContext(), RankFragment.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.wallet:
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


}
