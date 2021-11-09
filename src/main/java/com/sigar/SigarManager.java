package com.sigar;

import com.Cpu;
import com.common.StringUtils;
import org.hyperic.sigar.Sigar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: luo
 * @Description:
 * @Data: 17:54 2021/11/6
 */
public class SigarManager {
    static Logger logger = LoggerFactory.getLogger(Cpu.class);
    private static SigarManager sigarManager;
    public static SigarManager getProfile() {
        if (StringUtils.isNull(sigarManager)) {
            sigarManager = new SigarManager();
        }
        return sigarManager;
    }
    private SigarManager(){
        sigar=new Sigar();
    }

    Sigar sigar;
    public Sigar getSigar() {
        return sigar;
    }

    public void Refresh(){
        sigar=new Sigar();
    }




}
