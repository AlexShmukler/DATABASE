import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Bus implements Serializable,Comparable<Bus> {
    @XmlElement
    private Marks mark;
    @XmlElement
    private int capacity;
    @XmlElement
    private String number;

    public Bus(){
        this.mark = null;
        this.capacity = 0;
        this.number = null;
    }
    public Bus(Marks mark, int capacity, String number) {
        this.mark = mark;
        this.capacity = capacity;
        this.number = number;
    }

    public int busesNeeded(int people) {
        if (people <= 1) throw new IllegalArgumentException("Wrong amount of people");
        int result = (int)people / this.capacity;
        if (result * this.capacity < people)
            result++;
        return result;
    }

    public boolean isEnoughtSpace(int people){
        return this.capacity >= people;
    }


    public static final Comparator<Bus> COMPARE_BY_CAPACITY = new Comparator<Bus>() {
        @Override
        public int compare(Bus lhs, Bus rhs) {
            return lhs.getCapacity()- rhs.getCapacity();
        }
    };
    @Override
    public int compareTo(Bus other) {
        return number.compareTo(other.number);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Bus temp = (Bus) obj;
        return ((this.capacity == temp.capacity) || (this.mark == temp.mark) || (this.number == temp.number));
    }

    @Override
    public int hashCode() {
        int result = mark.hashCode();
        result = 31 * result + capacity;
        result = 31 * result + number.hashCode();
        return result;
    }

    public String toSrting(){
        return "capacity=" + capacity + "\nNumber=" + number + "\nMark=" + mark;
    }
    public Bus fromString(String[] str){
        Pattern pattern1 = Pattern.compile("Number=([A-Z]{2}\\d{4}[A-Z]{2})");
        Pattern pattern2 = Pattern.compile("Number=(\\d{2,3})");
        Pattern pattern3 = Pattern.compile("Mark=([A-Z][a-z]{3,8}})");
        Matcher match;

        for(int i = 0; i < 4; i++) {

            if((match = pattern1.matcher(str[i])).matches() == true){
                this.number = match.group(1);
            }
            if((match = pattern2.matcher(str[i])).matches() == true){
                this.capacity = Integer.parseInt(match.group(1));
            }
            if((match = pattern3.matcher(str[i])).matches()){
                this.mark = Marks.valueOf(match.group(1));
            }
        }
        return this;
    }

    public Marks getMark() {
        return mark;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getNumber() {
        return number;
    }

    public void setMark(Marks mark) {
        this.mark = mark;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
