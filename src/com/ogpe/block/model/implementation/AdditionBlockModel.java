package com.ogpe.block.model.implementation;

import java.math.BigDecimal;

import com.ogpe.block.model.BlockModel;
import com.ogpe.requester.Requester;

public class AdditionBlockModel extends BlockModel {

	private Requester<BigDecimal> firstOperandRequester;
	private Requester<BigDecimal> secondOperandRequester;
	
	public AdditionBlockModel() {
		super();
	}

	public Requester<BigDecimal> getFirstOperandRequester() {
		return firstOperandRequester;
	}

	public void setFirstOperandRequester(Requester<BigDecimal> firstOperandRequester) {
		this.firstOperandRequester = firstOperandRequester;
	}

	public Requester<BigDecimal> getSecondOperandRequester() {
		return secondOperandRequester;
	}

	public void setSecondOperandRequester(Requester<BigDecimal> secondOperandRequester) {
		this.secondOperandRequester = secondOperandRequester;
	}
}
