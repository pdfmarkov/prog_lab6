package com.markovpetr.command.commands.specific;

import com.markovpetr.command.commands.Command;
import com.markovpetr.command.commands.Fillable;
import com.markovpetr.command.entity.Person;
import com.markovpetr.command.entity.Persons;
import com.markovpetr.command.entity.exceptions.NotFound;

import java.util.Scanner;


public class UpdateCommand extends Command implements Fillable {

	public UpdateCommand(String name, String description, Class<?>[] argsTypes) {
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
				try {
					Integer id = Integer.parseInt((String) args[0]);
					int i = 0;
					for (Person p : persons) if (id == (long) p.getId()) i++;
					if (i == 0) return "Не существует элемента с таким индетификатором";
					else {
						persons.update(id, (Person) args[1]);
						return "Элемент обновлен";
					}
				} catch (NotFound notFound) {
					return "Не существует элемента с таким индетификатором";
				} catch (Exception e) {
					return "Вы ввели данные не верно.";
				}
			}
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}

//		Scanner scanner = new Scanner(System.in);
//		System.out.println(Arrays.toString(args));
//		if (args.length != 1) {
//			System.err.println("В команде " + getName() + " должно быть 1 параметр");
//		} else if (persons.isEmpty()) {
//			System.err.println("Команда не может быть выполнена, т.к. коллекция пуста. Добавьте элементы в коллекцию с помощью команды add");
//		} else {
//			try {
//				Integer id = Integer.parseInt(args[0]);
//				int i=0;
//				for (Person p : persons) if (id == (long) p.getId()) i++;
//				if (i==0) System.out.println("Не существует элемента с таким индетификатором");
//				else persons.update(id, Person.parsePerson(Person.fillPerson(scanner).toJSON()));
//			} catch (NotFound notFound) {
//				System.err.println("Не существует элемента с таким индетификатором");
//			} catch (Exception e) {
//				System.err.println("Вы ввели данные не верно.");
//			}
//		}
	}

	@Override
	public Object[] fill(Scanner scanner) {
		Object[] args = new Object[1];
		args[0] = Person.fillPerson(scanner);
		return args;
	}
}
