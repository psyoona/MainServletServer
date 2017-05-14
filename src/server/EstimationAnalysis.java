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
	static double maxValue; // 비교를 위한 변수
	
	public EstimationAnalysis(){
		// Constructor
		firstEmotion = Emotion.anger; secondEmotion = Emotion.anger; thirdEmotion = Emotion.anger;
		maxValue = 0;
		emotion = new Double[8];
	}
	
	public static void printEmotion(){
		System.out.println("이 사진의 Emotion은 "+ firstEmotion +" 입니다.");
	}
	
	public static String sendEmotion(){
		return String.valueOf(firstEmotion);
	}
	
	public static void decideEmotion(){
		for(int i=0; i<emotion.length; i++){
			if(emotion[i] >= maxValue){
				maxValue = emotion[i];
				firstEmotion = Emotion.values()[i];
			}
		}// End of for	
		
		printEmotion();
	}// End of decideEmotion function
	
	public static void analysis(String[] result){
		// 분석된 값을 변수에 입력시키는 메소드
		for(int j = 0; j < result.length; j++){			
			switch(result[j]){
			case "anger\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				emotion[0] = Double.valueOf(temp);
				
				break;
			case "contempt\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				emotion[1] = Double.valueOf(temp);
				
				break;
			case "disgust\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				emotion[2] = Double.valueOf(temp);
				
				break;
			case "fear\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				emotion[3] = Double.valueOf(temp);
				
				break;
			case "happiness\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				emotion[4] = Double.valueOf(temp);
				
				break;
			case "neutral\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				emotion[5] = Double.valueOf(temp);
				
				break;
			case "sadness\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				emotion[6] = Double.valueOf(temp);
				
				break;
			case "surprise\\":
				temp = result[j+1].substring(1, result[j+1].length()-3);
				emotion[7] = Double.valueOf(temp);
				
				break;				
			} // End Switch					
		}// End for
		decideEmotion();
	}// End analysis Function
}
