package com.markovpetr.command.entity;

import com.google.gson.JsonSyntaxException;
import com.markovpetr.command.entity.exceptions.NotFound;
import com.markovpetr.command.entity.exceptions.RightException;
import com.markovpetr.command.entity.exceptions.SameIdException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Persons extends PriorityQueue<Person>{
	private LocalDateTime initDate;
	private File file;

	public Persons(File file) {
		this.file = file;
		initDate = LocalDateTime.now();
	}

	/**
	 * Обновляет элемент по его индетификатору на элемент указанный в параметрах
	 *
	 * @param id индетификатор элемента
	 * @param person Элемент на который нужно изменить
	 * @throws NotFound В случае не существования элемента с таким индетификатором
	 */
	public void update(Integer id, Person person) throws NotFound {
		Person[] array = this.toArray(new Person[0]);
		List<Person> list = new ArrayList<>(Arrays.asList(array));

		for (Person p : list) {
			if ( (long) p.getId() == id) {
				person.setId(id);
				list.set(list.indexOf(p), person);
				this.clear();
				this.addAll(list);
				return;
			}
		}

		throw new NotFound();
	}

	/**
	 * Удаляет элемент коллекции по индетификатору
	 *
	 * @param id индетификатор элемента
	 * @throws NotFound В случае не существования элемента с таким индетификатором
	 */
	public void remove(long id) throws NotFound {
		Person[] array = this.toArray(new Person[0]);
		List<Person> list = new ArrayList<>(Arrays.asList(array));

		for (Person p : list) {
			if (p.getId() == id) {
				list.remove(p);
				this.clear();
				this.addAll(list);
				return;
			}
		}

		throw new NotFound();
	}

	/**
	 * Сохранает данные элементов в файл
	 */
	public void save() {
		try {
			if (file.isFile() && file.exists() && file.canRead() && file.canWrite()) {
				FileWriter writer = new FileWriter(file, false);
				writer.write("[\n");
				int i = 0;
				for (Person person : this) {
					i++;
					if (this.size() - i >= 1) writer.write(person.toJSON() + ",\n");
					else writer.write(person.toJSON() + "\n");
				}
				writer.write("]\n");
				writer.flush();
				writer.close();
				System.out.println("Иноформация сохранена в файл");
			 } else System.err.println("Не удалось сохранить файл");
		} catch (IOException e) {
			System.err.println("Произошла ошибка при записи в файл");
		}
	}

	/**
	 * Считывает данные элементов с файла
	 *
	 * @throws  FileNotFoundException если файл не удалось найти
	 * @throws RightException если с правами файла неполадки
	 * @throws  JsonSyntaxException если имеются ошибки в синтаксисе JSON файла
	 */
	public void parse() throws RightException, SameIdException {

		try {

			if (!file.canRead() && !file.canWrite() && file.exists()) throw new RightException("Возникла ошибка с правами файла");
			if (!file.canRead() && file.exists()) throw new RightException("Файл не удалось прочитать");
			if (!file.canWrite() && file.exists()) throw new RightException("В файл не получиться что-либо записать");
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line;

			while((line = reader.readLine()) != null) {
				if (!line.equals("[") && !line.equals("]") && !line.equals("")) {
					if (line.substring(line.length() - 1).equals(",")) {
						this.offer(Person.parsePerson(line.substring(0,line.length()-1)));
					} else this.offer(Person.parsePerson(line));
				}
			}
			fileReader.close();
			reader.close();

		} catch (FileNotFoundException e) {
			System.err.println("Файл не найден");
			System.exit(0);
		} catch (IOException e) {
			System.err.println("C чтением файла возникли неполадки");
			e.printStackTrace();
			System.exit(0);
		} catch (JsonSyntaxException e) {
			System.err.println("Файл содержит ошибки синтаксиса JSON");
			System.exit(0);
		}
		HashSet<Long> idList = new HashSet<Long>();
		for (Person p : this) { ;
			if (!idList.isEmpty()) {if (idList.contains(p.getId())) {throw new SameIdException();}}
			idList.add(p.getId());
		}
	}

	@Override
	public String toString() {
		return  "Тип коллекции: PriorityQueue\n" +
						"Дата инициализации: " + initDate.toString() + "\n" +
						"Количество элементов: " + this.size();
	}
}