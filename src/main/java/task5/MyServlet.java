package task5;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private void printQuestion(PrintWriter writer){
		
		writer.println("<html>");
        writer.println("<head><title>My first page</title></head>");
        writer.println("<body>Привет!" +
                "<FORM method=\"post\"> " +
                "Могу ли я <INPUT NAME=\"question\"> <BR> " +
                "<INPUT TYPE=SUBMIT>\n" +
                "</FORM> " +
                "</body>");
        writer.println("</html>");
	}
	private void printDialog(PrintWriter writer, String answer){
		writer.println("<html>");
        writer.println("<head><title>My first page</title></head>");
        writer.println("<body>Привет!" +
                "<FORM method=\"post\"> " +
                "Могу ли я <INPUT NAME=\"question\"> <BR> "+
                "<INPUT TYPE=SUBMIT>\n" +
                "</FORM> <br>" +
                answer +
                "</body>");
        writer.println("</html>");
        writer.close();


	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) // возвращает данные
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		printQuestion(pw);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) // записывает данные
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String q =req.getParameter("question");
		MyDataBase.addQuestion(q);
		//String request = req.getParameter("key_question");
		ReplyGenerator a = new DefaultReplyGenerator();
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		
		//printQuestion(pw);
		printDialog(pw,a.generate());
		pw.close();
	}
	

}
