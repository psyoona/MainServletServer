package imgServlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import img.ImageMerge;

public class ImageFilterServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	String loginID;
	String fileName;
	
	public void showImage(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("image/gif");

		byte[] by = new byte[4096]; //�ѹ��� �о�� ����ũ�� 1024 ����Ʈ
		loginID = req.getParameter("id");
		fileName = req.getParameter("filterName");		

		//��������� OutputStream ��ü
		ServletOutputStream out = resp.getOutputStream();
		try {
			//�̹��� �ּ� ����
			String path = ImageMerge.makeFilterPath(loginID, fileName);
			
			System.out.println(path);
			
			@SuppressWarnings("resource")
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
			
			//����(in)�� �ִ� �����͸� 2048����Ʈ(by) ��ŭ �о���� �����Ͱ� ������� �ݺ��� ����
			while(in.read(by) != -1) {
				out.write(by); //2048����Ʈ�� ���
			}
			//in.close();			
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
		
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("tesst");
		showImage(req, resp);
	}

}