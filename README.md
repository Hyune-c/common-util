# CommonUtil

실무에서 필요에 의해 만든 Custom Util 들을 공유합니다.  
재사용성과 범용 사용성을 고려하여 설계했지만 하위 호환성은 부족하기에 주석과 테스트 케이스를 참고하여 업무에 맞게 커스텀하기를 권장합니다.

### QuerydslUtil

- page 를 slice 로 변환시켜주는 기능
- 동적 쿼리를 위한 BooleanExpression 를 편하게 사용하는 기능
