package com.example.ketan_studio.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button logout;
    Button bookAppoinment;
    ImageSlider img;
    ArrayList imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        logout= (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUsertoLoginPage();
            }
        });

        bookAppoinment = (Button)findViewById(R.id.bookApoinment);
        bookAppoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUsertoChooseSpecificationPage();
            }
        });


        List<SlideModel> imagelist = new ArrayList<>();
        imagelist.add(new SlideModel("https://i.ytimg.com/vi/-tgHw3wUWoY/maxresdefault.jpg","One"));
        imagelist.add(new SlideModel("http://www.viendoraglass.com/size/1280x720/server10-cdn/2015/09/07/hospital-reception-b88ae5378ba3b230.jpg","Two"));
        imagelist.add(new SlideModel("http://www.suncityvillas.com/size/1280x720/server13-cdn/2016/05/11/hospital-elevator-lobby-hospital-room-interior-design-795df320b3405bcb.jpg","Three"));


        img = (ImageSlider)findViewById(R.id.imagesliders);
        img.setImageList(imagelist,true);

    }

    private void SendUsertoChooseSpecificationPage() {
        Intent sul=new Intent(MainActivity.this,ChosoeSpecification_Activity.class);
        startActivity(sul);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent sul=new Intent(MainActivity.this,DoctorHomePage.class);
//        startActivity(sul);
//        finish();
//    }

        @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user==null){
            SendUsertoLoginPage();
        }
    }

    private void SendUsertoLoginPage() {
        Intent sul=new Intent(MainActivity.this,Login_Activity.class);
        sul.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sul);
        finish();
    }
}
