package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CmdProcess {
	static String dburl = null;
	static JDBC adminJDBC;
	static boolean checkLogin; // �α��� ó�� �Ǿ����� üũ�ϴ� ����
	static boolean checkID; // �ߺ����̵� �ִ��� ó���ϴ� ����
	static String loginID; // �α��ε� ���̵� �ӽ÷� �����ϱ� ���� ����
	static String imgAddress; // �̹��� ��θ� �ӽ÷� �����صα� ���� ����

	static String[] album; // �����ͺ��̽����� ������ �ٹ� ����� ������ ���� ����
	static String[] fileName; // client�� ���� ���� ��θ� ������ ���� ����
	static int count = 0; // ������ �� �� ���Դ��� üũ�ϱ� ���� ����
	static String selectEmotion; // �����ͺ��̽����� ������ ���� ������ ����

	static EstimationAnalysis emotion; // ���� �м��ϱ� ���� Ŭ����

	protected static void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String result = CmdProcess.getBody(req);
		String[] array = result.split("\"");
		System.out.println(result);
		
		
		cmdProcess(array, resp);

	}

	// �Է°��� ���� ó������ �κ�
	public static void cmdProcess(String[] array, HttpServletResponse resp) throws IOException {
		// � ��ư�� Ŭ���Ǿ������� ���� ó���ϱ� ���� for-switch��
		for (int i = 0; i < array.length; i++) {
			switch (array[i]) {
			case "check":
				// ���̵� �ߺ�Ȯ�� ��ư�� Ŭ���� ���
				ID_Check idCheck = new ID_Check();
				idCheck.loginCheck(array, resp);
				break;

			case "register":
				// ȸ������ �ۼ��Ϸ� ��ư�� Ŭ���� ���
				Register info = new Register();
				info.register(array);
				break;

			case "login":
				// �α��� ��ư�� Ŭ���� ���
				Login access = new Login();
				access.login(array, resp);
				break;

			case "imgSave":
				// �̹��� ���� ��ư�� Ŭ���� ���
				ImgSave save = new ImgSave();
				save.imgSave(array);
				break;

			case "showAlbum":
				// ��� history ��ư�� Ŭ���� ���
//				ShowMergeAlbum album = new ShowMergeAlbum();
//				album.showMergeAlbum(array, resp);
				break;

			case "makeAlbum":
				// ���� ���� �� �ڵ����� �ٹ� ����� ����� �����
				MakeAlbum mkAlbum = new MakeAlbum();
				mkAlbum.makeAlbum(array, i);
				break;

			default:
				break;
			}
		}
	}

	public static String getBody(HttpServletRequest request) throws IOException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			}
		} catch (IOException ex) {
			throw ex;

		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();

		return body;
	}
}
