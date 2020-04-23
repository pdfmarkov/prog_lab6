package com.markovpetr.command.commands.specific;

import com.markovpetr.command.commands.Command;
import com.markovpetr.command.commands.Fillable;
import com.markovpetr.command.entity.Location;
import com.markovpetr.command.entity.Persons;

import java.util.Scanner;


public class CountGreaterThanLocationCommand extends Command implements Fillable {
	public CountGreaterThanLocationCommand(String name, String description, Class<?>[] argsTypes) {
		super(name, description, argsTypes);
	}

	@Override
	public String execute(Persons persons, Object... args) {
		try {
			validate(args);
			if (persons.isEmpty()) {
				return "Команда не может быть выполнена, т.к. коллекция пуста. " +
								"Добавьте элементы в коллекцию с помощью команды add";
			} else {
				// TODO: Сообщаю что этот код не рабочий, он не делает то что нужно
				Location location = (Location) args[0];

				int counter = 0;
				while (persons.peek() != null) {
					persons.remove();
				}

				return "Количество элементов большее заданного: " + counter;
			}
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}

//		Scanner scanner = new Scanner(System.in);
//		if (args.length != 0) {
//			System.err.println("В команде " + getName() + " не должно быть параметров");
//		} else if (persons.isEmpty()) {
//			System.err.println("Команда не может быть выполнена, т.к. коллекция пуста. Добавьте элементы в коллекцию с помощью команды add");
//		}else {
//			Location location = Location.parseLocation(Location.fillLocation(scanner).toJSON());
//			int counter = 0;
//			while (persons.peek()!=null)
//			{
//				System.out.println(persons.remove());
//			}
//
//			System.out.println("Количество элементов большее заданного: " + counter);
//		}
	}

	@Override
	public Object[] fill(Scanner scanner) {
		Object[] args = new Object[1];
		args[0] = Location.fillLocation(scanner);
		return args;
	}
}
