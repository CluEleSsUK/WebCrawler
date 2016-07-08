package com.patrick.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

    @Test
    public void stripSpaces_removesSpaces() throws Exception {
        //given
        String expected = "stringwithspaces";
        String initial = "string with spaces";

        //then
        assertThat(Utils.stripLastChar(initial)).isEqualTo(expected);
    }

    @Test
    public void stripSpaces_ReturnsNull_GivenNullValue() throws Exception {
        assertThat(Utils.stripLastChar(null)).isEqualTo(null);
    }
}
