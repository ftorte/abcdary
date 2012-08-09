package com.welmo.educational.scenes.components;

import java.util.List;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.shape.RectangularShape;

import com.welmo.educational.scenes.description.Events.Action;

public interface IActionOnSceneListener {
	public boolean onActionChangeScene(String nextSceneName); // Sprite call this interface to inform scene parentthat has been clicked
	public void onStick(IAreaShape currentShapeToStick, Action stickActionDescription);
}