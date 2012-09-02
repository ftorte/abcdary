package com.welmo.educational.scenes.components;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.welmo.educational.scenes.components.descriptors.SpriteObjectDescriptor;
import com.welmo.educational.scenes.components.descriptors.BasicObjectDescriptor.Alignment;
import com.welmo.educational.scenes.components.descriptors.TextObjectDescriptor;

public class TextComponent extends Text{
	// ===========================================================
	// Constants
	// ===========================================================
	//Log & Debug
	private static final String TAG = "ClickableSprite";

	// ===========================================================
	// Fields
	// ===========================================================
	private int nID											=-1;

	// ===========================================================
	// Constructors
	// ===========================================================
	public TextComponent(float pX, float pY, IFont pFont, CharSequence pText,
			TextOptions pTextOptions,
			VertexBufferObjectManager pTextVertexBufferObject) {
		super(pX, pY, pFont, pText, pTextOptions,
				pTextVertexBufferObject);
		// TODO Auto-generated constructor stub
	}
	
	public void configure(TextObjectDescriptor spDsc){
		nID = spDsc.getID();
		/* Setup Rotation*/
		setRotationCenter(getWidth()/2, getHeight()/2);
		setRotation(spDsc.getOriantation().getOriantation());
		//set position			
		setX(spDsc.getPosition().getX());
		setY(spDsc.getPosition().getY());
	}
	
	public void align(TextObjectDescriptor spDsc, IAreaShape theFather){
		//Setup horizontal Alignment
		Alignment alignment = spDsc.getPosition().getHorizzontalAlignment();
		if(alignment != Alignment.NO_ALIGNEMENT){
			switch(alignment){
				case LEFTH:
					this.setX(0);
					break;
				case RIGHT:
					setX(theFather.getWidth()-this.getWidth());
					break;
				case CENTER:
					setX(theFather.getWidth()/2-this.getWidth()/2);
					break;
				default:
					break;
			}
		}
		//Setup Vertical Alignment
		alignment = spDsc.getPosition().getVerticalAlignment();
		if(alignment != Alignment.NO_ALIGNEMENT){
			switch(alignment){
				case TOP:
					this.setY(0);
					break;
				case BOTTOM:
					setY(theFather.getHeight()-this.getHeight());
					break;
				case CENTER:
					setY(theFather.getHeight()/2-this.getHeight()/2);
					break;
				default:
					break;
			}
		}	
	}

}
