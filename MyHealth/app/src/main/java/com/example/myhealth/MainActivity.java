package com.example.myhealth;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myhealth.model.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FloatingActionButton actionButton;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Dialog dialog = new Dialog(this);
            actionButton = findViewById(R.id.action_button);
            dialog.setContentView(R.layout.weight_popup);

            actionButton.setOnClickListener(v -> {
                ImageView mImgAdd = dialog.findViewById(R.id.imageView_add);
                ImageView mImgSub = dialog.findViewById(R.id.imageView_sub);
                TextView mTxtDec = dialog.findViewById(R.id.txt_decimal);
                TextView mTxtFrac = dialog.findViewById(R.id.txt_frac);
                TextView mTxtSave = dialog.findViewById(R.id.txt_save);
                Profile profile = Profile.getInstance();
                AtomicBoolean dec = new AtomicBoolean(false);
                AtomicBoolean frac = new AtomicBoolean(true);
                mTxtDec.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorGrayWhite));
                mTxtFrac.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorWhite));
                mTxtFrac.setTextSize(38);
                mTxtDec.setTextSize(30);
                String doubleAsString = String.valueOf(profile.getCurrentWeight());
                int indexOfDecimal = doubleAsString.indexOf(".");

                mTxtDec.setText(doubleAsString.substring(0, indexOfDecimal));
                mTxtFrac.setText(doubleAsString.substring(indexOfDecimal + 1, indexOfDecimal + 2));

                mTxtDec.setOnClickListener(v1 -> {
                    dec.set(true);
                    frac.set(false);
                    mTxtDec.setTextSize(38);
                    mTxtFrac.setTextSize(30);
                    mTxtDec.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorWhite));
                    mTxtFrac.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorGrayWhite));
                });
                mTxtFrac.setOnClickListener(v2 -> {
                    dec.set(false);
                    frac.set(true);
                    mTxtDec.setTextSize(30);
                    mTxtFrac.setTextSize(38);
                    mTxtDec.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorGrayWhite));
                    mTxtFrac.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorWhite));
                });

                mImgAdd.setOnClickListener(v3 -> {
                    if (dec.get()) {
                        mTxtDec.setText(String.valueOf(Integer.parseInt(mTxtDec.getText().toString().trim()) + 1));
                    } else {
                        if (Integer.parseInt(mTxtFrac.getText().toString().trim()) == 9) {
                            mTxtDec.setText(String.valueOf(Integer.parseInt(mTxtDec.getText().toString().trim()) + 1));
                            mTxtFrac.setText("0");
                        } else {
                            mTxtFrac.setText(String.valueOf(Integer.parseInt(mTxtFrac.getText().toString().trim()) + 1));
                        }
                    }

                });

                mImgSub.setOnClickListener(v3 -> {
                    if (Integer.parseInt(mTxtDec.getText().toString().trim()) > 0)
                        if (dec.get()) {
                            mTxtDec.setText(String.valueOf(Integer.parseInt(mTxtDec.getText().toString().trim()) - 1));
                        } else {
                            if (Integer.parseInt(mTxtFrac.getText().toString().trim()) == 0) {
                                mTxtDec.setText(String.valueOf(Integer.parseInt(mTxtDec.getText().toString().trim()) - 1));
                                mTxtFrac.setText("9");
                            } else {
                                mTxtFrac.setText(String.valueOf(Integer.parseInt(mTxtFrac.getText().toString().trim()) - 1));
                            }
                        }
                });


                mTxtSave.setOnClickListener(f -> {
                    database = FirebaseDatabase.getInstance();

                    String value = mTxtDec.getText().toString().trim() + "." + mTxtFrac.getText().toString().trim();
                    profile.setCurrentWeight(Double.parseDouble(value));
                    profile.getHistory().getDate(Calendar.getInstance()).getWeight().setAmount(Double.parseDouble(value));
                    profile.calcBmi(Calendar.getInstance());
                    profile.calcBodyFat();


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    myRef = database.getReference("profiles").child(currentUser.getUid());
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("history/" + sdf.format(calendar.getTime()) + "/weight/amount", profile.getCurrentWeight());
                    data.put("history/" + sdf.format(calendar.getTime()) + "/bmi", profile.getHistory().getDate(Calendar.getInstance()).getBmi());
                    data.put("history/" + sdf.format(calendar.getTime()) + "/bodyFat", profile.getHistory().getDate(Calendar.getInstance()).getBodyFat());
                    data.put("currentWeight", profile.getCurrentWeight());
                    myRef.updateChildren(data, (databaseError, databaseReference) -> {
                        if (databaseError == null)
                            dialog.dismiss();
                    });

                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            });

            BottomNavigationView navView = findViewById(R.id.nav_view);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_dashboard, R.id.navigation_home, R.id.navigation_diet, R.id.navigation_water, R.id.navigation_settings)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
            this.getSupportActionBar().hide();
        } else {
            Intent intent = new Intent(this, EntryActivity.class);
            startActivity(intent);
            finish();
        }


    }


}
