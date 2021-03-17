package FileReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSV {

	public static List<String[]> parseCSV(String file) {

		List<String[]> lines = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(file));
			reader.readNext();
			lines = reader.readAll();
			reader.close();
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static List<String[]> getDateandGender(String file) {

		List<String[]> lines = new ArrayList<String[]>();
		List<String[]> listAll = new ArrayList<String[]>();
		lines = parseCSV(file);

		for (String[] line : lines) {
			String[] dobGender = new String[] { line[2], line[3] };
			listAll.add(dobGender);
		}

		return listAll;
	}

	public static List<String[]> sortList(String file) {
		List<String[]> lines = new ArrayList<String[]>();
		List<String> sortedValues = new ArrayList<String>();
		String joined;
		lines = parseCSV(file);

		for (String[] strings : lines) {
			joined = String.join(",", strings);
			joined.replaceAll("\\s", " ");
			sortedValues.add(joined);
		}
		lines.clear();

		Collections.sort(sortedValues);

		for (String string : sortedValues) {
			String[] splitList = string.split(",");
			lines.add(splitList);
		}
		return lines;
	}

	public static List<char[]> getChars(String file, int index) {

		List<String[]> lines = new ArrayList<String[]>();
		List<char[]> characters = new ArrayList<char[]>();
		lines = sortList(file);
		String[] record = lines.get(index);
		char[] fChars = record[0].toCharArray();
		char[] lChars = record[1].toCharArray();
		characters.add(fChars);
		characters.add(lChars);
//		for (char[] string : characters) {
////			System.out.println(Arrays.toString(string));
//		}
		return characters;
	}

	public static int checkMatch(String fName, String lName, List<char[]> chars) {

		char[] fChars = chars.get(0);
		char[] lChars = chars.get(1);
		int totalChars = fName.length() + lName.length();
		int matches = 0;
		int matchScore;

		for (char c : fChars) {
			for (int i = 0; i < fName.length(); i++) {

				if (i > chars.get(0).length ) {
					continue;
				} else if (c == fName.charAt(i)) {
					matches++;
					fName = fName.replaceFirst(String.valueOf(fName.charAt(i)), " ");
				}
			}
		}

		for (char c : lChars) {
			for (int i = 0; i < lName.length(); i++) {
				if (i > chars.get(1).length) {
					continue;
				} else if (c == lName.charAt(i)) {
					matches++;
					lName = lName.replaceFirst(String.valueOf(lName.charAt(i)), " ");
				}
			}
		}

		matchScore = (matches * 100) / totalChars;
		return matchScore;
	}

	public static List<String[]> findDuplicates(String file, int minScore) {

		List<String[]> lines = new ArrayList<String[]>();
		List<String[]> lines2 = new ArrayList<String[]>();
		List<String[]> uniqueLines = new ArrayList<String[]>();
		List<String[]> Duplicates = new ArrayList<String[]>();
		List<Integer> indexes = new ArrayList<Integer>();
		List<Integer> indexes2 = new ArrayList<Integer>();
		lines = sortList(file);
		lines2 = sortList(file);
		int counter = 0;
		int matchScore = 0;

		for (String[] strings : lines) {

			for (String[] strings2 : lines2) {
				int counter2 = 0;
				if (strings2.equals(lines2.get(0))) {
					continue;
				}

				if ((strings[2].equals(strings2[2])) && (strings[3].equals(strings2[3]))) {
					matchScore = 0;
					matchScore = checkMatch(strings2[0], strings2[1], getChars(file, counter));

					if (matchScore >= minScore && strings2 != lines.get(counter + counter2)) {
						indexes.add(counter + counter2);
					}
				}
			}

			counter++;
			lines2.remove(0);
		}
		for (Integer integer : indexes) {
			if (!indexes2.contains(integer)) {
				indexes2.add(integer);
			}
		}

		for (Integer integer : indexes2) {
			Duplicates.add(lines.get(integer));
		}

		int index = 0;
		for (String[] strings : lines) {
			if (!indexes.contains(index)) {
				uniqueLines.add(strings);
			}
			index++;
		}
//		for (String[] stringss : uniqueLines) {
//			System.out.println(Arrays.toString(stringss));
//		}

		for (String[] stringsss : Duplicates) {
			System.out.println(Arrays.toString(stringsss));
		}
//		System.out.println(uniqueLines.size());
		System.out.println(Duplicates.size());

		return Duplicates;

	}
}
