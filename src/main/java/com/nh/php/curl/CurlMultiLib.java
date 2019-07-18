package com.nh.php.curl;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface CurlMultiLib extends Library {
	CurlMultiLib INSTANCE = (CurlMultiLib) Native
			.loadLibrary("curl", CurlMultiLib.class);

	Pointer curl_multi_init();

	int curl_multi_cleanup(Pointer curlm);

	int curl_multi_add_handle(Pointer curlm, Pointer easyHandle);

	int curl_multi_remove_handle(Pointer curlm, Pointer easyHandle);

	int curl_multi_setopt(Pointer curlm, int option, Object obj);

	int curl_multi_fdset(Pointer curlm, fd_set read_fd_set, fd_set write_fd_set,
						 fd_set exc_fd_set, int[] max_fd);

	int curl_multi_perform(Pointer curlm, int[] runningHandles);

	String curl_multi_strerror(int errorNum);

}
