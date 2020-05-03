package com.example.myhealth.ui.signup;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myhealth.R;
import com.example.myhealth.model.History;
import com.example.myhealth.model.Profile;
import com.example.myhealth.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private Button mBtnSignUp;
    private TextView mTxtLogin;
    private EditText mEdtTxtName;
    private EditText mEdtTxtEmail;
    private EditText mEdtTxtPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private SignUpViewModel signUpViewModel;
    private User newUser;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mBtnSignUp = root.findViewById(R.id.btn_signUp);
        mTxtLogin = root.findViewById(R.id.txt_login);
        mEdtTxtEmail = root.findViewById(R.id.txt_email);
        mEdtTxtName = root.findViewById(R.id.txt_name);
        mEdtTxtPassword = root.findViewById(R.id.txt_password);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();

        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
        Log.i("result", "goal: " + signUpViewModel.getGoal().getValue()
                + "\ngender: " + signUpViewModel.getGender().getValue()
                + "\nactivity level: " + signUpViewModel.getActivityLevel().getValue()
                + "\nweight: " + signUpViewModel.getCurrentWeight().getValue()
                + "\nheight: " + signUpViewModel.getHeight().getValue()
                + "\nbirthday: " + Calendar.getInstance()
                + "\ntarget weight: " + signUpViewModel.getTargetWeight().getValue());

        mBtnSignUp.setOnClickListener(v -> {
            mProgressBar.setVisibility(View.VISIBLE);
            if (isValide())
                signUp(v);
            else
                showErrorMessage();
        });

        mTxtLogin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigate_signUp_to__login, null));

        return root;
    }

    private boolean isValide() {
        newUser = new User(mEdtTxtName.getText().toString().trim(), mEdtTxtEmail.getText().toString().trim(), mEdtTxtPassword.getText().toString().trim());
        return true;
    }

    private void signUp(final View v) {
        mAuth.createUserWithEmailAndPassword(newUser.getEmail(), newUser.getPassword())
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUser(user, v);
                    } else {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUser(final FirebaseUser user, final View v) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newUser.getName())
                .build();
        user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
            Profile.setProfile(new Profile(user.getUid(), user.getDisplayName(), user.getEmail(), newUser.getPassword(), signUpViewModel.getGoal().getValue(), signUpViewModel.getGender().getValue(), signUpViewModel.getActivityLevel().getValue(), signUpViewModel.getCurrentWeight().getValue(), signUpViewModel.getHeight().getValue(), signUpViewModel.getBirthday().getValue(), signUpViewModel.getTargetWeight().getValue(), new History()));
            Profile profile = Profile.getInstance();
            profile.calcWaterGoal();

            mProgressBar.setVisibility(View.INVISIBLE);
            Navigation.findNavController(v).navigate(R.id.navigate_signUp_to_recommendation);
        });
    }

    private void showErrorMessage() {
        mProgressBar.setVisibility(View.INVISIBLE);

    }



}
