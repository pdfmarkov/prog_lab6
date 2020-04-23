package com.markovpetr.command;

import com.markovpetr.command.commands.Command;
import com.markovpetr.command.commands.CommandManager;
import com.markovpetr.command.commands.exceptions.CommandAlreadyExistsException;
import com.markovpetr.command.commands.exceptions.NotFoundCommandException;
import com.markovpetr.command.commands.specific.*;
import com.markovpetr.command.entity.Location;
import com.markovpetr.command.entity.Person;
import com.markovpetr.command.entity.Persons;
import com.markovpetr.command.entity.exceptions.RightException;
import com.markovpetr.command.entity.exceptions.SameIdException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SameIdException, RightException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, CommandAlreadyExistsException {
		Persons persons = new Persons(new File("src\\com\\markovpetr\\command\\resources\\newData.json"));
		persons.parse();

		CommandManager manager = CommandManager.getInstance(persons);
		manager.initCommand(AddCommand.class, "add", "Добавляет элемент в коллекцию", Person.class);
		manager.initCommand(AddIfMinCommand.class, "add_if_min", "Добавляет элемент в коллекцию, если его " +
						"значение меньше, чем у минимального в коллекции", Person.class);
		manager.initCommand(ClearCommand.class, "clear", "Очищает коллекцию");
		manager.initCommand(CountGreaterThanLocationCommand.class, "count_greater_than_location",
						"Выводит количество элементов, значение поля location которых больше заданного", Location.class);
		manager.initCommand(ExecuteScriptCommand.class, "execute_script",
						"Считывает и испольняет скрипт из файла", String.class);
		manager.initCommand(ExitCommand.class, "exit", "Выход, без сохранения");
		manager.initCommand(HelpCommand.class, "help", "Выводит справку по коммандам");
		manager.initCommand(InfoCommand.class, "info", "Выводит информацию о коллекции");
		manager.initCommand(MaxByCoordinatesCommand.class, "max_by_coordinates",
						"Выводи элемент коллекции, у которого значение поля coordinates является максимальным");
		manager.initCommand(MinByIdCommand.class, "min_by_id",
						"Выводит элемент коллекции, индетификатор которого минимален");
		manager.initCommand(RemoveByIdCommand.class, "remove_by_id",
						"Удаляет элемент с заданным id", String.class);
		manager.initCommand(RemoveGreaterCommand.class, "remove_greater",
						"Удаляет из коллекции все элементы, превыщающий заданный", Person.class);
		manager.initCommand(RemoveHeadCommand.class, "remove_head", "Выводит и удаляет первый элемент коллекции");
		manager.initCommand(SaveCommand.class, "save", "Сохраняет информацию в файл");
		manager.initCommand(ShowCommand.class, "show", "Выводит все элементы коллекции");
		manager.initCommand(UpdateCommand.class, "update", "Обновляет значение элемента id",
						String.class, Person.class);


		Scanner scanner = new Scanner(System.in);

//		boolean loop = true;
//		while (loop) {
//			/// your code
//			System.out.print("Продолжить ввод (true/false):");
//			try {
//				loop = scanner.nextBoolean();
//			} catch (InputMismatchException e) {
//				System.out.println("Введите true либо false");
//			}
//		}

		while (true) {
			try {
				String line = scanner.nextLine().trim();
				String name = CommandManager.parseName(line);
				Object[] arg = CommandManager.parseArgs(line);
				Command command = CommandManager.getCommand(name);
				Object[] fillableArg = CommandManager.getFillableArgs(command, scanner);
				arg = CommandManager.concatArgs(arg, fillableArg);
				try {
					CommandManager.validate(command, arg);
					System.out.println("Команда успешна провалидирована");
					System.out.println(CommandManager.execute(command, arg));
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
				}
			} catch (NotFoundCommandException e) {
				System.err.println(e.getMessage());
			}
		}

	}
}
