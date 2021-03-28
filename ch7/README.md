# sprinig batch

---

spring batch는 현업에서는 shell에서 crontab으로 지정후, java를 실행하거나 spring에서 @Schedule 이나 @Cron 과 같은 어노테이션을 많이 썼었다.<br>
spring boot에서는 어떻게 사용하는지 공부하기 위해 보겠습니다.

![build.gradle](./Resource/buildgradle.png)

위의 사진 처럼 라이브러리를 추가해준다.<br>
처음보는 라이브러리라 하면 spring-boot-starter-batch 이다.<br>
이번 공부때 제일 중요한 라이브러리이다. 꼭 알아두자!

![Configuration](./Resource/Configuration.png)

Configuration은 Job과 Step을 설정해준다.<br>
예를 들어 helloJob와 helloStep을 만들어 실행이 되는지 확인해보겠습니다.

![argument1](./Resource/argument1.png)

java argument에 ```--spring.batch.job.names=helloJob``` 을 넣어 helloJob을 실행해 볼 수 있다.

![console](./Resource/console.png)

console에 보면 hello spring batch 가 뜬것을 볼 수 있다.

- 다른 방법

application.yml 이라는 스프링부트 설정을 잡는 곳을 이용해 보겠다.

![application](./Resource/applicationYML.png)

job.name을 설정하고 argument를 설정하면 더 짧게 argument을 지정할 수 있다.

![argument2](./Resource/argument2.png)

실행결과는 위에 ```--spring.batch.job.names=helloJob``` 와 같은 결과가 나온다.