## 西塔（CITA）测试链

https://testnet.citahub.com

## 获取代币

https://faucet.citahub.com/faucet/getNos

## codegen

```shell
java -jar lib/cita-sdk-20.2.0.jar solidity generate tests/src/main/resources/Token.bin tests/src/main/resources/Token.abi -o tests/src/main/java/ -p com.citahub.cita.tests
```

### 获取目录

```http request
GET http://localhost:8080/records/latest
```

### 发布

```http request
POST http://localhost:8080/records?data=你好
```

### 获取最新记录

```http request
GET http://localhost:8080/records/latest/1662221177760
```

### 部署

```http request
POST http://localhost:8080/records/deploy
```