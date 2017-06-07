package server;

// 상수들을 정의해놓는 클래스

public class Constants {
	public static String SUCCESS = "success"; // 로그인 성공 시 보낼 메시지
	public static String FAIL = "fail"; // 로그인 실패 시 보낼 메시지
	public static String ID = "id"; 
	public static String PW = "pw";
	public static String NICKNAME = "nickname";
	public static String USING = "using"; // 아이디가 사용 중일 때 보낼 메시지
	public static String NOTUSING = "notusing"; // 아이디가 사용 중이지 않을 때 보낼 메시지
	
	public static String IMG_PATH = "D://img/"; // 기본 이미지 경로	
	public static String BACK_LEFT_PATH = "D://img/emotion/left/"; // 기본 이미지 경로	
	public static String BACK_RIGHT_PATH = "D://img/emotion/right/"; // 기본 이미지 경로	
	
	public static String COMPLETE = "이미지 합성 완료";
	
	// 배경사진을 결정하기 위한 이미지 배경 처리
	public static String ANGER = "anger";
	public static String CONTEMPT = "contempt";
	public static String DISGUST = "disgust";
	public static String FEAR = "fear";
	public static String HAPPINESS = "happiness";
	public static String NEUTRAL = "neutral";
	public static String SADNESS = "sadness";
	public static String SURPRISE = "surprise";
	
	
	// Array Indexing
	public static int FIRST = 0;
	public static int SECOND = 1;
	public static int THIRD = 2;
	public static int FORTH = 3;
	public static int FIFTH = 4;
	public static int SIXTH = 5;
	public static int SEVENTH = 6;
	
	// set Background
	public static String LEFT = "left";
	public static String RIGHT = "right";
	
	// image Resize & Merge 1
	public static int RESIZEWIDTH1 = 1300; 
	public static int RESIZEHEIGHT1 = 1838; 
	public static int MERGE_X1 = 200; 
	public static int MERGE_Y1 = 400;
	
	// image Resize & Merge 2
	public static int RESIZEWIDTH2 = 743; 
	public static int RESIZEHEIGHT2 = 1050; 
	public static int MERGE_X2 = 500; 
	public static int MERGE_Y2 = 300;
	
	// image Resize & Merge 3
	public static int RESIZEWIDTH3 = 743; 
	public static int RESIZEHEIGHT3 = 1050; 
	public static int MERGE_X3 = 500; 
	public static int MERGE_Y3 = 300;
	
	// image Resize & Merge 4
	public static int RESIZEWIDTH4 = 743; 
	public static int RESIZEHEIGHT4 = 1050; 
	public static int MERGE_X4 = 390; 
	public static int MERGE_Y4 = 300; 
}