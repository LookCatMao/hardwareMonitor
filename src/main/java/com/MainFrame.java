package com;


import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MainFrame extends JFrame implements ActionListener{

    private static final long serialVersionUID = -7078030311369039390L;
    private JMenu menu;
    private JMenuBar jmenuBar;
    private String [] jmItemName = {"置于托盘","系统退出"};

    public MainFrame(){
        super("监控服务");
        init();
        this.setSize(500,400);
        this.setJMenuBar(jmenuBar);
        this.setLocationRelativeTo(null);
        systemTray();    //系统托盘
    }

    /**
     * 初始化界面
     */
    public void init(){
        menu = new JMenu("系统窗体");
        for(int i=0; i<jmItemName.length; i++){
            JMenuItem menuItem = new JMenuItem(jmItemName[i]);
            menuItem.addActionListener(this);
            menu.add(menuItem);
        }
        this.jmenuBar = new JMenuBar();
        this.jmenuBar.add(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actions = e.getActionCommand();
        if("置于托盘".equals(actions)){
            this.setVisible(false);
        }
        if("系统退出".equals(actions)){
            System.exit(0);
        }

    }

    /**系统托盘图标处理.*/
    private void  systemTray(){
        //判断系统是否支持托盘功能.
        if(SystemTray.isSupported()){
            //获得图片路径
            URL resource = this.getClass().getResource("/systray.jpg");
            //创建图片对象
            ImageIcon icon = new ImageIcon(resource);
            //创建弹出菜单对象
            PopupMenu popupMenu = new PopupMenu();
            //创建弹出菜单中的退出项
            MenuItem itemExit = new MenuItem("out");
            //创建弹出菜单中的显示主窗体项.
            MenuItem itemShow = new MenuItem("show");
            //给退出像添加事件监听
            itemExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            //给窗体最小化添加事件监听.
            itemShow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                }
            });
            popupMenu.add(itemExit);
            popupMenu.add(itemShow);
            TrayIcon trayIcon = new TrayIcon(icon.getImage(),"test",popupMenu);
            SystemTray sysTray = SystemTray.getSystemTray();
            try {
                sysTray.add(trayIcon);
            } catch (AWTException e1) {    }
        }
    }

}
