package server;

import java.io.File;

/*
 * 회원가입이 완료되었을 때
 * 아이디 명으로 된 폴더를 생성한다.
 */
public class MakeDirectory {
	public MakeDirectory(){}
	
	static void makeDirectory(String userID){
		 //생성할 파일경로 지정
        String path = "D://img//" + userID;
        //파일 객체 생성
        File file = new File(path);
        //!표를 붙여주어 파일이 존재하지 않는 경우의 조건을 걸어줌
        if(!file.exists()){
            //디렉토리 생성 메서드
            file.mkdirs();
            System.out.println(userID +" folder created directory successfully!");
        }
	}	
}
