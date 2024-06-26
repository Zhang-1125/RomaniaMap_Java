import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Search {
    private final String start;
    private final String goal;
    private final HashMap<String, Node> graph = new HashMap<>();
    private final HashMap<String, Boolean> explored = new HashMap<>();

    public Search(String start, String goal, String ss) throws IOException {
        this.start = start; // 设置起始点
        this.goal = goal; // 设置目标点

        try (BufferedReader br = new BufferedReader(new FileReader(ss))) {
            int stateNum = Integer.parseInt(br.readLine()); // 读取城市总数

            for (int i = 0; i < stateNum; i++) { // 遍历所有城市，读取并初始化城市信息
                String[] lineParts = br.readLine().split("\\s+");
                String name = lineParts[0];
                // int h = Integer.parseInt(lineParts[1]);
                int neighborNum = Integer.parseInt(lineParts[1]);
                double latitude = Double.parseDouble(lineParts[2]);
                double longitude = Double.parseDouble(lineParts[3]);

                Node tempNode = new Node(name, neighborNum, latitude, longitude); // 创建一个新的城市节点
                for (int j = 0; j < neighborNum; j++) {                           // 读取该城市的邻居城市及其距离
                    String[] neighborInfo = br.readLine().split("\\s+");
                    String neighborName = neighborInfo[0];

                    tempNode.nextState.put(j, neighborName);
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

    void resetExplored() {
        for (HashMap.Entry<String, Boolean> entry : explored.entrySet()) {
            entry.setValue(false);
        }
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
    }

    public void AStar() {
        System.out.println("----------------------------A*----------------------------");

        // 初始化起始节点和优先队列
        Node startNode = graph.get(start);
        TreeNode root = new TreeNode();
        Queue<TreeNode> Q = new PriorityQueue<>((o1, o2) -> (int) (o1.value - o2.value));
        root.node = startNode;
        root.value = startNode.h;
        Q.add(root);

        // 开始A*搜索
        while (!Q.isEmpty()) {
            TreeNode current = Q.poll();
            Node currentNode = current.node;
            System.out.println("Node name: " + currentNode.name + "\tValue: " + current.value);
            explored.put(currentNode.name, true);

            // 如果找到目标节点，则输出路径并结束搜索
            if (currentNode.name.equals(goal)) {
                System.out.println("Goal found!");
                Route(current, start);
                System.out.println("end");

                resetExplored();
                return;
            }

            // 遍历当前节点的所有邻居节点
            for (int i = 0; i < currentNode.neighborNum; i++) {
                String nextNodeName = currentNode.nextState.get(i);
                TreeNode currentChild = new TreeNode();
                currentChild.node = graph.get(nextNodeName);

                // 计算邻居节点的值
                currentChild.value = current.value
                        - currentNode.getH(graph.get(goal))
                        + graph.get(currentNode.nextState.get(i)).getDistanceBetween(current.node)
                        + graph.get(nextNodeName).getH(graph.get(goal));
                currentChild.father = current;
                current.child.put(current.childNum, currentChild);
                current.childNum++;

                // 输出邻居节点信息，并根据是否已探索，将其加入优先队列
                System.out.println("\tChildNode: " + nextNodeName + " \tf_cost: " + currentChild.value + " \tState: " + explored.get(nextNodeName));

                if (!explored.get(nextNodeName)) {
                    Q.add(currentChild);
                }
            }
            printPriorityQueue(Q);
            System.out.println("--------------------------------------------------------");
        }
        // 如果没有找到目标节点，输出提示信息
        System.out.println("No goal found!");

        resetExplored();
    }

    private void printPriorityQueue(Queue<TreeNode> Q) {
        System.out.println("Current Priority Queue:");
        for (TreeNode node : Q) {
            System.out.println("\tNode name: " + node.node.name + "\tValue: " + node.value);
        }
        System.out.println();
    }

    public Node getPosition(double lat, double lng){
        Node nowPositon = new Node("nowPosition", lat, lng);

        double shortestDistance = Double.MAX_VALUE;
        Node nearestNode = null;

        for (HashMap.Entry<String, Node> entry : graph.entrySet()) {
            Node currentNode = entry.getValue();
            double distance = nowPositon.getDistanceBetween(currentNode);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestNode = currentNode;
            }
        }
        return nearestNode;
    }

}