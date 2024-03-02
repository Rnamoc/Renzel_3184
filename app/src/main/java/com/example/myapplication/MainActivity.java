package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textLevel;
    TextView textRightAnswered;
    TextView textQuestion;

    Button buttonOp1;
    Button buttonOp2;
    Button buttonOp3;

    int level = 0;
    int great = 0;
    int rightAnswer = 0;
    String realOperation = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

        textLevel = findViewById(R.id.textQuestionNumber);
        textRightAnswered = findViewById(R.id.textRightAnswered);
        textQuestion = findViewById(R.id.textQuestion);

        buttonOp1 = findViewById(R.id.buttonOption1);
        buttonOp2 = findViewById(R.id.buttonOption2);
        buttonOp3 = findViewById(R.id.buttonOption3);

        textLevel.setText("Q : " + level + " / 10");
        textRightAnswered.setText("RA : " + great + " / 10");

        if (level < 10) {
            getARandomQuestion();
        }
    }

    @SuppressLint("SetTextI18n")
    private void getARandomQuestion() {
        buttonOp1.setBackgroundResource(R.drawable.buttons_option_bg);
        buttonOp2.setBackgroundResource(R.drawable.buttons_option_bg);
        buttonOp3.setBackgroundResource(R.drawable.buttons_option_bg);

        int firstNumber = new Random().nextInt(10);
        int secondNumber = new Random().nextInt(10);
        int operation = new Random().nextInt(3) + 1;
        int optionA = new Random().nextInt(100);
        int optionB = new Random().nextInt(100);

        if (operation == 1) {
            realOperation = "+";
            rightAnswer = firstNumber + secondNumber;
            textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
        } else if (operation == 2) {
            realOperation = "*";
            rightAnswer = firstNumber * secondNumber; // Fixed: multiplication
            textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
        } else {
            realOperation = "-";
            if (firstNumber < secondNumber) {
                rightAnswer = secondNumber - firstNumber;
                textQuestion.setText(secondNumber + " " + realOperation + " " + firstNumber + " = ?");
            } else {
                rightAnswer = firstNumber - secondNumber;
                textQuestion.setText(firstNumber + " " + realOperation + " " + secondNumber + " = ?");
            }
        }

        int position = new Random().nextInt(3) + 1;

        if (position == 1) {
            buttonOp1.setText(String.valueOf(rightAnswer));
            buttonOp2.setText(String.valueOf(optionA));
            buttonOp3.setText(String.valueOf(optionB));
        } else if (position == 2) {
            buttonOp1.setText(String.valueOf(optionA));
            buttonOp2.setText(String.valueOf(rightAnswer));
            buttonOp3.setText(String.valueOf(optionB));
        } else {
            buttonOp1.setText(String.valueOf(optionA));
            buttonOp2.setText(String.valueOf(optionB));
            buttonOp3.setText(String.valueOf(rightAnswer));
        }

        buttonOp1.setOnClickListener(v -> handleButtonClick(buttonOp1));
        buttonOp2.setOnClickListener(v -> handleButtonClick(buttonOp2));
        buttonOp3.setOnClickListener(v -> handleButtonClick(buttonOp3));
    }

    @SuppressLint("SetTextI18n")
    private void handleButtonClick(Button button) {
        if (button.getText().toString().equals(String.valueOf(rightAnswer))) {
            button.setBackgroundResource(R.drawable.right_answer_bg);
            great++;
        } else {
            button.setBackgroundResource(R.drawable.incorrect_answer_bg);
        }

        level++;
        textLevel.setText("Q : " + level + " / 10");
        textRightAnswered.setText("RA : " + great + " / 10");

        new Handler().postDelayed(() -> {
            if (level < 10) {
                getARandomQuestion();
            } else {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("RA", great);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
