package com.sigar;

import org.hyperic.sigar.Sigar;

import java.io.File;

/**
 * @Author: luo
 * @Description:
 * @Data: 17:25 2021/11/5
 */
public class SigarUtils {
    public static Sigar initSigar() {
        try {
            //此处只为得到依赖库文件的目录，可根据实际项目自定义
            String tomcatBinPath = System.getProperty("user.dir");
            String tomcatRootPath = tomcatBinPath.substring(0, tomcatBinPath.lastIndexOf(File.separator));
            String rootPath = tomcatRootPath + File.separator + "webapps";
            String filepath = "\\power\\WEB-INF\\lib";
            File classPath = new File(rootPath + filepath);//这里我直接把文件路径写死了


            String path = System.getProperty("java.library.path");
            String sigarLibPath = classPath.getCanonicalPath();
            //sigarLibPath = sigarLibPath.replace("bin", "webapps");
            //为防止java.library.path重复加，此处判断了一下
            if (!path.contains(sigarLibPath)) {
                if (isOSWin()) {
                    path += ";" + sigarLibPath;
                } else {
                    path += ":" + sigarLibPath;
                }
                System.setProperty("java.library.path", path);
            }
            return new Sigar();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isOSWin() {//OS 版本判断
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("win") >= 0) {
            return true;
        } else {
            return false;}
    }
}
