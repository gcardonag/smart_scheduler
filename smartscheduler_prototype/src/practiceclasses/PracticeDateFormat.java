package practiceclasses;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PracticeDateFormat {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		Date dat=new Date();
		String date=" 07/06/2012";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date myDate= df.parse(date);
		System.out.println("" + myDate);
		int myDat= df.parse(date).getDate();
		System.out.println("" + myDat);
		System.out.println("" + dat);
	}

}
