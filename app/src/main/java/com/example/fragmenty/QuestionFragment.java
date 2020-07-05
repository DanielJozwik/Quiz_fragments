package com.example.fragmenty;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
public class QuestionFragment extends Fragment {
    private Question question;
    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    public QuestionFragment() {
    }
    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable("question", question);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable("question");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_question_fragment, container, false);
        TextView questionTV = view.findViewById(R.id.questionTV);
        questionTV.setText(question.getQuestion());
        rb1 = view.findViewById(R.id.rb1);
        rb2 = view.findViewById(R.id.rb2);
        rb3 = view.findViewById(R.id.rb3);
        rg = view.findViewById(R.id.rg);
        rb1.setText(question.getAnswer1());
        rb2.setText(question.getAnswer2());
        rb3.setText(question.getAnswer3());
        return view;
    }
    public int getResult() {
        if (rg.getCheckedRadioButtonId() == -1) {
            return 2;
        }
        switch (question.getCorrectAnswer()) {
            case 0: if(rb1.isChecked()) return 1; break;
            case 1: if(rb2.isChecked()) return 1; break;
            case 2: if(rb3.isChecked()) return 1; break;
            default: return 0;
        }
        return 0;
    }
}
