package com.ogpe.blockx;

import com.ogpe.observable.Callback;

import javafx.scene.Node;

@FunctionalInterface
public interface EditingPaneProducer {

	Node produceEditingPane(Callback redrawCallback);
	
}
