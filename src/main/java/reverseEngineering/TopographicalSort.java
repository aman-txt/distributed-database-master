package reverseEngineering;

import java.util.*;

public class TopographicalSort {
    public List<Integer> canFinish(int numCourses, int[][] prerequisites) {
        System.out.println(numCourses);
        System.out.println(Arrays.deepToString(prerequisites));
        List<Integer> orderList = new ArrayList<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.put(i, new ArrayList<Integer>());
            indegree.put(i, 0);
        }

        for (int i = 0; i < prerequisites.length; i++) {
            Integer parent = prerequisites[i][0];
            Integer child = prerequisites[i][1];

            graph.get(parent).add(child);
            indegree.put(child, indegree.get(child) + 1);
        }

        for (int i = 0; i < numCourses; i++) {
            if (indegree.get(i) == 0)
                queue.add(i);
        }

        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            orderList.add(node);

            for (Integer n : graph.get(node)) {
                indegree.put(n, indegree.get(n) - 1);
                if (indegree.get(n) == 0)
                    queue.add(n);
            }
        }
        return orderList;
    }
}