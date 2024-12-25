public class Bukvar {
    private String userName; // имя пользователя
    private char[] letters; // буквы
    private int[] counts; // счетчики
    private int size; // Текущее количество элементов в массивах
    public Bukvar(String userName) {
        this.userName = userName;
        this.letters = new char[10]; // Начальный размер массива
        this.counts = new int[10];
        this.size = 0;
    }

    public String getUserName() {
        return userName;
    }
    // Метод для увеличения массива
    private void resizeArrays() {
        int newSize = letters.length * 2;
        // Тут подробно стоит обсудить увеличение массива в два раза.
        // Почему бы просто не добавить +1. Тогда при добавлении букв массив каждый раз
        // перезаписывался, чрезмерно используется память
        // А если мы один раз увеличим в два раза, то в целом это не так сильно ударит по использованию памяти,
        // так как это увеличение не такое большое и перезаписывать каждый раз массив не надо
        // например было 11 букв, я добавил еще 9. Это целых 9 раз массив перезапишется, а при
        // увеличении длины в 2 раза, из 11 станет 22, длина изменится 1 раз и хватит длины для всех букв.
        char[] newLetters = new char[newSize];
        int[] newCounts = new int[newSize];
        // Копирование элементов
        for (int i = 0; i < letters.length; i++) {
            newLetters[i] = letters[i];
            newCounts[i] = counts[i];
        }
        letters = newLetters; // чтобы не писать везде new
        counts = newCounts;
    }

    // Метод для поиска индекса буквы
    private int findLetter(char letter) { //
        for (int i = 0; i < size; i++) {
            if (letters[i] == letter) {
                return i;
            }
        }
        return -1; // это означает, что нужная буква не найдена.
        // это нужно для будущего использование через 6 строчек (проверки условия)
    }

    // Метод для добавления буквы
    public void addLetter(char letter, int count) {
        int i = findLetter(letter);
        if (i != -1) {
            counts[i] += count; // Если буква есть, увеличиваем её количество
        } else {
            // Если буквы нет, добавляем ее
            if (size == letters.length) {
                resizeArrays(); // увеличиваем массив, если нужно
            }
            letters[size] = letter;
            counts[size] = count;
            size++;
        }
    }

    public String toString(int format) {
        if (format == 1) {
            return format1();
        }
        if (format == 2) {
            return format2();
        }
        else {
            return "Неверный формат";
        }
    }

    // Формат вывода 1: "aaaaa c kkk"
    private String format1() {
        StringBuilder sb = new StringBuilder(); // для удобства
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < counts[i]; j++) {
                sb.append(letters[i]);
            }
        }
        return sb.toString();
    }

    // Формат вывода 2: "a-5; c-1; k-3"
    private String format2() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < size; i++) {
            if (!first) {
                sb.append("; ");
            } else {
                first = false;
            }
            sb.append(letters[i]).append("-").append(counts[i]);
        }
        return sb.toString();
    }
    // Геттер для получения букв
    public String getLetters() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(letters[i]).append("-").append(counts[i]).append("; ");
        }
        return sb.toString().trim();
        // В методе getLetters() мы формируем строку, добавляя в нее данные о буквах и их количествах через цикл.
        // После каждого добавления буквы и количества, мы также добавляем ";" в конец.
        // В результате, после завершения цикла в конце строки может оказаться пробел с точкой с запятой.
    }
    // Метод для подсчета количества определенной буквы
    public int getCount(char letter) {
        int i = findLetter(letter); // Ищем индекс буквы
        if (i != -1) {
            return counts[i]; // Если буква найдена, возвращаем ее количество
        }
        else {
            return 0; // Если буква не найдена, возвращаем 0
        }
    }
    // общий подсчет всех букв
    public int getTotalCount() {
        int totalCount = 0;
        for (int i = 0; i < size; i++) {
            totalCount += counts[i];
        }
        return totalCount;
    }
    // Метод для сборки слова, удаляя использованные буквы
    public String buildWord(String word) {
        StringBuilder result = new StringBuilder();
        char[] wordChars = word.toCharArray(); // Преобразуем слово в массив символов
        for (char letter : wordChars) {
            int i = findLetter(letter); // Ищем индекс буквы
            if(i != -1) {
                if (counts[i] > 0) { // Проверяем, есть ли ещё буква в массиве
                    result.append(letter); // Добавляем букву в результат
                    counts[i]--; // Уменьшаем количество буквы
                }
            }
        }
        return result.toString(); // Возвращаем собранное слово
    }
    // Метод для проверки возможности составить слово (без изменения массива)
    public boolean canBuildWord(String word) {
        char[] tempLetters = new char[letters.length];
        int[] tempCounts = new int[counts.length]; // копии
        for (int i = 0; i < size; i++) {
            tempLetters[i] = letters[i];
            tempCounts[i] = counts[i];
        }
        char[] wordChars = word.toCharArray();
        for (char letter : wordChars) {
            int index = findLetterInCopy(letter, tempLetters, size);
            if (index == -1 || tempCounts[index] <= 0) {
                return false;
            }
            tempCounts[index]--;
        }
        return true;
    }

    // поиск буквы, без её удаления
    private int findLetterInCopy(char letter, char[] letters, int size) {
        for (int i = 0; i < size; i++) {
            if (letters[i] == letter) {
                return i;
            }
        }
        return -1;
    }
    // Метод для сборки слова без изменения исходного массива
    public String buildWordWithoutChanges(String word) {
        StringBuilder result = new StringBuilder();
        char[] tempLetters = new char[letters.length]; // копия букв
        int[] tempCounts = new int[counts.length]; // копия счетчиков

        // создаем копии
        for (int i = 0; i < size; i++) {
            tempLetters[i] = letters[i];
            tempCounts[i] = counts[i];
        }
        char[] wordChars = word.toCharArray();
        for (char letter : wordChars) {
            int index = findLetterInCopy(letter, tempLetters, size); // создаем слова
            if (index != -1) {
                if (tempCounts[index] > 0) {
                    result.append(letter); // добавление буквы в слово
                    tempCounts[index]--;
                }
            }
        }
        return result.toString();
    }
    // Метод для прочтения слова (если возможно) и удаления букв
    public String readWord(String word) {
        if (canBuildWord(word)) {
            return buildWord(word); // Если можно построить слово, строим его и удаляем
        }
        return "слово нельзя построить"; // Если нельзя построить слово, возвращаем фразу
    }
    // Метод для подсчёта количества возможных слов
    // Достаточно сложный метод
    public int countPossibleWords(String word) {
        if (!canBuildWord(word)) {
            return 0; // Если слово нельзя составить - 0
        }
        int minCount = Integer.MAX_VALUE;
        char[] wordChars = word.toCharArray();
        int[] tempCounts = new int[counts.length]; // временный массив для хранения количества букв
        for(int i = 0; i < size; i++){ // копируем основные данные во временный массив
            tempCounts[i] = counts[i];
        }
        for (char letter: wordChars) {
            int i = findLetterInCopy(letter, letters, size); // ищем индекс
            if(i == -1){
                return 0;
            }
            int letterCount = 0;
            for(char l: wordChars){
                if (letter == l)
                    letterCount++; // считаем сколько раз буква повторяется в слове
            }
            minCount = Math.min(minCount, tempCounts[i] / letterCount); // минимум раз
        }
        return minCount;
    }
}

// Изменяющие методы:
//resizeArrays(): Увеличивает размер массивов letters и counts, создавая новые массивы.
//addLetter(char letter, int count): Добавляет новую букву или увеличивает счетчик существующей, изменяя letters, counts, и size.
//buildWord(String word): Уменьшает количество доступных букв в массиве counts.
//readWord(String word): Вызывает buildWord, если слово можно составить, тем самым изменяя массив counts.
//Методы, не изменяющие массивы:
//getTotalCount(): Просто подсчитывает общее количество букв, не меняя массивы.
//toString(int format) и format1(), format2(): Возвращает строковое представление, не изменяя массивы.
//getLetters(): Возвращает представление текущего состояния, не изменяя массивы.
//getCount(char letter): Возвращает количество указанной буквы, не меняя массивы.
//buildWordWithoutChanges(String word): Создает копию массивов и работает с ней.
//canBuildWord(String word) Создает копию массивов и работает с ней.
//countPossibleWords(String word): Создает копию массивов и работает с ней.