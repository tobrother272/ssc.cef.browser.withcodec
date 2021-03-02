/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;

/**
 * @author PC
 */
public class Facking {

    public static void faking(CefBrowser browser, CefFrame frame) {
        Facking.fakeUserAgent(ToolSetting.getInstance().userAgent, browser, frame);
        //Facking.fakeCanvas("", browser, frame);
        //Facking.fakeAppVersion(ToolSetting.getInstance().appVersion, browser, frame);
        //Facking.fakeOSCpu(ToolSetting.getInstance().osCpu, browser, frame);
        //Facking.fakePlatform(ToolSetting.getInstance().platForm, browser, frame);
        //Facking.fakeLocalHost(ToolSetting.getInstance().localIp, browser, frame);
    }
    
    
    public static void fakeUserAgent(String userAgent, CefBrowser browser, CefFrame frame) {
        String jsFakeUseragent
                = "Object.defineProperty(Navigator.prototype, \"userAgent\", {\n"
                + "        get() {\n"
                + "            return '" + userAgent + "';\n"
                + "        }\n"
                + "    });";
        browser.executeJavaScript(jsFakeUseragent, browser.getURL(), 0);
        frame.executeJavaScript(jsFakeUseragent, browser.getURL(), 0);
    }

    public static void fakeAppVersion(String userAgent, CefBrowser browser, CefFrame frame) {
        String jsFakeAppVersion
                = "Object.defineProperty(Navigator.prototype, \"appVersion\", {\n"
                + "        get() {\n"
                + "            return '" + userAgent + "';\n"
                + "        }\n"
                + "    });";
        browser.executeJavaScript(jsFakeAppVersion, browser.getURL(), 0);
        frame.executeJavaScript(jsFakeAppVersion, browser.getURL(), 0);
    }

    public static void fakePlatform(String platForm, CefBrowser browser, CefFrame frame) {
        String jsPlatform
                = "Object.defineProperty(Navigator.prototype, \"platform\", {\n"
                + "        get() {\n"
                + "            return '" + platForm + "';\n"
                + "        }\n"
                + "    });";
        browser.executeJavaScript(jsPlatform, browser.getURL(), 0);
        frame.executeJavaScript(jsPlatform, browser.getURL(), 0);
    }

    public static void fakeOSCpu(String osCpu, CefBrowser browser, CefFrame frame) {
        String jsPlatform
                = "Object.defineProperty(Navigator.prototype, \"platform\", {\n"
                + "        get() {\n"
                + "            return '" + osCpu + "';\n"
                + "        }\n"
                + "    });";
        browser.executeJavaScript(jsPlatform, browser.getURL(), 0);
        frame.executeJavaScript(jsPlatform, browser.getURL(), 0);
    }

    public static void fakeLocalHost(String localHost, CefBrowser browser, CefFrame frame) {
        String jsLocalIp=
                 "const setLocalDescription = RTCPeerConnection.prototype.setLocalDescription;\n"
                + "    Object.defineProperty(RTCPeerConnection.prototype, \"setLocalDescription\", {\n"
                + "        \"value\": function () {\n"
                + "            var onicecandidate = this.onicecandidate;\n"
                + "            this.onicecandidate = function (evt) {\n"
                + "                evt.candidate.candidate = evt.candidate.candidate.replace(evt.candidate.candidate.split(\" \")[4], '" + localHost + "');\n"
                + "                onicecandidate(evt);\n"
                + "            };\n"
                + "            return setLocalDescription.apply(this, arguments);\n"
                + "        }\n"
                + "    });\n";
        browser.executeJavaScript(jsLocalIp, browser.getURL(), 0);
        frame.executeJavaScript(jsLocalIp, browser.getURL(), 0);
    }

    public static void fakeCanvas(String userAgent, CefBrowser browser, CefFrame frame) {
        String casvas = "  var inject = function () {\n"
                + "    const toBlob = HTMLCanvasElement.prototype.toBlob;\n"
                + "    const toDataURL = HTMLCanvasElement.prototype.toDataURL;\n"
                + "    const getImageData = CanvasRenderingContext2D.prototype.getImageData;"
                + "     var noisify = function (canvas, context) {\n"
                + "        const shift = {\n"
                + "            'r': Math.floor(0.1 * 10) - 5,\n"
                + "            'g': Math.floor(1.0 * 10) - 5,\n"
                + "            'b': Math.floor(0.7 * 10) - 5,\n"
                + "            'a': Math.floor(0.3 * 10) - 5\n"
                + "        };\n"
                + "        const width = canvas.width, height = canvas.height;\n"
                + "        const imageData = getImageData.apply(context, [0, 0, width, height]);\n"
                + "        for (let i = 0; i < height; i++) {\n"
                + "            for (let j = 0; j < width; j++) {\n"
                + "                const n = ((i * (width * 4)) + (j * 4));\n"
                + "                imageData.data[n + 0] = imageData.data[n + 0] + shift.r;\n"
                + "                imageData.data[n + 1] = imageData.data[n + 1] + shift.g;\n"
                + "                imageData.data[n + 2] = imageData.data[n + 2] + shift.b;\n"
                + "                imageData.data[n + 3] = imageData.data[n + 3] + shift.a;\n"
                + "            }\n"
                + "        };\n"
                + "        context.putImageData(imageData, 0, 0);\n"
                + "  };\n"
                + "  Object.defineProperty(HTMLCanvasElement.prototype, \"toBlob\", {\n"
                + "        \"value\": function () {\n"
                + "            noisify(this, this.getContext(\"2d\"));\n"
                + "            return toBlob.apply(this, arguments);\n"
                + "        }\n"
                + "  });"
                + "  Object.defineProperty(HTMLCanvasElement.prototype, \"toDataURL\", {\n"
                + "        \"value\": function () {\n"
                + "            noisify(this, this.getContext(\"2d\"));\n"
                + "            return toDataURL.apply(this, arguments);\n"
                + "        }\n"
                + "    });"
                + "  Object.defineProperty(CanvasRenderingContext2D.prototype, \"getImageData\", {\n"
                + "        \"value\": function () {\n"
                + "            noisify(this.canvas, this);\n"
                + "            return getImageData.apply(this, arguments);\n"
                + "        }\n"
                + "    });\n"
                + " }\n"
                + "       var script_1 = document.createElement('script');\n"
                + "       script_1.id = \"mainScript\";"
                + "       script_1.textContent=\"(\" + inject + \")();\";"
                + "       var script_2 = document.createElement('script');\n"
                + "       window.top.document.documentElement.appendChild(script_1);\n"
                + "       script_2.textContent = `{\n"
                + "	    const iframes = window.top.document.querySelectorAll(\"iframe[sandbox]\");\n"
                + "	    for (var i = 0; i < iframes.length; i++) {\n"
                + "	      if (iframes[i].contentWindow) {\n"
                + "	        if (iframes[i].contentWindow.CanvasRenderingContext2D) {\n"
                + "	          iframes[i].contentWindow.CanvasRenderingContext2D.prototype.getImageData = CanvasRenderingContext2D.prototype.getImageData;\n"
                + "	        }\n"
                + "	        if (iframes[i].contentWindow.HTMLCanvasElement) {\n"
                + "	          iframes[i].contentWindow.HTMLCanvasElement.prototype.toBlob = HTMLCanvasElement.prototype.toBlob;\n"
                + "	          iframes[i].contentWindow.HTMLCanvasElement.prototype.toDataURL = HTMLCanvasElement.prototype.toDataURL;\n"
                + "	        }\n"
                + "	      }\n"
                + "	    }\n"
                + "	   }`;\n"
                + "        //\n"
                + "        //window.top.document.documentElement.appendChild(script_2);"
                + "";
        browser.executeJavaScript(casvas, browser.getURL(), 0);
        frame.executeJavaScript(casvas, browser.getURL(), 0);
    }
}
