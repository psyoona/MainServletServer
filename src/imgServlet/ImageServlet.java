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
	String loginID;
	String fileName;
	
	public void showImage(HttpServletRequest req, HttpServletResponse resp, String filePath) throws IOException{
		resp.setContentType("image/gif");

		byte[] by = new byte[4096]; //한번에 읽어올 파일크기 1024 바이트
		System.out.println("get!");
		
		loginID = req.getParameter("id");
		fileName = req.getParameter("fileName");
		
		System.out.println(loginID);
		System.out.println(fileName);
		
		
		//fileName = fileName.substring(9, fileName.length()-5);
		//System.out.println(fileName.length()-5);
		
		// 데이터베이스에서 로그인 아이디와 파일 내
		

		//출력을위한 OutputStream 객체
		ServletOutputStream out = resp.getOutputStream();
		try {
			//이미지 주소 저장
			//String imagePath = getServletContext().getRealPath("")+"images\\aa.jpg";
			StringBuilder imgPath = new StringBuilder();
			imgPath.append(Constants.IMG_PATH);
			imgPath.append(loginID);
			imgPath.append("/final/");
			imgPath.append(fileName);
			imgPath.append(".jpg");			
			
			System.out.println("설정된 이미지 경로 : " + imgPath.toString());
			
			@SuppressWarnings("resource")
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(imgPath.toString()));
			
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