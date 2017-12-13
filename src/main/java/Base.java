import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Base {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return connection;
    }
    public void addBus(String mark, int capacity, String number){

        try {
            Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("insert into bus(Number,Capacity,Mark) VALUES ("+ "'" + number + "','" + capacity+ "','" + mark + "');");
        } catch (SQLException e) {
            System.out.println("Can not add object to database");
        }
    }
    public void deleteBus(int idBus){
        try {
            Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("delete from bus where idBus = "+idBus+";");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void updateBus(String number, int capacity, String mark, int id) throws SQLException {
        Connection conn =DriverManager.getConnection(URL,USERNAME,PASSWORD);
        PreparedStatement statement= conn.prepareStatement("UPDATE  products SET Number='"+number+"',Capacity = "+capacity+"','Mark = "+mark+"',"+" WHERE id='"+id+"';");
        statement.execute();
    }
    public static void insertGarage(Garage b) throws SQLException {
        Connection conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        PreparedStatement statement = conn.prepareStatement("insert into garage (Capacity,Location) values('"+b.getLocation()+"','"+b.getCapacity()+"')",Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        Statement st = conn.createStatement();
        conn.setAutoCommit(false);
        for(Bus p: b.getBuses()){
            st.addBatch("insert into bus (Number,Capacity,Mark) values('" + p.getNumber() + "','" + p.getCapacity() + "','" + p.getMark() + "','" + rs.getInt(1) + "')");
        }
        int [] count=st.executeBatch();
        conn.commit();
        conn.close();
    }
    public static void deleteBasket(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        Statement statement =conn.createStatement();
        statement.execute("delete from bus where bus.garage="+id+";");
        Statement statement2=conn.createStatement();
        statement2.execute("delete from garage WHERE idgarage="+id+";");
        conn.close();
    }
    public static void deleteAllBuses() throws SQLException {
        Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        Statement statement = conn.createStatement();
        statement.execute("delete from bus;");
        conn.close();
    }
    public   List<Bus> getAllBuses() throws SQLException {
        Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        List<Bus> res = new ArrayList<Bus>();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM products;");
        while(rs.next()){
            Marks b = Marks.valueOf("Mark." + rs.getString("Marks"));
            Bus p = new Bus(b,rs.getInt("Capacity"),rs.getString("Number"));
            res.add(p);
        }
        return res;
    }
    public static void main(String[] args) throws SQLException {
        Base base = new Base();
        base.addBus("Laz",332,"32FEfE32");

    }
}
