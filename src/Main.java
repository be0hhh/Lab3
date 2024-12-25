import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Bukvar bukvar = new Bukvar("TestUser");

        // Ввод букв и их количества
        System.out.println("==== Ввод букв ====");
        while (true) {
            System.out.print("Введите букву (или 'stop' для завершения ввода): ");
            String input = in.nextLine();
            if (input.equalsIgnoreCase("stop")) {
                break;
            }
            if(input.length() != 1){
                System.out.println("Введите только один символ!");
                continue;
            }
            char letter = input.charAt(0);
            System.out.print("Введите количество: ");
            int count = in.nextInt();
            in.nextLine(); // Consume newline
            bukvar.addLetter(letter, count);
            System.out.println("Буква '" + letter + "' добавлена в количестве " + count);
        }

        System.out.println("\n--- Вывод букв (формат 1) ---");
        System.out.println(bukvar.toString(1));

        System.out.println("\n--- Вывод букв (формат 2) ---");
        System.out.println(bukvar.toString(2));

        // Ввод буквы для получения количества
        System.out.print("\nВведите букву для получения количества: ");
        char letterToGetCount = in.nextLine().charAt(0);
        System.out.println("Количество буквы '" + letterToGetCount + "': " + bukvar.getCount(letterToGetCount));

        System.out.println("\n--- Получение общего количества букв ---");
        System.out.println("Общее количество: " + bukvar.getTotalCount());

        // Ввод слова для сборки (с удалением)
        System.out.print("\nВведите слово для сборки (с удалением): ");
        String wordToBuild = in.nextLine();
        System.out.println("Собранное слово: " + bukvar.buildWord(wordToBuild));
        System.out.println("Текущие буквы после сборки: " + bukvar.getLetters());

        // Ввод слова для сборки (без удаления)
        System.out.print("\nВведите слово для сборки (без удаления): ");
        String wordToBuildWithoutChanges = in.nextLine();
        System.out.println("Собранное слово: " + bukvar.buildWordWithoutChanges(wordToBuildWithoutChanges));
        System.out.println("Текущие буквы после сборки без изменений: " + bukvar.getLetters());

        // Ввод слова для проверки возможности составить
        System.out.print("\nВведите слово для проверки: ");
        String wordToCheck = in.nextLine();
        System.out.println("Можно составить '" + wordToCheck + "': " + bukvar.canBuildWord(wordToCheck));
        System.out.println("Текущие буквы после проверки: " + bukvar.getLetters());

        // Ввод слова для чтения (с удалением)
        System.out.print("\nВведите слово для чтения (с удалением): ");
        String wordToRead = in.nextLine();
        System.out.println("Результат чтения: " + bukvar.readWord(wordToRead));
        System.out.println("Текущие буквы после чтения: " + bukvar.getLetters());
        // Ввод слова для подсчета количества возможных слов
        System.out.print("\nВведите слово для подсчета количества возможных слов: ");
        String wordToCount = in.nextLine();
        System.out.println("Можно составить '" + wordToCount + "' раз: " + bukvar.countPossibleWords(wordToCount));
        System.out.println("Текущие буквы после подсчета: " + bukvar.getLetters());
        in.close();
    }
}