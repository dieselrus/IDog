package ru.dsoft38.idog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.AsyncTask;

public class TCPServerAsync extends AsyncTask<String, Void, String> {

	ServerSocket ss; 	// ������� ����� ������� �
						// ����������� ��� �
						// �������������� �����
	Socket socket;
	
	@Override
	protected String doInBackground(String... params) {
		int port = 2222; // ��������� ���� (����� ���� ����� ����� �� 1025 ��
							// 65535)
		try {
			ss = new ServerSocket(port); // ������� ����� ������� �
														// ����������� ��� �
														// �������������� �����
			System.out.println("Waiting for a client...");

			socket = ss.accept(); // ���������� ������ ����� �����������
											// � ������� ��������� ����� ���-��
											// �������� � ��������
			System.out
					.println("Got a client :) ... Finally, someone saw me through all the cover!");
			System.out.println();

			// ����� ������� � �������� ������ ������, ������ ����� �������� �
			// �������� ������ �������.
			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();

			// ������������ ������ � ������ ���, ���� ����� ������������
			// ��������� ���������.
			DataInputStream in = new DataInputStream(sin);
			DataOutputStream out = new DataOutputStream(sout);

			String line = null;
			while (true) {

				line = in.readUTF(); // ������� ���� ������ ������� ������
										// ������.
				System.out.println("line = " + line);
				
				if( line.equals("up") ){
					MainActivity.sendData("up");
				} if ( line.equals("dw") ) {
					MainActivity.sendData("dw");
				} if  (line.equals("lf") ) {
					MainActivity.sendData("lf");
				}if ( line.equals("rg") ) {
					MainActivity.sendData("rg");
				}
				
				
				System.out.println("The dumb client just sent me this line : "
						+ line);
				System.out.println("I'm sending it back...");
				out.writeUTF(line); // �������� ������� ������� �� ����� ������
									// ������.
				out.flush(); // ���������� ����� ��������� �������� ������.
				System.out.println("Waiting for the next line...");
				System.out.println();
				
				//return line;
			}
		} catch (Exception x) {
			x.printStackTrace();
			System.out.println(x.getMessage());
			
			return x.getMessage();
		}

	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		System.out.println("TCPServer down!");
		MainActivity.createTCPServer();
		
		if (result == "true") {
			// MainActivity.setMessageStatus(true);
		} else {
			// MainActivity.setMessageStatus(false);
		}
	}
}
