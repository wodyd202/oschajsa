# 옷찾사(oschajsa)
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

![KakaoTalk_Photo_2021-12-11-16-52-36](https://user-images.githubusercontent.com/77535935/145669120-5160398f-ed33-41e1-a651-cf017fc3fc0d.jpeg)

![KakaoTalk_Photo_2021-12-11-16-52-43](https://user-images.githubusercontent.com/77535935/145669083-17f2239e-6f60-46b3-8807-4e153ad5305c.jpeg)
##

#### APIs

|  |  |  |
| ------ | ------ | ------ |
| /oauth/token | POST | 토큰 발급 |
| /api/v1/users | POST | 사용자 등록 |
| /api/v1/users | PATCH | 사용자 정보 변경 |
| /api/v1/users | DELETE | 회원 탈퇴 |
| /api/v1/users | GET | 사용자 정보 조회 |

|  |  |  |
| ------ | ------ | ------ |
| /api/v1/stores | POST | 업체 등록 |
| /api/v1/stores/{businessNumber}/logo | POST | 업체 로고 추가 및 변경 |
| /api/v1/stores/{businessNumber} | PATCH | 업체명 정보 변경 |
| /api/v1/stores/{businessNumber}/tag | PATCH | 업체 태그 추가 |
| /api/v1/stores/{businessNumber}/tag | DELETE | 업체 태그 제거 |
| /api/v1/stores/{businessNumber} | GET | 업체 상세 조회 |
| /api/v1/stores/difference-coordinate | GET | 좌표값의 x(km) 떨어진 업체 조회 |
| /api/v1/stores/address-info | GET | 도, 시, 동 기준으로 업체 조회 |

|  |  |  |
| ------ | ------ | ------ |
| /api/v1/stores/interest/{businessNumber} | POST | 관심 업체 등록 |
| /api/v1/stores/interest | GET | 관심 업체 조회 |
