package server;

public class EstimationAnalysis {
	// 받은 JSON 값으로부터 감정을 분석하는 클래스
	// 감정 변수들
	public enum Emotion {anger, contempt, disgust, fear, happiness, neutral, sadness, surprise}
	
	static double anger; // 화냄
	static double contempt; // 경멸, 멸시, 무시
	static double disgust; // 혐오감, 역겨움
	static double fear; // 공포, 두려움
	static double happiness; // 행복
	static double neutral; // 중립적인
	static double sadness; // 슬픔
	static double surprise; // 놀람
	
	static Emotion firstEmotion; // 가장 비중이 높은 감정
	static Emotion secondEmotion; // 두번째로 비중이 높은 감정
	static Emotion thirdEmotion; // 세번째로 비중이 높은 감정	
	
	static String temp; // 값을 임시저장해둘 변수
	static double maxValue; // 비교를 위한 변수
	
	public EstimationAnalysis(){
		// Constructor
		firstEmotion = Emotion.anger; secondEmotion = Emotion.anger; thirdEmotion = Emotion.anger;
		maxValue = 0.000001;		
	}
	
	public static void printEmotion(){
		System.out.println("이 사진의 Emotion은 "+ firstEmotion +" 입니다.");
	}
	
	public static String sendEmotion(){
		return String.valueOf(firstEmotion);
	}
	
	public static void decideEmotion(){
		// 값에 따라 감정을 결정한다.
		if(anger >= maxValue){
			firstEmotion = Emotion.anger;
			maxValue = anger;
		}
		
		if(contempt >= maxValue){
			firstEmotion = Emotion.contempt;
			maxValue = contempt;
		}
		
		if(disgust >= maxValue){
			firstEmotion = Emotion.disgust;
			maxValue = disgust;
		}
		
		if(fear >= maxValue){
			firstEmotion = Emotion.fear;
			maxValue = fear;
		}
		
		if(happiness >= maxValue){
			firstEmotion = Emotion.happiness;
			maxValue = happiness;
		}
		
		if(neutral >= maxValue){
			firstEmotion = Emotion.neutral;
			maxValue = neutral;
		}
		
		if(sadness >= maxValue){
			firstEmotion = Emotion.sadness;
			maxValue = sadness;
		}
		
		if(surprise >= maxValue){
			firstEmotion = Emotion.surprise;
			maxValue = surprise;
		}
		
		printEmotion();
	}
	
	public static void analysis(String[] result){
		// 분석된 값을 변수에 입력시키는 메소드
		for(int j = 0; j < result.length; j++){			
			switch(result[j]){
			case "anger\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				anger = Double.valueOf(temp);
				
				break;
			case "contempt\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				contempt = Double.valueOf(temp);
				
				break;
			case "disgust\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				disgust = Double.valueOf(temp);
				
				break;
			case "fear\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				fear = Double.valueOf(temp);
				
				break;
			case "happiness\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				happiness = Double.valueOf(temp);
				
				break;
			case "neutral\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				neutral = Double.valueOf(temp);
				
				break;
			case "sadness\\":
				temp = result[j+1].substring(1, result[j+1].length()-2);
				sadness = Double.valueOf(temp);
				
				break;
			case "surprise\\":
				temp = result[j+1].substring(1, result[j+1].length()-3);
				surprise = Double.valueOf(temp);
				
				break;				
			} // End Switch					
		}// End for
		decideEmotion();
	}// End analysis Function
}
