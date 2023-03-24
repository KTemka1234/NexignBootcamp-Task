import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        String file = "cdr.txt";
        HashMap<String, LinkedList<CallDataRecord>> cdrHashMap = new HashMap<>();
        CallDataRecord cdr = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                cdr = parseCDRLine(line);
                line = reader.readLine();
                // Если ключа нет, создаем новый список и добавляем его в HashMap.
                // Если ключ есть, добавляем новую запись в конец списка
                String phone = cdr.phone();
                LinkedList<CallDataRecord> cdrList = null;
                if (!cdrHashMap.containsKey(phone)) {
                    cdrList = new LinkedList<>();
                    cdrList.add(cdr);
                    cdrHashMap.put(cdr.phone(), cdrList);
                } else {
                    cdrList = cdrHashMap.get(phone);
                    cdrList.add(cdr);
                }
            }
        } catch (ParseException ex) {
            System.out.println("CDR line parsing error: " + ex.getMessage());
        } catch(IOException ex) {
            System.out.println("Error while reading file: " + ex.getMessage());
        }
        ReportGenerator.generateReport("reports", cdrHashMap);
        //TODO: Проверить логику работы
    }

    private static CallDataRecord parseCDRLine(String record) throws ParseException {
        String[] CDRParts = record.split(" ");
        if (CDRParts.length != 5)
            throw new ParseException("Invalid CDR line", 0);
        if (!checkCallType(CDRParts[0]))
            throw new ParseException("Invalid call type", 0);
        if (!checkPhoneNumber(CDRParts[1]))
            throw new ParseException("Invalid phone number", 0);
        return new CallDataRecord(CDRParts[0], CDRParts[1],
                parseDate(CDRParts[2]), parseDate(CDRParts[3]), parseTariffType(CDRParts[4]));
    }

    private static boolean checkCallType(String callType) {
        return callType.equals("01") || callType.equals("02");
    }

    private static boolean checkPhoneNumber(String phone) {
        return phone.matches("\\d{11}");
    }

    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.parse(dateString);
    }

    private static TariffType parseTariffType(String tariffType) throws ParseException {
        if (tariffType.length() != 2) {
            throw new ParseException("Invalid tariff type", 0);
        }
        return switch (tariffType) {
            case "06" -> TariffType.UNLIMITED;
            case "03" -> TariffType.PER_MINUTE;
            case "11" -> TariffType.REGULAR;
            default -> throw new ParseException("Invalid tariff type", 0);
        };
    }
}
