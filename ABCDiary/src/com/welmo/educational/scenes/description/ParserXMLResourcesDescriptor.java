package com.welmo.educational.scenes.description;


import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.graphics.Typeface;

import com.welmo.educational.managers.ResourceDescriptorsManager;
import com.welmo.educational.scenes.description.SceneObjectDescriptor.SceneObjectTypes;
import com.welmo.educational.utility.ScreenDimensionHelper;


public class ParserXMLResourcesDescriptor extends DefaultHandler {

	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	private ResourceDescriptorsManager		pResDescManager;
	private ScreenDimensionHelper			dimHelper=null;

	//Containers for element description
	protected TextureDescriptor				pTextureDsc;
	protected FontDescriptor				pFontDsc;
	protected TextureRegionDescriptor		pTextureRegionDsc;
	protected ColorDescriptor				pColorDsc;
	
	//--------------------------------------------------------
	
	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		super.processingInstruction(target, data);
	}

	public ParserXMLResourcesDescriptor(Context ctx) {
		super();
		dimHelper = ScreenDimensionHelper.getInstance(ctx);
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
		pResDescManager = ResourceDescriptorsManager.getInstance();
	}

	@Override
	/*
	 * Fonction �tant d�clench�e lorsque le parser trouve un tag XML
	 * C'est cette m�thode que nous allons utiliser pour instancier un nouveau feed
	 */
	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {

		if (localName.equalsIgnoreCase(XMLTags.TEXTURE)){
	
			if(this.pTextureDsc != null)
				throw new NullPointerException("ParserXMLSceneDescriptor encountered texture description with another texture description inside");
			pTextureDsc = new TextureDescriptor();

			// Read texture description
			pTextureDsc.ID=0;
			pTextureDsc.Name = new String(attributes.getValue(XMLTags.NAME));
			pTextureDsc.Parameters[XMLTags.HEIGHT_IDX]=Integer.parseInt(attributes.getValue(XMLTags.HEIGHT));
			pTextureDsc.Parameters[XMLTags.WIDTH_IDX]=Integer.parseInt(attributes.getValue(XMLTags.WIDTH));
			
			pResDescManager.addTexture(pTextureDsc.Name, pTextureDsc);
			return;
		
		}
		if (localName.equalsIgnoreCase(XMLTags.FONT)){
			
			if(this.pFontDsc != null)
				throw new NullPointerException("ParserXMLSceneDescriptor encountered font description with another font description inside");
			pFontDsc = new FontDescriptor();

			// Read font description
			pFontDsc.ID=0;
			pFontDsc.Name = new String(attributes.getValue(XMLTags.NAME));
			
			/*int nattr = attributes.getLength();
			for (int i = 0; i < attributes.getLength(); i++){
				String attr = attributes.getValue(i);
				select(attr)
					case(XMLTags.TYPEFACE_FAMILY)*/
			
			String strVal1,strVal2;
			
			//read Typeface configuration
			if((strVal1 = attributes.getValue(XMLTags.TYPEFACE_FAMILY)) != null && (strVal2=attributes.getValue(XMLTags.TYPEFACE_STYLE)) != null)
					pFontDsc.TypeFace = Typeface.create(strVal1,Integer.parseInt(strVal2));
			
			//read anti-aliasing configuration
			if((strVal1 = attributes.getValue(XMLTags.ANTIALIASING)) != null)			
				pFontDsc.AntiAlias = Boolean.parseBoolean(strVal1);
			
			//read FontSize configuration
			if((strVal1 = attributes.getValue(XMLTags.FONT_SIZE)) != null)		
				pFontDsc.Parameters[XMLTags.FONT_SIZE_IDX]=Integer.parseInt(attributes.getValue(XMLTags.FONT_SIZE));
			
			//read FontColor configuration
			if((strVal1 = attributes.getValue(XMLTags.FONT_COLOR)) != null)		
				pFontDsc.Parameters[XMLTags.FONT_COLOR_IDX]=Integer.parseInt(attributes.getValue(XMLTags.FONT_COLOR));
		
			pResDescManager.addFont(pFontDsc.Name, pFontDsc);
			return;
		}
	
		if (localName.equalsIgnoreCase(XMLTags.TEXTUREREGION)){
		
			if(this.pTextureRegionDsc != null) //check is new texture region
				throw new NullPointerException("ParserXMLSceneDescriptor encountered texture description with another texture description inside");
			
			if(this.pTextureDsc == null) //check region is part of a texture
				throw new NullPointerException("ParserXMLSceneDescriptor encountered textureregion description withou texture description");
			
			pTextureRegionDsc = new TextureRegionDescriptor();

			pTextureRegionDsc.ID=0;
			pTextureRegionDsc.Name = new String(attributes.getValue(XMLTags.NAME));
			pTextureRegionDsc.filename = new String(attributes.getValue(XMLTags.FILE_NAME));
			pTextureRegionDsc.Parameters[XMLTags.HEIGHT_IDX]=dimHelper.parsLenght(ScreenDimensionHelper.H,attributes.getValue(XMLTags.HEIGHT));
			pTextureRegionDsc.Parameters[XMLTags.WIDTH_IDX]=dimHelper.parsLenght(ScreenDimensionHelper.W,attributes.getValue(XMLTags.WIDTH));
			pTextureRegionDsc.Parameters[XMLTags.POSITION_X_IDX]=dimHelper.parsPosition(ScreenDimensionHelper.X,attributes.getValue(XMLTags.POSITION_X));
			pTextureRegionDsc.Parameters[XMLTags.POSITION_Y_IDX]=dimHelper.parsPosition(ScreenDimensionHelper.Y,attributes.getValue(XMLTags.POSITION_Y));
			pTextureRegionDsc.textureName = new String(pTextureDsc.Name);					//add parent texture name to textureregion descriptor
			
			pResDescManager.addTextureRegion(pTextureRegionDsc.Name, pTextureRegionDsc);	//add textureregion to maps or texture region
			pTextureDsc.Regions.add(pTextureRegionDsc);										//add textureregion to list of region in parent texture
			return;
		}
		
		if (localName.equalsIgnoreCase(XMLTags.COLOR)){
			
			if(this.pColorDsc != null)
				throw new NullPointerException("ParserXMLSceneDescriptor encountered color description inside another color description inside");
			
			pColorDsc = new ColorDescriptor();

			// Read texture description
			pColorDsc.Name = new String(attributes.getValue(XMLTags.NAME));
			pColorDsc.Parameters[XMLTags.RED_IDX]=Integer.parseInt(attributes.getValue(XMLTags.RED), 16);
			pColorDsc.Parameters[XMLTags.GREEN_IDX]=Integer.parseInt(attributes.getValue(XMLTags.GREEN), 16);
			pColorDsc.Parameters[XMLTags.BLUE_IDX]=Integer.parseInt(attributes.getValue(XMLTags.BLUE), 16);
			
			pResDescManager.addColor(pColorDsc.Name, pColorDsc);
			return;
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

		if (localName.equalsIgnoreCase(XMLTags.TEXTURE)){
			pTextureDsc = null;
		}
		
		if (localName.equalsIgnoreCase(XMLTags.TEXTUREREGION)){
			pTextureRegionDsc = null;
		}
		
		if (localName.equalsIgnoreCase(XMLTags.COLOR)){
			pColorDsc = null;
		}
		
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
