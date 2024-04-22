import java.util.HashMap;

public class TreeNode {
    public int childNum;
    public double value;
    public Node node;
    public TreeNode father;
    public HashMap<Integer, TreeNode> child = new HashMap<Integer, TreeNode>();

}
