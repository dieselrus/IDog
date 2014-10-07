package ru.dsoft38.idog;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends SerialPortActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sendData("_data1");
		sendData("_data2");
		sendData("_data3");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(MainActivity.this, SerialPortPreferences.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDataReceived(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {
			public void run() {
				//if (mReception != null) {
				//	mReception.append(new String(buffer, 0, size));
				//}
			}
		});
	}
	
	private void sendData(String _data){
		int i;
		
		char[] text = new char[_data.length()];
		for (i=0; i<_data.length(); i++) {
			text[i] = _data.charAt(i);
		}
		int b = 110;
		
		try {
			mOutputStream.write(new String(text).getBytes());
			mOutputStream.write(b);
			mOutputStream.write('\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
