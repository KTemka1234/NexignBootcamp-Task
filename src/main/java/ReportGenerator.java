import java.util.HashMap;
import java.util.LinkedList;

public class ReportGenerator {
    public static void generateReport(String path, HashMap<String, LinkedList<CallDataRecord>> allCalls) {
        // Сортируем звонки с каждого номера по типу звонка и времени начала звонка
        CallDataRecordComparator comparator = new CallDataRecordComparator();
        int reportIndex = 1;
        for (LinkedList<CallDataRecord> value : allCalls.values()) {
            value.sort(comparator);
            String reportPath = "reports/report_" + reportIndex + ".txt";
            writeTableInFile(reportPath, value);
            //TODO: Проверить логику работы
        }
    }

    private static String getTable(LinkedList<CallDataRecord> phoneCalls) {
        double cost = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append("""
                ----------------------------------------------------------------------------
                | Call Type |   Start Time        |     End Time        | Duration | Cost  |
                """);

        for (CallDataRecord cdr : phoneCalls) {
            sb.append("");
            //TODO: Убрать данную функцию и сделать запись данный напрямую в файл без создания String-перемнной
        }

        sb.append("----------------------------------------------------------------------------\n" +
                "|                                           Total Cost: |     " + cost + " rubles |\n" +
                "----------------------------------------------------------------------------");
        return sb.toString();
    }

    private static void writeTableInFile(String path, LinkedList<CallDataRecord> phoneCalls) {
        //TODO: Реализовать запись данных в файл
    }
}
