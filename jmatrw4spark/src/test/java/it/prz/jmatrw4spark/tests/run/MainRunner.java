package it.prz.jmatrw4spark.tests.run;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import it.prz.jmatrw4spark.JMATFileInputFormat;

public class MainRunner {

	public static void main(String[] args) {
		SparkConf sparkconf = new SparkConf()
				     .setAppName("Simple Application")
				     .setMaster("spark://10.86.71.158:7077")
				     .set("spark.driver.extraClassPath", "E:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/*")
				     .set("spark.executor.extraClassPath", "E:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/*")
				     .set("fs.default.name", "file:///")
				     ;
		JavaSparkContext sc = new JavaSparkContext(sparkconf);
		Configuration hadoopConfig = sc.hadoopConfiguration();
		hadoopConfig.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()  );
		hadoopConfig.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName()   );
		//sc.addJar("e:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/jmatrw-0.2.jar");
		//sc.addJar("e:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/jmatrw4spark-0.2.jar");
		
		/*JavaRDD<Double> matrdd2 = sc.parallelize(Arrays.asList(1.0, 3.0, 2.0));
		System.out.println("Start counting parallelize...");
		long values = matrdd2.count();
		System.out.println("Value count of parallelize is " + values);*/
		
		JavaPairRDD<Long, Double> matrdd = sc.newAPIHadoopFile("e:/tmp/vecRow03_x256.mat", JMATFileInputFormat.class, Long.class, Double.class, hadoopConfig);
		System.out.println("Start job...");
		long values = matrdd.count();
		System.out.println("Value count of hadoop is " + values);
		
		
		sc.stop();
		sc.close();
	}

}
