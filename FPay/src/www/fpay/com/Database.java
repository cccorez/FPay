package www.fpay.com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database extends SQLiteOpenHelper {

	protected static final String DatabaseName = "FPay";
	protected static final int Version = 1;
	protected static final String UserInfoTable = "UserInfo";
	public Database(Context param1)
	{
		super(param1,DatabaseName,null,Version);
		Log.w("Database","Yeni Database olusturdu");
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.w("Database","Yeni Database olusturdu \n");
		db.execSQL("CREATE TABLE "+ UserInfoTable +" (Id TEXT,Password TEXT,Number TEXT,Email TEXT,SignUpDate TEXT,Name TEXT,LastLoginDate TEXT,Bio TEXT)");
		Log.w("Fpay", "CREATE TABLE "+ UserInfoTable +" (Id TEXT,Password TEXT,Number TEXT,Email TEXT,SignUpDate TEXT,Name TEXT,LastLoginDate TEXT,Bio TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w("Database","Database Guncellendi..");
		db.execSQL("DROP TABLE UserInfo");
		onCreate(db);
		
	}
	
}
