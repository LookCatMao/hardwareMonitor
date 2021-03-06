package com.web;

import com.alibaba.fastjson.JSONObject;
import com.hardware.*;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
/**
 * @Author: luo
 * @Description:
 * @Data: 20:01 2021/11/3
 */
public class WebSocketServerManager extends WebSocketServer{

    public WebSocketServerManager() throws UnknownHostException {
    }

    public WebSocketServerManager(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        System.out.println("websocket Server start at port:"+port);
    }


    /**
     * 触发连接事件
     */
    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        System.out.println("new connection ===" + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    /**
     *
     * 连接断开时触发关闭事件
     */
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    /**
     * 客户端发送消息到服务器时触发事件
     */
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("you have a new message: " + message);
        //向客户端发送消息
        JSONObject resultJson = new JSONObject();
        resultJson.put("gpu", Nvidia.getProfile().getInfo());

        JSONObject cpuJson = Cpu.getProfile().getInfo();
       // cpuJson.put("temperature", SystemInfoUtils.getCPUTemperature());
        resultJson.put("cpu", cpuJson);
        resultJson.put("hdd", HardDiskDrive.getInfo());
        resultJson.put("memory", Memory.getProfile().getInfo());
        resultJson.put("net", Network.getProfile().getNetInfo());
        resultJson.put("ethernet", Network.getProfile().ethernet());
        conn.send(resultJson.toJSONString());
    }

    /**
     * 触发异常事件
     */
    @Override
    public void onError(WebSocket conn, Exception e) {
        //e.printStackTrace();
        if( conn != null ) {
            //some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {

    }
}
