package com.neotys.tcp.common;

import javax.swing.*;
import java.net.URL;

public class TcpUtils {
    private static final ImageIcon TCP_ICON;

    static {

        final URL iconURL = TcpUtils.class.getResource("tcp.png");
        if (iconURL != null) {
            TCP_ICON = new ImageIcon(iconURL);
        } else {
            TCP_ICON = null;
        }
    }

    public TcpUtils() {
    }

    public static ImageIcon getTcpIcon() {
        return TCP_ICON;
    }

}