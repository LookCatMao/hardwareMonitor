package com;

import com.alibaba.fastjson.JSONObject;
import com.common.Constants;
import com.common.StringUtils;
import com.sigar.SigarManager;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.Swap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: luo
 * @Description:
 * @Data: 17:48 2021/11/6
 */
public class Memory {
    static Logger logger = LoggerFactory.getLogger(Nvidia.class);
    private static Memory memory;
    public static Memory getProfile() {
        if (StringUtils.isNull(memory)) {
            memory = new Memory();
        }
        return memory;
    }


    public JSONObject getInfo() {
        Sigar sigar = SigarManager.getProfile().getSigar();
        JSONObject jsonObject = new JSONObject();
        try {
            Mem mem = sigar.getMem();
            jsonObject.put("total", mem.getTotal() / Constants.GB);
            jsonObject.put("used", mem.getUsed() / Constants.GB);
            jsonObject.put("free", mem.getFree() / Constants.GB);
            Swap swap = sigar.getSwap();
            //交换区总量
            jsonObject.put("swapTotal", mem.getTotal() / Constants.GB);
            //当前交换区使用量
            jsonObject.put("swapUsed", mem.getUsed() / Constants.GB);
            // 当前交换区剩余量
            jsonObject.put("swapFree", swap.getFree() / Constants.GB);
        } catch (Exception e) {
            logger.error("内存信息获取失败");
            e.printStackTrace();
        }
        return jsonObject;
    }
}
