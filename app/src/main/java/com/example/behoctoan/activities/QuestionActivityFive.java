package com.example.behoctoan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.behoctoan.Models.QuestionModel;
import com.example.behoctoan.R;
import com.example.behoctoan.databinding.ActivityQuestionFiveBinding;

import java.util.ArrayList;

public class QuestionActivityFive extends AppCompatActivity {

    private ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    private CountDownTimer timer;
    private ActivityQuestionFiveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionFiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        resetTimer();
        timer.start();

        String setName = getIntent().getStringExtra("set");

        if (setName != null && setName.equals("BÀI LUYỆN TẬP 1")) {
            setOne();
        }

        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);
                }
            });
        }

        playAnimation(binding.question, 0, list.get(position).getQuestion());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    timer.cancel();
                }
                timer.start();

                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float) 0.3);
                enableOption(true);
                position++;

                if (position == list.size()) {
                    Intent intent = new Intent(QuestionActivityFive.this, ScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                count = 0;
                playAnimation(binding.question, 0, list.get(position).getQuestion());
            }
        });
    }

    private void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                binding.timer.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(QuestionActivityFive.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionActivityFive.this, SetActivityFive.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        };
    }

    private void playAnimation(final View view, final int value, final String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        if (value == 0 && count < 4) {
                            String option = "";

                            if (count == 0) {
                                option = list.get(position).getOptionA();
                            } else if (count == 1) {
                                option = list.get(position).getOptionB();
                            } else if (count == 2) {
                                option = list.get(position).getOptionC();
                            } else if (count == 3) {
                                option = list.get(position).getOptionD();
                            }

                            playAnimation(binding.optionContainer.getChildAt(count), 0, option);
                            count++;
                        }
                    }


                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (value == 0) {
                            try {
                                ((TextView) view).setText(data);
                                binding.totalQuestion.setText(position + 1 + "/" + list.size());
                            } catch (Exception e) {
                                ((Button) view).setText(data);
                            }

                            view.setTag(data);
                            playAnimation(view, 1, data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {

                    }
                });
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setEnabled(enable);

            if (enable) {
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }
    }

    private void checkAnswer(Button selectedOption) {
        if (timer != null) {
            timer.cancel();
        }

        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);

        if (selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())) {
            score++;
            selectedOption.setBackgroundResource(R.drawable.right_answ);
        } else {
            selectedOption.setBackgroundResource(R.drawable.wrong_answ);
            Button correctOption = (Button) binding.optionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.right_answ);
        }
    }

    private void setOne() {
        list.add(new QuestionModel("hi", "A. 1", "B. 2", "C. 3", "D. 4", "D. 4"));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
        list.add(new QuestionModel("", "A. ", "B. ", "C. ", "D. ", ""));
    }
}
