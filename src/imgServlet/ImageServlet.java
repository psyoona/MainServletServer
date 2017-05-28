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

		byte[] by = new byte[4096]; //한번에 읽어올 파일크기 1024 바이트
		System.out.println("get!");
		
		System.out.println(req.getParameter("id"));

		//출력을위한 OutputStream 객체
		ServletOutputStream out = resp.getOutputStream();
		try {
			//이미지 주소 저장
			//String imagePath = getServletContext().getRealPath("")+"images\\aa.jpg";
			String imagePath = Constants.IMG_PATH+"root/album/frame.jpg";			
			
			//inputStream : 파일을 1바이트씩 읽어옴
			//BufferedInputStream : inputStream객체로 버퍼객체를 생성
			@SuppressWarnings("resource")
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(imagePath));
			
			//버퍼(in)에 있는 데이터를 2048바이트(by) 만큼 읽어오고 데이터가 없을경우 반복문 종료
			while(in.read(by) != -1) {
				out.write(by); //2048바이트씩 출력
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