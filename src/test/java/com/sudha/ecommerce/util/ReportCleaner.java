package com.sudha.ecommerce.util;

import java.io.File;

public class ReportCleaner {

    public static void cleanDirectory(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    cleanDirectory(file.getAbsolutePath());
                    file.delete();
                }
            }
        }
    }
}