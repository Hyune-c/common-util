package com.example.commonutil.jwt;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface Token<T> {

	@Getter
	@RequiredArgsConstructor
	enum ClaimKey {
		USER_ID("userId"),
		TOKEN_TYPE("tokenType");

		private final String keyName;
	}

	void validation();

	Claims getClaims();
}
