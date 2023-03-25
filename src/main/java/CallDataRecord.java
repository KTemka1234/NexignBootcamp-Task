import java.util.Date;

public class CallDataRecord {
    private String callType;
    private String phone;
    private Date callStartTime;
    private Date callEndTime;
    private TariffType tariff;

    public CallDataRecord(String callType, String phone, Date callStartTime, Date callEndTime, TariffType tariff) {
        this.callType = callType;
        this.phone = phone;
        this.callStartTime = callStartTime;
        this.callEndTime = callEndTime;
        this.tariff = tariff;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(Date callStartTime) {
        this.callStartTime = callStartTime;
    }

    public Date getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(Date callEndTime) {
        this.callEndTime = callEndTime;
    }

    public TariffType getTariff() {
        return tariff;
    }

    public void setTariff(TariffType tariff) {
        this.tariff = tariff;
    }
}
