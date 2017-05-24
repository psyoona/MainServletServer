package server;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/gif");

		byte[] by = new byte[1024]; //�ѹ��� �о�� ����ũ�� 1024 ����Ʈ

		//��������� OutputStream ��ü
		ServletOutputStream out = resp.getOutputStream();
		try {
			//�̹��� �ּ� ����
			//String imagePath = getServletContext().getRealPath("")+"images\\aa.jpg";
			String imagePath = Constants.IMG_PATH;			
			
			//inputStream : ������ 1����Ʈ�� �о��
			//BufferedInputStream : inputStream��ü�� ���۰�ü�� ����
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(imagePath));
			//����(in)�� �ִ� �����͸� 1024����Ʈ(by) ��ŭ �о���� �����Ͱ� ������� �ݺ��� ����
			while(in.read(by) != 0) {
				out.write(by); //1024����Ʈ�� ���
			}
			in.close();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}	
	
	void showImage(String id){
		
	}
}