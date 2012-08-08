package com.welmo.educational.scenes;

import org.andengine.engine.Engine;

import com.welmo.educational.managers.SceneManager;

import android.content.Context;

public interface IManageableScene {
	public void loadScene(String SceneName);
	public void init(Engine theEngine, Context ctx);
	public void resetScene();
}
