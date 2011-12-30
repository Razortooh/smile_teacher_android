package com.razortooth.smile.ui;

import java.io.IOException;
import java.util.List;

import net.iharder.Base64;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.razortooth.smile.R;
import com.razortooth.smile.bu.Constants;
import com.razortooth.smile.domain.Question;
import com.razortooth.smile.domain.QuestionList;
import com.razortooth.smile.domain.Student;
import com.razortooth.smile.domain.StudentQuestionDetail;
import com.razortooth.smile.ui.adapter.StudentQuestionDetailAdapter;

public class StudentStatusDetailsActivity extends Activity {

    public static final String PARAM_QUESTIONS = "questions";
    public static final String PARAM_STUDENT = "student";

    private Student student;
    private QuestionList questions;

    private ListView lvListQuestionDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.student_details);

        Bundle b = getIntent().getExtras();
        questions = b.getParcelable(PARAM_QUESTIONS);

        student = (Student) getIntent().getSerializableExtra(PARAM_STUDENT);

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(getString(R.string.scoreboard_of) + " " + student.getName());

        String score = String.valueOf(student.getScore());
        String total = String.valueOf(student.getAnswered());

        TextView tvScore = (TextView) findViewById(R.id.tv_score);
        tvScore.setText(getString(R.string.score) + " " + score + "/" + total);

        List<StudentQuestionDetail> studentQuestionDetails = student.getDetails();
        StudentQuestionDetailAdapter adapter = new StudentQuestionDetailAdapter(this,
            studentQuestionDetails);
        lvListQuestionDetails = (ListView) findViewById(R.id.list);
        lvListQuestionDetails.setAdapter(adapter);
        lvListQuestionDetails.setOnItemClickListener(new OpenItemDetailsListener());
    }

    private class OpenItemDetailsListener implements OnItemClickListener {

        private static final int ALTERNATIVE_1 = 1;
        private static final int ALTERNATIVE_2 = 2;
        private static final int ALTERNATIVE_3 = 3;
        private static final int ALTERNATIVE_4 = 4;

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
            Dialog detailsDialog = new Dialog(StudentStatusDetailsActivity.this, R.style.Dialog);
            detailsDialog.setContentView(R.layout.student_question_details);
            detailsDialog.show();

            StudentQuestionDetail studentQuestionDetail = (StudentQuestionDetail) lvListQuestionDetails
                .getItemAtPosition(position);
            if (!questions.isEmpty()) {
                for (Question question : questions) {
                    if (question.getNumber() == studentQuestionDetail.getNumber()) {
                        loadDetails(detailsDialog, question,
                            studentQuestionDetail.getChosenAnswer(),
                            studentQuestionDetail.getChosenRating());
                    }
                }
            }
        }

        private void loadDetails(Dialog detailsDialog, Question question, int chosenAnswer,
            int chosenRating) {

            ImageView ivAlt1 = (ImageView) detailsDialog.findViewById(R.id.iv_alternative1);
            ImageView ivAlt2 = (ImageView) detailsDialog.findViewById(R.id.iv_alternative2);
            ImageView ivAlt3 = (ImageView) detailsDialog.findViewById(R.id.iv_alternative3);
            ImageView ivAlt4 = (ImageView) detailsDialog.findViewById(R.id.iv_alternative4);

            switch (chosenAnswer) {
                case ALTERNATIVE_1:
                    ivAlt1.setVisibility(View.VISIBLE);
                    break;
                case ALTERNATIVE_2:
                    ivAlt2.setVisibility(View.VISIBLE);
                    break;
                case ALTERNATIVE_3:
                    ivAlt3.setVisibility(View.VISIBLE);
                    break;
                case ALTERNATIVE_4:
                    ivAlt4.setVisibility(View.VISIBLE);
                    break;
            }

            TextView tvQuestionNumber = (TextView) detailsDialog
                .findViewById(R.id.tv_question_number);
            tvQuestionNumber.setText("Question No." + question.getNumber());

            TextView tvOwner = (TextView) detailsDialog.findViewById(R.id.tv_create_by);
            tvOwner.setText("( " + StudentStatusDetailsActivity.this.getString(R.string.create_by)
                + " " + question.getOwner() + " )");

            ImageView tvImage = (ImageView) detailsDialog.findViewById(R.id.iv_image);
            if (question.hasImage()) {
                byte[] imgContent;
                try {
                    imgContent = Base64.decode(question.getImage());
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgContent, 0, imgContent.length);
                    tvImage.setImageBitmap(bmp);
                } catch (IOException e) {
                    Log.e(Constants.LOG_CATEGORY, "Error decode image: ", e);
                }
            } else {
                tvImage.setVisibility(View.GONE);
            }

            TextView tvQuestion = (TextView) detailsDialog.findViewById(R.id.tv_question);
            if (!question.getQuestion().equals("")) {
                tvQuestion.setText("Question: " + question.getQuestion());
            } else {
                tvQuestion.setVisibility(View.GONE);
            }

            TextView tvAlternative1 = (TextView) detailsDialog.findViewById(R.id.tv_alternative1);
            if (!question.getOption1().equals("")) {
                tvAlternative1.setText(StudentStatusDetailsActivity.this
                    .getString(R.string.alternative1) + " " + question.getOption1());
            } else {
                tvAlternative1.setVisibility(View.GONE);
            }

            TextView tvAlternative2 = (TextView) detailsDialog.findViewById(R.id.tv_alternative2);
            if (!question.getOption1().equals("")) {
                tvAlternative2.setText(StudentStatusDetailsActivity.this
                    .getString(R.string.alternative2) + " " + question.getOption2());
            } else {
                tvAlternative2.setVisibility(View.GONE);
            }

            TextView tvAlternative3 = (TextView) detailsDialog.findViewById(R.id.tv_alternative3);
            if (!question.getOption1().equals("")) {
                tvAlternative3.setText(StudentStatusDetailsActivity.this
                    .getString(R.string.alternative3) + " " + question.getOption3());
            } else {
                tvAlternative3.setVisibility(View.GONE);
            }

            TextView tvAlternative4 = (TextView) detailsDialog.findViewById(R.id.tv_alternative4);
            if (!question.getOption1().equals("")) {
                tvAlternative4.setText(StudentStatusDetailsActivity.this
                    .getString(R.string.alternative4) + " " + question.getOption4());
            } else {
                tvAlternative4.setVisibility(View.GONE);
            }

            TextView tvCorrectAnswer = (TextView) detailsDialog
                .findViewById(R.id.tv_correct_answer);
            tvCorrectAnswer.setText(StudentStatusDetailsActivity.this
                .getString(R.string.correct_answer) + ": " + question.getAnswer());

            TextView tvAverageRating = (TextView) detailsDialog
                .findViewById(R.id.tv_average_rating);
            tvAverageRating.setText("Average rating: " + chosenRating);

            final RatingBar rbRatingBar = (RatingBar) detailsDialog
                .findViewById(R.id.rb_ratingbar);
            rbRatingBar.setRating(chosenRating);
        }
    }
}
