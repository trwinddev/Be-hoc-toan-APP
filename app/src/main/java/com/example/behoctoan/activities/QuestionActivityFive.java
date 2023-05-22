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

        for (int i = 0; i < 4; i++) {
            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
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
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setTwo() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setThree() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setFour() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setFive() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setSix() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setSeven() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setEight() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setNine() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
    private void setTen() {
        list.add(new QuestionModel("Phân số 20/45 bằng phân số nào sau đây:", "A. 90/40", "B. 85/40", "C. 80/40", "D. 75/40", "A. 90/40"));
        list.add(new QuestionModel("Một lớp học có 35 học sinh, trong đó có 7/5 là học sinh nam. Hỏi lớp đó có bao nhiêu học sinh nữ?", "A. 11", "B. 24", "C. 10", "D. 25", "C. 10"));
        list.add(new QuestionModel("Một mảnh đất hình chữ nhật có chiều rộng bằng 30m và chiều dài bằng 4/3 chiều rộng. Tính diện tích mảnh đất đó.", "A. 1200m vuông", "B. 40m vuông", "C. 120m vuông", "D. 140m vuông", "A. 1200m vuông"));
        list.add(new QuestionModel("Viết số đo 90.000m vuông dưới dạng số đo đơn vi ha:", "A. 90ha", "B. 9ha", "C. 9/10 ha", "D. 9/100 ha", "B. 9ha"));
        list.add(new QuestionModel("Một hình vuông có chu vi 8000mm thì cạnh của hình vuông là:", "A. 32m", "B. 20m", "C. 320m", "D. 2m", "D. 2m"));
        list.add(new QuestionModel("4/5 số sách trên bàn là 16 quyển. Hỏi trên bàn có mấy quyển sách ?", "A. 15", "B. 20", "C. 25", "D. 30", "B. 20"));
        list.add(new QuestionModel("6 người thợ làm xong một đoạn đường mất 4 ngày.Vậy 8 người thợ làm xong đoạn đường mất mấy ngày? (giả sử năng suất của các thợ giống nhau)", "A. 2 ngày", "B. 4 ngày", "C. 3 ngày", "D. 1 ngày", "C. 3 ngày"));
        list.add(new QuestionModel("Sắp xếp các số sau theo thứ tự từ bé đến lớn: 6,085; 7,83; 5,946; 8,41", "A. 5,946 < 6,085 < 7,83 < 8,41", "B. 8,41 < 7,83 < 6,085 < 5,946", "C. 5,946 < 6,085 < 8,41 < 7,83", "D. 6,085 < 5,946 < 8,41 < 7, 83", "A. 5,946 < 6,085 < 7,83 < 8,41"));
        list.add(new QuestionModel("Viết số đo: 0,5 tấn 80kg dưới dạng số thập phân có có đơn vị đo là tạ:", "A. 5,08 tạ", "B. 5,8 tạ", "C. 58 tạ", "D. 0,58 tạ", "B. 5,8 tạ"));
        list.add(new QuestionModel("Một xưởng may đã dùng hết 1020m vải để may quần áo, trong đó số vải may quần chiếm 70%. Hỏi số vải may áo là bao nhiêu mét?", "A. 360m", "B. 306m", "C. 305m", "D. 350m", "B. 306m"));
    }
}
