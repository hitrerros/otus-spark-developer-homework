package org.otus.hw11

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.otus.SparkAppRunner

object CountriesStatistics extends SparkAppRunner {
  /*
Функция, которая возвращает датафрейм со странами, которые граничат с 5 или более чем с 5 странами. В датафрейме должны быть столбцы:

Country - Международное название страны
NumBorders - Количество граничащих стран
BorderCountries - Список граничащих стран в формате строки через запятую.
   */
  def adjacentCountries(countriesDF: DataFrame): DataFrame = {

    val countryAndNeighbors = countriesDF
      .select(
        col("name.common").as("country_name"),
        explode(col("borders")).as("border_code")
      )
      .join(
        countriesDF.select(
          col("cca3").as("neighbor_code"),
          col("name.common").as("neighbor_name")
        ),
        col("border_code") === col("neighbor_code")
      )

    countryAndNeighbors
      .groupBy("country_name")
      .agg(
        count("*").as("border_count"),
        collect_list("neighbor_name").as("adjacent_countries")
      )
      .filter(col("border_count") >= 5)
  }

  /*
  Функция, которая возвращает рейтинг языков, на которых говорят в наибольшем количестве стран. В датафрейме должны быть столбцы:

  Language - название языка;
  NumCountries - количество стран, в которых говорят на языке;
  Countries - список международных названий стран, в которых говорят на языке, в формате `ArrayType
   */
  def languageRate(countriesDF: DataFrame): DataFrame = {

    countriesDF
      .select(
        col("name.common").as("country_name"),
        array(col("languages.*")).as("languages_array")
      )
      .select(
        col("country_name"),
        explode(col("languages_array")).as("language")
      )
      .filter(col("language").isNotNull)
      .groupBy("country_name")
      .agg(
        count("*").as("language_count"),
        collect_list("language").as("languages")
      )
      .orderBy(desc("language_count"))

  }

  val countriesFile = spark.read
    .format("json")
    .option("multiline", "true")
    .load("countries.json")

  saveAsParquet(adjacentCountries(countriesFile), "adjacent_countries")
  saveAsParquet(languageRate(countriesFile), "language_rate")
}
