package com.rkgn.utils;

import com.rkgn.entity.Administrator;
import com.rkgn.entity.Browser;
import com.rkgn.entity.Operator;
import com.rkgn.entity.User;
import com.rkgn.exception.DataException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class UserDAO {
    private static final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private static Connection connection;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/jdbc.properties"));
            String url = (String) properties.get("jdbc.url");
            String username = (String) properties.get("jdbc.username");
            String password = (String) properties.get("jdbc.password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | IOException e) {
            Dialog.error(null, "数据库连接失败：" + e.getMessage());
            System.exit(1);
        }
    }

    public static void init() throws DataException, SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tb_user");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            String role = resultSet.getString(4);
            switch (role.toLowerCase()) {
                case Role.ADMINISTRATOR:
                    users.put(name, new Administrator(name, password));
                    break;
                case Role.OPERATOR:
                    users.put(name, new Operator(name, password));
                    break;
                case Role.BROWSER:
                    users.put(name, new Browser(name, password));
                    break;
                default:
                    throw new DataException("数据库用户权限读取错误");
            }
        }
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static boolean delUser(String name) throws SQLException {
        if (!users.containsKey(name)) {
            return false;
        }
        users.remove(name);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tb_user WHERE name = ?");
        preparedStatement.setString(1, name);
        preparedStatement.execute();
        return preparedStatement.getUpdateCount() != 0;
    }

    public static boolean addUser(User user) throws SQLException {
        if (users.containsKey(user.getName())) {
            return false;
        }
        users.put(user.getName(), user);
        long id = IdUtils.nextId();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tb_user VALUES (? , ? , ? , ?)");
        preparedStatement.setString(1, String.valueOf(id));
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getRole());
        preparedStatement.execute();
        return preparedStatement.getUpdateCount() != 0;
    }

    public static List<User> getAllUsers() {
        return users.values().stream().toList();
    }

    public static User searchUser(String name, String password) {
        if (!users.containsKey(name) || !users.get(name).getPassword().equals(password)) {
            return null;
        }
        return users.get(name);
    }

    public static boolean updatePassword(String name, String password) throws SQLException {
        if (!users.containsKey(name)) {
            return false;
        }
        User user = users.get(name);
        user.setPassword(password);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tb_user SET password = ? WHERE name = ?");
        preparedStatement.setString(1, password);
        preparedStatement.setString(2, name);
        preparedStatement.execute();
        return preparedStatement.getUpdateCount() != 0;
    }

    public static boolean updateRole(String name, String role) throws SQLException {
        if (!users.containsKey(name)) {
            return false;
        }
        User user = users.get(name);
        user.setRole(role);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tb_user SET role = ? WHERE name = ?");
        preparedStatement.setString(1, role);
        preparedStatement.setString(2, name);
        preparedStatement.execute();
        return preparedStatement.getUpdateCount() != 0;
    }

    public static boolean updateUser(User user) throws SQLException {
        if (!users.containsKey(user.getName())) {
            return false;
        }
        users.put(user.getName(), user);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tb_user SET password = ? , role = ? WHERE name = ?");
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(2, user.getRole());
        preparedStatement.setString(3, user.getName());
        preparedStatement.execute();
        return preparedStatement.getUpdateCount() != 0;
    }
}
