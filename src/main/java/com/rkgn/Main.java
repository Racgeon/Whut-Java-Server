package com.rkgn;

import com.rkgn.exception.DataException;
import com.rkgn.gui.ServerFrame;
import com.rkgn.utils.Dialog;
import com.rkgn.utils.DocDAO;
import com.rkgn.utils.UserDAO;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            UserDAO.init();
            DocDAO.docInit();
        } catch (DataException | SQLException e) {
            Dialog.error(null, e.getMessage());
            System.exit(0);
        }

        ServerFrame frame = new ServerFrame();
        frame.setVisible(true);
        ServerFrame.console = frame.textArea;
    }
}