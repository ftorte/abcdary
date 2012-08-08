package com.welmo.educational.scenes.description;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.welmo.educational.managers.ResourceDescriptorsManager;
import com.welmo.educational.managers.SceneDescriptorsManager;
import com.welmo.educational.scenes.description.Events.Action;
import com.welmo.educational.scenes.description.Events.EventDescriptionsManager;
import com.welmo.educational.scenes.description.Events.Modifier;
import com.welmo.educational.scenes.description.tags.ScnTags;
import com.welmo.educational.utility.ScreenDimensionHelper;


public class ParserXMLSceneDescriptor extends DefaultHandler {

	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	private static final String TAG = "ParserXMLSceneDescriptor";
	
	private SceneDescriptorsManager			pSceneDescManager;
	private ScreenDimensionHelper			dimHelper=null;
	private EventDescriptionsManager		pEventDscMgr;

	protected SceneDescriptor				pSceneDsc;
	protected SpriteDescriptor 				pSpriteDsc;
	protected SpriteDescriptor 				pCompoundSpriteDsc;
	protected MultiViewSceneDescriptor		pMultiViewSceneDsc;
	protected Action		pAction;
	protected Modifier		pModifier;
	
	
	//--------------------------------------------------------
	
	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		super.processingInstruction(target, data);
	}

	public ParserXMLSceneDescriptor(Context ctx) {
		super();
		dimHelper = ScreenDimensionHelper.getInstance(ctx);
		pEventDscMgr = EventDescriptionsManager.getInstance();
	}


	@Override
	// ===========================================================
	// * Cette m�thode est appel�e par le parser une et une seule
	// * fois au d�marrage de l'analyse de votre flux xml.
	// * Elle est appel�e avant toutes les autres m�thodes de l'interface,
	// * � l'exception unique, �videmment, de la m�thode setDocumentLocator.
	// * Cet �v�nement devrait vous permettre d'initialiser tout ce qui doit
	// * l'�tre avant led�but du parcours du document.
	// ===========================================================
	public void startDocument() throws SAXException {
		super.startDocument();
		pSceneDescManager = SceneDescriptorsManager.getInstance();
	}

	@Override
	/*
	 * Fonction �tant d�clench�e lorsque le parser trouve un tag XML
	 * C'est cette m�thode que nous allons utiliser pour instancier un nouveau feed
	 */
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {	
		
		
		
		if (localName.equalsIgnoreCase(ScnTags.S_MULTIVIEWSCENE)){
			if(this.pMultiViewSceneDsc != null)
				throw new NullPointerException("ParserXMLSceneDescriptor encountered multiview scene description with another multiview scene description inside");
			pMultiViewSceneDsc = new MultiViewSceneDescriptor();

			// Read scene description
			pMultiViewSceneDsc.ID=0;
			pMultiViewSceneDsc.Name = new String(attributes.getValue(ScnTags.S_A_NAME));
			pSceneDescManager.addMVScene(pMultiViewSceneDsc.Name, pMultiViewSceneDsc);
			return;
		}
		if (localName.equalsIgnoreCase(ScnTags.S_SCENE)){
			if(this.pSceneDsc != null)
				throw new NullPointerException("ParserXMLSceneDescriptor encountered texture description with another texture description inside");
			pSceneDsc = new SceneDescriptor();

			//if inside multiview add scene to parent multiview descriptor
			if(this.pMultiViewSceneDsc != null){
				pMultiViewSceneDsc.scScenes.add(pSceneDsc);
			}
				
				
			// Read scene description
			pSceneDsc.ID=0;
			pSceneDsc.Name = new String(attributes.getValue(ScnTags.S_A_NAME));
			pSceneDescManager.addScene(pSceneDsc.Name, pSceneDsc);
			return;
		}
		if (localName.equalsIgnoreCase(ScnTags.S_SPRITE)){
			if(this.pSpriteDsc != null) //check if new scene object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description with another sceneobject description inside");
			
			if(this.pSceneDsc == null) //check if sceneobject is part of a scene
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description withou sceneo description");
			
			pSpriteDsc = new SpriteDescriptor();
			// Read the sprite
			pSpriteDsc.ID=0;
			pSpriteDsc.resourceName = new String(attributes.getValue(ScnTags.S_A_RESOURCE_NAME));
			Log.i(TAG,attributes.getValue(ScnTags.S_A_TYPE));
			pSpriteDsc.type = SpriteDescriptor.SpritesTypes.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			pSpriteDsc.Parameters[ScnTags.S_A_POSITION_X_IDX] = dimHelper.parsPosition(ScreenDimensionHelper.X,attributes.getValue(ScnTags.S_A_POSITION_X));
			pSpriteDsc.Parameters[ScnTags.S_A_POSITION_Y_IDX] = dimHelper.parsPosition(ScreenDimensionHelper.Y,attributes.getValue(ScnTags.S_A_POSITION_Y));
			pSpriteDsc.Parameters[ScnTags.S_A_WIDTH_IDX] = dimHelper.parsLenght(ScreenDimensionHelper.W, attributes.getValue(ScnTags.S_A_WIDTH));
			pSpriteDsc.Parameters[ScnTags.S_A_HEIGHT_IDX] = dimHelper.parsLenght(ScreenDimensionHelper.H, attributes.getValue(ScnTags.S_A_HEIGHT));

			//check if the sprite is part of a compound sprite and add it to it else add it to the scene
			if(pCompoundSpriteDsc != null)
				pCompoundSpriteDsc.coumpoundElements.add(pSpriteDsc);
			else
				pSceneDsc.scObjects.add(pSpriteDsc);										//add scene object to list of region in parent texture				
		}
		
		if (localName.equalsIgnoreCase(ScnTags.S_COMPOUND_SPRITE)){
			if(this.pCompoundSpriteDsc != null) //check if new compound sprite
				throw new NullPointerException("ParserXMLSceneDescriptor encountered compoundsprite description with another compoundsprite description inside");

			if(this.pSceneDsc == null) //check if compound is part of a scene
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description withou sceneo description");
			pCompoundSpriteDsc = new SpriteDescriptor();
			// Read the compound sprite parameters
			pCompoundSpriteDsc.ID=0;
			pCompoundSpriteDsc.type = SpriteDescriptor.SpritesTypes.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			//add compound sprite to scene
			pSceneDsc.scObjects.add(pCompoundSpriteDsc);			 
		}
			
		if (localName.equalsIgnoreCase(ScnTags.S_ACTION)){
			if(this.pAction != null) //check if new action object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered action description with another action description inside");
			if(this.pSpriteDsc == null && this.pCompoundSpriteDsc == null ) //check if action is part of a sprite
				throw new NullPointerException("ParserXMLSceneDescriptor encountered acton description withou sprite or compound sprite");
			
			//create new action
			pAction = new Action();
			
			// read type and init the correct parameter as per action type
			pAction.type=Action.ActionType.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			switch (Action.ActionType.valueOf(attributes.getValue(ScnTags.S_A_TYPE))){
				case CHANGE_SCENE:
					pAction.NextScene=attributes.getValue(ScnTags.S_A_NEXT_SCENE);
					break;
				default:
					break;
			}
			
			// add the new event to the event list for the related object
			if(this.pSpriteDsc != null)
				pEventDscMgr.addAction(EventDescriptionsManager.Events.valueOf(attributes.getValue(ScnTags.S_A_EVENT)),pSpriteDsc,pAction);
			else
				pEventDscMgr.addAction(EventDescriptionsManager.Events.valueOf(attributes.getValue(ScnTags.S_A_EVENT)),pCompoundSpriteDsc,pAction);
		}
			
		if (localName.equalsIgnoreCase(ScnTags.S_MODIFIER)){
			if(this.pModifier != null) //check if new action object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered action description with another action description inside");
			if(this.pSpriteDsc == null && this.pCompoundSpriteDsc == null ) //check if modifier is part of a sprite
				throw new NullPointerException("ParserXMLSceneDescriptor encountered modifier description withou sprite or compound sprite");
			
			//create new action and add it to the sprite description
			pModifier = new Modifier();
			//pSpriteDsc.modifiers.add(pModifier);
			
			//pModifier.event=SpriteDescriptor.SpritesEvents.valueOf(attributes.getValue(ScnTags.S_A_EVENT));;
			pModifier.type=Modifier.ModifierType.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			switch (Modifier.ModifierType.valueOf(attributes.getValue(ScnTags.S_A_TYPE))){
				case MOVE:
					pModifier.fMoveFactor=Float.parseFloat(attributes.getValue(ScnTags.S_A_MOVE_FACTOR));
					break;
				case SCALE:
					pModifier.fScaleFactor=Float.parseFloat(attributes.getValue(ScnTags.S_A_SCALE_FACTOR));
					break;
					
				default:
					break;
			}
			// add the new event to the event list for the related object
			if(this.pSpriteDsc != null)
				pEventDscMgr.addModifier(EventDescriptionsManager.Events.valueOf(attributes.getValue(ScnTags.S_A_EVENT)),pSpriteDsc,pModifier);
			else
				pEventDscMgr.addModifier(EventDescriptionsManager.Events.valueOf(attributes.getValue(ScnTags.S_A_EVENT)),pCompoundSpriteDsc,pModifier);
	
		}
	}

	@Override
	// * Fonction �tant d�clench�e lorsque le parser � pars�
	// * l'int�rieur de la balise XML La m�thode characters
	// * a donc fait son ouvrage et tous les caract�re inclus
	// * dans la balise en cours sont copi�s dans le buffer
	// * On peut donc tranquillement les r�cup�rer pour compl�ter
	// * notre objet currentFeed
	public void endElement(String uri, String localName, String name) throws SAXException {	
		
		if (localName.equalsIgnoreCase(ScnTags.S_SCENE))pSceneDsc = null;
		if (localName.equalsIgnoreCase(ScnTags.S_MULTIVIEWSCENE))pMultiViewSceneDsc = null;
		if (localName.equalsIgnoreCase(ScnTags.S_SPRITE))pSpriteDsc = null;
		if (localName.equalsIgnoreCase(ScnTags.S_ACTION))pAction = null; 
		if (localName.equalsIgnoreCase(ScnTags.S_MODIFIER))pModifier = null; 
		if (localName.equalsIgnoreCase(ScnTags.S_COMPOUND_SPRITE))pCompoundSpriteDsc = null;
		
	}


	// * Tout ce qui est dans l'arborescence mais n'est pas partie
	// * int�grante d'un tag, d�clenche la lev�e de cet �v�nement.
	// * En g�n�ral, cet �v�nement est donc lev� tout simplement
	// * par la pr�sence de texte entre la balise d'ouverture et
	// * la balise de fermeture
	public void characters(char[] ch,int start, int length)	throws SAXException{
		String lecture = new String(ch,start,length);
		//if(buffer != null) buffer.append(lecture);
	}
}
