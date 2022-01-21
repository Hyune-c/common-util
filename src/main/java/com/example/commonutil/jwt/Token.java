package com.example.commonutil.jwt;

import io.jsonwebtoken.Claims;

public interface Token<T> {

	String TOKEN_TYPE_CLAIM_KEY = "tokenType";

	void validation();

	Claims getClaims();
}
