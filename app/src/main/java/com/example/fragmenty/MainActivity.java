package com.example.fragmenty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private List<Question> questionList;
    private int currentQuestion = 0;
    private boolean quizFinished = false;
    private TextView timeTV;
    private int points = 0;
    private QuestionFragment questionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeTV = findViewById(R.id.timeTV);
        questionList = new ArrayList<>();
        questionList.add(new Question("Jakiego koloru jest flaga Polski?", "Biało-czerwona", "Niebiesko-żółta", "Czerwono-Biała", 0));
        questionList.add(new Question("Jakie miasto jest stolicą Polski?", "Gniezno", "Kraków", "Warszawa", 2));
        questionList.add(new Question("Co jest godłem Polski?", "Jastrząb", "Orzeł", "Lew", 1));
    }

    @Override
    protected void onResume() {
        super.onResume();
        startQuiz();
    }
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }
    private void startQuiz() {
        quizFinished = false;
        points = 0;
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTime(millisUntilFinished);
            }
            @Override
            public void onFinish() {
                finishQuiz();
            }
        };
        countDownTimer.start();
        nextQuestion();
    }
    private void finishQuiz() {
        quizFinished = true;
        countDownTimer.cancel();
        timeTV.setText("Koniec quizu!");
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setEnabled(false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.linearLayout, SummaryFragment.newInstance(points, questionList.size()));
        transaction.commit();
    }
    private void nextQuestion() {
        if (quizFinished) { return;}
        if (currentQuestion == questionList.size()) {
            finishQuiz();
            return;
        }
        Question question = questionList.get(currentQuestion);
        questionFragment = QuestionFragment.newInstance(question);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.linearLayout, questionFragment);
        transaction.commit();
        currentQuestion++;
    }
    private void updateTime(long millisUntilFinished) {
        int time = (int) (millisUntilFinished / 1000);
        timeTV.setText(String.format("Czas do końca: %d s", time));
    }
    public void nextQuestion(View view) {
        int result = questionFragment.getResult();
        if (result == 0) {
            Toast.makeText(this, "Niestety", Toast.LENGTH_SHORT).show();
            nextQuestion();
        }
        if (result == 1) {
            Toast.makeText(this, "Brawo", Toast.LENGTH_SHORT).show();
            points++;
            nextQuestion();
        }
        if (result == 2) {
            Toast.makeText(this, "Zaznacz odpowiedż", Toast.LENGTH_SHORT).show();
        }
    }
}
