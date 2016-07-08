package com.patrick.main;

import com.patrick.utils.LinkTree;
import com.patrick.web.ContentResolver;
import org.junit.Before;
import org.junit.Test;

import static com.patrick.utils.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class WebCrawlerTest {

    private WebCrawler webCrawler;
    private ContentResolver contentResolver = mock(ContentResolver.class);
    private static final String DOMAIN = "google.com";
    private static final String DEFAULT_URL = "http://google.com";

    @Before
    public void setUp() throws Exception {
        webCrawler = new WebCrawler(contentResolver, DOMAIN);
    }

    //FIXME: remove need for spaces after links!
    @Test
    public void treeFromContent_ShouldReturnValidData() throws Exception {
        //given
        String content = "http://google.com blah blah http://google.com/thing blah blah http://google.com/other ";
        given(contentResolver.getUrlAsString(DEFAULT_URL))
                .willReturn(content);
        given(contentResolver.getUrlAsString("http://google.com/thing"))
                .willReturn("http://google.com/otherthing ");

        //when
        LinkTree<String> siteMap = webCrawler.crawl(DEFAULT_URL);

        //then
        assertThat(siteMap.getChildren().size()).isEqualTo(2);
        assertThat(siteMap.getChildren().get(0).getChildren().size()).isEqualTo(1);
    }

    @Test
    public void shouldOpenValidLink_AndEvaluateContents_GivenCurrentDomain() throws Exception {
        //given
        String content = "http://google.com/thing ";
        given(contentResolver.getUrlAsString(DEFAULT_URL)).willReturn(content);
        given(contentResolver.getUrlAsString(stripLastChar(content))).willReturn("something");

        //when
        webCrawler.crawl(DEFAULT_URL);

        //then
        verify(contentResolver).getUrlAsString(stripLastChar(content));
    }

    @Test
    public void shouldAddLink_ButNotVisit_GivenDifferentDomain() throws Exception {
        //given
        String content = "http://www.otherdomain.com ";
        given(contentResolver.getUrlAsString(DEFAULT_URL)).willReturn(content);

        //when
        LinkTree<String> siteMap = webCrawler.crawl(DEFAULT_URL);

        //then
        assertThat(siteMap.getChildren().size()).isEqualTo(1);
        verify(contentResolver).getUrlAsString(DEFAULT_URL);
        verifyNoMoreInteractions(contentResolver);
    }

    @Test
    public void shouldNotFollowDomainLink_GivenAlreadyFollowed() throws Exception {
        //given
        String content = "http://www.google.com/link http://www.google.com/link ";
        given(contentResolver.getUrlAsString(DEFAULT_URL)).willReturn(content);

        //when
        LinkTree<String> siteMap = webCrawler.crawl(DEFAULT_URL);

        //then
        assertThat(siteMap.getChildren().size()).isEqualTo(1);
        assertThat(siteMap.getChildren().get(0).getData()).isEqualTo("http://www.google.com/link");
    }


    @Test
    public void shouldNotMappedExternalLink_GivenAlreadyMapped() throws Exception {
        //given
        String content = "http://www.otheruri.com/link http://www.otheruri.com/link ";
        given(contentResolver.getUrlAsString(DEFAULT_URL)).willReturn(content);

        //when
        LinkTree<String> siteMap = webCrawler.crawl(DEFAULT_URL);

        //then
        assertThat(siteMap.getChildren().size()).isEqualTo(1);
        assertThat(siteMap.getChildren().get(0).getData()).isEqualTo("http://www.otheruri.com/link");
    }

    @Test
    public void shouldAddLink_GivenLinkEndsInAngleBracket() throws Exception {
        //given
        String content = "http://www.otheruri.com/link<";
        given(contentResolver.getUrlAsString(DEFAULT_URL)).willReturn(content);

        //when
        LinkTree<String> siteMap = webCrawler.crawl(DEFAULT_URL);

        //then
        assertThat(siteMap.getChildren().size()).isEqualTo(1);
        assertThat(siteMap.getChildren().get(0).getData()).isEqualTo("http://www.otheruri.com/link");
    }

    @Test
    public void shouldAddLink_GivenLinkEndsInQuotes() throws Exception {
        //given
        String content = "http://www.otheruri.com/link\"";
        given(contentResolver.getUrlAsString(DEFAULT_URL)).willReturn(content);

        //when
        LinkTree<String> siteMap = webCrawler.crawl(DEFAULT_URL);

        //then
        assertThat(siteMap.getChildren().size()).isEqualTo(1);
        assertThat(siteMap.getChildren().get(0).getData()).isEqualTo("http://www.otheruri.com/link");
    }

    @Test
    public void shouldAddLink_GivenStartsWithHttps() throws Exception {
        //given
        String content = "https://www.otheruri.com/link ";
        given(contentResolver.getUrlAsString(DEFAULT_URL)).willReturn(content);

        //when
        LinkTree<String> siteMap = webCrawler.crawl(DEFAULT_URL);

        //then
        assertThat(siteMap.getChildren().size()).isEqualTo(1);
        assertThat(siteMap.getChildren().get(0).getData()).isEqualTo("https://www.otheruri.com/link");
    }
}
