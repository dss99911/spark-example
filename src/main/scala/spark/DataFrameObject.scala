package spark

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.{concat_ws, element_at, explode, typedLit, udf}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import spark.Read.Person

class DataFrameObject {
  private val spark: SparkSession = SparkSessions.createSparkSession()

  import spark.implicits._

  private val df: DataFrame = Read.getParquetDataFrame()

  df
    // object를 바깥으로 빼기
    // https://stackoverflow.com/questions/32906613/flattening-rows-in-spark
    .withColumn("exp", explode($"array")) //해당 값들을 row들로 변환한다.
    .withColumn("exp", explode($"obj.data"))
    .withColumn("concats", concat_ws(",", $"array"))//array를 concat하여 스트링을 만듬
    .withColumn("dt", typedLit(Seq(1, 2, 3)))
    .withColumn("item", element_at($"array_column", 1))//get item of an index. index starts with 1. udf에서 Array로 리턴했을 때 Array 값이 됨.
    .withColumn("item", $"sequence._1")//Sequence값인 경우, sequence의 필드명을 넣으면됨 _1, _2

  /**
   * object를 컬럼에 넣기
   * flattening을 할 때, cache를 해주면, 속도 향상이 되는 경우가 있다. 원인은 잘 모름.
   */
  def convertObjectToColumns() = {
    val objectUDF: UserDefinedFunction = udf((num: Int) => {
      T("a", "B", num)
    })
    import spark.implicits._
    Seq(1, 2, 3)
      .toDF("value")
      .withColumn("test", objectUDF($"value"))
      .cache() // flattening을 할 때, cache를 해주면, 속도 향상이 되는 경우가 있다. 원인은 잘 모름.
      //nested column을 밖으로 빼기
      .selectExpr("test.*", "*")
      //안쓰는 컬럼 삭제
      .drop("value", "test")
      .show()
  }

  def convertDataFrameToObject() = {
    Read.getCsv()
      .as[Person] //convert to object
      .map(p => p.age)
      .show()
  }
}

case class T(a: String, b: String, num: Int)
