package DTO;

import java.text.NumberFormat;
import java.util.Locale;

public class Format {
	public static String vnd(Double number) {
		Locale locale = Locale.forLanguageTag("vi-VN"); // Thiết lập locale cho tiếng Việt
		NumberFormat numberFormat = NumberFormat.getInstance(locale);
		String formattedNumber = numberFormat.format(number);
		return formattedNumber;
	}
}