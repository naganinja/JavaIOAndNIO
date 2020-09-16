package com.naga.blocking.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 
 * Simple Blocking IO Server
 *
 */
public class EchoBlockingIOServer {

	public static void main(String[] args) throws IOException {

		int portNumber = 4444;
		System.out.println("Waiting on port : " + portNumber + "...");
		boolean listening = true;
		// bind server socket to port
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) { // long running server

				/*
				 * Wait for the client to make a connection and when it does, create a new
				 * socket to handle the request
				 */
				Socket clientSocket = serverSocket.accept();

				// Handle each connection in a new thread to manage concurrent users
				new Thread(() -> {
					try {
						// Get input and output stream from the socket
						PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(
								new InputStreamReader(clientSocket.getInputStream()));

						// Process client request and send back response
						String request, response;
						while ((request = in.readLine()) != null) {
							response = processRequest(request);
							out.println(response);
							if (request.contains("Done")) {
								clientSocket.close();
								break;
							}
						}

					} catch (IOException e) {
						System.err.println("Error occurred while reading the client data on server side causedBy"
								+ e.getMessage() + " handled by server thread " + Thread.currentThread().getName());
					}

				}).start();
			}
		}

	}

	public static String processRequest(String request) {
		System.out.println("Server received client message " + request + " handled by server Thread:"
				+ Thread.currentThread().getName());
		return request;
	}

}
