package com.simplilearn.user;


	import java.io.IOException;  
	import java.io.PrintWriter;  
	import javax.servlet.ServletException;  
	import javax.servlet.http.HttpServlet;  
	import javax.servlet.http.HttpServletRequest;  
	import javax.servlet.http.HttpServletResponse;  
	import javax.servlet.http.HttpSession;  
	public class DashboardServlet extends HttpServlet {  
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
	                      throws ServletException, IOException {  
	response.setContentType("text/html");  
	PrintWriter out=response.getWriter();  
	request.getRequestDispatcher("link.html").include(request, response);  

	HttpSession session=request.getSession(false);  
	        if(session!=null){  
	        String name=(String)session.getAttribute("name");  

	out.print("Hello, "+name+" Welcome to Dashboard<br><br>");
	out.print("Shivu Dasboard");
	out.print("<a href='LogoutServlet'>Logout</a>");
	        }  
	else{
	out.print("\nPlease login first");  
	request.getRequestDispatcher("login.html").include(request, response);  
	        }  
	out.close();  
	    }  
	}  


