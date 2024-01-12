import java.io.*;
import java.util.ArrayList;

public class FileWr {
    private boolean ap = false;
    private String path = "";
    private String prefix = "";
    private boolean shortstat = false;
    private boolean longstat = false;
    private ArrayList<String> files = new ArrayList<String>();
    private int countF = 0;
    private int countI = 0;
    private int countS = 0;
    private float maxF = 0f;
    private float minF = Float.MAX_VALUE;
    private float sredI = 0f;
    private float sredF = 0f;
    private int minI = Integer.MAX_VALUE;
    private int maxI = 0;
    private int sumI = 0;
    private float sumF = 0f;
    private int stringMax = 0;
    private int stringMin = Integer.MAX_VALUE;
    private ArrayList<String> oint = new ArrayList<String>();
    private ArrayList<String> ofloat = new ArrayList<String>();
    private ArrayList<String> ostring = new ArrayList<String>();

    FileWr(String[] b) {
        doset(b);
    }

    void mani() throws FileNotFoundException {
            while (!files.isEmpty()) {
                for (int i = 0; i < files.size(); i++) {
                    try (BufferedReader asd = new BufferedReader(new FileReader(files.get(i)))) {
                        String line;
                        while ((line = asd.readLine()) != null) {
                            if (stringToInt(line)) {
                                oint.add(line);
                                countI++;
                                sumI += Integer.parseInt(line);
                                if (Integer.parseInt(line) < minI) {
                                    minI = Integer.parseInt(line);
                                } else if (maxI < Integer.parseInt(line)) {
                                    maxI = Integer.parseInt(line);
                                }
                            } else if (stringToFloat(line)) {
                                ofloat.add(line);
                                countF++;
                                sumF += Float.parseFloat(line);
                                if (Float.parseFloat(line) < minF) {
                                    minF = Float.parseFloat(line);
                                } else if (maxF < Float.parseFloat(line)) {
                                    maxF = Float.parseFloat(line);
                                }
                            } else if (line != "") {
                                ostring.add(line);
                                countS++;
                                if (line.length() < stringMin) {
                                    stringMin = line.length();
                                } else if (stringMax < line.length()) {
                                    stringMax = line.length();
                                }
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                files.clear();
            }
            if (countI != 0) {
                sredI = (float) sumI / countI;
            }
            if (countF != 0) {
                sredF = (float) sumF / countF;
            }
            if (!oint.isEmpty()) {
                try (BufferedWriter asd = new BufferedWriter(new FileWriter(path + prefix + "Integers.txt", ap))) {
                    for (String item : oint) {
                        asd.write(item + "\n");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (!ofloat.isEmpty()) {
                try (BufferedWriter asd = new BufferedWriter(new FileWriter(path + prefix + "Floats.txt", ap))) {
                    for (String item : ofloat) {
                        asd.write(item + "\n");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (!ostring.isEmpty()) {
                try (BufferedWriter asd = new BufferedWriter(new FileWriter(path + prefix + "Strings.txt", ap))) {
                    for (String item : ostring) {
                        asd.write(item + "\n");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (stringMin == Integer.MAX_VALUE) {
                stringMin = stringMax;
            }
            if(minI == Integer.MAX_VALUE){
                maxI = minI;
            }
            if(minF == Float.MAX_VALUE){
                maxF = minF;
            }
            if (longstat) {
                System.out.println("Полная статистика:\n" + "количество Integer:" + countI + "\n" + "количество Float:" + countF + "\n" + "количество String:" + countS + "\n" + "сумма Integer:" + sumI + "\n" + "сумма Float:" + sumF + "\n" + "среднее Integer:" + sredI + "\n" + "среднее Float:" + sredF + "\n" + "Максимальная длина строки:" + stringMax + "\n" + "Минимальная длина строка:" + stringMin + "\n" + "Максимальное число Integer:" + maxI + "\n" + "Минимальное число Integer:" + minI + "\n" + "Максимальное число Float:" + maxF + "\n" + "Минимальное число Float:" + minF);
            } else if (shortstat) {
                System.out.println("Краткая статистика:\n" + "количество Integer:" + countI + "\n" + "количество Float:" + countF + "\n" + "количество String:" + countS);
            }
    }

    private boolean stringToFloat(String a) throws NumberFormatException {
        try {
            Float.parseFloat(a);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean stringToInt(String b) throws NumberFormatException {
        try {
            Integer.parseInt(b);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void doset(String[] b) {
        for (int i = 0; i < b.length; i++) {
            switch (b[i]) {
                case ("-s"):
                    this.shortstat = true;
                    break;
                case ("-f"):
                    this.longstat = true;
                    break;
                case ("-o"):
                    this.path = b[i + 1];
                    break;
                case ("-p"):
                    this.prefix = b[i + 1];
                    break;
                case ("-a"):
                    this.ap = true;
                    break;
                default:
                    if (b[i].lastIndexOf(".txt") != -1) {
                        this.files.add(b[i]);
                    }
            }
        }
    }
}