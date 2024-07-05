package org.example;

import java.sql.DriverManager;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int [] ids = {1,2,3};
        String [] names = {"a", "b", "c" };

        Class.forName("org.sqlite.JDBC");
        String dburl = "JDBC:sqlite:real.db";
        var conn = DriverManager.getConnection(dburl);
        var stmt = conn.createStatement();
        var sql = "create table if not exists user (id integer primary key, name text not null)";
        stmt.execute(sql);

        sql = " insert into user (id,name) values (?, ?)";
        var insertst = conn.prepareStatement(sql);

        for (int i = 0; i < ids.length; i++){
            insertst.setInt(1, ids[i]);
           insertst.setString(2,names[i]);

           insertst.executeUpdate();
        }


        insertst.close();

        sql = " select id, name from user";
        var rs = stmt.executeQuery(sql);

        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");

            System.out.println(id + ":" + name);
        }

        sql = "drop table user";
        stmt.execute(sql);

//        System.out.println(conn);

        stmt.close();
        conn.close();
    }
}