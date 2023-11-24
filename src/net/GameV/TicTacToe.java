package net.GameV;

import java.util.Scanner;

public class TicTacToe {
	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// Настройки
	public static final int WIDTH = 4; // min 3, max 26
	public static final int HEIGHT = 4; // min 3, max 9
	public static final int WIN_LINE_LENGTH = 3; // min 3, max 9
	public static final String[] PLAYERS = { "X", "O" };

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Ограничения ширины и высоты игрового поля. Создание игрогого поля
		int width = Math.max(3, Math.min(WIDTH, 26));
		int height = Math.max(3, Math.min(HEIGHT, 9));
		String[][] map = new String[height][width];

		int playerIndex = 0;
		while (true) {
			printMap(map);

			// Определение текущего игрока
			String player = PLAYERS[playerIndex % PLAYERS.length];

			// Получаем ход от пользователя
			int[] m;
			while (true) {
				System.out.print("\nХодит (" + player + "): ");
				String move = scanner.nextLine();
				m = parsMove(move.toUpperCase());
				if (m != null) {
					break;
				}
			}
			int i = m[0];
			int j = m[1];

			// Проверка, занято ли выбранное место
			if (map[i][j] != null) {
				System.out.println("Место занято!");
				continue;
			}

			// Установка символа игрока на игровое поле
			map[i][j] = player;

			// Проверка на выигрыш текущего игрока
			if (isWin(map, player, i, j)) {
				printMap(map);
				System.out.println(player + " победил!");
				break;
			}

			// Переход к следующему игроку
			playerIndex++;
		}

		scanner.close();
	}

	private static int[] parsMove(String move) {
		int[] out = new int[2];

		int width = Math.max(3, Math.min(WIDTH, 26));
		int height = Math.max(3, Math.min(HEIGHT, 9));

		if (move.length() < 2 || move.length() > 3) {
			System.out.println("Неверный формат ввода. Введите координаты в формате 'A1'.");
			return null;
		}

		char colChar = move.charAt(0);
		char rowChar = move.charAt(1);

		// Проверяем, является ли второй символ допустимой цифрой столбца
		if (LETTERS.indexOf(colChar) == -1) {
			System.out.println(
			        "Неверный символ для столбца. Используйте буквы от A до " + LETTERS.charAt(WIDTH - 1) + ".");
			return null;
		}

		// Получаем индекс символа в столбце
		int colIndex = LETTERS.indexOf(colChar);

		// Проверяем, является ли первый символ допустимым символом строки
		if (!Character.isDigit(rowChar)) {
			System.out.println("Неверный символ для строки. Используйте цифры от 1 до " + height + ".");
			return null;
		}

		// Преобразовываем символ столбца в индекс
		int rowIndex = Character.getNumericValue(rowChar) - 1;

		// Проверяем, находятся ли индексы в пределах границ
		if (rowIndex < 0 || rowIndex >= height || colIndex < 0 || colIndex >= width) {
			System.out.println("Координаты выходят за границы игрового поля.");
			return null;
		}

		out[0] = rowIndex;
		out[1] = colIndex;

		return out;
	}

	private static boolean isWin(String[][] map, String player, int iIn, int jIn) {
		int winLineLength = Math.max(3, Math.min(WIN_LINE_LENGTH, 9));
		int height = map.length;
		int width = map[0].length;

		// Проверка по вертикали
		boolean flag = true;
		for (int i = 0; i < Math.min(height, winLineLength); i++) {
			flag &= map[i][jIn] == player;
		}
		if (flag) return true;

		// Проверка по горизонтали
		flag = true;
		for (int j = 0; j < Math.min(width, winLineLength); j++) {
			flag &= map[iIn][j] == player;
		}
		if (flag) return true;

		// Проверка по диагонали справа налево
		flag = true;
		for (int d = 0; d < Math.min((width + height) / 2, winLineLength); d++) {
			flag &= map[d][d] == player;
		}
		if (flag) return true;

		// Проверка по диагонали слева направо
		flag = true;
		int startD = Math.min((width + height) / 2, winLineLength) - 1;
		for (int d = startD; d >= 0; d--) {
			int d1 = startD - d;
			flag &= map[d1][d] == player;
		}
		if (flag) return true;

		return false;
	}

	private static void printMap(String[][] map) {
		int height = map.length;
		int width = map[0].length;

		System.out.println();

		// Буквы с верху
		System.out.print("   ");
		for (int i = 0; i < width; i++) {
			System.out.print(String.format(" %-3s", LETTERS.charAt(i % LETTERS.length())));
		}
		System.out.println();

		// Середина сетки
		System.out.print("  ┌─");
		for (int i = 0; i < width - 1; i++) {
			System.out.print("──┬─");
		}
		System.out.println("──┐");

		// Центр сетки
		for (int i = 0; i < height; i++) {

			// Цифры слева
			System.out.print((i + 1) + " │ ");

			for (int j = 0; j < width; j++) {
				String v = map[i][j];
				System.out.print(v == null ? " " : v);
				if (j < width - 1) {
					System.out.print(" │ ");
				}

			}
			System.out.print(" │");

			if (i < height - 1) {
				System.out.println();
				System.out.print("  ├─");
				for (int j = 0; j < width - 1; j++) {
					System.out.print("──┼─");
				}
				System.out.print("──┤");
				System.out.println();
			}
		}
		System.out.println();

		// Нижняя часть сетки
		System.out.print("  └─");
		for (int i = 0; i < width - 1; i++) {
			System.out.print("──┴─");
		}
		System.out.println("──┘");

	}

}
