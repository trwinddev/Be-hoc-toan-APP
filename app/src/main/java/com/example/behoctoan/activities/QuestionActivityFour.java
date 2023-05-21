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
import com.example.behoctoan.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

public class QuestionActivityFour extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;

    ActivityQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        resetTimer();
        timer.start();

        String setName = getIntent().getStringExtra("set");

        if(setName.equals("BÀI LUYỆN TẬP 1")) {
            setOne();
        } else if (setName.equals("BÀI LUYỆN TẬP 2")) {
            setTwo();
        } else if (setName.equals("BÀI LUYỆN TẬP 3")) {
            setThree();
        } else if (setName.equals("BÀI LUYỆN TẬP 4")) {
            setFour();
        } else if (setName.equals("BÀI LUYỆN TẬP 5")) {
            setFive();
        } else if (setName.equals("BÀI LUYỆN TẬP 6")) {
            setSix();
        } else if (setName.equals("BÀI LUYỆN TẬP 7")) {
            setSeven();
        } else if (setName.equals("BÀI LUYỆN TẬP 8")) {
            setEight();
        } else if (setName.equals("BÀI LUYỆN TẬP 9")) {
            setNine();
        } else if (setName.equals("BÀI LUYỆN TẬP 10")) {
            setTen();
        }

        for(int i=0; i<4; i++) {
            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener(){
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
                if(timer != null) {
                    timer.cancel();
                }
                timer.start();

                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float) 0.3);
                enableOption(true);
                position++;

                if(position == list.size()) {
                    Intent intent = new Intent(QuestionActivityFour.this, ScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", list.size());
                    startActivity(intent);;
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
                binding.timer.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(QuestionActivityFour.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionActivityFour.this, SetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        };
    }

    private void playAnimation(View view, int value, String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {
                        if(value==0 && count < 4) {
                            String option = "";

                            if(count==0) {
                                option = list.get(position).getOptionA();
                            }else if(count==1) {
                                option = list.get(position).getOptionB();
                            }else if(count==2) {
                                option = list.get(position).getOptionC();
                            }else if(count==3) {
                                option = list.get(position).getOptionD();
                            }

                            playAnimation(binding.optionContainer.getChildAt(count), 0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        if(value==0) {
                            try {
                                ((TextView)view).setText(data);
                                binding.totalQuestion.setText(position+1+"/"+list.size());
                            }catch (Exception e) {
                                ((Button)view).setText(data);
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

            if(enable) {
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }
    }

    private void checkAnswer(Button selectedOption) {
        if(timer != null) {
            timer.cancel();
        }

        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);

        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())) {
            score++;
            selectedOption.setBackgroundResource(R.drawable.right_answ);
        }
        else {
            selectedOption.setBackgroundResource(R.drawable.wrong_answ);
            Button correctOption = (Button) binding.optionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.right_answ);
        }
    }

    private void setOne() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setTwo() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setThree() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setFour() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setFive() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setSix() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setSeven() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setEight() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setNine() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }
    private void setTen() {
        list.add(new QuestionModel("Kết quả của 3 + 5 là:", "A. 5", "B. 7", "C. 8", "D. 9", "C. 8"));
        list.add(new QuestionModel("Số cần điền vào: ... – 2 = 3 là:", "A. 1", "B. 5", "C. 9", "D. 4", "B. 5"));
        list.add(new QuestionModel("Sắp xếp các số: 0, 5, 2, 10 theo thứ tự từ bé đến lớn:", "A. 10, 5, 2, 0", "B. 2 , 0 , 10 , 5", "C. 5, 2, 10, 0", "D. 0 , 2 , 5 , 10", "D. 0 , 2 , 5 , 10"));
        list.add(new QuestionModel("Số cần điền vào: 7 + 1 > ... + 2 là:", "A. 7", "B. 5", "C. 8", "D. 10", "B. 5"));
        list.add(new QuestionModel("Số lớn nhất có một chữ số là:", "A. 0", "B. 8", "C. 9", "D. 10", "C. 9"));
        list.add(new QuestionModel("Số bé nhất trong các số: 8, 3, 10, 6 là:", "A. 8", "B. 3", "C. 10", "D. 6", "B. 3"));
        list.add(new QuestionModel("Kết quả của phép tính: 10 – 8 + 3 là:", "A. 7", "A. 1", "C. 8", "D. 5", "D. 5"));
        list.add(new QuestionModel("Số bé nhất có một chữ số là:", "A. 1", "B. 0", "C. 2", "D. 9", "B. 0"));
        list.add(new QuestionModel("Số lớn nhất trong các số: 1, 9, 4, 7 là:", "A. 7", "B. 4", "C. 9", "D. 1", "C. 9"));
        list.add(new QuestionModel("Phép tính nào sai?", "A. 7 – 5 = 2", "B. 4 + 4 = 9", "3 + 3 = 6", "C. 10 – 9 = 1", "B. 4 + 4 = 9"));
    }

}