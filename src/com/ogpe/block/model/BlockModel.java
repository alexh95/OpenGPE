package com.ogpe.block.model;

import java.util.ArrayList;
import java.util.List;

import com.ogpe.block.wire.WireNodeTarget;

public abstract class BlockModel {

	private List<WireNodeTarget<?>> wireNodeTargets;

	public BlockModel() {
		wireNodeTargets = new ArrayList<>();
	}

	public void addWireNodeTarget(WireNodeTarget<?> wireNodeTarget) {
		wireNodeTargets.add(wireNodeTarget);
	}

	public List<WireNodeTarget<?>> getWireNodeTargets() {
		return wireNodeTargets;
	}

	public WireNodeTarget<?> getWireNodeTarget(double x, double y) {
		WireNodeTarget<?> closestWireNodeTarget = null;
		double closestWireNodeTargetDistance = 0;

		for (WireNodeTarget<?> wireNodeTarget : wireNodeTargets) {
			double dx = x - wireNodeTarget.getWireNodeTargetView().getX();
			double dy = y - wireNodeTarget.getWireNodeTargetView().getY();
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (closestWireNodeTarget == null || distance < closestWireNodeTargetDistance) {
				closestWireNodeTarget = wireNodeTarget;
				closestWireNodeTargetDistance = distance;
			}
		}
		
		return closestWireNodeTarget;
	};
}
