package com.patrick.utils;

public class TreePrinter {

    public static void print(LinkTree<String> tree) {
        System.out.print(tree.getData());
        if (tree.hasChildren()) {
            tree.getChildren().stream()
                    .forEach(child -> {
                        System.out.print("\t");
                        TreePrinter.print(child);
                        spaces(tree.getData().length());
                    });
        }
        System.out.println();
    }

    private static void spaces(int numberOfSpaces) {
        for (int i=0; i< numberOfSpaces; i++) {
            System.out.print(" ");
        }
    }
}
