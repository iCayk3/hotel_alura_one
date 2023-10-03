package br.com.hotel_alura.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatarData {
	
	public static final String formatarData(String data){
		Date dt = null;
		try {
			dt = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy" ,Locale.ENGLISH).parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String dataFormatada = new SimpleDateFormat("yyyy-MM-dd").format(dt);
		return dataFormatada;
	}
}
