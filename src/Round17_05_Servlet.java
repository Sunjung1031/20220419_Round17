import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Round17_05_Servlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = this.getServletContext().getRealPath("/data");
		String file_name = this.getInitParameter("result");
		
		File dir = new File(path);
		if(!dir.exists()) dir.mkdir();
		File file = new File(dir, file_name);
		
		int agree = 0;
		int disagree = 0;
		if(file.exists()) {
			DataInputStream f_in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			
			try {
				agree = f_in.readInt();
				disagree = 0;
			}catch(Exception e) {
				agree = 0;
				disagree = 0;
					}
			f_in.close();
		}
		
		String choice = req.getParameter("choice");
		
		if(choice != null && choice.equalsIgnoreCase("yes")) agree++;
		else if(choice != null && choice.equalsIgnoreCase("no")) disagree++;
		
		DataOutputStream f_out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		
		f_out.writeInt(agree);
		f_out.writeInt(disagree);
		f_out.close();
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		out.println("<html><head><title>결과</title></head>");
		out.println("<body><center>");
		out.println("<h3>찬/반 투표 결과!</h3>");
		out.println("<img src='Servlet_05_Sub?agree=" + agree);
		out.println("&disagree=" + disagree + "'/>");
		out.println("</center></body></html>");
		

	}

}
