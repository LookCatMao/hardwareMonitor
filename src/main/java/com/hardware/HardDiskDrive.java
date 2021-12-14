package com.hardware;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sigar.SigarManager;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * @Author: luo
 * @Description: 硬盘信息获取
 * @Data: 18:21 2021/11/6
 */
public class HardDiskDrive {
    public static JSONArray getInfo() {
        Sigar sigar = SigarManager.getProfile().getSigar();
        JSONArray fileJsonArray = new JSONArray();
        try {
            FileSystem[] fileSystemList = sigar.getFileSystemList();
            for (FileSystem fs : fileSystemList) {
                JSONObject fileJson = new JSONObject();
                fileJsonArray.add(fileJson);
                fileJson.put("devName", fs.getDevName());
                fileJson.put("dirName", fs.getDirName());
                fileJson.put("flags", fs.getFlags());
                // 文件系统类型，比如 FAT32、NTFS
                fileJson.put("sysTypeName", fs.getSysTypeName());
                // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
                fileJson.put("typeName", fs.getTypeName());
                // 文件系统类型
                fileJson.put("type", fs.getType());
                FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
                //读出
                fileJson.put("diskReads", usage.getDiskReads());
                //写入
                fileJson.put("diskWrites", usage.getDiskWrites());
                switch (fs.getType()) {
                    // TYPE_UNKNOWN ：未知
                    case 0:
                        break;
                    // TYPE_NONE
                    case 1:
                        break;
                    // TYPE_LOCAL_DISK : 本地硬盘
                    case 2:
                        // 文件系统总大小 KB
                        fileJson.put("total", usage.getTotal());
                        // 文件系统剩余大小 KB
                        fileJson.put("free", usage.getFree());
                        // 文件系统可用大小 KB
                        fileJson.put("avail", usage.getAvail());
                        // 文件系统已经使用量 KB
                        fileJson.put("used", usage.getUsed());
                        // 文件系统资源的利用率 KB
                        fileJson.put("usePercent", usage.getUsePercent() * 100D);
                        break;
                    // TYPE_NETWORK ：网络
                    case 3:
                        break;
                    // TYPE_RAM_DISK ：闪存
                    case 4:
                        break;
                    // TYPE_CDROM ：光驱
                    case 5:
                        break;
                    // TYPE_SWAP ：页面交换
                    case 6:
                        break;
                    default:
                }
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return fileJsonArray;
    }
}
