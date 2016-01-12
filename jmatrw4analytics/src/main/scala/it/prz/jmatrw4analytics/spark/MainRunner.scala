package it.prz.jmatrw4analytics.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import it.prz.jmatrw4spark.JMATFileInputFormat
import org.apache.hadoop.mapreduce.InputFormat

object MainRunner {
  
  def main(args: Array[String]): Unit = {
    
    
    val sparkconf = new SparkConf();
    sparkconf.setAppName("Simple Application")
             .setMaster("spark://10.86.71.158:7077")
             .set("spark.driver.extraClassPath", "E:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/*")
				     .set("spark.executor.extraClassPath", "E:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/*")
				     .set("fs.default.name", "file:///");
    
    val sc = new SparkContext(sparkconf);
    val hadoopConfig = sc.hadoopConfiguration;
    
    //var brdd = new NewHadoopRDD(;
    
    val filePath = "e:/tmp/vecRow03_x256.mat";
    
    //val matrdd = sc.newAPIHadoopFile[Long, Double, JMATFileInputFormat](filePath);
    val matrdd = sc.newAPIHadoopFile(filePath, classOf[InputFormat[Long, Double]], classOf[Long], classOf[Double], hadoopConfig)
    
    println("Start job...");
    val size = matrdd.count();
    println("Value count of rdd is " + size);
  }//EndMethod.
  
}//EndObject.