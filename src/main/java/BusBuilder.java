import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusBuilder {
    private Marks mark;
    private int capacity;
    private String number;
    private static String NUMBER_PATTERN = "[A-Z]{2}\\d{4}[A-Z]{2}";
    private static int MIN_CAPACITY = 16;
    private static int MAX_CAPACITY = 200;

    public static void setNumberPattern(String numberPattern) {
        BusBuilder.NUMBER_PATTERN = numberPattern;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMark(Marks mark) {
        this.mark = mark;
    }

    public Bus build(Marks mark, int capacity, String number){
        StringBuilder possibleException = new StringBuilder();
        if (capacity > MAX_CAPACITY | capacity < MIN_CAPACITY) possibleException.append("Wrong capacity");
        Pattern p = Pattern.compile(this.NUMBER_PATTERN);
        Matcher m = p.matcher(number);
        if (!m.matches()) possibleException.append("Wrong number");
        if(possibleException.length() != 0)
            throw new IllegalArgumentException(possibleException.toString());
        this.mark = mark;
        this.capacity = capacity;
        this.number = number;
        return new Bus(this.mark,this.capacity,this.number);
    }
}
