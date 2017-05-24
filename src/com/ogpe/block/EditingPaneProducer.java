package com.ogpe.block;

import com.ogpe.observable.Callback;

import javafx.scene.Node;

@FunctionalInterface
public interface EditingPaneProducer {

	Node produceEditingPane(Block block, Callback redrawCallback);
	
}
