package space.firsov.kvantnews;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;

public class QuestionsAnswersDB {

    private static final String DATABASE_NAME = "answers_questions.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "answers";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_ANSWER = "answer";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_QUESTION = 1;
    private static final int NUM_COLUMN_ANSWER = 2;

    private SQLiteDatabase mDataBase;

    public QuestionsAnswersDB(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    void insert(String question, String answer) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION, question);
        cv.put(COLUMN_ANSWER, answer);
        mDataBase.insert(TABLE_NAME, null, cv);
    }

    public void deleteAll() {
        mDataBase.execSQL("DELETE FROM " + TABLE_NAME);
    }


    public ArrayList<Pair<String, String>> selectAll() {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Pair<String, String>> arr = new ArrayList<>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                String question = mCursor.getString(NUM_COLUMN_QUESTION);
                String answer = mCursor.getString(NUM_COLUMN_ANSWER);
                arr.add(new Pair<>(question, answer));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER, " +
                    COLUMN_QUESTION+ " TEXT, " +
                    COLUMN_ANSWER + " TEXT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}