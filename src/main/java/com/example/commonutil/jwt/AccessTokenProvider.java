package com.example.commonutil.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

public class AccessTokenProvider implements TokenProvider<AccessToken> {

	private final Key secreatKey;
	private final SignatureAlgorithm signatureAlgorithm;
	private final Supplier<Date> defaultExpiration;

	public AccessTokenProvider(final String secreat, final SignatureAlgorithm signatureAlgorithm, final Supplier<Date> defaultExpiration) {
		final byte[] keyBytes = Decoders.BASE64.decode(secreat);
		this.secreatKey = Keys.hmacShaKeyFor(keyBytes);
		this.signatureAlgorithm = signatureAlgorithm;
		this.defaultExpiration = defaultExpiration;
	}

	@Override
	public AccessToken create(final Map<String, Object> claims, final Date expiredDate) {
		return new AccessToken(claims, expiredDate, secreatKey, signatureAlgorithm);
	}

	@Override
	public AccessToken create(final Map<String, Object> claims) {
		return create(claims, defaultExpiration.get());
	}

	@Override
	public AccessToken convert(final String accessTokenString) {
		return new AccessToken(accessTokenString, secreatKey);
	}

	public AccessToken convert(final RefreshToken refreshToken) {
		return create(refreshToken.getClaims(), defaultExpiration.get());
	}
}
