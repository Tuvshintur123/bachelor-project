package com.example.myhealth.ui.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myhealth.MainActivity;
import com.example.myhealth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private Button mBtnLogin;
    private TextView mTxtSignUp;
    private EditText mEdtTxtEmail;
    private EditText mEdtTxtPassword;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        mTxtSignUp = root.findViewById(R.id.txt_signUp);
        mBtnLogin = root.findViewById(R.id.btn_login);
        mEdtTxtEmail = root.findViewById(R.id.txt_email);
        mEdtTxtPassword = root.findViewById(R.id.txt_password);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                if (isValide())
                    login();
                else
                    showErrorMessage();
            }
        });
        mTxtSignUp.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigate_login_to_question_goal, null));
        return root;
    }

    private boolean isValide() {
        return true;
    }

    private void login() {
        mAuth.signInWithEmailAndPassword(mEdtTxtEmail.getText().toString(), mEdtTxtPassword.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startMainActivity();
                        } else {
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showErrorMessage() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
