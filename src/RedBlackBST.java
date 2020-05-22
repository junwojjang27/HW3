import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private Node root;

	private class Node {
		private Key key; 
		private Value val;
		private Node left, right;
		private boolean color;
		private int size;

		public Node(Key key, Value val, boolean color, int size) {
			this.key = key;
			this.val = val;
			this.color = color;
			this.size = size;
		}
	}

	public RedBlackBST() {
	}

	private boolean isRed(Node x) {
		if (x == null)
			return false;
		return x.color == RED;
	}

	public int size() {						//전체 키의 개수!!
		return size(root);
	}
	
	private int size(Node x) {
		if (x == null)
			return 0;
		return x.size;
	}
	
	public boolean isEmpty() {
		return root == null;
	}

	public Value getValue(Key key) {		//key값으로 value값 구하기!!
		return getValue(root, key);
	}

	private Value getValue(Node x, Key key) {
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if (cmp < 0)
				x = x.left;
			else if (cmp > 0)
				x = x.right;
			else
				return x.val;
		}
		return null;
	}

	public boolean contains(Key key) {
		return getValue(key) != null;
	}

	
	public void insert(Key key, Value val) {	//rbt에 삽입!!
		if (key == null)
			throw new IllegalArgumentException("키를 입력하지 않았습니다.");
		if (val == null) {
			delete(key);
			return;
		}

		root = insert(root, key, val);
		root.color = BLACK;
	}

	private Node insert(Node h, Key key, Value val) {
		if (h == null)
			return new Node(key, val, RED, 1);

		int cmp = key.compareTo(h.key);
		if (cmp < 0)
			h.left = insert(h.left, key, val);
		else if (cmp > 0)
			h.right = insert(h.right, key, val);
		else
			h.val = val;

		if (isRed(h.right) && !isRed(h.left))
			h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left))
			h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))
			flipColors(h);
		h.size = size(h.left) + size(h.right) + 1;

		return h;
	}


	public Key min() {							//트리에서 가장 작은 키 구하기.
		if (isEmpty())
			throw new NoSuchElementException("트리에 아무 노드가 없음");
		return min(root).key;
	}

	private Node min(Node x) {
		if (x.left == null)
			return x;
		else
			return min(x.left);
	}

	public Key max() {							//트리에서 가장 큰 키 구하기!!
		if (isEmpty())
			throw new NoSuchElementException("트리에 아무 노드가 없음");
		return max(root).key;
	}

	private Node max(Node x) {
		if (x.right == null)
			return x;
		else
			return max(x.right);
	}
	
	public void deleteMin() {					//트리에서 가장 작은 키 삭제!!
		if (isEmpty())
			throw new NoSuchElementException("삭제할 노드가 없음");

		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMin(root);
		if (!isEmpty())
			root.color = BLACK;
	}

	private Node deleteMin(Node h) {
		if (h.left == null)
			return null;

		if (!isRed(h.left) && !isRed(h.left.left))
			h = moveRedLeft(h);

		h.left = deleteMin(h.left);
		return balance(h);
	}

	public void deleteMax() {					//트리에서 가장 큰 키 삭제!!
		if (isEmpty())
			throw new NoSuchElementException("삭제할 노드가 없음");

		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMax(root);
		if (!isEmpty())
			root.color = BLACK;
	}

	private Node deleteMax(Node h) {
		if (isRed(h.left))
			h = rotateRight(h);

		if (h.right == null)
			return null;

		if (!isRed(h.right) && !isRed(h.right.left))
			h = moveRedRight(h);

		h.right = deleteMax(h.right);

		return balance(h);
	}

	public void delete(Key key) {				//트리에서 특정 키 삭제!!
		if (key == null)
			throw new IllegalArgumentException("해당 키가 이미 존재하지 않음");
		if (!contains(key))
			return;

		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = delete(root, key);
		if (!isEmpty())
			root.color = BLACK;
	}

	private Node delete(Node h, Key key) {

		if (key.compareTo(h.key) < 0) {
			if (!isRed(h.left) && !isRed(h.left.left))
				h = moveRedLeft(h);
			h.left = delete(h.left, key);
		} else {
			if (isRed(h.left))
				h = rotateRight(h);
			if (key.compareTo(h.key) == 0 && (h.right == null))
				return null;
			if (!isRed(h.right) && !isRed(h.right.left))
				h = moveRedRight(h);
			if (key.compareTo(h.key) == 0) {
				Node x = min(h.right);
				h.key = x.key;
				h.val = x.val;
				h.right = deleteMin(h.right);
			} else
				h.right = delete(h.right, key);
		}
		return balance(h);
	}
	
	private Node rotateLeft(Node h) {				//LR(왼쪽 회전)
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = x.left.color;
		x.left.color = RED;
		x.size = h.size;
		h.size = size(h.left) + size(h.right) + 1;
		return x;
	}

	private Node rotateRight(Node h) {				//RR(오른쪽 회전)
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = x.right.color;
		x.right.color = RED;
		x.size = h.size;
		h.size = size(h.left) + size(h.right) + 1;
		return x;
	}

	private void flipColors(Node h) {				//FC(색깔 반전)
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}

	private Node moveRedLeft(Node h) {

		flipColors(h);
		if (isRed(h.right.left)) {
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	private Node moveRedRight(Node h) {
		flipColors(h);
		if (isRed(h.left.left)) {
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	private Node balance(Node h) {

		if (isRed(h.right))
			h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left))
			h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))
			flipColors(h);

		h.size = size(h.left) + size(h.right) + 1;
		return h;
	}

	public int height() {								//트리의 깊이!!
		return height(root);
	}

	private int height(Node x) {
		if (x == null)
			return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	public void inorder() {								//트리에서 키를 오름차순으로 정렬!!
		System.out.print("In-order : ");
		inorder(root);
		System.out.println("");		
	}
	
	private void inorder(Node node) {
		if(node == null) return;
		inorder(node.left);
		System.out.print(node.key + " ");
		inorder(node.right);
	}
	
	public Key getRootKey() {
		return root.key;
	}
}