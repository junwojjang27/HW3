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
			// 파일 객체 생성
			File file = new File("C:\\Users\\junwo\\OneDrive\\바탕 화면\\CARRIERS.txt");
			// 입력 스트림 생성
			FileReader filereader = new FileReader(file);
			// 입력 버퍼 생성
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
		System.out.println("트리의 깊이 : " + rbt.height());
		System.out.println("루트 키 : " + rbt.getRootKey());
		System.out.println("전체 노드의 개수 : " + rbt.size());
		System.out.println("트리에서 가장 작은 키 : " + rbt.min());
		System.out.println("트리에서 가장 큰 키 : " + rbt.max());
		
		// 함수가 제기능을 잘하나 확인하기위해 inorder로 테스트를 해서 검증가능!
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
