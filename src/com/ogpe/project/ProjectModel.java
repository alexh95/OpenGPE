package com.ogpe.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ogpe.blockx.Block;
import com.ogpe.blockx.Rectangle;
import com.ogpe.blockx.RunningContext;
import com.ogpe.blockx.wire.WireNetwork;
import com.ogpe.blockx.wire.WireNode;
import com.ogpe.observable.Callback;
import com.ogpe.observable.Observable;
import com.ogpe.observable.Observer;

import javafx.scene.Node;

public class ProjectModel {

	private final Callback redrawCallback;
	private final Observable<Node> editingPanePaneObservable;
	private final Observable<String> consoleOutputObservable;

	private final List<Block> blocks;
	private final List<WireNode> wireNodes;
	private final WireNetwork wireNetwork;

	public ProjectModel(Callback redrawCallback, Observer<Node> editingPanePaneObserver,
			Observer<String> consoleOutputObserver) {
		this.redrawCallback = redrawCallback;
		editingPanePaneObservable = new Observable<>();
		editingPanePaneObservable.addObserver(editingPanePaneObserver);
		consoleOutputObservable = new Observable<>();
		consoleOutputObservable.addObserver(consoleOutputObserver);

		blocks = new ArrayList<>();
		wireNodes = new ArrayList<>();
		wireNetwork = new WireNetwork();
	}

	public boolean canPlace(Rectangle rectangle) {
		for (Block block : blocks) {
			if (!block.isMoving()) {
				if (block.getRectangle().intersects(rectangle)) {
					return false;
				}
			}
		}
		return true;
	}

	public void callbackRedraw() {
		redrawCallback.callback();
	}

	public void updateEditingPaneObservable(Node editingPane) {
		editingPanePaneObservable.updateObservers(editingPane);
	}

	public Observable<String> getConsoleOutputObservable() {
		return consoleOutputObservable;
	}

	public Callback getRedrawCallback() {
		return redrawCallback;
	}

	public Observable<Node> getEditingPanePaneObservable() {
		return editingPanePaneObservable;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void addBlock(Block block) {
		blocks.add(block);
		wireNodes.addAll(block.getWireNodes().values());
	}

	public void removeBlock(Block block) {
		blocks.remove(block);
		wireNodes.removeAll(block.getWireNodes().values());
		wireNetwork.removeNode(block.getWireNodes().values());
	}

	public void removeBlock(Collection<Block> blocks) {
		blocks.forEach(this::removeBlock);
	}

	public List<WireNode> getWireNodes() {
		return wireNodes;
	}

	public WireNetwork getWireNetwork() {
		return wireNetwork;
	}

	public void run() {
		wireNetwork.getLinks().forEach(link -> {
			link.getDst().setProvider(link.getSrc());
		});
		RunningContext context = new RunningContext(consoleOutputObservable, 1);
		blocks.forEach(block -> block.runBlock(context));
	}

}
