# CommonUtil

- 실무에서 필요에 의해 만든 Custom Util 들을 공유합니다.  
- 재사용성과 범용 사용성을 고려하여 설계했지만 하위 호환성은 부족하기에 주석과 테스트 케이스를 참고하여 업무에 맞게 커스텀하기를 권장합니다.
- Spring Boot 환경의 라이브러리들이 일부 사용되었습니다.

### QuerydslUtil

- page 를 slice 로 변환시켜주는 기능
- 동적 쿼리를 위한 BooleanExpression 를 편하게 사용하는 기능

### JWT

- jsonwebtoken 라이브러리 사용
- JwtConfig 를 통한 설정 분리
- AccessToken, RefreshToken 생성과 상호 갱신 기능
- Provider 를 통한 안전한 생성
- Role 에 대한 인터페이스는 제공하지 않습니다. <- 추후 개선 예정
