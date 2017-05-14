package server;

public class EstimationAnalysis {
	// ���� JSON �����κ��� ������ �м��ϴ� Ŭ����
	// ���� ������
	public enum Emotion {anger, contempt, disgust, fear, happiness, neutral, sadness, surprise}
	
	static double anger; // ȭ��
	static double contempt; // ���, ���, ����
	static double disgust; // ������, ���ܿ�
	static double fear; // ����, �η���
	static double happiness; // �ູ
	static double neutral; // �߸�����
	static double sadness; // ����
	static double surprise; // ���
	
	static Emotion firstEmotion; // ���� ������ ���� ����
	static Emotion secondEmotion; // �ι�°�� ������ ���� ����
	static Emotion thirdEmotion; // ����°�� ������ ���� ����	
	
	static String temp; // ���� �ӽ������ص� ����
	static double maxValue; // �񱳸� ���� ����
	
	public EstimationAnalysis(){
		// Constructor
		firstEmotion = Emotion.anger; secondEmotion = Emotion.anger; thirdEmotion = Emotion.anger;
		maxValue = 0.000001;		
	}
	
	public static void printEmotion(){
		System.out.println("�� ������ Emotion�� "+ firstEmotion +" �Դϴ�.");
	}
	
	public static String sendEmotion(){
		return String.valueOf(firstEmotion);
	}
	
	public static void decideEmotion(){
		// ���� ���� ������ �����Ѵ�.
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
		// �м��� ���� ������ �Է½�Ű�� �޼ҵ�
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
