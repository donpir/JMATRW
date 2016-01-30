package it.prz.jmatrw4analytics.spark;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import it.prz.jmatrw4spark.JMATFileInputFormat;

public class JavaMainRunner {

	public static void main(String[] args) {
		SparkConf sparkconf = new SparkConf()
				     .setAppName("Simple Application")
				     .setMaster("spark://1.245.77.10:7077")
				     .set("spark.driver.extraClassPath", "E:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/*")
				     .set("spark.executor.extraClassPath", "E:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/*")
				     .set("fs.default.name", "file:///")
				     ;
		JavaSparkContext sc = new JavaSparkContext(sparkconf);
		Configuration hadoopConfig = sc.hadoopConfiguration();
		hadoopConfig.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()  );
		hadoopConfig.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName()   );
		
		String inputFilePath = "e:/tmp/vecRow03_x256.mat";
		
		JavaPairRDD<Long, Double> matrdd = sc.newAPIHadoopFile(inputFilePath, JMATFileInputFormat.class, Long.class, Double.class, hadoopConfig);
		System.out.println("Calculating Math Statistics");
		
		long values = matrdd.count();
		System.out.println("Value count of hadoop is " + values);
		
		
		sc.stop();
		sc.close();
	}

}
