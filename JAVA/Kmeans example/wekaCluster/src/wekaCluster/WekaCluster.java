package wekaCluster;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;  // weka���� k-means �� ���� ���� ���� ���Թ�

public class WekaCluster {
 
    public WekaCluster(String filepath) {
        try {
            Instances data = DataSource.read(filepath); // �����ͺ��̽����� ���� �о���� ��
            int clusters = calculateRuleOfThumb(data.numInstances());  // ������ Ŭ�����͸� ���� ����ϴ� ��
            System.out.println("Rule of Thumb Clusters = " + clusters);  // Ŭ������ ���� ���
            SimpleKMeans kMeans = new SimpleKMeans();  
            kMeans.setNumClusters(clusters);  // Ŭ������ ���� ����
            kMeans.setSeed(42);  // �ʱ� ������ ����
            kMeans.buildClusterer(data);  // �����ͷ� Ŭ�����͸�
            showCentroids(kMeans, data);  // �߽��� ǥ��
            showInstanceInCluster(kMeans, data); // Ŭ�����͸� ǥ��
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    public int calculateRuleOfThumb(int rows) {
        return (int)Math.sqrt(rows/2); // ������ Ŭ������ ���� ���ϴ� ����
    }
 
    public void showCentroids(SimpleKMeans kMeans, Instances data) {
        Instances centroids = kMeans.getClusterCentroids();
        for (int i = 0; i < centroids.numInstances(); i++) {
            System.out.println("Centroid: " + i + ": " + centroids.instance(i));  // �߽��� ���
        }
    }
 
    public void showInstanceInCluster(SimpleKMeans kMeans, Instances data) {
        try {
            for (int i = 0; i < data.numInstances(); i++) {
                System.out.println("Instance " + i + " is in cluster "
                        + kMeans.clusterInstance(data.instance(i)));  // Ŭ�����͸� ��� ���
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        // .arff ���� ��ġ�� ���ϴ� Ŭ�������� ���� �����Ѵ�.
        WekaCluster wc = new WekaCluster("C:\\Users\\deco2\\Downloads\\��������\\����ڹ�\\Kmeans\\weka-3-7-12\\data\\cpu.with.vendor.arff");  // ���� k-means�� �ʿ��� ���� ��ġ ���
    }
 
}