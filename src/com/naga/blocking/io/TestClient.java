package com.naga.blocking.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * Test client for Blocking IO server
 *
 */
public class TestClient {
	
	public static AtomicInteger counter = new AtomicInteger();
	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		
		while (currentTime < startTime + 3000) {
			// Do something here
			//Socket client = startClient(counter);
			//new Thread(() -> startClient(counter.incrementAndGet())).start();
			startClient(counter.incrementAndGet());
			currentTime = System.currentTimeMillis();
		}
		
		System.out.println(" \n Total no of requests serviced in " + (3000/1000) + " secs are :" + counter.get());

	}

	public static void startClient(int counter)  {

		String hostName = "localhost";
		int portNumber = 4444;
		String message = String.format(" [Socket client %s Msg: Done] ", counter);
		Socket echoSocket;
		try {
			echoSocket = new Socket(hostName, portNumber);
			sendMesage(echoSocket, message);
			
			//sendMesage(echoSocket, String.format("[Socket client msg - %s Done]", counter));
			
		} catch (UnknownHostException e) {
			System.err.println("Unknown host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName + ".." + e.toString());
			System.exit(1);
		}
	}

	private static void sendMesage(Socket socket,String message) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedReader stdIn = new BufferedReader(new StringReader(message));
		String userInput;
		while ((userInput = stdIn.readLine()) != null) {
			System.out.println("Sending client msg =" + message);
			out.println(userInput); // write to server
			System.out.println(" Relieved echo: " + in.readLine()); // Wait for the server to
															// echo back
		}
	}

}
