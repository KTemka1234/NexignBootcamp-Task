import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;

public class ReportGenerator {
    public static void generateReports(String path, HashMap<String, LinkedList<CallDataRecord>> allCalls) {
        // Сортируем звонки с каждого номера по типу звонка и времени начала звонка
        CallDataRecordComparator comparator = new CallDataRecordComparator();
        int reportIndex = 1;
        for (LinkedList<CallDataRecord> value : allCalls.values()) {
            value.sort(comparator);
            String reportPath = path + "/report_" + reportIndex + ".txt";
            writeTableInFile(reportPath, value);
            reportIndex += 1;
        }
    }

    private static void writeTableInFile(String path, LinkedList<CallDataRecord> phoneCalls) {
        String tariffIndex = phoneCalls.getFirst().tariff().toString();
        String phone = phoneCalls.getFirst().phone();
        String tableCap = String.format(
                """
                Tariff index: %s
                -------------------------------------------------------------------------------
                Report for phone number %s:
                -------------------------------------------------------------------------------
                | Call Type |     Start Time      |       End Time      | Duration |   Cost   |
                -------------------------------------------------------------------------------
                """, tariffIndex, phone
        );
        String tableBottomTemplate = """
                -------------------------------------------------------------------------------
                |                                           Total Cost: |    %6.2f rubles    |
                -------------------------------------------------------------------------------
                """;

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(tableCap);
            double totalCost = 0.0;
            DateTimeFormatter durationFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (CallDataRecord phoneCall : phoneCalls) {
                String callType = phoneCall.callType();
                String callStartTime = dateFormat.format(phoneCall.callStartTime());
                String callEndTime = dateFormat.format(phoneCall.callEndTime());
                LocalDateTime duration = calcCallDuration(phoneCall);
                String durationFormatted = durationFormat.format(duration);
                double callCost = phoneCall.tariff().applyTariff(duration.getMinute(), callType);
                totalCost += callCost;
                String tableLine =  String.format(
                        "|     %s    | %s | %s | %s |  %5.2f   |\n", callType, callStartTime,
                        callEndTime, durationFormatted, callCost
                );
                writer.write(tableLine);
            }
            writer.write(String.format(tableBottomTemplate, totalCost));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static LocalDateTime calcCallDuration(CallDataRecord phoneCall) {
        LocalDateTime startDate = LocalDateTime.ofInstant(phoneCall.callStartTime().toInstant(),
                ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(phoneCall.callEndTime().toInstant(),
                ZoneId.systemDefault());
        Duration duration = Duration.between(startDate, endDate);
        return LocalDateTime.of(1, 1, 1, 0, 0, 0).plus(duration);
    }
}
