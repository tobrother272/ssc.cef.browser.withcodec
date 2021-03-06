/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Label;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.cef.browser.CefBrowser;
import ssccefbrowser.SSCCefBrowser;

/**
 * @author simplesolution.co
 */
public class TaskReadThread implements Runnable {

    //private variables
    Socket socket;
    DataInputStream input;
    DataOutputStream output;
    private CefBrowser browser;
    private Label messageLabel;
    private SSCCefBrowser form;

    //constructor
    public TaskReadThread(Socket socket, CefBrowser browser, Label messageLabel, SSCCefBrowser form) {
        this.socket = socket;
        this.browser = browser;
        this.messageLabel = messageLabel;
        this.form = form;
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        try {
            output = new DataOutputStream(socket.getOutputStream());

            //messageLabel.setText("#connected#" + ToolSetting.getInstance().account);
        } catch (Exception e) {
        }
        while (true) {
            if (socket.isClosed() || !socket.isConnected() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                System.exit(0);
            }
            try {
                input = new DataInputStream(socket.getInputStream());
                String message = input.readUTF();
                //messageLabel.setText(message);
                if (message.contains("exit")) {
                    System.exit(0);
                    break;
                }
                extractAction(message.replaceAll("#" + ToolSetting.getInstance().account + "#", ""));
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                System.exit(0);
                break;
            } catch (Exception s) {
                System.exit(0);
                break;
            }
        }
    }

    public boolean waitLoading(int timeout) {
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        try {
            while (currentTime < timeout) {
                currentTime = (System.currentTimeMillis() - startTime) / 1000;
                //messageLabel.setText("loading " + currentTime + "/" + timeout);
                //&&ToolSetting.getInstance().countRequest<=1
                if (!browser.isLoading()) {
                    //System.out.println("Chờ load timeout");
                    return true;
                }
                System.out.println("Chờ load " + currentTime + "/" + timeout + " còn " + ToolSetting.getInstance().countRequest + " requests");
                sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (currentTime >= timeout) {
            System.out.println("Chờ load timeout");
            return false;
        }
        return true;
    }

    public void load(String query, String lastID) {
        try {
            sendMessageToServer("Nhận lệnh load " + query);
            //messageLabel.setText("Nhận lệnh load " + query);
            browser.loadURL(query);
            if (!waitLoading(30)) {
                sendMessageToServer("#" + lastID + "#false");
            }
        } catch (Exception e) {
        }
        sendMessageToServer("#" + lastID + "#true");
    }

    public void click(String query, String lastID) {
        try {
            sendMessageToServer("Nhận lệnh click " + query);
            messageLabel.setText("Nhận lệnh click " + query);
            if (frame.length() == 0) {
                browser.executeJavaScript(query, browser.getURL(), 0);
            } else {
                browser.getFrame(frame).executeJavaScript(query, browser.getFrame(frame).getURL(), 0);
            }
            //sleep(1);
            if (!waitLoading(30)) {
                sendMessageToServer("#" + lastID + "#false");
            }
        } catch (Exception e) {
        }
        sendMessageToServer("#" + lastID + "#true");
    }

    public void exeJS(String query, String lastID) {
        try {
            sendMessageToServer("Nhận lệnh exeJS " + query);
            messageLabel.setText("Nhận lệnh exeJS " + query);
            if (frame.length() == 0) {
                browser.executeJavaScript(query, browser.getURL(), 0);
            } else {
                browser.getFrame(frame).executeJavaScript(query, browser.getFrame(frame).getURL(), 0);
            }
            //sleep(1);
            if (!waitLoading(30)) {
                sendMessageToServer("#" + lastID + "#false");
            }
        } catch (Exception e) {
        }
        sendMessageToServer("#" + lastID + "#true");
    }

    public void getJs(String query, String lastID) {
        try {
            sendMessageToServer("Nhận lệnh getjs " + query);
            //messageLabel.setText("Nhận lệnh click " + query);
            System.out.println("query window.cefQuery({request:'#" + lastID + "#jsvalue='+" + query + "})");
            if (frame.length() == 0) {
                browser.executeJavaScript("window.cefQuery({request:'#" + lastID + "#jsvalue='+" + query + "})", browser.getURL(), 0);
            } else {
                browser.getFrame(frame).executeJavaScript("window.cefQuery({request:'#" + lastID + "#jsvalue='+" + query + "})", browser.getFrame(frame).getURL(), 0);
            }
            //sleep(1);
            if (!waitLoading(30)) {
                sendMessageToServer("#" + lastID + "#false");
            }
        } catch (Exception e) {
        }
    }

    public void clickAndWait(String query, String lastID) {
        try {
            sendMessageToServer("Nhận lệnh click ");
            if (frame.length() == 0) {
                browser.executeJavaScript(query, browser.getURL(), 0);
            } else {
                browser.getFrame(frame).executeJavaScript(query, browser.getFrame(frame).getURL(), 0);
            }
            System.out.println("Đã click đang chờ");
            //sleep(2);
        } catch (Exception e) {
        }
        if (!waitLoading(30)) {
            sendMessageToServer("#" + lastID + "#false");
        } else {
            sendMessageToServer("#" + lastID + "#true");
        }
    }

    public void type(String query, String lastID) {
        try {
            sendMessageToServer("Nhận lệnh type " + query);
            //messageLabel.setText("Nhận lệnh type " + query);
            if (frame.length() == 0) {
                browser.executeJavaScript(query, browser.getURL(), 0);
                sleep(2);
                browser.executeJavaScript(query, browser.getURL(), 0);
            } else {
                browser.getFrame(frame).executeJavaScript(query, browser.getFrame(frame).getURL(), 0);
                sleep(1);
                browser.getFrame(frame).executeJavaScript(query, browser.getFrame(frame).getURL(), 0);
            }
            //sleep(2);
            if (!waitLoading(30)) {
                sendMessageToServer("#" + lastID + "#false");
            }
        } catch (Exception e) {
        }
        sendMessageToServer("#" + lastID + "#true");
    }
    private String lastID = "";
    private String frame = "";

    public String getLastID() {
        return lastID;
    }

    public void setLastID(String lastID) {
        this.lastID = lastID;
    }

    public void extractAction(String message) {
        try {

            lastID = "";
            //System.out.println("Received message "+message);
            if (message.split("lastID:")[1].contains("frame=")) {
                lastID = message.split("lastID:")[1].split("frame=")[0];
                frame = message.split("lastID:")[1].split("frame=")[1];
            } else {
                lastID = message.split("lastID:")[1];
                frame = "";
            }

            message = message.split("lastID:")[0];
            String query = message.split("\\|")[0];
            int time = 0;
            try {
                time = Integer.parseInt(message.split("\\|")[1]);
            } catch (Exception e) {
                time = 10;
            }
            message = query;
            if (message.startsWith("#LOAD#")) {
                load(message.replaceAll("#LOAD#", ""), lastID);
            } else if (message.startsWith("#TYPE#")) {
                type(message.replaceAll("#TYPE#", ""), lastID);
            } else if (message.startsWith("#CLICKANDWAITLOAD#")) {
                clickAndWait(message.replaceAll("#CLICKANDWAITLOAD#", ""), lastID);
            } else if (message.startsWith("#CLICK#")) {
                click(message.replaceAll("#CLICK#", ""), lastID);
            } else if (message.startsWith("#GETURL#")) {
                sendMessageToServer("#" + lastID + "#url=" + browser.getURL());
            } else if (message.startsWith("#WAIT#")) {
                waitLoading(30);
            } else if (message.startsWith("#GETJS#")) {
                getJs(message.replaceAll("#GETJS#", ""), lastID);
                //waitJSValue(30, id);
            } else if (message.startsWith("#EXEJS#")) {
                exeJS(message.replaceAll("#EXEJS#", ""), lastID);
                //waitJSValue(30, id);
            } else if (message.startsWith("#CURRENTURL#")) {
                sendMessageToServer("#CURRENTURL#" + browser.getURL());
            } else if (message.startsWith("#GETFRAMES#")) {

                sendMessageToServer("Nhận lệnh GETFRAMES");
                try {
                    String frames = "";
                    for (String frameName : browser.getFrameNames()) {
                        if (browser.getFrameNames().indexOf(frameName) == browser.getFrameNames().size() - 1) {
                            frames = frames + frameName;
                        } else {
                            frames = frames + frameName + ",";
                        }
                    }
                    sendMessageToServer("#" + lastID + "#jsvalue=" + frames);
                } catch (Exception e) {
                    sendMessageToServer("#" + lastID + "#False get frames");
                }

            } else if (message.startsWith("#BACK#")) {
                sendMessageToServer("Nhận lệnh back");
                browser.goBack();
            } else if (message.equals("exit")) {
                socket.close();
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadURl(String url) {
        browser.loadURL(url);
        if (!waitLoading(30)) {
            sendMessageToServer("#" + lastID + "#false");
        }
    }

    /*
    public void waitLoading(int timeOut) {
        sendMessageToServer("Nhận lệnh chờ load");
        if (!browser.isLoading()) {
            sendMessageToServer("loadsuccess");
        } else {
            sendMessageToServer("Đang load");
        }

    }
     */
    public void waitJSValue(int timeOut, String id) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        long startTime = System.currentTimeMillis();
        long currentTime = (System.currentTimeMillis() - startTime) / 1000;
        while (currentTime < timeOut) {
            currentTime = (System.currentTimeMillis() - startTime) / 1000;
            //sendMessageToServer("current result "+currentJs.getCurrentJSResult());
            //if (currentJs.getCurrentJSResult() == null) {
            //    continue;
            //}
            //if (currentJs.getCurrentJSResult().startsWith(id)) {
            //    sendMessageToServer(currentJs.getCurrentJSResult());
            //    break;
            //}
        }
        sendMessageToServer("timeout");
    }

    public boolean firstMessage = true;

    public void sendMessageToServer(String message) {
        try {
            System.out.println(message);
            output.writeUTF(message);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
