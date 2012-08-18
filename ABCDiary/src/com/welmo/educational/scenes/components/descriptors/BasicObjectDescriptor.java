package com.welmo.educational.scenes.components.descriptors;

public abstract class BasicObjectDescriptor{
	public interface IDimension{
		void setWidth(int w);
		void setHeight(int h);
		int getWidth();
		int getHeight();
	}
	public interface IPosition{
		void setX(int w);
		void setY(int h);
		int getX();
		int getY();
	}
	public interface IOrientation{
		void setOriantation(float angle);
		float getOriantation();
	}
	
	public int ID;
	protected int pX, pY;
	protected int width, height;
	protected float orientation;
	protected int alignement;

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
}
