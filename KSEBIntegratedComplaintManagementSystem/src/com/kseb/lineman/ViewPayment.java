package com.kseb.lineman;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kseb.DBConnection;

@WebServlet("/viewPayment")
public class ViewPayment extends HttpServlet{

	/**
	 * @author JACKSON
	 */
	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	PreparedStatement pstNew=null;
	ResultSet rsNew=null;
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher=null;

	int allocationID=0;
	int paymentID=0;
	String date=null;
	Float amount=0f;
	String details=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	try {
		response.setContentType("text/html");
		
		con=dbCon.getConnection();
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		int staffID = Integer.parseInt(session.getAttribute("staffID").toString());
		
		out.print("<html>");
		out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/table.css\" /></head>");
		out.print("<body>");
		out.print("<h1>Payments</h1>");
		out.print("<div class='tableop'>");
		out.print("<table>");
		out.print("<tr><th>Payment ID</th><th>Payment Date</th><th>Allocation ID</th><th>Payment Amount</th><th>Payment Details</th><th>Edit</th></tr>");
		
		pst=con.prepareStatement("select work_alloc_id from work_allocation where staff_id=?");
		pst.setInt(1, staffID);
		rs=pst.executeQuery();
		while(rs.next()) {
			allocationID=rs.getInt(1);
			
			pstNew=con.prepareStatement("select payment_bill_id, payment_bill_date, payment_bill_amount, payment_bill_details from payment_bill where payment_work_alloc_id=?");
			pstNew.setInt(1, allocationID);
			rsNew=pstNew.executeQuery();
			while(rsNew.next()) {
				paymentID=rsNew.getInt(1);
				date=rsNew.getString(2);
				amount=rsNew.getFloat(3);
				details=rsNew.getString(4);

				out.print("<tr><td>"+paymentID+"</td><td>"+date+"</td><td>"+allocationID+"</td><td>"+amount+"</td><td>"+details+"</td><td>edit</td></tr>");
			}
			rsNew.close();
		}
		rs.close();
		
		out.print("</table>");
		out.print("</div>");
		out.print("</body>");
		out.print("</html>");
	    
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	}
}
