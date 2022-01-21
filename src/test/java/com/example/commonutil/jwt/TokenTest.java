package com.example.commonutil.jwt;

import static com.example.commonutil.jwt.Token.TOKEN_TYPE_CLAIM_KEY;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("JWT")
@SpringBootTest
class TokenTest {

	@Autowired private AccessTokenProvider accessTokenProvider;
	@Autowired private RefreshTokenProvider refreshTokenProvider;

	@DisplayName("accessToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"userId,1111"
	})
	void accessToken_create(final String userIdKey, final String userIdValue) {
		// given
		final Map<String, Object> claims = new HashMap<>();
		claims.put(userIdKey, userIdValue);

		// when
		final AccessToken accessToken = accessTokenProvider.create(claims);

		// then
		assertThat(accessToken.getClaims().get(userIdKey)).isEqualTo(userIdValue);
		assertThat(accessToken.getClaims().get(TOKEN_TYPE_CLAIM_KEY)).isEqualTo(TokenType.ACCESS.name());
	}

	@DisplayName("accessTokenString 으로 accesToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"userId,1111"
	})
	void accessToken_createByAccessTokenString(final String userIdKey, final String userIdValue) {
		// given
		final AccessToken accessToken = createAccessToken(userIdKey, userIdValue);

		// when
		final AccessToken newAccessToken = accessTokenProvider.convert(accessToken.getToken());

		// then
		assertThat(newAccessToken.getClaims().get(userIdKey)).isEqualTo(userIdValue);
		assertThat(newAccessToken.getClaims().get(TOKEN_TYPE_CLAIM_KEY)).isEqualTo(TokenType.ACCESS.name());
	}

	@DisplayName("[실패] refreshTokenString 으로 accesToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"userId,1111"
	})
	void accessToken_createByRefreshTokenString(final String userIdKey, final String userIdValue) {
		// given
		final RefreshToken refreshToken = createRefreshToken(userIdKey, userIdValue);

		// when
		assertThrows(IllegalStateException.class, () -> accessTokenProvider.convert(refreshToken.getToken()));

		// then
	}

	@DisplayName("refreshToken 으로 accesToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"userId,1111"
	})
	void accessToken_createByRefreshToken(final String userIdKey, final String userIdValue) {
		// given
		final RefreshToken refreshToken = createRefreshToken(userIdKey, userIdValue);

		// when
		final AccessToken newAccessToken = accessTokenProvider.convert(refreshToken);

		// then
		assertThat(newAccessToken.getClaims().get(userIdKey)).isEqualTo(userIdValue);
		assertThat(newAccessToken.getClaims().get(TOKEN_TYPE_CLAIM_KEY)).isEqualTo(TokenType.ACCESS.name());
	}

	@DisplayName("refreshToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"userId,1111"
	})
	void refreshToken_create(final String userIdKey, final String userIdValue) {
		// given
		final AccessToken accessToken = createAccessToken(userIdKey, userIdValue);

		// when
		final RefreshToken refreshToken = refreshTokenProvider.convert(accessToken);

		// then
		assertThat(refreshToken.getClaims().get(userIdKey)).isEqualTo(userIdValue);
		assertThat(refreshToken.getClaims().get(TOKEN_TYPE_CLAIM_KEY)).isEqualTo(TokenType.REFRESH.name());
	}

	@DisplayName("refreshTokenString 으로 refreshToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"userId,1111"
	})
	void refreshToken_createByRefreshTokenString(final String userIdKey, final String userIdValue) {
		// given
		final RefreshToken refreshToken = createRefreshToken(userIdKey, userIdValue);

		// when
		final RefreshToken newRefreshToken = refreshTokenProvider.convert(refreshToken.getToken());

		// then
		assertThat(newRefreshToken.getClaims().get(userIdKey)).isEqualTo(userIdValue);
		assertThat(newRefreshToken.getClaims().get(TOKEN_TYPE_CLAIM_KEY)).isEqualTo(TokenType.REFRESH.name());
	}

	@DisplayName("[실패] createTokenString 으로 refreshToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"userId,1111"
	})
	void refreshToken_createByCreateTokenString(final String userIdKey, final String userIdValue) {
		// given
		final AccessToken accessToken = createAccessToken(userIdKey, userIdValue);

		// when
		assertThrows(IllegalStateException.class, () -> refreshTokenProvider.convert(accessToken.getToken()));

		// then
	}

	private AccessToken createAccessToken(final String userIdKey, final String userIdValue) {
		final Map<String, Object> claims = new HashMap<>();
		claims.put(userIdKey, userIdValue);
		return accessTokenProvider.create(claims);
	}

	private RefreshToken createRefreshToken(final String userIdKey, final String userIdValue) {
		final Map<String, Object> claims = new HashMap<>();
		claims.put(userIdKey, userIdValue);
		final AccessToken accessToken = accessTokenProvider.create(claims);
		return refreshTokenProvider.convert(accessToken);
	}
}
