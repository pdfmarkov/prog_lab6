package com.markovpetr.command.commands.specific;

import com.markovpetr.command.commands.Command;
import com.markovpetr.command.entity.Persons;
import com.markovpetr.command.entity.exceptions.NotFound;


public class RemoveByIdCommand extends Command {
	public RemoveByIdCommand(String name, String description, Class<?>[] argsTypes) {
		super(name, description, argsTypes);
	}

	@Override
	public String execute(Persons persons, Object... args) {
		try {
			validate(args);
			try {
				long id = Long.parseLong((String) args[0]);
				persons.remove(id);
				return "Запись с индетификатором " + id + " была удалена";
			} catch (NotFound notFound) {
				return "Не существует элемента с таким индетификатором";
			} catch (Exception e) {
				return "Вы ввели данные не верно.";
			}
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}

//		if (args.length != 1) {
//			System.err.println("В команде " + getName() + " должен быть 1 параметр");
//		} else if (persons.isEmpty()) {
//			System.err.println("Команда не может быть выполнена, т.к. коллекция пуста. Добавьте элементы в коллекцию с помощью команды add");
//		}else {
//			try {
//				long id = Long.parseLong(args[0]);
//				persons.remove(id);
//			} catch (NotFound notFound) {
//				System.err.println("Не существует элемента с таким индетификатором");
//			} catch (Exception e) {
//				System.err.println("Вы ввели данные не верно.");
//			}
//		}
	}
}
