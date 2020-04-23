package main;

import main.answers.Answer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Receiver extends Thread {
	private DatagramChannel channel;
	private SocketAddress serverAddress;

	private ByteBuffer buffer;
	public Receiver(DatagramChannel channel, SocketAddress serverAddress) {
		this.channel = channel;
		this.serverAddress = serverAddress;
		this.buffer = ByteBuffer.allocate(16384);
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				channel.connect(serverAddress);
				channel.receive(buffer);
				buffer.flip();

				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
				ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
				Answer answer = (Answer) objectInputStream.readObject();
				answer.printAnswer();

				objectInputStream.close();
				byteArrayInputStream.close();
				buffer.clear();
				channel.disconnect();
			} catch (PortUnreachableException | IllegalStateException e) {
				System.out.println("Сервер не доступен");
				try {
					channel.disconnect();
				} catch (IOException ex) {
//					System.out.println("Сервер не доступен");
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public synchronized void start() {
		this.setDaemon(true);
		super.start();
	}
}
