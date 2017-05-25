package server;

public class EstimationAnalysis {
	// 받은 JSON 값으로부터 감정을 분석하는 클래스
	// 감정 변수들
	public enum Emotion {anger, contempt, disgust, fear, happiness, neutral, sadness, surprise}
		
	static Double[] emotion;
	
	static Emotion firstEmotion; // 가장 비중이 높은 감정
	static Emotion secondEmotion; // 두번째로 비중이 높은 감정
	static Emotion thirdEmotion; // 세번째로 비중이 높은 감정	
	
	static String temp; // 값을 임시저장해둘 변수
	static Double tempDouble;
	static double maxValue; // 비교를 위한 변수
	
	static String imgPath; // 이미지 경로를 저장해두기 위한 임시 변수
	
	@SuppressWarnings("static-access")
	public EstimationAnalysis(String imgPath){
		// Constructor
		firstEmotion = Emotion.anger;
		maxValue = 0;
		emotion = new Double[8];
		this.imgPath = imgPath;
	}
	
	public static void saveEmotion(){
		// 결정된 하나의 감정을 저장하기 위한 메소드
		JDBC adminJDBC = new JDBC();
//		System.out.println(maxValue);
		adminJDBC.saveEmotion(imgPath, firstEmotion, maxValue);
		System.out.println("이 사진의 Emotion은 "+ firstEmotion +" 입니다.");
	}	
	
	public static void decideEmotion(){
		for(int i=0; i<emotion.length; i++){
			if(emotion[i] >= maxValue){
				maxValue = emotion[i];
				firstEmotion = Emotion.values()[i];
			}
		}// End of for	
		
		// 결정된 Emotion을 데이터베이스에 저장한다.
		saveEmotion();
	}// End of decideEmotion function
	
	public static void analysis(String[] result){
		// 분석된 값을 변수에 입력시키는 메소드		
		for(int j = 0; j < result.length; j++){
			try{
				switch(result[j]){
				case "anger\\":
					temp = result[j+1].substring(1, result[j+1].length()-2);
					tempDouble = Double.valueOf(temp);
					emotion[0] = Double.parseDouble(String.format("%.6f", tempDouble));
					//System.out.println(emotion[0]);
					
					break;
				case "contempt\\":
					temp = result[j+1].substring(1, result[j+1].length()-2);
					tempDouble = Double.valueOf(temp);
					emotion[1] = Double.parseDouble(String.format("%.6f", tempDouble));
					break;
				case "disgust\\":
					temp = result[j+1].substring(1, result[j+1].length()-2);
					tempDouble = Double.valueOf(temp);
					emotion[2] = Double.parseDouble(String.format("%.6f", tempDouble));
					break;
				case "fear\\":
					temp = result[j+1].substring(1, result[j+1].length()-2);
					tempDouble = Double.valueOf(temp);
					emotion[3] = Double.parseDouble(String.format("%.6f", tempDouble));
					break;
				case "happiness\\":
					temp = result[j+1].substring(1, result[j+1].length()-2);
					tempDouble = Double.valueOf(temp);
					emotion[4] = Double.parseDouble(String.format("%.6f", tempDouble));
					break;
				case "neutral\\":
					temp = result[j+1].substring(1, result[j+1].length()-2);
					tempDouble = Double.valueOf(temp);
					emotion[5] = Double.parseDouble(String.format("%.6f", tempDouble));
					break;
				case "sadness\\":
					temp = result[j+1].substring(1, result[j+1].length()-2);
					tempDouble = Double.valueOf(temp);
					emotion[6] = Double.parseDouble(String.format("%.6f", tempDouble));
					break;
				case "surprise\\":
					// 문제 발생 가능성
					temp = result[j+1].substring(1, result[j+1].length()-3);
					tempDouble = Double.valueOf(temp);
					emotion[7] = Double.parseDouble(String.format("%.6f", tempDouble));
					break;				
				} // End of Switch					
				
			}catch(NumberFormatException ne){
				
			}		
		}// End of for
		decideEmotion();		
	}// End of analysis Function
}
