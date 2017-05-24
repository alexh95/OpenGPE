package com.ogpe.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ogpe.block.Block;
import com.ogpe.block.BlockType;
import com.ogpe.block.RunningContext;
import com.ogpe.block.geometry.Point;
import com.ogpe.block.geometry.Rectangle;
import com.ogpe.block.wire.WireLink;
import com.ogpe.block.wire.WireNetwork;
import com.ogpe.block.wire.WireNode;
import com.ogpe.block.wire.WireNodeHighlight;
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

	private int maxRunnningIterations;

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

		maxRunnningIterations = 0;
	}

	public boolean canPlace(Rectangle rectangle) {
		if (rectangle.x < 0 || rectangle.y < 0) {
			return false;
		}
		
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
		wireNodes.stream().filter(wireNode -> !wireNetwork.contains(wireNode))
				.forEach(wireNode -> wireNode.setHighlight(WireNodeHighlight.UNSET));
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

	public void setMaxRunnningIterations(int maxRunnningIterations) {
		this.maxRunnningIterations = maxRunnningIterations;
	}
	
	private boolean canRun() {
		return wireNodes.stream().map(wireNetwork::contains).reduce(false, Boolean::logicalOr);
	}

	public void run() {
		if (blocks.isEmpty()) {
			consoleOutputObservable.updateObservers("Cannot Run! No blocks.");
			return;
		}
		if (!canRun()) {
			consoleOutputObservable.updateObservers("Cannot Run! Unlinked nodes.");
			return;
		}
		consoleOutputObservable.updateObservers("Running:");
		blocks.forEach(block -> block.reset());
		wireNetwork.getLinks().forEach(link -> {
			link.getDst().setProvider(link.getSrc());
		});
		RunningContext context = new RunningContext(consoleOutputObservable, 1);
		blocks.forEach(block -> block.preRunBlock(context));
		blocks.forEach(block -> block.runBlock(context));
		blocks.forEach(block -> block.postRunBlock(context));
	}

	public void runContinuously() {
		if (blocks.isEmpty()) {
			consoleOutputObservable.updateObservers("Cannot Run! No blocks.");
			return;
		}
		if (!canRun()) {
			consoleOutputObservable.updateObservers("Cannot Run! Unlinked nodes.");
			return;
		}
		consoleOutputObservable.updateObservers("Running Continuously (up to " + maxRunnningIterations + " times):");
		blocks.forEach(block -> block.reset());
		wireNetwork.getLinks().forEach(link -> {
			link.getDst().setProvider(link.getSrc());
		});
		boolean stopped = false;
		for (int runningIndex = 1; runningIndex <= maxRunnningIterations && !stopped; ++runningIndex) {
			RunningContext context = new RunningContext(consoleOutputObservable, runningIndex);
			blocks.forEach(block -> block.preRunBlock(context));
			blocks.forEach(block -> block.runBlock(context));
			blocks.forEach(block -> block.postRunBlock(context));
			stopped = context.isStopped();
		}
	}
	
	public void newProject() {
		blocks.clear();
		wireNodes.clear();
		wireNetwork.getLinks().clear();
		redrawCallback.callback();
	}
	
	public void saveProject(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write(blocks.size() + "");
		bufferedWriter.newLine();
		int blockIndex = 0;
		Map<Block, Integer> writeBlocks = new HashMap<>();
		for (Block block : blocks) {
			++blockIndex;
			writeBlocks.put(block, blockIndex);
			bufferedWriter.write(blockIndex + "");
			bufferedWriter.write(",");
			bufferedWriter.write(block.blockType.name());
			bufferedWriter.write(",");
			bufferedWriter.write(block.getRectangle().x + "");
			bufferedWriter.write(",");
			bufferedWriter.write(block.getRectangle().y + "");
			bufferedWriter.newLine();
		}
		
		bufferedWriter.write(wireNetwork.getLinks().size() + "");
		bufferedWriter.newLine();
		for (WireLink wireLink : wireNetwork.getLinks()) {
			WireNode src = wireLink.getSrc();
			bufferedWriter.write(writeBlocks.get(src.getBlock()) + "-" + src.key);
			bufferedWriter.write(",");
			WireNode dst = wireLink.getDst();
			bufferedWriter.write(writeBlocks.get(dst.getBlock()) + "-" + dst.key);
			bufferedWriter.newLine();
		}
	}
	
	public void openProject(BufferedReader bufferedReader) throws IOException {
		blocks.clear();
		wireNodes.clear();
		wireNetwork.getLinks().clear();
		
		Map<Integer, Block> readBlocks = new TreeMap<>();
		int blockCount = Integer.parseInt(bufferedReader.readLine());
		for (int index = 0; index < blockCount; ++index) {
			String[] words = bufferedReader.readLine().split(",");
			int blockIndex = Integer.parseInt(words[0]);
			Point position = new Point(Double.parseDouble(words[2]), Double.parseDouble(words[3]));
			Block block = BlockType.valueOf(words[1]).makeBlock(position);
			readBlocks.put(blockIndex, block);
			addBlock(block);
		}
		
		int linkCount = Integer.parseInt(bufferedReader.readLine());
		for (int index = 0; index < linkCount; ++index) {
			String[] words = bufferedReader.readLine().split(",");
			
			String[] srcParts = words[0].split("-");
			int srcBlockIndex = Integer.parseInt(srcParts[0]);
			String srcNodeKey = srcParts[1];
			Block srcBlock = readBlocks.get(srcBlockIndex);
			WireNode srcNode = srcBlock.getWireNodes().get(srcNodeKey);
			
			String[] dstParts = words[1].split("-");
			int dstBlockIndex = Integer.parseInt(dstParts[0]);
			String dstNodeKey = dstParts[1];
			Block dstBlock = readBlocks.get(dstBlockIndex);
			WireNode dstNode = dstBlock.getWireNodes().get(dstNodeKey);
			
			WireLink link = new WireLink(srcNode, dstNode);
			wireNetwork.addLink(link);
		}
		
		wireNodes.stream().filter(wireNetwork::contains).forEach(wireNode -> wireNode.setHighlight(WireNodeHighlight.SET));
		redrawCallback.callback();
	}
	
}
