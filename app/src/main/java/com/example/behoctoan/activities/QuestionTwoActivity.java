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
import com.example.behoctoan.databinding.ActivityQuestionTwoBinding;

import java.util.ArrayList;

public class QuestionTwoActivity extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;

    ActivityQuestionTwoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionTwoBinding.inflate(getLayoutInflater());
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
                    Intent intent = new Intent(QuestionTwoActivity.this, ScoreActivity.class);
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
                Dialog dialog = new Dialog(QuestionTwoActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionTwoActivity.this, SetsTwoActivity.class);
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
        list.add(new QuestionModel("Kết quả của phép cộng 67 + 26 là:", "A. 83", "B. 93", "C. 94", "D. 95", "B. 93"));
        list.add(new QuestionModel("Phép trừ 100 - 57 có kết quả là:", "A. 53", "B. 44", "C. 43", "D. 33", "C. 43"));
        list.add(new QuestionModel("Tổng nào dưới đây bé hơn 56?", "A. 50 + 8", "B. 49 + 7", "C. 36 + 29", "D. 48 + 6", "D. 48 + 6"));
        list.add(new QuestionModel("Trong một ngày, cửa hàng bán được 56 kg đường, trong đó buổi sáng bán được 27kg. Hỏi buổi chiều bán được bao nhiêu kg đường?", "A. 83 kg", "B. 29 kg", "C. 39 kg", "D. 13 kg", "B. 29 kg"));
        list.add(new QuestionModel("Thứ ba tuần này là ngày 20 tháng 12. Vậy thứ ba tuần sau là ngày mấy?", "A. Ngày 26 tháng 12", "B. Ngày 27 tháng 12", "C. Ngày 28 tháng 12", "D. Ngày 29 tháng 12", "B. Ngày 27 tháng 12"));
        list.add(new QuestionModel("Hiệu của 73 và 37 là:", "A. 36", "B. 46", "C. 35", "D. 47", "A. 36"));
        list.add(new QuestionModel("Chiều dài của cái giường em nằm ước chừng là:", "A. 50 cm", "B. 2 km", "C. 2 m", "D. 10 dm", "C. 2 m"));
        list.add(new QuestionModel("Số liền trước của 80 là:", "A. 79", "B. 80", "C. 81", "D. 82", "A. 79"));
        list.add(new QuestionModel("Số thích hợp điền vào chỗ chấm 60 cm = ... dm là:", "A. 6 dm", "B. 6", "C. 60", "D. 6 cm", "B. 6"));
        list.add(new QuestionModel("Lukaku và Martinez có 22 quả bóng nhựa. Nếu lấy bớt của Lukaku 5 quả thì hai bạn còn lại bao nhiêu quả bóng nhựa? Hai bạn còn lại số quả bóng nhựa là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
    }
    private void setTwo() {
        list.add(new QuestionModel("Các số 28; 37; 46; 52 được viết theo thứ tự từ lớn đến bé là:", "A. 46; 37; 52; 28", "B. 28; 37; 46; 52", "C. 52; 46; 37; 28", "D. 52; 37; 46; 28", "C. 52; 46; 37; 28"));
        list.add(new QuestionModel("Số lớn hơn 74 và nhỏ hơn 76 là:", "A. 73", "B. 77", "C. 75", "D. 76", "C. 75"));
        list.add(new QuestionModel("Lan và Hồng có 22 quyển truyện tranh. Nếu lấy bớt của Hồng 5 quyển thì hai bạn còn lại bao nhiêu quyển truyện tranh? Hai bạn còn lại số quyển truyện tranh là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
        list.add(new QuestionModel("Số lớn nhất có 2 chữ số khác nhau là:", "A. 99", "B. 89", "C. 98", "D. 100", "C. 98"));
        list.add(new QuestionModel("92 bằng tổng của hai số nào trong cac cặp số sau đây?", "A. 32 và 50", "B. 55 và 47", "C. 37 và 55", "D. 55 và 47", "C. 37 và 55"));
        list.add(new QuestionModel("Lan gấp được 12 máy bay, Thành gấp được 14 máy bay. Hỏi cả hai bạn gấp được bao nhiêu máy bay?", "A. 16 máy bay", "B.  26 máy bay", "C. 2 máy bay", "D. 36 máy bay", "B.  26 máy bay"));
        list.add(new QuestionModel("Tính nhanh: 1 + 2 + 3 + 17 + 18 + 19", "A. 60", "B. 40", "C. 80", "D. 100", "A. 60"));
        list.add(new QuestionModel("Tìm x: x – 17 = 32 – 8", "A. x = 24", "B. x = 41", "C. x = 31", "D. x = 12", "B. x = 41"));
        list.add(new QuestionModel("Linh học bài từ lúc 8 giờ và đi ngủ lúc 10 giờ. Thời gian Linh học bài là mấy giờ?", "A. 18 giờ", "B. 2 giờ", "C. 9 giờ", "D. 6 giờ", "B. 2 giờ"));
        list.add(new QuestionModel("Hôm nay là thứ 6 ngày 23 tháng 8. Vậy thứ sáu tuần sau là ngày bao nhiêu?", "A.  24 tháng 8", "B.  25 tháng 8", "C. 30 tháng 8", "D.  31 tháng 8", "C. 30 tháng 8"));
    }
    private void setThree() {
        list.add(new QuestionModel("Kết quả của phép cộng 67 + 26 là:", "A. 83", "B. 93", "C. 94", "D. 95", "B. 93"));
        list.add(new QuestionModel("Phép trừ 100 - 57 có kết quả là:", "A. 53", "B. 44", "C. 43", "D. 33", "C. 43"));
        list.add(new QuestionModel("Tổng nào dưới đây bé hơn 56?", "A. 50 + 8", "B. 49 + 7", "C. 36 + 29", "D. 48 + 6", "D. 48 + 6"));
        list.add(new QuestionModel("Trong một ngày, cửa hàng bán được 56 kg đường, trong đó buổi sáng bán được 27kg. Hỏi buổi chiều bán được bao nhiêu kg đường?", "A. 83 kg", "B. 29 kg", "C. 39 kg", "D. 13 kg", "B. 29 kg"));
        list.add(new QuestionModel("Thứ ba tuần này là ngày 20 tháng 12. Vậy thứ ba tuần sau là ngày mấy?", "A. Ngày 26 tháng 12", "B. Ngày 27 tháng 12", "C. Ngày 28 tháng 12", "D. Ngày 29 tháng 12", "B. Ngày 27 tháng 12"));
        list.add(new QuestionModel("Hiệu của 73 và 37 là:", "A. 36", "B. 46", "C. 35", "D. 47", "A. 36"));
        list.add(new QuestionModel("Chiều dài của cái giường em nằm ước chừng là:", "A. 50 cm", "B. 2 km", "C. 2 m", "D. 10 dm", "C. 2 m"));
        list.add(new QuestionModel("Số liền trước của 80 là:", "A. 79", "B. 80", "C. 81", "D. 82", "A. 79"));
        list.add(new QuestionModel("Số thích hợp điền vào chỗ chấm 60 cm = ... dm là:", "A. 6 dm", "B. 6", "C. 60", "D. 6 cm", "B. 6"));
        list.add(new QuestionModel("Lukaku và Martinez có 22 quả bóng nhựa. Nếu lấy bớt của Lukaku 5 quả thì hai bạn còn lại bao nhiêu quả bóng nhựa? Hai bạn còn lại số quả bóng nhựa là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
    }
    private void setFour() {
        list.add(new QuestionModel("Các số 28; 37; 46; 52 được viết theo thứ tự từ lớn đến bé là:", "A. 46; 37; 52; 28", "B. 28; 37; 46; 52", "C. 52; 46; 37; 28", "D. 52; 37; 46; 28", "C. 52; 46; 37; 28"));
        list.add(new QuestionModel("Số lớn hơn 74 và nhỏ hơn 76 là:", "A. 73", "B. 77", "C. 75", "D. 76", "C. 75"));
        list.add(new QuestionModel("Lan và Hồng có 22 quyển truyện tranh. Nếu lấy bớt của Hồng 5 quyển thì hai bạn còn lại bao nhiêu quyển truyện tranh? Hai bạn còn lại số quyển truyện tranh là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
        list.add(new QuestionModel("Số lớn nhất có 2 chữ số khác nhau là:", "A. 99", "B. 89", "C. 98", "D. 100", "C. 98"));
        list.add(new QuestionModel("92 bằng tổng của hai số nào trong cac cặp số sau đây?", "A. 32 và 50", "B. 55 và 47", "C. 37 và 55", "D. 55 và 47", "C. 37 và 55"));
        list.add(new QuestionModel("Lan gấp được 12 máy bay, Thành gấp được 14 máy bay. Hỏi cả hai bạn gấp được bao nhiêu máy bay?", "A. 16 máy bay", "B.  26 máy bay", "C. 2 máy bay", "D. 36 máy bay", "B.  26 máy bay"));
        list.add(new QuestionModel("Tính nhanh: 1 + 2 + 3 + 17 + 18 + 19", "A. 60", "B. 40", "C. 80", "D. 100", "A. 60"));
        list.add(new QuestionModel("Tìm x: x – 17 = 32 – 8", "A. x = 24", "B. x = 41", "C. x = 31", "D. x = 12", "B. x = 41"));
        list.add(new QuestionModel("Linh học bài từ lúc 8 giờ và đi ngủ lúc 10 giờ. Thời gian Linh học bài là mấy giờ?", "A. 18 giờ", "B. 2 giờ", "C. 9 giờ", "D. 6 giờ", "B. 2 giờ"));
        list.add(new QuestionModel("Hôm nay là thứ 6 ngày 23 tháng 8. Vậy thứ sáu tuần sau là ngày bao nhiêu?", "A.  24 tháng 8", "B.  25 tháng 8", "C. 30 tháng 8", "D.  31 tháng 8", "C. 30 tháng 8"));
    }
    private void setFive() {
        list.add(new QuestionModel("Kết quả của phép cộng 67 + 26 là:", "A. 83", "B. 93", "C. 94", "D. 95", "B. 93"));
        list.add(new QuestionModel("Phép trừ 100 - 57 có kết quả là:", "A. 53", "B. 44", "C. 43", "D. 33", "C. 43"));
        list.add(new QuestionModel("Tổng nào dưới đây bé hơn 56?", "A. 50 + 8", "B. 49 + 7", "C. 36 + 29", "D. 48 + 6", "D. 48 + 6"));
        list.add(new QuestionModel("Trong một ngày, cửa hàng bán được 56 kg đường, trong đó buổi sáng bán được 27kg. Hỏi buổi chiều bán được bao nhiêu kg đường?", "A. 83 kg", "B. 29 kg", "C. 39 kg", "D. 13 kg", "B. 29 kg"));
        list.add(new QuestionModel("Thứ ba tuần này là ngày 20 tháng 12. Vậy thứ ba tuần sau là ngày mấy?", "A. Ngày 26 tháng 12", "B. Ngày 27 tháng 12", "C. Ngày 28 tháng 12", "D. Ngày 29 tháng 12", "B. Ngày 27 tháng 12"));
        list.add(new QuestionModel("Hiệu của 73 và 37 là:", "A. 36", "B. 46", "C. 35", "D. 47", "A. 36"));
        list.add(new QuestionModel("Chiều dài của cái giường em nằm ước chừng là:", "A. 50 cm", "B. 2 km", "C. 2 m", "D. 10 dm", "C. 2 m"));
        list.add(new QuestionModel("Số liền trước của 80 là:", "A. 79", "B. 80", "C. 81", "D. 82", "A. 79"));
        list.add(new QuestionModel("Số thích hợp điền vào chỗ chấm 60 cm = ... dm là:", "A. 6 dm", "B. 6", "C. 60", "D. 6 cm", "B. 6"));
        list.add(new QuestionModel("Lukaku và Martinez có 22 quả bóng nhựa. Nếu lấy bớt của Lukaku 5 quả thì hai bạn còn lại bao nhiêu quả bóng nhựa? Hai bạn còn lại số quả bóng nhựa là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
    }
    private void setSix() {
        list.add(new QuestionModel("Các số 28; 37; 46; 52 được viết theo thứ tự từ lớn đến bé là:", "A. 46; 37; 52; 28", "B. 28; 37; 46; 52", "C. 52; 46; 37; 28", "D. 52; 37; 46; 28", "C. 52; 46; 37; 28"));
        list.add(new QuestionModel("Số lớn hơn 74 và nhỏ hơn 76 là:", "A. 73", "B. 77", "C. 75", "D. 76", "C. 75"));
        list.add(new QuestionModel("Lan và Hồng có 22 quyển truyện tranh. Nếu lấy bớt của Hồng 5 quyển thì hai bạn còn lại bao nhiêu quyển truyện tranh? Hai bạn còn lại số quyển truyện tranh là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
        list.add(new QuestionModel("Số lớn nhất có 2 chữ số khác nhau là:", "A. 99", "B. 89", "C. 98", "D. 100", "C. 98"));
        list.add(new QuestionModel("92 bằng tổng của hai số nào trong cac cặp số sau đây?", "A. 32 và 50", "B. 55 và 47", "C. 37 và 55", "D. 55 và 47", "C. 37 và 55"));
        list.add(new QuestionModel("Lan gấp được 12 máy bay, Thành gấp được 14 máy bay. Hỏi cả hai bạn gấp được bao nhiêu máy bay?", "A. 16 máy bay", "B.  26 máy bay", "C. 2 máy bay", "D. 36 máy bay", "B.  26 máy bay"));
        list.add(new QuestionModel("Tính nhanh: 1 + 2 + 3 + 17 + 18 + 19", "A. 60", "B. 40", "C. 80", "D. 100", "A. 60"));
        list.add(new QuestionModel("Tìm x: x – 17 = 32 – 8", "A. x = 24", "B. x = 41", "C. x = 31", "D. x = 12", "B. x = 41"));
        list.add(new QuestionModel("Linh học bài từ lúc 8 giờ và đi ngủ lúc 10 giờ. Thời gian Linh học bài là mấy giờ?", "A. 18 giờ", "B. 2 giờ", "C. 9 giờ", "D. 6 giờ", "B. 2 giờ"));
        list.add(new QuestionModel("Hôm nay là thứ 6 ngày 23 tháng 8. Vậy thứ sáu tuần sau là ngày bao nhiêu?", "A.  24 tháng 8", "B.  25 tháng 8", "C. 30 tháng 8", "D.  31 tháng 8", "C. 30 tháng 8"));
    }
    private void setSeven() {
        list.add(new QuestionModel("Kết quả của phép cộng 67 + 26 là:", "A. 83", "B. 93", "C. 94", "D. 95", "B. 93"));
        list.add(new QuestionModel("Phép trừ 100 - 57 có kết quả là:", "A. 53", "B. 44", "C. 43", "D. 33", "C. 43"));
        list.add(new QuestionModel("Tổng nào dưới đây bé hơn 56?", "A. 50 + 8", "B. 49 + 7", "C. 36 + 29", "D. 48 + 6", "D. 48 + 6"));
        list.add(new QuestionModel("Trong một ngày, cửa hàng bán được 56 kg đường, trong đó buổi sáng bán được 27kg. Hỏi buổi chiều bán được bao nhiêu kg đường?", "A. 83 kg", "B. 29 kg", "C. 39 kg", "D. 13 kg", "B. 29 kg"));
        list.add(new QuestionModel("Thứ ba tuần này là ngày 20 tháng 12. Vậy thứ ba tuần sau là ngày mấy?", "A. Ngày 26 tháng 12", "B. Ngày 27 tháng 12", "C. Ngày 28 tháng 12", "D. Ngày 29 tháng 12", "B. Ngày 27 tháng 12"));
        list.add(new QuestionModel("Hiệu của 73 và 37 là:", "A. 36", "B. 46", "C. 35", "D. 47", "A. 36"));
        list.add(new QuestionModel("Chiều dài của cái giường em nằm ước chừng là:", "A. 50 cm", "B. 2 km", "C. 2 m", "D. 10 dm", "C. 2 m"));
        list.add(new QuestionModel("Số liền trước của 80 là:", "A. 79", "B. 80", "C. 81", "D. 82", "A. 79"));
        list.add(new QuestionModel("Số thích hợp điền vào chỗ chấm 60 cm = ... dm là:", "A. 6 dm", "B. 6", "C. 60", "D. 6 cm", "B. 6"));
        list.add(new QuestionModel("Lukaku và Martinez có 22 quả bóng nhựa. Nếu lấy bớt của Lukaku 5 quả thì hai bạn còn lại bao nhiêu quả bóng nhựa? Hai bạn còn lại số quả bóng nhựa là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
    }
    private void setEight() {
        list.add(new QuestionModel("Các số 28; 37; 46; 52 được viết theo thứ tự từ lớn đến bé là:", "A. 46; 37; 52; 28", "B. 28; 37; 46; 52", "C. 52; 46; 37; 28", "D. 52; 37; 46; 28", "C. 52; 46; 37; 28"));
        list.add(new QuestionModel("Số lớn hơn 74 và nhỏ hơn 76 là:", "A. 73", "B. 77", "C. 75", "D. 76", "C. 75"));
        list.add(new QuestionModel("Lan và Hồng có 22 quyển truyện tranh. Nếu lấy bớt của Hồng 5 quyển thì hai bạn còn lại bao nhiêu quyển truyện tranh? Hai bạn còn lại số quyển truyện tranh là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
        list.add(new QuestionModel("Số lớn nhất có 2 chữ số khác nhau là:", "A. 99", "B. 89", "C. 98", "D. 100", "C. 98"));
        list.add(new QuestionModel("92 bằng tổng của hai số nào trong cac cặp số sau đây?", "A. 32 và 50", "B. 55 và 47", "C. 37 và 55", "D. 55 và 47", "C. 37 và 55"));
        list.add(new QuestionModel("Lan gấp được 12 máy bay, Thành gấp được 14 máy bay. Hỏi cả hai bạn gấp được bao nhiêu máy bay?", "A. 16 máy bay", "B.  26 máy bay", "C. 2 máy bay", "D. 36 máy bay", "B.  26 máy bay"));
        list.add(new QuestionModel("Tính nhanh: 1 + 2 + 3 + 17 + 18 + 19", "A. 60", "B. 40", "C. 80", "D. 100", "A. 60"));
        list.add(new QuestionModel("Tìm x: x – 17 = 32 – 8", "A. x = 24", "B. x = 41", "C. x = 31", "D. x = 12", "B. x = 41"));
        list.add(new QuestionModel("Linh học bài từ lúc 8 giờ và đi ngủ lúc 10 giờ. Thời gian Linh học bài là mấy giờ?", "A. 18 giờ", "B. 2 giờ", "C. 9 giờ", "D. 6 giờ", "B. 2 giờ"));
        list.add(new QuestionModel("Hôm nay là thứ 6 ngày 23 tháng 8. Vậy thứ sáu tuần sau là ngày bao nhiêu?", "A.  24 tháng 8", "B.  25 tháng 8", "C. 30 tháng 8", "D.  31 tháng 8", "C. 30 tháng 8"));
    }
    private void setNine() {
        list.add(new QuestionModel("Kết quả của phép cộng 67 + 26 là:", "A. 83", "B. 93", "C. 94", "D. 95", "B. 93"));
        list.add(new QuestionModel("Phép trừ 100 - 57 có kết quả là:", "A. 53", "B. 44", "C. 43", "D. 33", "C. 43"));
        list.add(new QuestionModel("Tổng nào dưới đây bé hơn 56?", "A. 50 + 8", "B. 49 + 7", "C. 36 + 29", "D. 48 + 6", "D. 48 + 6"));
        list.add(new QuestionModel("Trong một ngày, cửa hàng bán được 56 kg đường, trong đó buổi sáng bán được 27kg. Hỏi buổi chiều bán được bao nhiêu kg đường?", "A. 83 kg", "B. 29 kg", "C. 39 kg", "D. 13 kg", "B. 29 kg"));
        list.add(new QuestionModel("Thứ ba tuần này là ngày 20 tháng 12. Vậy thứ ba tuần sau là ngày mấy?", "A. Ngày 26 tháng 12", "B. Ngày 27 tháng 12", "C. Ngày 28 tháng 12", "D. Ngày 29 tháng 12", "B. Ngày 27 tháng 12"));
        list.add(new QuestionModel("Hiệu của 73 và 37 là:", "A. 36", "B. 46", "C. 35", "D. 47", "A. 36"));
        list.add(new QuestionModel("Chiều dài của cái giường em nằm ước chừng là:", "A. 50 cm", "B. 2 km", "C. 2 m", "D. 10 dm", "C. 2 m"));
        list.add(new QuestionModel("Số liền trước của 80 là:", "A. 79", "B. 80", "C. 81", "D. 82", "A. 79"));
        list.add(new QuestionModel("Số thích hợp điền vào chỗ chấm 60 cm = ... dm là:", "A. 6 dm", "B. 6", "C. 60", "D. 6 cm", "B. 6"));
        list.add(new QuestionModel("Lukaku và Martinez có 22 quả bóng nhựa. Nếu lấy bớt của Lukaku 5 quả thì hai bạn còn lại bao nhiêu quả bóng nhựa? Hai bạn còn lại số quả bóng nhựa là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
    }
    private void setTen() {
        list.add(new QuestionModel("Các số 28; 37; 46; 52 được viết theo thứ tự từ lớn đến bé là:", "A. 46; 37; 52; 28", "B. 28; 37; 46; 52", "C. 52; 46; 37; 28", "D. 52; 37; 46; 28", "C. 52; 46; 37; 28"));
        list.add(new QuestionModel("Số lớn hơn 74 và nhỏ hơn 76 là:", "A. 73", "B. 77", "C. 75", "D. 76", "C. 75"));
        list.add(new QuestionModel("Lan và Hồng có 22 quyển truyện tranh. Nếu lấy bớt của Hồng 5 quyển thì hai bạn còn lại bao nhiêu quyển truyện tranh? Hai bạn còn lại số quyển truyện tranh là:", "A. 27", "B. 17", "C. 22", "D. 15", "B. 17"));
        list.add(new QuestionModel("Số lớn nhất có 2 chữ số khác nhau là:", "A. 99", "B. 89", "C. 98", "D. 100", "C. 98"));
        list.add(new QuestionModel("92 bằng tổng của hai số nào trong cac cặp số sau đây?", "A. 32 và 50", "B. 55 và 47", "C. 37 và 55", "D. 55 và 47", "C. 37 và 55"));
        list.add(new QuestionModel("Lan gấp được 12 máy bay, Thành gấp được 14 máy bay. Hỏi cả hai bạn gấp được bao nhiêu máy bay?", "A. 16 máy bay", "B.  26 máy bay", "C. 2 máy bay", "D. 36 máy bay", "B.  26 máy bay"));
        list.add(new QuestionModel("Tính nhanh: 1 + 2 + 3 + 17 + 18 + 19", "A. 60", "B. 40", "C. 80", "D. 100", "A. 60"));
        list.add(new QuestionModel("Tìm x: x – 17 = 32 – 8", "A. x = 24", "B. x = 41", "C. x = 31", "D. x = 12", "B. x = 41"));
        list.add(new QuestionModel("Linh học bài từ lúc 8 giờ và đi ngủ lúc 10 giờ. Thời gian Linh học bài là mấy giờ?", "A. 18 giờ", "B. 2 giờ", "C. 9 giờ", "D. 6 giờ", "B. 2 giờ"));
        list.add(new QuestionModel("Hôm nay là thứ 6 ngày 23 tháng 8. Vậy thứ sáu tuần sau là ngày bao nhiêu?", "A.  24 tháng 8", "B.  25 tháng 8", "C. 30 tháng 8", "D.  31 tháng 8", "C. 30 tháng 8"));
    }


}