package com.patrick.web;

import com.patrick.exception.RequestFailedException;

public interface ContentResolver {
    String getUrlAsString(String link) throws RequestFailedException;
}
