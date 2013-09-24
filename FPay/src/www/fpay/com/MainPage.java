package www.fpay.com;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainPage extends Activity {

	
	Button BtnSendPhoto;
	Database Dat;
	public static int deneme = 1;
	private static User ThisUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_page_layout);
		try
			{
			if(ThisUser == null)
				ReadUser();
			TextView Welcome_text = new TextView(this);
			Welcome_text =(TextView)findViewById(R.id.Welcome_UserName);
			if(ThisUser !=null)
				Welcome_text.setText("Welcome " + ThisUser.getUserName() + ".. :)");
			}
		finally
			{
			
			}
		
		ButtonListener();
	}
	
	// Kullanici bilgilerini yerel databaseden okuyacak;
	// Eger bulamazsa yeni bir kullanici yaratacak yada olan kullanici bilgilerini internetten cekecek.
	private void ReadUser()
	{
		String[] wanted = {"Id","Password","Number","Email","Name","Bio"};
		Dat = new Database(this);
		// Deneme yapmak icin gerekli, sonra sil..
		if(deneme == 1)
			{
			SQLiteDatabase db = Dat.getWritableDatabase();
			db.execSQL("DROP TABLE `UserInfo`");
			db.execSQL("CREATE TABLE `UserInfo` (Id TEXT,Password TEXT,Number TEXT,Email TEXT,SignUpDate TEXT,Name TEXT,LastLoginDate TEXT,Bio TEXT)");
			Log.w("Fpay", "CREATE TABLE `UserInfo` (Id TEXT,Password TEXT,Number TEXT,Email TEXT,SignUpDate TEXT,Name TEXT,LastLoginDate TEXT,Bio TEXT)");
			deneme++;
			}
		SQLiteDatabase db = Dat.getReadableDatabase();
		Cursor data = db.query("UserInfo",wanted,null,null,null,null,null);
		// bulamadi demektir, yeni kullanici olusturma sayfasina gidiyor.
		if(data.getCount() == 0)
		{
		    Intent CreateNewUserPage= new Intent(MainPage.this, NewUser.class);
		    startActivity(CreateNewUserPage);
		    finish();
		}
		// Kullanici bulundu, Kisisel bilgilerini yerel databaseden cek..
		else
			{
			User getUser = new User(); 
		    if  (data.moveToFirst()) 
		    	{
		        Log.w("MainPage", "Get UserId from Local Database \n");
		        getUser.setUserId(data.getString(data.getColumnIndex("Id")));
		        Log.w("MainPage", "Get UserNumber from Local Database \n");
		        getUser.setUserNumber(data.getString(data.getColumnIndex("Number")));
		        Log.w("MainPage", "Get UserEmail from Local Database \n");
		        getUser.setUserEmail(data.getString(data.getColumnIndex("Email")));
		        Log.w("MainPage", "Get UserName from Local Database \n");
		        getUser.setUserName(data.getString(data.getColumnIndex("Name")));
		        Log.w("MainPage", "Get UserBio from Local Database \n");
		        getUser.setUserBio(data.getString(data.getColumnIndex("Bio")));
		    	}
		    ThisUser = new User(); 
		    ThisUser.setAll(getUser);
			}
	}
	
	public void ButtonListener() {
		 
		BtnSendPhoto = (Button) findViewById(R.id.btn_SendPhoto);
		BtnSendPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    Intent SendNewPhotoPage= new Intent(MainPage.this, SendNewPhoto.class);
			    startActivity(SendNewPhotoPage);
			}
 
		});
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

}
