import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.DoubleAccumulator;

/**
 * Author: Gregory Westbrook
 * Class: Advanced Java
 * Project: 03
 * Date Due: 2016.10.12
 *
 * Created by Greg on 12/1/2016 at 6:14 PM.
 */
public class DataModel {
    private String quarter;
    private String language;
    private Integer count;

    public DataModel(String quarter, String language, int count) {
        this.quarter = quarter;
        this.language = language;
        this.count = count;
    }

    //A constructor that takes in a complete line from the csv file
    public DataModel(String s){
        //Winter,Python,42
        Pattern commaPattern = Pattern.compile(",");
        List<String> data = commaPattern.splitAsStream(s).collect(Collectors.toList());
        this.quarter = data.get(0);
        this.language = data.get(1);
        this.count = Integer.parseInt(data.get(2));
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
