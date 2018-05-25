package com.codecool.DAO;
import com.codecool.Connection.ConnectionBuilder;
import com.codecool.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface DAO {

    User extractUserFromRow(ResultSet resultSet) throws SQLException;

}
