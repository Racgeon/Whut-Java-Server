package com.rkgn.utils;

import com.rkgn.entity.Doc;
import com.rkgn.exception.DataException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DocDAO {
    private static final ConcurrentHashMap<String, Doc> docs = new ConcurrentHashMap<>();
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

    public static void docInit() throws DataException, SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tb_doc");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String creator = resultSet.getString(2);
            Timestamp timestamp = resultSet.getTimestamp(3);
            String description = resultSet.getString(4);
            String filename = resultSet.getString(5);
            docs.put(filename, new Doc(id, creator, timestamp, description, filename));
        }
    }


    public static boolean delDoc(String filename) throws SQLException {
        if (!docs.containsKey(filename)) {
            return false;
        }
        docs.remove(filename);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tb_doc WHERE filename = ?");
        preparedStatement.setString(1, filename);
        preparedStatement.execute();
        return preparedStatement.getUpdateCount() != 0;
    }

    public static boolean addDoc(Doc doc) throws SQLException {
        if (docs.containsKey(doc.getFilename())) {
            return false;
        }
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO tb_doc (creator, description, filename) VALUES (?, ?, ?)");
        preparedStatement.setString(1, doc.getCreator());
        preparedStatement.setString(2, doc.getDescription());
        preparedStatement.setString(3, doc.getFilename());
        preparedStatement.execute();
        PreparedStatement queryPreparedStatement =
                connection.prepareStatement("SELECT * FROM tb_doc where filename= ?");
        queryPreparedStatement.setString(1, doc.getFilename());
        ResultSet resultSet = queryPreparedStatement.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String creator = resultSet.getString(2);
            Timestamp timestamp = resultSet.getTimestamp(3);
            String description = resultSet.getString(4);
            String filename = resultSet.getString(5);
            Doc newDoc = new Doc(id, creator, timestamp, description, filename);
            docs.put(doc.getFilename(), newDoc);
        }
        return preparedStatement.getUpdateCount() != 0;
    }

    public static List<Doc> getAllDocs() {
        return docs.values().stream().toList();
    }

    public static Doc searchDoc(String name) {
        if (!docs.containsKey(name)) {
            return null;
        }
        return docs.get(name);
    }
}
