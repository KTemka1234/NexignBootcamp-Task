public enum TariffType {
    UNLIMITED(1.0),
    PER_MINUTE(1.5),
    REGULAR(0.5);

    private final double costPerMinute;

    TariffType(double costPerMinute) {
        this.costPerMinute = costPerMinute;
    }

    public double getCostPerMinute() {
        return costPerMinute;
    }
    public double applyTariff(int minutes, String callType) {
        if (this == UNLIMITED) {
            return Math.max(minutes - 300, 0);
        } else if (this == PER_MINUTE) {
            return minutes * costPerMinute;
        } else {
            if (callType.equals("02"))
            {
                return 0;
            }
            return minutes - 100 <= 0 ?
                    minutes * costPerMinute :
                    100 * costPerMinute + (minutes - 100) * costPerMinute;
        }
    }
}
