package main;

import com.markovpetr.command.commands.exceptions.CommandAlreadyExistsException;
import com.markovpetr.command.entity.exceptions.RightException;
import com.markovpetr.command.entity.exceptions.SameIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
	public static int SERVER_PORT = 3292;
	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	public static void main(String[] args) throws SameIdException, InvocationTargetException, IllegalAccessException, InstantiationException, RightException, NoSuchMethodException, CommandAlreadyExistsException {
		Server server = new Server();
		server.launch();
	}

	public Server() {}

	public void launch() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, CommandAlreadyExistsException, SameIdException, RightException {
		DatagramSocket socket = setSocket();

		if (socket != null) {
//			System.out.println("------------------");
//			System.out.println("Сервер запущен");
			logger.info("Сервер запущен");

			Scanner scanner = new Scanner(System.in);
			Interpreter interpreter = new Interpreter();
			Sender sender = new Sender(socket);
			Receiver receiver = new Receiver(socket, interpreter, sender);

			sender.setDaemon(true);
			receiver.setDaemon(true);

			sender.start();
			receiver.start();

			shutDownHook();
			while (true) {
				interpreter.askCommand(scanner);
			}
		}
	}

	public DatagramSocket setSocket() {
		try {
			return new DatagramSocket(SERVER_PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void shutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("Сервер остановлен");
		}));
	}
}
