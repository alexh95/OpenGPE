package com.ogpe.blockx.wire;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WireNetwork {

	private final Set<WireLink> links;
	
	public WireNetwork() {
		links = new HashSet<>();
	}
	
	public Set<WireLink> getLinks() {
		return links;
	}
	
	public void addLink(WireLink link) {
		links.add(link);
	}
	
	public void removeLink(WireLink link) {
		links.remove(link);
	}
	
	public void removeLink(Collection<WireLink> links) {
		links.forEach(this::removeLink);
	}
	
	public void removeNode(WireNode node) {
		links.removeAll(links.stream().filter(link -> link.contains(node)).collect(Collectors.toSet()));
	}
	
	public void removeNode(Collection<WireNode> nodes) {
		nodes.forEach(this::removeNode);
	}
	
	public boolean contains(WireNode node) {
		return links.stream()
				.map(link -> link.contains(node))
				.reduce(false, Boolean::logicalOr);
	}
	
}
