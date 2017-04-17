package com.ogpe.block.model;

import java.util.ArrayList;
import java.util.List;

import com.ogpe.block.wire.WireNodeTarget;

public abstract class BlockModel {

	private List<WireNodeTarget> wireNodeTargets;
	
	public BlockModel() {
		wireNodeTargets = new ArrayList<>();
	}
	
	public void addWireNodeTarget(WireNodeTarget wireNodeTarget) {
		wireNodeTargets.add(wireNodeTarget);
	}
	
	public List<WireNodeTarget> getWireNodeTargets() {
		return wireNodeTargets;
	}
}
