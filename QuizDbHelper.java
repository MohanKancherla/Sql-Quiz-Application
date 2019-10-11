package com.example.sqlquizapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sqlquizapplication.QuizContract.QuestionsTable;

import java.util.ArrayList;
import java.util.List;

//This is QuizDbHelper which will create the table, insert records into table and retrieve data from table
public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="SQLQuiz.db";
    private static final int DATABASE_VERSION=3;

    private SQLiteDatabase dbase;

//    public QuizDbHelper()
//    {
//        this.dbase= dbase;
//    }

    public QuizDbHelper(Context context) {
        //this.dbase=dbase;
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //this.dbase=dbase;

        this.dbase=sqLiteDatabase;

        final String SQL_CREATE_QUESTIONS_TABLE;

        //Creation of Questions table
        try {
            SQL_CREATE_QUESTIONS_TABLE = " CREATE TABLE " +
                    QuestionsTable.TABLE_NAME + " (" +
                    QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                    QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                    QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                    QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                    QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                    QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                    ")";

            sqLiteDatabase.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        }
        catch (SQLException e)
        {
            Log.i("Info","Error in creation of SQL table creation");
        }

       //
//        if(SQL_CREATE_QUESTIONS_TABLE!=null) {
//            dbase.execSQL(SQL_CREATE_QUESTIONS_TABLE);
//        }

        //Log.i("After table exceution",SQL_CREATE_QUESTIONS_TABLE);
//        dbase.execSQL("CREATE TABLE "+
//                QuestionsTable.TABLE_NAME + " (" +
//                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
//                QuestionsTable.COLUMN_QUESTION +" TEXT, " +
//                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
//                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
//                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
//                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER)");

        fillQuestionTable(sqLiteDatabase);
    }

    //If there is change of database version , it will drop the older version table if exits and create it again.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //This function is for providing questions, options , answer and calling the addquestion function
    private void fillQuestionTable(SQLiteDatabase sqLiteDatabase) {

        // Easy Questions
        Question q1 = new Question("What Does SQL stand for?",
                "Structured Query Language",
                "Strong Query Language",
                "Structured Question Language",
                1, Question.DIFFICULTY_EASY);
        addQuestion(q1, sqLiteDatabase);
        Question q2 = new Question("Which SQL statement is used to extract data from a database?",
                "GET", "EXTRACT", "SELECT",
                3, Question.DIFFICULTY_EASY);
        addQuestion(q2, sqLiteDatabase);
        Question q3 = new Question("Which SQL statement is used to update data in a database?",
                "SAVE", "MODIFY", "UPDATE",
                3, Question.DIFFICULTY_EASY);
        addQuestion(q3, sqLiteDatabase);
        Question q4 = new Question("Which SQL statement is used to delete data from a database?",
                "REMOVE", "DELETE", "COLLAPSE",
                2, Question.DIFFICULTY_EASY);
        addQuestion(q4, sqLiteDatabase);
        Question q5 = new Question("Which SQL statement is used to insert new data in a database?",
                "ADD RECORD", "INSERT NEW", "INSERT INTO",
                3, Question.DIFFICULTY_EASY);
        addQuestion(q5, sqLiteDatabase);
        // Medium Questions
        Question q6 = new Question("With SQL, how do you select a column named \"FirstName\" from a table named \"Persons\"?",
                "EXTRACT FirstName from Persons",
                "SELECT Persons.FirstName",
                "SELECT FirstName from Persons",
                3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q6,sqLiteDatabase);

        Question q7 = new Question("With SQL, how do you select all the columns from a table named \"Persons\"?",
                "SELECT *.Persons",
                "SELECT * from Persons",
                "SELECT Persons",
                2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q7,sqLiteDatabase);

//        Question q8 = new Question("With SQL, how do you select all the records from a table named \"Persons\" where the \"FirstName\" is \"Peter\" and the \"LastName\" is \"Jackson\"?",
//                "SELECT * from Persons WHERE FirstName<>'Peter' AND LastName<>'Jackson'",
//                "SELECT * from Persons WHERE FirstName='Peter' AND LastName='Jackson'",
//                "SELECT FirstName='Peter',LastName='Jackson' From Persons",
//                2, Question.DIFFICULTY_MEDIUM);
//        addQuestion(q8,sqLiteDatabase);

                Question q8 = new Question("Which SQL statement is used to create a table in a database?",
                "CREATE TABLE ",
                "CREATE DATABSE TABLE",
                "CREATE DATABASE TAB",
                1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q8,sqLiteDatabase);

        Question q9 = new Question("Which SQL statement is used to return only different values?",
                "SELECT DISTINCT",
                "SELECT UNIQUE",
                "SELECT DIFFERENT",
                1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q9,sqLiteDatabase);

        Question q10 = new Question("Which SQL keyword is used to sort the result-set?",
                "ORDER",
                "SORT BY",
                "ORDER BY",
                3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q10,sqLiteDatabase);

        //Hard Questions
        Question q11 = new Question("With SQL, how can you insert a new record into the \"Persons\" table?",
                "INSERT INTO Persons VALUES ('Jimmy','Jackson')",
                "INSERT ('Jimmy','Jackson') INTO Persons",
                "INSERT VALUES ('Jimmy','Jackson') INTO Persons",
                1, Question.DIFFICULTY_HARD);
        addQuestion(q11, sqLiteDatabase);
        Question q12 = new Question("Which operator is used to select values within a range?",
                "WITHIN",
                "RANGE",
                "BETWEEN",
                3, Question.DIFFICULTY_HARD);
        addQuestion(q12, sqLiteDatabase);
        Question q13 = new Question("With SQL, how can you return the number of records in the \"Persons\" table?",
                "SELECT LEN(*) FROM Persons",
                "SELCT COUNT(*) FROM Persons",
                "SELECT NO(*) FROM Persons",
                2, Question.DIFFICULTY_HARD);
        addQuestion(q13, sqLiteDatabase);
        Question q14 = new Question("What is the most common type of join?",
                "INSIDE JOIN",
                "INNER JOIN",
                "JOINED TABLE",
                2, Question.DIFFICULTY_HARD);
        addQuestion(q14, sqLiteDatabase);
        Question q15 = new Question("Which operator is used to search for a specified pattern in a column?",
                "LIKE",
                "FROM",
                "GET",
                1, Question.DIFFICULTY_HARD);
        addQuestion(q15, sqLiteDatabase);

    }
    //This function for inserting question into the table
    private void addQuestion(Question question,SQLiteDatabase sqLiteDatabase)
    {
        ContentValues cv= new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION,question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR,question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY,question.getDifficulty());

        //Log.i("Inserting query before",null);
        try {
            sqLiteDatabase.insert(QuestionsTable.TABLE_NAME, null, cv);
        }
        catch (SQLException e)
        {
            Log.i("Info","Error in the insertion of table records");
        }
        //Log.i("Inserting query after",null);
    }

    //Getting the questions into the arraylist
    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();

        try {
            Cursor cu = null;
            dbase = this.getWritableDatabase();
            cu = dbase.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
            if (cu.moveToFirst()) {
                do {

                    Question question = new Question();
                    question.setQuestion(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                    question.setOption1(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                    question.setOption2(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                    question.setOption3(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                    question.setAnswerNr(cu.getInt(cu.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                    question.setDifficulty(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                    questionList.add(question);
                }
                while (cu.moveToNext());
            }

            cu.close();
        }
        catch (SQLException e)
        {
            Log.i("Info","Error in retrieving of questionlist array ");
        }
        return questionList;
    }

    //Getting the questions into the array list as per the difficulty level
    public ArrayList<Question> getQuestions(String difficulty){
        ArrayList<Question> questionList = new ArrayList<>();

        try {
            Cursor cu = null;
            dbase = this.getWritableDatabase();
            String[] selectionArgs = new String[]{difficulty};
            cu = dbase.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                    " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
            if (cu.moveToFirst()) {
                do {

                    Question question = new Question();
                    question.setQuestion(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                    question.setOption1(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                    question.setOption2(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                    question.setOption3(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                    question.setAnswerNr(cu.getInt(cu.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                    question.setDifficulty(cu.getString(cu.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                    questionList.add(question);
                }
                while (cu.moveToNext());
            }

            cu.close();
        }
        catch (SQLException e)
        {
            Log.i("Info","Error in retriveing of questionlist array");
        }
        return questionList;
    }
}
