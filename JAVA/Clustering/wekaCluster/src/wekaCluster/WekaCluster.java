package wekaCluster;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;  // weka���� k-means �� ���� ���� ���� ���Թ�

import weka.core.converters.CSVLoader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;  // csv������ arff���Ϸ� ��ȯ�� �� ���� Ŭ������ ���� ���� ���� ���Թ�

import java.lang.Exception;  // ����ó���� �ϱ����� ���Թ�

/*  csv������ arff�������� ��ȯ�����ֱ� ���� ����ߴ� Ŭ����
class CSV2Arff {  
	void Convert(String args) throws Exception {
		// load CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(args));
		Instances data = loader.getDataSet();
		String fileName = args;

		// save ARFF
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.write(data.toString());
		writer.flush();
		writer.close();
	}
}
*/

class instancePrint{  // Ŭ�����͸� ��� ��� 	
	 void WekaCluster(String filepath) {  // ������ġ�� �Է¹޾Ƽ� �����Ű�� �κ�
	        try {
	            Instances data = DataSource.read(filepath); // �����ͺ��̽����� ���� �о���� ��
	      //    int clusters = calculateRuleOfThumb(data.numInstances());  // ������ Ŭ�����͸� ���� ����ϴ� ��
	            int clusters = 4;  // ���������߿� ������ 4���� ������� �Ǿ��־ �Ϻη� 4�� �����غ�
	            
	            
	            System.out.println("====================== �� 50�� ���� ����ȭ ��� =======================");
	            System.out.println("[���űݾ�(0~1,000,000), ���ż���(0~50)���� ����ȭ�ϰ� ����ȣ(1~50)�� ���� ����]");
	            
	            System.out.print("\n");
	   //       System.out.println("�� ������ ������ ����: " + clusters);  // ������ Ŭ������ ���� ���
	            System.out.println("�� ������ ����: " + clusters);  
	            System.out.print("\n");
	            	            
	            SimpleKMeans kMeans = new SimpleKMeans();  // kmeans ���̺귯�� ����
	            kMeans.setNumClusters(clusters);  // Ŭ������ ���� ����
	            kMeans.setSeed(17);  // �ʱ� ������ ����
	            kMeans.buildClusterer(data);  // �����ͷ� Ŭ�����͸�
	            
	            
	            System.out.println("�� �� �׷��� �߽����� ��ǥ");
	            showCentroids(kMeans, data);  // �߽��� ǥ��
	         
	            
	            System.out.println("�� �� ���� ����ȭ ���");
	            showInstanceInCluster(kMeans, data); // �� ���� ǥ��
	            
	            
	            System.out.println("�� �� ������  ���� �з����");
	            showEachClusterList(kMeans, data);  // �� �׷캰 ǥ��
	           
	            
	            }catch (Exception e) {  // ����ó��
	            	e.printStackTrace(); }
	    } 
	 
	    public int calculateRuleOfThumb(int rows) {  // ������ ����ȭ ������ ��ȯ�ϴ� �Լ�
	        return (int)Math.sqrt(rows/2); // ������ ����ȭ ���� ���ϴ� ����
	    } 
	    
	    public void showCentroids(SimpleKMeans kMeans, Instances data) {  // �� ������ �߽����� ����ϴ� �Լ�
	        Instances centroids = kMeans.getClusterCentroids();
	        for (int i = 0; i < centroids.numInstances(); i++) {
	            System.out.println("������ȣ " + (i+1) + ": " + centroids.instance(i));  // ������ȣ�� 0���� �Ҵ�Ǿ 1�� ������, �߽��� ���
	        }
	        System.out.print("\n");
	    }
	    
	    public void showInstanceInCluster(SimpleKMeans kMeans, Instances data) {  // �� ���� ����ȭ ����� ����ϴ� �Լ�
	        try {
	            for (int i = 0; i < data.numInstances(); i++) {       	
	                System.out.println("����ȣ " + (i+1) + "���� " + (kMeans.clusterInstance(data.instance(i))+1) + "�����Դϴ�. "); 
	                // �� ����ȣ��, ������ȣ�� 0���� �Ҵ�Ǿ, 1���� ǥ�õǰ� �ϱ����� �� 1�� ������ ���, ����ȭ ��� ��� ���       	            
	            }
	        }catch (Exception e) {  // ����ó��
	            e.printStackTrace(); }	
	       System.out.print("\n");
	    }
	    
	    public void showEachClusterList(SimpleKMeans kMeans, Instances data) {  // �� �׷캰 �� ����ȭ ����� ����ϴ� �Լ�
	    	 try {
	    //		 int clusters = calculateRuleOfThumb(data.numInstances()); 
	    		 int clusters = 4;  // Ư���� ����ȭ ������ 4���� ������
	    		 		 
	    		 for (int i = 0; i < clusters; i++) {    // ����ȭ ������ŭ �ݺ�
	    			 System.out.print((i+1) + "�׷� = ( ");    // �� �׷��ȣ�� ������ȣ�� ���߱� ���� 1�� ���ؼ� ���			 
	    			 int flag=0;  // �� �׷��� ���� ù��° ���� ��ȣ�� �˱� ���� ���� 
	    			 for (int j = 0; j < data.numInstances(); j++) { // ��ü ���� ����ŭ �ݺ�
	    				 if(i == kMeans.clusterInstance(data.instance(j)) && flag==0 ){ // �� �׷��� ù��°, �ѹ����� �����    					 
		    				 	System.out.print( (j+1) );  // ����ȣ�� 0���� �Ҵ�Ǿ��⶧���� 1���� ǥ�õǰ��ϵ����� 1�� ���ؼ� ���
		    				 	flag = j;  // �÷��׿� �� �׷��� ù ����ȣ�� ����
		    				 }  				    				 
	    				 if(i == kMeans.clusterInstance(data.instance(j)) && flag < j){  // �� �������ʹ� �÷��� ������ ���� ã�Ƽ� �����		 
	    				 	System.out.print(", " +(j+1) );  // ����ȣ�� ���
	    				 }  	    				
	    			}
	    			 		System.out.println(" ) ");
	    		}
	         }catch (Exception e) {  // ���� ó��
	             e.printStackTrace(); }      
	    }	
}


public class WekaCluster { 
    public static void main(String[] args) throws Exception {
    	    
    //	WekaCluster c1 = new WekaCluster("C:\\Users\\deco2\\Downloads\\client data\\real data\\���ڷ�.arff");  // ������ csv������ arff�������� �ٲ� �� ��� Ŭ����
    	
    	instancePrint c2 = new instancePrint();  //  ����ȭ ����� �������� Ŭ���� ����
    	c2.WekaCluster("C:\\Users\\deco2\\Downloads\\client data\\real data\\���ڷ�.arff");   // ���� k-means�� �ʿ��� ���� ��ġ ��� 
    }
}