package com.example.myhealth.ui.question;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myhealth.R;
import com.example.myhealth.ui.signup.SignUpViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionCurrentWeightFragment extends Fragment {

    private Button mBtnNext;
    private EditText mTxtCurrentWeight;
    private SignUpViewModel signUpViewModel;

    public QuestionCurrentWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_question_current_weight, container, false);
        mBtnNext = root.findViewById(R.id.btn_next);
        mTxtCurrentWeight = root.findViewById(R.id.edtTxt_current_weight);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
        mBtnNext.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigate_question_current_weight_to_question_height, null));

        mTxtCurrentWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(mTxtCurrentWeight.getText())) {
                    mBtnNext.setEnabled(true);
                    signUpViewModel.setCurrentWeight(Double.valueOf(mTxtCurrentWeight.getText().toString()));
                }
            }
        });


        return root;
    }
}
