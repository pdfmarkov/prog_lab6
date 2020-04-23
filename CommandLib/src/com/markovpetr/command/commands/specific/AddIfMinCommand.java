package com.markovpetr.command.commands.specific;

import com.markovpetr.command.commands.Command;
import com.markovpetr.command.commands.Fillable;
import com.markovpetr.command.entity.Person;
import com.markovpetr.command.entity.Persons;

import java.util.Scanner;


public class AddIfMinCommand extends Command implements Fillable {
	public AddIfMinCommand(String name, String description, Class<?>[] argsTypes) {
		super(name, description, argsTypes);
	}

	@Override
	public String execute(Persons persons, Object... args) {
		try {
			validate(args);

			if (persons.isEmpty()) {
				return "Элемент не может быть добавлен, т.к. коллекция пуста. " +
								"Добавьте элементы в коллекцию с помощью обычной команды add";
			} else {
				Person person = (Person) args[0];

				long min = Long.MAX_VALUE;
				for (Person p : persons) {
					if (p.getId() < min) {
						min = p.getId();
					}
				}

				if (min > person.getId()) {
					persons.add(person);
					return "Элемент добавлен, так как его значение " + person.getId() + " меньше чем минимальное " + min;
				} else {
					return "Элемент не добавлен, так как его значение " + person.getId() + " больше или равно минимальному " + min;
				}
			}
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}

//		Scanner scanner = new Scanner(System.in);
//		if (args.length != 0) {
//			System.err.println("В команде " + getName() + " не должно быть параметров");
//		} else if (persons.isEmpty()) {
//			System.err.println("Элемент не может быть добавлен, т.к. коллекция пуста. Добавьте элементы в коллекцию с помощью обычной команды add");
//		} else {
//				Person person = null;
//			try {
//				person = Person.parsePerson(Person.fillPerson(scanner).toJSON());
//			} catch (WrongPersonException e){
//				System.err.println("Не удалось добавить Person");
//			}
//			long min = Long.MAX_VALUE;
//				for (Person p : persons) {
//					if (p.getId() < min) {
//						min = p.getId();
//					}
//				}
//
//				if (min > person.getId()) {
//					persons.add(person);
//					System.out.println("Элемент добавлен, так как его значение " + person.getId() + " меньше чем минимальное " + min);
//				} else {
//					System.out.println("Элемент не добавлен, так как его значение " + person.getId() + " больше или равно минимальному " + min);
//				}
//			}
	}


	@Override
	public Object[] fill(Scanner scanner) {
		Object[] args = new Object[1];
		args[0] = Person.fillPerson(scanner);
		return args;
	}
}

