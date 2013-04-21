package spaceapps.etchickens;





import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class SpaceChickenDB extends Activity{
	//About the Chicken
	public static final String CHICKEN_ID = "chicken_id"; 
	public static final String CHICKEN_NAME = "chicken_name";
	public static final String EGG_NUMBER = "egg_numbers";
	public static final String CHICKEN_AGE = "chicken_age";
	public static final String CHICKEN_GENDER = "chicken_gender";
	public static final String CHICKEN_PICTURE = "picture_location";
	
	
	//About the Guide Info
	
	public static final String INFO_ID = "id_info";
	public static final String PAGE_INFO = "page_info";
	public static final String PAGE_CONTENT = "page_content";
	public static final String PAGE_NAME = "page_name";
	
	private static final String GUIDE_TABLE = "guide_info";
	private static final String DATABASE_NAME = "database_name";
	private static final String CHICK_TABLE = "chick_tab";
	private static final int DATABASE_VERSION = 1;
	
	private static String CREATE_GUIDE_TABLE = "CREATE TABLE " + GUIDE_TABLE + " (" +
			
			//Make the ID of each row auto increment. We need a way to clear and reset this number once a row is done.
			INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			
			PAGE_INFO + " TEXT, " +
			
			PAGE_CONTENT + "TEXT NOT NULL);";
	private static String CREATE_CHICKEN_TABLE = "CREATE TABLE " + CHICK_TABLE + " (" +
			
			//Make the ID of each row auto increment. We need a way to clear and reset this number once a row is done.
			CHICKEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			
			/**
			 * All Chicken should have a name.
			 */
			CHICKEN_NAME + " TEXT NOT NULL, " +
			
			/**
			 * All Chicken Should have a specified number of eggs. Even If that number is zero.
			 */
			EGG_NUMBER + "INTEGER NOT NULL, " + 
			
			CHICKEN_AGE + "INTEGER NOT NULL, " +
			
			CHICKEN_GENDER + "TEXT NOT NULL);"
			
			;
	
		private DbHelper dHelp;
		private final Context appContext;
		private SQLiteDatabase ourDatabase;
	//creates the basic sqlite database.
	//Everything else beyond this is naught. 
		private static class DbHelper extends SQLiteOpenHelper{

			public DbHelper(Context context) {
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
				
			}
			
			/**
			 * @param db
			 * This creates the database for the application.
			 * I will add 
			 */
			@Override
			public void onCreate(SQLiteDatabase db) {
				// TODO Auto-generated method stub
				db.execSQL(CREATE_GUIDE_TABLE); //CREATE A BASIC GUIDE TABLE
				db.execSQL(CREATE_CHICKEN_TABLE); //CREATE A BASIC CHICKEN TABLE
				
			}

			
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				db.execSQL("DROP TABLE IF EXIST " + GUIDE_TABLE);
				db.execSQL("DROP TABLE IF EXIST " + CHICK_TABLE);
				onCreate(db);
				
			}
			
		}
		
		public SpaceChickenDB(Context c){
			appContext = c;
		}
		
		
		/***
		 * This grabs and opens the database you have
		 * This returns an exception if there is no database to open.
		 * @return
		 * @throws SQLException
		 */
		public SpaceChickenDB open() throws SQLException{
			dHelp = new DbHelper(appContext);
			ourDatabase = dHelp.getWritableDatabase();
			return this;
		}
		
		
		public void close(){
			dHelp.close();
		}
		
		public long createEntryChicken(String name, int eggNum, int age, String gender, String picLocal ) throws SQLException{
			ContentValues cv = new ContentValues();
			cv.put(CHICKEN_NAME, name);
			cv.put(EGG_NUMBER, eggNum);
			cv.put(CHICKEN_AGE, age);
			cv.put(CHICKEN_GENDER, gender);
			
			return ourDatabase.insert(CHICK_TABLE, null, cv);
			
			
		}
		public long createEntryGuide(String name, String pageInfo, String pageContent ) throws SQLException{
			ContentValues cv = new ContentValues();
			cv.put(PAGE_NAME, name);
			cv.put(PAGE_INFO, pageInfo);
			cv.put(PAGE_CONTENT, pageContent);
			return ourDatabase.insert(CHICK_TABLE, null, cv);
			
			
		}
		
		
		/***
		 * Gets the data for the table and adds the information to result string. This allows the data to be displayed for anything that is trying to access it.
		 * @return
		 * @throws SQLException
		 */
		public String getData() throws SQLException{
			String[] columns = new String[]{NODE_ID, NODE_NAME};
			Cursor c = ourDatabase.query( DATABASE_TABLE, columns, null, null, null, null, null);
			String result = "";
			
			int iRow = c.getColumnIndex(NODE_ID);
			int iName = c.getColumnIndex(NODE_NAME);
			int iLat = c.getColumnIndex(NODE_LATITUDE);
			int iLong = c.getColumnIndex(NODE_LONGITUDE);
			
			
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				result = result + c.getString(iRow) + "  " + c.getString(iName) + "\n";
			}
			
			return result;
		}
	
}
