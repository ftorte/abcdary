package com.welmo.educational.scenes.components;

import java.util.List;

import org.andengine.input.touch.TouchEvent;

import com.welmo.educational.scenes.description.Events.Action;

public interface IActionOnSceneListener {
	public boolean onActionChangeScene(String nextScene); // Sprite call this interface to inform parent that has been clicked

	void onStick(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX,
			float pTouchAreaLocalY, Action pModifierList);
}