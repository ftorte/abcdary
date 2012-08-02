package com.welmo.educational.scenes.components;

public interface IActionLeastener {
	public static int CHANGE_SCENE = 1;
	public void onActionChangeScene(int actionType, String nextScene); // Sprite call this interface to inform parent that has been clicked
}