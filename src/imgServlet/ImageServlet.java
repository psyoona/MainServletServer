package imgServlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Constants;

public class ImageServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void showImage(HttpServletRequest req, HttpServletResponse resp, String filePath) throws IOException{
		resp.setContentType("image/gif");

		byte[] by = new byte[4096]; //�ѹ��� �о�� ����ũ�� 1024 ����Ʈ
		System.out.println("get!");
		
		System.out.println(req.getParameter("id"));

		//��������� OutputStream ��ü
		ServletOutputStream out = resp.getOutputStream();
		try {
			//�̹��� �ּ� ����
			//String imagePath = getServletContext().getRealPath("")+"images\\aa.jpg";
			String imagePath = Constants.IMG_PATH+"root/album/frame.jpg";			
			
			//inputStream : ������ 1����Ʈ�� �о��
			//BufferedInputStream : inputStream��ü�� ���۰�ü�� ����
			@SuppressWarnings("resource")
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(imagePath));
			
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
		String filePath = "test";
		showImage(req, resp, filePath);
	}	
	
	void showImage(String id){
		
	}
}