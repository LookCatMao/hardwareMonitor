package com;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.StringUtils;
import com.sigar.SigarManager;
import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: luo
 * @Description:
 * @Data: 18:42 2021/11/6
 */
public class Network {
    static Logger logger = LoggerFactory.getLogger(Nvidia.class);
    private static Network network;
    public static Network getProfile() {
        if (StringUtils.isNull(network)) {
            network = new Network();
        }
        return network;
    }

    public JSONObject getNetInfo(){
        JSONObject jsonObject=new JSONObject();
        Sigar sigar = SigarManager.getProfile().getSigar();
        try {
            String[]  ifNames = sigar.getNetInterfaceList();
            JSONArray jsonArray=new JSONArray();
            long rxPacketsTotal=0L;
            long txPacketsTotal=0L;
            long rxBytesTotal=0L;
            long txBytesTotal=0L;
            jsonObject.put("networkCard",jsonArray);
            for (String name : ifNames) {
                JSONObject newJson=new JSONObject();
                jsonArray.add(newJson);
                NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
                //网络设备名
                newJson.put("name",name);
                // IP地址
                newJson.put("address",ifconfig.getAddress());
                // 子网掩码
                newJson.put("netmask",ifconfig.getNetmask());
                newJson.put("flags",ifconfig.getFlags()& 1L);
                if ((ifconfig.getFlags() & 1L) <= 0L) {
                   // logger.debug("!IFF_UP...skipping getNetInterfaceStat");
                    continue;
                }
                NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
                // 接收的总包裹数
                long  rxPackets=ifstat.getRxPackets();
                rxPacketsTotal=rxPacketsTotal+rxPackets;
                newJson.put("rxPackets",rxPackets);
                // 发送的总包裹数
                long txPackets= ifstat.getTxPackets();
                txPacketsTotal=txPacketsTotal+txPackets;
                newJson.put("txPackets",txPackets);
                // 接收到的总字节数
                long rxBytes= ifstat.getRxBytes();
                rxBytesTotal=rxBytesTotal+rxBytes;
                newJson.put("rxBytes",rxBytes);
                // 发送的总字节数
                System.out.println(name + "发送的总字节数:" + ifstat.getTxBytes());
                long txBytes= ifstat.getTxBytes();
                txBytesTotal=txBytesTotal+txBytes;
                newJson.put("txBytes",txBytes);
                // 接收到的错误包数
                long rxErrors=  ifstat.getRxErrors();
                newJson.put("rxErrors",rxErrors);
                // 发送数据包时的错误数
               long txErrors= ifstat.getTxErrors();
                newJson.put("txErrors",txErrors);
                // 接收时丢弃的包数
                long rxDropped= ifstat.getRxDropped();
                newJson.put("rxDropped",rxDropped);
                // 发送时丢弃的包数
                long txDropped = ifstat.getTxDropped();
                newJson.put("txDropped",txDropped);
            }
            jsonObject.put("rxPacketsTotal",rxPacketsTotal);
            jsonObject.put("txPacketsTotal",txPacketsTotal);
            jsonObject.put("rxBytesTotal",rxBytesTotal);
            jsonObject.put("txBytesTotal",txBytesTotal);
        } catch (Exception e) {
            logger.error("网络信息获取失败");
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray ethernet() {
        JSONArray jsonArray = new JSONArray();
        Sigar sigar = SigarManager.getProfile().getSigar();
        try {
            String[]  ifaces = sigar.getNetInterfaceList();
            for (String iface : ifaces) {
                JSONObject jsonObject = new JSONObject();
                NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(iface);
                if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
                        || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                        || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                    continue;
                }
                // IP地址
                jsonObject.put("address", cfg.getAddress());
                // 网关广播地址
                jsonObject.put("broadcast", cfg.getBroadcast());
                // 网卡MAC地址
                jsonObject.put("hwaddr", cfg.getHwaddr());
                // 子网掩码
                jsonObject.put("netmask", cfg.getNetmask());
                // 网卡描述信息
                jsonObject.put("description", cfg.getDescription());
                //网卡名
                jsonObject.put("name", cfg.getName());
                //网卡类型
                jsonObject.put("type", cfg.getType());
                jsonArray.add(jsonObject);
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
