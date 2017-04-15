package com.ogpe.block.model.implementation;

import com.ogpe.block.behaviour.Provider;
import com.ogpe.block.model.BlockModel;

public class PrintBlockModel extends BlockModel {
	
	private Provider<?> printValueProvider;

	public PrintBlockModel(Provider<?> printValueProvider) {
		this.printValueProvider = printValueProvider;
	}

	public Provider<?> getPrintValueProvider() {
		return printValueProvider;
	}

	public void setPrintValueProvider(Provider<?> printValueProvider) {
		this.printValueProvider = printValueProvider;
	}
}
