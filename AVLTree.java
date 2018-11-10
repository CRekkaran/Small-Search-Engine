public class AVLTree { 
  
    NodeAVL root; 
    MyLinkedList<NodeAVL> sequence = new MyLinkedList<NodeAVL>();
  
    // A utility function to get the height of the tree 
    int height(NodeAVL N) { 
        if (N == null) 
            return 0; 
  
        return N.height; 
    } 
  
    // A utility function to get maximum of two integers 
    int max(int a, int b) { 
        return (a > b)?a:b; 
    } 
  
    // A utility function to right rotate subtree rooted with y 
    // See the diagram given above. 
    NodeAVL rightRotate(NodeAVL y) { 
        NodeAVL x = y.left; 
        NodeAVL T2 = x.right; 
  
        // Perform rotation 
        x.right = y; 
        y.left = T2; 
  
        // Update heights 
        y.height = max(height(y.left), height(y.right)) + 1; 
        x.height = max(height(x.left), height(x.right)) + 1; 
  
        // Return new root 
        return x; 
    } 
  
    // A utility function to left rotate subtree rooted with x 
    // See the diagram given above. 
    NodeAVL leftRotate(NodeAVL x) { 
        NodeAVL y = x.right; 
        NodeAVL T2 = y.left; 
  
        // Perform rotation 
        y.left = x; 
        x.right = T2; 
  
        //  Update heights 
        x.height = max(height(x.left), height(x.right)) + 1; 
        y.height = max(height(y.left), height(y.right)) + 1; 
  
        // Return new root 
        return y; 
    } 
  
    // Get Balance factor of node N 
    int getBalance(NodeAVL N) { 
        if (N == null) 
            return 0; 
  
        return height(N.left) - height(N.right); 
    } 
  
    NodeAVL insert(NodeAVL node, Position key) { 
  
        /* 1.  Perform the normal BST insertion */
        if (node == null) 
            return (new NodeAVL(key)); 
  
        if (key.getWordIndex() < node.key.getWordIndex()) 
            node.left = insert(node.left, key); 
        else if (key.getWordIndex() > node.key.getWordIndex()) 
            node.right = insert(node.right, key); 
        else // Duplicate keys not allowed 
            return node; 
  
        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left), 
                              height(node.right)); 
  
        /* 3. Get the balance factor of this ancestor 
              node to check whether this node became 
              unbalanced */
        int balance = getBalance(node); 
  
        // If this node becomes unbalanced, then there 
        // are 4 cases Left Left Case 
        if (balance > 1 && key.getWordIndex() < node.left.key.getWordIndex()) 
            return rightRotate(node); 
  
        // Right Right Case 
        if (balance < -1 && key.getWordIndex() > node.right.key.getWordIndex()) 
            return leftRotate(node); 
  
        // Left Right Case 
        if (balance > 1 && key.getWordIndex() > node.left.key.getWordIndex()) { 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
        } 
  
        // Right Left Case 
        if (balance < -1 && key.getWordIndex() < node.right.key.getWordIndex()) { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
        } 
  
        /* return the (unchanged) node pointer */
        return node; 
    } 

    



//////////////////////////////////////////////////////////////////////////
    void preOrder(NodeAVL node) {  // sequence should be empty before entrering this :/
        if (node != null) { 
            // System.out.print(node.key + " "); 
        	this.sequence.addElement(node);
            if(node.left!=null){
            	preOrder(node.left);
            }
            if(node.right!=null){
            	preOrder(node.right);
            }
        } 
    } 
    MyLinkedList<NodeAVL> giveSequence(AVLTree tree){ //can use this function only once otherwise same elements get added manytimes :/
    	preOrder(tree.root);
    	return tree.sequence;
    }
//////////////////////////////////////////////////////////////////////////

    // public static void main(String[] args) {
    // 	AVLTree tree = new AVLTree();
    // 	Position
    // 	tree.insert(root, 5);
    // 	tree.giveSequence();
    // }


}

class NodeAVL {
	int height;
	Position key; 
    NodeAVL left, right; 
  
    NodeAVL(Position d) { 
        this.key = d; 
        this.height = 1; 
    } 
}