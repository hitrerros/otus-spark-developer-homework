package org.otus

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

trait SparkAppRunner extends App {
  lazy val spark: SparkSession = SparkSession
    .builder()
    .appName("spark-apps")
    .master("local[*]")
    .getOrCreate

  def saveAsParquet(dataFrame: DataFrame, tableName: String): Unit = {
    dataFrame.write.mode(SaveMode.Overwrite).format("parquet").saveAsTable(tableName)
  }

}
