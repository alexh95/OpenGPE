package com.ogpe.project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ogpe.block.Block;
import com.ogpe.block.factory.BlockFactory;
import com.ogpe.block.network.NetworkNode;
import com.ogpe.block.network.NetworkNodeHighlight;
import com.ogpe.observable.Observable;
import com.ogpe.observable.Observer;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class ProjectModel {

	// Observable
	private Observable<Object> redrawObservable;
	private Observable<Node> editingPanePaneObservable;
	private Observable<String> consoleOutputObservable;

	private BlockFactory blockFactory;
	private List<Block<?, ?>> blocks;
	private List<NetworkNode<?>> networkNodes;

	// Pan

	// Place
	private BlockSelection placingBlockSelection;
	private boolean displayPlacing;
	private boolean selectedBlockPlaceble;
	private Rectangle selectingBlockPlacingRectangle;
	private Block<?, ?> editingBlock;

	// Select
	private List<Block<?, ?>> selectedBlocks;
	private boolean dragSelecting;
	private double startDragSelectionX;
	private double startDragSelectionY;
	private Rectangle dragSelectionRectangle;
	
	// Move
	private Block<?, ?> movingBlock;
	private double movingBlockOffsetX;
	private double movingBlockOffsetY;

	// Wire
	private boolean wiring;
	private NetworkNode<?> wiringNetworkNode;
	private double wiringStartX;
	private double wiringStartY;
	private double wireX;
	private double wireY;

	public ProjectModel(Observer<Object> redrawObserver, Observer<Node> editingPanePaneObserver, Observer<String> consoleOutputObserver) {
		redrawObservable = new Observable<>();
		redrawObservable.addObserver(redrawObserver);
		editingPanePaneObservable = new Observable<>();
		editingPanePaneObservable.addObserver(editingPanePaneObserver);
		consoleOutputObservable = new Observable<>();
		consoleOutputObservable.addObserver(consoleOutputObserver);

		blockFactory = new BlockFactory();
		blocks = new ArrayList<>();
		networkNodes = new ArrayList<>();

		// Pan

		// Place
		setPlacingBlockSelection(BlockSelection.CONSTANT_BOOLEAN);
		setDisplayPlacing(false);
		setSelectedBlockPlaceble(false);
		setSelectingBlockPlacingRectangle(null);
		unsetEditingBlock();
		
		// Select 
		selectedBlocks = new ArrayList<>();
		
		// Move
		movingBlock = null;
		movingBlockOffsetX = 0;
		movingBlockOffsetY = 0;
		
		// Wire
		wiring = false;
		wiringNetworkNode = null;
		wiringStartX = 0;
		wiringStartY = 0;
		wireX = 0;
		wireY = 0;
	}

	// Observable
	public void updateRedrawObservable() {
		redrawObservable.updateObservers(null);
	}

	public void updateEditingPaneObservable(Node editingPane) {
		editingPanePaneObservable.updateObservers(editingPane);
	}

	public Observable<String> getConsoleOutputObservable() {
		return consoleOutputObservable;
	}

	// Getters
	public BlockFactory getBlockFactory() {
		return blockFactory;
	}

	public List<Block<?, ?>> getBlocks() {
		return blocks;
	}

	public List<NetworkNode<?>> getNetworkNodes() {
		return networkNodes;
	}

	// Place
	public BlockSelection getPlacingBlockSelection() {
		return placingBlockSelection;
	}

	public void setPlacingBlockSelection(BlockSelection placingBlockSelection) {
		this.placingBlockSelection = placingBlockSelection;
	}

	public boolean isDisplayPlacing() {
		return displayPlacing;
	}

	public void setDisplayPlacing(boolean displayPlacing) {
		this.displayPlacing = displayPlacing;
	}

	public boolean isSelectedBlockPlaceble() {
		return selectedBlockPlaceble;
	}

	public void setSelectedBlockPlaceble(boolean selectedBlockPlaceble) {
		this.selectedBlockPlaceble = selectedBlockPlaceble;
	}

	public Rectangle getSelectingBlockPlacingRectangle() {
		return selectingBlockPlacingRectangle;
	}

	public void setSelectingBlockPlacingRectangle(Rectangle selectingBlockPlacingRectangle) {
		this.selectingBlockPlacingRectangle = selectingBlockPlacingRectangle;
	}

	public void setEditingBlock(Block<?, ?> block) {
		unsetEditingBlock();
		if (block != null) {
			editingBlock = block;
			editingBlock.getBlockView().setEdited(true);
			Node editingPane = editingBlock.getBlockView().getEditingPane(redrawObservable);
			editingPanePaneObservable.updateObservers(editingPane);
		}
	}

	public void unsetEditingBlock() {
		if (editingBlock != null) {
			editingBlock.getBlockView().setEdited(false);
			editingPanePaneObservable.updateObservers(null);
			editingBlock = null;
		}
	}

	public boolean canPlace(double x, double y, double w, double h) {
		boolean intersects = false;
		for (Block<?, ?> block : blocks) {
			if (!block.getBlockView().isMoving()) {
				intersects |= block.getBlockView().intersects(x, y, w, h);
			}
		}
		return !intersects;
	}

	public Block<?, ?> findBlock(double x, double y) {
		List<Block<?, ?>> foundBlocks = blocks.stream().filter(block -> block.getBlockView().isInside(x, y))
				.collect(Collectors.toList());
		if (foundBlocks.size() == 1) {
			return foundBlocks.get(0);
		} else {
			return null;
		}
	}

	// Select
	public List<Block<?, ?>> getSelectedBlocks() {
		return selectedBlocks;
	}
	
	public boolean isDragSelecting() {
		return dragSelecting;
	}
	
	public void setDragSelecting(boolean dragSelecting) {
		this.dragSelecting = dragSelecting;
	}
	
	public double getStartDragSelectionX() {
		return startDragSelectionX;
	}
	
	public void setStartDragSelectionX(double startDragSelectionX) {
		this.startDragSelectionX = startDragSelectionX;
	}
	
	public double getStartDragSelectionY() {
		return startDragSelectionY;
	}
	
	public void setStartDragSelectionY(double startDragSelectionY) {
		this.startDragSelectionY = startDragSelectionY;
	}
	
	public Rectangle getDragSelectionRectangle() {
		return dragSelectionRectangle;
	}
	
	public void setDragSelectionRectangle(Rectangle dragSelectionRectangle) {
		this.dragSelectionRectangle = dragSelectionRectangle;
	}
	
	public void selectBlock(Block<?, ?> block) {
		if (!selectedBlocks.contains(block)) {
			selectedBlocks.add(block);
			block.getBlockView().setSelected(true);
		}
	}

	public void deselectBlock(Block<?, ?> block) {
		if (selectedBlocks.contains(block)) {
			selectedBlocks.remove(block);
			block.getBlockView().setSelected(false);
		}
	}

	public void deselectAllSelectedBlocks() {
		selectedBlocks.forEach(block -> block.getBlockView().setSelected(false));
		selectedBlocks.clear();
	}
	
	public void deleteSelected() {
		selectedBlocks.forEach(selectedBlock -> {
			// The nodes that are about to be deleted
			List<NetworkNode<?>> deletingNetworkNodes = selectedBlock.getBlockModel().getNetworkNodes();

			// Reset the nodes contained by those nodes
			deletingNetworkNodes.forEach(deletingNetworkNode -> {
				NetworkNode<?> containedNetworkNode = deletingNetworkNode.getNetworkNode();
				if (containedNetworkNode != null) {
					containedNetworkNode.setNodeSet(false);
					containedNetworkNode.setHighlighted(NetworkNodeHighlight.UNSET);
				}
			});

			// Reset the nodes containing those nodes
			getNetworkNodes().forEach(networkNode -> {
				if (deletingNetworkNodes.contains(networkNode.getNetworkNode())) {
					networkNode.setNetworkNode(null);
					networkNode.setNodeSet(false);
					networkNode.setHighlighted(NetworkNodeHighlight.UNSET);
				}
			});

			// Remove those nodes
			getNetworkNodes().removeAll(deletingNetworkNodes);
		});
		getBlocks().removeAll(selectedBlocks);
	}

	// Move
	public Block<?, ?> getMovingBlock() {
		return movingBlock;
	}

	public void setMovingBlock(Block<?, ?> movingBlock) {
		this.movingBlock = movingBlock;
	}

	public double getMovingBlockOffsetX() {
		return movingBlockOffsetX;
	}

	public void setMovingBlockOffsetX(double movingBlockOffsetX) {
		this.movingBlockOffsetX = movingBlockOffsetX;
	}

	public double getMovingBlockOffsetY() {
		return movingBlockOffsetY;
	}

	public void setMovingBlockOffsetY(double movingBlockOffsetY) {
		this.movingBlockOffsetY = movingBlockOffsetY;
	}
	
	public void unsetMovingBlock() {
		if (movingBlock != null) {
			movingBlock.getBlockView().setMoving(false);
			movingBlock = null;
		}
	}

	// Wire
	public boolean isWiring() {
		return wiring;
	}

	public void setWiring(boolean wiring) {
		this.wiring = wiring;
	}

	public NetworkNode<?> getWiringNetworkNode() {
		return wiringNetworkNode;
	}

	public void setWiringNetworkNode(NetworkNode<?> wiringNetworkNode) {
		this.wiringNetworkNode = wiringNetworkNode;
	}

	public double getWiringStartX() {
		return wiringStartX;
	}

	public void setWiringStartX(double wiringStartX) {
		this.wiringStartX = wiringStartX;
	}

	public double getWiringStartY() {
		return wiringStartY;
	}

	public void setWiringStartY(double wiringStartY) {
		this.wiringStartY = wiringStartY;
	}

	public double getWireX() {
		return wireX;
	}

	public void setWireX(double wireX) {
		this.wireX = wireX;
	}

	public double getWireY() {
		return wireY;
	}

	public void setWireY(double wireY) {
		this.wireY = wireY;
	}
	
	public NetworkNode<?> getClosestNetworkNode(double x, double y) {
		double maximumDistance = 16;
		NetworkNode<?> closestNetworkNode = null;
		double closestNetworkNodedistance = 0;
		for (NetworkNode<?> networkNode : getNetworkNodes()) {
			double dx = x - networkNode.getX();
			double dy = y - networkNode.getY();
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance <= maximumDistance && (closestNetworkNode == null || distance < closestNetworkNodedistance)) {
				closestNetworkNode = networkNode;
				distance = closestNetworkNodedistance;
			}
		}
		return closestNetworkNode;
	}

	public boolean isValidNodeForWiring(NetworkNode<?> networkNode) {
		return !networkNode.isNodeSet() && wiringNetworkNode.isAssignable(networkNode);
	}
}
