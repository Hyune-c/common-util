package com.example.commonutil.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessToken implements Token<Claims> {

	@Getter
	private final String token;
	private final Key secreatKey;

	AccessToken(final String token, final Key secreatKey) {
		this.token = token;
		this.secreatKey = secreatKey;

		validation();
	}

	AccessToken(final Map<String, Object> claims, final Date expiredDate, final Key secreatKey, final SignatureAlgorithm signatureAlgorithm) {
		claims.put(TOKEN_TYPE_CLAIM_KEY, TokenType.ACCESS.name());
		this.token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(expiredDate)
				.signWith(secreatKey, signatureAlgorithm)
				.compact();
		this.secreatKey = secreatKey;

		validation();
	}

	@Override
	public void validation() {
		if (!getClaims().get(TOKEN_TYPE_CLAIM_KEY).equals(TokenType.ACCESS.name())) {
			throw new IllegalStateException();
		}
	}

	@Override
	public Claims getClaims() {
		return Jwts.parserBuilder().setSigningKey(secreatKey).build().parseClaimsJws(token).getBody();
	}
}
