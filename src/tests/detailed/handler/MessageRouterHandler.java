// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.
package tests.detailed.handler;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;
import util.TaskReadThread;

public class MessageRouterHandler extends CefMessageRouterHandlerAdapter {
    private TaskReadThread task;
    public MessageRouterHandler(TaskReadThread task) {
        System.out.println("Đã tạo MessageRouterHandler");
        this.task = task;
    }
    @Override
    public boolean onQuery(CefBrowser browser, CefFrame frame, long query_id, String request,
            boolean persistent, CefQueryCallback callback) {
        System.out.println("message JS " + request);
        String msg = request;
        task.sendMessageToServer(msg);
        callback.success(new StringBuilder(msg).reverse().toString());
        return true;
    }
}
