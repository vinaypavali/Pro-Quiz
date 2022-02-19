package com.quiz.proquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quiz.proquiz.Auth.LoginActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends AppCompatActivity {

    TextView et1,et2,et3;
    CircleImageView iv;
    ImageView ivp;
    private FirebaseUser user;
    private FirebaseAuth mauth;
    private DatabaseReference dbRef;
    private FirebaseStorage storage;
    private FirebaseDatabase db;
    private String userId;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

          iv = (CircleImageView) findViewById(R.id.imageView);
          ivp = (ImageView) findViewById(R.id.pluse);
         et1=(TextView) findViewById(R.id.editText1);
         et2=(TextView) findViewById(R.id.editText2);
         et3=(TextView) findViewById(R.id.editText3);
        progressBar=(ProgressBar) findViewById(R.id.pb);
             Button bt =findViewById(R.id.out);

        BottomNavigationView bnv =findViewById(R.id.bottomBar);

         mauth = FirebaseAuth.getInstance();
         db = FirebaseDatabase.getInstance();
         storage = FirebaseStorage.getInstance();
         user =FirebaseAuth.getInstance().getCurrentUser();
         dbRef = FirebaseDatabase.getInstance().getReference("Users");
         userId=user.getUid();

        progressBar.setVisibility(View.VISIBLE);

         dbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 Users users = snapshot.getValue(Users.class);
                 if(users !=null){
                     String UserName= users.userName;
                     String mobile = users.mobile;
                     String gmail=users.gmail;


                     et1.setText(UserName);
                     et2.setText(mobile);
                     et3.setText(gmail);


                     if(snapshot.hasChild("profilepic")){
                         String profilepic = snapshot.child("profilepic").getValue().toString();
                         Picasso.get().load(profilepic).into(iv);
                         progressBar.setVisibility(View.GONE);
                     }

                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 progressBar.setVisibility(View.GONE);

                 Toast.makeText(ProfileFragment.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
             }
         });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                startActivity(new Intent(ProfileFragment.this, LoginActivity.class));
                finish();
            }
        });


        bnv.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(), WalletFragment.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.profile:
                        finish();

                        return true;

                }
                return false;
            }
        });

        ivp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent.launch("image/*");


            }
        });

    }

    ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        iv.setImageURI(result);

                        final StorageReference reference =storage.getReference().child("profile pictures").child(FirebaseAuth.getInstance().getUid());
                        reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        db.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                                .child("profilepic").setValue(uri.toString());

                                        Toast.makeText(ProfileFragment.this,"Uploaded",Toast.LENGTH_SHORT).show();


                                    }
                                });

                            }
                        });
                    }

                }
            });

}



