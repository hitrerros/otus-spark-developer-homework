package org.otus

import org.apache.spark.sql.{DataFrame, Dataset, Encoder, SaveMode, SparkSession}

trait SparkAppRunner extends App {
  lazy val spark: SparkSession = SparkSession
    .builder()
    .appName("spark-apps")
    .master("local[*]")
    .getOrCreate

  def saveAsParquet(dataFrame: DataFrame,tableName : String) : Unit = {
    dataFrame.write.mode(SaveMode.Overwrite).format("parquet").saveAsTable(tableName)
  }

  def readAsParquet[T : Encoder](fileName: String): Dataset[T] = {
    spark.read.parquet(fileName).as[T]
  }

  def readAsCSV[T : Encoder](fileName: String): Dataset[T] = {
    spark.read
      .option("header", value = true)
      .csv(fileName).as[T]
  }

}
