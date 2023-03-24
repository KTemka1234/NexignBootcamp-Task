import java.util.Date;

public record CallDataRecord(String callType,
                             String phone,
                             Date callStartTime,
                             Date callFinishTime,
                             TariffType tariff) {

}
