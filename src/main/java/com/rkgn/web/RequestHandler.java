package com.rkgn.web;

import cn.hutool.json.JSONUtil;
import com.rkgn.dto.*;
import com.rkgn.entity.*;
import com.rkgn.gui.ServerFrame;
import com.rkgn.utils.Constants;
import com.rkgn.utils.DocDAO;
import com.rkgn.utils.UserDAO;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class RequestHandler {
    private final Socket clientSocket;
    private final BufferedReader br;
    private String data;
    private final Response response;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        response = new Response(clientSocket);
        try {
            InputStream in = clientSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute() {
        try {
            if ((data = br.readLine()) != null) {
                switch (data) {
                    case "login" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " login");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " login\n");
                        handleLogin();
                    }
                    case "add" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " add");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " add\n");
                        handleAdd();
                    }
                    case "update" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " update");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " update\n");
                        handleUpdate();
                    }
                    case "upload" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " upload");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " upload\n");
                        handleUpload();
                    }
                    case "download" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " download");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " download\n");
                        handleDownload();
                    }
                    case "get all users" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " get all users");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " get all users\n");
                        handleGetAllUsers();
                    }
                    case "get all docs" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " get all docs");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " get all docs\n");
                        handleGetAllDocs();
                    }
                    case "delete" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " delete");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " delete\n");
                        handleDelete();
                    }
                    case "get user" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " get user");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " get user\n");
                        handleGetUser();
                    }
                    case "change password" -> {
                        log.info(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " change password");
                        ServerFrame.console.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " change password\n");
                        handleChangePassword();
                    }
                }
            }
        } catch (IOException e) {
            try {
                response.fail("执行" + data + "时服务器异常！" + e.getMessage());
                ServerFrame.console.append("执行" + data + "时服务器异常！" + e.getMessage() + "\n");
                clientSocket.close();
            } catch (IOException ex) {
                log.error(ex.getMessage());
                ServerFrame.console.append(ex.getMessage() + "\n");
            }
            log.error(e.getMessage());
        }
    }

    private void handleChangePassword() throws IOException {
        String json = br.readLine();
        ChangePasswordForm changePasswordForm = JSONUtil.toBean(json, ChangePasswordForm.class);
        String username = changePasswordForm.getUsername();
        String password = changePasswordForm.getPassword();
        try {
            if (password != null) {
                UserDAO.updatePassword(username, password);
                response.ok();
            }
        } catch (SQLException e) {
            response.fail(e.getMessage());
        }
    }

    private void handleGetUser() throws IOException {
        String json = br.readLine();
        GetUserForm getUserForm = JSONUtil.toBean(json, GetUserForm.class);
        String username = getUserForm.getUsername();
        User user = UserDAO.getUser(username);
        response.ok(user);
    }

    private void handleDelete() throws IOException {
        String json = br.readLine();
        DeleteForm deleteForm = JSONUtil.toBean(json, DeleteForm.class);
        String username = deleteForm.getUsername();
        try {
            UserDAO.delUser(username);
            response.ok();
        } catch (SQLException e) {
            response.fail(e.getMessage());
        }
    }

    private void handleUpdate() throws IOException {
        String json = br.readLine();
        UpdateForm updateForm = JSONUtil.toBean(json, UpdateForm.class);
        String username = updateForm.getUsername();
        String password = updateForm.getPassword();
        String role = updateForm.getRole();
        try {
            if (password != null) {
                UserDAO.updatePassword(username, password);
                response.ok();
            }
            if (role != null) {
                if (!role.equals("browser") && !role.equals("operator") && !role.equals("administrator")) {
                    response.fail("角色信息出现错误！");
                } else {
                    UserDAO.updateRole(username, role);
                    response.ok();
                }
            }
        } catch (SQLException e) {
            response.fail(e.getMessage());
        }
    }

    private void handleAdd() throws IOException {
        String json = br.readLine();
        AddForm addForm = JSONUtil.toBean(json, AddForm.class);
        String username = addForm.getUsername();
        String password = addForm.getPassword();
        String role = addForm.getRole();
        User user = switch (role) {
            case "browser" -> new Browser(username, password);
            case "operator" -> new Operator(username, password);
            case "administrator" -> new Administrator(username, password);
            default -> null;
        };
        try {
            if (user != null) {
                UserDAO.addUser(user);
                response.ok();
            } else {
                response.fail("角色信息出现错误！");
            }
        } catch (SQLException e) {
            response.fail(e.getMessage());
        }
    }

    private void handleGetAllDocs() {
        List<Doc> allDocs = DocDAO.getAllDocs();
        response.ok(allDocs);
    }

    private void handleGetAllUsers() {
        List<User> allUsers = UserDAO.getAllUsers();
        response.ok(allUsers);
    }

    private void handleDownload() throws IOException {
        String json = br.readLine();
        DownloadForm downloadForm = JSONUtil.toBean(json, DownloadForm.class);
        File file = new File(Constants.DOCUMENT_PATH_PREFIX + downloadForm.getFilename());
        OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
        osw.write(file.length() + "\n");
        osw.flush();
        BufferedInputStream lbr = new BufferedInputStream(new FileInputStream(Constants.DOCUMENT_PATH_PREFIX + downloadForm.getFilename()));
        byte[] read = lbr.readAllBytes();
        OutputStream out = clientSocket.getOutputStream();
        out.write(read);
        out.flush();
    }

    private void handleUpload() throws IOException {
        String json = br.readLine();
        UploadForm uploadForm = JSONUtil.toBean(json, UploadForm.class);
        String creator = uploadForm.getCreator();
        String description = uploadForm.getDescription();
        String filename = uploadForm.getFilename();
        Long bytes = uploadForm.getBytes();
        OutputStream fw = new BufferedOutputStream(new FileOutputStream(Constants.DOCUMENT_PATH_PREFIX + filename));
        InputStream is = clientSocket.getInputStream();
        byte[] read = is.readNBytes(bytes.intValue());
        fw.write(read);
        fw.flush();
        try {
            DocDAO.addDoc(new Doc(null, creator, null, description, filename));
        } catch (SQLException e) {
            response.fail(e.getMessage());
            return;
        }
        response.ok();
//        clientSocket.close();
    }

    private void handleLogin() throws IOException {
        String json;
        json = br.readLine();
        LoginForm form = JSONUtil.toBean(json, LoginForm.class);
        User user = UserDAO.searchUser(form.getUsername(), form.getPassword());
        if (user == null) {
            response.fail("您输入的账号或密码错误！");
        } else {
            response.ok(user);
        }
    }
}