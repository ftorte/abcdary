package com.welmo.educational.scenes.components.descriptors;

public abstract class BasicObjectDescriptor{
	public enum Alignment {
	    NO_ALIGNEMENT, CENTER, LEFTH, TOP, BOTTOM, RIGHT
	}
	/* Inner Interfaces to:
	 * Manage Object Dimension => IDimension
	 * Manage Object Position => IPosition
	 * Manage Object Orientation => IOrientation
	 */
	public interface IDimension{
		void setWidth(int w);
		void setHeight(int h);
		int getWidth();
		int getHeight();
	}
	public interface IPosition{
		void setX(int w);
		void setY(int h);
		void setHorizontalAlignment(Alignment hA);
		void setVerticalAlignment(Alignment vA);
	
		int getX();
		int getY();
		Alignment getHorizzontalAlignment();
		Alignment getVerticalAlignment();
	}
	public interface IOrientation{
		void setOriantation(float angle);
		float getOriantation();
	}
	//----------------------------------------------------------------------//
	//Protected members
	protected int 			ID;
	protected int 			pX, pY;
	protected int 			width, height;
	protected float 		orientation;
	protected Alignment		horizzontalAlignment; 
	protected Alignment		verticalAlignment;
	//----------------------------------------------------------------------//
	// Public methods
	//----------------------------------------------------------------------//
	public IDimension getDimension(){
		return  new IDimension()  {  
			public void setWidth(int w){
				width=w;
			};
			public void setHeight(int h){
				height=h;
			};
			public int getWidth(){
				return width;
			}
			public int getHeight(){
				return height;
			}
		};
	}
	public IPosition getPosition(){
		return  new IPosition()  {  
			public void setX(int x){
				pX=x;
			};
			public void setY(int y){
				pY=y;
			};
			public int getX(){
				return pX;
			}
			public int getY(){
				return pY;
			}
			public Alignment getHorizzontalAlignment() {
				return horizzontalAlignment;
			}
			public void setHorizontalAlignment(Alignment alignement) {
				horizzontalAlignment = alignement;
			}
			public Alignment getVerticalAlignment() {
				return verticalAlignment;
			}
			public void setVerticalAlignment(Alignment alignement) {
				verticalAlignment = alignement;
			}
		};
	}
	public IOrientation getOriantation(){
		return  new IOrientation()  {  
			public void setOriantation(float angle){
				orientation=angle;
			};
			public float getOriantation(){
				return orientation;
			}
		};
	}
	public int getID(){
		return ID;
	}
}
