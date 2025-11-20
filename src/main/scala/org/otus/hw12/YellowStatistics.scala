package org.otus.hw12

import org.otus.SparkAppRunner

import java.time.LocalDateTime

case class TripData(
    VendorID: Int,
    tpep_pickup_datetime: LocalDateTime,
    tpep_dropoff_datetime: LocalDateTime,
    passenger_count: Int,
    trip_distance: Double,
    RatecodeID: Int,
    store_and_fwd_flag: String,
    PULocationID: Int,
    DOLocationID: Int,
    payment_type: Int,
    fare_amount: Double,
    extra: Double,
    mta_tax: Double,
    tip_amount: Double,
    tolls_amount: Double,
    improvement_surcharge: Double,
    total_amount: Double
)

case class Zone(
    LocationID: String,
    Borough: String,
    Zone: String,
    service_zone: String
)

object YellowStatistics extends SparkAppRunner {
  import spark.implicits._
  val tripsDS = readAsParquet[TripData]("data/yellow_taxi_jan_25_2018")
  val zonesDS = readAsCSV[Zone]("data/taxi_zones.csv")

  tripsDS.createTempView("trips")
  zonesDS.createTempView("zones")

  /*
Построить витрину, содержащую агрегированную информацию о поездках из каждой зоны:
  количество поездок;
минимальное растоение поездок;
среднее растояние;
максимальное растояние;
среднеквадратическое отклонение.
   */

  val resultDF = spark
    .sql("""
           | SELECT
           |  zones.Zone AS zone,
           |  MAX(trip_distance) AS max_distance,
           |  AVG(trip_distance) AS avg_distance,
           |  MIN(trip_distance) AS min_distance,
           |  var_pop(trip_distance) AS square_deviation_distance
           |
           |   FROM trips AS trips JOIN zones AS zones
           |   ON trips.PULocationID = zones.LocationID
           |
           |  GROUP BY  zone
           |""".stripMargin)
    .toDF()

  saveAsParquet(resultDF, "result_distance")

}
