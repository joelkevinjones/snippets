import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class SimpleDateFormatParseDemo {
    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("MMMMM dd, yyyy", Locale.ENGLISH);
	df.setLenient(true);
	ParsePosition parsePosition = new ParsePosition(0);
	System.out.println("args[0]: " + args[0] +
			   " parsePosition: " + parsePosition);
	Date date = df.parse(args[0], parsePosition);
	String dateString;
	if (date == null) {
	    dateString = "\u25CA\u25CA Invalid Date \u25CA\u25CA"; // LOZENGE
	} else {
	    dateString = df.format(date.getTime());
	}
	System.out.println("parsePosition.getErrorIndex(): " +
	                   parsePosition.getErrorIndex() +
			   ", parsePosition.getIndex(): " +
			   parsePosition.getIndex() + ", " +
			   dateString);
    }
}
