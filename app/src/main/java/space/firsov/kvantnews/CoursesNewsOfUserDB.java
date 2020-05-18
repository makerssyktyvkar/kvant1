package space.firsov.kvantnews;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;


class CoursesNewsOfUserDB {

        private static final String DATABASE_NAME = "course_news.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "course_news";

        private static final String COLUMN_ID = "id";
        private static final String COLUMN_ID_COURSE = "id_course";
        private static final String COLUMN_TITLE = "title";
        private static final String COLUMN_MESSAGE = "message";
        private static final String COLUMN_IMAGE = "image";
        private static final String COLUMN_ADD_INFO = "addInfo";

        private static final int NUM_COLUMN_ID = 0;
        private static final int NUM_COLUMN_TITLE = 1;
        private static final int NUM_COLUMN_MESSAGE = 2;
        private static final int NUM_COLUMN_IMAGE = 3;
        private static final int NUM_COLUMN_ADD_INFO = 4;
        private static final int NUM_COLUMN_ID_COURSE = 5;
        public static int maxId = 0;

        private SQLiteDatabase mDataBase;

        public CoursesNewsOfUserDB(Context context) {
            OpenHelper mOpenHelper = new OpenHelper(context);
            mDataBase = mOpenHelper.getWritableDatabase();
        }

        public long insert(long id_course, String title,String message,String image,String addInfo) {
            ContentValues cv=new ContentValues();
            cv.put(COLUMN_ID_COURSE, id_course);
            cv.put(COLUMN_TITLE, title);
            cv.put(COLUMN_MESSAGE, message);
            cv.put(COLUMN_IMAGE, image);
            cv.put(COLUMN_ADD_INFO, addInfo);
            return mDataBase.insert(TABLE_NAME, null, cv);
        }

        public void deleteAll() {
            mDataBase.delete(TABLE_NAME, null, null);
        }

        public void delete(long id) {
            mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
        }



        public ArrayList<courseNews> selectAll() {
            Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

            ArrayList<courseNews> arr = new ArrayList<>();
            mCursor.moveToFirst();
            if (!mCursor.isAfterLast()) {
                do {
                    long id = mCursor.getLong(NUM_COLUMN_ID);
                    long id_course = mCursor.getLong(NUM_COLUMN_ID_COURSE);
                    String title = mCursor.getString(NUM_COLUMN_TITLE);
                    String message = mCursor.getString(NUM_COLUMN_MESSAGE);
                    String image = mCursor.getString(NUM_COLUMN_IMAGE);
                    String addInfo = mCursor.getString(NUM_COLUMN_ADD_INFO);
                    arr.add(new courseNews(id_course,title,message,image,addInfo));
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
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ID_COURSE+ " INTEGER, " +
                        COLUMN_TITLE+ " TEXT, " +
                        COLUMN_MESSAGE + " TEXT, " +
                        COLUMN_IMAGE + " INT,"+
                        COLUMN_ADD_INFO+" INT);";
                db.execSQL(query);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
            }
        }

    }