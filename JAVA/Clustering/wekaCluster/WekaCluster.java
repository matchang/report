package wekaCluster;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;  // weka에서 k-means 를 쓰기 위해 넣은 삽입문

import weka.core.converters.CSVLoader;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;  // csv파일을 arff파일로 변환할 떄 쓰는 클래스를 쓰기 위해 넣은 삽입문

import java.lang.Exception;  // 예외처리를 하기위한 삽입문

/*  csv파일을 arff형식으로 변환시켜주기 위해 사용했던 클래스
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

class instancePrint{  // 클러스터링 결과 출력 	
	 void WekaCluster(String filepath) {  // 파일위치를 입력받아서 실행시키는 부분
	        try {
	            Instances data = DataSource.read(filepath); // 데이터베이스에서 파일 읽어오는 거
	      //    int clusters = calculateRuleOfThumb(data.numInstances());  // 적절한 클러스터링 갯수 계산하는 식
	            int clusters = 4;  // 과제내용중에 군집을 4개로 나누라고 되어있어서 일부로 4로 지정해봄
	            
	            
	            System.out.println("====================== 고객 50명에 대한 군집화 결과 =======================");
	            System.out.println("[구매금액(0~1,000,000), 구매수량(0~50)으로 군집화하고 고객번호(1~50)로 각각 구분]");
	            
	            System.out.print("\n");
	   //       System.out.println("● 적절한 군집의 갯수: " + clusters);  // 적절한 클러스터 갯수 출력
	            System.out.println("● 군집의 갯수: " + clusters);  
	            System.out.print("\n");
	            	            
	            SimpleKMeans kMeans = new SimpleKMeans();  // kmeans 라이브러리 선언
	            kMeans.setNumClusters(clusters);  // 클러스터 갯수 설정
	            kMeans.setSeed(17);  // 초기 난수값 설정
	            kMeans.buildClusterer(data);  // 데이터로 클러스터링
	            
	            
	            System.out.println("● 각 그룹의 중심점의 좌표");
	            showCentroids(kMeans, data);  // 중심점 표시
	         
	            
	            System.out.println("● 각 고객별 군집화 결과");
	            showInstanceInCluster(kMeans, data); // 각 고객별 표시
	            
	            
	            System.out.println("● 각 군집별  고객의 분류결과");
	            showEachClusterList(kMeans, data);  // 각 그룹별 표시
	           
	            
	            }catch (Exception e) {  // 예외처리
	            	e.printStackTrace(); }
	    } 
	 
	    public int calculateRuleOfThumb(int rows) {  // 적절한 군집화 갯수를 반환하는 함수
	        return (int)Math.sqrt(rows/2); // 적절한 군집화 갯수 구하는 공식
	    } 
	    
	    public void showCentroids(SimpleKMeans kMeans, Instances data) {  // 각 군집의 중심점을 출력하는 함수
	        Instances centroids = kMeans.getClusterCentroids();
	        for (int i = 0; i < centroids.numInstances(); i++) {
	            System.out.println("군집번호 " + (i+1) + ": " + centroids.instance(i));  // 군집번호가 0부터 할당되어서 1을 더해줌, 중심점 출력
	        }
	        System.out.print("\n");
	    }
	    
	    public void showInstanceInCluster(SimpleKMeans kMeans, Instances data) {  // 각 고객별 군집화 결과를 출력하는 함수
	        try {
	            for (int i = 0; i < data.numInstances(); i++) {       	
	                System.out.println("고객번호 " + (i+1) + "번은 " + (kMeans.clusterInstance(data.instance(i))+1) + "군집입니다. "); 
	                // 각 고객번호와, 군집번호가 0부터 할당되어서, 1부터 표시되게 하기위해 각 1을 더해준 모습, 군집화 결과 결과 출력       	            
	            }
	        }catch (Exception e) {  // 예외처리
	            e.printStackTrace(); }	
	       System.out.print("\n");
	    }
	    
	    public void showEachClusterList(SimpleKMeans kMeans, Instances data) {  // 각 그룹별 고객 군집화 결과를 출력하는 함수
	    	 try {
	    //		 int clusters = calculateRuleOfThumb(data.numInstances()); 
	    		 int clusters = 4;  // 특별히 군집화 갯수를 4개로 지정함
	    		 		 
	    		 for (int i = 0; i < clusters; i++) {    // 군집화 갯수만큼 반복
	    			 System.out.print((i+1) + "그룹 = ( ");    // 각 그룹번호와 군집번호를 맞추기 위해 1을 더해서 출력			 
	    			 int flag=0;  // 각 그룹의 가장 첫번째 고객의 번호만 알기 위해 만듬 
	    			 for (int j = 0; j < data.numInstances(); j++) { // 전체 고객의 수만큼 반복
	    				 if(i == kMeans.clusterInstance(data.instance(j)) && flag==0 ){ // 각 그룹의 첫번째, 한번씩만 실행됨    					 
		    				 	System.out.print( (j+1) );  // 고객번호도 0부터 할당되엇기때문에 1부터 표시되게하디위해 1을 더해서 출력
		    				 	flag = j;  // 플래그에 각 그룹의 첫 고객번호를 저장
		    				 }  				    				 
	    				 if(i == kMeans.clusterInstance(data.instance(j)) && flag < j){  // 그 다음부터는 플래그 이후의 고객만 찾아서 실행됨		 
	    				 	System.out.print(", " +(j+1) );  // 고객번호를 출력
	    				 }  	    				
	    			}
	    			 		System.out.println(" ) ");
	    		}
	         }catch (Exception e) {  // 예외 처리
	             e.printStackTrace(); }      
	    }	
}


public class WekaCluster { 
    public static void main(String[] args) throws Exception {
    	    
    //	WekaCluster c1 = new WekaCluster("C:\\Users\\deco2\\Downloads\\client data\\real data\\고객자료.arff");  // 본인이 csv파일을 arff형식으로 바꿀 떄 썼던 클래스
    	
    	instancePrint c2 = new instancePrint();  //  군집화 결과를 보기위해 클래스 선언
    	c2.WekaCluster("C:\\Users\\deco2\\Downloads\\client data\\real data\\고객자료.arff");   // 본인 k-means에 필요한 파일 위치 경로 
    }
}