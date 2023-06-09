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

public class QuestionActivityThree extends AppCompatActivity {

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
                    enableOption(false);
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
                    Intent intent = new Intent(QuestionActivityThree.this, ScoreActivity.class);
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
                Dialog dialog = new Dialog(QuestionActivityThree.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionActivityThree.this, SetsActivityThree.class);
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
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setTwo() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setThree() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setFour() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setFive() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setSix() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setSeven() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setEight() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setNine() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }
    private void setTen() {
        list.add(new QuestionModel("Cho dãy số liệu: 8; 1998; 195; 2007; 1000; 71 768; 9999; 17. Dãy trên có tất cả:", "A. 11 số", "B. 9 số", "C. 8 số", "D. 10 số", "C. 8 số"));
        list.add(new QuestionModel("Tổng của 47 856 và 35 687 là:", "A. 83433", "B. 82443", "C. 83543", "D. 82543", "C. 83543"));
        list.add(new QuestionModel("Trong các số dưới đây, số nào không thuộc dãy số: 1, 4, 7, 10, 13, ...", "A. 1000", "B. 1234", "C. 2007", "D. 100", "A. 1000"));
        list.add(new QuestionModel("Mai có 7 viên bi, Hồng có 15 viên bi. Hỏi Hồng phải cho Mai bao nhiêu viên bi để số bi của hai bạn bằng nhau.", "A. 3 viên", "B. 5 viên", "C. 4 viên", "D. 6 viên", "C. 4 viên"));
        list.add(new QuestionModel("Tìm x biết: 8462 - x = 762", "A. x = 8700", "B. x = 6700", "C. x = 7600", "D. x = 7700", "D. x = 7700"));
        list.add(new QuestionModel("Số nhỏ nhất có 4 chữ số là:", "A. 1011", "B. 1001", "C. 1000", "D. 1111", "C. 1000"));
        list.add(new QuestionModel("Số lẻ liền sau số 2007 là:", "A. 2008", "B. 2009", "C. 2017", "D. 2005", "B. 2009"));
        list.add(new QuestionModel("Hiệu của số lớn nhất có bốn chữ số và số nhỏ nhất có ba chữ số là:", "A. 9899", "B. 9999", "C. 9888", "D. 8888", "A. 9899"));
        list.add(new QuestionModel("Biết 356a7 > 35679 giá trị của a là:", "A. 0", "B. 10", "C. 7", "D. 9", "D. 9"));
        list.add(new QuestionModel("Lớp 3A có 28 học sinh. Nếu số học sinh lớp 3A xếp đều vào 7 hàng thì lớp 3B có 6 hàng như thế. Hỏi lớp 3B có bao nhiêu học sinh?", "A.  34 học sinh", "B.  27 học sinh", "C.  21 học sinh", "D.  24 học sinh", "D.  24 học sinh"));
    }


}