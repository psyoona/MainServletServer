package login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
	public static String encryption(String str) {
		String SHA = null;
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			SHA = sb.toString();
			System.out.println(SHA);
			System.out.println(str);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}
		return SHA;
	}
}
