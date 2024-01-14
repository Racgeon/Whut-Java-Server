package com.rkgn.utils;

import javax.swing.*;
import java.awt.*;

public class Dialog {
    public static void error(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "错误", JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "成功", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int confirm(Component parent, String msg) {
        return JOptionPane.showConfirmDialog(parent, msg, "确认", JOptionPane.YES_NO_OPTION);
    }
}
