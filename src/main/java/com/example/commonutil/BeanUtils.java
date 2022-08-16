package com.example.commonutil;

import java.util.function.Function;

public class BeanUtils {

	public static <T, R> R getOrNull(final T dto, final Function<T, R> getter) {
		if (dto == null) {
			return null;
		}
		return getter.apply(dto);
	}

	public static <T, R> R getOrDefault(final T dto, final Function<T, R> getter, final R defaultValue) {
		if (dto == null || getter.apply(dto) == null) {
			return defaultValue;
		}

		return getter.apply(dto);
	}
}
