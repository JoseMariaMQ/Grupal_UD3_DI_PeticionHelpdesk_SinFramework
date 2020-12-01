package model;

import java.sql.*;

public class AccessingDataBase {
    private Connection conn;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private ResultSet rsRows;
    private PreparedStatement psUp;

    private int cont = 0;
    public Object[][] servicesTable;
    public Services s;

    public AccessingDataBase() {
    }

    public void connect() {

        try {
            System.out.println("Intentando conexión con BBDD...");
            //Carga driver mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/store";
            String user = "root";
            String password = "";

            //Objeto conexión
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión con éxito");
            st = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertService(Services service) {
        String sql = "INSERT INTO services (user, phone, email, city, cp, services) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            System.out.println("Insertando servicio...");
            ps = conn.prepareStatement(sql);

            ps.setString(1, service.getUser());
            ps.setInt(2, service.getPhone());
            ps.setString(3, service.getEmail());
            ps.setString(4, service.getCity());
            ps.setInt(5, service.getCp());
            ps.setString(6, service.getServices());

            ps.executeUpdate();
            System.out.println("Servicio insertado correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void countRows() {
        try {
            rsRows = st.executeQuery("SELECT * FROM services;");
            while(rsRows.next()) {
                cont++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                rsRows.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void showServices() {
        countRows();
        String sql = "SELECT * FROM services;";
        try {
            rs = st.executeQuery(sql);
            servicesTable = new Object[cont][7];

            for(int j=0; j<servicesTable.length; j++) {
                rs.next();
                for(int k=0; k<servicesTable[j].length; k++) {
                    servicesTable[j][k] = rs.getString(k+1);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                st.close();
                rs.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void updateService(Services s) {
        String sql = "UPDATE services SET user = ?, phone = ?, email = ?, city = ?, cp = ?, services = ? WHERE id = ?;";
        try {
            System.out.println("Actualizando servicio...");
            psUp = conn.prepareStatement(sql);

            psUp.setString(1, s.getUser());
            psUp.setInt(2, s.getPhone());
            psUp.setString(3, s.getEmail());
            psUp.setString(4, s.getCity());
            psUp.setInt(5, s.getCp());
            psUp.setString(6, s.getServices());
            psUp.setInt(7, s.getId());

            psUp.executeUpdate();
            System.out.println("Servicio actualizado correctamente!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                psUp.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
