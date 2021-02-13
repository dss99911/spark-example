# Monitoring
- [x] [Monitoring](https://spark.apache.org/docs/latest/monitoring.html)
    - [x] [Web UI](https://spark.apache.org/docs/latest/web-ui.html)
    - 4040 port
        - driver-node:4040
        - 앱이 실행될 동안만 유효한 것 같고, 설정에 따라, 마지막 실행된 앱에 대한 정보만 확인할 수 있는 듯하다.
    - Viewing After the Fact
        - 히스토리들을 볼 수 있음
        - ```./sbin/start-history-server.sh ``` 를 통해서 실행
        - `http://<server-url>:18080`
        - incompleted application history는 별도의 링크가 아래에 있음
        - 도중에 kill되면, 히스토리에서는 볼 수 없는듯. (Hadoop monitoring에서 킬된 정보는 뜨는데, 자세한 정보가 없음..)

    - Hadoop Monitoring
        - `http://<server-url>:8088`
        - Spark monitoring과 비슷한 정보를 줌. Hadoop cluster정보에 대한 모니터링 정보도 추가로 보여주는듯..

# Performamce
- [ ] [Tuning Guide](https://spark.apache.org/docs/latest/tuning.html): best practices to optimize performance and memory use
   - [Kryo serialization](https://spark.apache.org/docs/latest/tuning.html#data-serialization)
    - prefer arrays of objects, and primitive types, instead of the standard Java or Scala collection classes.
        - The [fastutil](http://fastutil.di.unimi.it/) library provides convenient collection classes for primitive types
- [ ] [Performance tuning](https://spark.apache.org/docs/latest/sql-performance-tuning.html#join-strategy-hints-for-sql-queries)
- [x] [external article. Performance Tutorial](https://blog.scottlogic.com/2018/03/22/apache-spark-performance.html#:~:text=A%20shuffle%20occurs%20when%20data,likely%20on%20a%20different%20executor.)    
