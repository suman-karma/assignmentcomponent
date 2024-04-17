package io.mhe.assignmentcomponent.vo;

import java.math.BigDecimal;
import java.util.List;

public class NumberUtilities {
	
	private NumberUtilities() {}
	
	private static final double NEGATIVE_1MILLION = -1000000.0;

	public static int compareValues(Double arg0, Double arg1) {
		if (parseDouble(arg0, NEGATIVE_1MILLION) < parseDouble(arg1, NEGATIVE_1MILLION)) {
			return -1;
		} else if (parseDouble(arg0, NEGATIVE_1MILLION) > parseDouble(arg1, NEGATIVE_1MILLION)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static int compareLong(long l1, long l2) {
		if (l1 < l2) {
			return -1;
		} else if (l1 > l2) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static int compareInt(long i1, long i2) {
		if (i1 < i2) {
			return -1;
		} else if (i1 > i2) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static Float parseFloat(Object obj, float defaultValue) {
		float returnValue = defaultValue;
		if (obj != null) {
			if (obj instanceof Float) {
				returnValue = (Float) obj;
			} else if (ValidationUtils.isValidFloat("" + obj)) {
				returnValue = Float.parseFloat("" + obj);
			}
		}
		return returnValue;
	}
    
	public static Integer parseInteger(Object obj, int defaultValue) {
		int returnValue = defaultValue;
		if (obj != null) {
			if (obj instanceof Integer) {
				returnValue = (Integer) obj;
			} else if (obj instanceof Float) {
				returnValue = ((Float) obj).intValue();
			} else if (obj instanceof Double) {
				returnValue = ((Double) obj).intValue();
			} else if (ValidationUtils.isValidLong("" + obj)) {
				returnValue = new Long(Long.parseLong("" + obj)).intValue();
			} else if (ValidationUtils.isValidDouble("" + obj)) {
				returnValue = parseInteger(Double.parseDouble(("" + obj).trim()), defaultValue);
			} else if (ValidationUtils.isValidFloat("" + obj)) {
				returnValue = parseInteger(Float.parseFloat(("" + obj).trim()),defaultValue);
			} else if (ValidationUtils.isValidInt("" + obj)) {
				returnValue = Integer.parseInt(("" + obj).trim());
			}

		}
		return returnValue;
	}
    
	public static Short parseShort(Object obj, short defaultValue) {
		short returnValue = defaultValue;
		if (obj != null) {
			if (ValidationUtils.isValidShort("" + obj)) {
				returnValue = Short.parseShort("" + obj);
			} else if (obj instanceof Integer) {
				returnValue = ((Integer) obj).shortValue();
			} else if (obj instanceof Float) {
				returnValue = ((Float) obj).shortValue();
			} else if (obj instanceof Double) {
				returnValue = ((Double) obj).shortValue();
			} else if (ValidationUtils.isValidLong("" + obj)) {
				returnValue = new Long(Long.parseLong("" + obj)).shortValue();
			} else if (ValidationUtils.isValidDouble("" + obj)) {
				returnValue = parseShort(Double.parseDouble(("" + obj).trim()), defaultValue);
			} else if (ValidationUtils.isValidFloat("" + obj)) {
				returnValue = parseShort(Float.parseFloat(("" + obj).trim()), defaultValue);
			} else if (ValidationUtils.isValidInt("" + obj)) {
				returnValue = Integer.valueOf(Integer.parseInt(("" + obj).trim())).shortValue();
			}
		}
		return returnValue;
	}
    
	public static Long parseLong(Object obj, long defaultValue) {
		long returnValue = defaultValue;
		if (obj != null) {
			if (obj instanceof Long) {
				returnValue = (Long) obj;
			} else if (obj instanceof Float) {
				returnValue = ((Float) obj).longValue();
			} else if (obj instanceof Double) {
				returnValue = ((Double) obj).longValue();
			} else if (ValidationUtils.isValidLong("" + obj)) {
				returnValue = Long.parseLong("" + obj);
			} else if (ValidationUtils.isValidDouble("" + obj)) {
				returnValue = parseLong(Double.parseDouble(("" + obj).trim()), defaultValue);
			} else if (ValidationUtils.isValidFloat("" + obj)) {
				returnValue = parseLong(Float.parseFloat(("" + obj).trim()), defaultValue);
			} else if (ValidationUtils.isValidInt("" + obj)) {
				returnValue = parseLong(Integer.parseInt(("" + obj).trim()), defaultValue);
			}
		}
		return returnValue;
	}
    
	public static Double parseDouble(Object obj, double defaultValue) {
		double returnValue = defaultValue;
		if (obj != null) {
			if (obj instanceof Double) {
				returnValue = (Double) obj;
			} else if (ValidationUtils.isValidDouble("" + obj)) {
				returnValue = Double.parseDouble("" + obj);
			} else if (obj instanceof Float) {
				returnValue = ((Float) obj).doubleValue();
			} else if (obj instanceof Long) {
				returnValue = ((Long) obj).doubleValue();
			} else if (obj instanceof Integer) {
				returnValue = ((Integer) obj).doubleValue();
			} else if (obj instanceof Short) {
				returnValue = ((Short) obj).doubleValue();
			} else if (ValidationUtils.isValidLong("" + obj)) {
				returnValue = Long.parseLong("" + obj);
			}
		}
		return returnValue;
	}
    
    /**
    * Converts an list of Long objects to an array of long primitives
    *
    * @param longList the Long list
    *
    * @return an array of long primitives
    */
	public static long[] toLongArray(List<Long> longList) {
		long[] longArray = new long[longList.size()];
		for (int i = 0; i < longList.size(); i++) {
			longArray[i] = longList.get(i);
		}
		return longArray;
	}
    
  /**
   * The following method rounds off a float to two decimal places
   * 
   * @param num : the float type value to be rounded off
   * @param decimalPlace : the number of decimal points to be rounded off to 
   * */
	public static float roundOffFloat(float num, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(num));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}
}