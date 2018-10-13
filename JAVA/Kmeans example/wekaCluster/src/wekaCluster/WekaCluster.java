package wekaCluster;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;  // weka에서 k-means 를 쓰기 위해 넣은 삽입문

public class WekaCluster {
 
    public WekaCluster(String filepath) {
        try {
            Instances data = DataSource.read(filepath); // 데이터베이스에서 파일 읽어오는 거
            int clusters = calculateRuleOfThumb(data.numInstances());  // 적절한 클러스터링 갯수 계산하는 식
            System.out.println("Rule of Thumb Clusters = " + clusters);  // 클러스터 갯수 출력
            SimpleKMeans kMeans = new SimpleKMeans();  
            kMeans.setNumClusters(clusters);  // 클러스터 갯수 설정
            kMeans.setSeed(42);  // 초기 난수값 설정
            kMeans.buildClusterer(data);  // 데이터로 클러스터링
            showCentroids(kMeans, data);  // 중심점 표시
            showInstanceInCluster(kMeans, data); // 클러스터링 표시
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    public int calculateRuleOfThumb(int rows) {
        return (int)Math.sqrt(rows/2); // 적절한 클러스터 갯수 구하는 공식
    }
 
    public void showCentroids(SimpleKMeans kMeans, Instances data) {
        Instances centroids = kMeans.getClusterCentroids();
        for (int i = 0; i < centroids.numInstances(); i++) {
            System.out.println("Centroid: " + i + ": " + centroids.instance(i));  // 중심점 출력
        }
    }
 
    public void showInstanceInCluster(SimpleKMeans kMeans, Instances data) {
        try {
            for (int i = 0; i < data.numInstances(); i++) {
                System.out.println("Instance " + i + " is in cluster "
                        + kMeans.clusterInstance(data.instance(i)));  // 클러스터링 결과 출력
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        // .arff 파일 위치와 원하는 클러스터의 수를 전달한다.
        WekaCluster wc = new WekaCluster("C:\\Users\\deco2\\Downloads\\과제모음\\고급자바\\Kmeans\\weka-3-7-12\\data\\cpu.with.vendor.arff");  // 본인 k-means에 필요한 파일 위치 경로
    }
 
}