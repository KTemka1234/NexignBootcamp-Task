import java.util.Comparator;

public class CallDataRecordComparator implements Comparator<CallDataRecord> {
    @Override // Данный компаратор сравнивает два cdr сначала по типу звонка, затем по времени начала звонка
    public int compare(CallDataRecord cdr1, CallDataRecord cdr2) {
        int result = cdr1.callType().compareTo(cdr2.callType());
        if (result == 0)
            result = cdr1.callStartTime().compareTo(cdr2.callStartTime());
        return result;
        //TODO: Проверить логику работы
    }
}
