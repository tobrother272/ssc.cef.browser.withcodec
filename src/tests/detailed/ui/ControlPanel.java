// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package tests.detailed.ui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.cef.OS;
import org.cef.browser.CefBrowser;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
    private final JButton backButton_;
    private final JButton playButton_;
    private final JButton forwardButton_;
    private final JButton reloadButton_;
    private final JTextField address_field_;
    private final JLabel zoom_label_;
    private double zoomLevel_ = 0;
    private final CefBrowser browser_;
    class SchedulingEventQueue extends EventQueue
    {
      // Use Map< AWTEvent, List< AWTEvent > > to support multiple events
      private final Map< AWTEvent, AWTEvent > eventSchedule = new HashMap< AWTEvent, AWTEvent >();

      public void scheduleEvent( final AWTEvent event, final AWTEvent dependentEvent )
      {
        eventSchedule.put( dependentEvent, event );
      }

      @Override
      protected void dispatchEvent( final AWTEvent event )
      {
        try
        {
          super.dispatchEvent( event );
        }
        finally
        {
          // Dispatch any dependent event
          AWTEvent scheduledEvent = eventSchedule.remove( event );
          if( scheduledEvent != null )
          {
            postEvent( scheduledEvent );
          }
        }
      }
    }
    public ControlPanel(CefBrowser browser) {
        assert browser != null;
        browser_ = browser;
        //browser_.stopLoad();
       
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(Box.createHorizontalStrut(5));
        add(Box.createHorizontalStrut(5));

        backButton_ = new JButton("Back");
        backButton_.setFocusable(false);
        backButton_.setAlignmentX(LEFT_ALIGNMENT);
        backButton_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.goBack();
            }
        });
        add(backButton_);
        
        
        
 
       
        
        
        
        
        
        add(Box.createHorizontalStrut(5));

        forwardButton_ = new JButton("Forward");
        forwardButton_.setFocusable(false);
        forwardButton_.setAlignmentX(LEFT_ALIGNMENT);
        forwardButton_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.goForward();
            }
        });
        add(forwardButton_);
        add(Box.createHorizontalStrut(5));

        reloadButton_ = new JButton("Reload");
        reloadButton_.setFocusable(false);
        reloadButton_.setAlignmentX(LEFT_ALIGNMENT);
        reloadButton_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reloadButton_.getText().equalsIgnoreCase("reload")) {
                    int mask = OS.isMacintosh() ? ActionEvent.META_MASK : ActionEvent.CTRL_MASK;
                    if ((e.getModifiers() & mask) != 0) {
                        System.out.println("Reloading - ignoring cached values");
                        browser_.reloadIgnoreCache();
                    } else {
                        System.out.println("Reloading - using cached values");
                        browser_.reload();
                    }
                } else {
                    browser_.stopLoad();
                }
            }
        });
        add(reloadButton_);
        add(Box.createHorizontalStrut(5));

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(addressLabel);
        add(Box.createHorizontalStrut(5));

        address_field_ = new JTextField(100);
        address_field_.setAlignmentX(LEFT_ALIGNMENT);
        address_field_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.loadURL(getAddress());
            }
        });
        add(address_field_);
        
        
        
        playButton_ = new JButton("PLAY");
        playButton_.setFocusable(false);
        playButton_.setAlignmentX(LEFT_ALIGNMENT);
        playButton_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String frame="";
                for (String frameIdentifier : browser_.getFrameNames()) {
                    System.out.println(""+frameIdentifier);
                    frame=frameIdentifier;
                }
                System.out.println("");
                browser_.executeJavaScript("window.cefQuery({request:'#1614686286284#jsvalue='+console.log(document.evaluate(\"//iframe[contains(@src,'/includes/commerce/authenticate')]\", document, null, 7, null).snapshotItem(0).getAttribute(\"src\"))})", browser.getURL(), 0);
            }
        });
        add(playButton_);
        
        add(Box.createHorizontalStrut(5));

        JButton goButton = new JButton("Go");
        goButton.setFocusable(false);
        goButton.setAlignmentX(LEFT_ALIGNMENT);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.loadURL(getAddress());
            }
        });
        add(goButton);
        add(Box.createHorizontalStrut(5));

        JButton minusButton = new JButton("-");
        minusButton.setFocusable(false);
        minusButton.setAlignmentX(CENTER_ALIGNMENT);
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.setZoomLevel(--zoomLevel_);
                zoom_label_.setText(new Double(zoomLevel_).toString());
            }
        });
        add(minusButton);

        zoom_label_ = new JLabel("0.0");
        add(zoom_label_);

        JButton plusButton = new JButton("+");
        plusButton.setFocusable(false);
        plusButton.setAlignmentX(CENTER_ALIGNMENT);
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.setZoomLevel(++zoomLevel_);
                zoom_label_.setText(new Double(zoomLevel_).toString());
            }
        });
        add(plusButton);
    }

    public void update(
            CefBrowser browser, boolean isLoading, boolean canGoBack, boolean canGoForward) {
        if (browser == browser_) {
            backButton_.setEnabled(canGoBack);
            forwardButton_.setEnabled(canGoForward);
            reloadButton_.setText(isLoading ? "Abort" : "Reload");
        }
    }

    public String getAddress() {
        String address = address_field_.getText();
        // If the URI format is unknown "new URI" will throw an
        // exception. In this case we interpret the value of the
        // address field as search request. Therefore we simply add
        // the "search" scheme.
        try {
            address = address.replaceAll(" ", "%20");
            URI test = new URI(address);
            if (test.getScheme() != null) return address;
            if (test.getHost() != null && test.getPath() != null) return address;
            String specific = test.getSchemeSpecificPart();
            if (specific.indexOf('.') == -1)
                throw new URISyntaxException(specific, "No dot inside domain");
        } catch (URISyntaxException e1) {
            address = "search://" + address;
        }
        return address;
    }

    public void setAddress(CefBrowser browser, String address) {
        if (browser == browser_) address_field_.setText(address);
    }

    public JTextField getAddressField() {
        return address_field_;
    }
}
