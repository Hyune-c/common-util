package com.example.commonutil.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

	// '테스트시크릿' SHA256 해시
	private static final String SECREAT = "05146D99AB51F39775332C474DEBB3682271071D6B5F7AF11C3AA1D3F6C6F0BB";
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

	@Bean
	public AccessTokenProvider jwtAuthTokenProvider() {
		final long retentionMinute = 60L;
		return new AccessTokenProvider(SECREAT, SIGNATURE_ALGORITHM, () -> Date.from(
				LocalDateTime.now().plusMinutes(retentionMinute).atZone(ZoneId.systemDefault()).toInstant()));
	}

	@Bean
	public RefreshTokenProvider refreshTokenProvider() {
		final long retentionMinute = 60 * 24 * 14L;
		return new RefreshTokenProvider(SECREAT, SIGNATURE_ALGORITHM, () -> Date.from(
				LocalDateTime.now().plusMinutes(retentionMinute).atZone(ZoneId.systemDefault()).toInstant()));
	}
}
