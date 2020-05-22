import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RbtTest {
	public static void main(String[] args) {
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();

		int i = 0;

		try {
			// ���� ��ü ����
			File file = new File("C:\\Users\\junwo\\OneDrive\\���� ȭ��\\CARRIERS.txt");
			// �Է� ��Ʈ�� ����
			FileReader filereader = new FileReader(file);
			// �Է� ���� ����
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {		
				keys.add(line.split("\t")[0]);
				values.add(line.split("\t")[1]);
				i++;
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			System.out.println(e);
		}

		RedBlackBST<String, String> rbt = new RedBlackBST<String, String>();

		for (int j = 0; j < keys.size(); j++) {
			rbt.insert(keys.get(j), values.get(j));
		}
		System.out.println("Ʈ���� ���� : " + rbt.height());
		System.out.println("��Ʈ Ű : " + rbt.getRootKey());
		System.out.println("��ü ����� ���� : " + rbt.size());
		System.out.println("Ʈ������ ���� ���� Ű : " + rbt.min());
		System.out.println("Ʈ������ ���� ū Ű : " + rbt.max());
		
		// �Լ��� ������� ���ϳ� Ȯ���ϱ����� inorder�� �׽�Ʈ�� �ؼ� ��������!
		rbt.inorder();

		for (int k = 0; k < 5; k++) {
			String search_key;
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter your search key : ");
			search_key = scan.nextLine();
			System.out.println(rbt.getValue(search_key));
		}
	}
}
