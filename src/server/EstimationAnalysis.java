package server;

public class EstimationAnalysis {
	// ���� JSON �����κ��� ������ �м��ϴ� Ŭ����
	// ���� ������
	public enum Emotion {anger, contempt, disgust, fear, happiness, neutral, sadness, surprise}
		
	static Double[] emotion;
	
	static Emotion firstEmotion; // ���� ������ ���� ����
	static Emotion secondEmotion; // �ι�°�� ������ ���� ����
	static Emotion thirdEmotion; // ����°�� ������ ���� ����	
	
	static String temp; // ���� �ӽ������ص� ����
	static Double tempDouble;
	static double maxValue; // �񱳸� ���� ����
	
	static String imgPath; // �̹��� ��θ� �����صα� ���� �ӽ� ����
	
	@SuppressWarnings("static-access")
	public EstimationAnalysis(String imgPath){
		// Constructor
		firstEmotion = Emotion.anger;
		maxValue = 0;
		emotion = new Double[8];
		this.imgPath = imgPath;
	}
	
	public static void saveEmotion(){
		// ������ �ϳ��� ������ �����ϱ� ���� �޼ҵ�
		JDBC adminJDBC = new JDBC();
//		System.out.println(maxValue);
		adminJDBC.saveEmotion(imgPath, firstEmotion, maxValue);
		System.out.println("�� ������ Emotion�� "+ firstEmotion +" �Դϴ�.");
	}	
	
	public static void decideEmotion(){
		for(int i=0; i<emotion.length; i++){
			if(emotion[i] >= maxValue){
				maxValue = emotion[i];
				firstEmotion = Emotion.values()[i];
			}
		}// End of for	
		
		// ������ Emotion�� �����ͺ��̽��� �����Ѵ�.
		saveEmotion();
	}// End of decideEmotion function
	
	public static void analysis(String[] result){
		// �м��� ���� ������ �Է½�Ű�� �޼ҵ�		
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
					// ���� �߻� ���ɼ�
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
