package net.ukr.geka3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.stream.FileImageInputStream;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<String, String> dictionary = getDictionary();
		dictionary.put("application", "Приложение");
		dictionary.put("process", "процесс");
		dictionary.put("step", "шаг");

		for (;;) {
			switch (getMenuRes()) {
			case 1:
				System.out.println(translate(getText("text.txt"), dictionary));
				break;
			case 2:
				addWords(dictionary);
				break;
			case 3:
				return;

			}
		}

	}

	private static void addWords(HashMap<String, String> dictionary) {

		Scanner sc = new Scanner(System.in);

		System.out
				.println("input word and tranlation over space or \"exit\" to end");

		String temp = "";
		for (; !(temp = sc.nextLine()).equals("exit");) {
			String[] arrayTemp = temp.split(" ");
			if (arrayTemp.length == 2) {
				dictionary.put(arrayTemp[0], arrayTemp[1]);

			} else {
				System.out.println("input right values as window окно");
			}
		}

		saveDictionary(dictionary);

	}

	public static HashMap<String, String> getDictionary() {
		HashMap<String, String> diction = new HashMap<String, String>();
		try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(
				"diction.txt"))) {

			diction = (HashMap<String, String>) OIS.readObject();
			// System.out.println("i was here");
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return diction;
	}

	public static void saveDictionary(HashMap<String, String> dictionary) {
		try (ObjectOutputStream OOS = new ObjectOutputStream(
				new FileOutputStream("diction.txt"))) {
			OOS.writeObject(dictionary);
			// System.out.println("i was here");
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private static int getMenuRes() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("1 to translate " + System.lineSeparator()
					+ " 2 to add to dictionary" + System.lineSeparator()
					+ " 3 to exit");
			String text;
			for (;;) {
				text = sc.nextLine();
				switch (text) {
				case "1":
					return 1;

				case "2":
					return 2;

				case "3":
					return 3;

				default:

					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String translate(String text, HashMap<String, String> diction) {
		String[] textArray = text.split(" ");
		StringBuilder newText = new StringBuilder();
		for (String temp : textArray) {

			newText.append(diction.getOrDefault(temp.toLowerCase(), temp) + " ");
		}
		saveText(newText.toString(), "Ukraine.out");
		return newText.toString();

	}
	
	public static void saveText(String text, String fileAdress){
		File file = new File(fileAdress);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (file.isDirectory()){
			System.out.println("there is directory");
			return;
		}
		
		try (FileWriter fw = new FileWriter(file)){
			
			fw.write(text);
			fw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getText(String fileAdress) {

		File file = new File(fileAdress);
		if (!file.exists() && file.isDirectory()) {
			return null;
		}
		HashMap<Integer, String> text = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String temp = null;
			StringBuilder sb = new StringBuilder();
			for (; (temp = br.readLine()) != null;) {
				sb.append(temp + " ");
			}
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
