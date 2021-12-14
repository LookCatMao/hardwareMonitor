package com.hardware;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.Constants;
import com.common.StringUtils;
import de.bommel24.nvmlj.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: luo
 * @Description:
 * @Data: 20:14 2021/11/3
 */
public class Nvidia {
    static Logger logger = LoggerFactory.getLogger(Nvidia.class);
    private static Nvidia nvidia;
    public static Nvidia getProfile() {
        if (StringUtils.isNull(nvidia)) {
            nvidia = new Nvidia();
        }
        return nvidia;
    }

    Nvidia(){
        System.setProperty("nvml.path", System.getProperty("user.dir")+ "/dll/nvml.dll");
        try {
            NVMLJ.nvmlInit();
        } catch (NVMLJException e) {
            e.printStackTrace();
        }
    }

   void destroy(){
        try {
            NVMLJ.nvmlShutdown();
        } catch (NVMLJException e) {
            e.printStackTrace();
        }
    }


    public JSONObject getInfo() {
        JSONObject jsonObject =new JSONObject();
        try {
            //获取GPU数量
            int deviceCount = NVMLJ.nvmlDeviceGetCount();
            jsonObject.put("driverVersion",NVMLJ.nvmlSystemGetDriverVersion());

            JSONArray jsonArray=new JSONArray();
            jsonObject.put("devices",jsonArray);
            for(int i=0;i<deviceCount;i++) {
                JSONObject deviceJson=new JSONObject();
                jsonArray.add(deviceJson);
                NVMLDevice nvmlDevice = NVMLJ.nvmlDeviceGetHandleByIndex(0);
                //获取GPU名字
                deviceJson.put("name", nvmlDevice.nvmlDeviceGetName());
                //获取GPU温度
                deviceJson.put("temperature", nvmlDevice.nvmlDeviceGetTemperature(NVMLTemperatureSensors.NVML_TEMPERATURE_GPU));
                //获取GPU利用率
                NVMLUtilization nvmlUtilization = nvmlDevice.nvmlDeviceGetUtilizationRates();
                deviceJson.put("utilizationRates", nvmlUtilization.gpu);
                //获取显存情况 单位字节
                NVMLMemory nvmlMemory = nvmlDevice.nvmlDeviceGetMemoryInfo();
                deviceJson.put("memoryFree", nvmlMemory.free*1.0/ Constants.GB);
                deviceJson.put("memoryUsed", nvmlMemory.used*1.0/ Constants.GB);
                deviceJson.put("memoryTotal", nvmlMemory.total*1.0/ Constants.GB);
                //获取显卡功耗情况 单位毫瓦
                deviceJson.put("powerUsage",  nvmlDevice.nvmlDeviceGetPowerUsage()*1.0/Constants.W);
                //获取GPU唯一序列号
                //deviceJson.put("serial",nvmlDevice.nvmlDeviceGetSerial());
                //获取进程列表
               // NVMLProcessInfo[] nvmlProcessInfoArray =nvmlDevice.nvmlDeviceGetComputeRunningProcesses();
                //获取风扇使用率
               // deviceJson.put("fanSpeed",nvmlDevice.nvmlDeviceGetFanSpeed());
                //获取全球唯一UUID
                deviceJson.put("uuid",nvmlDevice.nvmlDeviceGetUUID());
            }

        } catch (NVMLJException e) {
            logger.error("nvidia信息获取失败");
            e.printStackTrace();
        }
        return jsonObject;
    }


}
