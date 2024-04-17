/*
 * $Source$
 * $Revision$ $Date$ $Author$ $Date$ GenUtil.java
 * Copyright 2001 The McGraw-Hill Companies. All Rights Reserved Created on Jul 6, 2003, 4:55:19 PM by Kameshwar.
 */

package io.mhe.assignmentcomponent.vo;

/*import com.mhe.assignmentcopy.common.Logger;
import com.mhe.connect.business.common.utils.HashingUtil;
import com.mhe.connect.business.common.utils.ProductUtility;
import com.mhe.connect.business.common.utils.ProductVariables;
import com.mhe.connect.exception.ConnectApplicationException;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;*/

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.json.JsonParseException;
import org.springframework.util.CollectionUtils;

/*import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;*/
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Kameshwar
 * @author yogesh_yadav
 * 
 */
public final class GenUtil {
	private static Logger logger							= LoggerFactory.getLogger(GenUtil.class);
	public static final String				EMPTY_STR						= "";
	private static final SimpleDateFormat	DATE_FORMATTER					= new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat	TIME_FORMATTER					= new SimpleDateFormat("HH:mm:ss");
	private static final String				LDAP_EMAIL_SUFFIX				= "@mcgraw-hill.com";
	private static final String				DATE_FORMAT						= "dd-MMM-yyyy HH:mm:ss";
	private static final int				BUFFER_SIZE						= 1024 * 8;
	private static final int				CONNECTION_DEFAULT_TIMEOUT		= 60 * 1000;
	private static final int				OUTPUT_STREAM_BUFFER_SIZE		= 1024;
	private static final int				SC_WID_NOT_FOUND				= 606;
	private static final int				SC_OK							= 200;
	private static final int				MILLISECONDS					= 1000;
	private static final int				READ_URL_DEFAULT_TIMEOUT		= 2 * 60 * 1000;
	private static final int				READ_URL_BUFFER_SIZE			= 1024 * 2;
	private static final int				SC_NOT_FOUND					= 404;
	private static final int				HEX_MAX_SINGLE_DIGIT			= 0xf;
	private static final int				HALF_BYTE						= 4;
	private static final int				DEFAULT_FLUSH_CACHE_INTERVAL	= 240000;
	private static final int				DEFAULT_EZTO_TIMEOUT			= 5000;
	private static final int				STRING_BUFFER_CAPACITY			= 100;
	private static final int				HOUR_12							= 12;
	private static final int				DATE_FORMAT_DAY_START_INDEX		= 3;
	private static final int				DATE_FORMAT_DAY_END_INDEX		= 5;
	private static final int				DATE_FORMAT_MONTH_START_INDEX	= 0;
	private static final int				DATE_FORMAT_MONTH_END_INDEX		= 2;
	private static final int				DATE_FORMAT_YEAR_START_INDEX	= 6;
	private static final int				STACKTRACE_MAX_RECURSION_COUNT	= 6;
	private static final int				JSON_FORMAT_STRING_BUFFER_SIZE	= 50;
	private static final int				RANDOM_ID_SIZE					= 10;
	private static final int				FIRST_DOUBLE_DIGIT				= 10;
	private static final int				NUMBER_OF_CHARS					= 36;
	private static final int				CHAR_START_INDEX				= 48;
	private static final int				LOWER_CASE_CHAR_START_INDEX		= 97;
	private static final String				TOOL_TITLE						= "tool_title";
	public static final String				TOMCAT							= "tomcat";
	public static final String              EZT_LATE_SUBMISSION_ORGS       = "EZT_LATE_SUBMISSION_ORGS";
	public static final int                 NUMBER_OF_DAYS_TO_ADD           = 365 * 100;

	private GenUtil() {
	}

	public static String getStringValueFromRequest(HttpServletRequest request, String attributeName) {
		if (request == null || attributeName == null) {
			return null;
		} else {
			String returnValue = request.getParameter(attributeName);
			if (returnValue == null) {
				returnValue = request.getAttribute(attributeName) != null ? request.getAttribute(attributeName).toString() : null;
			}
			return returnValue;
		}
	}



	public static final String formatDate(Date in) {
		String ret = DATE_FORMATTER.format(in);
		ret += "T" + TIME_FORMATTER.format(in) + "VV";
		return ret;
	}

	public static final String formatDate(Date date, String pattern) {
		Date localDate = (date == null ? new Date() : date);
		// pattern for example: 03/26/08 10:15AM EST has something like MM/dd/yy hh:mma z
		SimpleDateFormat simpleFormat = new SimpleDateFormat(pattern);
		return simpleFormat.format(localDate);
	}

	public static String postDataToURL(String url, String postData) throws IOException {
		return postDataToURL(url, postData, CONNECTION_DEFAULT_TIMEOUT);
	}

	public static String postDataToURL(String url, String postData, int timeout) throws IOException {
		long starttime = System.currentTimeMillis();
		URL scoringURL = new URL(url);
		HttpURLConnection conn = null;
		StringBuffer sb2 = new StringBuffer();
		int responseCode = 0;
		try {
			// Tell browser to allow me to send data to server.

			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(OUTPUT_STREAM_BUFFER_SIZE);

			// Stream that writes into buffer
			PrintWriter out = new PrintWriter(byteStream, true);

			// Write POST data into local buffer
			out.print(postData);
			// Flush since above used print, not println
			out.flush();
			// POST requests are required to have Content-Length
			String lengthString = String.valueOf(byteStream.size());
			if (logger.isDebugEnabled()) {
				logger.debug("OPEN CONNECTION");
				logger.debug("WRITING BYTE STREAM SIZE " + lengthString);
			}
			conn = (HttpURLConnection) scoringURL.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			logger.debug("EZTO time out ::" + Integer.parseInt(Configuration.getSystemValue("EZTO_READ_TIMEOUT")));
			// Set the socket read time out from configuration
			conn.setReadTimeout(Integer.parseInt(Configuration.getSystemValue("EZTO_READ_TIMEOUT")));
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Referer", GenUtil.getServerURL());
			conn.setRequestProperty("Content-Length", lengthString);
			conn.setConnectTimeout(Integer.parseInt(Configuration.getSystemValue("CONNECTION_TIMEOUT")));

			// Write POST data to real output stream
			if (logger.isDebugEnabled()) {
				logger.debug("URL=" + url);
				logger.debug("POSTDATA=" + postData);
				logger.debug("READ TIMEOUT= " + timeout);
				logger.debug("WRITING TO URL");
			}
			byteStream.writeTo(conn.getOutputStream());

			responseCode = conn.getResponseCode();
			if (logger.isDebugEnabled()) {
				logger.debug("GOT RESPONSE CODE " + responseCode);
			}
			if (responseCode == SC_WID_NOT_FOUND && postData.indexOf("connectForceSubmission") != -1) {
				sb2.append("WID_NOT_FOUND");
				return sb2.toString();
			}
			if (isBlankString(sb2.toString()) && (url.indexOf("pullRegistration") != -1 || postData.indexOf("connectForceSubmission") != -1)) {
				if (responseCode == SC_OK) {
					sb2.append("true");
				} else {
					sb2.append("false");
					logger.error("ForceSubmission RESPONSE CODE {}", responseCode);
				}
				return sb2.toString();
			}
			InputStream in = conn.getInputStream();
			int x = 0;
			byte[] read = new byte[BUFFER_SIZE];
			while ((x = in.read(read)) != -1) {
				sb2.append(new String(read, 0, x));
			}
		} catch (IOException ex) {
			logger.error("GenUtil.postDataToURL(): Total Time in milli seconds :" + "URL:" + url + ",time:"
					+ (System.currentTimeMillis() - starttime));
			logger.error("GenUtil.postDataToURL(): URL=" + url);
			logger.error("GenUtil.postDataToURL(): PostData=" + postData);
			logger.error("responseCode " + responseCode);
			throw ex;
		} finally {
			conn.disconnect();
			long endtime = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug("GenUtil postDataToURL: Start time: " + starttime + " : End time: " + endtime + " Time taken: "
						+ (((float) (endtime - starttime))) / MILLISECONDS + " : sec");
			}
		}
		return sb2.toString();
	}
	public static String getServerURL() {
		return getServerUrlWithSsl();
		
	}
	
	public static String getServerUrlWithSsl() {
		String server;
		if ("Y".equalsIgnoreCase(Configuration.getSystemValue("IS_SSL_ENABLED"))) {
			server = "https://" + Configuration.getSystemValue("host_name");
		} else {
			server = "http://" + Configuration.getSystemValue("host_name");
		}
		logger.debug(" The server requested is {}", new Object[]{server});
		if (Configuration.getSystemValue("host_port").equals("80")) {
			return server;
		} else {
			return server + ":" + Configuration.getSystemValue("host_port");
		}
	}

	
	public static String getWebServerUrl() {
		String webServerUrl = null;
		String httpswebServerURL = Configuration.getSystemValue("HTTPS_WEBSERVER_URL");
		if ("Y".equalsIgnoreCase(Configuration.getSystemValue("IS_SSL_ENABLED"))) {
			webServerUrl = httpswebServerURL;
		} else {
			webServerUrl = Configuration.getSystemValue("HTTP_WEBSERVER_URL");
		}
		return webServerUrl;
	}
	
	public static String readURL(String url) throws IOException {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection httpUrlConnection = null;
		BufferedReader rdr = null;
		try {
			URL readUrl = new URL(url);
			httpUrlConnection = (HttpURLConnection) readUrl.openConnection();

			httpUrlConnection.setConnectTimeout(CONNECTION_DEFAULT_TIMEOUT);
			httpUrlConnection.setReadTimeout(READ_URL_DEFAULT_TIMEOUT);

			int x = 0;
			char[] read = new char[READ_URL_BUFFER_SIZE];
			httpUrlConnection.connect();

			if (httpUrlConnection.getResponseCode() == SC_NOT_FOUND) {
				logger.error("GenUtil.readURL(): URL= " + url);
				logger.error("GenUtil.readURL(): httpUrlConnection.getResponseCode()=404 " + url);
				return "";
			} else if (httpUrlConnection.getURL().toString().indexOf("404.htm") > 1) {
				logger.error("GenUtil.readURL(): URL= " + url);
				logger.error("GenUtil.readURL(): httpUrlConnection.getURL().toString().indexOf(404.htm) > 1 " + url);
				return "";
			} else {
				if (httpUrlConnection.getContentLength() != 0) {
					InputStream in = httpUrlConnection.getInputStream();
					rdr = new BufferedReader(new InputStreamReader(in));
					while ((x = rdr.read(read)) != -1) {
						sb.append(new String(read, 0, x));
					}
				}
			}
		} catch (Exception ex) {
			logger.error("GenUtil.readURL(): URL= " + url);
			//logger.error(ex);
			return "";
		} finally {
			if (rdr != null) {
				rdr.close();
			}
			if (httpUrlConnection != null) {
				httpUrlConnection.disconnect();
			}
		}
		return sb.toString();
	}

	/**
	 * Generic method to return String array from delimiter separated string.
	 * @param a
	 * @param delimiter
	 * @return
	 */
	public static String[] getArrayFromString(String a, String delimiter) {
		if (isBlankString(a)) {
			return (new String[0]);
		}
		StringTokenizer st = new StringTokenizer(a, delimiter);
		ArrayList<String> b = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			b.add(st.nextToken());
		}
		return (String[]) b.toArray(new String[0]);
	}

	public static long[] getlongArrayFromStringArray(String[] arr) {
		if (isBlankArray(arr)) {
			return new long[0];
		}
		long[] longArray = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			longArray[i] = Long.parseLong(arr[i].trim());
		}
		return longArray;
	}

	/**
	 * Generic Method to conevert String Array to List of long.
	 * @param arr
	 * @return
	 */
	public static List<Long> getLongListFromStringArray(String[] arr) {

		if (isBlankArray(arr)) {
			return new ArrayList<Long>();
		}
		List<Long> longList = new ArrayList<Long>(arr.length);
		for (int i = 0; i < arr.length; i++) {
			longList.add(Long.parseLong(arr[i].trim()));
		}
		return longList;
	}

	/**
	 * Generic Method to convert String list to List of Long.
	 * @param
	 * @return
	 */
	public static List<Long> getLongListFromStringList(List<String> strList) {

		if (strList == null || strList.size() == 0) {
			return new ArrayList<Long>();
		}
		return getLongListFromStringArray(strList.toArray(new String[strList.size()]));
	}

	/*
	 * This uapi return the int array for the passed String array .
	 * @params String[] arr return int[] intArray
	 */
	public static int[] getintArrayFromStringArray(String[] arr) {
		if (isBlankArray(arr)) {
			return new int[0];
		}
		int[] intArray = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			intArray[i] = Integer.parseInt(arr[i].trim());
		}
		return intArray;
	}

	public static String getHexDigest(String convertString) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		String msgId = "default";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(convertString.getBytes());
			byte res[] = md5.digest();
			msgId = "";
			for (int k = 0; k < res.length; k++) {
				int a = res[k] >> HALF_BYTE & HEX_MAX_SINGLE_DIGIT;
				int b = res[k] & HEX_MAX_SINGLE_DIGIT;
				msgId = msgId + hexDigit[a] + "" + hexDigit[b];
			}

		} catch (NoSuchAlgorithmException e) {
			logger.error("Exception occured : ", e);
		}
		return msgId;
	}

	public static boolean isNumber(String value) {
		boolean isNumber = true;
		try {
			Long.parseLong(value);
		} catch (Exception ex) {
			isNumber = false;
			return isNumber;
		}
		return isNumber;
	}

	public static String substitute(String original, String bad, String good) {
		if (original == null) {
			return "";
		}
		if (original.indexOf(bad) < 0) {
			return original;
		}
		String orgStrlocalRef = original;
		StringBuffer orig = new StringBuffer("");

		int position = orgStrlocalRef.indexOf(bad);
		while (position >= 0) {
			orig.append(orgStrlocalRef.substring(0, position));
			orig.append(good);
			orgStrlocalRef = orgStrlocalRef.substring(position + bad.length());

			position = orgStrlocalRef.indexOf(bad);
		}

		orig.append(orgStrlocalRef);

		return orig.toString();
	}

	public static boolean isBlankString(String str) {
		if (str == null || "".equals(str.trim()) || str.equals("null")) {
			return true;
		}

		return false;
	}

	public static boolean isBlankArray(Object[] arr) {
		if (arr == null || arr.length == 0) {
			return true;
		}

		return false;
	}
/*
	public static String determineLoginPage(String prodId, String loginPageParm, String serverName, String serverPort, String additionalParms) {
		String loginPage = loginPageParm;
		if (GenUtil.isBlankString(loginPage)) {
			if (ProductUtility.getInstance().isBrandMatch(prodId, ProductVariables.BRAND_MATHZONE)) {
				loginPage = "https://www.mathzone.com";
			} else if (ProductUtility.getInstance().isBrandMatch(prodId, ProductVariables.BRAND_ARIS)) {
				loginPage = "https://www.mharis.com";
			} else {
				String webServerUrl = null;
				if ("Y".equalsIgnoreCase(Configuration.getSystemValue("IS_SSL_ENABLED"))) {
					webServerUrl = Configuration.getSystemValue("HTTPS_WEBSERVER_URL");
				} else {
					webServerUrl = Configuration.getSystemValue("HTTP_WEBSERVER_URL");
				}
				loginPage = webServerUrl + "/connect/shortUrl.do?" + additionalParms;
			}

		} else if (!loginPage.startsWith("http://")) {
			if (loginPage.startsWith("/")) {
				loginPage = loginPage.substring(1, loginPage.length());
			}

			if (loginPage.indexOf('?') != -1) {
				if (serverPort != null && "80".equalsIgnoreCase(serverPort)) {
					loginPage = "https://" + serverName + "/" + loginPage + "&" + additionalParms;
				} else {
					loginPage = "https://" + serverName + ":" + serverPort + "/" + loginPage + "&" + additionalParms;
				}
			} else {
				if (serverPort != null && "80".equalsIgnoreCase(serverPort)) {
					loginPage = "https://" + serverName + "/" + loginPage + "?" + additionalParms;
				} else {
					loginPage = "https://" + serverName + ":" + serverPort + "/" + loginPage + "?" + additionalParms;
				}
			}

		}

		return loginPage;
	}
*/
	// Only LDAP user id is non-numberic and has _ in it such as yong_chen
	public static boolean isLDAPUser(String userId) {

		if (userId.toLowerCase().endsWith(LDAP_EMAIL_SUFFIX)) {
			return true;
		} else if (userId.indexOf('@') != -1) {
			return false;
		}
		return userId.indexOf('_') > 0;

	}

	// Retrieve firstName from userId
	public static String getLDAPUserFirstName(String userId) {
		String localUserId = userId;
		if (isLDAPUser(localUserId)) {
			// removing @from LDAP if exists. Praveen on 17th March 08
			if (localUserId.indexOf('@') != -1) {
				localUserId = localUserId.substring(0, localUserId.indexOf('@'));
			}
			int g = localUserId.indexOf('_');
			return localUserId.substring(0, g);
		} else {
			return "first name";
		}
	}

	// //Retrieve lastName from userId
	public static String getLDAPUserLastName(String userId) {
		String localUserId = userId;
		if (isLDAPUser(localUserId)) {
			// removing @from LDAP if exists. Praveen. 17th March 08
			if (localUserId.indexOf('@') != -1) {
				localUserId = localUserId.substring(0, localUserId.indexOf('@'));
			}
			int g = localUserId.indexOf('_');
			return localUserId.substring(g + 1, localUserId.length());
		} else {
			return "last name";
		}
	}

	public static String getLDAPUserId(String userId) {
		if (isLDAPUser(userId)) {
			return (getLDAPUserFirstName(userId) + "_" + getLDAPUserLastName(userId)).toLowerCase();
		}
		return null;
	}

	public static void addStringArrayToList(String[] array, List list) {
		if (array != null && array.length > 0 && list != null) {
			for (int i = 0; i < array.length; i++) {
				list.add(array[i]);
			}
		}
	}

	/**
	 * @param
	 */
	public static void logStackTraceAsError(Logger logger, Exception exception) {
		final StringWriter stacksw = new StringWriter();
		exception.printStackTrace(new PrintWriter(stacksw));
		//logger.error(stacksw);
	}

	public static boolean notifyEZTest() {
		// Get The Server name and Make the Url .
		HttpURLConnection con = null;
		try {
			logger.debug("ServiceUrl is" + Configuration.getSystemValue("startup_service_url"));

			String interval = Configuration.getSystemValue("FLUSH_CACHE_TIMEOUT");
			if (logger.isDebugEnabled()) {
				logger.debug("Ping Interval from Config " + interval + " Seconds");
			}
			int flushCacheInterval = DEFAULT_FLUSH_CACHE_INTERVAL;
			try {
				flushCacheInterval = Integer.parseInt(interval) * MILLISECONDS;
			} catch (NumberFormatException e) {
				logger.error("GenUtil.notifyEZTest(): ERROR: FLUSH_CACHE_INTERVEL " + flushCacheInterval);
				logger.error("GenUtil.notifyEZTest(): Unable to retrieve FLUSH_CACHE_INTERVEL from configuration : setting as 4 min");
			}

			StringBuffer notifyUrl = new StringBuffer(Configuration.getSystemValue("eztest.hm.internal.server.url"));
			notifyUrl.append("?todo=flushCache");
			String md5String = ""; //xxx to update HashingUtil.getDigest("todo=flushCache", Configuration.getSystemValue("eztest.pass.phrase"));
			notifyUrl.append("&key=");
			notifyUrl.append(md5String);
			notifyUrl.append("&Referer=");
			notifyUrl.append(URLEncoder.encode(GenUtil.getServerURL(), "UTF-8"));
			logger.debug("GenUtil.notifyEZTest(): The notify_url is " + notifyUrl);
			// Got the Url . Now make the HTTP Connection to the url and read the response code.
			String httpUrl = notifyUrl.toString();
			URL url = new URL(httpUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(flushCacheInterval);
			con.setReadTimeout(flushCacheInterval);

			int response = con.getResponseCode();
			con.disconnect();
			logger.debug("GenUtil.notifyEZTest(): the Response Code is " + response);
			return true;
		} catch (Exception e) {
			// Just Log the Exception and return as False
			logger.error("GenUtil.notifyEZTest(): Error Occurred" + e);
			logger.error(e.getMessage(), e.getCause());
			con.disconnect();
			return false;
		} finally {
			con = null;
		}
	}

	/**
	 * This method is used to access URLs for performing certain functionality.
	 * @param paramsMap collection map containing all input parameters (key/value) within. Parameter key requestPath is
	 * mandatory - path of the action to be invoked. Parameter key nodeServers is mandatory - list of node servers in
	 * cluster Parameter key className (value with package names) and key methodName is mandatory if requestPath is
	 * UpdateCacheAction - dynamic invocation of objects using reflections Other optional parameters key include support
	 * using query string (with any key/value)
	 */
	public static void accessNodeUrls(Map paramsMap) {
		logger.debug("[GenUtil] [accessNodeUrls] [paramsMap] =>" + paramsMap);

		// Mandatory parameters. Example: classware/hmUpdateCacheAction.do
		String requestPath = (String) paramsMap.get("requestPath");
		String nodeServers = (String) paramsMap.get("nodeServers");

		String queryStr = "";
		Set set = paramsMap.keySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = (String) paramsMap.get(key);
			// if key is requestPath or nodeServers do not append it to query string
			if (key != null && !key.equalsIgnoreCase("requestPath") && !key.equalsIgnoreCase("nodeServers")) {
				try {
					queryStr = queryStr + key + "=" + URLEncoder.encode(value, "UTF-8") + "&";
				} catch (UnsupportedEncodingException e) {
					logger.error("[GenUtil] [accessNodeUrls] [UnsupportedEncodingException]" + e);
				}
			}
		}
		logger.debug("[GenUtil] [accessNodeUrls] [queryStr]=>" + queryStr);

		if (!GenUtil.isBlankString(nodeServers)) {
			List servers = getServerList(nodeServers);
			logger.debug("[GenUtil] [accessNodeUrls] [servers]=>" + servers);
			if (servers.size() > 0) {
				// First call to connect to URL of first node server
				connectServer(servers, 0, requestPath, queryStr);
			}
		}
	}

	/**
	 * This method is used to connect to the server nodes. Even If one or more server node fails, it will try connecting
	 * to other server nodes part of the list.
	 * @param servers - list of all node servers in cluster
	 * @param serverIndex - start index of the node server to start iterating from
	 * @param requestPath - path of the action to be invoked
	 * @param queryStr - request parameters as query string
	 */
	private static void connectServer(List servers, int serverIndex, String requestPath, String queryStr) {
		HttpURLConnection conn = null;
		int localServerIndex = serverIndex;
		try {
			// starting from the index mentioned, iterate through all other nodes
			for (int i = localServerIndex; i < servers.size(); i++) {
				String remoteRequest = (String) servers.get(i) + (requestPath != null ? requestPath : "") + "?" + queryStr;
				logger.debug("[GenUtil] [connectServer] [remoteRequest] =>" + remoteRequest);
				HttpURLConnection.setFollowRedirects(true);
				URL url = new URL(remoteRequest);
				conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int code = conn.getResponseCode();
				logger.debug("[GenUtil] [connectServer] [code] =>" + code);
				// request has succeeded
				if (code == SC_OK) {
					conn.disconnect();
				}
			}
		} catch (MalformedURLException e) {
			logger.error("[GenUtil] [accessNodeUrls] [Exception] [For server index] =>" + localServerIndex + " [MalformedURLException] =>" + e);
			localServerIndex = localServerIndex + 1;
			if (localServerIndex < servers.size()) {
				// calls for other nodes in case of one node getting failed
				connectServer(servers, localServerIndex, requestPath, queryStr);
			}
		} catch (IOException e) {
			logger.error("[GenUtil] [accessNodeUrls] [Exception] [For server index] =>" + localServerIndex + " [IOException] =>" + e);
			localServerIndex = localServerIndex + 1;
			if (localServerIndex < servers.size()) {
				// calls for other nodes in case of one node getting failed
				connectServer(servers, localServerIndex, requestPath, queryStr);
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
	}

	/**
	 * This method is used to get the servers list based on the node servers configured in cluster.
	 * @param nodeServers - comma separated node server names from properties file.
	 * @return nodeServersList - an array list containing all node servers.
	 */
	private static List getServerList(String nodeServers) {
		StringTokenizer st = new StringTokenizer(nodeServers, ",");
		List nodeServersList = new ArrayList();
		while (st.hasMoreTokens())
		{
			nodeServersList.add(st.nextToken().trim());
		}
		return nodeServersList;
	}

	public static boolean pingEZTO() {
		int timeout = DEFAULT_EZTO_TIMEOUT;
		String url = Configuration.getSystemValue("ezt_pollurl");
		logger.debug("Ping EZTO : url = " + url + " : timeout = " + timeout);
		try {
			postDataToURL(url, "", timeout);
			return true;
		} catch (Exception e) {
			logger.error("Ping EZTest server failed : URL = " + url + " : Timeout = " + timeout + "ms");
			//logger.error(e);
			return false;
		}
	}

	public static List<String> getListFromTokenSeparatedString(String s, String token) {
		List<String> list = new ArrayList<String>();
		if (s == null || "".equals(s.trim())) {
			return list;
		}
		String[] strArr = s.split(token);
		return Arrays.asList(strArr);
	}

	public static String getCommaSeperatedString(String[] ids) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(ids[i]);
		}
		return sb.toString();
	}

	public static String getCommaSeperatedStringFromLongArray(long[] ids) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(ids[i]);
		}
		return sb.toString();
	}

	public static <T> String getCommaSeperatedStringsFromList(List<T> ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(ids.get(i).toString());
		}
		return sb.toString();
	}

	public static <T> String getTokenSeperatedStringsFromList(List<T> ids, String token) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; ids != null && i < ids.size(); i++) {
			if (i != 0) {
				sb.append(token);
			}
			sb.append(ids.get(i).toString());
		}
		return sb.toString();
	}

	/**
	 * retuns a comma separated String from passed list of String with token appended before and after each elements e.g
	 * if token passed is single quote then the return value would be like 'e1','e2'
	 * @param list
	 * @param token
	 * @return
	 */
	public static String getCommaSeperatedStringsFromList(List<String> list, String token) {
		StringBuffer sb = new StringBuffer(STRING_BUFFER_CAPACITY);
		for (int i = 0; list != null && i < list.size(); ++i) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(token);
			sb.append(list.get(i));
			sb.append(token);
		}
		return sb.toString();
	}

	/**
	 * This method takes Set as a input with token and returns comma separated string as output.
	 * @param set
	 * @param token
	 * @return
	 */
	public static String getCommaSeperatedStringsFromSet(Set<String> set, String token) {
		StringBuffer sb = new StringBuffer(STRING_BUFFER_CAPACITY);
		int cnt = 0;
		for (String str : set) {
			if (cnt++ > 0) {
				sb.append(",");
			}
			sb.append(token);
			sb.append(str);
			sb.append(token);

		}
		return sb.toString();
	}

	/**
	 * Method to return a comma separated string for the given collection of elements. Start token will be prepended and
	 * End token will be appended to the collection element. <br/>
	 * 
	 * @param collection Collection of objects
	 * @param startToken Token to be used before each element
	 * @param endToken Token to be used after each element
	 * @return the comma separated string
	 */
	public static <T> String getCommaSeperatedStringFromCollection(final Collection<T> collection,
			String startToken, String endToken) {
		String comma = ", ";
		StringBuilder sb = new StringBuilder();
		if (!isNull(collection)) {
			for (T element : collection) {
				sb.append(startToken).append(element.toString()).append(endToken).append(comma);
			}
			sb.setLength(sb.length() - comma.length());
		}
		return sb.toString();
	}

	public static String getDateInCorrectFormat(String date, String hour, String minute) {
		return GenUtil.formatDate(getDate(date, hour, minute));
	}
	
	public static String getLateSubmissionDateInCorrectFormat(String date, String hour, String minute) {
		return GenUtil.formatDate(getLateSubmissionDate(date, hour, minute));
	}

	public static String get24Hour(String hour, String meridian) {
		int time = 0;
		try {
			time = Integer.parseInt(hour);

			if (meridian.equalsIgnoreCase("pm")) {
				if (time < HOUR_12) {
					time = Integer.parseInt(hour) + HOUR_12;
				}
			}
			else if (meridian.equalsIgnoreCase("am") && time == HOUR_12) {
				time = 0;
			}
		} catch (Exception e) {
			logger.error(" Exception in converting hour:" + hour + " meridian:" + meridian, e);
		}
		return "" + time;
	}

	public static Date getDate(String dateToParse, String hour, String minute) {
		Date date;
		Date parsedDate;

		if (StringUtils.isBlank(dateToParse) || StringUtils.isBlank(hour) || StringUtils.isBlank(minute)) {
			date = new Date();
		} else {
			try {

				SimpleDateFormat sdFormat = new SimpleDateFormat("MM/dd/yyyy");
				parsedDate = sdFormat.parse(dateToParse);
				String formatedstartDate = sdFormat.format(parsedDate);
				logger.debug(dateToParse + " Date formated in mm/dd/yyyy " + formatedstartDate);
				int sDt = Integer.parseInt(formatedstartDate.substring(DATE_FORMAT_DAY_START_INDEX, DATE_FORMAT_DAY_END_INDEX));
				int sMt = Integer.parseInt(formatedstartDate.substring(DATE_FORMAT_MONTH_START_INDEX, DATE_FORMAT_MONTH_END_INDEX)) - 1;
				int sYr = Integer.parseInt(formatedstartDate.substring(DATE_FORMAT_YEAR_START_INDEX));
				int hr = Integer.parseInt(hour);
				int min = Integer.parseInt(minute);
				Calendar cG = new GregorianCalendar(sYr, sMt, sDt, hr, min, 0);
				date = cG.getTime();

			} catch (ParseException e) {
				logger.error("Parsing Exception:", e);
				date = new Date();
			}
		}
		return date;
	}
	
	public static Date getLateSubmissionDate(String dateToParse, String hour, String minute) {
		Date date;
		Date parsedDate;

		if (StringUtils.isBlank(dateToParse) || StringUtils.isBlank(hour) || StringUtils.isBlank(minute)) {
			date = new Date();
		} else {
			try {

				SimpleDateFormat sdFormat = new SimpleDateFormat("MM/dd/yyyy");
				parsedDate = sdFormat.parse(dateToParse);
				String formatedstartDate = sdFormat.format(parsedDate);
				logger.debug(dateToParse + " Date formated in mm/dd/yyyy " + formatedstartDate);
				int sDt = Integer.parseInt(formatedstartDate.substring(DATE_FORMAT_DAY_START_INDEX, DATE_FORMAT_DAY_END_INDEX));
				int sMt = Integer.parseInt(formatedstartDate.substring(DATE_FORMAT_MONTH_START_INDEX, DATE_FORMAT_MONTH_END_INDEX)) - 1;
				int sYr = Integer.parseInt(formatedstartDate.substring(DATE_FORMAT_YEAR_START_INDEX));
				int hr = Integer.parseInt(hour);
				int min = Integer.parseInt(minute);
				Calendar cG = new GregorianCalendar(sYr, sMt, sDt, hr, min, 0);
				date = cG.getTime();

			} catch (Exception e) {
				logger.error("[GenUtil] [getDate] Parsing Exception:", e);
				try {
					//First try to set date to 2100.01.01.01.00.00
					String defaultEndDateStr = Configuration.getSystemValue("hm.assignment.default.enddate");
					String[] defaultEndDateArr = StringUtils.split(defaultEndDateStr, ".");
					logger.debug("[GenUtil] [getDate] defaultEndDateStr: {}", defaultEndDateStr);
					date = DateTimeSupport.getFutureDateInDefaultTimeZone( Integer.parseInt(defaultEndDateArr[0]), Integer.parseInt(defaultEndDateArr[1]),
							Integer.parseInt(defaultEndDateArr[2]), Integer.parseInt(defaultEndDateArr[3]), Integer.parseInt(defaultEndDateArr[4]),
							Integer.parseInt(defaultEndDateArr[5]) );
					logger.debug("[GenUtil] [getDate] future date using hm.assignment.default.enddate: {}", date);
				} catch (Exception ex) {
					logger.error("[GenUtil] [getDate] Exception occurred : ", ex);
					//if the previous approach also fails then add 100 years from current datetime and then assign
					date = DateTimeSupport.getFutureDateInDefaultTimeZone( NUMBER_OF_DAYS_TO_ADD );
					logger.debug("[GenUtil] [getDate] future date using NUMBER_OF_DAYS_TO_ADD: {}, date: {}", NUMBER_OF_DAYS_TO_ADD, date);
				}
				
			}
		}
		return date;
	}

	public static Date getDefaultDate(String timezone, boolean startDate) {
		String configDate = null;
		Date defaultDate = null;
		if (startDate) {
			configDate = Configuration.getSystemValue("hm.assignment.default.startdate");
		}
		else {
			configDate = Configuration.getSystemValue("hm.assignment.default.enddate");
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			TimeZone tz = DateUtil.DEFAULT_TIMEZONE;
			formatter.setTimeZone(tz);
			defaultDate = formatter.parse(configDate);
			defaultDate = DateUtil.format(defaultDate, timezone);
			if (logger.isDebugEnabled()) {
				logger.debug("Default date = " + defaultDate);
			}
		} catch (Exception e) {
			logger.error("Exception in setting defaultDate " + e);
		}
		return defaultDate;
	}
	
	/**
	 * @comment This method is used to return the default due date for Avalon type assignment
	 * @param timezone
	 * @param startDate
	 * @return
	 */
	public static String getDefaultDateForAvalon(String timezone, boolean startDate) {
		String configDate = null,defaultDateinString=null;
		Date defaultDate = null;
		if (startDate) {
			configDate = Configuration.getSystemValue("hm.assignment.default.startdate");
		}
		else {
			configDate = Configuration.getSystemValue("hm.assignment.default.enddate");
		}
		try {
			defaultDateinString = DateUtil.converDateFormat(DateUtil.APX_DATE_FORMAT,timezone,configDate); 
			if (logger.isDebugEnabled()) {
				logger.debug("Default date = " + defaultDateinString);
			}
		} catch (Exception e) {
			logger.error("Exception in setting defaultDate " + e);
		}
		return defaultDateinString;
	}

	public static boolean isEqual(String str1, String str2) {
		if (str1 == null || str2 == null) {
			return false;
		} else {
			return str1.equals(str2);
		}
	}

	/**
	 * Checking the equality of Two String ignoring the cases
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqualIgnoreCase(String str1, String str2) {
		if (str1 == null || str2 == null) {
			return false;
		} else {
			return str1.equalsIgnoreCase(str2);
		}
	}


	public static boolean isNull(Collection<?> collection) {
		return !(collection != null && collection.size() > 0);
	}

	public static String getForceGradeSubmissionURL(long assignmentId, String pollerJobName) {
		String url;
		if ("Y".equalsIgnoreCase(Configuration.getSystemValue("IS_SSL_ENABLED"))) {
			url = "https://" + Configuration.getSystemValue("host_name");
		} else {
			url = "http://" + Configuration.getSystemValue("host_name");
		}
		if (!Configuration.getSystemValue("host_port").equals("80")) {
			url += ":" + Configuration.getSystemValue("host_port");
		}
		
		url += "/connect/eztoForceSubmit.do?assignmentId=" + assignmentId;

		if (!GenUtil.isBlankString(pollerJobName))
		{
			url += "&poller=" + pollerJobName;
		}

		String encodedUrl = null;
		try {
			encodedUrl = URLEncoder.encode(url, "UTF-8");
			// double encoding is required to get back the parameters from ezto
			encodedUrl = URLEncoder.encode(encodedUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception in getForceGradeSubmissionURL", e);
		}
		return encodedUrl != null ? encodedUrl : url;
	}

	public static String getEscapedName(String name) {
		String regex = "(\\\\)([.,:;?/!~`_\\(\\)\\{\\}+=\\-\\\\|<>@%#$^&*\\[\\]])";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		return matcher.replaceAll("$2");
	}

	/**
	 * <p>
	 * This method is used for encoding string into html entity code.
	 * </p>
	 * @param input
	 * @return
	 */
	public static String buildHtmlEntityCode(String input) {
		StringBuffer output = new StringBuffer(input.length() * 2);

		int len = input.length();
		int code;
		char ch;
		/*
		for (int i = 0; i < len; i++) {
			code = input.codePointAt(i);
			ch = (char) code;

			if (CharUtils.isAsciiAlphanumeric(ch)) {
				output.append(ch);
			}
			else {
				output.append("&#" + code + ";");
			}
		}
			}
		 */
		return output.toString();
	}
	
	public static String buildHtmlEntityCode(String strVal, boolean handleBackSlashAndDoubleQuote) {
		// Replace special characters with html entity codes
		String replacedStr = buildHtmlEntityCode(strVal);
		
		if(handleBackSlashAndDoubleQuote){
			// Prepend backslash entity code for backslash entity code
			replacedStr = replacedStr.replace("&#92;", "&#92;&#92;");
			
			// Prepend backslash entity code for double quote entity code
			replacedStr = replacedStr.replace("&#34;", "&#92;&#34;");
		}
		return replacedStr;
	}	

	public static final String getDateInCorrectFormat(
			final String completeDate, final String completeTime) {
		return formatDate(getDate(completeDate, completeTime));
	}

	public static Date getDate(final String completeDate, final String completeTime) {
		Date date;
		if (completeDate.equals("mm/dd/yyyy")) {
			date = new Date();
		} else {
			String[] sepDate = completeDate.split("/");
			String[] sepTime = completeTime.split(":");
			int sDt = Integer.parseInt(sepDate[1]);
			int sMt = Integer.parseInt(sepDate[0]) - 1;
			int sYr = Integer.parseInt(sepDate[2]);
			int hr = Integer.parseInt(sepTime[0]);
			int min = Integer.parseInt(sepTime[1]);
			date = new GregorianCalendar(sYr, sMt, sDt, hr, min).getTime();
		}
		return date;
	}

	public static String getStackTraceFromException(Exception e) {
		StringBuffer message = new StringBuffer();
		message.append(e.toString());
		message.append("\n");

		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++) {
			message.append("\tat " + trace[i] + "\n");
		}
		Throwable ourCause = e.getCause();
		if (ourCause != null) {
			getStackTraceAsCause(0, message, trace, e);
		}
		return message.toString();
	}

	private static void getStackTraceAsCause(int count, StringBuffer buf, StackTraceElement[] causedTrace, Exception e) {
		StackTraceElement[] trace = e.getStackTrace();
		// introducing this to avoid stackOverflow Error ; recursing to 5 levels at most
		int recursionCount = count + 1;

		int m = trace.length - 1, n = causedTrace.length - 1;
		while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n])) {
			m--;
			n--;
		}
		int framesInCommon = trace.length - 1 - m;

		buf.append("Caused by: ");
		buf.append(e.toString());
		for (int i = 0; i <= m; i++) {
			buf.append("\tat " + trace[i] + "\n");
		}
		if (framesInCommon != 0) {
			buf.append("\t... " + framesInCommon + " more");
		}
		// Recurse if we have a cause
		Throwable ourCause = e.getCause();
		if (ourCause != null && recursionCount < STACKTRACE_MAX_RECURSION_COUNT) {
			getStackTraceAsCause(recursionCount, buf, trace, e);
		}

	}

	// For getting current date with time zone from course time zone.
	public static Date getCurrentDateWithTimeZone(String courseTimeZone) throws ParseException {
		String localCourseTimeZone = courseTimeZone;
		if (GenUtil.isBlankString(localCourseTimeZone)) {
			localCourseTimeZone = DateUtil.DB_TIMEZONE_ID;
		}
		TimeZone tz = TimeZone.getTimeZone(localCourseTimeZone);
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		format.setTimeZone(tz);
		Date currentDate = new Date();
		Date postedDate = format.parse(format.format(currentDate));

		String dateInESTStr = DateUtil.formatToDefaultTimeZone(postedDate);
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.parse(dateInESTStr);
	}

	/**
	 * This method is used to split the param which is separated by the token.
	 * @param parameter
	 * @param token
	 * @return
	 */
	public static String[] splitParameter(String parameter, String token) {
		String[] param = new String[1];
		param = parameter.split(token);
		return param;
	}

	/**
	 * For a given property and Map of key/vlaue this method returns the json type format String.
	 * @param prop
	 * @param elements
	 * @return
	 */
	/*public static String getJsonFormat(String prop, Map<String, String> elements) {
		StringBuffer sb = new StringBuffer(JSON_FORMAT_STRING_BUFFER_SIZE);
		sb.append("\"" + prop + "\":{");
		String key = null, value = null;
		Iterator<Entry<String, String>> itr = elements.entrySet().iterator();
		int size = elements.size(), count = 0;
		while (itr.hasNext()) {
			Entry<String, String> entry = itr.next();
			key = entry.getKey();
			value = entry.getValue();
			sb.append(JSONObject.quote(key)).append(":");
			sb.append(JSONObject.quote(value));
			++count;
			if (count < size) {
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}
*/
	/**
	 * Create Json object for given map of elements
	 * @param
	 * @return
	 */
	/*public static String getJsonFormat(Map<String, String> elements) {
		StringBuffer sb = new StringBuffer(JSON_FORMAT_STRING_BUFFER_SIZE);
		sb.append("{");
		String key = null, value = null;
		Iterator<Entry<String, String>> itr = elements.entrySet().iterator();
		int size = elements.size();
		int count = 0;
		while (itr.hasNext()) {
			Entry<String, String> entry = itr.next();
			key = entry.getKey();
			value = entry.getValue();
			sb.append(JSONObject.quote(key));
			sb.append(":");
			sb.append(JSONObject.quote(value));
			++count;
			if (count < size) {
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}*/
	
	/**
	 * This method is used to check whether given Object is null or not
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNull(Object obj) {

		boolean result = false;

		if (obj == null) {
			result = true;
		}
		return result;
	}

	public static Date getDateAfterGivenDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	public static String[] getStringArrayFromList(List<Long> list) {
		Long[] arr = (Long[]) list.toArray(new Long[list.size()]);

		if (isBlankArray(arr)) {
			return new String[0];
		}
		String[] sArray = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			sArray[i] = arr[i].toString();
		}
		return sArray;
	}

	public static String getValueFromSession(HttpServletRequest request, String key) {
		if (request == null || key == null || "".equals(key)) {
			return null;
		}
		return (String) request.getSession().getAttribute(key);
	}
	
	public static final String getValueFromReqOrSession(HttpServletRequest req, String key, String defaultValue){
		String val = getStringValueFromRequest(req, key);
		if(val == null){
			val = getValueFromSession(req, key);
		}
		if(val == null){
			return defaultValue;
		}
		return val;
	}

	/**
	 * It returns the toString implementation of Object array, provided Object has implemented the toString method in
	 * readable format.
	 * @param tArr
	 * @return
	 */
	public static final <T> String getStringOutOfArray(T[] tArr) {
		if (tArr == null || tArr.length == 0) {
			return "Array is empty or null";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (T t : tArr) {
			sb.append(t.toString()).append("\n");
		}
		sb.append("}\n");
		return sb.toString();
	}

	/**
	 * if string is null, it returns empty String, else it trims the string and returns.
	 * @param s
	 * @return
	 */
	public static String getNotNullString(String s) {
		if (s == null) {
			return EMPTY_STR;
		}
		return s.trim();
	}

	/**
	 * This utility method is responsible for encoding the value of given field(s), from the elements of given list.
	 * This method takes a list of object and a list of field names of that object, on which we have to perform
	 * encoding. using reflection, method is getting value of that field and values is being encoded and again being set
	 * in the field. In case, we have to perform encoding on list(let say- List<String>)element,instead of any of its
	 * field,is also been taken care.
	 ** @param lst
	 * @param classFieldNames
	 */
	public static <T> void encodeList(List<T> lst, List<String> classFieldNames) {

		try {
			List<T> tempList = new ArrayList<T>();
			Iterator<T> iter = lst.iterator();

			while (iter.hasNext()) {
				T t = iter.next();
				if (classFieldNames != null && classFieldNames.size() > 0) {

					for (String classFieldName : classFieldNames)
					{
						Field f = t.getClass().getDeclaredField(classFieldName);
						f.setAccessible(true);
						if (f.getType().equals(String.class))
						{
							f.set(t, URLEncoder.encode(f.get(t).toString(), "UTF-8"));
						}
					}
				} else {
					if (t.getClass().equals(String.class))
					{
						T temp = (T) URLEncoder.encode(t.toString(), "UTF-8").toString();
						iter.remove();
						tempList.add(temp);
					}

				}
			}
			lst.addAll(tempList);

		} catch (Exception e) {
			logger.error("Error occured while encoding");
		}

	}

	/**
	 * This method will give 10 digit Alpha numeric number
	 */
	public static String getRandamAlphaNum() {
		final int idSize = RANDOM_ID_SIZE;
		final int numOfChars = NUMBER_OF_CHARS;
		StringBuffer id = new StringBuffer();
		Random r = new Random();

		int index = 0;
		int x = 0;

		while (x < idSize) {
			index = r.nextInt(numOfChars);
			if (index < FIRST_DOUBLE_DIGIT) {
				id.append((char) (CHAR_START_INDEX + index));
			}
			else if (FIRST_DOUBLE_DIGIT <= index && index < NUMBER_OF_CHARS) {
				index = index - FIRST_DOUBLE_DIGIT;
				id.append((char) (LOWER_CASE_CHAR_START_INDEX + index));
			}
			x++;
		}

		return id.toString();
	}

	public static boolean strCompareWithNullCheck(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		else if (s1 != null && s1.equals(s2)) {
			return true;
		}
		else if (s2 != null && s2.equals(s1)) {
			return true;
		}
		return false;

	}

	public static long[] getlongArrayFromLongList(List<Long> arrList) {
		if (arrList.isEmpty()) {
			return new long[0];
		}
		long[] longArray = new long[arrList.size()];
		for (int i = 0; i < arrList.size(); i++) {
			longArray[i] = arrList.get(i);
		}
		return longArray;
	}

	public static String[] splitParameter(String parameter) {
		String[] param = null;
		if (parameter != null) {
			param = parameter.split("_");
		}
		return param;
	}
	
	public static String removeSpecialCharacters(String str) {
		String pattern = "([^\\w-])";
		str = str.replaceAll(pattern, "");
		return str;
	}
	
	public static <T> Set<T> toSet(T[] array){
		Set<T> set = new HashSet<T>();
		if(null != array){
			for (T eachArrayElement : array) {
				set.add(eachArrayElement);
			}
		}
		return set;
	}
	
	public static  Set<Long> toLongSet(String[] array){
		Set<Long> set = new HashSet<Long>();
		if(null != array){
			for (String eachArrayElement : array) {
				set.add(Long.parseLong(eachArrayElement));
			}
		}
		return set;
	}
	
	public static <T> String[] toStringArray(Collection<T> collection){
		if(CollectionUtils.isEmpty(collection)){
			return new String[0];
		}else{
			String[] strArray = new String[collection.size()];
			int index = 0;
			for (T eachItem : collection) {
				if (null != eachItem) {
					strArray[index] = eachItem.toString(); 
				}
				index++;				
			}
			return strArray;
		}
		
	}
	
	public static String stripXSS(String value) {
		if (value != null) {
			Pattern[] patterns = new Pattern[] {
					// Script fragments
					Pattern.compile("<script>(.*?)</script>",
							Pattern.CASE_INSENSITIVE),
					// src='...'
					Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
									| Pattern.DOTALL),
					Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
									| Pattern.DOTALL),
					Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
							Pattern.CASE_INSENSITIVE),
					Pattern.compile("src[\r\n]*=*",
									Pattern.CASE_INSENSITIVE),
					// lonely script tags
					Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
					Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
							| Pattern.MULTILINE | Pattern.DOTALL),
					// eval(...)
					Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
							| Pattern.MULTILINE | Pattern.DOTALL),
					// expression(...)
					Pattern.compile("expression\\((.*?)\\)",
							Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
									| Pattern.DOTALL),
					// javascript:...
					Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
					// vbscript:...
					Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
					// Greater than .....
					Pattern.compile("\">", Pattern.CASE_INSENSITIVE),
					// less than ....
					Pattern.compile("<\"", Pattern.CASE_INSENSITIVE),

					Pattern.compile("\"\"/>", Pattern.CASE_INSENSITIVE),
					
					Pattern.compile("\"/>", Pattern.CASE_INSENSITIVE),
					
					Pattern.compile("<input", Pattern.CASE_INSENSITIVE),

					Pattern.compile("<img", Pattern.CASE_INSENSITIVE),

					Pattern.compile("<\\s*script\\s*>|alert\\s*\\(\\s*\\)",
							Pattern.CASE_INSENSITIVE),

					Pattern.compile("<\\s*script\\s*>|confirm\\s*\\(\\s*\\)",
							Pattern.CASE_INSENSITIVE),
							
					Pattern.compile("<\\s*script\\s*>|prompt\\s*\\(\\s*\\)",
									Pattern.CASE_INSENSITIVE),
									
					Pattern.compile("alert|confirm|prompt",
											Pattern.CASE_INSENSITIVE),

					// onload(...)=...
					Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
							| Pattern.MULTILINE | Pattern.DOTALL),
				    Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE
									| Pattern.MULTILINE | Pattern.DOTALL),		
							
					Pattern.compile("onfocus(.*?)=", Pattern.CASE_INSENSITIVE
									| Pattern.MULTILINE | Pattern.DOTALL)
				   
				   };
			try {
                value = URLDecoder.decode(value, "UTF-8");
				value = value.replaceAll("\0", "");
				// Remove all sections that match a pattern
				for (Pattern scriptPattern : patterns) {
					value = scriptPattern.matcher(value).replaceAll(" ");
				}
			} catch (Exception e) {
			}
		}
		return value;
	}
	
	/**
	 * This method validate the attemptNo is valid as per policy and user extension.
	 * @param attemptNo attempt number being used.
	 * @param attemptsNoPolicy max allowed attempt for the assignment.
	 * @param extForAttempts max attempt allowed as per extension given to the student.
	 * @throws ConnectApplicationException
	 */
	public static void validateAttemptNo(int attemptNo, String attemptsNoPolicy, int extForAttempts) throws ConnectApplicationException{
		boolean policyValid  = true;
		if (attemptNo > 1) {
			if (!GenUtil.isBlankString(attemptsNoPolicy) && (attemptNo > Integer.valueOf(attemptsNoPolicy)) && !"-1".equals(attemptsNoPolicy) && !(-1 == extForAttempts)) {
				policyValid = false;
			}
			logger.debug("policyValid : "+policyValid);
			if (extForAttempts > 0) {
				if(attemptNo > extForAttempts){
					throw new ConnectApplicationException("OAPI-1022");
				}	
			} else {
				if(!policyValid){
					throw new ConnectApplicationException("OAPI-1022");
				}
			}
		}
	}
	
	public static boolean isStudentRole(String userRole){
		if("Student".equalsIgnoreCase(userRole) || "S".equalsIgnoreCase(userRole)){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Returns whether the assignment type is generic assignment or not
	 * @param genericLTITools
	 * @param assignmentType
	 * @return
	 */
	public static boolean isGenericAssignment(List<HashMap<String, String>> genericLTITools, String assignmentType) {
		boolean isGeneric = false;
		if (!CollectionUtils.isEmpty(genericLTITools) && StringUtils.isNotBlank(assignmentType)) {
			for(Map<String, String> genericTool:genericLTITools) {
				String toolType = genericTool.get(TOOL_TITLE);
				if (toolType != null && toolType.equalsIgnoreCase(assignmentType)) {
					isGeneric = true;
					break;
				}
			}
		}
		return isGeneric;
	}
	
	/**
	 * Returns server host name by querying localhost hostname for tomcat server,
	 * and weblogic.name system property for weblogic.
	 * @return hostname of server
	 */
	public static String getServerHostName() {
		try {
			if (Configuration.isTomcat()) {
				return InetAddress.getLocalHost().getHostName();
			} else {
				return System.getProperty("weblogic.Name");
			}
		} catch (Exception e) {
			logger.error("Error while getting server host name: ", e);
		}
		return "unknown";
	}
	

	/**
	 * This method is overloaded version which takes request object instead of session object and checks if hasAssociatedRolePresent role flag is true
	 * then returns sectionPrimaryInstructorId. Otherwise returns sessionUserId.
	 * @param methodName
	 * @param request
	 * @return
	 */
	public static String getPrimaryInsIdOrUserIdFromSession(String methodName, HttpServletRequest request) {
		return getPrimaryInsIdOrUserIdFromSession(methodName, request.getSession());
	}

	/**
	 * This method is overloaded version which takes session instead of request object and checks if hasAssociatedRolePresent role flag is true
	 * then returns sectionPrimaryInstructorId.Otherwise returns sessionUserId.
	 * @param methodName
	 * @param
	 * @return
	 */
	public static String getPrimaryInsIdOrUserIdFromSession(String methodName, HttpSession session) {
		String sessionUserId = (String) session.getAttribute("userId");
		boolean hasAssociatedRolePresent = Boolean.parseBoolean((String) session.getAttribute("hasAssociatedRolePresent"));
		String sectionPrimaryInstructorId = (String) session.getAttribute("sectionPrimaryInstructorId");
		String personRole = String.valueOf(session.getAttribute("personRole"));
		if (hasAssociatedRolePresent && StringUtils.isNotBlank(sectionPrimaryInstructorId)) {
			String callerclassName = processLogAndGetCallerInfo(Thread.currentThread());
			logger.debug("[ADDITIONAL_ROLE] User having additional role {} called from {} class and {} method, replaced user {} with primary instructor id {}",
					new Object[] { personRole, callerclassName, methodName, sessionUserId, sectionPrimaryInstructorId});
			return sectionPrimaryInstructorId;
		}
		return sessionUserId;
	}

	private static String processLogAndGetCallerInfo(Thread currentThread) {
		if (Objects.nonNull(currentThread) && Objects.nonNull(currentThread.getStackTrace()) && currentThread.getStackTrace().length >= 4 && Objects.nonNull(currentThread.getStackTrace()[3])) {
			if (!currentThread.getStackTrace()[3].getClassName().contains("GenUtil")) {
				return currentThread.getStackTrace()[3].getClassName();
			} else {
				if (currentThread.getStackTrace().length >= 5 && Objects.nonNull(currentThread.getStackTrace()[4])) {
					return currentThread.getStackTrace()[4].getClassName();
				}
			}
		}
		return "";
	}

	/**
	 * This api checks whether the current user is a co-instructor
	 * TODO: This api is a temporary solution. Until the ER provides the role details.
	 *
	 * @param request
	 * @return
	 */
	public static boolean isCoInstructorAssociationPresent(HttpServletRequest request){
		return Boolean.parseBoolean((String) request.getSession().getAttribute("hasAssociatedRolePresent"));
	}

    /**
     * This method converts String to Map<String, Object> and is used to convert JSON String into JSON object.
     * @param permissions
     * @return Map
     */
    public static Map<String, Object> convertStringToMap(String permissions) {
		Map<String, Object> permissionsMap = new HashMap<String, Object>();
		try {
			permissionsMap = new ObjectMapper().readValue(permissions, HashMap.class);
		} catch (JsonParseException e) {
			logger.error("[JSON_STRING_TO_MAP_CONVERSION] JsonParsing Exception while converting json:", e);
		} catch (JsonMappingException e) {
			logger.error("[JSON_STRING_TO_MAP_CONVERSION] JsonMapping Exception while converting json:", e);
		} catch (IOException e) {
			logger.error("[JSON_STRING_TO_MAP_CONVERSION] IO Exception while converting json:", e);
		}
		return permissionsMap;
	}

	/**
	 * This method converts Map<String, Object> to String and is used too convert JSON Object as Map into JSON String.
	 * @param permissionsMap
	 * @return Json String
	 */
	public static String convertMapToString( Map<String, Object> permissionsMap) {
		String permissions = "";
		try {
			permissions = new ObjectMapper().writeValueAsString(permissionsMap);
		} catch (JsonParseException e) {
			logger.error("[JSON_MAP_TO_STRING_CONVERSION] JsonParsing Exception while converting json:", e);
		} catch (JsonMappingException e) {
			logger.error("[JSON_MAP_TO_STRING_CONVERSION] JsonMapping Exception while converting json:", e);
		} catch (IOException e) {
			logger.error("[JSON_MAP_TO_STRING_CONVERSION] IO Exception while converting json:", e);
		}
		return permissions;
	}
	
	public static boolean isUserTA(HttpSession session) {
		String personRole = (String) session.getAttribute("personRole");
		return ("teaching-assistant".equalsIgnoreCase(personRole));
	}
	
	public static void logActivity(Map<String, String> loggerMap, HttpSession session) {
		logger.debug("[USER ACTIVITY] User : {} with Role : {} accessing : {} at {}. trackingId : [{}]",
				new Object[] {(String)session.getAttribute("userId"), (String) session.getAttribute("personRole"), 
						loggerMap, convertToZonedDateTime(System.currentTimeMillis()), (String)session.getAttribute("USER_TRACKER")});
	}
	
	public static String convertToZonedDateTime(long longDate) {
		Calendar cal = Calendar.getInstance();
	    cal.setTimeZone(TimeZone.getTimeZone("America/New_York"));
	    cal.setTimeInMillis(longDate);
	    String formatedDate = String.format("%02d-%02d-%02d %02d:%02d:%02d",
	    		cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.DAY_OF_MONTH),
	    		cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		return formatedDate;
	}
	
	/**
	 * CST-8565-Adding feature flag for EZT late submission
	 */
	public static boolean isLateSubmissionFeatureEnabled() {
		boolean isLateSubmissionFeatureEnabled = false;
		if("Y".equalsIgnoreCase(Configuration.getSystemValue("IS_EZT_LATE_SUBMISSION_FEATURE_ENABLED"))) {
			isLateSubmissionFeatureEnabled = true; 
		}
		return isLateSubmissionFeatureEnabled;
	}
	
	/**
	 * @author Sayan.Halder@mheducation.com
	 * CST-9017
	 * @param orgXid
	 * @return boolean
	 */
	public static boolean isValidOrgXidForEZTLateSubmission(String orgXid) {
		boolean isValidOrgXidForEZTLateSubmission = false;
		String eztOrgs = Configuration.getSystemValue(EZT_LATE_SUBMISSION_ORGS);
		logger.debug("[isValidOrgXidForEZTLateSubmission] Valid eztOrgXids : {}, section org_xid: {}", eztOrgs, orgXid);
		if(isBlankString(eztOrgs)) {
			//When there are no specific org xids then show ezt late submission for all
			isValidOrgXidForEZTLateSubmission = true;
		} else if (isBlankString( orgXid )) {
			//When the org_xid for the section is null then return false and do not show the ezt late submission options
			isValidOrgXidForEZTLateSubmission = false;
		} else {
			isValidOrgXidForEZTLateSubmission = eztOrgs.contains(orgXid);
			logger.debug("[isValidOrgXidForEZTLateSubmission] OrgXid obtained for EZT_Late_Submission : {} : isValidOrgXidForEZTLateSubmission : {}", orgXid, isValidOrgXidForEZTLateSubmission);
		}
		return isValidOrgXidForEZTLateSubmission;
	}
	
	/**
	 * This method is used to split the param which is separated by the token
	 * and further checks if a parameter is present or not and based on that splits.
	 * @param parameter
	 * @param
	 * @return
	 */
	public static String[] splitParameter(String parameter, String splitToken, String checkToken) {
		logger.debug("Calling new param splitter with {}, {}, {}", new Object[] {parameter, splitToken, checkToken});
		String[] splitParam = null;
		if (parameter.contains(checkToken)) {
			String[] param = parameter.split(checkToken);
			splitParam = param[0].split(splitToken);
		}
		else {
			splitParam = parameter.split(splitToken);
		}
		return splitParam;
	}

}
