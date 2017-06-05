package server;

import JDBC.JDBC;

public class EstimationAnalysis {
	// ���� JSON �����κ��� ������ �м��ϴ� Ŭ����
	// ���� ������
	public enum Emotion {anger, contempt, disgust, fear, happiness, neutral, sadness, surprise}
		
	static Double[] emotion;
	
	static Emotion selecctEmotion; // ���� ������ ���� ����
	
	static String temp; // ���� �ӽ������ص� ����
	static Double tempDouble;
	static double maxValue; // �񱳸� ���� ����
	
	static String imgPath; // �̹��� ��θ� �����صα� ���� �ӽ� ����
	
	@SuppressWarnings("static-access")
	public EstimationAnalysis(String imgPath){
		// Constructor
		selecctEmotion = Emotion.neutral;
		maxValue = -1;
		emotion = new Double[8];
		this.imgPath = imgPath;
	}
	
	public static void saveEmotion(){
		// ������ �ϳ��� ������ �����ϱ� ���� �޼ҵ�
		JDBC adminJDBC = new JDBC();
		adminJDBC.saveEmotion(imgPath, selecctEmotion, maxValue);
		System.out.println("�� ������ Emotion�� "+ selecctEmotion +" �Դϴ�.");
	}	
	
	public static void decideEmotion(){
		for(int i=0; i < emotion.length; i++){
			if(emotion[i] >= maxValue){
				maxValue = emotion[i];
				selecctEmotion = Emotion.values()[i];
			}			
		}// End of for	
		
		// ������ Emotion�� �����ͺ��̽��� �����Ѵ�.
		saveEmotion();
	}// End of decideEmotion function
	
	public static void analysis(String[] result, int faceCount){
		// �м��� ���� ������ �Է½�Ű�� �޼ҵ�		
		if(faceCount == 0){
			// �ʱ�ȭ
			for(int i=0; i<emotion.length; i++){
				emotion[i] = 0.0;
			}
		}
		for(int j = 0; j < result.length; j++){
			try{				
				switch(result[j]){
				case "anger\\":
					temp = result[j+1].substring(1, result[j+1].length()-2);
					tempDouble = Double.valueOf(temp);
					emotion[0] = Double.parseDouble(String.format("%.6f", tempDouble));
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
					if(faceCount == 1){
						System.out.println(faceCount + ": 1");
						temp = result[j+1].substring(1, result[j+1].length()-3);
						tempDouble = Double.valueOf(temp);
						emotion[7] = Double.parseDouble(String.format("%.6f", tempDouble));						
					}else{
						System.out.println(faceCount + ": NOT 1");
						temp = result[j+1].substring(1, result[j+1].length()-5);
						tempDouble = Double.valueOf(temp);
						emotion[7] = Double.parseDouble(String.format("%.6f", tempDouble));						
						faceCount--;
					}
					break;				
				} // End of Switch					
				
			}catch(NumberFormatException ne){
				
			}		
		}// End of for
		decideEmotion();		
	}// End of analysis Function
}
