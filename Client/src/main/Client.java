package main;

import com.markovpetr.command.commands.exceptions.CommandAlreadyExistsException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class Client {
	public static void main(String[] args) {
		Client client = new Client();
		client.launch();
	}

	public void launch() {
		try {
			DatagramChannel channel = DatagramChannel.open();
			SocketAddress address = new InetSocketAddress("localhost", 3292);

			Receiver receiver = new Receiver(channel, address);
			receiver.start();

			Sender sender = new Sender(channel, address);
			CommandReader reader = new CommandReader(sender);

			reader.read();
		} catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | CommandAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
}
