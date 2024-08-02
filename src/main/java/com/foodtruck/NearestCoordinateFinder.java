package com.foodtruck;


import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * 要查找给定经纬度集合中距离最近的一个
 * 1.使用Haversine公式计算两点之间的距离。
 * 2.遍历所有经纬度，找出距离给定点最近的点。
 */
public class NearestCoordinateFinder {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = NearestCoordinateFinder.class.getClassLoader();
        URL resource = classLoader.getResource("");
        FileReader reader = new FileReader(resource.getPath() + "\\Mobile_Food_Facility_Permit.csv");
        List<FoodTruck> foodTrucks = new CsvToBeanBuilder(reader)
                .withType(FoodTruck.class).build().parse();
        FoodTruck target = new FoodTruck();
        // 根据经纬度找出距离当前位置最近的FoodTruck
        target.setLatitude(37.7600869319869);
        target.setLongitude(-122.418806481101);
        Optional<FoodTruck> nearest = findNearestCoordinate(foodTrucks, target);
        nearest.ifPresent(foodTruck -> System.out.println(JsonUtil.toJson(foodTruck)));
    }

    /**
     * 遍历所有经纬度，找出距离给定点最近的点
     *
     * @param foodTrucks
     * @param target
     * @return
     */
    public static Optional<FoodTruck> findNearestCoordinate(List<FoodTruck> foodTrucks, FoodTruck target) {
        Optional<FoodTruck> nearest = Optional.empty();
        double minDistance = Double.MAX_VALUE;
        for (FoodTruck foodTruck : foodTrucks) {
            double distance = calculateDistance(target, foodTruck);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = Optional.of(foodTruck);
            }
        }
        return nearest;
    }

    /**
     * 使用Haversine公式计算两点之间的距离。
     *
     * @param c1
     * @param c2
     * @return
     */
    public static double calculateDistance(FoodTruck c1, FoodTruck c2) {
        double dLat = Math.toRadians(c2.getLatitude() - c1.getLatitude());
        double dLon = Math.toRadians(c2.getLongitude() - c1.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(c1.getLatitude())) * Math.cos(Math.toRadians(c2.getLatitude())) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c; // 6371 is the radius of Earth in kilometers
    }


}
