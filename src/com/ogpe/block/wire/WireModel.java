package com.ogpe.block.wire;

import java.util.ArrayList;
import java.util.List;

import com.ogpe.requester.Requester;

public class WireModel<T> {

	private List<WireNode<T>> wireNodes;
	private Requester<T> sourceRequester;

	public WireModel() {
		wireNodes = new ArrayList<>();
		sourceRequester = null;
	}

	public boolean addWireNode(WireNode<T> wireNode) {
		wireNodes.add(wireNode);
		WireNodeTarget<T> wireNodeTarget = wireNode.getWireNodeTarget();
		switch (wireNodeTarget.getWireNodeTargetType()) {
		case SOURCE:
			if (sourceRequester != null) {
				return false;
			}
			sourceRequester = wireNodeTarget.getRequester();
			break;
		case DESTINATION:
			wireNodeTarget.setRequester(sourceRequester);
			break;
		}
		return true;
	}

	public void removeWireNode(WireNode<T> wireNode) {
		wireNodes.remove(wireNode);
		WireNodeTarget<T> wireNodeTarget = wireNode.getWireNodeTarget();
		switch (wireNodeTarget.getWireNodeTargetType()) {
		case SOURCE:
			sourceRequester = null;
			break;
		case DESTINATION:
			wireNodeTarget.setRequester(null);
			break;
		}
	}
}
