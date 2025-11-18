package org.otus.hw10

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import org.apache.spark.sql.{Row, SparkSession}

object TaxiTripsCalculator extends App {
  val spark = SparkSession.builder
    .appName("WordCount")
    .master("local[*]") // local mode with all cores
    .getOrCreate()
  val sparkContext = spark.sparkContext

  val roundTimeStamp: (Row, String) => LocalDateTime =
    (row: Row, name: String) => {
      val ts = row.getAs[String](name)
      val dt = LocalDateTime.parse(
        ts,
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
      )
      dt.truncatedTo(ChronoUnit.HOURS)
    }

  val zones = spark.read
    .option("header", value = true)
    .csv("taxi_zone_lookup.csv")
    .rdd
    .groupBy(_.getAs[String]("LocationID"))

  val trips = spark.read
    .option("header", value = true)
    .csv("tripdata.csv")
    .rdd
    .map { row =>
      Row(
        row.getAs[String]("PULocationID"),
        roundTimeStamp.apply(row, "tpep_pickup_datetime")
      )
    }
    .groupBy(_.getAs[String](0))
    .map(row => (row._1, (row._2.head.getAs[LocalDateTime](1), row._2.size)))

  val combined =
    trips
      .join(zones)
      .map { case (_, ((time, count), borough)) =>
        (borough.head.getAs[String](1), time, count)
      }
      .groupBy { case (borough, time, _) =>
        (borough, time)
      }
      .map { case ((borough, time), lines) =>
        s"${borough},${time},${lines.map(_._3).sum}"
      }
      .mapPartitionsWithIndex {
        case (0, iter) =>
          Iterator(
            "borough_name,date_time,trips_number"
          ) ++ iter
        case (_, iter) => iter
      }

  combined.saveAsTextFile("out.csv")
}
