package com.ogpe.block.model.implementation;

import com.ogpe.block.model.BlockModel;
import com.ogpe.requester.Requester;

public class PrintBlockModel extends BlockModel {

	private Requester<?> printValueRequester;

	public PrintBlockModel(Requester<?> printValueRequester) {
		this.printValueRequester = printValueRequester;
	}

	public Requester<?> getPrintValueRequester() {
		return printValueRequester;
	}

	public void setPrintValueRequester(Requester<?> printValueRequester) {
		this.printValueRequester = printValueRequester;
	}
}
