package com.ogpe.fx;

public enum BlockListElement {
	CONSTANT("Constant Block"), 
	SUM("Sum Block"), 
	PRINT("Print Block");

	public static BlockListElement valueOfDisplayName(String displayName) {
		BlockListElement blockListElement = null;
		for (BlockListElement block : values()) {
			if (block.getDisplayName().equals(displayName)) {
				blockListElement = block;
			}
		}
		return blockListElement;
	}

	private String displayName;

	private BlockListElement(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
