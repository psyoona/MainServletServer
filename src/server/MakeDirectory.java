package server;

import java.io.File;

/*
 * ȸ�������� �Ϸ�Ǿ��� ��
 * ���̵� ������ �� ������ �����Ѵ�.
 */
public class MakeDirectory {
	public MakeDirectory(){}
	
	public static void makeDirectory(String userID){
		 //������ ���ϰ�� ����
        String path = "D://img//" + userID;
        //���� ��ü ����
        File file = new File(path);
        File fileAlbum = new File(path+"/album");
        File mergeAlbum = new File(path+"/merge");
        File finalAlbum = new File(path+"/final");
        //!ǥ�� �ٿ��־� ������ �������� �ʴ� ����� ������ �ɾ���
        if(!file.exists()){
            //���丮 ���� �޼���
            file.mkdirs();
            fileAlbum.mkdirs();
            mergeAlbum.mkdirs();
            finalAlbum.mkdirs();
            System.out.println(userID +" folder created directory successfully!");
        }
	}
	
}
