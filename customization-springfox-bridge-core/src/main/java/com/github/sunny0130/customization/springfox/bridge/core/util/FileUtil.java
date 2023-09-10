package com.github.sunny0130.customization.springfox.bridge.core.util;

import java.io.File;

public class FileUtil {
    private static String currentFilePath = "";

    public static String getCurrentFilePath() {
        if (StringUtil.isBlank(currentFilePath)) {
            File file = new File("");
            currentFilePath = file.getAbsolutePath();
        }
        return currentFilePath;
    }

}
