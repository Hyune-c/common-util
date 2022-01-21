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
public class RefreshToken implements Token<Claims> {

	@Getter
	private final String token;
	private final Key secreatKey;

	RefreshToken(final String token, final Key secreatKey) {
		this.token = token;
		this.secreatKey = secreatKey;

		validation();
	}

	RefreshToken(final Map<String, Object> claims, final Date expiredDate, final Key secreatKey, final SignatureAlgorithm signatureAlgorithm) {
		claims.put(ClaimKey.TOKEN_TYPE.getKeyName(), TokenType.REFRESH.name());
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
		if (!getClaims().get(ClaimKey.TOKEN_TYPE.getKeyName()).equals(TokenType.REFRESH.name())) {
			throw new IllegalStateException();
		}
	}

	@Override
	public Claims getClaims() {
		return Jwts.parserBuilder().setSigningKey(secreatKey).build().parseClaimsJws(token).getBody();
	}
}
