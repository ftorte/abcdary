package com.welmo.educational.scenes;

import java.util.Arrays;

import org.andengine.engine.Engine;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;

import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.scenes.description.SceneDescriptor;
import com.welmo.educational.scenes.description.SceneObjectDescriptor;
import com.welmo.educational.scenes.description.XMLTags;


import android.content.Context;
import android.graphics.Color;

public class SceneLetter extends ManageableScene {


	// ===========================================================
	// Constants	
	// ===========================================================
	//Log & Debug & trace
	private static final String TAG = "SceneLetter";
	// ===========================================================
	// Temporary scene descriptor to build schene
	@Override
	public void loadScene(String SceneName, ResourcesManager res) {
		super.loadScene(SceneName, res);
	}

	// ===========================================================
	// Inner private classes to handle on click events
	// ===========================================================
	private class ClicalbeSpriteLeastener implements ClickableSprite.IClickLeastener{

		private int miItemClicked=-1;
		@Override
		public void onClick(int ObjectID) {
			miItemClicked = ObjectID;
		}
		@Override
		public void reset() {
			miItemClicked=-1;
		}
		@Override
		public int getObjectID(){
			return miItemClicked;
		}
	}
}
