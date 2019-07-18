package com.nh.php.curl;

public interface CurlCode {
	int CURLE_OK = 0;
	int CURLE_UNSUPPORTED_PROTOCOL = 1;     /* 1 */
	int CURLE_FAILED_INIT = 2;              /* 2 */
	int CURLE_URL_MALFORMAT = 3;            /* 3 */
	int CURLE_NOT_BUILT_IN = 4;             /* 4 - [was obsoleted in August 2007 for
                                    7.17.0, reused in April 2011 for 7.21.5] */
	int CURLE_COULDNT_RESOLVE_PROXY = 5;    /* 5 */
	int CURLE_COULDNT_RESOLVE_HOST = 6;     /* 6 */
	int CURLE_COULDNT_CONNECT = 7;          /* 7 */
	int CURLE_FTP_WEIRD_SERVER_REPLY = 8;   /* 8 */
	int CURLE_REMOTE_ACCESS_DENIED = 9;     /* 9 a service was denied by the server
                                    due to lack of access - when login fails
                                    this is not returned. */
	int CURLE_FTP_ACCEPT_FAILED = 10;        /* 10 - [was obsoleted in April 2006 for
                                    7.15.4, reused in Dec 2011 for 7.24.0]*/
	int CURLE_FTP_WEIRD_PASS_REPLY = 11;     /* 11 */
	int CURLE_FTP_ACCEPT_TIMEOUT = 12;       /* 12 - timeout occurred accepting server
                                    [was obsoleted in August 2007 for 7.17.0,
                                    reused in Dec 2011 for 7.24.0]*/
	int CURLE_FTP_WEIRD_PASV_REPLY = 13;     /* 13 */
	int CURLE_FTP_WEIRD_227_FORMAT = 14;     /* 14 */
	int CURLE_FTP_CANT_GET_HOST = 15;        /* 15 */
	int CURLE_OBSOLETE16 = 16;               /* 16 - NOT USED */
	int CURLE_FTP_COULDNT_SET_TYPE = 17;     /* 17 */
	int CURLE_PARTIAL_FILE = 18;             /* 18 */
	int CURLE_FTP_COULDNT_RETR_FILE = 19;    /* 19 */
	int CURLE_OBSOLETE20 = 20;               /* 20 - NOT USED */
	int CURLE_QUOTE_ERROR = 21;              /* 21 - quote command failure */
	int CURLE_HTTP_RETURNED_ERROR = 22;      /* 22 */
	int CURLE_WRITE_ERROR = 23;              /* 23 */
	int CURLE_OBSOLETE24 = 24;               /* 24 - NOT USED */
	int CURLE_UPLOAD_FAILED = 25;            /* 25 - failed upload "command" */
	int CURLE_READ_ERROR = 26;               /* 26 - couldn't open/read from file */
	int CURLE_OUT_OF_MEMORY = 27;            /* 27 */
	/* Note: CURLE_OUT_OF_MEMORY may sometimes indicate a conversion error
			   instead of a memory allocation error if CURL_DOES_CONVERSIONS
			   is defined
	  */
	int CURLE_OPERATION_TIMEDOUT = 28;       /* 28 - the timeout time was reached */
	int CURLE_OBSOLETE29 = 29;               /* 29 - NOT USED */
	int CURLE_FTP_PORT_FAILED = 30;          /* 30 - FTP PORT operation failed */
	int CURLE_FTP_COULDNT_USE_REST = 31;     /* 31 - the REST command failed */
	int CURLE_OBSOLETE32 = 32;               /* 32 - NOT USED */
	int CURLE_RANGE_ERROR = 33;              /* 33 - RANGE "command" didn't work */
	int CURLE_HTTP_POST_ERROR = 34;          /* 34 */
	int CURLE_SSL_CONNECT_ERROR = 35;        /* 35 - wrong when connecting with SSL */
	int CURLE_BAD_DOWNLOAD_RESUME = 36;      /* 36 - couldn't resume download */
	int CURLE_FILE_COULDNT_READ_FILE = 37;   /* 37 */
	int CURLE_LDAP_CANNOT_BIND = 38;         /* 38 */
	int CURLE_LDAP_SEARCH_FAILED = 39;       /* 39 */
	int CURLE_OBSOLETE40 = 40;               /* 40 - NOT USED */
	int CURLE_FUNCTION_NOT_FOUND = 41;       /* 41 */
	int CURLE_ABORTED_BY_CALLBACK = 42;      /* 42 */
	int CURLE_BAD_FUNCTION_ARGUMENT = 43;    /* 43 */
	int CURLE_OBSOLETE44 = 44;               /* 44 - NOT USED */
	int CURLE_INTERFACE_FAILED = 45;         /* 45 - CURLOPT_INTERFACE failed */
	int CURLE_OBSOLETE46 = 46;               /* 46 - NOT USED */
	int CURLE_TOO_MANY_REDIRECTS  = 47;      /* 47 - catch endless re-direct loops */
	int CURLE_UNKNOWN_OPTION = 48;           /* 48 - User specified an unknown option */
	int CURLE_TELNET_OPTION_SYNTAX  = 49;    /* 49 - Malformed telnet option */
	int CURLE_OBSOLETE50 = 50;               /* 50 - NOT USED */
	int CURLE_PEER_FAILED_VERIFICATION = 51;  /* 51 - peer's certificate or fingerprint
                                     wasn't verified fine */
	int CURLE_GOT_NOTHING = 52;              /* 52 - when this is a specific error */
	int CURLE_SSL_ENGINE_NOTFOUND = 53;      /* 53 - SSL crypto engine not found */
	int CURLE_SSL_ENGINE_SETFAILED = 54;     /* 54 - can not set SSL crypto engine as
                                    default */
	int CURLE_SEND_ERROR = 55;               /* 55 - failed sending network data */
	int CURLE_RECV_ERROR = 56;               /* 56 - failure in receiving network data */
	int CURLE_OBSOLETE57 = 57;               /* 57 - NOT IN USE */
	int CURLE_SSL_CERTPROBLEM = 58;          /* 58 - problem with the local certificate */
	int CURLE_SSL_CIPHER = 59;               /* 59 - couldn't use specified cipher */
	int CURLE_SSL_CACERT = 60;               /* 60 - problem with the CA cert (path?) */
	int CURLE_BAD_CONTENT_ENCODING = 61;     /* 61 - Unrecognized/bad encoding */
	int CURLE_LDAP_INVALID_URL = 62;         /* 62 - Invalid LDAP URL */
	int CURLE_FILESIZE_EXCEEDED = 63;        /* 63 - Maximum file size exceeded */
	int CURLE_USE_SSL_FAILED = 64;           /* 64 - Requested FTP SSL level failed */
	int CURLE_SEND_FAIL_REWIND = 65;         /* 65 - Sending the data requires a rewind
                                    that failed */
	int CURLE_SSL_ENGINE_INITFAILED = 66;    /* 66 - failed to initialise ENGINE */
	int CURLE_LOGIN_DENIED = 67;             /* 67 - user, password or similar was not
                                    accepted and we failed to login */
	int CURLE_TFTP_NOTFOUND = 68;            /* 68 - file not found on server */
	int CURLE_TFTP_PERM = 69;                /* 69 - permission problem on server */
	int CURLE_REMOTE_DISK_FULL = 70;         /* 70 - out of disk space on server */
	int CURLE_TFTP_ILLEGAL = 71;             /* 71 - Illegal TFTP operation */
	int CURLE_TFTP_UNKNOWNID = 72;           /* 72 - Unknown transfer ID */
	int CURLE_REMOTE_FILE_EXISTS = 73;       /* 73 - File already exists */
	int CURLE_TFTP_NOSUCHUSER = 74;          /* 74 - No such user */
	int CURLE_CONV_FAILED = 75;              /* 75 - conversion failed */
	int CURLE_CONV_REQD = 76;                /* 76 - caller must register conversion
                                    callbacks using curl_easy_setopt options
                                    CURLOPT_CONV_FROM_NETWORK_FUNCTION,
                                    CURLOPT_CONV_TO_NETWORK_FUNCTION, and
                                    CURLOPT_CONV_FROM_UTF8_FUNCTION */
	int CURLE_SSL_CACERT_BADFILE = 77;       /* 77 - could not load CACERT file, missing
                                    or wrong format */
	int CURLE_REMOTE_FILE_NOT_FOUND = 78;    /* 78 - remote file not found */
	int CURLE_SSH = 79;                      /* 79 - error from the SSH layer, somewhat
                                    generic so the error message will be of
                                    interest when this has happened */

	int CURLE_SSL_SHUTDOWN_FAILED = 80;      /* 80 - Failed to shut down the SSL
                                    connection */
	int CURLE_AGAIN = 81;                    /* 81 - socket is not ready for send/recv,
                                    wait till it's ready and try again (Added
                                    in 7.18.2) */
	int CURLE_SSL_CRL_BADFILE = 82;          /* 82 - could not load CRL file, missing or
                                    wrong format (Added in 7.19.0) */
	int CURLE_SSL_ISSUER_ERROR = 83;         /* 83 - Issuer check failed.  (Added in
                                    7.19.0) */
	int CURLE_FTP_PRET_FAILED = 84;          /* 84 - a PRET command failed */
	int CURLE_RTSP_CSEQ_ERROR = 85;          /* 85 - mismatch of RTSP CSeq numbers */
	int CURLE_RTSP_SESSION_ERROR = 86;       /* 86 - mismatch of RTSP Session Ids */
	int CURLE_FTP_BAD_FILE_LIST = 87;        /* 87 - unable to parse FTP file list */
	int CURLE_CHUNK_FAILED = 88;             /* 88 - chunk callback reported error */
	int CURL_LAST = 89; /* never use! */

}
