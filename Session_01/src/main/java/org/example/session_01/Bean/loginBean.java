package org.example.session_01.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginBean {
    private Connection connection;

    public loginBean(Connection connection) {
        this.connection = connection;
    }

    /**
     * Check login
     *
     * @param us   username
     * @param pass password
     * @return true nêếu đăng nhập thành công, false nếu đăng nhập thất bại
     */
    public boolean login(String us, String pass) {
        boolean isValid = false;
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, us);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
}