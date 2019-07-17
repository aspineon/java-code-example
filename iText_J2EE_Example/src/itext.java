

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Servlet implementation class itext
 */
public class itext extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:sss");
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			// ������� ��������
			Document document = new Document();
			// ������� ���� ��� ������ ������ 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// ������� �������� � ���� ��� ������ ������
			PdfWriter.getInstance(document, baos);
			// ������� �������� 
			document.open();
			// ���������� ����������� �����������
			document.add(new Paragraph(sdf.format(new java.util.Date())));
			// ������� �������� - ������������� ��� ���������
			document.close();
			
			// ������� ������������ (��������) �� �����
			response.setContentType("application/pdf");
			// ������� ������������ (��������) �� ������ ������ 
			response.setContentLength(baos.size());
			// �������� ������ ��� ������ ������
			ServletOutputStream out = response.getOutputStream();
			// ������ ���� ������������ (��������)
			baos.writeTo(out);
			out.flush();		
		}catch(DocumentException ex){
			PrintWriter pw=response.getWriter();
			pw.println("<html>");
			pw.println("	<head>");
			pw.println("	<title>Error</title>");
			pw.println("	</head>");
			pw.println("	<body>"+ex.getMessage()+"</body>");
			pw.println("</html>");
			pw.close();
		}
	}
}
