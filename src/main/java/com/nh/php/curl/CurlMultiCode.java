package com.nh.php.curl;

public interface CurlMultiCode {
	/*
	 * please call curl_multi_perform() or curl_multi_socket*() soon
	 */
	int CURLM_CALL_MULTI_PERFORM = -1;

	/* OK */
	int CURLM_OK = 0;

	/* the passed-in handle is not a valid CURLM handle */
	int CURLM_BAD_HANDLE = 1;

	/* the passed-in handle is not a valid CURLM handle */
	int CURLM_BAD_EASY_HANDLE = 2;

	/* the passed-in handle is not a valid CURLM handle */
	int CURLM_OUT_OF_MEMORY = 3;

	/* the passed-in handle is not a valid CURLM handle */
	int CURLM_INTERNAL_ERROR = 4;

	/* the passed-in handle is not a valid CURLM handle */
	int CURLM_BAD_SOCKET = 5;

	/* the passed-in handle is not a valid CURLM handle */
	int CURLM_UNKNOWN_OPTION = 6;

	/* last */
	int CURLM_LAST = 7;

}
