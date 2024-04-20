import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class Search {
    private String start;
    private String goal;
    private int stateNum;
    private HashMap<String, Node> graph = new HashMap<String, Node>();
    private HashMap<String, Boolean> explored = new HashMap<String, Boolean>();

    public Search(String start, String goal, String ss) throws IOException {
        this.start = start; // 设置起始点
        this.goal = goal; // 设置目标点

        try (BufferedReader br = new BufferedReader(new FileReader(ss))) {
            stateNum = Integer.parseInt(br.readLine()); // 读取城市总数

            for (int i = 0; i < stateNum; i++) { // 遍历所有城市，读取并初始化城市信息
                String[] lineParts = br.readLine().split("\\s+");
                String name = lineParts[0];
                int h = Integer.parseInt(lineParts[1]);
                int neighborNum = Integer.parseInt(lineParts[2]);

                Node tempNode = new Node(name, h, neighborNum); // 创建一个新的城市节点
                for (int j = 0; j < neighborNum; j++) { // 读取该城市的邻居城市及其距离
                    String[] neighborInfo = br.readLine().split("\\s+");
                    String neighborIndex = neighborInfo[0];
                    int distance = Integer.parseInt(neighborInfo[1]);

                    tempNode.nextState.put(distance, new Node.neighbor(neighborIndex, distance));
                }
                br.readLine();
                graph.put(name, tempNode); // 将城市节点添加到图中
                explored.put(tempNode.name, false); // 标记该城市为未探索
            }
        } catch (IOException e) {
            System.err.println("File open or read error!");
            throw e;
        }
    }

    void resetExplored(){

    }

    void Route(TreeNode tn, String startNodeName) {
        if (tn == null || tn.node == null) {
            return;
        }

        // 从当前节点向上回溯，打印路径
        Stack<String> pathStack = new Stack<>();
        pathStack.push(tn.node.name);

        TreeNode currentNode = tn;
        while (currentNode.father != null) {
            pathStack.push(currentNode.father.node.name);
            currentNode = currentNode.father;
        }

        System.out.println("Path from " + startNodeName + " to " + tn.node.name + ":");
        while (!pathStack.isEmpty()) {
            System.out.print(pathStack.pop() + " -> ");
        }
        System.out.println(tn.node.name);
    }

    public void AStar(){
        
    }
}
