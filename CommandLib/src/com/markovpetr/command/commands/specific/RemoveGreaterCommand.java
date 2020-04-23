package com.markovpetr.command.commands.specific;

import com.markovpetr.command.commands.Command;
import com.markovpetr.command.commands.Fillable;
import com.markovpetr.command.entity.Person;
import com.markovpetr.command.entity.Persons;

import java.util.Iterator;
import java.util.Scanner;


public class RemoveGreaterCommand extends Command implements Fillable {
	public RemoveGreaterCommand(String name, String description, Class<?>[] argsTypes) {
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
				Person person = (Person) args[0];

				Iterator<Person> iter = persons.iterator();
				while (iter.hasNext()) {
					Person p = iter.next();
					if (p.getId() > person.getId()) {
						persons.remove(p);
						iter = persons.iterator();
					}
				}

				return "Были удалены все элементы со значением выше " + person.getId();
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
//			Person person = null;
//			try {
//				person = Person.parsePerson(Person.fillPerson(scanner).toJSON());
//			} catch (WrongPersonException e) {
//				System.err.println("Не удалось добавить Person");
//			}  catch (JsonSyntaxException e) {
//				System.err.println("Ошибка JSON синтаксиса");
//			}
//			Iterator<Person> iter = persons.iterator();
//			while (iter.hasNext()) {
//				Person p = iter.next();
//				if (p.getId() > person.getId()) {
//					persons.remove(p);
//					iter = persons.iterator();
//				}
//			}
//
//			System.out.println("Были удалены все элементы со значением выше " + person.getId());
	}

	@Override
	public Object[] fill(Scanner scanner) {
		Object[] args = new Object[1];
		args[0] = Person.fillPerson(scanner);
		return args;
	}
}
