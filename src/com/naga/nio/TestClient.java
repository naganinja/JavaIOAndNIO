package com.naga.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 
 * Test client for NIO server
 *
 */
public class TestClient {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		int counter = 0;
		while (currentTime < startTime + 3000) {
			// Do something here
			SocketChannel client = startClient(++counter);
			currentTime = System.currentTimeMillis();
		}

		System.out.println(" \n Total no of requests serviced in " + (3000/1000) + " secs are :" + counter);

	}

	public static SocketChannel startClient(int counter) throws IOException, InterruptedException {

		InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5555);
		SocketChannel client = SocketChannel.open(hostAddress);

		// System.out.println("Client... started");

		String message = String.format(" [NIO Socket client %s - Msg : Done] ", counter);

		sendMessage(client, message);

		return client;
	}

	private static void sendMessage(SocketChannel client, String message) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
		System.out.println("Sending client msg =" + message);
		client.write(buffer);
		buffer.clear();
		client.read(buffer);
		String response = new String(buffer.array()).trim();
        System.out.println("Got Echo response=" + response);
        buffer.clear();
	}

	

}
