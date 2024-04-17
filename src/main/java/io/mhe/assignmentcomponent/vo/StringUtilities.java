package io.mhe.assignmentcomponent.vo;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.*;

/****************************************************************
 * 
 * The purpose of this class is to provide static utilities for obtaining String -- Dollar Ammounts.
 * 
 * Validation of Strings is performed in ValidationUtils.java.
 * 
 * @author Richard D. Ferri richard.ferri@verizonwireless.com
 * @version 1.0.0-r1
 * 
 * **************************************************************/
public abstract class StringUtilities {
	//private static Logger		logger					= Logger.getInstance(StringUtilities.class);

	/**
	 * Name: _AJAX_ENCODING Type: static final String Default: Set AJAX Default Encoding Type Conformance: Never Changes
	 */
	public static final String	AJAX_ENCODING			= "ISO-8859-1";
	
	public static enum STRING_ENCODING_TYPE {UTF_8("UTF-8"),ISO_8859_1("ISO-8859-1");
		
		private String encodeBy;

		private STRING_ENCODING_TYPE(String encodeBy) {
			this.encodeBy = encodeBy;
		}	
	}

	/** milliseconds in a day */
	private static final long	MILLISECONDS_IN_DAY		= 1000L * 24L * 60L * 60L;

	private static final int	HEX_MAX_SINGLE_DIGIT	= 0x000000ff;
	private static final int	SEGMENT_MILLI_SECOND	= 12;
	private static final int	SEGMENT_RANDOM			= 22;
	private static final int	SEGMENT_IDENTITY_HASH	= 8;
	private static final int	MAX_RANDOM_INT1			= 5;
	private static final int	RANDOM_TIMESTAMP_LENGTH	= 8;
	private static final int	MAX_RANDOM_INT2			= 9;

	/****
	 * * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** This method gets the current date time
	 * stamp and returns it to the user in a String in the specified format. Use the characters below to specify a
	 * format.
	 * 
	 * getCurrentTimeStamp("HHmmssSS") If the time is 22 : 14 : 12 : 82 <HOURS : MINUTES : SECONDS : MILLISECONDS>
	 * RETURNS 22141282
	 * 
	 * 
	 * 
	 * @param simpleDateFormat String representing the simple date format. The format of this string is as follows:<br>
	 * <br>
	 * 
	 * Symbol Meaning Presentation Example<br>
	 * ------ ------- ------------ -------<br>
	 * G era designator (Text) AD<br>
	 * y year (Number) 1996<br>
	 * M month in year (Text & Number) July & 07<br>
	 * d day in month (Number) 10<br>
	 * h hour in am/pm (1~12) (Number) 12<br>
	 * H hour in day (0~23) (Number) 0<br>
	 * m minute in hour (Number) 30<br>
	 * s second in minute (Number) 55<br>
	 * S millisecond (Number) 978<br>
	 * E day in week (Text) Tuesday<br>
	 * D day in year (Number) 189<br>
	 * F day of week in month (Number) 2 (2nd Wed in July)<br>
	 * w week in year (Number) 27<br>
	 * W week in month (Number) 2<br>
	 * a am/pm marker (Text) PM<br>
	 * k hour in day (1~24) (Number) 24<br>
	 * K hour in am/pm (0~11) (Number) 0<br>
	 * z time zone (Text) Pacific Standard Time<br>
	 * ' escape for text (Delimiter)<br>
	 * '' single quote (Literal) '<br>
	 * @return null if the paramter _simpleDateFormat is incorrect in terms of java.text.SimpleDateFormat (As Shown
	 * Above) Otherwise it returns a valid time stamp String as specified by the user. ** * ** * ** * ** * ** * ** * **
	 * * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** *
	 **/
	public static String getCurrentTimeStamp(String simpleDateFormat) {
		try {
			return (new SimpleDateFormat(simpleDateFormat)).format(new Date());
		} catch (Exception ex) {
			/***************************************************************
			 * This can be written better.
			 * 
			 * ************************************************************/
			return null;
		}
	}

	public static String getNonNulTrimmedString(String str) {
		return getNonNullString(str).trim();
	}

	public static String getNonNullString(String str) {
		String returnValue = "";
		if (str != null) {
			returnValue = str;
		}
		return returnValue;
	}

	/**
	 * Returns number of days back for a string formatted MM/dd/yyyy
	 * @param wfgDateStart String formtted MM/dd/yyyy
	 * @return int represneting the number of days back from date specified to today. -1 if the date is after today or
	 * an invalid date.
	 */
	public static int numberOfDaysBack(String wfgDateStart) {
		int daysBack = -1;
		if (!ValidationUtils.stringIsBlankOrNull(wfgDateStart)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date wfgDate = sdf.parse(wfgDateStart);
				Date now = new Date();
				if (now.after(wfgDate)) {
					daysBack = (int) ((now.getTime() - wfgDate.getTime()) / (MILLISECONDS_IN_DAY));
				}
			} catch (Exception ex) {
				/** LOG HERE **/
			}
		}
		return daysBack;
	}

	public static String getMapString(Map<?, ?> map) {
		StringBuilder returnValue = new StringBuilder();
		if (map != null && (!map.isEmpty())) {
			Iterator<?> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object val = map.get(key);
				if (val instanceof String) {
					returnValue.append(key + " == " + ((String) val) + "\n");
				} else if (val instanceof Double) {
					returnValue.append(key + " == " + ((Double) val) + "\n");
				} else if (val instanceof Integer) {
					returnValue.append(key + " == " + ((Integer) val) + "\n");
				} else if (val instanceof Long) {
					returnValue.append(key + " == " + ((Long) val) + "\n");
				} else if (val instanceof Float) {
					returnValue.append(key + " == " + ((Float) val) + "\n");
				} else if (val instanceof Short) {
					returnValue.append(key + " == " + ((Short) val) + "\n");
				} else {
					if (val != null) {
						returnValue.append(key + " == " + val.toString() + "\n");
					}
				}
			}
		}
		return returnValue.toString();
	}

	/**
	 * Returns email addresses from an array of addresses retrieve from the Java Mail API Message class.
	 * @param addresses Address[] when blank or null - null string is returned, otherwise a comma separated String is
	 * returned as.
	 * @return String comma separated email addresses.

	public static String getEmailAddresses(Address[] addresses) {
		String[] emailAddresses = new String[0];
		try {
			if (!ValidationUtils.arrayIsNullOrEmpty(addresses)) {
				emailAddresses = new String[addresses.length];
				for (int i = 0; i < addresses.length; i++) {
					emailAddresses[i] = ((InternetAddress) addresses[i]).getAddress();
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return StringUtilities.getArrayString(emailAddresses, ",");
	}
	 */
	public static String getListString(List<String> array, String separator) {
		String result = "";
		if (!(array == null || array.size() == 0)) {
			String arrayStrings[] = new String[array.size()];
			int counter = 0;
			for (String str : array) {
				arrayStrings[counter++] = (ValidationUtils.stringIsBlankOrNull(str)) ? "" : str;
			}
			result = getArrayString(arrayStrings, separator);
		}
		return result;
	}

	public static String getArrayListString(List<String> array, String separator) {
		String result = "";
		if (!(array == null || array.size() == 0)) {
			String arrayStrings[] = new String[array.size()];
			int counter = 0;
			for (String str : array) {
				arrayStrings[counter++] = (ValidationUtils.stringIsBlankOrNull(str)) ? "" : str;
			}
			result = getArrayString(arrayStrings, separator);
		}
		return result;
	}

	public static String getArrayObjectString(Object[] o, String s) {
		if (o != null && o.length > 0) {

			String[] ss = new String[o.length];
			for (int i = 0; i < o.length; i++) {
				ss[i] = "" + o[i];
			}
			return getArrayString(ss, s);
		} else {
			return "";
		}

	}

	public static String getSetString(Set<?> array, String separator) {
		String result = "";
		if (!(array == null || array.size() == 0)) {
			String arrayStrings[] = new String[array.size()];
			int counter = 0;
			for (Object str : array) {
				arrayStrings[counter++] = (getNonNullString(str));
			}
			result = getArrayString(arrayStrings, separator);
		}
		return result;
	}

	public static double getDoubleFromStringWithDollarSign(String val) {
		double value = 0.0;
		if (ValidationUtils.isValidDoubleWithDollarSign(val)) {
			if (val.indexOf('$') > -1) {
				value = Double.parseDouble(val.substring(val.indexOf('$')+1));
			} else {
				value = Double.parseDouble(val);
			}
		}
		return value;
	}

	/****
	 * * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** This method gets the specified time
	 * stamp from the specified date. If a null date is passed the current date is used with the specified timestamp
	 * data.
	 * 
	 * 
	 * Use the characters below to specify a format.
	 * 
	 * getCurrentTimeStamp("HHmmssSS") If the time is 22 : 14 : 12 : 82 <HOURS : MINUTES : SECONDS : MILLISECONDS>
	 * RETURNS 22141282
	 * 
	 * 
	 * 
	 * @param simpleDateFormat String representing the simple date format. The format of this string is as follows:<br>
	 * <br>
	 * 
	 * Symbol Meaning Presentation Example<br>
	 * ------ ------- ------------ -------<br>
	 * G era designator (Text) AD<br>
	 * y year (Number) 1996<br>
	 * M month in year (Text & Number) July & 07<br>
	 * d day in month (Number) 10<br>
	 * h hour in am/pm (1~12) (Number) 12<br>
	 * H hour in day (0~23) (Number) 0<br>
	 * m minute in hour (Number) 30<br>
	 * s second in minute (Number) 55<br>
	 * S millisecond (Number) 978<br>
	 * E day in week (Text) Tuesday<br>
	 * D day in year (Number) 189<br>
	 * F day of week in month (Number) 2 (2nd Wed in July)<br>
	 * w week in year (Number) 27<br>
	 * W week in month (Number) 2<br>
	 * a am/pm marker (Text) PM<br>
	 * k hour in day (1~24) (Number) 24<br>
	 * K hour in am/pm (0~11) (Number) 0<br>
	 * z time zone (Text) Pacific Standard Time<br>
	 * ' escape for text (Delimiter)<br>
	 * '' single quote (Literal) '<br>
	 * @return null if the paramter _simpleDateFormat is incorrect in terms of java.text.SimpleDateFormat (As Shown
	 * Above) Otherwise it returns a valid time stamp String as specified by the user. ** * ** * ** * ** * ** * ** * **
	 * * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** *
	 **/
	public static String getTimeStamp(String simpleDateFormat, Date date) {
		String returnValue = "";
		try {

			if (date == null) {
				returnValue = getCurrentTimeStamp(simpleDateFormat);
			} else {
				returnValue = (new SimpleDateFormat(simpleDateFormat)).format(date);
			}

		} catch (Exception ex) {
			returnValue = "";
		}
		return returnValue;
	}

	/**
	 * Returns a Unique Identifier Key used for generating SPOC keys for SPOC Emails.
	 * @return String 50 character long unique identifier key.
	 */
	/*public static String getUniqueIdentifier() {
		StringBuffer strRetVal = new StringBuffer();
		String strTemp = "";
		try {
			// Get IPAddress Segment
			InetAddress addr = InetAddress.getLocalHost();

			byte[] ipaddr = addr.getAddress();
			for (int i = 0; i < ipaddr.length; i++) {
				Byte b = new Byte(ipaddr[i]);

				strTemp = Integer.toHexString(b.intValue() & HEX_MAX_SINGLE_DIGIT);
				while (strTemp.length() < 2) {
					strTemp = '0' + strTemp;
				}
				strRetVal.append(strTemp);
			}

			// Get CurrentTimeMillis() segment
			strTemp = Long.toHexString(System.currentTimeMillis());
			while (strTemp.length() < SEGMENT_MILLI_SECOND) {
				strTemp = '0' + strTemp;
			}
			strRetVal.append(strTemp);

			// Get Random Segment
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
			strTemp = "" + prng.nextLong();
			if (strTemp.length() < SEGMENT_RANDOM) {
				while (strTemp.length() < SEGMENT_RANDOM) {
					strTemp = '0' + strTemp;
				}
			} else if (strTemp.length() > SEGMENT_RANDOM) {
				strTemp = strTemp.substring(0, SEGMENT_RANDOM);
			}
			strRetVal.append(strTemp);
			// Get IdentityHash() segment
			strTemp = Long.toHexString(System.identityHashCode(new Object()));
			while (strTemp.length() < SEGMENT_IDENTITY_HASH) {
				strTemp = '0' + strTemp;
			}
			strRetVal.append(strTemp);
		} catch (java.net.UnknownHostException ex) {
			*//** LOG HERE **//*
			//logger.error(ex);
		} catch (java.security.NoSuchAlgorithmException ex) {
			*//** LLOG HERE **//*
			//logger.error(ex);
		}
		return strRetVal.toString().toUpperCase();
	}*/

	/****
	 * * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** This method gets the current date time
	 * stamp and returns it to the user in a String in the specified format. A Random Number Generator is applied to the
	 * String before it is returned. Use the characters below to specify a format.
	 * 
	 * getCurrentTimeStamp("HHmmssSS") If the time is 22 : 14 : 12 : 82 <HOURS : MINUTES : SECONDS : MILLISECONDS>
	 * RETURNS 22141282
	 * 
	 * 
	 * 
	 * @param simpleDateFormat String representing the simple date format. The format of this string is as follows:<br>
	 * <br>
	 * Symbol Meaning Presentation Example<br>
	 * ------ ------- ------------ -------<br>
	 * G era designator (Text) AD<br>
	 * y year (Number) 1996<br>
	 * M month in year (Text & Number) July & 07<br>
	 * d day in month (Number) 10<br>
	 * h hour in am/pm (1~12) (Number) 12<br>
	 * H hour in day (0~23) (Number) 0<br>
	 * m minute in hour (Number) 30<br>
	 * s second in minute (Number) 55<br>
	 * S millisecond (Number) 978<br>
	 * E day in week (Text) Tuesday<br>
	 * D day in year (Number) 189<br>
	 * F day of week in month (Number) 2 (2nd Wed in July)<br>
	 * w week in year (Number) 27<br>
	 * W week in month (Number) 2<br>
	 * a am/pm marker (Text) PM<br>
	 * k hour in day (1~24) (Number) 24<br>
	 * K hour in am/pm (0~11) (Number) 0<br>
	 * z time zone (Text) Pacific Standard Time<br>
	 * ' escape for text (Delimiter)<br>
	 * '' single quote (Literal) '<br>
	 * @return null if the paramter _simpleDateFormat is incorrect in terms of java.text.SimpleDateFormat (As Shown
	 * Above) Otherwise it returns a valid time stamp String as specified by the user. ** * ** * ** * ** * ** * ** * **
	 * * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** *
	 **/
	public static String getRandomTimeStamp(String simpleDateFormat) {
		Random random = new Random();
		for (int i = 0; i < random.nextInt(); i++) {
			random.nextInt(MAX_RANDOM_INT1);
		}

		String currentDateTime = getCurrentTimeStamp("SSssmmHH");
		int val = random.nextInt(MAX_RANDOM_INT1) * Integer.parseInt(currentDateTime);
		String returnValue = "" + val;
		if (returnValue.length() > RANDOM_TIMESTAMP_LENGTH) {
			return returnValue.substring(0, RANDOM_TIMESTAMP_LENGTH);
		}
		while (returnValue.length() < RANDOM_TIMESTAMP_LENGTH) {
			returnValue = "" + random.nextInt(MAX_RANDOM_INT2) + returnValue;
		}
		return returnValue;
	}

	/**
	 * Returns a String separated by _separator with maximum length _maxLength. if _maxLength is exceeded then the
	 * string will only contain elements up to the last index of the separator.
	 * @param array String[] to be concatenated
	 * @param separator String representing the separator
	 * @param maxLength int representing the maximum length
	 * @return String of the values
	 */
	public static String getArrayString(String[] array, String separator, int maxLength) {
		String returnValue = getArrayString(array, separator);
		if (maxLength > 0
				&& ((!ValidationUtils.stringIsBlankOrNull(returnValue)) && (returnValue.length() > maxLength))) {
			returnValue = returnValue.substring(0, returnValue.lastIndexOf(','));
		}
		return returnValue;
	}

	/**
	 * Takes a String array and formats it to a String.
	 * @param array String[] to be formatted into a string.
	 * @param separator String separator between strings.
	 * @return String[] if the array is null or blank, a blank string is returned.
	 */
	public static String getArrayString(String[] array, String separator) {
		if (ValidationUtils.arrayIsNullOrEmpty(array)) {
			return "";
		} else {
			String returnValue = "";
			for (int i = 0; i < array.length; i++) {
				if (!ValidationUtils.stringIsBlankOrNull(array[i])) {
					returnValue = returnValue + array[i];
				}
				if (!ValidationUtils.stringIsBlankOrNull(array[i]) && i < (array.length - 1)) {
					returnValue = returnValue + separator;
				}
			}
			return returnValue;
		}
	}

	public static String getArrayString(long[] array, String separator) {
		if (array == null || array.length == 0) {
			return "";
		} else {
			String returnValue = "";
			for (int i = 0; i < array.length; i++) {
				if (!ValidationUtils.stringIsBlankOrNull("" + array[i])) {
					returnValue = returnValue + array[i];
				}
				if (!ValidationUtils.stringIsBlankOrNull("" + array[i]) && i < (array.length - 1)) {
					returnValue = returnValue + separator;
				}
			}
			return returnValue;
		}
	}

	public static String getW3CXMLDocumentString(org.w3c.dom.Document document) {
		String returnValue = "";
		try {
			if (document != null) {
				Source source = new DOMSource(document);
				StringWriter stringWriter = new StringWriter();
				Result result = new StreamResult(stringWriter);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer();
				transformer.transform(source, result);
				returnValue = stringWriter.getBuffer().toString();
			}
		} catch (Exception ex) {
			/** LOG HERE **/
		}
		return returnValue;
	}

	public static String[] getArrayStringToString(String string, String separator) {
		String array[] = new String[0];
		if ((!ValidationUtils.stringIsBlankOrNull(string)) && (!ValidationUtils.stringIsBlankOrNull(separator))) {
			array = string.split(separator);
		}
		return array;
	}

	public static String prependString(String stringToManipulate, String stringToPrepend, int stringLength) {
		String copy = stringToManipulate;
		if (!ValidationUtils.stringIsBlankOrNull(copy)) {
			while (copy.length() < stringLength) {
				copy = stringToPrepend + copy;
			}
		}
		return copy;
	}

	public static String replaceNextIndex(String stringToManipulate, String stringToReplace, String replacementString) {
		String returnValue = "";
		if ((!ValidationUtils.stringIsBlankOrNull(stringToManipulate)) && (!ValidationUtils.stringIsBlankOrNull(stringToReplace))
				&& (replacementString != null)) {
			int startIndex = stringToManipulate.indexOf(stringToReplace);
			int endIndex = startIndex + stringToReplace.length();
			if (startIndex > -1 && endIndex > -1) {
				StringBuilder str = new StringBuilder(stringToManipulate);
				str = str.replace(startIndex, endIndex, replacementString);
				returnValue = str.toString();
			} else {
				returnValue = stringToManipulate;
			}
		}
		return returnValue;
	}

	public static String getStringFromBlob(Blob blob) {
		String returnValue = "";
		try {
			if (blob != null) {
				returnValue = new String(blob.getBytes(1, (int) blob.length()));
			}
		} catch (Exception ex) {
			/** LOG HERE **/
		}
		return returnValue;
	}

	/**
	 * This method returns a non null string from object.
	 * @param obj Object being checked against.
	 * @return
	 */
	public static String getNonNullString(Object obj) {
		if (obj == null) {
			return "";
		} else if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof StringBuffer) {
			return ((StringBuffer) obj).toString();
		} else if (obj instanceof StringBuilder) {
			return ((StringBuilder) obj).toString();
		} else {
			return "" + obj;
		}
	}

	/**
	 * Parses a comma-separated list into an array of Strings Values can contain whitespace, but whitespace at the
	 * beginning and end of each value is trimmed.
	 * 
	 * @param csvList a string of comma seperated values
	 * @return array of Strings
	 */
	public static List<String> parseCommaDelimitedList(String csvList) {
		return parseList(csvList, ",");
	}

	/**
	 * Parses a list according to the specified delimiter into an array of Strings.
	 * 
	 * @param list a string of token seperated values
	 * @param delim the delimiter character(s). Each character in the string is a single delimeter.
	 * @return an array of strings
	 * @see StringTokenizer
	 */
	public static List<String> parseList(String list, String delim) {
		List<String> result = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(list, delim);
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		return result;
	}

	public static String getRepeatingStringOccurance(Object toString, String separator, int numberOfTimes) {
		if (numberOfTimes < 1) {
			throw new IllegalArgumentException("Error, unable to repeat any string " + numberOfTimes + " times.");
		}
		if (toString == null) {
			throw new IllegalArgumentException("Error, unable to repeat null value.");
		}
		if (separator == null) {
			throw new IllegalArgumentException("Error, unable to have a null separator.");
		}
		StringBuilder returnValue = new StringBuilder("");
		String value = "" + toString;
		for (int i = 0; i < numberOfTimes; i++) {
			returnValue.append(value);
			if (i < (numberOfTimes - 1)) {
				returnValue.append(separator);
			}
		}
		return returnValue.toString();
	}

	public static String getNonNullStringWithin(String str, int maxSize) {
		String strVal = getNonNullString(str);
		if (strVal.length() > maxSize) {
			strVal = strVal.substring(0, maxSize);
		}
		return strVal;
	}

	public static int compareStrings(String arg0, String arg1) {
		return getNonNullString(arg0).compareTo(getNonNullString(arg1));
	}
	
	public static int compareStringsWithoutCase(String str1, String str2){
		if(null == str1){
			return -1;
		}
		else if(null == str2){
			return 1;
		}else{
			int returnValue = str1.compareToIgnoreCase(str2);
			return (0 == returnValue) ? str1.compareTo(str2) : returnValue;
		}
		
	}

	/*public static <T> Set<String> getStringSet(Collection<T> collection) {
		Set<String> stringSet = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(collection)) {
			for (T itemInCollection : collection) {
				if (null != itemInCollection) {
					stringSet.add(itemInCollection.toString());
				}
			}
		}
		return stringSet;

	}*/
	
	/**
	 * Method to convert any collection to string array.
	 * @param
	 * @return
	 */
	/*public static <T> String[] getAsStringArray(Collection<? extends Number> collection) {
		if (CollectionUtils.isNotEmpty(collection)) {
			String[] strArray = new String[collection.size()];
			int i = 0;			
			for (Number itemInCollection : collection) {
				strArray[i] = String.valueOf(itemInCollection);
				i++;
			}
			return strArray;
		}else{
			return new String[0];
		}
		

	}*/
	
	/*public static Integer getAsInteger(String stringToParse){
		if(StringUtils.hasText(stringToParse)){
			return Integer.parseInt(stringToParse);
		}
		return null;
	}*/
	
	/*public static String encode(String param, STRING_ENCODING_TYPE encoder) {
		try {
			return URLEncoder.encode(param, encoder.encodeBy);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("unable to encode string ", e).setParameter("param", param).setParameter("encoder", encoder);
		}
	}
	
	public static String decode(String param, STRING_ENCODING_TYPE encoder) {
		try {
			return URLDecoder.decode(param, encoder.encodeBy);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("unable to decode string ", e).setParameter("param", param).setParameter("encoder", encoder);
		}
	}*/
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	/**
	 * Convenience method to convert a CSV string list to a set that preserves insertion order.
	 * Note that this will suppress duplicates.
	 * @param str the input String
	 * @return a Set of String entries in the list
	 */
	/*public static Set<String> commaDelimitedListToOrderedSet(String str) {
		Set<String> set = new LinkedHashSet<String>();
		String[] tokens = StringUtils.commaDelimitedListToStringArray(str);
		for (String token : tokens) {
			set.add(token);
		}
		return set;
	}*/
}
