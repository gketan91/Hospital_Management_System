package com.example.ketan_studio.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import Model.Appoinment;
import ViewHolder.SeeApoinmentViewHolder;

public class SeeAppinmentDoctorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseAuth mAuth;


    public String value;
    DatabaseReference  docReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_appinment_doctor);
        mAuth = FirebaseAuth.getInstance();




        Retrivedata();







    }

    public void Retrivedata() {

        Toast.makeText(this, "Enter Inside:", Toast.LENGTH_SHORT).show();


        Intent i = getIntent();
        String name = i.getStringExtra("DocName");
        Toast.makeText(this, "Getted :"+name, Toast.LENGTH_SHORT).show();
        showlist(name);

    }

    private void showlist(String v) {


        recyclerView = (RecyclerView)findViewById(R.id.recler_seeAppoinment_dr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(SeeAppinmentDoctorActivity.this, "Inside showlist level 1:"+v, Toast.LENGTH_SHORT).show();
        DatabaseReference mReff = FirebaseDatabase.getInstance().getReference("Appionment");
        FirebaseRecyclerOptions opt = new FirebaseRecyclerOptions.Builder<Appoinment>().setQuery(mReff.orderByChild("Doctor_Name").equalTo(v),Appoinment.class).build();
        Toast.makeText(SeeAppinmentDoctorActivity.this, "Insdide showlist Level 2:"+v, Toast.LENGTH_SHORT).show();
        FirebaseRecyclerAdapter<Appoinment, SeeApoinmentViewHolder> adapter = new FirebaseRecyclerAdapter<Appoinment, SeeApoinmentViewHolder>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull SeeApoinmentViewHolder holder, int position, @NonNull Appoinment model) {
                holder.name.setText(model.getPatient_Name());
            }

            @NonNull
            @Override
            public SeeApoinmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_see_appoinment,parent,false);
                return new SeeApoinmentViewHolder(v);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);



    }
}
