package ru.fbochkarev.personregister.cron;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.fbochkarev.personregister.model.Person;
import ru.fbochkarev.personregister.soap.SoapClient;
import ru.fbochkarev.personregister.soap.SoapClientImpl;

import javax.xml.soap.SOAPException;
import java.sql.*;
import java.util.Date;
import java.util.List;

@Component
public class CheckInn {
    //  Database credentials
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/register";
    private static final String USER = "user";
    private static final String PASS = "password";
    private SoapClient soapClient = new SoapClientImpl();

    //Обработчик, который запускается каждые 30 секунд
    @Scheduled(cron = "0/30 * * * * ?")
    @SuppressWarnings("unchecked")
    private void check() throws SOAPException, SQLException {
        Statement stmt;
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }

        //--------------- SELECT DATA ------------------
        assert connection != null;
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM person;");
        while (rs.next()) {
            int id = rs.getInt("id");
            String idrequest = rs.getString("person_idrequest");
            String inn = rs.getString("person_inn");

            if (idrequest != null && inn == null) {
                inn = soapClient.getINNFLFIODR(idrequest);
                String sql = "UPDATE person set person_inn = " + inn + " where ID=" + id + ";";
                stmt.executeUpdate(sql);
            }
        }
        rs.close();
        stmt.close();
        System.out.println("-- Operation SELECT done successfully");

    }
}
