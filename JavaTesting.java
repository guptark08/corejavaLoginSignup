package javatesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.SQLException;

public class JavaTesting {

    Scanner sc = new Scanner(System.in);
    //for SQL query
    Connection con;
    Statement st;
    ResultSet rs;
    //signup value string
    String name, age, gender, emailID, password;
    //inputing value string
    String username, passwrd;

    public void employeeSignup() throws IOException{
        // name 
        while (name == null) {
            System.out.println("Enter your name:");
            name = sc.next();
            if (!name.matches("[a-zA-Z]+")) {
                name = null;
            }
        }
        // age 
        while (age == null) {
            System.out.println("Enter your Age:");
            age = sc.next();
            if (!age.matches("[0-9]{2}")) {
                age = null;
            }
        }
        // gender
        while (gender == null) {
            System.out.println("Enter your gender: M/F");
            gender = sc.next();
            if (!gender.matches("[MFmf]?")) {
                gender = null;
            }
        }
        //emailID
        while (emailID == null) {
            System.out.println("Enter your mail ID:");
            emailID = sc.next();
            if (!emailID.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}")) {
                emailID = null;
            }
        }
        //password
        while (password == null) {
            System.out.println("Enter your Password:");
            password = sc.next();
            if (!password.matches(".{8,}")) {
                System.out.println("Password must be 8 characters long");
                password = null;
            }
        }
        insertEmp();
        mainpart();
    }

    public void adminViewDatabase() {
        String findname;
        System.out.println("Press 'Y' to see the employee records 'S' for searching name");
        char ch = sc.next().charAt(0);
        if (ch == 'Y' || ch == 'y') {
            try {
                connect();
                st = con.createStatement();
                rs = st.executeQuery("select* from emp");
                while (rs.next()) {
                    System.out.println("List of all Employee are:");
                    System.out.println("name:" + rs.getString(1));
                    System.out.println("age:" + rs.getString(2));
                    System.out.println("gender:" + rs.getString(3));
                    System.out.println("mailID:" + rs.getString(4));
                }
            } catch (SQLException e) {
            }
        } else if (ch == 'S' || ch == 's') {

            System.out.println("Enter Keywords to find Name");
            findname = sc.next();
            try {
                connect();
                st = con.createStatement();
                rs = st.executeQuery("select * from emp where name like'" + findname + "'");
                while (rs.next()) {
                    System.out.println("Employee are:");
                    System.out.println("name:" + rs.getString(1));
                    System.out.println("age:" + rs.getString(2));
                    System.out.println("gender:" + rs.getString(3));
                    System.out.println("mailID:" + rs.getString(4));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void employeeLogin() {
        connect();
        System.out.println("Enter your username:");
        String loginusername = sc.next();
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from emp where emailid='" + loginusername + "'");
            if (rs.next()) {
                System.out.println("Enter Password");
                passwrd = sc.next();
                String pass = rs.getString(5);
                if (passwrd.equals(pass)) {
                    System.out.println("Welcome:" + rs.getString(1));
                    employeeView();
                } else {
                    System.out.println("Incorrect password" + rs.getString(1));
                }
            } else {
                System.out.println("not valid user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        try
//        {
//            connect();
//            st=con.createStatement();
//            rs=st.executeQuery("select * from emp where emailid='"+username+"' and passkey='"+passwrd+"'");
//            if(!rs.next())
//            {
//                System.out.println("No such User in database");
//            }
//            else
//            {
//                System.out.println("Welcome " + rs.getString(1));
//            }
//        }       
    }

    public void employeeView() {
        System.out.println("Enter 'e' for edit the details");
        char empOption = sc.next().charAt(0);

    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectfirst", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEmp() {
        try {
            connect();
            PreparedStatement ps = con.prepareStatement("insert into emp values(?,?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, age);
            ps.setString(3, gender);
            ps.setString(4, emailID);
            ps.setString(5, password);
            int x = ps.executeUpdate();
            if (x > 0) {
                System.out.println("registered");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchDetails() {
        try {
            connect();
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from emp");
            while (rs.next()) {
                rs.getString(1);
                rs.getString(2);
                rs.getString(3);
                rs.getString(4);
                rs.getString(5);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mainpart() throws IOException{
        char option;
        System.out.println("Welcome to Project 'Arush'");
        System.out.println("Enter 'L' for Login And Enter 'S' for Signup for Employee");
        option = sc.next().charAt(0);
        switch (option) {
            case 'L':
            case 'l':
                char againOption;
                System.out.println("Enter 'A' for Admin And Enter 'E' for Employee");
                againOption = sc.next().charAt(0);
                switch (againOption) {
                    case 'A':
                    case 'a':
                        admin();
                        break;
                    case 'E':
                    case 'e':
                        employeeLogin();
                        break;
                }
                break;
            case 'S':
            case 's':
                employeeSignup();
                break;
        }
    }

    public void admin() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String admin = "admin", pass;
        String uadmin, upass;
        String sb1 = "admin";
        String nsb1;
        String sb2 = "123";
        System.out.println("Enter adminID:");
        uadmin = br.readLine();
        if (uadmin.equals(sb1)) {
            System.out.println("Enter your Password:");
            upass = br.readLine();
            if (upass.equals(sb2)) {
                System.out.println("Welcome admin");
                System.out.println("Enter 1 to change admin ID");
                int a = sc.nextInt();
                switch (a) {
                    case 1:
                        System.out.println("change admin ID");
                        String newadminID = br.readLine();
                        nsb1 = sb1.replace("admin", newadminID);
                        System.out.println("now your new ID is :" + newadminID);
                        break;
                }
            } else {
                System.out.println("Sorry!! you are not valid user");
            }
        } else {
            System.out.println("admin not matched");
        }

//        String admin = "admin", pass = "123";
//        String uadmin, upass;
//        System.out.println("Enter adminID:");
//        uadmin = sc.nextLine();
//
//        if (uadmin.equals(admin)) {
//            System.out.println("Enter pass:");
//            upass = sc.nextLine();
//            if (upass.equals(pass)) {
//                System.out.println("Welcome Admin");
//                adminViewDatabase();
//                System.out.println("Enter 'exit'for Logout otherwise just enter 'no'");
//                String exit = sc.next();
//                if (exit.equals("no")) {
//                    mainpart();
//                }
//            } else {
//                System.out.println("Incorrect password");
//            }
//        } else {
//            System.out.println("admin not matched");
//        }
    }

    public static void main(String[] args) throws IOException {
        JavaTesting obj = new JavaTesting();
        obj.mainpart();
    }
}