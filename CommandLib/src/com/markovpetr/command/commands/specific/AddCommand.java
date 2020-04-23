package com.markovpetr.command.commands.specific;

import com.markovpetr.command.commands.Fillable;
import com.markovpetr.command.commands.Command;
import com.markovpetr.command.entity.Person;
import com.markovpetr.command.entity.Persons;

import java.util.Scanner;


public class AddCommand extends Command implements Fillable {

	public AddCommand(String name, String description, Class<?>[] argsTypes) {
		super(name, description, argsTypes);
	}

	@Override
	public String execute(Persons persons, Object... args) {
		try {
			validate(args);
			persons.offer((Person) args[0]);
			return "Элемент добавлен в коллекцию";
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}

//		Scanner scanner = new Scanner(System.in);
//		if (args.length != 0) {
//			System.err.println("В команде " + getName() + " не должно быть параметров");
//		} else {
//			try {
//				persons.offer(Person.parsePerson(Person.fillPerson(scanner).toJSON()));
//			}  catch (WrongPersonException e) {
//				System.err.println("Не удалось добавить Person в коллекцию");
//			}  catch (JsonSyntaxException e) {
//				System.err.println("Ошибка JSON синтаксиса");
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
