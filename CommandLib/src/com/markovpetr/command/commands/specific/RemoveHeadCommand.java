package com.markovpetr.command.commands.specific;

import com.markovpetr.command.commands.Command;
import com.markovpetr.command.entity.Person;
import com.markovpetr.command.entity.Persons;


public class RemoveHeadCommand extends Command {
	public RemoveHeadCommand(String name, String description, Class<?>[] argsTypes) {
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
				Person person = persons.poll();
				return "Удален Person: "+person.toString();
			}
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}

//		if (args.length != 0) {
//			System.err.println("В команде " + getName() + " не должно быть параметров");
//		} else if (persons.isEmpty()) {
//			System.err.println("Команда не может быть выполнена, т.к. коллекция пуста. Добавьте элементы в коллекцию с помощью команды add");
//		}else {
//			Person person = persons.poll();
//			System.out.println("Удален Person: "+person.toString());
//		}
	}
}
