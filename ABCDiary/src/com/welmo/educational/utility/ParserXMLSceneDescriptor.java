package com.welmo.educational.utility;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.welmo.educational.managers.ResourceDescriptorsManager;
import com.welmo.educational.utility.SceneObjectDescriptor.SceneObjectTypes;


public class ParserXMLSceneDescriptor extends DefaultHandler {

	
	//--------------------------------------------------------
	// XML TAGS List
	//--------------------------------------------------------
	private final String TEXTURE 			= "texture";
	private final String TEXTUREREGION		= "textureregion";
	private final String HEIGHT				= "height";
	private final String WIDTH				= "width";
	private final String POSITION_X			= "px";
	private final String POSITION_Y			= "py";
	
	private final String NAME				= "name";
	
	private final String SCENE 				= "scene";
	private final String SCENE_OBJECT 		= "sceneobject";
	private final String SCENE_TYPE 		= "type";
	private final String RESOURCE_NAME 		= "resourceName";

			
	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	private ResourceDescriptorsManager		pResDescManager;

	//Containers for element description
	protected TextureDescriptor				pTextureDsc;
	protected TextureRegionDescriptor		pTextureRegionDsc;
	protected SceneDescriptor				pSceneDsc;
	protected SceneObjectDescriptor			pSceneObjectDsc;
	
	//--------------------------------------------------------
	
	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		super.processingInstruction(target, data);
	}

	public ParserXMLSceneDescriptor() {
		super();
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
		pResDescManager = ResourceDescriptorsManager.getInstance();
	}

	@Override
	/*
	 * Fonction étant déclenchée lorsque le parser trouve un tag XML
	 * C'est cette méthode que nous allons utiliser pour instancier un nouveau feed
	 */
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {

		if (localName.equalsIgnoreCase(TEXTURE)){
	
			if(this.pTextureDsc != null)
				throw new NullPointerException("ParserXMLSceneDescriptor encountered texture description with another texture description inside");
					pTextureDsc = new TextureDescriptor();

			// Read texture description
			pTextureDsc.ID=0;
			pTextureDsc.Name = new String(attributes.getValue(NAME));
			pTextureDsc.Parameters[0]=Integer.parseInt(attributes.getValue(HEIGHT));
			pTextureDsc.Parameters[1]=Integer.parseInt(attributes.getValue(WIDTH));
			
			pResDescManager.addTexture(pTextureDsc.Name, pTextureDsc);
			return;
		}
		if (localName.equalsIgnoreCase(TEXTUREREGION)){
		
			if(this.pTextureRegionDsc != null) //check is new texture region
				throw new NullPointerException("ParserXMLSceneDescriptor encountered texture description with another texture description inside");
			
			if(this.pTextureDsc == null) //check region is part of a texture
				throw new NullPointerException("ParserXMLSceneDescriptor encountered textureregion description withou texture description");
			
			pTextureRegionDsc = new TextureRegionDescriptor();

			pTextureRegionDsc.ID=0;
			pTextureRegionDsc.Name = new String(attributes.getValue(NAME));
			pTextureRegionDsc.Parameters[0]=Integer.parseInt(attributes.getValue(HEIGHT));
			pTextureRegionDsc.Parameters[1]=Integer.parseInt(attributes.getValue(WIDTH));
			pTextureRegionDsc.Parameters[2]=Integer.parseInt(attributes.getValue(this.POSITION_X));
			pTextureRegionDsc.Parameters[3]=Integer.parseInt(attributes.getValue(this.POSITION_Y));
			pTextureRegionDsc.textureName = new String(pTextureDsc.Name);					//add parent texture name to textureregion descriptor
			
			
			pResDescManager.addTextureRegion(pTextureRegionDsc.Name, pTextureRegionDsc);	//add textureregion to maps or texture region
			pTextureDsc.Regions.add(pTextureRegionDsc);										//add textureregion to list of region in parent texture
			return;
		}
		if (localName.equalsIgnoreCase(SCENE)){
			if(this.pSceneDsc != null)
				throw new NullPointerException("ParserXMLSceneDescriptor encountered texture description with another texture description inside");
			pSceneDsc = new SceneDescriptor();

			// Read scene description
			pSceneDsc.ID=0;
			pSceneDsc.Name = new String(attributes.getValue(NAME));
			pResDescManager.addScene(pSceneDsc.Name, pSceneDsc);
			return;
		}
		if (localName.equalsIgnoreCase(SCENE_OBJECT)){
			if(this.pSceneObjectDsc != null) //check is new scene object descriptor
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description with another sceneobject description inside");
			
			if(this.pSceneDsc == null) //check region is part of a texture
				throw new NullPointerException("ParserXMLSceneDescriptor encountered sceneobject description withou sceneo description");
			
			pSceneObjectDsc = new SceneObjectDescriptor();

			
			// Read scene description
			pSceneObjectDsc.ID=0;
			pSceneObjectDsc.resourceName = new String(attributes.getValue(RESOURCE_NAME));
			pSceneObjectDsc.type = SceneObjectTypes.valueOf(attributes.getValue(SCENE_TYPE));
			pSceneObjectDsc.Parameters[0] = Integer.parseInt(attributes.getValue(this.POSITION_X));
			pSceneObjectDsc.Parameters[1] = Integer.parseInt(attributes.getValue(this.POSITION_Y));
			pSceneObjectDsc.Parameters[2] = Integer.parseInt(attributes.getValue(this.WIDTH));
			pSceneObjectDsc.Parameters[3] = Integer.parseInt(attributes.getValue(this.HEIGHT));

			pSceneDsc.scObjects.add(pSceneObjectDsc);										//add scene object to list of region in parent texture
			return;
			
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

		if (localName.equalsIgnoreCase(TEXTURE)){
			pTextureDsc = null;
		}
		
		if (localName.equalsIgnoreCase(TEXTUREREGION)){
			pTextureRegionDsc = null;
		}
		if (localName.equalsIgnoreCase(SCENE)){
			pSceneDsc = null;
		}
		if (localName.equalsIgnoreCase(SCENE_OBJECT)){
			pSceneObjectDsc = null;
		}
		
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
