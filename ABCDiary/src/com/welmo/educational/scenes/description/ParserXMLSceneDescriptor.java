package com.welmo.educational.scenes.description;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.graphics.Typeface;

import com.welmo.educational.managers.ResourceDescriptorsManager;
import com.welmo.educational.managers.SceneDescriptorsManager;
import com.welmo.educational.scenes.description.tags.ScnTags;
import com.welmo.educational.utility.ScreenDimensionHelper;


public class ParserXMLSceneDescriptor extends DefaultHandler {

	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	private SceneDescriptorsManager			pSceneDescManager;
	private ScreenDimensionHelper			dimHelper=null;

	protected SceneDescriptor				pSceneDsc;
	protected SpriteDescriptor 				pSpriteDsc;
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
	}


	@Override
	// ===========================================================
	// * Cette méthode est appelée par le parser une et une seule
	// * fois au démarrage de l'analyse de votre flux xml.
	// * Elle est appelée avant toutes les autres méthodes de l'interface,
	// * à l'exception unique, évidemment, de la méthode setDocumentLocator.
	// * Cet événement devrait vous permettre d'initialiser tout ce qui doit
	// * l'être avant ledébut du parcours du document.
	// ===========================================================
	public void startDocument() throws SAXException {
		super.startDocument();
		pSceneDescManager = SceneDescriptorsManager.getInstance();
	}

	@Override
	/*
	 * Fonction étant déclenchée lorsque le parser trouve un tag XML
	 * C'est cette méthode que nous allons utiliser pour instancier un nouveau feed
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

			//add sprite object to parent scene
			pSceneDsc.scObjects.add(pSpriteDsc);										//add scene object to list of region in parent texture
			
			// Read the sprite
			pSpriteDsc.ID=0;
			pSpriteDsc.resourceName = new String(attributes.getValue(ScnTags.S_A_RESOURCE_NAME));
			pSpriteDsc.Parameters[ScnTags.S_A_POSITION_X_IDX] = dimHelper.parsPosition(ScreenDimensionHelper.X,attributes.getValue(ScnTags.S_A_POSITION_X));
			pSpriteDsc.Parameters[ScnTags.S_A_POSITION_Y_IDX] = dimHelper.parsPosition(ScreenDimensionHelper.Y,attributes.getValue(ScnTags.S_A_POSITION_Y));
			pSpriteDsc.Parameters[ScnTags.S_A_WIDTH_IDX] = dimHelper.parsLenght(ScreenDimensionHelper.W, attributes.getValue(ScnTags.S_A_WIDTH));
			pSpriteDsc.Parameters[ScnTags.S_A_HEIGHT_IDX] = dimHelper.parsLenght(ScreenDimensionHelper.H, attributes.getValue(ScnTags.S_A_HEIGHT));
			pSpriteDsc.type = SpriteDescriptor.SpritesTypes.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			return;	
		}
		if (localName.equalsIgnoreCase(ScnTags.S_ACTION)){
			if(this.pAction != null) //check if new action object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered action description with another action description inside");
			if(this.pSpriteDsc == null) //check if action is part of a sprite
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description withou sceneo description");
			
			//create new action and add it to the sprite description
			pAction = new Action();
			pSpriteDsc.actions.add(pAction);
			
			pAction.event=SpriteDescriptor.SpritesEvents.valueOf(attributes.getValue(ScnTags.S_A_EVENT));;
			pAction.type=SpriteDescriptor.ActionType.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			switch (SpriteDescriptor.ActionType.valueOf(attributes.getValue(ScnTags.S_A_TYPE))){
				case CHANGE_SCENE:
					pAction.NextScene=attributes.getValue(ScnTags.S_A_NEXT_SCENE);
					break;
				default:
					break;
			}
		}
			
		if (localName.equalsIgnoreCase(ScnTags.S_MODIFIER)){
			if(this.pModifier != null) //check if new action object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered action description with another action description inside");
			if(this.pSpriteDsc == null) //check if action is part of a sprite
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description withou sceneo description");
			
			//create new action and add it to the sprite description
			pModifier = new Modifier();
			pSpriteDsc.modifiers.add(pModifier);
			
			pModifier.event=SpriteDescriptor.SpritesEvents.valueOf(attributes.getValue(ScnTags.S_A_EVENT));;
			pModifier.type=SpriteDescriptor.ModifierType.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			switch (SpriteDescriptor.ModifierType.valueOf(attributes.getValue(ScnTags.S_A_TYPE))){
				case MOVE:
					pModifier.fMoveFactor=Float.parseFloat(attributes.getValue(ScnTags.S_A_MOVE_FACTOR));
					break;
				case SCALE:
					pModifier.fScaleFactor=Float.parseFloat(attributes.getValue(ScnTags.S_A_SCALE_FACTOR));
					break;
					
				default:
					break;
			}
		}
	}

	@Override
	// * Fonction étant déclenchée lorsque le parser à parsé
	// * l'intérieur de la balise XML La méthode characters
	// * a donc fait son ouvrage et tous les caractère inclus
	// * dans la balise en cours sont copiés dans le buffer
	// * On peut donc tranquillement les récupérer pour compléter
	// * notre objet currentFeed
	public void endElement(String uri, String localName, String name) throws SAXException {	
		
		if (localName.equalsIgnoreCase(ScnTags.S_SCENE))pSceneDsc = null;
		if (localName.equalsIgnoreCase(ScnTags.S_MULTIVIEWSCENE))pMultiViewSceneDsc = null;
		if (localName.equalsIgnoreCase(ScnTags.S_SPRITE))pSpriteDsc = null;
		if (localName.equalsIgnoreCase(ScnTags.S_ACTION))pAction = null; 
		
	}


	// * Tout ce qui est dans l'arborescence mais n'est pas partie
	// * intégrante d'un tag, déclenche la levée de cet événement.
	// * En général, cet événement est donc levé tout simplement
	// * par la présence de texte entre la balise d'ouverture et
	// * la balise de fermeture
	public void characters(char[] ch,int start, int length)	throws SAXException{
		String lecture = new String(ch,start,length);
		//if(buffer != null) buffer.append(lecture);
	}
}
