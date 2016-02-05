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
import it.prz.jmatrw4analytics.mathexp.model.DTBool
import sun.swing.FilePane
import org.apache.hadoop.conf.Configuration

object ScalaMainRunner {
  
  def main(args: Array[String]): Unit = { 
    val pathLibrary = "D:/Programmi/spark-1.5.2-bin-hadoop2.4/libthirdparty/*";
    val sparkconf = new SparkConf();
    sparkconf.setAppName("Simple Application")
             .setMaster("spark://172.16.15.34:7077")
             .set("spark.driver.extraClassPath", pathLibrary)
				     .set("spark.executor.extraClassPath", pathLibrary)
				     .set("fs.default.name", "file:///");
    
    val sc = new SparkContext(sparkconf);
    val hadoopConfig = sc.hadoopConfiguration;
    hadoopConfig.set("fs.hdfs.impl", classOf[org.apache.hadoop.hdfs.DistributedFileSystem].getName);
    hadoopConfig.set("fs.file.impl", classOf[org.apache.hadoop.fs.LocalFileSystem].getName);
    
    val _args = "vecRow128K.mat";
    var count = 1;
    while (count > 0) {
      runtest(_args, sc, hadoopConfig);
      count = count - 1;
    }
  }//EndMethod.
  
  def runtest(args: String, sc : SparkContext, hadoopConfig : Configuration) {
  
    
    //Prapares the formula.
    val filterFormula : String = "Peak > mean + 1 * stddev";
    val expEvaluator = new MathExpEvaluator(filterFormula);
    val variables = scala.collection.mutable.Map[String, Double]("Peak" -> 100);

    //Get the file name from arguments.
    val filePath = "d:/tmp/" + args; //vecRow12_x65568.mat
    println("Running file " + filePath);
    
    val startTime = System.currentTimeMillis();
     
    //Create BaseRDD.
    val matrdd = sc.newAPIHadoopFile(filePath, classOf[JMATInputFormat[Long, Double]], classOf[Long], classOf[Double], hadoopConfig)
   
    //Run statistics.
    val matrddvalues = matrdd.map(x => x._2.toDouble);
    val stats = matrddvalues.stats();
    variables.put("mean", stats.mean);
    variables.put("stddev", stats.stdev);
    
    //Counts the number of values.
    val numOfValues = matrdd.count();
    
    //Filter the data getting an RDD with filtered values.
    def filterNumber( vars : scala.collection.mutable.Map[String, Double], number : Double ) : Boolean = {
      vars.put("Peak", number);
      val result = expEvaluator.evaluate(vars);
      result match {
        case result : DTBool => return result.value;
        case _ => throw new IllegalArgumentException
      }
    }
    
    val filteredRDD = matrddvalues.filter( x => filterNumber(variables, x) );
    val numOfValuesFiltered = filteredRDD.count();
    
    val endTime = System.currentTimeMillis();
    val diffTime = endTime - startTime;
    
    println("Mean: " + stats.mean + " Stddev: " + stats.stdev + " formula:" + (stats.mean + 1 * stats.stdev));
    println("Num of items in base rdd is " + numOfValues + " The num of filtered items are " + numOfValuesFiltered);
    println(diffTime + " milliseconds");
  }
  
  
}//EndObject.