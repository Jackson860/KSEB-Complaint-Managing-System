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

@WebServlet("/materialRequest")
public class MaterialRequest extends HttpServlet{

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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			
			con=dbCon.getConnection();
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			int staffID = Integer.parseInt(session.getAttribute("staffID").toString());
			
			out.print("<html>");
			out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/addComplaint.css\" /></head>");
			out.print("<body>");
			out.print("<div class='add-complaint-box'> ");
			out.print(" <h2>REQUEST MATERIALS</h2> ");
			out.print(" <form method='post' action='newMaterialRequest'>");
			out.print(" <div class='user-box'> ");
			out.print(" <input type='date' name='date' required='' id='date'>");
			out.print(" <label class='selectLabel'>Date</label> ");
			out.print(" </div> ");
			out.print(" <div class='user-box'> ");
			out.print(" <select name='allocationID' id='allocationID'>");
			
			pst=con.prepareStatement("select work_alloc_id from work_allocation where staff_id=?");
			pst.setInt(1, staffID);
			rs=pst.executeQuery();
			while(rs.next()) {
				out.print("<option value="+rs.getInt(1)+">"+rs.getInt(1)+"</option>");
			}
			rs.close();
			
			out.print("</select>");
			out.print("<label class='selectLabel'>Allocation ID</label> ");
			out.print(" </div> ");
			out.print(" <div class='user-box'> ");
			out.print(" <textarea name='materialDetails' id='materialDetails' required=''></textarea>");
			out.print(" <label>Material Details</label>");
			out.print(" </div> ");
			out.print(" <div class='wrapper'>");
			out.print(" <input type='submit' value='REQUEST MATERIALS' id='submit' />");
			out.print(" <span></span> <span></span> <span></span> <span></span> <span></span>");
			out.print(" </div> ");
			out.print(" </form>");
			out.print("</div>");
			out.print("</body>");
			out.print("</html>");
			
		} 	catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
