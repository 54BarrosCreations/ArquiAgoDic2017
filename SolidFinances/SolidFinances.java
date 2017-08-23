import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import java.io.File;

public class SolidFinances {
	private static final String SOURCE = "System.in";
	private static final String DATE_FORMAT = "dd/MMM/yyyy";
	private static final String MIN = "Min: ";
	private static final String MAX = "Max: ";
	private static final String AVG = "Avg: ";
	private static final String MODE = "Frequent payee: ";
	private static final String EMPTY = "";
	private static final String REGION = "US";


	static Reader reader;
	static Calculator calc;
	static Writer writer;

	public static void main(String args[]) throws Exception {
		// Instantiate objects.
		reader = new Reader(SOURCE);
		calc = new Calculator();
		writer = new Writer();

		// this.writer.askForInput();
		writer.askForInput(SOURCE);

		// Reader asks for number of stores.
		int input = reader.getInteger();

		// Initialize arrays.
		String stores[] = new String[input];
		float payments[] = new float[input];

		// Reader work.
		reader.populateArrays(input, stores, payments);
		reader.close();

		// Calcualtor work.
		String min = calc.min(payments) + "";
		String max = calc.max(payments) + "";
		String avg = calc.avg(payments) + "";
		String mode = calc.mode(stores) + "";

		// Writer work.
		writer.printDate(DATE_FORMAT);
		writer.println(EMPTY);
		writer.print(MIN);
		writer.printCurrency(min);
		writer.print(MAX);
		writer.printCurrency(max);
		writer.print(AVG);
		writer.printCurrency(avg);
		writer.println(EMPTY);
		writer.println(MODE + mode);
	}
}

class Reader {
	private static Scanner sc;

	public Reader(String source) throws Exception {
		if (source.equals("System.in")) {
			this.sc = new Scanner(System.in);
		} else {
			File file = new File(source);
			try {
				this.sc = new Scanner(file);
			} catch(Exception e) {
				throw new Exception("Exception");
			}
		}
	}

	public static int getInteger() {
		return Integer.parseInt(sc.nextLine());
	}

	public static void populateArrays(int input, String stores[], float payments[]) {
		for (int i = 0; i < input; i++) {
			String ln = sc.nextLine();
			StringTokenizer st = new StringTokenizer(ln);
     	
     	stores[i] = st.nextToken();
     	payments[i] = Float.parseFloat(st.nextToken());
		}
	}

	public void close() {
		this.sc.close();
	}
}

class Writer {
	public Writer() {

	}

	public static void askForInput(String source) {
		if (source.equals("System.in")) {
			System.out.println("How many purchases did you do today?");
		}
	}

	public static void printDate(String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date date = new Date();
		String ds = df.format(date);

		System.out.println("\nPurchases of " + ds);
	}

	public static void printCurrency(String curr) {
		
	}

	public static void print(String str) {
		System.out.print(str);
	}

	public static void println(String str) {
		System.out.println(str);
	}
}

class Calculator {
	public static float min(float payments[]) {
		float ans = payments[0];

		for (int i = 1; i < payments.length; i++) {
			if (payments[i] < ans) {
				ans = payments[i];
			}
		}
		return ans;
	}

	public static float max(float payments[]) {
		float ans = payments[0];

		for (int i = 1; i < payments.length; i++) {
			if (payments[i] > ans) {
				ans = payments[i];
			}
		}
		return ans;
	}

	public static float avg(float payments[]) {
		float ans = 0.0f;

		for (int i = 0; i < payments.length; i++) {
			ans += payments[i];
		}
		ans /= payments.length;
		return ans;
	}

	public static String mode(String stores[]) {
		Map<String, Integer> mode = new HashMap<String, Integer>();
		Integer aux;
		int max = 0;
		String mr = null;

		for (String s: stores) {
			aux = mode.get(s);

			if (aux == null) {
				aux = new Integer(0);
			}
			aux++;
			mode.put(s, aux);

			if (aux > max) {
				max = aux;
				mr = s;
			}
		}
		return mr;
	}
}