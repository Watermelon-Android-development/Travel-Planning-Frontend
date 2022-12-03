package com.example.travelplan;

import java.util.ArrayList;
import java.util.List;

public class ShortestRouteAlgorithm {
    private static List<Integer> origin, result;
    private static double shortestDistance = Double.POSITIVE_INFINITY;
    private static double[][] allLocations, allDistances;
    public static List<Integer> getShortestRoute(double[] userPosition, List<TravelDatabaseHelper.Site> sites) {
        allLocations = new double[sites.size() + 1][2];
        allDistances = new double[allLocations.length][allLocations.length];
        allLocations[0] = userPosition.clone();
        origin = new ArrayList<>();
        for (int i = 0; i < sites.size(); i++) {
            allLocations[i+1] = new double[]{sites.get(i).getxCoor(), sites.get(i).getyCoor()};
            origin.add(sites.get(i).getId());
        }
        for (int i = 0; i < allLocations.length; i++) {
            for (int j = 0; j < allLocations.length; j++) {
                if (j == i || allDistances[i][j] != 0) continue;
                double distance = Math.sqrt(Math.pow(allLocations[i][0] - allLocations[j][0], 2)
                        + Math.pow(allLocations[i][1] - allLocations[j][1], 2));
                allDistances[i][j] = allDistances[j][i] = distance;
            }
        }
        boolean[] isVisited = new boolean[allLocations.length];
        dfs(isVisited, 0, 0, new ArrayList<Integer>());
        return result;
    }

    private static void dfs(boolean[] isVisited, int current, double distance, List<Integer> curResult) {
        isVisited[current] = true;
        curResult.add(origin.get(current));
        for (int i = 0; i < isVisited.length; i++) {
            if (!isVisited[i]) break;
            if (i == isVisited.length - 1) {
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    result = new ArrayList<Integer>(curResult);
                }
                return;
            }
        }

        for (int i = 0; i < isVisited.length; i++) {
            if (isVisited[i]) continue;
            double newDistance = distance + allDistances[current][i];
            if (newDistance >= shortestDistance) continue;
            dfs(isVisited, i, newDistance, curResult);
            curResult.remove(curResult.size() - 1);
            isVisited[i] = false;
        }
    }
}
