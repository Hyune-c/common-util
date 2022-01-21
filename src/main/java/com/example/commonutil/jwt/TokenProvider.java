package com.example.commonutil.jwt;

import java.util.Date;
import java.util.Map;

public interface TokenProvider<T> {

	T create(Map<String, Object> claims, Date expiredDate);

	T create(Map<String, Object> claims);

	T convert(String token);
}
