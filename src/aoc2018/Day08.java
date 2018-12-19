package aoc2018;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day08 {
    private List<Integer> codes = new ArrayList<>();
    private List<Integer> codes2 = new ArrayList<>();
    private int result = 0;

    public static void main(String[] args) {
        Day08 day = new Day08();
        day.solve();
    }

    private void solve() {
        String dataPath = "src\\resources\\input-day8-1.txt";
        FileReader fr = new FileReader(dataPath);
        String[] data = fr.readFirstRow().split(" ");
        codes = Stream.of(data).map(Integer::parseInt).collect(Collectors.toList());
        codes2 = codes;

        // Task 1
        //parseData();
        //System.out.println("\nTotal: " + result);

        // Task 2
        Node<Integer> root = new Node<>(new ArrayList<>());
        parseData2(root);
        root = root.deleteRootNode();

        //printTree(root, " ");
        System.out.println(root.getSum() + task1(root, 0));
        System.out.println(task2(root, 0));
    }


    private void parseData() {
        int childs = codes.get(0);
        int metaCount = codes.get(1);
        System.out.print("\n" + childs +" " + metaCount + " ");
        codes.remove(0);
        codes.remove(0);
        while (childs > 0) {
            parseData();
            childs--;
        }
        for (int i = 0; i < metaCount; i++) {
            getMetadata(0);
        }
    }


    private void parseData2(Node<Integer> node) {
        int childs = codes2.get(0);
        int metaCount = codes2.get(1);
        Node<Integer> child = node.addChild(new Node<>(new ArrayList<>()));

        //System.out.print("\n" + childs +" " + metaCount + " ");
        codes2.remove(0);
        codes2.remove(0);
        while (childs > 0) {
            parseData2(child);
            childs--;
        }
        int data = 0;
        for (int i = 0; i < metaCount; i++) {
            child.setData(codes2.get(0));
            codes2.remove(0);
        }
    }


    private void getMetadata(int pos) {
        //System.out.print(codes.get(pos) + " ");
        result += codes.get(pos);
        codes.remove(pos);
    }

    private static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node + " " + node.getData() + " > " + node.getSum());
        node.getChildren().forEach(n ->  printTree(n, appender + appender));
    }

    private int task1(Node<Integer> node, int sum) {
        int tmp = sum;
        for (Node<Integer> n : node.getChildren()) {
            tmp += n.getSum();
            tmp = task1(n, tmp);
        }
        return tmp;
    }


    private int task2(Node<Integer> node, int sum) {
        int total = sum;
        int tmp = 0;
        //System.out.println("DEBUG: " + node + " " + node.getData() + " " + node.getChildren().size() + " " + node.getParent());
        List<Integer> links = node.getData();
        if (node.getChildren().isEmpty()) {
            tmp = node.getSum();
        } else {
            for (int link : links) {
                if (node.getChildren().size() > link-1 ) {
                    tmp = task2(node.getChildren().get(link-1), tmp);
                }
            }
        }
        total += tmp;
        //System.out.println(node.getData() + " = " + tmp + " > total:" + total);
        return total;
    }


    class Node<T> {
        private List<Integer> data = null;
        private List<Node<T>> children = new ArrayList<>();
        private Node<T> parent = null;

        public Node(List<Integer> data) {
            this.data = data;
        }

        public Node<T> addChild(Node<T> child) {
            child.setParent(this);
            this.children.add(child);
            return child;
        }

        public List<Node<T>> getChildren() {
            return children;
        }

        public List<Integer> getData() {
            return data;
        }

        public int getSum() {
            return data.stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        }

        public void setData(int data) {
            this.data.add(data);
        }

        private void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public Node<T> getParent() {
            return parent;
        }

        public Node<T> deleteRootNode() {
            if (parent != null) {
                throw new IllegalStateException("deleteRootNode not called on root");
            }
            Node<T> newParent = null;
            if (!getChildren().isEmpty()) {
                newParent = getChildren().get(0);
                newParent.setParent(null);
                getChildren().remove(0);
                for (Node<T> each : getChildren()) {
                    each.setParent(newParent);
                }
                newParent.getChildren().addAll(getChildren());
            }
            this.getChildren().clear();
            return newParent;
        }
    }
}

