package com.appdirect.sdk.appmarket.dataconnections;

import com.appdirect.sdk.appmarket.events.ErrorCode;

/**
 * This interface defines two methods (with default implementations) that define the response page
 * to authoziation requests for data connections. I.e. when a user clicks 'Connect' to a CMS
 * (AppInsights or AppWise) connector, the string returned from these methods are used as the
 * response page to a successful or failed authorization.
 */
public interface DataConnectionsAuthorizationResponse {

	/**
	 * This method returns the default success html page for Authorization requests for data connections.
	 * It can be overridden by developers implementing the data connection authorization handler who
	 * want to control the result page.
	 * @return  the default success html page for Authorization requests for data connections
	 */
	default String success() {
		String result = "<html><head>\n" +
				"    <title>Authorization Success</title>\n" +
				"    <style>\n" +
				"        body { padding: 1em; text-align: center; }\n" +
				"    </style>\n" +
				"</head>\n" +
				"<body>\n" +
				"<p><b>Authorization Success</b></p>\n" +
				"\n" +
				"<p>Authorization was successful. Please close this window.</p>\n" +
				"\n" +
				"</body></html>";
		return result;
	}

	/**
	 * This method returns the default failure html page for Authorization requests for data connections.
	 * It incorporates the passed in errorCode and message.
	 * It can be overridden by developers implementing the data connection authorization handler who
	 * want to control the result page.
	 * @param errorCode failure errorCode
	 * @param message failure message
	 * @return the default failure html page for Authorization requests for data connections
	 */
	default String failure (ErrorCode errorCode, String message) {
		String result = "<html><head>\n" +
				"    <title>Authorization Failure</title>\n" +
				"    <style>\n" +
				"        body { padding: 1em; text-align: center; }\n" +
				"    </style>\n" +
				"</head>\n" +
				"<body>\n" +
				"<p><b>Authorization Failue</b></p>\n" +
				"\n" +
				"<p>Authorization was unsuccessful. Please close this window and try again.</p>\n" +
				"\n" +
				"<p>Message: " + message + ". Code: " + errorCode + ".</p>\n" +
				"\n" +
				"</body></html>";
		return result;
	}
}
