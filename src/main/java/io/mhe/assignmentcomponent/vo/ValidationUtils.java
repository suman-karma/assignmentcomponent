package io.mhe.assignmentcomponent.vo;

//import com.mhe.connect.business.common.Logger;
//import com.mhe.connect.exception.ConnectApplicationException;

//import javax.mail.internet.InternetAddress;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class ValidationUtils {

	//private static Logger		logger										= Logger.getInstance(ValidationUtils.class);

	/** date format with length 19 */
	private static final int	TIMESTAMP_LENGTH							= 19;
	private static final int	TIMESTAMP_MONTH_START_INDEX					= 0;
	private static final int	TIMESTAMP_MONTH_END_INDEX					= 2;
	private static final int	TIMESTAMP_DAY_START_INDEX					= 3;
	private static final int	TIMESTAMP_DAY_END_INDEX						= 5;
	private static final int	TIMESTAMP_YEAR_START_INDEX					= 6;
	private static final int	TIMESTAMP_YEAR_END_INDEX					= 10;
	private static final int	TIMESTAMP_HOUR_START_INDEX					= 11;
	private static final int	TIMESTAMP_HOUR_END_INDEX					= 13;
	private static final int	TIMESTAMP_MINUTE_START_INDEX				= 14;
	private static final int	TIMESTAMP_MINUTE_END_INDEX					= 16;
	private static final int	TIMESTAMP_SECOND_START_INDEX				= 17;
	private static final int	TIMESTAMP_SECOND_END_INDEX					= 19;

	/** date format with length 6 */
	private static final int	TIMESTAMP_COMPACT_FORMAT_LENGTH				= 6;

	/** date format with length 8 */
	private static final int	TIMESTAMP_COMPACT_FORMAT2_LENGTH			= 8;
	private static final int	TIMESTAMP_COMPACT_FORMAT2_MONTH_START_INDEX	= 0;
	private static final int	TIMESTAMP_COMPACT_FORMAT2_DAY_START_INDEX	= 2;
	private static final int	TIMESTAMP_COMPACT_FORMAT2_YEAR_START_INDEX	= 4;

	private static final int	TIMESTAMP_INVALID_TOKEN_COUNT				= 3;

	/**
	 * Returns true if the has 1 null element or if the entire array is empty or null.
	 * @param array Object[] that is being evaluated.
	 * @return true if array is null or empty or if a null element is in the array and false otherwise.
	 */
	public static boolean arrayContainsNullElements(Object[] array) {
		if (arrayIsNullOrEmpty(array)) {
			return true;
		} else {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Returns true if the array is null or has length of 0 and false otherwise.
	 * @param array Object[] that is being evaluated.
	 * @return true if array is null or empty and false otherwise.
	 */
	public static boolean arrayIsNullOrEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * Returns true if String argument is null, blank or equals the string null. Useful for checking database result set
	 * data when sometimes a null string is returned as a string with the value NULL
	 * @param str String to be evaluated.
	 * @return true if string is null, blank or equals the string null and false otherwise.
	 */
	public static boolean stringIsBlankOrNullDB(String str) {
		return ValidationUtils.stringIsBlankOrNull(str) || str.trim().equalsIgnoreCase("null");
	}

	public static boolean stringIsBlankOrNull(String str) {
		return (str == null || str.trim().equals(""));
	}

	/**
	 * Returns a stack trace formatted string of the passed in exception.
	 * 
	 * @param ex Exception to be formatted into string Must not be null.
	 * @return a string format of the exception.
	 */
	public static String getStackTrace(Throwable ex) {
		StringBuilder errStr = new StringBuilder();
		if (ex != null) {
			errStr.append("[Exception Type {" + ex.getClass().getName() + "}]\n");
			errStr.append("[Stack Trace Message{" + ex.getMessage() + "}]");
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			errStr.append("[Stack Trace { " + writer.toString() + " } ]");
		}
		return errStr.toString();
	}

	public static boolean isValidLong(String str) {
		try {
			Long.parseLong(str.trim());
			return true;
		} catch (NumberFormatException ne) {
			return false;
		}
	}

	public static boolean isValidStringTypeGreaterThanMin(Object obj, int minSize) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String) {
			return ((String) obj).length() >= minSize;
		} else if (obj instanceof StringBuffer) {
			return ((StringBuffer) obj).length() >= minSize;
		} else if (obj instanceof StringBuilder) {
			return ((StringBuilder) obj).length() >= minSize;
		}
		return false;
	}

	/**
	 * Null object returns true. If O is an instance of String, String Buffer , String Builder, make sure that the value
	 * is <= Max Size in length. No Trimming is done.
	 * @param obj Object should be String, StringBuffer or StringBuilder
	 * @param maxSize int representing the max size
	 * @return boolean true if _o ==null or _o is String,StringBuffer, StringBuilder <= _maxSize and false if wrong
	 * object type is passed or >= size _maxSize
	 */
	public static boolean isValidStringTypeLessThanMax(Object obj, int maxSize) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String) {
			return ((String) obj).length() <= maxSize;
		} else if (obj instanceof StringBuffer) {
			return ((StringBuffer) obj).length() <= maxSize;
		} else if (obj instanceof StringBuilder) {
			return ((StringBuilder) obj).length() <= maxSize;
		}
		return false;
	}

	public static boolean isValidShort(String str) {
		try {
			Short.parseShort(str.trim());
			return true;
		} catch (NumberFormatException ne) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/****
	 * 
	 * Returns true if the _str String is a valid integer.
	 * @param str String representing an int that must be an int.
	 * @return true if _str argument is a valid int. false if _str argument is a valid int.
	 ****/
	public static boolean isValidInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ne) {
			return false;
		}
	}

	public static boolean isValidFloat(String str) {
		try {
			Float.parseFloat(str.trim());
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isValidDoubleWithDollarSign(String str) {
		boolean isValid = false;
		try {
			if ((!stringIsBlankOrNull(str)) && (str.indexOf('$') > -1)) {
				isValid = isValidDouble(str.substring(str.indexOf('$')+1));
			}
		} catch (Exception ex) {
		}
		return isValid;
	}

	/****
	 * Returns true if the _str String is a valid integer.
	 * @param str String representing an int that must be an int.
	 * @return true if _str argument is a valid int. false if _str argument is a valid int.
	 ****/
	public static boolean isValidDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException ne) {
			return false;
		}
	}

	/**
	 * Returns true if time stamp is valid and in the format of MM/dd/YYYY HH:mm:ss
	 * @param timeStamp String timestamp.
	 * @return boolean true if the timestamp is valid and false otherwise.
	 */
	public static boolean isValidTimeStampGMT(String timeStamp) {
		if (ValidationUtils.stringIsBlankOrNull(timeStamp)) {
			return false;
		}
		String tsCopy = timeStamp.trim();
		if (tsCopy.length() == TIMESTAMP_LENGTH) {
			try {
				int mon = Integer.parseInt(tsCopy.substring(TIMESTAMP_MONTH_START_INDEX, TIMESTAMP_MONTH_END_INDEX));
				int date = Integer.parseInt(tsCopy.substring(TIMESTAMP_DAY_START_INDEX, TIMESTAMP_DAY_END_INDEX));
				int year = Integer.parseInt(tsCopy.substring(TIMESTAMP_YEAR_START_INDEX, TIMESTAMP_YEAR_END_INDEX));
				int hour = Integer.parseInt(tsCopy.substring(TIMESTAMP_HOUR_START_INDEX, TIMESTAMP_HOUR_END_INDEX));
				int min = Integer.parseInt(tsCopy.substring(TIMESTAMP_MINUTE_START_INDEX, TIMESTAMP_MINUTE_END_INDEX));
				int sec = Integer.parseInt(tsCopy.substring(TIMESTAMP_SECOND_START_INDEX, TIMESTAMP_SECOND_END_INDEX));
				GregorianCalendar dateCheck = new GregorianCalendar();
				dateCheck.setLenient(false);
				dateCheck.set(Calendar.DATE, date);
				dateCheck.set(Calendar.MONDAY, (mon - 1));
				dateCheck.set(Calendar.YEAR, year);
				dateCheck.set(Calendar.HOUR_OF_DAY, hour);
				dateCheck.set(Calendar.MINUTE, min);
				dateCheck.set(Calendar.SECOND, sec);
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				df.setLenient(false);
				df.format(dateCheck.getTime());
				return true;
			} catch (Exception ex) {
				return false;
			}
		} else {
			return false;
		}
	}

	/***
	 * Returns true if the int set of _mon , _date , _year are valid.
	 * @param mon int which is valid. [0 to 11]
	 * @param date int which is valid. [1-31 depending on month]
	 * @param year int [1970 - 2300]
	 * @return true if the date is a valid GregorianCalendar date and false otherwise.
	 ***/
	public static boolean isValidDate(int mon, int date, int year) {
		try {
			GregorianCalendar dateCheck = new GregorianCalendar();
			dateCheck.setLenient(false);
			dateCheck.set(Calendar.DATE, date);
			dateCheck.set(Calendar.MONDAY, (mon - 1));
			dateCheck.set(Calendar.YEAR, year);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			df.setLenient(false);
			df.format(dateCheck.getTime());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Sets the date based upon the string....
	 * @param dateStr must be a valid date string in the form of MMDDYYYY MDYYYY M/D/YYYY M/DD/YYYY MM/D/YYYY MM/DD/YYYY
	 * @return true if the date is valid and false otherwise.
	 ***/
	public static Calendar parseDate(String dateStr) {
		if (isValidDate(dateStr)) {
			int month = -1;
			int date = -1;
			int year = -1;
			StringTokenizer tokenizer = new StringTokenizer(dateStr, "/", false);
			String dateStrTrimmed = dateStr.trim();
			if (tokenizer.countTokens() == 1) {
				if (dateStrTrimmed.length() == TIMESTAMP_COMPACT_FORMAT_LENGTH) {
					// Set Month , Date , Year based upon 6 digit string
					month = Integer.parseInt(dateStrTrimmed.substring(0, 1));
					date = Integer.parseInt(dateStrTrimmed.substring(1, 2));
					year = Integer.parseInt(dateStrTrimmed.substring(2));
				} else {
					// Length is 8 Set Month, Date, Year based upon 8 digit string
					month = Integer.parseInt(dateStrTrimmed.substring(TIMESTAMP_COMPACT_FORMAT2_MONTH_START_INDEX,
							TIMESTAMP_COMPACT_FORMAT2_DAY_START_INDEX));
					date = Integer.parseInt(dateStrTrimmed.substring(TIMESTAMP_COMPACT_FORMAT2_DAY_START_INDEX,
							TIMESTAMP_COMPACT_FORMAT2_YEAR_START_INDEX));
					year = Integer.parseInt(dateStrTrimmed.substring(TIMESTAMP_COMPACT_FORMAT2_YEAR_START_INDEX));
				}
			} else {
				// No Exception there are exactly 2 forward slashes that will not be returend.
				int counter = 0;
				while (tokenizer.hasMoreElements()) {
					if (counter == 0) {
						month = Integer.parseInt(tokenizer.nextToken());
					} else if (counter == 1) {
						date = Integer.parseInt(tokenizer.nextToken());
					} else {
						year = Integer.parseInt(tokenizer.nextToken());
					}
					++counter;
				}
			}
			return new GregorianCalendar(year, (month - 1), date);
		}
		return null;
	}

	/**
	 * Verifies that the string argument represents a valid date in the format of MMDDYYYY MDYYYY M/D/YYYY M/DD/YYYY
	 * MM/D/YYYY MM/DD/YYYY
	 * @param dateStr must be a valid date string in the form of MMDDYYYY MDYYYY M/D/YYYY M/DD/YYYY MM/D/YYYY MM/DD/YYYY
	 * @return true if the date is valid and false otherwise.
	 ***/
	public static boolean isValidDate(String dateStr) {
		if (dateStr == null || dateStr.trim().equals("")) {
			return false;
		} else {
			int month = -1;
			int date = -1;
			int year = -1;
			StringTokenizer tokenizer = new StringTokenizer(dateStr, "/", false);
			String dateStrTrimmed = dateStr.trim();
			// 0 tokens
			try {
				if (tokenizer.countTokens() == 1) {
					// If string length is 6 or 8 the date can be valid othrewise it is not.
					if (dateStrTrimmed.length() == TIMESTAMP_COMPACT_FORMAT_LENGTH) {
						// Set Month , Date , Year based upon 6 digit string
						month = Integer.parseInt(dateStrTrimmed.substring(0, 1));
						date = Integer.parseInt(dateStrTrimmed.substring(1, 2));
						year = Integer.parseInt(dateStrTrimmed.substring(2));
					} else if (dateStrTrimmed.length() == TIMESTAMP_COMPACT_FORMAT2_LENGTH) {
						// Length is 8 Set Month, Date, Year based upon 8 digit string
						month = Integer.parseInt(dateStrTrimmed.substring(TIMESTAMP_COMPACT_FORMAT2_MONTH_START_INDEX,
								TIMESTAMP_COMPACT_FORMAT2_DAY_START_INDEX));
						date = Integer.parseInt(dateStrTrimmed.substring(TIMESTAMP_COMPACT_FORMAT2_DAY_START_INDEX,
								TIMESTAMP_COMPACT_FORMAT2_YEAR_START_INDEX));
						year = Integer.parseInt(dateStrTrimmed.substring(TIMESTAMP_COMPACT_FORMAT2_YEAR_START_INDEX));
					} else {
						return false;
					}
				} else if (tokenizer.countTokens() != TIMESTAMP_INVALID_TOKEN_COUNT) {
					return false;
				} else {
					// No Exception there are exactly 2 forward slashes that will not be returend.
					int counter = 0;
					while (tokenizer.hasMoreElements()) {
						if (counter == 0) {
							month = Integer.parseInt(tokenizer.nextToken());
						} else if (counter == 1) {
							date = Integer.parseInt(tokenizer.nextToken());
						} else if (counter == 2) {
							year = Integer.parseInt(tokenizer.nextToken());
						} else {
							// Should never reach this state.
							return false;
						}
						++counter;
					}
				}
				return isValidDate(month, date, year);
			} catch (NumberFormatException ne) {
				return false;
			}
		}
	}

	/**
	 * Returns true if the string _str is of length _length or less
	 * @param str String to be checked for length
	 * @param length length that the string must be equal to or less than
	 * @return true if String _str is of length 0 to _length and false otherwise.
	 */
	public static boolean checkStringLength(String str, int length) {
		return (ValidationUtils.stringIsBlankOrNull(str)) || ((length > -1) && (str.length() <= length));
	}

	/**
	 * Returns true if the string _str is greater than or equal to start length and less than or equal to end length.
	 * @param str String to be checked for length
	 * @param startLength length that the string must be equal to or greater than
	 * @param endLength length that the string must be equal to or greater than
	 * @return true if String _str is of length 0 to _length and false otherwise.
	 */
	public static boolean checkStringLength(String str, int startLength, int endLength) {
		return (checkStringLength(str, endLength) && (str != null) && (str.length() >= startLength));
	}

	/**
	 * Returns the names of all MX Records for an email address domain.
	 * @param domainName String the domain name to be queried.
	 * @return String representing all MX Records for the specified domain name.
	 */
	public static String[] getMXRecords(String domainName) {
		String mxRecords[] = null;
		ArrayList<String> records = new ArrayList<String>();
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
			DirContext ictx = new InitialDirContext(env);
			Attributes attrs3 = ictx.getAttributes(domainName, new String[] { "MX" });
			for (NamingEnumeration<? extends Attribute> namingEnum = attrs3.getAll(); namingEnum.hasMoreElements();) {
				Attribute mxRecord = (Attribute) namingEnum.next();
				for (NamingEnumeration<?> mxValues = mxRecord.getAll(); mxValues.hasMoreElements();) {
					String val = (String) mxValues.nextElement();
					records.add(val);
				}
			}
			mxRecords = new String[records.size()];
			for (int j = 0; j < records.size(); j++) {
				String val = (String) records.get(j);
				mxRecords[j] = val;
			}
		} catch (Exception ex) {
			return null;
		}
		return mxRecords;
	}

	/***
	 * Retrieves the string representing the domain name to be queried.
	 * @param emailAddr String Email Address which needs to be parsed.
	 * @return String representing the domain name of that email address.
	 * **/
	public static String getDomainName(String emailAddr) {
		if (!ValidationUtils.stringIsBlankOrNull(emailAddr) && (emailAddr.indexOf('@') > -1)) {
			return emailAddr.substring((1 + emailAddr.indexOf('@')));
		}
		return null;
	}

	/**
	 * This is a private utility to check and see if an email address is valid or not. If the email address has a valid
	 * domain name, @ symbol in the correct place then true is reeturned. Otherwise false is returned.
	 * @param emailAddress String representing email address.
	 * @return true if email address is valid and false otherwise.
	 */
	public static boolean isValidEmailAddress(String emailAddress) {
		return isValidEmailAddressFormat(emailAddress);
	}

	/**
	 * This is a public utility to check and see if an email address is valid format or not. If the email address has a
	 * valid domain name, @ symbol in the correct place then true is reeturned. Otherwise false is returned.
	 * @param emailAddress String representing email address.
	 * @return true if email address is valid and false otherwise.
	 */
	public static boolean isValidEmailAddressFormat(String emailAddress) {
		boolean isValidEmail = false;
		if (!ValidationUtils.stringIsBlankOrNull(emailAddress)) {
			try {
				//new InternetAddress(emailAddress, true);
				isValidEmail = true;
			} catch (Exception e) {
				//logger.error(e);
			}
		}
		return isValidEmail;
	}

	/**
	 * This is a public utility to check and see if an email address is valid format or not. If the email address has a
	 * valid domain name, @ symbol in the correct place then true is reeturned. Otherwise false is returned.
	 * @param emailAddress String representing email address.
	 * @return true if email address is valid and false otherwise.
	 */
	public static boolean isValidEmailAddressDomain(String emailAddress) {
		boolean isValidEmail = false;
		if (!ValidationUtils.stringIsBlankOrNull(emailAddress)) {
			try {
				//checkForMXRecords(emailAddress);
				isValidEmail = true;
			} catch (Exception e) {
				//logger.error(e);
			}
		}
		return isValidEmail;
	}





	/**
	 * 
	 * @param array
	 * @return
	 */
	public static boolean stringArrayIsNullOrHasNullElements(String[] array) {
		boolean hasNullElements = false;
		if (arrayIsNullOrEmpty(array)) {
			hasNullElements = true;
		} else {
			for (int i = 0; ((i < array.length) && (!hasNullElements)); i++) {
				if (ValidationUtils.stringIsBlankOrNull(array[i])) {
					hasNullElements = true;
				}
			}
		}
		return hasNullElements;
	}


	public static void compareOriginalAndSelectedProducts(List<String> originalList, String selected, List<String> parisKeysToBeDeleted) {
		//logger.debug("in compareOriginalAndSelectedProducts params are: OriginalParisKey{}, selectedParisKey{}: ",new Object[]{originalList,selected});
		
		// create selected list from selected string
		List<String> selectedList = Arrays.asList(GenUtil.getArrayFromString(selected, ","));

		for (String orgProd : originalList) {
			if (!selectedList.contains(orgProd)) {
				parisKeysToBeDeleted.add(orgProd);
			}
		}
	}
}