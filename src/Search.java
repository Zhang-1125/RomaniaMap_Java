import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Search {
    private String start;
    private String goal;
    private HashMap<String, Node> graph = new HashMap<>();
    private HashMap<String, Boolean> explored = new HashMap<>();

    public Search(String start, String goal, String ss) throws IOException {
        this.start = start; // 设置起始点
        this.goal = goal; // 设置目标点

        try (BufferedReader br = new BufferedReader(new FileReader(ss))) {
            int stateNum = Integer.parseInt(br.readLine()); // 读取城市总数

            for (int i = 0; i < stateNum; i++) { // 遍历所有城市，读取并初始化城市信息
                String[] lineParts = br.readLine().split("\\s+");
                String name = lineParts[0];
                int h = Integer.parseInt(lineParts[1]);
                int neighborNum = Integer.parseInt(lineParts[2]);

                Node tempNode = new Node(name, h, neighborNum); // 创建一个新的城市节点
                for (int j = 0; j < neighborNum; j++) { // 读取该城市的邻居城市及其距离
                    String[] neighborInfo = br.readLine().split("\\s+");
                    String neighborName = neighborInfo[0];
                    int neighborDistance = Integer.parseInt(neighborInfo[1]);

                    tempNode.nextState.put(j, new Node.neighbor(neighborName, neighborDistance));
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
        System.out.println(tn.node.name);
    }


    public void AStar() {
        System.out.println("----------------------------A*----------------------------");

        Node startNode = graph.get(start);
        TreeNode root = new TreeNode();
        Queue<TreeNode> Q = new PriorityQueue<>(new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                return o1.value - o2.value;
            }
        });
        root.node = startNode;
        root.value = startNode.h;
        Q.add(root);

        while (!Q.isEmpty()) {
            TreeNode current = Q.poll();
            Node currentNode = current.node;
            System.out.println("Node name: " + currentNode.name + "\tValue: " + current.value);
            explored.put(currentNode.name, true);

            if (currentNode.name.equals(goal)) {
                System.out.println("Goal found!");
                Route(current, start);
                System.out.println("end");

                resetExplored();
                return;
            }

            for (int i = 0; i < currentNode.neighborNum; i++) {
                String nextNodeName = currentNode.nextState.get(i).Name;
                TreeNode currentChild = new TreeNode();
                currentChild.node = graph.get(nextNodeName);
                // currentChild.setNode(graph.get(nextNodeName));
                // 计算新节点的值
                // currentChild.setValue(current.getValue() - currentNode.getH() + currentNode.getNextState().get(i).getDistance() + graph.get(nextNodeName).getH());
                currentChild.value = current.value - currentNode.h + currentNode.nextState.get(i).Distance + graph.get(nextNodeName).h;
                currentChild.father = current;
                current.child.put(current.childNum, currentChild);
                current.childNum++;

                // 输出新节点信息
                System.out.println("\tChildNode: " + nextNodeName + " \tValue: " + currentChild.value + " \tState: " + explored.get(nextNodeName));
                // 如果邻居节点未被探索，则将其加入到优先队列中
                if (!explored.get(nextNodeName)) {
                    Q.add(currentChild);
                }
            }
            System.out.println("--------------------------------------------------------");
        }
        System.out.println("No goal found!");

        resetExplored();
    }
}