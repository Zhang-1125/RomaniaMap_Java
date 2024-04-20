import java.util.HashMap;

public class TreeNode {
    public int childNum;
    public int value;
    public int depth;
    public Node node;
    public TreeNode father;
    public HashMap<Integer, TreeNode> child = new HashMap<Integer, TreeNode>();

}
