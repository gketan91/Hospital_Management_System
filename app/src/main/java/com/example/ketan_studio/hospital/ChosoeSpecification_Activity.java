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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.internal.FlowLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Model.Specification;
import ViewHolder.SpecificationShowViewHolder;

public class ChosoeSpecification_Activity extends AppCompatActivity {

    RecyclerView recyclerview_Choose_Speci_id;
    FirebaseRecyclerAdapter<Specification, SpecificationShowViewHolder> adapter;
    DatabaseReference myReff;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosoe_specification_);
        recyclerview_Choose_Speci_id = (RecyclerView)findViewById(R.id.recyclerview_Choose_Speci_id);
        recyclerview_Choose_Speci_id.setLayoutManager(new LinearLayoutManager(this));
        myReff = FirebaseDatabase.getInstance().getReference("Specification");
        mAuth = FirebaseAuth.getInstance();

        FirebaseRecyclerOptions opt = new FirebaseRecyclerOptions.Builder<Specification>().setQuery(myReff,Specification.class).build();
        adapter = new FirebaseRecyclerAdapter<Specification, SpecificationShowViewHolder>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull SpecificationShowViewHolder holder, int position, @NonNull final Specification model) {
                holder.spec.setText(String.valueOf(model.getSpecification()));
                String url = model.getImage();
                Log.i("Error",": "+url);
                Picasso.get().load(url).fit().into(holder.pic);
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ChosoeSpecification_Activity.this,Doctor_Choose_Activity.class);
                        i.putExtra("Specification Name",model.getSpecification());
                        startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public SpecificationShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_choose_specification,parent,false);
                return new SpecificationShowViewHolder(v);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerview_Choose_Speci_id.setAdapter(adapter);

    }
}
