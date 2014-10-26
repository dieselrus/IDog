package ru.dsoft38.idog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.AsyncTask;

public class TCPServerAsync extends AsyncTask<String, Void, String> {

	ServerSocket ss; 	// создаем сокет сервера и
						// привязываем его к
						// вышеуказанному порту
	Socket socket;
	
	@Override
	protected String doInBackground(String... params) {
		int port = 2222; // случайный порт (может быть любое число от 1025 до
							// 65535)
		try {
			ss = new ServerSocket(port); // создаем сокет сервера и
														// привязываем его к
														// вышеуказанному порту
			System.out.println("Waiting for a client...");

			socket = ss.accept(); // заставляем сервер ждать подключений
											// и выводим сообщение когда кто-то
											// связался с сервером
			System.out
					.println("Got a client :) ... Finally, someone saw me through all the cover!");
			System.out.println();

			// Берем входной и выходной потоки сокета, теперь можем получать и
			// отсылать данные клиенту.
			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();

			// Конвертируем потоки в другой тип, чтоб легче обрабатывать
			// текстовые сообщения.
			DataInputStream in = new DataInputStream(sin);
			DataOutputStream out = new DataOutputStream(sout);

			String line = null;
			while (true) {

				line = in.readUTF(); // ожидаем пока клиент пришлет строку
										// текста.
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
				out.writeUTF(line); // отсылаем клиенту обратно ту самую строку
									// текста.
				out.flush(); // заставляем поток закончить передачу данных.
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
