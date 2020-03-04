package space.firsov.kvantnews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class NewsDB {

	private static final String DATABASE_NAME = "simple.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "news_table";

	private static final String COLUMN_ID = "id";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_MESSAGE = "message";
	private static final String COLUMN_IMAGE = "image";
	private static final String COLUMN_ADD_INFO = "add_info";

	private static final int NUM_COLUMN_ID = 0;
	private static final int NUM_COLUMN_TITLE = 1;
	private static final int NUM_COLUMN_MESSAGE = 2;
	private static final int NUM_COLUMN_IMAGE = 3;
	private static final int NUM_COLUMN_ADD_INFO = 4;

	private SQLiteDatabase mDataBase;

	NewsDB(Context context) {
		OpenHelper mOpenHelper = new OpenHelper(context);
		mDataBase = mOpenHelper.getWritableDatabase();
	}

	void insert(String title,String message,String image,String addInfo) {
		ContentValues cv=new ContentValues();
		cv.put(COLUMN_TITLE, title);
		cv.put(COLUMN_MESSAGE, message);
		cv.put(COLUMN_IMAGE, image);
		cv.put(COLUMN_ADD_INFO, addInfo);
		mDataBase.insert(TABLE_NAME, null, cv);
	}

	void deleteAll() {
		mDataBase.execSQL("DELETE FROM " + TABLE_NAME);
	}


	ArrayList<News> selectAll() {
		ArrayList<News> arr = new ArrayList<>();
		try (Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null)) {

			arr = new ArrayList<>();
			mCursor.moveToFirst();
			if (!mCursor.isAfterLast()) {
				do {
					String title = mCursor.getString(NUM_COLUMN_TITLE);
					String message = mCursor.getString(NUM_COLUMN_MESSAGE);
					String image = mCursor.getString(NUM_COLUMN_IMAGE);
					String addInfo = mCursor.getString(NUM_COLUMN_ADD_INFO);
					arr.add(new News(title, message, image, addInfo));
				} while (mCursor.moveToNext());
			}
		}catch (Exception e){
			return arr;
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
					COLUMN_TITLE+ " TEXT, " +
					COLUMN_MESSAGE + " TEXT, " +
					COLUMN_IMAGE + " TEXT, " +
					COLUMN_ADD_INFO + " TEXT);";
			db.execSQL(query);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

}