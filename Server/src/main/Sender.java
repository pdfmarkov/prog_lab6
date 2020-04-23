package main;

import main.answers.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
	private DatagramSocket socket;

	public Sender(DatagramSocket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		while (!isInterrupted());
	}

	public void send(Answer answer, InetAddress address, int port) {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		     ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
			objectOutputStream.writeObject(answer);
			objectOutputStream.flush();

			byte[] bytes = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.flush();

			DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, address, port);
			socket.send(datagramPacket);
			logger.info("Отправлен ответ на " + datagramPacket.getAddress() + ":" + datagramPacket.getPort());
			answer.logAnswer();
		} catch (IOException e) {
			logger.error("Не получилось отправить ответ");
			e.printStackTrace();
		}
	}
}
