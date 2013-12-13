package task5;

import java.sql.*;
import java.util.ArrayList;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

public class MyDataBase {
	private static boolean initialized = false;

    public static void init() {
        try {
            // Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            initialized=true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to load the JDBC driver class");
        }
        connect();
    }

    private static Connection connection;

    public static void connect() {
        try {
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/talkie", "root", "");
            connection.createStatement().execute("set names cp1251");
        } catch (SQLException x) {
            x.printStackTrace();
            System.out.println("Couldn’t get connection !");
        }
    }

    public static void addAllAnswersToDatabase() throws SQLException {
        ArrayList<String> answers = new ArrayList<String>();
        answers.add("It's good idea");
        answers.add("You can try...");
        answers.add("No, don't do it");
        answers.add("Yes, of course");
        answers.add("You can, but sooo carefully");
        answers.add("Your question has been sent to the FSB. Wait for you drove");
        for (String s:answers){
            connection.createStatement().executeUpdate("insert into answers (answer) values (\"" + s + "\");");
        }
    }

    static int counter = 0;

    public static ArrayList<String> getAnswers() throws SQLException {
        ArrayList<String> outputList = new ArrayList<String>();

        PreparedStatement pps = connection.prepareCall("show databases;");
        ResultSet rrs = pps.executeQuery();
        while(rrs.next()) {
            System.out.println(rrs.getObject(1));
        }

        PreparedStatement stmt = connection.prepareStatement("select * from answers;");
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            addAllAnswersToDatabase();
            return getAnswers();
        } else {
            rs.beforeFirst();
            while (rs.next()){
                outputList.add(rs.getString(1));
            }
            return outputList;
        }
    }

    public static void addQuestion(String a) {
        if (!initialized) {
            init();
        }
        try {
            connection.prepareStatement("insert into questions (question) values (\"" + a + "\")").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


}