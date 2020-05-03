package com.example.myhealth.ui.question;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myhealth.R;
import com.example.myhealth.ui.signup.SignUpViewModel;


public class QuestionGenderFragment extends Fragment {

    private Button mBtnNext;
    private RadioGroup mRadioGroup;
    private RadioButton mRdBtnMale, mRdBtnFemale;
    private SignUpViewModel signUpViewModel;

    public QuestionGenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_question_gender, container, false);
        mRadioGroup = root.findViewById(R.id.radioGroup);
        mRdBtnFemale = root.findViewById(R.id.rdBtn_female);
        mRdBtnMale = root.findViewById(R.id.rdBtn_male);
        mBtnNext = root.findViewById(R.id.btn_next);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        mBtnNext.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigate_question_gender_to_question_activity_level, null));

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            mBtnNext.setEnabled(true);
            if (checkedId == R.id.rdBtn_female) {
                mRdBtnFemale.setBackgroundColor(Color.GRAY);
                mRdBtnMale.setBackgroundColor(Color.WHITE);
                signUpViewModel.setGender("Эмэгтэй");
            }
            else {
                mRdBtnMale.setBackgroundColor(Color.GRAY);
                mRdBtnFemale.setBackgroundColor(Color.WHITE);
                signUpViewModel.setGender("Эрэгтэй");
            }
        });

        return root;
    }
}
