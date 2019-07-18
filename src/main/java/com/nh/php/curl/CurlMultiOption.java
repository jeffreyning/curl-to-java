package com.nh.php.curl;

public interface CurlMultiOption extends CurlOptionType {
	/* This is the socket callback function pointer */
	int CURLMOPT_SOCKETFUNCTION = CURLOPTTYPE_FUNCTIONPOINT + 1;

	/* This is the argument passed to the socket callback */
	int CURLMOPT_SOCKETDATA = CURLOPTTYPE_OBJECTPOINT + 2;

	/* set to 1 to enable pipelining for this multi handle */
	int CURLMOPT_PIPELINING = CURLOPTTYPE_LONG + 3;

	/* This is the timer callback function pointer */
	int CURLMOPT_TIMERFUNCTION = CURLOPTTYPE_FUNCTIONPOINT + 4;

	/* This is the argument passed to the timer callback */
	int CURLMOPT_TIMERDATA = CURLOPTTYPE_OBJECTPOINT + 5;

	/* maximum number of entries in the connection cache */
	int CURLMOPT_MAXCONNECTS = CURLOPTTYPE_LONG + 6;

	int CURLMOPT_LASTENTRY = CURLOPTTYPE_LONG + 7; /* the last unused */
}
