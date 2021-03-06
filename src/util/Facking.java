/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefCommandLine;
import org.cef.network.CefRequest;

/**
 * @author PC
 */
public class Facking {

    private static void faking(CefBrowser browser, CefFrame frame, String function) {
        //System.out.println("browser.getURL() " + browser.getURL() + " " + function);
        //System.out.println("frame.getURL() " + frame.getURL() + " " + function);
        Facking.fakeUserAgent(ToolSetting.getInstance().userAgent, browser, frame);
        Facking.fakeLocalHost(ToolSetting.getInstance().localIp, browser, frame);
        Facking.fakeFont(browser, frame);
        Facking.fakeAudioContext(browser, frame);
        Facking.fakeCanvas(browser, frame);
        Facking.fakeOpenGL(browser, frame);

    }

    public static void fakingLoadEnd(CefBrowser browser, CefFrame frame) {
        if (!ToolSetting.getInstance().fakingLoadEnd.equals(browser.getURL()) && (frame.getURL().length() != 0 && !ToolSetting.getInstance().fakingLoadEnd.equals(frame.getURL()))) {
            faking(browser, frame, "Facking endLoad ");
            if (!ToolSetting.getInstance().fakingLoadEnd.equals(browser.getURL())) {
                ToolSetting.getInstance().fakingLoadEnd = browser.getURL();
            } else {
                ToolSetting.getInstance().fakingLoadEnd = browser.getURL();
            }
        }
    }

    public static void fakingBeforeLoad(CefBrowser browser, CefFrame frame) {
        if (!ToolSetting.getInstance().fakingBeforeLoad.equals(browser.getURL()) && (frame.getURL().length() != 0 && !ToolSetting.getInstance().fakingBeforeLoad.equals(frame.getURL()))) {
            faking(browser, frame, "Facking beforeLoad ");
            if (!ToolSetting.getInstance().fakingBeforeLoad.equals(browser.getURL())) {
                ToolSetting.getInstance().fakingBeforeLoad = browser.getURL();
            } else {
                ToolSetting.getInstance().fakingBeforeLoad = browser.getURL();
            }
        }
    }

    public static void fakingResourceRequestHandler(CefBrowser browser, CefFrame frame) {
        if (!ToolSetting.getInstance().fakingResourceRequestHandler.equals(browser.getURL()) && (frame.getURL().length() != 0 && !ToolSetting.getInstance().fakingResourceRequestHandler.equals(frame.getURL()))) {
            faking(browser, frame, "Facking ResourceRequestHandler ");
            if (!ToolSetting.getInstance().fakingResourceRequestHandler.equals(browser.getURL())) {
                ToolSetting.getInstance().fakingResourceRequestHandler = browser.getURL();
            } else {
                ToolSetting.getInstance().fakingResourceRequestHandler = browser.getURL();
            }
        }
    }

    public static void fakingOnBeforeResourceLoad(CefBrowser browser, CefFrame frame) {
        if (!ToolSetting.getInstance().fakingOnBeforeResourceLoad.equals(browser.getURL()) && (frame.getURL().length() != 0 && !ToolSetting.getInstance().fakingOnBeforeResourceLoad.equals(frame.getURL()))) {
            faking(browser, frame, "Facking OnBeforeResourceLoad ");
            if (!ToolSetting.getInstance().fakingOnBeforeResourceLoad.equals(browser.getURL())) {
                ToolSetting.getInstance().fakingOnBeforeResourceLoad = browser.getURL();
            } else {
                ToolSetting.getInstance().fakingOnBeforeResourceLoad = browser.getURL();
            }
        }
    }

    public static void fakeUserAgent(String userAgent, CefBrowser browser, CefFrame frame) {
        String jsFakeUseragent
                = "Object.defineProperty(Navigator.prototype, \"userAgent\", {\n"
                + "        get() {\n"
                + "            return '" + ToolSetting.getInstance().userAgent + "';\n"
                + "        }\n"
                + "    });"
                + "Object.defineProperty(Navigator.prototype, \"appName\", {\n"
                + "          get() {\n"
                + "              return  '" + ToolSetting.getInstance().appName + "';\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"product\", {\n"
                + "          get() {\n"
                + "              return  '" + ToolSetting.getInstance().product + "';\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"vendorSub\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().vendorSub + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"platform\", {\n"
                + "          get() {\n"
                + "              return  '" + ToolSetting.getInstance().platform + "';\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"appCodeName\", {\n"
                + "          get() {\n"
                + "              return  '" + ToolSetting.getInstance().appCodeName + "';\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"productSub\", {\n"
                + "          get() {\n"
                + "              return  '" + ToolSetting.getInstance().productSub + "';\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"vendor\", {\n"
                + "          get() {\n"
                + "              return  '" + ToolSetting.getInstance().vendor + "';\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"deviceMemory\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().deviceMemory + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Screen.prototype, \"availTop\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().availTop + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Screen.prototype, \"colorDepth\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().colorDepth + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Screen.prototype, \"pixelDepth\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().pixelDepth + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Screen.prototype, \"availWidth\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().availWidth + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Screen.prototype, \"height\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().height + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Screen.prototype, \"availHeight\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().availHeight + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Screen.prototype, \"width\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().width + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"plugins\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().plugins + ";\n"
                + "          }"
                + " });"
                + "Object.defineProperty(Navigator.prototype, \"mimeTypes\", {\n"
                + "          get() {\n"
                + "              return  " + ToolSetting.getInstance().mimes + ";\n"
                + "          }"
                + " });";
        browser.executeJavaScript(jsFakeUseragent, browser.getURL(), 0);
        frame.executeJavaScript(jsFakeUseragent, frame.getURL(), 0);
    }

    public static void fakeLocalHost(String localHost, CefBrowser browser, CefFrame frame) {
        String jsLocalIp
                = "const createOffer = RTCPeerConnection.prototype.createOffer;\n"
                + "    Object.defineProperty(RTCPeerConnection.prototype, \"createOffer\", {\n"
                + "        \"value\": function () {\n"
                + "            var onicecandidate = this.onicecandidate;\n"
                + "            this.onicecandidate = function (evt) {\n"
                + "                evt.candidate.candidate = evt.candidate.candidate.replace(evt.candidate.candidate.split(\" \")[4], '" + localHost + "');\n"
                + "                onicecandidate(evt);\n"
                + "            };\n"
                + "            return createOffer.apply(this, arguments);\n"
                + "        }\n"
                + "    });\n";
        browser.executeJavaScript(jsLocalIp, browser.getURL(), 0);
        frame.executeJavaScript(jsLocalIp, frame.getURL(), 0);
    }

    public static void fakeCanvas(CefBrowser browser, CefFrame frame) {
        String casvas = "  var injectCanvas = function () {\n"
                + "    const toBlob = HTMLCanvasElement.prototype.toBlob;\n"
                + "    const toDataURL = HTMLCanvasElement.prototype.toDataURL;\n"
                + "    const getImageData = CanvasRenderingContext2D.prototype.getImageData;"
                + "     var noisify = function (canvas, context) {\n"
                + "        const shift = {\n"
                + "            'r': Math.floor(" + ToolSetting.getInstance().canvasNoiseR + " * 10) - 5,\n"
                + "            'g': Math.floor(" + ToolSetting.getInstance().canvasNoiseG + " * 10) - 5,\n"
                + "            'b': Math.floor(" + ToolSetting.getInstance().canvasNoiseB + " * 10) - 5,\n"
                + "            'a': Math.floor(" + ToolSetting.getInstance().canvasNoiseA + " * 10) - 5\n"
                + "        };\n"
                + "        const width = canvas.width, height = canvas.height;\n"
                + "        const imageData = getImageData.apply(context, [0, 0, width, height]);\n"
                + "        for (let i = 0; i < height; i++) {\n"
                + "            for (let j = 0; j < width; j++) {\n"
                + "                const n = ((i * (width * 4)) + (j * 4));\n"
                + "                imageData.data[n + 0] = imageData.data[n + 0] +shift.r;\n"
                + "                imageData.data[n + 1] = imageData.data[n + 1] +shift.g;\n"
                + "                imageData.data[n + 2] = imageData.data[n + 2] +shift.b;\n"
                + "                imageData.data[n + 3] = imageData.data[n + 3] +shift.a;\n"
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
                + " }\n";
        String injectFrame = "    var script_1 = document.createElement('script');\n"
                + "    script_1.id = \"mainScript\";\n"
                + "    script_1.textContent = \"(\" + injectCanvas + \")()\";\n"
                + "    document.documentElement.appendChild(script_1); ";
        String runJs = "injectCanvas();";
        String inject = "";
        //signin/
        if (browser.getURL().startsWith("https://accounts.google.com/") || frame.getURL().startsWith("https://accounts.google.com/")) {
            inject = runJs;
        } else {
            inject = injectFrame;
        }

        String frameAdv = "    for (var i = 0; i < [...window.top.document.querySelectorAll(\"iframe[sandbox]\")].length; i++) {\n"
                + "	      if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow) {\n"
                + "	        if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.CanvasRenderingContext2D) {\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.CanvasRenderingContext2D.prototype.getImageData = CanvasRenderingContext2D.prototype.getImageData;\n"
                + "	        }\n"
                + "	        if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.HTMLCanvasElement) {\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.HTMLCanvasElement.prototype.toBlob = HTMLCanvasElement.prototype.toBlob;\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.HTMLCanvasElement.prototype.toDataURL = HTMLCanvasElement.prototype.toDataURL;\n"
                + "	        }\n"
                + "	      }\n"
                + "	    };console.log(`xxxxxx`)\n";
        browser.executeJavaScript(casvas + inject + frameAdv, browser.getURL(), 0);
        frame.executeJavaScript(casvas + inject + frameAdv, frame.getURL(), 0);

    }

    public static void fakeFont(CefBrowser browser, CefFrame frame) {
        String font = "  var injectFont = function () {\n"
                + "     Object.defineProperty(HTMLElement.prototype, \"offsetHeight\", {\n"
                + "        get() {\n"
                + "            const height = Math.floor(this.getBoundingClientRect().height);\n"
                + "            if (height % 2 == 0) {\n"
                + "                result = height + " + ToolSetting.getInstance().font_offset.toArray()[0] + ";\n"
                + "            }\n"
                + "            if (height % 3 == 0) {\n"
                + "                result = height + " + ToolSetting.getInstance().font_offset.toArray()[1] + ";\n"
                + "            }\n"
                + "            if (height % 4 == 0) {\n"
                + "                result = height + " + ToolSetting.getInstance().font_offset.toArray()[2] + ";\n"
                + "            }\n"
                + "            if (height % 5 == 0) {\n"
                + "                result = height + " + ToolSetting.getInstance().font_offset.toArray()[3] + ";\n"
                + "            }\n"
                + "            return result;\n"
                + "        }\n"
                + "    });"
                + "       Object.defineProperty(HTMLElement.prototype, \"offsetWidth\", {\n"
                + "        get() {\n"
                + "            const width = Math.floor(this.getBoundingClientRect().width);\n"
                + "            //const valid = width && rand.sign() === 1;\n"
                + "            //const result = valid ? width + rand.noise() : width;\n"
                + "            if (width % 2 == 0) {\n"
                + "                result = width + " + ToolSetting.getInstance().font_offset.toArray()[4] + ";\n"
                + "            }\n"
                + "            if (width % 3 == 0) {\n"
                + "                result = width + " + ToolSetting.getInstance().font_offset.toArray()[5] + ";\n"
                + "            }\n"
                + "            if (width % 4 == 0) {\n"
                + "                result = width + " + ToolSetting.getInstance().font_offset.toArray()[6] + ";\n"
                + "            }\n"
                + "            if (width % 5 == 0) {\n"
                + "                result = width + " + ToolSetting.getInstance().font_offset.toArray()[7] + ";\n"
                + "            }\n"
                + "            return result;\n"
                + "        }\n"
                + "    }); "
                + "}\n"
                + "       injectFont();";
        browser.executeJavaScript(font, browser.getURL(), 0);
        frame.executeJavaScript(font, frame.getURL(), 0);

    }

    public static void fakeAudioContext(CefBrowser browser, CefFrame frame) {
        String audioContext = "  var injectAudio = function () {\n"
                + "       const context = {\n"
                + "    \"BUFFER\": null,\n"
                + "    \"getChannelData\": function (e) {\n"
                + "      const getChannelData = e.prototype.getChannelData;\n"
                + "      Object.defineProperty(e.prototype, \"getChannelData\", {\n"
                + "        \"value\": function () {\n"
                + "          const results_1 = getChannelData.apply(this, arguments);\n"
                + "          if (context.BUFFER !== results_1) {\n"
                + "            context.BUFFER = results_1;\n"
                + "            for (var i = 0; i < results_1.length; i += 100) {\n"
                + "              let index = Math.floor(" + ToolSetting.getInstance().audioContext_1 + " * i);\n"
                + "              results_1[index] = results_1[index] + " + ToolSetting.getInstance().audioContext_2 + " * 0.0000001;\n"
                + "            }\n"
                + "          }\n"
                + "          return results_1;\n"
                + "        }\n"
                + "      });\n"
                + "    },\n"
                + "    \"createAnalyser\": function (e) {\n"
                + "      const createAnalyser = e.prototype.__proto__.createAnalyser;\n"
                + "      Object.defineProperty(e.prototype.__proto__, \"createAnalyser\", {\n"
                + "        \"value\": function () {\n"
                + "          const results_2 = createAnalyser.apply(this, arguments);\n"
                + "          const getFloatFrequencyData = results_2.__proto__.getFloatFrequencyData;\n"
                + "          Object.defineProperty(results_2.__proto__, \"getFloatFrequencyData\", {\n"
                + "            \"value\": function () {\n"
                + "              const results_3 = getFloatFrequencyData.apply(this, arguments);\n"
                + "              for (var i = 0; i < arguments[0].length; i += 100) {\n"
                + "                let index = Math.floor(" + ToolSetting.getInstance().audioContext_3 + " * i);\n"
                + "                arguments[0][index] = arguments[0][index] + " + ToolSetting.getInstance().audioContext_4 + " * 0.1;\n"
                + "              }\n"
                + "              return results_3;\n"
                + "            }\n"
                + "          });\n"
                + "          //\n"
                + "          return results_2;\n"
                + "        }\n"
                + "      });\n"
                + "    },\n"
                + "  };"
                + "       context.getChannelData(AudioBuffer);\n"
                + "       context.createAnalyser(AudioContext);\n"
                + "       context.getChannelData(OfflineAudioContext);\n"
                + "       context.createAnalyser(OfflineAudioContext);\n"
                + " };\n";
        String injectFrame = "  var script_audio = document.createElement('script');\n"
                + "       script_audio.id = \"script_audio\";\n"
                + "       script_audio.textContent = \"(\" + injectAudio + \")()\";\n"
                + "       document.documentElement.appendChild(script_audio); ";
        String runJs = "injectAudio();\n";

        String inject = "";
        //signin/
        if (browser.getURL().startsWith("https://accounts.google.com/") || frame.getURL().startsWith("https://accounts.google.com/")) {
            inject = runJs;
        } else {
            inject = injectFrame;
        }

        String audioAdv
                = "      var script_frameAudio = document.createElement('script'); "
                + "      script_frameAudio.textContent =`"
                + "	    for (var i = 0; i < [...window.top.document.querySelectorAll(\"iframe[sandbox]\")].length; i++) {\n"
                + "	       if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow) {\n"
                + " if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioBuffer) {\n"
                + "          if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioBuffer.prototype) {\n"
                + "            if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioBuffer.prototype.getChannelData) {\n"
                + "              [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioBuffer.prototype.getChannelData = AudioBuffer.prototype.getChannelData;\n"
                + "            }\n"
                + "          }\n"
                + "        }"
                + "if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioContext) {\n"
                + "          if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioContext.prototype) {\n"
                + "            if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioContext.prototype.__proto__) {\n"
                + "              if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioContext.prototype.__proto__.createAnalyser) {\n"
                + "                [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.AudioContext.prototype.__proto__.createAnalyser = AudioContext.prototype.__proto__.createAnalyser;\n"
                + "              }\n"
                + "            }\n"
                + "          }\n"
                + "        }"
                + "if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext) {\n"
                + "          if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext.prototype) {\n"
                + "            if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext.prototype.__proto__) {\n"
                + "              if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext.prototype.__proto__.createAnalyser) {\n"
                + "                [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext.prototype.__proto__.createAnalyser = OfflineAudioContext.prototype.__proto__.createAnalyser;\n"
                + "              }\n"
                + "            }\n"
                + "          }\n"
                + "        }"
                + " if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext) {\n"
                + "          if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext.prototype) {\n"
                + "            if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext.prototype.__proto__) {\n"
                + "              if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext.prototype.__proto__.getChannelData) {\n"
                + "                [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.OfflineAudioContext.prototype.__proto__.getChannelData = OfflineAudioContext.prototype.__proto__.getChannelData;\n"
                + "              }\n"
                + "            }\n"
                + "          }\n"
                + "   }"
                + "	      }\n"
                + "	    }`\n"
                + "   window.top.document.documentElement.appendChild(script_frameAudio);";
        browser.executeJavaScript(audioContext + inject + audioAdv, browser.getURL(), 0);
        frame.executeJavaScript(audioContext + inject + audioAdv, frame.getURL(), 0);
    }

    public static void fakeOpenGL(CefBrowser browser, CefFrame frame) {
        String font = "  var injectOpenGL = function () {\n"
                + "var config = {\n"
                + "        \"random\": {\n"
                + "            \"value\": function () {\n"
                + "                return Math.random()\n"
                + "            },\n"
                + "            \"item\": function (e) {\n"
                + "                var rand = e.length * config.random.value();\n"
                + "                return e[Math.floor(rand)];\n"
                + "            },\n"
                + "            \"array\": function (e) {\n"
                + "                var rand = config.random.item(e);\n"
                + "                return new Int32Array([rand, rand]);\n"
                + "            },\n"
                + "            \"items\": function (e, n) {\n"
                + "                var length = e.length;\n"
                + "                var result = new Array(n);\n"
                + "                var taken = new Array(length);\n"
                + "                if (n > length) n = length;\n"
                + "                //\n"
                + "                while (n--) {\n"
                + "                    var i = Math.floor(config.random.value() * length);\n"
                + "                    result[n] = e[i in taken ? taken[i] : i];\n"
                + "                    taken[i] = --length in taken ? taken[length] : length;\n"
                + "                }\n"
                + "                //\n"
                + "                return result;\n"
                + "            }\n"
                + "        },\n"
                + "        \"spoof\": {\n"
                + "            \"webgl\": {\n"
                + "                \"buffer\": function (target) {\n"
                + "                    const bufferData = target.prototype.bufferData;\n"
                + "                    Object.defineProperty(target.prototype, \"bufferData\", {\n"
                + "                        \"value\": function () {\n"
                + "                            var index = Math.floor(" + ToolSetting.getInstance().webglNoise + " * 10);\n"
                + "                            var noise = 0.1 * " + ToolSetting.getInstance().webglNoise + " * arguments[1][index];\n"
                + "                            arguments[1][index] = arguments[1][index] + noise;\n"
                + "                            //\n"
                + "                            return bufferData.apply(this, arguments);\n"
                + "                        }\n"
                + "                    });\n"
                + "                },\n"
                + "                \"parameter\": function (target) {\n"
                + "                    const getParameter = target.prototype.getParameter;\n"
                + "                    Object.defineProperty(target.prototype, \"getParameter\", {\n"
                + "                        \"value\": function () {\n"
                + "                            var float32array = new Float32Array([1, 8192]);\n"
                + "                            if (arguments[0] === 3415) return " + ToolSetting.getInstance().openGL_stencilBits + " ;\n"
                + "                            else if (arguments[0] === 3414) return " + ToolSetting.getInstance().openGL_depthBits + " ;\n"
                + "                            else if (arguments[0] === 7938) return \"" + ToolSetting.getInstance().openGL_version + "\";\n"
                + "                            else if (arguments[0] === 35661) return " + ToolSetting.getInstance().openGL_maxCombinedTextureImageUnits + " ;\n"
                + "                            else if (arguments[0] === 37446) return '" + ToolSetting.getInstance().openGL_unmaskedRenderer + "' ;\n"
                + "                            else if (arguments[0] === 3386) return new Int32Array([" + ToolSetting.getInstance().webgl_3386 + "," + ToolSetting.getInstance().webgl_3386 + "]);\n"
                + "                            else if (arguments[0] === 36347) return " + ToolSetting.getInstance().openGL_maxVertexUniformVectors + " ;\n"
                + "                            else if (arguments[0] === 36349) return " + ToolSetting.getInstance().openGL_maxFragmentUniformVectors + " ;\n"
                + "                            else if (arguments[0] === 34047) return " + ToolSetting.getInstance().openGL_maxAnisotropy + ";\n"
                + "                            else if (arguments[0] === 34921) return " + ToolSetting.getInstance().openGL_maxVertexAttribs + ";\n"
                + "                            else if (arguments[0] === 7937) return '" + ToolSetting.getInstance().openGL_renderer + "';\n"
                + "                            else if (arguments[0] === 35724) return '" + ToolSetting.getInstance().openGL_shadingLanguage + "';\n"
                + "                            else if (arguments[0] === 7936) return '" + ToolSetting.getInstance().openGL_vendor + "';\n"
                //+ "                            else if (arguments[0] === 33901) return " + ToolSetting.getInstance().openGL_renderer + ";\n"
                //+ "                            else if (arguments[0] === 33902) return " + ToolSetting.getInstance().openGL_renderer + ";\n"
                + "                            else if (arguments[0] === 34930) return " + ToolSetting.getInstance().openGL_maxTextureImageUnits + ";\n"
                + "                            else if (arguments[0] === 36348) return " + ToolSetting.getInstance().openGL_maxVaryingVectors + ";\n"
                + "                            else if (arguments[0] === 35660) return " + ToolSetting.getInstance().openGL_maxVertexTextureImageUnits + ";\n"
                + "                            else if (arguments[0] === 34076) return " + ToolSetting.getInstance().openGL_maxCubeMapTextureSize + ";\n"
                + "                            else if (arguments[0] === 34024) return " + ToolSetting.getInstance().openGL_maxRenderBufferSize + ";\n"
                + "                            else if (arguments[0] === 3379) return " + ToolSetting.getInstance().openGL_maxTextureSize + ";\n"
                + "                            else if (arguments[0] === 3413) return " + ToolSetting.getInstance().openGL_alphaBits + ";\n"
                + "                            else if (arguments[0] === 3412) return " + ToolSetting.getInstance().openGL_blueBits + ";\n"
                + "                            else if (arguments[0] === 3411) return " + ToolSetting.getInstance().openGL_greenBits + ";\n"
                + "                            else if (arguments[0] === 3410) return " + ToolSetting.getInstance().openGL_redBits + ";\n"
                + "                            //\n"
                + "                            return getParameter.apply(this, arguments);\n"
                + "                        }\n"
                + "                    });\n"
                + "                },\n"
                + "\"supportedExtensions\": function (target) {\n"
                + "                    const getSupportedExtensions = target.prototype.getSupportedExtensions;\n"
                + "                    Object.defineProperty(target.prototype, \"getSupportedExtensions\", {\n"
                + "                        \"value\": function () {\n"
                + "                            return " + ToolSetting.getInstance().openGL_exts + " ;\n"
                + "                        }\n"
                + "                    });\n"
                + "                },\n"
                + "            }\n"
                + "        }\n"
                + "    };\n"
                + "    //\n"
                + "    config.spoof.webgl.buffer(WebGLRenderingContext);\n"
                + "    config.spoof.webgl.buffer(WebGL2RenderingContext);\n"
                + "    config.spoof.webgl.parameter(WebGLRenderingContext);\n"
                + "    config.spoof.webgl.parameter(WebGL2RenderingContext);\n"
                + "    config.spoof.webgl.supportedExtensions(WebGLRenderingContext);\n"
                + "    config.spoof.webgl.supportedExtensions(WebGL2RenderingContext);\n"
                + "}\n";
        String injectFrame = "      var script_openGl = document.createElement('script');\n"
                + "       script_openGl.id = \"script_openGl\";\n"
                + "       script_openGl.textContent = \"(\" + injectOpenGL + \")()\";\n"
                + "       document.documentElement.appendChild(script_openGl); ";
        String runJs = "injectOpenGL();";
        String inject = "";
        //signin/
        if (browser.getURL().startsWith("https://accounts.google.com/") || frame.getURL().startsWith("https://accounts.google.com/")) {
            inject = runJs;
        } else {
            inject = injectFrame;
        }
        String openGLAdv
                = "      var script_frameOpenGl = document.createElement('script'); "
                + "      script_frameOpenGl.textContent =`"
                + "      for (var i = 0; i < [...window.top.document.querySelectorAll(\"iframe[sandbox]\")].length; i++) {\n"
                + "	      if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow) {\n"
                + "	        if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.WebGLRenderingContext) {\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.WebGLRenderingContext.prototype.bufferData = WebGLRenderingContext.prototype.bufferData;\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.WebGLRenderingContext.prototype.getParameter = WebGLRenderingContext.prototype.getParameter;\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.WebGLRenderingContext.prototype.getSupportedExtensions = WebGLRenderingContext.prototype.getSupportedExtensions;\n"
                + "	        }\n"
                + "	        if ([...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.WebGL2RenderingContext) {\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.WebGL2RenderingContext.prototype.bufferData = WebGL2RenderingContext.prototype.bufferData;\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.WebGL2RenderingContext.prototype.getParameter = WebGL2RenderingContext.prototype.getParameter;\n"
                + "	          [...window.top.document.querySelectorAll(\"iframe[sandbox]\")][i].contentWindow.WebGL2RenderingContext.prototype.getSupportedExtensions = WebGL2RenderingContext.prototype.getSupportedExtensions;\n"
                + "	        }\n"
                + "	      }\n"
                + "	}`\n"
                + "     window.top.document.documentElement.appendChild(script_frameOpenGl); "
                + "    ";
        browser.executeJavaScript(font + inject + openGLAdv, browser.getURL(), 0);
        frame.executeJavaScript(font + inject + openGLAdv, frame.getURL(), 0);

    }

    public static void fakeArgument(CefCommandLine ccl) {
        ccl.appendSwitchWithValue("--cache-path", ToolSetting.getInstance().profile);
        ccl.appendSwitch("start-maximized");
        ccl.appendSwitch("--cache-path");
        ccl.appendArgument("--cache-path");
        ccl.appendArgument("--no-sandbox");
        ccl.appendArgument("--disable-dev-shm-usage");
        ccl.appendArgument("--silent");
        ccl.appendArgument("--disable-certificate-errors");
        ccl.appendArgument("--test-type");
        ccl.appendArgument("--incognito");
        ccl.appendSwitchWithValue("test-type", "browser");
        ccl.appendArgument("-–disable-web-security");
        ccl.appendArgument("--allow-running-insecure-content");
        ccl.appendSwitchWithValue("--useAutomationExtension", "false");
        ccl.appendSwitchWithValue("--excludeSwitches", "enable-automation");
        ccl.appendSwitchWithValue("--autoplay-policy", "no-user-gesture-required");
        ccl.appendSwitchWithValue("disable-web-security", "1");
        ccl.appendSwitchWithValue("--log-severity=", "disable");
        ccl.appendSwitch("-–disable-web-security");
        ccl.appendSwitch("--allow-running-insecure-content");
        ccl.appendSwitchWithValue("--enable-media-stream", "1");
        ccl.appendSwitch("--enable-npapi");
        ccl.appendArgument("--enable-npapi");
        ccl.appendSwitchWithValue("--disable-features", "PreloadMediaEngagementData, MediaEngagementBypassAutoplayPolicies,MimeHandlerViewInCrossProcessFrame");
        ccl.appendSwitchWithValue("--enable-blink-features", "WebBluetooth,Badging,InstalledApp,WakeLock,Notifications,WebAnimationsAPI,AOMPhase1 en 0 1 1 prof/S6PzeRAd");
        ccl.appendSwitchWithValue("--unique-process-id", "1amfxmkz");
        ccl.appendSwitchWithValue("--log-severity", "disable"); 
    }

    public static void fakeHeader(CefBrowser browser, CefFrame frame, CefRequest request) {
        //request.setHeaderByName("content-security-policy", "content-unsecure-policy", true);
        
        
        request.setHeaderByName("X-Frame-Options","deny", true);
        //request.setReferrer(request.getReferrerURL(), CefRequest.ReferrerPolicy.REFERRER_POLICY_ORIGIN);
        
        if (request.getReferrerURL().startsWith("https://accounts.google.com/signin/v2/identifier")) {
            //System.out.println("request "+request.getReferrerURL());
            //https://accounts.google.com/signin/v2/identifier
             request.setHeaderByName("User-Agent", ToolSetting.firefoxUA, true);
            //https://myaccount.google.com/device-activity
            
            

            /*
            HashMap<String, String> headerMap = new HashMap<>();
            request.getHeaderMap(headerMap);
            headerMap.put("User-Agent", ToolSetting.firefoxUA);
            headerMap.forEach((k, v) -> {
                //if(k.contains("security")){
                //System.out.println(k + "  " + v);
                //}
            });
            headerMap.remove("Content-Type");
            //headerMap.remove("x-frame-options");
            //headerMap.remove("Origin");
            request.setHeaderMap(headerMap);
             */
        } else {
            //request.setHeaderByName("User-Agent", ToolSetting.getInstance().userAgent, true);
        }
    }
}
