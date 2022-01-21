package com.example.commonutil.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

public class RefreshTokenProvider implements TokenProvider<RefreshToken> {

	private final Key secreatKey;
	private final SignatureAlgorithm signatureAlgorithm;
	private final Supplier<Date> defaultExpiration;

	public RefreshTokenProvider(final String secreat, final SignatureAlgorithm signatureAlgorithm, final Supplier<Date> defaultExpiration) {
		final byte[] keyBytes = Decoders.BASE64.decode(secreat);
		this.secreatKey = Keys.hmacShaKeyFor(keyBytes);
		this.signatureAlgorithm = signatureAlgorithm;
		this.defaultExpiration = defaultExpiration;
	}

	@Override
	public RefreshToken create(final Map<String, Object> claims, final Date expiredDate) {
		return new RefreshToken(claims, expiredDate, secreatKey, signatureAlgorithm);
	}

	@Override
	public RefreshToken create(final Map<String, Object> claims) {
		return create(claims, defaultExpiration.get());
	}

	@Override
	public RefreshToken convert(final String refreshTokenString) {
		return new RefreshToken(refreshTokenString, secreatKey);
	}

	public RefreshToken convert(final AccessToken token) {
		return create(token.getClaims(), defaultExpiration.get());
	}
}
