import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            createRecord();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createRecord() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите фамилию, имя, отчество, дату рождения (dd.mm.yyyy), номер телефона (целое беззнаковое число без форматирования) и пол (символ латиницей f или m), разделенные пробелом");

        String input = sc.nextLine();
        String[] array = input.split(" ");
        if (array.length != 6){
//            System.out.println("Введено неверное количество параметров");
            throw new IllegalArgumentException("Введено неверное количество параметров");
        }

        String surname = array[0];
        String name = array[1];
        String fatherName = array[2];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        Date birthDate = null;
        try {
            birthDate = dateFormat.parse(array[3]);
        }catch (ParseException e){
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        int phone;
        try {
            phone = Integer.parseInt(array[4]);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Неверный формат телефона");
        }

        String sex = array[5];
        if (!sex.toLowerCase().equals("m") && !sex.toLowerCase().equals("f")){
            throw new RuntimeException("Неверно введен пол");
        }

        File file = new File(surname.toLowerCase());
        FileWriter fw = null;

        try {
            if (!file.exists()) {

                fw = new FileWriter(file);
                fw.write(String.format("%s %s %s %s %s %s", surname, name, fatherName, dateFormat.format(birthDate), phone, sex));
                fw.close();
            } else {
                fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.newLine();
                bw.write(String.format("%s %s %s %s %s %s", surname, name, fatherName, dateFormat.format(birthDate), phone, sex));
                bw.close();
                fw.close();
            }
        } catch (IOException e) {
            System.out.print("Error: " + e + "ошибка записи");
        }
    }
}