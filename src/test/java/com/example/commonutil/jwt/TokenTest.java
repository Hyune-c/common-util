package com.example.commonutil.jwt;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.commonutil.jwt.Token.ClaimKey;
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
			"01022223333"
	})
	void accessToken_create(final String userIdValue) {
		// given
		final Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimKey.USER_ID.getKeyName(), userIdValue);

		// when
		final AccessToken accessToken = accessTokenProvider.create(claims);

		// then
		assertThat(accessToken.getClaims().get(ClaimKey.USER_ID.getKeyName())).isEqualTo(userIdValue);
		assertThat(accessToken.getClaims().get(ClaimKey.TOKEN_TYPE.getKeyName())).isEqualTo(TokenType.ACCESS.name());
	}

	@DisplayName("accessTokenString 으로 accesToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"01022223333"
	})
	void accessToken_createByAccessTokenString(final String userIdValue) {
		// given
		final AccessToken accessToken = createAccessToken(userIdValue);

		// when
		final AccessToken newAccessToken = accessTokenProvider.convert(accessToken.getToken());

		// then
		assertThat(newAccessToken.getClaims().get(ClaimKey.USER_ID.getKeyName())).isEqualTo(userIdValue);
		assertThat(newAccessToken.getClaims().get(ClaimKey.TOKEN_TYPE.getKeyName())).isEqualTo(TokenType.ACCESS.name());
	}

	@DisplayName("[실패] refreshTokenString 으로 accesToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"01022223333"
	})
	void accessToken_createByRefreshTokenString(final String userIdValue) {
		// given
		final RefreshToken refreshToken = createRefreshToken(userIdValue);

		// when
		assertThrows(IllegalStateException.class, () -> accessTokenProvider.convert(refreshToken.getToken()));

		// then
	}

	@DisplayName("refreshToken 으로 accesToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"01022223333"
	})
	void accessToken_createByRefreshToken(final String userId) {
		// given
		final RefreshToken refreshToken = createRefreshToken(userId);

		// when
		final AccessToken newAccessToken = accessTokenProvider.convert(refreshToken);

		// then
		assertThat(newAccessToken.getClaims().get(ClaimKey.USER_ID.getKeyName())).isEqualTo(userId);
		assertThat(newAccessToken.getClaims().get(ClaimKey.TOKEN_TYPE.getKeyName())).isEqualTo(TokenType.ACCESS.name());
	}

	@DisplayName("refreshToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"01022223333"
	})
	void refreshToken_create(final String userIdValue) {
		// given
		final AccessToken accessToken = createAccessToken(userIdValue);

		// when
		final RefreshToken refreshToken = refreshTokenProvider.convert(accessToken);

		// then
		assertThat(refreshToken.getClaims().get(ClaimKey.USER_ID.getKeyName())).isEqualTo(userIdValue);
		assertThat(refreshToken.getClaims().get(ClaimKey.TOKEN_TYPE.getKeyName())).isEqualTo(TokenType.REFRESH.name());
	}

	@DisplayName("refreshTokenString 으로 refreshToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"01022223333"
	})
	void refreshToken_createByRefreshTokenString(final String userIdValue) {
		// given
		final RefreshToken refreshToken = createRefreshToken(userIdValue);

		// when
		final RefreshToken newRefreshToken = refreshTokenProvider.convert(refreshToken.getToken());

		// then
		assertThat(newRefreshToken.getClaims().get(ClaimKey.USER_ID.getKeyName())).isEqualTo(userIdValue);
		assertThat(newRefreshToken.getClaims().get(ClaimKey.TOKEN_TYPE.getKeyName())).isEqualTo(TokenType.REFRESH.name());
	}

	@DisplayName("[실패] createTokenString 으로 refreshToken 생성")
	@ParameterizedTest
	@CsvSource(value = {
			"01022223333"
	})
	void refreshToken_createByCreateTokenString(final String userIdValue) {
		// given
		final AccessToken accessToken = createAccessToken(userIdValue);

		// when
		assertThrows(IllegalStateException.class, () -> refreshTokenProvider.convert(accessToken.getToken()));

		// then
	}

	private AccessToken createAccessToken(final String userIdValue) {
		final Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimKey.USER_ID.getKeyName(), userIdValue);
		return accessTokenProvider.create(claims);
	}

	private RefreshToken createRefreshToken(final String userIdValue) {
		final Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimKey.USER_ID.getKeyName(), userIdValue);
		final AccessToken accessToken = accessTokenProvider.create(claims);
		return refreshTokenProvider.convert(accessToken);
	}
}
