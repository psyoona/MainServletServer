package server;

import java.io.File;

/*
 * ȸ�������� �Ϸ�Ǿ��� ��
 * ���̵� ������ �� ������ �����Ѵ�.
 */
public class MakeDirectory {
	public MakeDirectory(){}
	
	static void makeDirectory(String userID){
		 //������ ���ϰ�� ����
        String path = "D://img//" + userID;
        //���� ��ü ����
        File file = new File(path);
        //!ǥ�� �ٿ��־� ������ �������� �ʴ� ����� ������ �ɾ���
        if(!file.exists()){
            //���丮 ���� �޼���
            file.mkdirs();
            System.out.println(userID +" folder created directory successfully!");
        }
	}	
}
