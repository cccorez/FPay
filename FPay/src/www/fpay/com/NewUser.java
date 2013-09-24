package www.fpay.com;

import java.util.Calendar;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends Activity {
	
	Database Dat;
	Button BtnSignUp;
	EditText UserName;
	EditText UserNumber;
	EditText UserMail;
	String NewUserId;
	
	
	private static final String SOAP_ACTION = "http://tempuri.org/CelsiusToFahrenheit";
	private static final String METHOD_NAME = "CelsiusToFahrenheit";
	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newuser_page_layout);
		getActionBar().hide();
		
		ButtonListener();
	}
	
	private void ButtonListener()
	{
		BtnSignUp = (Button) findViewById(R.id.btn_SignUp);
		BtnSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			
			    ConnectivityManager connMgr = (ConnectivityManager) 
			            getSystemService(Context.CONNECTIVITY_SERVICE);
			        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			        if (networkInfo != null && networkInfo.isConnected()) {
			            
			        	
			        	Toast.makeText(getApplicationContext()," INTERNET VAR",Toast.LENGTH_SHORT).show(); 
			        	
			        	Toast.makeText(getApplicationContext(),(new DownloadWebpageTask().execute(" ")).toString(),Toast.LENGTH_SHORT).show(); 	
			        	
			        	
			        	
			        	
			        	
			        } else{
			        	Toast.makeText(getApplicationContext()," INTERNET YOK",Toast.LENGTH_SHORT).show(); 
			        }
				
				
				
				UserNumber = (EditText)findViewById(R.id.inp_UserNumber);
				UserMail = (EditText)findViewById(R.id.inp_UserMail);
				UserName = (EditText)findViewById(R.id.inp_UserName);
				if(isValid() == 0)
					{
					if(UserIdGenerator())
						{	
						Calendar c = Calendar.getInstance();
						
					    Log.w("New User Add", "INSERT INTO `" + Database.UserInfoTable +"` (Id,Number,Email,SignUpDate,Name,LastLoginDate) VALUES('" 
								+ NewUserId.toString()  + "','" 
								+ UserNumber.getText().toString() + "','"
								+ UserMail.getText().toString() + "','"
								+ UserName.getText().toString() + "','"
								+ c.getTime()+ "','"
								+ c.getTime()+"')");
						Dat = new Database(NewUser.this);
						SQLiteDatabase db = Dat.getWritableDatabase();
						db.execSQL("INSERT INTO `" + Database.UserInfoTable +"` (Id,Number,Email,SignUpDate,Name,LastLoginDate) VALUES('" 
										+ NewUserId.toString()  + "','" 
										+ UserNumber.getText().toString() + "','"
										+ UserMail.getText().toString() + "','"
										+ c.getTime()+ "','"
										+ UserName.getText().toString() + "','"
										+ c.getTime()+"')");
						
				    	Intent MainPage= new Intent(NewUser.this, MainPage.class);
				    	startActivity(MainPage);
				    	finish();
						}
					}
			}
 
		});
		
	}
	
	
	private boolean UserIdGenerator()
	{
	NewUserId = UserNumber.getText().toString();
	return true;
	}
	
	
	
    private static final Pattern rfc2822 = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
    );
	
	private int isValid()
		{
		String Numb = UserNumber.getText().toString();
		String Mail = UserMail.getText().toString();
		
		if(!isNumber(Numb))
			return 1;
		

	    if (!rfc2822.matcher(Mail).matches()) {
	        return 2;
	    }
		return 0;
		}
	
	public boolean isNumber(String str) {
	    int size = str.length();

	    for (int i = 0; i < size; i++) {
	        if (!Character.isDigit(str.charAt(i))) {
	            return false;
	        }
	    }

	    return size > 0;
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_page, menu);
		return true;
	}

	class DownloadWebpageTask extends AsyncTask<String, Void, String> {
		
	    @Override
	    protected String doInBackground(String... urls) {
	          
	        try {
	        	
	        	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        	request.addProperty("Celsius", "10");

			    SoapSerializationEnvelope envelope =
			        new SoapSerializationEnvelope(SoapEnvelope.VER11);
			    envelope.dotNet = true;
			    envelope.setOutputSoapObject(request);

			    AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);

				try{
			        androidHttpTransport.call(SOAP_ACTION, envelope);

					SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
					
			    return response.toString() + "  Oldu"; 
				}
				catch(Exception ex)
				{
				return ex.getMessage() + " Olmadiii" ;
				}
	        	
	        	
	        } catch (Exception e) {
	        	return "exception";
	        }
	    }
	    // onPostExecute displays the results of the AsyncTask.
	    @Override
	    protected void onPostExecute(String result) {
	    	UserName.setText(result);
	   }
	}

	
	
}

