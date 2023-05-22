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
import com.example.behoctoan.databinding.ActivityQuestionFourBinding;
import com.example.behoctoan.databinding.ActivityQuestionTwoBinding;

import java.util.ArrayList;

public class QuestionFourActivity extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;

    ActivityQuestionFourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionFourBinding.inflate(getLayoutInflater());
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
                    Intent intent = new Intent(QuestionFourActivity.this, ScoreActivity.class);
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
                Dialog dialog = new Dialog(QuestionFourActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionFourActivity.this, SetsTwoActivity.class);
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
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setTwo() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setThree() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setFour() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setFive() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setSix() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setSeven() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setEight() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setNine() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }
    private void setTen() {
        list.add(new QuestionModel("Số bốn mươi lăm nghìn ba trăm linh tám được viết là:", "A. 45307", "B. 45308", "C. 45380", "D. 45038", "B. 45308"));
        list.add(new QuestionModel("Một cửa hàng trong hai ngày bán được 620 kg gạo. Hỏi trong 7 ngày cửa hàng bán được bao nhiêu ki-lô-gam gạo? (Biết rằng số gạo mỗi ngày bán được là như nhau).", "A. 4340 kg", "B. 217 kg", "C. 434 kg", "D. 2170 kg", "D. 2170 kg"));
        list.add(new QuestionModel("Số đo chiều cao của 5 học sinh lớp Năm lần lượt là: 148 cm; 146 cm ; 144 cm ; 142 cm; 140 cm. Hỏi trung bình số đo chiều cao của mỗi cm là bao nhiêu xăng-ti-mét?", "A. 144 cm", "B. 142 cm", "C. 145 cm", "D. 146 cm", "C. 145 cm"));
        list.add(new QuestionModel("Số trung bình cộng của hai số bằng 40. Biết rằng một trong hai số đó bằng 58. Tìm số kia?", "A. 98", "B. 18", "C. 49", "D. 22", "A. 98"));
        list.add(new QuestionModel("Một người đi xe máy trong 1/5 phút được 324 m. Hỏi trong một giây người ấy đi được bao nhiêu mét?", "A. 27 m", "B. 12 m", "C. 270 m", "D. 3888 m", "A. 27 m"));
        list.add(new QuestionModel("Năm 1459 thuộc thế kỷ thứ mấy?", "A. XII", "B. XIII", "C. XIV", "D. XV", "C. XIV"));
        list.add(new QuestionModel("Tìm x biết: 6 < x < 9 và x là số lẻ:", "A. 6", "B. 7", "C. 8", "D. 9", "B. 7"));
        list.add(new QuestionModel("Số tự nhiên liền trước số 10001 là:", "A. 10011", "B. 10002", "C. 10021", "D. 10000", "D. 10000"));
        list.add(new QuestionModel("Giá trị của chữ số 8 trong số sau: 45873246.", "A. 8 000", "B. 80 000", "C. 800 000", "D. 8 000 000", "C. 800 000"));
        list.add(new QuestionModel("Đọc số sau: 325600608", "A. Ba trăm hai mươi lăm triệu sáu mươi nghìn sáu trăm linh tám", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám", "C. Ba trăm hai mươi lăm triệu sáu nghìn sáu trăm linh tám", "D. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm tám mươi", "B. Ba trăm hai mươi lăm triệu sáu trăm nghìn sáu trăm linh tám"));
    }

}