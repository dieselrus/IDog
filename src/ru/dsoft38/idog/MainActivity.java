package ru.dsoft38.idog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends SerialPortActivity {

	Button btnUp;
	Button btnDown;
	Button btnLeft;
	Button btnRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnUp = (Button)findViewById(R.id.up);
		btnDown = (Button)findViewById(R.id.down);
		btnLeft = (Button)findViewById(R.id.left);
		btnRight = (Button)findViewById(R.id.right);
		
		createTCPServer();
		
		// устанавливаем один обработчик для всех кнопок
		//btnUp.setOnClickListener();
		//btnDown.setOnClickListener((OnClickListener) this);
		//btnLeft.setOnClickListener((OnClickListener) this);
		//btnRight.setOnClickListener((OnClickListener) this);
		
		//sendData("_data1");
		//sendData("_data2");
		//sendData("_data3");
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
	
	public static void sendData(String _data){
		/*
		int i;
		
		char[] text = new char[_data.length()];
		for (i=0; i<_data.length(); i++) {
			text[i] = _data.charAt(i);
		}
		int b = 110;
		*/
		System.out.println("send data:" + _data);
		try {
			mOutputStream.write(_data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void btnClick(View v){
		
		// выводим сообщение
	    Toast.makeText(this, v.getId(), Toast.LENGTH_SHORT).show();  
	    
	    switch (v.getId()) {
		case R.id.up:
			sendData("up"); //117112
			break;
		case R.id.down:
			sendData("dw");	//100119	
			break;
		case R.id.left:
			sendData("lf"); //108102
			break;
		case R.id.right:
			sendData("rg"); //114103 startActivity(new Intent(MainActivity.this, SerialPortPreferences.class));
			break;
		case R.id.btnSettings:
			startActivity(new Intent(MainActivity.this, SerialPortPreferences.class));
			break;
		default:
			break;
		}
		
	}
	
	public static void createTCPServer(){
		new TCPServerAsync().execute();
		System.out.println("TCPServer create!");
	}
}
