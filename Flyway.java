package com.flyway;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List;
import org.simplilearn.workshop.util.StringUtil;
i@WebServlet("/Flyway")
public class Flyway extends HttpServlet {
	
	
	public Connection con=null; 
	public Statement st=null;
	public ConnectionUtil() throws ClassNotFoundException, SQLException{ 
	Class.forName("com.mysql.cj.jdbc.Driver");
	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/flyaway","root 
	","9866");
	System.out.println("connection established with database"); 
	st=con.createStatement();
	}
	public List<String[]> getAvailableFlights(String f, String t, String d) {
	List<String[]> flights=new ArrayList<>();
	String query="SELECT * FROM flyaway.flights where fromf='"+f+"' and 
	tof='"+t+"' and datef='"+d+"'";
	try {
	ResultSet rs=st.executeQuery(query);
	if(rs.next()) {
	String[] flight=new String[3]; 
	flight[0]=rs.getString("name"); 
	flight[1]=rs.getString("timef"); 
	flight[2]=rs.getString("price"); 
	flights.add(flight);
	return flights;
	}
	} catch (SQLException e) { 
	e.printStackTrace();
	}
	return null;
	}
	public HashMap<String, String> checkUser(String email, String password) { 
	HashMap<String,String> user=null;
	String query="select * from user where email='"+email+"' and
	password='"+password+"'";
	try {
	ResultSet rs=st.executeQuery(query);
	if(rs.next()) {
	user=new HashMap<>();
	user.put("name", rs.getString("name")); 
	user.put("email",rs.getString("email")); 
	user.put("phno",rs.getString("phno"));
	user.put("adno",rs.getString("adno"));
	}
	return user;
	} catch (SQLException e) { 
	e.printStackTrace();
	}
	return user;
	}
	public boolean insertUser(HashMap<String, String> user) {
	String query="INSERT INTO user (email, password, name, phno, adno) 
	values('"+user.get("email")+"','"+user.get("password")+"','"+user.get("name")+"',' 
	"+user.get("phno")+"','"+user.get("adno")+"')";
	try {
	st.execute(query);
	return true;
	} catch (SQLException e) { 
	e.printStackTrace();
	}
	return false;
	}
	public boolean checkAdmin(String email, String password) {
	try {
	ResultSet rs=st.executeQuery("select * from admin where
	email='"+email+"' and password='"+password+"'");
	if(rs.next())
	return true;
	} catch (SQLException e) { 
	e.printStackTrace();
	}
	return false;
	}
	public boolean changeAdminPassword(String email, String password) {
	try {
	ResultSet rs=st.executeQuery("select * from admin where
	email='"+email+"'");
	if(!rs.next()) {
	return false;
	}
	st.execute("update admin set password='"+password+"' where
	email='"+email+"'");
	return true;
	} catch (SQLException e) {
	e.printStackTrace();
	}
	return false;
	}
	public boolean insertFlight(HashMap<String, String> flight) throws SQLException {
	//PreparedStatement stm=con.prepareStatement("INSERT INTO 'flyaway.flights' 
	('name', 'from', 'to', 'date', 'time', 'price') values(?,?,?,?,?,?)");
	//String sql="INSERT INTO flights ('name','from','to','date','time','price') 
	values('"+flight.get("name")+"','"+flight.get("from")+"','"+flight.get("to")+"','"
	+flight.get("date")+"','"+flight.get("time")+"','"+flight.get("price")+"');"; String
	query1 = "INSERT INTO flights (name, fromf, tof, datef, timef, price)
	VALUES" + " ('"
	+ StringUtil.fixSqlFieldValue(flight.get("name")) + "'," + " '"
	+ StringUtil.fixSqlFieldValue(flight.get("from")) + "'," + " '"
	+ StringUtil.fixSqlFieldValue(flight.get("to")) + "'," + " '" + 
	StringUtil.fixSqlFieldValue(flight.get("date")) + "'," + " '"
	+ StringUtil.fixSqlFieldValue(flight.get("time")) + "'," + " '"
	+ StringUtil.fixSqlFieldValue(flight.get("price")) + "')";
	//String sql="INSERT INTO `flyaway`.`flights` (`name`, `fromf`, `tof`,
	`datef`, `timef`, `price`) VALUES ('indigo', 'hyd', 'viz', '2021-04-08', '10:00', 
	'2000');";
	System.out.println(flight.get("date")); 
	System.out.println(flight.get("time"));
	//String query1="INSERT into flyaway.flights (name,from,to,date,time,price) 
	values('indigo','hyd','viz','24-02-2022','10:30','2000')";
	try {
	//stm.execute(); 
	st.execute(query1); 
	return true;
	} catch (SQLException e) { 
	System.out.print("error"); 
	e.printStackTrace();
	}
	return false;
	}
	}
	SERVLETS
	FilghtInsert.java
	package com.flightbooking.servlets;
	import javax.servlet.annotation.WebServlet; 
	import javax.servlet.http.HttpServlet; 
	import java.io.IOException;
	import java.sql.SQLException;
	import java.util.HashMap;
	import javax.servlet.ServletException;
	import javax.servlet.http.HttpServletRequest; 
	import javax.servlet.http.HttpServletResponse; 
	import javax.servlet.http.HttpSession;
	import com.flightbooking.database.ConnectionUtil;
	@WebServlet("/InsertFlight")
	public class FilghtInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	String name=request.getParameter("name"); 
	String from=request.getParameter("from"); 
	String to=request.getParameter("to");
	String departure=request.getParameter("departure"); 
	String time=request.getParameter("time");
	String price=request.getParameter("price");
	HashMap<String,String> flight=new HashMap<>(); 
	flight.put("name", name);
	flight.put("from", from); 
	flight.put("to", to); 
	flight.put("date", departure); 
	flight.put("time", time); 
	flight.put("price", price);
	try {
	ConnectionUtil dao=new ConnectionUtil(); 
	HttpSession session=request.getSession(); 
	if(dao.insertFlight(flight)) {
	Successfully");
	}
	else {
	}
	session.setAttribute("message", "Flight Added
	session.setAttribute("message", "Invalid Details");
	} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block 
	System.out.print("error"); 
	e.printStackTrace();
	}
	response.sendRedirect("AdminHome.jsp");
	}
	}
	ForgotPassword.java
	package com.flightbooking.servlets;
	import javax.servlet.annotation.WebServlet;
	import java.io.IOException;
	import java.sql.SQLException;
	import javax.servlet.ServletException; 
	import javax.servlet.http.HttpServlet; 
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	import com.flightbooking.database.ConnectionUtil; 
	@WebServlet("/ForgotPassword")
	public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
	String email=request.getParameter("email"); 
	String password=request.getParameter("password");
	try {
	Successfully");
	ConnectionUtil dao=new ConnectionUtil(); 
	HttpSession session=request.getSession(); 
	if(dao.changeAdminPassword(email,password)) {
	session.setAttribute("message", "Password Changed
	}
	else {
	}
	session.setAttribute("message", "Invalid Details");
	} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block 
	e.printStackTrace();
	}
	response.sendRedirect("AdminPage.jsp");
	}
	}
	ListOfFlights.java
	package com.flightbooking.servlets;
	import java.io.IOException; 
	import java.sql.SQLException; 
	import java.util.List;
	import javax.servlet.ServletException; 
	import javax.servlet.annotation.WebServlet; 
	import javax.servlet.http.HttpServlet; 
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	import com.flightbooking.database.ConnectionUtil; 
	@WebServlet("/FlightList")
	public class ListOfFlights extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse 
	response) throws ServletException, IOException {
	String from=request.getParameter("from"); 
	String to=request.getParameter("to");
	String departure=request.getParameter("departure");
	departure);
	try {
	ConnectionUtil dao = new ConnectionUtil(); 
	List<String[]> flights=dao.getAvailableFlights(from, to,
	HttpSession session=request.getSession(); 
	session.setAttribute("flights", flights);
	} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block 
	e.printStackTrace();
	}
	response.sendRedirect("FlightList.jsp");
	}
	}
	LoginOfAdmin.java
	package com.flightbooking.servlets;
	import java.io.IOException;
	import java.sql.SQLException;
	import javax.servlet.ServletException; 
	import javax.servlet.annotation.WebServlet; 
	import javax.servlet.http.HttpServlet; 
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	import com.flightbooking.database.ConnectionUtil;
	@WebServlet("/AdminLogin")
	public class LoginOfAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	String email=request.getParameter("email");
	String password=request.getParameter("password");
	try {
	ConnectionUtil dao=new ConnectionUtil();
	if(dao.checkAdmin(email,password)) { 
	response.sendRedirect("AdminHome.jsp");
	}
	else {
	}
	HttpSession session=request.getSession(); 
	session.setAttribute("message", "Invalid Details"); 
	response.sendRedirect("AdminPage.jsp");
	} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block 
	e.printStackTrace();
	}
	}
	}
	LoginOfUser.java
	package com.flightbooking.servlets;
	import javax.servlet.annotation.WebServlet;
	import java.io.IOException; 
	import java.sql.SQLException; 
	import java.util.HashMap;
	import javax.servlet.ServletException; 
	import javax.servlet.http.HttpServlet; 
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	import com.flightbooking.database.ConnectionUtil; 
	@WebServlet("/UserLogin")
	public class LoginOfUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	String email=request.getParameter("email"); 
	String password=request.getParameter("password");
	try {
	ConnectionUtil dao=new ConnectionUtil(); 
	HashMap<String,String> user=dao.checkUser(email,password); 
	HttpSession session=request.getSession();
	if(user!=null) {
	session.setAttribute("user", user); 
	response.sendRedirect("HomePage.jsp");
	}
	else {
	}
	session.setAttribute("message", "Invalid Details"); 
	response.sendRedirect("UserPage.jsp");
	} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block 
	e.printStackTrace();
	}
	}
	}
	Logout.java
	package com.flightbooking.servlets;
	import javax.servlet.annotation.WebServlet;
	import java.io.IOException;
	import javax.servlet.ServletException; 
	import javax.servlet.http.HttpServlet; 
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	@WebServlet("/Logout")
	public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	HttpSession session=request.getSession(); 
	session.setAttribute("user", null); 
	response.sendRedirect("HomePage.jsp");
	}
	}
	RegistrationOfUser.java
	package com.flightbooking.servlets;
	import javax.servlet.annotation.WebServlet;
	import java.io.IOException; 
	import java.sql.SQLException; 
	import java.util.HashMap;
	import javax.servlet.ServletException; 
	import javax.servlet.http.HttpServlet; 
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	import com.flightbooking.database.ConnectionUtil; 
	@WebServlet("/UserRegistration")
	public class RegistrationOfUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse 
	response) throws ServletException, IOException {
	String email=request.getParameter("email"); 
	String password=request.getParameter("password"); 
	String name=request.getParameter("name");
	String phno=request.getParameter("phno"); 
	String adno=request.getParameter("adno");
	HashMap<String,String> user=new HashMap<>(); 
	user.put("email", email); 
	user.put("password", password); 
	user.put("name", name);
	user.put("phno", phno);
	user.put("adno", adno);
	try {
	Successfully");
	ConnectionUtil dao=new ConnectionUtil(); 
	boolean result=dao.insertUser(user); 
	HttpSession session=request.getSession(); 
	if(result) {
	session.setAttribute("message", "User Added
	}
	else {
	}
	session.setAttribute("message","Invalid Details");
	} catch (ClassNotFoundException | SQLException e) {
	// TODO Auto-generated catch block 
	e.printStackTrace();
	}
	response.sendRedirect("UserPage.jsp");
	}
	}
	StringUtil.java
	package com.simplelearn.util;
	public class StringUtil {
	public static String fixSqlFieldValue(String value) {
	if (value == null) {
	return null;
	}
	int length = value.length();
	StringBuffer fixedValue = new StringBuffer((int)(length*1.1));
	for(int i = 0 ; i < length ;i++) { 
	char c = value.charAt(i); if 
	( c == '\'') {
	fixedValue.append("''");
	}else {
	fixedValue.append(c);
	}
	}
	return fixedValue.toString();
	}
	public static String encodeHtmlTag(String tag) {
	if (tag==null)
	return null;
	int length = tag.length();
	StringBuffer encodedTag = new StringBuffer(2 * length);
	for(int i = 0 ; i < length;i++) { 
	char c = tag.charAt(i); 
	if(c=='<')
	encodedTag.append("<");
	else if(c=='>')
	encodedTag.append(">");
	else if(c=='&')
	encodedTag.append("&amp;");
	else if(c=='"')
	encodedTag.append("&quot;");
	else if(c==' ')
	encodedTag.append("&nbsp;");
	else
	}
	encodedTag.append(c);
	return encodedTag.toString();
	}
	}
	