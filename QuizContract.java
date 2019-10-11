package com.example.sqlquizapplication;

import android.provider.BaseColumns;

// This class for tablename , question column, options and difficulty level into string variables, so that it can be easy to use.

public final class QuizContract {

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME="quiz_questions";
        public static final String COLUMN_QUESTION="question";
        public static final String COLUMN_OPTION1="option1";
        public static final String COLUMN_OPTION2="option2";
        public static final String COLUMN_OPTION3="option3";
        public static final String COLUMN_ANSWER_NR="answer_nr";
        public static final String COLUMN_DIFFICULTY="difficulty";

    }
}
