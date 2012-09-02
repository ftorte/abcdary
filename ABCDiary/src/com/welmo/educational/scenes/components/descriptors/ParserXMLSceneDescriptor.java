package com.welmo.educational.scenes.components.descriptors;



import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import com.welmo.educational.managers.SceneDescriptorsManager;
import com.welmo.educational.scenes.components.Stick;
import com.welmo.educational.scenes.components.descriptors.BasicObjectDescriptor.Alignment;
import com.welmo.educational.scenes.components.descriptors.BasicObjectDescriptor.IDimension;
import com.welmo.educational.scenes.components.descriptors.BasicObjectDescriptor.IOrientation;
import com.welmo.educational.scenes.components.descriptors.BasicObjectDescriptor.IPosition;
import com.welmo.educational.scenes.description.tags.ScnTags;
import com.welmo.educational.scenes.events.descriptors.Action;
import com.welmo.educational.scenes.events.descriptors.EventDescriptionsManager;
import com.welmo.educational.scenes.events.descriptors.Modifier;
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
	protected SpriteObjectDescriptor 		pSpriteDsc;
	protected SpriteObjectDescriptor 		pCompoundSpriteDsc;
	protected MultiViewSceneDescriptor		pMultiViewSceneDsc;
	protected Action						pAction;
	protected Modifier						pModifier;
	protected TextObjectDescriptor			pTextDescriptor;
	
	protected BackGroundObjectDescriptor	pBackGroundDescriptor;
	
	
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
			pSceneDsc.name = new String(attributes.getValue(ScnTags.S_A_NAME));
			pSceneDescManager.addScene(pSceneDsc.name, pSceneDsc);
			
			return;
		}
		if (localName.equalsIgnoreCase(ScnTags.S_SPRITE)){
			if(this.pSpriteDsc != null) //check if new scene object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description with another sceneobject description inside");
			
			if(this.pSceneDsc == null) //check if sceneobject is part of a scene
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description withou sceneo description");
			
			pSpriteDsc = new SpriteObjectDescriptor();
			// Read the sprite
			pSpriteDsc.ID=Integer.parseInt(attributes.getValue(ScnTags.S_A_ID));
			pSpriteDsc.textureName = new String(attributes.getValue(ScnTags.S_A_RESOURCE_NAME));
			
			this.parseAttributesPosition(pSpriteDsc.getPosition(),attributes);
			this.parseAttributesDimensions(pSpriteDsc.getDimension(),attributes);
			this.parseAttributesOrientation(pSpriteDsc.getOriantation(),attributes);
			
			pSpriteDsc.type = SpriteObjectDescriptor.SpritesTypes.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			
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
			
			pCompoundSpriteDsc = new SpriteObjectDescriptor();
			
			// Read the compound sprite parameters
			pCompoundSpriteDsc.ID=Integer.parseInt(attributes.getValue(ScnTags.S_A_ID ));
			pCompoundSpriteDsc.type = SpriteObjectDescriptor.SpritesTypes.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			//manage position and dimensions
			this.parseAttributesPosition(pCompoundSpriteDsc.getPosition(),attributes);
			//add compound sprite to scene
			pSceneDsc.scObjects.add(pCompoundSpriteDsc);			 
		}
			
		if (localName.equalsIgnoreCase(ScnTags.S_ACTION)){
			if(this.pAction != null) //check if new action object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered action description with another action description inside");
			if(this.pSpriteDsc == null && this.pCompoundSpriteDsc == null ) //check if action is part of a sprite
				throw new NullPointerException("ParserXMLSceneDescriptor encountered acton description not in a sprite or compound sprite");
			
			//create new action
			pAction = new Action();
			
			// read type and init the correct parameter as per action type
			pAction.type=Action.ActionType.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			switch (Action.ActionType.valueOf(attributes.getValue(ScnTags.S_A_TYPE))){
				case CHANGE_SCENE:
					pAction.NextScene=attributes.getValue(ScnTags.S_A_NEXT_SCENE);
					break;
				case STICK:	
					pAction.stick_with=Integer.parseInt(attributes.getValue(ScnTags.S_A_STICK_WITH));
					pAction.stickMode=Stick.StickMode.valueOf(attributes.getValue(ScnTags.S_A_STICK_MODE));
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
				throw new NullPointerException("ParserXMLSceneDescriptor encountered modifier description not in a sprite or compound sprite");
			
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
		
		if (localName.equalsIgnoreCase(ScnTags.S_TEXT)){
			if(this.pTextDescriptor != null) //check if new action object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered text description with another text description inside");
	
			//create new action
			pTextDescriptor = new TextObjectDescriptor();
			
			// read type and init the correct parameter as per action type
			//  <text ID="3" resourceName="FontAndroid" message="TXT" type="STATIC"></text>
			  
			pTextDescriptor.ID=Integer.parseInt(attributes.getValue(ScnTags.S_A_ID ));
			
			pTextDescriptor.FontName = attributes.getValue(ScnTags.S_A_RESOURCE_NAME);
			pTextDescriptor.message = new String (attributes.getValue(ScnTags.S_A_MESSAGE));	
			pTextDescriptor.type=TextObjectDescriptor.TextTypes.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
			
			pTextDescriptor.colorName=  new String (attributes.getValue(ScnTags.S_A_COLOR));
			
			this.parseAttributesPosition(pTextDescriptor.getPosition(),attributes);
			this.parseAttributesOrientation(pTextDescriptor.getOriantation(),attributes);

			//check if the sprite is part of a compound sprite and add it to it else add it to the scene
			if(pCompoundSpriteDsc != null){
				pCompoundSpriteDsc.textElements.add(pTextDescriptor);
			}
			else {
				if(pSpriteDsc != null){
					pSpriteDsc.textElements.add(pTextDescriptor);
				}
				else {
					pSceneDsc.scText.add(pTextDescriptor);
				} 
			}
		} 
		
		if (localName.equalsIgnoreCase(ScnTags.S_BACKGROUND)){
			readBackGroudDescription(attributes);
		}
	}
	
	private void readBackGroudDescription(Attributes attributes){
		if(this.pBackGroundDescriptor != null) //check if new action object descriptor
			throw new NullPointerException("ParserXMLSceneDescriptor encountered background description with another background description inside");
		
		//create new action
		pBackGroundDescriptor = pSceneDsc.thebackGround;
		pBackGroundDescriptor.ID=Integer.parseInt(attributes.getValue(ScnTags.S_A_ID ));

		//pModifier.event=SpriteDescriptor.SpritesEvents.valueOf(attributes.getValue(ScnTags.S_A_EVENT));;
		pBackGroundDescriptor.type=BackGroundObjectDescriptor.BackGroundTypes.valueOf(attributes.getValue(ScnTags.S_A_TYPE));
		switch (pBackGroundDescriptor.type){
		case COLOR:
			pBackGroundDescriptor.color=attributes.getValue(ScnTags.S_A_COLOR);
			break;
		case SPRITE:
			//pBackGroundDescriptor.sprite = new SpriteObjectDescriptor();
			//TODO readSpriteObject()
			break;
		default:
			break;
		}		
	}
	
	private void parseAttributesPosition(IPosition pPosition,Attributes attributes){
		if((attributes.getValue(ScnTags.S_A_POSITION_X) != null) && (attributes.getValue(ScnTags.S_A_POSITION_Y) != null)){
			pPosition.setX(dimHelper.parsPosition(ScreenDimensionHelper.X,attributes.getValue(ScnTags.S_A_POSITION_X)));
			pPosition.setY(dimHelper.parsPosition(ScreenDimensionHelper.Y,attributes.getValue(ScnTags.S_A_POSITION_Y)));
		}
		else{
			pPosition.setX(0);
			pPosition.setY(0);
		}
		//read H Alignment
		if(attributes.getValue(ScnTags.S_A_H_ALIGNEMENT) != null){ 
			pPosition.setHorizontalAlignment(Alignment.valueOf(attributes.getValue(ScnTags.S_A_H_ALIGNEMENT)));
		}
		else{
			pPosition.setHorizontalAlignment(Alignment.NO_ALIGNEMENT);
		}
		//read V Alignment
		if(attributes.getValue(ScnTags.S_A_H_ALIGNEMENT) != null){ 
			pPosition.setVerticalAlignment(Alignment.valueOf(attributes.getValue(ScnTags.S_A_V_ALIGNEMENT)));
		}
		else{
			pPosition.setVerticalAlignment(Alignment.NO_ALIGNEMENT);
		}
	}
	
	private void parseAttributesDimensions(IDimension pDimensions,Attributes attributes){
		if((attributes.getValue(ScnTags.S_A_WIDTH) != null) && (attributes.getValue(ScnTags.S_A_WIDTH) != null)){
			pDimensions.setWidth(dimHelper.parsLenght(ScreenDimensionHelper.W, attributes.getValue(ScnTags.S_A_WIDTH)));
			pDimensions.setHeight(dimHelper.parsLenght(ScreenDimensionHelper.H, attributes.getValue(ScnTags.S_A_HEIGHT)));
		}
		else{
			pDimensions.setWidth(100);
			pDimensions.setHeight(100);
		}
	}
	private void parseAttributesOrientation(IOrientation pDimensions,Attributes attributes){
		
		if(attributes.getValue(ScnTags.S_A_ORIENTATION) != null)
			pDimensions.setOriantation(Float.parseFloat(attributes.getValue(ScnTags.S_A_ORIENTATION)));
		else
			pDimensions.setOriantation(0f);
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
		if (localName.equalsIgnoreCase(ScnTags.S_MODIFIER))pModifier = null; 
		if (localName.equalsIgnoreCase(ScnTags.S_COMPOUND_SPRITE))pCompoundSpriteDsc = null;
		if (localName.equalsIgnoreCase(ScnTags.S_TEXT))pTextDescriptor = null;
		if (localName.equalsIgnoreCase(ScnTags.S_BACKGROUND))pBackGroundDescriptor = null;
	}


	// * Tout ce qui est dans l'arborescence mais n'est pas partie
	// * intégrante d'un tag, déclenche la levée de cet événement.
	// * En général, cet événement est donc levé tout simplement
	// * par la présence de texte entre la balise d'ouverture et
	// * la balise de fermeture
	public void characters(char[] ch,int start, int length)	throws SAXException{
		String lecture = new String(ch,start,length);
	}
}
