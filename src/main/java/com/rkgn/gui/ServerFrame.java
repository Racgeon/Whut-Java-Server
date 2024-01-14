package com.rkgn.gui;

import com.rkgn.web.RequestHandler;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


@Slf4j
public class ServerFrame extends JFrame {
    public static JTextArea console;
    private final static ExecutorService es = new ThreadPoolExecutor(5, 10, 100L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    private final ConcurrentHashMap<Integer, ServerSocket> serverSocketMap = new ConcurrentHashMap<>();

    public ServerFrame() {
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = toolkit.getScreenSize().width / 5;
        int height = toolkit.getScreenSize().height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    private void startButtonMouseClicked(MouseEvent e) {
        String text = portField.getText();
        int port = Integer.parseInt(text);
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                serverSocketMap.put(port, serverSocket);
                log.info("端口号为：" + port + "的服务器启动...");
                console.append("端口号为：" + port + "的服务器启动...\n");
                while (true) {
                    Socket accept = serverSocket.accept();
                    es.submit(() -> new RequestHandler(accept).execute());
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
                console.append(ex.getMessage() + "\n");
            }
        }).start();
    }

    private void closeButtonMouseClicked(MouseEvent e) {
        String text = portField.getText();
        int port = Integer.parseInt(text);
        ServerSocket serverSocket = serverSocketMap.get(port);
        try {
            log.info("端口号为：" + serverSocket.getLocalPort() + "的服务器关闭...");
            console.append("端口号为：" + serverSocket.getLocalPort() + "的服务器关闭...\n");
            serverSocket.close();
        } catch (IOException ex) {
            if (!ex.getMessage().equals("Socket closed")) {
                log.error(ex.getMessage());
                console.append(ex.getMessage() + "\n");
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel = new JPanel();
        label = new JLabel();
        portField = new JTextField();
        startButton = new JButton();
        closeButton = new JButton();
        scrollPane = new JScrollPane();
        textArea = new JTextArea();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel ========
        {
            panel.setLayout(new FlowLayout());

            //---- label ----
            label.setText("\u7aef\u53e3\u53f7");
            panel.add(label);

            //---- portField ----
            portField.setPreferredSize(new Dimension(150, 30));
            portField.setText("8888");
            panel.add(portField);

            //---- startButton ----
            startButton.setText("\u542f\u52a8");
            startButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    startButtonMouseClicked(e);
                }
            });
            panel.add(startButton);

            //---- closeButton ----
            closeButton.setText("\u5173\u95ed");
            closeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    closeButtonMouseClicked(e);
                }
            });
            panel.add(closeButton);
        }
        contentPane.add(panel);

        //======== scrollPane ========
        {
            scrollPane.setPreferredSize(new Dimension(3, 400));

            //---- textArea ----
            textArea.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 16));
            textArea.setEditable(false);
            scrollPane.setViewportView(textArea);
        }
        contentPane.add(scrollPane);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel;
    private JLabel label;
    private JTextField portField;
    private JButton startButton;
    private JButton closeButton;
    private JScrollPane scrollPane;
    public JTextArea textArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
