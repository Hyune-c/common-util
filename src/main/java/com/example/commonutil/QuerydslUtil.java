package com.example.commonutil;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@UtilityClass
public class QuerydslUtil {

	public static <T> Slice<T> createSlice(final List<T> content, final Pageable pageable) {
		boolean hasNext = false;

		if (content.size() > pageable.getPageSize()) {
			content.remove(content.size() - 1);
			hasNext = true;
		}
		return new SliceImpl<>(content, pageable, hasNext);
	}

	public static Order getDirection(final Sort.Order order) {
		if (order.getDirection().isAscending()) {
			return ASC;
		}
		return DESC;
	}

	public static BooleanExpression dynamicEq(final StringPath target, final String input) {
		if (StringUtils.hasText(input)) {
			return target.eq(input);
		}
		return null;
	}

	public static BooleanExpression dynamicEq(final NumberPath<Long> target, final Long input) {
		if (input == null) {
			return null;
		}
		return target.eq(input);
	}

	public static BooleanExpression dynamicEq(final NumberPath<Integer> target, final Integer input) {
		if (input == null) {
			return null;
		}
		return target.eq(input);
	}
}
