package it.prz.jmatrw4analytics.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import it.prz.jmatrw4spark.JMATFileInputFormat
import it.prz.jmatrw4spark.JMATInputFormat
import org.apache.hadoop.mapreduce.InputFormat
import org.apache.hadoop.mapreduce.{Job => NewHadoopJob}
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat => NewFileInputFormat}
import org.apache.spark.rdd.HadoopRDD
import it.prz.jmatrw4analytics.mathexp.MathExpEvaluator
import it.prz.jmatrw4analytics.mathexp.MathExpEvaluator

object ScalaMainRunner {
  
  def main(args: Array[String]): Unit = { 
    val sparkconf = new SparkConf();
    sparkconf.setAppName("Simple Application")
             .setMaster("spark://1.17.99.202:7077")
             .set("spark.driver.extraClassPath", "E:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/*")
				     .set("spark.executor.extraClassPath", "E:/installprogram/spark-1.5.2-bin-hadoop2.4/libthirdparty/*")
				     .set("fs.default.name", "file:///");
    
    val sc = new SparkContext(sparkconf);
    val hadoopConfig = sc.hadoopConfiguration;
    hadoopConfig.set("fs.hdfs.impl", classOf[org.apache.hadoop.hdfs.DistributedFileSystem].getName);
    hadoopConfig.set("fs.file.impl", classOf[org.apache.hadoop.fs.LocalFileSystem].getName);
    
    //Prapares the formula.
    val filterFormula : String = "Peak > mean - 4,7 * stddev";
    val expEvaluator = new MathExpEvaluator(filterFormula);
    val variables = scala.collection.mutable.Map[String, Double]("Peak" -> 100);

    val startTime = System.currentTimeMillis();
     
    //Create BaseRDD.
    val filePath = "e:/tmp/vecRow03_x256.mat";
    val matrdd = sc.newAPIHadoopFile(filePath, classOf[JMATInputFormat[Long, Double]], classOf[Long], classOf[Double], hadoopConfig)
   
    //Run statistics.
    val matrddvalues = matrdd.map(x => x._2.toDouble);
    val stats = matrddvalues.stats();
    variables.put("mean", stats.mean);
    variables.put("stddev", stats.stdev);
    
    //Counts the number of values.
    val numOfValues = matrdd.count();
    
    //Filter the data getting an RDD with filtered values.
    //matrddvalues.filter(
    
    val endTime = System.currentTimeMillis();
    val diffTime = endTime - startTime;
    
    println("Value count of rdd is " + numOfValues);
    println(diffTime + " milliseconds");
  }//EndMethod.
  
}//EndObject.