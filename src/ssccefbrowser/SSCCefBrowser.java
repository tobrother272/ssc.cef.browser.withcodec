/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssccefbrowser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.OS;
import org.cef.SystemBootstrap;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;
import org.cef.handler.CefLoadHandler;
import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.network.CefCookieManager;
import tests.detailed.BrowserFrame;
import tests.detailed.dialog.DownloadDialog;
import tests.detailed.handler.AppHandler;
import tests.detailed.handler.ContextMenuHandler;
import tests.detailed.handler.DragHandler;
import tests.detailed.handler.JSDialogHandler;
import tests.detailed.handler.KeyboardHandler;
import tests.detailed.handler.MessageRouterHandler;
import tests.detailed.handler.RequestHandler;
import tests.detailed.ui.ControlPanel;
import tests.detailed.ui.MenuBar;
import tests.detailed.ui.StatusPanel;
import tests.detailed.util.DataUri;
import util.TaskReadThread;
import util.ToolSetting;

/**
 *
 * @author PC
 */
public class SSCCefBrowser extends BrowserFrame {

    private static final long serialVersionUID = -2295538706810864538L;
    private TaskReadThread task;
    private Label messageLabel;

    public static void main(String[] args) {

        System.setProperty("java.library.path", System.getProperty("user.dir")
                + File.separator + "dll" + ";"
                + System.getProperty("user.dir") + File.separator + "dll" + File.separator + "cef.pak");
        try {
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
            if (OS.isWindows()) {
                SystemBootstrap.loadLibrary("jawt");
                SystemBootstrap.loadLibrary("chrome_elf");
                SystemBootstrap.loadLibrary("libcef");
                SystemBootstrap.loadLibrary("jcef");
            } else if (OS.isLinux()) {
                SystemBootstrap.loadLibrary("cef");
            }
        } catch (Exception e) {
        }

        List<String> arryAry = new ArrayList<>();
        arryAry.addAll(Arrays.asList(args));
        for (String string : arryAry) {
            if (string.startsWith("port:")) {
                ToolSetting.getInstance().socketPort = Integer.parseInt(string.replaceAll("port:", ""));
            } else if (string.startsWith("profile:")) {
                ToolSetting.getInstance().profile = string.replaceAll("profile:", "");
            } else if (string.startsWith("proxy:")) {
                ToolSetting.getInstance().proxyHost = string.replaceAll("proxy:", "").split(":")[0];
                ToolSetting.getInstance().proxyPort = Integer.parseInt(string.replaceAll("proxy:", "").split(":")[0]);
            } else if (string.startsWith("useragent:")) {
                ToolSetting.getInstance().userAgent = string.replaceAll("useragent:", "");
            } else if (string.startsWith("account:")) {
                ToolSetting.getInstance().account = string.replaceAll("account:", "");
            } else if (string.startsWith("w:")) {
                ToolSetting.getInstance().w = Integer.parseInt(string.replaceAll("w:", ""));
            } else if (string.startsWith("h:")) {
                ToolSetting.getInstance().h = Integer.parseInt(string.replaceAll("h:", ""));
            } else if (string.startsWith("fake:")) {
                ToolSetting.getInstance().fake = Boolean.parseBoolean(string.replaceAll("fake:", ""));
            }
        }
        ToolSetting.getInstance().init();
        if (!CefApp.startup(args)) {
            System.out.println("Startup initialization failed!");
            return;
        }

        // OSR mode is enabled by default on Linux.
        // and disabled by default on Windows and Mac OS X.
        boolean osrEnabledArg = false;
        boolean transparentPaintingEnabledArg = false;
        boolean createImmediately = false;
        for (String arg : args) {
            arg = arg.toLowerCase();
            if (arg.equals("--off-screen-rendering-enabled")) {
                osrEnabledArg = true;
            } else if (arg.equals("--transparent-painting-enabled")) {
                transparentPaintingEnabledArg = true;
            } else if (arg.equals("--create-immediately")) {
                createImmediately = true;
            }
        }

        System.out.println("Offscreen rendering " + (osrEnabledArg ? "enabled" : "disabled"));

        // MainFrame keeps all the knowledge to display the embedded browser
        // frame.
        final SSCCefBrowser frame = new SSCCefBrowser(
                osrEnabledArg, transparentPaintingEnabledArg, createImmediately, args);
        frame.setSize(ToolSetting.getInstance().w, ToolSetting.getInstance().h);
        frame.setVisible(true);
    }

    private final CefClient client_;
    private String errorMsg_ = "";
    private ControlPanel control_pane_;
    private StatusPanel status_panel_;
    private boolean browserFocus_ = true;
    private boolean osr_enabled_;
    private boolean transparent_painting_enabled_;
    private Socket socket;
    private boolean loading;

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public static void click(Component target, int x, int y) {
        MouseEvent press, release, click;
        Point point;
        long time;

        point = new Point(x, y);

        SwingUtilities.convertPointToScreen(point, target);

        time = System.currentTimeMillis();
        press = new MouseEvent(target, MouseEvent.MOUSE_PRESSED, time, 0, x, y, point.x, point.y, 1, false, MouseEvent.BUTTON1);
        release = new MouseEvent(target, MouseEvent.MOUSE_RELEASED, time, 0, x, y, point.x, point.y, 1, false, MouseEvent.BUTTON1);
        click = new MouseEvent(target, MouseEvent.MOUSE_CLICKED, time, 0, x, y, point.x, point.y, 1, false, MouseEvent.BUTTON1);

        target.dispatchEvent(press);
        target.dispatchEvent(click);
        target.dispatchEvent(release);
    }

    public SSCCefBrowser(boolean osrEnabled, boolean transparentPaintingEnabled,
            boolean createImmediately, String[] args) {
        this.osr_enabled_ = true;
        this.transparent_painting_enabled_ = transparentPaintingEnabled;

        CefApp myApp;
        if (CefApp.getState() != CefApp.CefAppState.INITIALIZED) {
            // 1) CefApp is the entry point for JCEF. You can pass
            //    application arguments to it, if you want to handle any
            //    chromium or CEF related switches/attributes in
            //    the native world.
            /*
            CefSettings settings = new CefSettings();
            settings.windowless_rendering_enabled = osrEnabled;
            // try to load URL "about:blank" to see the background color
            settings.background_color = settings.new ColorType(100, 255, 242, 211);
            settings.user_agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36";
            //if (ToolSetting.getInstance().profile.length() != 0) {
            //    settings.cache_path = ToolSetting.getInstance().profile;
            //}
             */
            String[] args2 = new String[]{
                "--remote-debugging-port=12345",
                // "--cache-path=\"C:\\Users\\PC\\Desktop\\User Data\\Default\""
                "-–disable-web-security",
                "--enable-media-stream=1",
                "--autoplay-policy=no-user-gesture-required",
                "--useflash",
                "--skipframes",
                "--unique-process-id=1amfxmkz",
                "--profile",
                "--extensions",
                "--no-sandbox",
                "--lang=en-US",
                "--log-severity=disable",
                "--disable-features=MimeHandlerViewInCrossProcessFrame",
                "--high-dpi-support",
                "--disable-site-isolation-trials",
                "--disable-gpu",
                "--disable-gpu-compositing",
                "--disable-gpu-shader-disk-cache",
                "--ignore-certificate-errors",
                "--enable-widevine-cdm",
                "--enable-blink-features=WebBluetooth,Badging,InstalledApp,WakeLock,Notifications,WebAnimationsAPI,AOMPhase1 en 0 1 1 prof/S6PzeRAd",
                "--allow-running-insecure-content"
            };
            myApp = CefApp.getInstance(args2, ToolSetting.getInstance().getCefSettings());
            CefApp.CefVersion version = myApp.getVersion();
            System.out.println("Using:\n" + version);
            //    We're registering our own AppHandler because we want to
            //    add an own schemes (search:// and client://) and its corresponding
            //    protocol handlers. So if you enter "search:something on the web", your
            //    search request "something on the web" is forwarded to www.google.com
            CefApp.addAppHandler(new AppHandler(args));
        } else {
            myApp = CefApp.getInstance();
        }

        //    By calling the method createClient() the native part
        //    of JCEF/CEF will be initialized and an  instance of
        //    CefClient will be created. You can create one to many
        //    instances of CefClient.
        client_ = myApp.createClient();

        // 2) You have the ability to pass different handlers to your
        //    instance of CefClient. Each handler is responsible to
        //    deal with different informations (e.g. keyboard input).
        //
        //    For each handler (with more than one method) adapter
        //    classes exists. So you don't need to override methods
        //    you're not interested in.
        DownloadDialog downloadDialog = new DownloadDialog(this);
        client_.addContextMenuHandler(new ContextMenuHandler(this));
        client_.addDownloadHandler(downloadDialog);
        client_.addDragHandler(new DragHandler());
        client_.addJSDialogHandler(new JSDialogHandler());
        client_.addKeyboardHandler(new KeyboardHandler());
        client_.addRequestHandler(new RequestHandler(this));

        //    Beside the normal handler instances, we're registering a MessageRouter
        //    as well. That gives us the opportunity to reply to JavaScript method
        //    calls (JavaScript binding). We're using the default configuration, so
        //    that the JavaScript binding methods "cefQuery" and "cefQueryCancel"
        //    are used.
        // 2.1) We're overriding CefDisplayHandler as nested anonymous class
        //      to update our address-field, the title of the panel as well
        //      as for updating the status-bar on the bottom of the browser
        client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
                control_pane_.setAddress(browser, url);
            }

            @Override
            public void onTitleChange(CefBrowser browser, String title) {
                setTitle(title);
            }

            @Override
            public void onStatusMessage(CefBrowser browser, String value) {
                status_panel_.setStatusText(value);
            }
        });

        // 2.2) To disable/enable navigation buttons and to display a prgress bar
        //      which indicates the load state of our website, we're overloading
        //      the CefLoadHandler as nested anonymous class. Beside this, the
        //      load handler is responsible to deal with (load) errors as well.
        //      For example if you navigate to a URL which does not exist, the
        //      browser will show up an error message.
        client_.addLoadHandler(new CefLoadHandlerAdapter() {
            @Override
            public void onLoadingStateChange(CefBrowser browser, boolean isLoading,
                    boolean canGoBack, boolean canGoForward) {
                control_pane_.update(browser, isLoading, canGoBack, canGoForward);
                status_panel_.setIsInProgress(isLoading);
                setLoading(isLoading);
                if (!isLoading && !errorMsg_.isEmpty()) {
                    browser.loadURL(DataUri.create("text/html", errorMsg_));
                    errorMsg_ = "";
                }
            }

            @Override
            public void onLoadError(CefBrowser browser, CefFrame frame, CefLoadHandler.ErrorCode errorCode,
                    String errorText, String failedUrl) {
                if (errorCode != CefLoadHandler.ErrorCode.ERR_NONE && errorCode != CefLoadHandler.ErrorCode.ERR_ABORTED) {
                    errorMsg_ = "<html><head>";
                    errorMsg_ += "<title>Error while loading</title>";
                    errorMsg_ += "</head><body>";
                    errorMsg_ += "<h1>" + errorCode + "</h1>";
                    errorMsg_ += "<h3>Failed to load " + failedUrl + "</h3>";
                    errorMsg_ += "<p>" + (errorText == null ? "" : errorText) + "</p>";
                    errorMsg_ += "</body></html>";
                    browser.stopLoad();
                }
            }
        });

        // Create the browser.
        messageLabel = new Label("......................");
        CefBrowser browser = client_.createBrowser("https://music.apple.com/us/browse", osrEnabled, transparentPaintingEnabled, null);
        setBrowser(browser);
        JPanel contentPanel = createContentPanel();

        /*
        
        JButton backButton_ = new JButton("Back");
        backButton_.setFocusable(false);
        backButton_.setAlignmentX(LEFT_ALIGNMENT);
        backButton_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String stringTokenizer : getBrowser().getFrameNames()) {
                    System.out.println(""+stringTokenizer);
                    
                }
                 getBrowser().getFrame("Widget").executeJavaScript("document.getElementById('account_name_text_field').value='xxxxxxxx'", getBrowser().getFrame("Widget").getURL(), 0);
              //getBrowser().executeJavaScript("", getBrowser().getURL(), 0);
            }
        });
        
        */
        
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(messageLabel, BorderLayout.PAGE_START);

        // Clear focus from the browser when the address field gains focus.
        control_pane_.getAddressField().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) {
                    return;
                }
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                control_pane_.getAddressField().requestFocus();
            }
        });

        // Clear focus from the address field when the browser gains focus.
        client_.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
                if (browserFocus_) {
                    return;
                }
                browserFocus_ = true;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                browser.setFocus(true);
            }

            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
                browserFocus_ = false;
            }
        });

        if (createImmediately) {
            browser.createImmediately();
        }

        contentPanel.add(getBrowser().getUIComponent(), BorderLayout.CENTER);
        MenuBar menuBar = new MenuBar(this, browser, control_pane_, downloadDialog, CefCookieManager.getGlobalManager());
        menuBar.addBookmark("Canvas checking", "https://browserleaks.com/canvas");
        menuBar.addBookmark("Audio Test", "https://music.apple.com/us/listen-now?at=1000l4QJ&ct=402&itscg=10000&itsct=402x");
        menuBar.addBookmark("Youtube", "https://www.youtube.com/");
        menuBar.addBookmark("Test Mp3", "https://www.nhaccuatui.com/bai-hat/dong-doi-dan-truong.NelwjxRBA6g4.html");
        menuBar.addBookmark("Test YTB Video", "https://www.youtube.com/watch?v=LTByTPP_NxY");
        menuBar.addBookmark("Version", "chrome://version");

        menuBar.addBookmarkSeparator();
        setJMenuBar(menuBar);

    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        control_pane_ = new ControlPanel(getBrowser());
        if (ToolSetting.getInstance().socketPort != 0) {
            try {
                // Create a socket to connect to the server
                socket = new Socket("127.0.0.1", ToolSetting.getInstance().socketPort);
                task = new TaskReadThread(socket, getBrowser(), messageLabel, this);
                Thread thread = new Thread(task);
                thread.start();
                CefMessageRouter msgRouter = CefMessageRouter.create();
                msgRouter.addHandler(new MessageRouterHandler(task), true);
                //msgRouter.addHandler(new MessageRouterHandlerEx(client_), false);
                client_.addMessageRouter(msgRouter);
            } catch (Exception ex) {

            }
        } else {
            CefMessageRouter msgRouter = CefMessageRouter.create();
            msgRouter.addHandler(new MessageRouterHandler(task), true);
            //msgRouter.addHandler(new MessageRouterHandlerEx(client_), false);
            client_.addMessageRouter(msgRouter);
        }
        status_panel_ = new StatusPanel();
        contentPanel.add(control_pane_, BorderLayout.NORTH);
        contentPanel.add(status_panel_, BorderLayout.SOUTH);
        return contentPanel;
    }

    public boolean isOsrEnabled() {
        return osr_enabled_;
    }

    public boolean isTransparentPaintingEnabled() {
        return transparent_painting_enabled_;
    }

}
