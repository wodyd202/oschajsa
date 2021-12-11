# 옷찾사(oschajsa)
##### 쇼핑몰이 존재하지 않는 의류 소상 공인들을 위한 업체 위치 정보 제공 서비스
###
##

#### Tech
- Spring Boot
- Redis
- Mysql
- Maven
- Junit

{<img src="https://coveralls.io/repos/github/wodyd202/oschajsa/badge.svg" alt="Coverage Status" />}[https://coveralls.io/github/wodyd202/oschajsa]

##

#### Install
* 실행전 Mysql 3306 port가 구성되어있어야합니다.
* Redis는 embedded redis를 사용하고 있으나, 프로젝트에 포함된 docker-compose를 사용해 별개로 구성해도 무방합니다.

```sh
git clone https://github.com/wodyd202/oschajsa.git
mvn clean spring-boot:run
```
##

#### 아키텍처

해당 이미지는 업체 기준의 아키텍처를 그린 이미지입니다. 서비스가 CQRS로 분리되어있기 때문에 조회를 제외한 사용자의 요청은 Command Layer에서 처리하게 됩니다.
Command Layer에서 생성, 수정, 삭제 프로세스를 거친 후 도메인 이벤트가 publish되어 query layer 및 다른 도메인이 해당 이벤트를 consume하고 그에 맞게 처리하게 됩니다.

Command Layer에서는 Mysql DB로 데이터를 처리하였고, 이벤트를 받아 처리하는  Query layer에서는 Redis를 사용해 데이터를 처리하였습니다.

Command Layer에서 데이터를 지속적으로 처리시 Query Layer와 연동되어있는 Redis에 데이터가 계속 쌓이게되는데 데이터가 쌓이면 쌓일 수록 조회되지않는 데이터가 누적이 될것입니다.
이로인해 메모리 낭비가 발생하게되는데 이를 보완하기위해 TTL(7일)을 적용하였고, redis에서 조회했으나 데이터가 존재하지 않을 시 해당 데이터를 데이터베이스에서 가져와 다시 동기화(Lazy Loading
)하는 로직을 구현했습니다.

##

#### APIs

|  |  |  |
| ------ | ------ | ------ |
| /oauth/token | POST | 토큰 발급 |
| /api/v1/user | POST | 사용자 등록 |
| /api/v1/user/address | PUT | 사용자 주소지 변경 |
| /api/v1/user | DELETE | 회원 탈퇴 |
| /api/v1/user | GET | 사용자 정보 조회 |

|  |  |  |
| ------ | ------ | ------ |
| /api/v1/store | POST | 업체 등록 |
| /api/v1/store/{businessNumber}/logo | PUT | 업체 로고 변경 |
| /api/v1/store/{businessNumber}/business-name | PUT | 업체명 변경 |
| /api/v1/store/{businessNumber}/tel | PUT | 업체 전화번호 변경 |
| /api/v1/store/{businessNumber}/business-hour | PUT | 업체 운영시간 변경 |
| /api/v1/store/{businessNumber}/tag | PUT | 업체 태그 추가 |
| /api/v1/store/{businessNumber}/tag | DELETE | 업체 태그 제거 |
| /api/v1/store/{businessNumber} | GET | 업체 상세 조회 |
| /api/v1/store/difference-coordinate | GET | 좌표값의 x(km) 떨어진 업체 조회 |

|  |  |  |
| ------ | ------ | ------ |
| /api/v1/interest/{businessNumber} | POST | 관심 업체 등록 |
| /api/v1/interest | GET | 관심 업체 조회 |
