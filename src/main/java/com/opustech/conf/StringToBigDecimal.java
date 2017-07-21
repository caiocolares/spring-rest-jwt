package com.opustech.conf;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;

public class StringToBigDecimal implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String source) {
		if(source == null || source.trim().isEmpty()){
			return BigDecimal.ZERO;
		}
		source = source.replace(".", "").replace(",", ".");
		return new BigDecimal(source);
	}

}
