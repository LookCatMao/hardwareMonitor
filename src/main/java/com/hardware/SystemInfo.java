package com.hardware;

import com.MonitorApplication;
import com.sigar.SigarManager;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Who;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: luo
 * @Description:
 * @Data: 17:33 2021/11/6
 */
public class SystemInfo {
    static Logger logger = LoggerFactory.getLogger(MonitorApplication.class);

    public static void initMessage() {
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        String hostName = null;
        String ip = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
            ip = addr.getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("获取主机信息失败");
            e.printStackTrace();
        }
        Map<String, String> map = System.getenv();
        // 获取用户名
        String userName = map.get("USERNAME");
        // 获取计算机名
        String computerName = map.get("COMPUTERNAME");
        // 获取计算机域名
        String userDomain = map.get("USERDOMAIN");
        logger.info("用户名:{}", userName);
        logger.info("计算机名:{}", computerName);
        logger.info("计算机域名:{}", userDomain);
        logger.info("本地ip地址:{} ", ip);
        logger.info("本地主机名:{}", hostName);
        logger.info("JVM可以使用的总内存:{}", r.totalMemory());
        logger.info("JVM可以使用的剩余内存:{}", r.freeMemory());
        logger.info("JVM可以使用的处理器个数:{}", r.availableProcessors());
        logger.info("Java的运行环境版本：{}", props.getProperty("java.version"));
        logger.info("Java的运行环境供应商：{}", props.getProperty("java.vendor"));
        logger.info("Java供应商的URL：{} ", props.getProperty("java.vendor.url"));
        logger.info("Java的安装路径：{} ", props.getProperty("java.home"));
        logger.info("Java的虚拟机规范版本：{} ", props.getProperty("java.vm.specification.version"));
        logger.info("Java的虚拟机规范供应商：{} ", props.getProperty("java.vm.specification.vendor"));
        logger.info("Java的虚拟机规范名称：{} ", props.getProperty("java.vm.specification.name"));
        logger.info("Java的虚拟机实现版本：{} ", props.getProperty("java.vm.version"));
        logger.info("Java的虚拟机实现供应商：{} ", props.getProperty("java.vm.vendor"));
        logger.info("Java的虚拟机实现名称：{} ", props.getProperty("java.vm.name"));
        logger.info("Java运行时环境规范版本：{} ", props.getProperty("java.specification.version"));
        logger.info("Java运行时环境规范供应商：{} ", props.getProperty("java.specification.vender"));
        logger.info("Java运行时环境规范名称：{} ", props.getProperty("java.specification.name"));
        logger.info("Java的类格式版本号：{} ", props.getProperty("java.class.version"));
        logger.info("Java的类路径：{} ", props.getProperty("java.class.path"));
        logger.info("加载库时搜索的路径列表：{} ", props.getProperty("java.library.path"));
        logger.info("默认的临时文件路径：{} ", props.getProperty("java.io.tmpdir"));
        logger.info("一个或多个扩展目录的路径：{} ", props.getProperty("java.ext.dirs"));
        logger.info("操作系统的名称：{} ", props.getProperty("os.name"));
        logger.info("操作系统的构架：{} ", props.getProperty("os.arch"));
        logger.info("操作系统的版本：{} ", props.getProperty("os.version"));
        logger.info("文件分隔符：{} ", props.getProperty("file.separator"));
        logger.info("路径分隔符：{} ", props.getProperty("path.separator"));
        logger.info("行分隔符：{} ", props.getProperty("line.separator"));
        logger.info("用户的账户名称：{} ", props.getProperty("user.name"));
        logger.info("用户的主目录：{} ", props.getProperty("user.home"));
        logger.info("用户的当前工作目录：{} ", props.getProperty("user.dir"));
    }

    public static void os() {
        OperatingSystem OS = OperatingSystem.getInstance();
        // 操作系统内核类型如： 386、486、586等x86
        logger.info("操作系统: {}", OS.getArch());
        logger.info("操作系统CpuEndian(): {}", OS.getCpuEndian());//
        logger.info("操作系统DataModel(): {}", OS.getDataModel());//
        // 系统描述
        logger.info("操作系统的描述: {}", OS.getDescription());
        // 操作系统类型
        logger.info("OS.getName(): {}", OS.getName());
        logger.info("OS.getPatchLevel(): {}", OS.getPatchLevel());//
        // 操作系统的卖主
        logger.info("操作系统的卖主: {}", OS.getVendor());
        // 卖主名称
        logger.info("操作系统的卖主名: {}", OS.getVendorCodeName());
        // 操作系统名称
        logger.info("操作系统名称: {}", OS.getVendorName());
        // 操作系统卖主类型
        logger.info("操作系统卖主类型: {}", OS.getVendorVersion());
        // 操作系统的版本号
        logger.info("操作系统的版本号: {}", OS.getVersion());
    }

    private static void who() throws SigarException {
        Sigar sigar = SigarManager.getProfile().getSigar();
        Who who[] = sigar.getWhoList();
        if (who == null) {
            return;
        }

        for (Who _who : who) {
            logger.info("用户控制台: {}", _who.getDevice());
            logger.info("用户host: {}", _who.getHost());
            logger.info("getTime(): {}", _who.getTime());
            logger.info("当前系统进程表中的用户名: {}", _who.getUser());
        }
    }
}
