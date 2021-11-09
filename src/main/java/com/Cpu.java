package com;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.StringUtils;
import com.sigar.SigarManager;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: luo
 * @Description: Cpu管理器
 * @Data: 17:00 2021/11/6
 */
public class Cpu {
    static Logger logger = LoggerFactory.getLogger(Cpu.class);
    private static Cpu cpu;
    public static Cpu getProfile() {
        if (StringUtils.isNull(cpu)) {
            cpu = new Cpu();
        }
        return cpu;
    }

    public JSONObject getInfo() {
        Sigar sigar = SigarManager.getProfile().getSigar();
        JSONObject jsonObject = new JSONObject();
        try {
            //获取CPU基本信息
            CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
            //获取CPU运行信息
            CpuPerc[] cpuPercList = sigar.getCpuPercList();
            JSONArray cpuJsonList = new JSONArray();
            jsonObject.put("cpuList", cpuJsonList);
            for (int i = 0; i < cpuInfoList.length; i++) {
                CpuInfo cpuInfo = cpuInfoList[i];
                JSONObject cpuJson = new JSONObject();
                cpuJsonList.add(cpuJson);
                cpuJson.put("order", i);
                //CPU的总量MHz
                cpuJson.put("mhz", cpuInfo.getMhz());
                cpuJson.put("vendor", cpuInfo.getVendor());
                cpuJson.put("model", cpuInfo.getModel());
                cpuJson.put("cacheSize", cpuInfo.getCacheSize());
                cpuJson.put("totalCores", cpuInfo.getTotalCores());
                cpuJson.put("totalSockets", cpuInfo.getTotalSockets());
                CpuPerc cpuPerc = cpuPercList[i];
                //用户使用率
                cpuJson.put("user", cpuPerc.getUser());
                //系统使用率
                cpuJson.put("sys", cpuPerc.getSys());
                //当前等待率
                cpuJson.put("wait", cpuPerc.getWait());
                //错误率
                cpuJson.put("nice", cpuPerc.getNice());
                //当前空闲率
                cpuJson.put("idle", cpuPerc.getIdle());
                //总的使用率
                cpuJson.put("combined", cpuPerc.getCombined());
            }
        } catch (SigarException e) {
            logger.error("cpu信息获取失败");
            e.printStackTrace();
        }
        return jsonObject;
    }
}
