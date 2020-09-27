package com.example.ketan_studio.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import Model.User;
import ViewHolder.UserListViewHolder;

public class UserList_Activity extends AppCompatActivity {

    RecyclerView recycle;
    FirebaseRecyclerAdapter<User, UserListViewHolder> adapter;
    DatabaseReference myReff;
    ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_);
        loadingbar = new ProgressDialog(this);
        myReff = FirebaseDatabase.getInstance().getReference("User");
        recycle = (RecyclerView)findViewById(R.id.recyclerview);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        showlist();

    }

    private void showlist() {
        FirebaseRecyclerOptions opt = new FirebaseRecyclerOptions.Builder<User>().setQuery(myReff,User.class).build();
        adapter = new FirebaseRecyclerAdapter<User, UserListViewHolder>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull final UserListViewHolder holder, int position, @NonNull final User model) {
                holder.name.setText(model.getName());
                holder.email.setText(model.getEmail());
                holder.makeaddmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingbar.setTitle("Creating User Account");
                        loadingbar.setMessage("Please wait until we create Account");
                        loadingbar.show();
                        final String emailid = model.getEmail();
                        final String name = model.getName();
                        final String uid = model.getUID();
                        final DatabaseReference adminReff = FirebaseDatabase.getInstance().getReference();
                        adminReff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.child("Admins").child(uid).exists()){
                                    HashMap<String,Object> m = new HashMap<>();
                                    m.put("UID",uid);
                                    m.put("Name",name);
                                    m.put("Email",emailid);
                                    adminReff.child("Admins").child(uid).updateChildren(m);
                                    loadingbar.dismiss();
                                    Toast.makeText(UserList_Activity.this, "Admin Added Sucessfully", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(UserList_Activity.this, "Aldready Admin or Error", Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }

            @NonNull
            @Override
            public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleruserlist,parent,false);
                return new UserListViewHolder(v);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycle.setAdapter(adapter);
    }
}
