package com.patrick.utils;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkTreeTest {

    private LinkTree<String> tree;
    private static final String ROOT_DATA = "SOME DATA";

    @Before
    public void setUp() throws Exception {
        tree = new LinkTree<>(ROOT_DATA);

    }

    @Test
    public void addChild_AddsGivenFieldToTree() throws Exception {
        //when
        tree.addChild("moreData");

        //then
        assertThat(tree.getChildren().size()).isEqualTo(1);
        assertThat(tree.getChildren().get(0).getData()).isEqualTo("moreData");
    }

    @Test
    public void hasChildren_ReturnsTrue_GivenTreeHasChildren() throws Exception {
        //when
        tree.addChild("moreData");

        //then
        assertThat(tree.hasChildren()).isTrue();
    }
}

