package com.example.ketan_studio.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import Model.ChooseDr;
import ViewHolder.ChooseDrViewHolder;

public class Doctor_Choose_Activity extends AppCompatActivity {
    TextView maintitle;
    RecyclerView recyclerView;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__choose_);
        Intent i = getIntent();
        title = i.getStringExtra("Specification Name");
        maintitle = (TextView)findViewById(R.id.title_doctor_choose_id);
        maintitle.setText(title);

        recyclerView = (RecyclerView)findViewById(R.id.recler_dr_choose_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showDoctor();

    }

    private void showDoctor() {
        DatabaseReference mReff = FirebaseDatabase.getInstance().getReference("Doctor");
        FirebaseRecyclerOptions opt = new FirebaseRecyclerOptions.Builder<ChooseDr>().setQuery(mReff.orderByChild("Specification").equalTo(title),ChooseDr.class).build();
        FirebaseRecyclerAdapter<ChooseDr, ChooseDrViewHolder> adapter = new FirebaseRecyclerAdapter<ChooseDr, ChooseDrViewHolder>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull ChooseDrViewHolder holder, int position, @NonNull ChooseDr model) {
                final String name = model.getName();
                final String quail = model.getQualification();
                holder.drname.setText(name);
                holder.drQuali.setText(quail);
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Doctor_Choose_Activity.this,ConfirmBooking.class);
                        i.putExtra("drname",name);
                        i.putExtra("drQuai",quail);
                        startActivity(i);
                    }
                });
            }

            @NonNull
            @Override
            public ChooseDrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_choose_dr,parent,false);
                return new ChooseDrViewHolder(v);
            }
        };
        adapter.notifyDataSetChanged();
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
