#  <a href="https://wodyd202.github.io/oschajsa/oschajsa.html">옷찾사(oschajsa)</a>
쇼핑몰이 존재하지 않는 의류 소상 공인들을 위한 업체 위치 정보 제공 서비스


#### Tech
- Spring Boot(2.4.6)
- Kafka(6)
- Redis(6)
- Mysql(5)
- Junit5
- DDD(cqrs), TDD

<a href="https://coveralls.io/github/wodyd202/oschajsa"><img src="https://coveralls.io/repos/github/wodyd202/oschajsa/badge.svg" alt="Coverage Status" /></a>

##

#### Install
* 실행전 Mysql(3306 port), Redis(6379 port), kafka(9092 port)가 구성되어있어야합니다.
* 위 프로젝트에 Mysql, Redis, Kafka의 <a href ="https://github.com/wodyd202/oschajsa/tree/master/docker">docker-compose.yml</a>가 포함되어 있습니다.

```sh
git clone https://github.com/wodyd202/oschajsa.git
mvn clean spring-boot:run
```
##
#### 아키텍처

![KakaoTalk_Photo_2021-12-11-16-52-36](https://user-images.githubusercontent.com/77535935/145974645-25cce457-a481-4dc1-b918-11fdd1c896e9.jpeg)

##
#### 데이터 조회 시나리오

![KakaoTalk_Photo_2021-12-11-16-52-43](https://user-images.githubusercontent.com/77535935/145975491-baf5597a-16d6-45ee-ac2a-db2ffc3b1708.jpeg)
