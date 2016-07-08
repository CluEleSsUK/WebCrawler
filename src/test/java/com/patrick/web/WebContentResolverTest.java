package com.patrick.web;

import com.patrick.exception.RequestFailedException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class WebContentResolverTest {

    private ContentResolver contentResolver;
    private HttpClient httpClient = mock(HttpClient.class);
    private HttpResponse response = mock(HttpResponse.class);
    private HttpEntity entity = mock(HttpEntity.class);
    private StatusLine status = mock(StatusLine.class);

    private static final String defaultUrl = "http://www.google.com";
    private static final String expectedOutput = "valid response";

    @Before
    public void setUp() throws Exception {
        contentResolver = new WebContentResolver(httpClient);

        given(entity.getContent()).willReturn(new ByteArrayInputStream(expectedOutput.getBytes("UTF-8")));
        given(response.getEntity()).willReturn(entity);
        given(status.getStatusCode()).willReturn(200);
        given(response.getStatusLine()).willReturn(status);
        given(httpClient.execute(any())).willReturn(response);
    }

    @Test
    public void getUrlAsString_ShouldInvokeHttpClient() throws Exception {
        //when
        String actualOutput = contentResolver.getUrlAsString(defaultUrl);

        //then
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test(expected = RequestFailedException.class)
    public void getUrlAsString_ShouldThrowException_GivenStatusCodeOtherThan200() throws Exception {
        //given
        given(status.getStatusCode()).willReturn(500);

        //when
        contentResolver.getUrlAsString(defaultUrl);
    }
}
