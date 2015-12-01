package cn.edu.zju.hong;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class CustomPaintListener implements PaintListener{
	private Color color=null;
	private Point location=null;
	private Point size=null;
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color=color;
	}
	
	public Point getLocation(){
		return location;
	}
	
	public Point getSize(){
		return size;
	}
	
	public void setLocation(Point location){
		this.location=location;
	}
	
	public void setLocation(int x,int y){
		location=new Point(0,0);
		location.x=x;
		location.y=y;
	}
	
	public void setSize(Point size){
		this.size=size;
	}
	
	public void setSize(int x,int y){
		size=new Point(0,0);
		size.x=x;
		size.y=y;
	}
	
	@Override
	public void paintControl(PaintEvent e) {
		// TODO Auto-generated method stub
		GC gc=e.gc;
		gc.setForeground(color);
		if (location==null){
			if (size==null)
				gc.drawRectangle(e.x, e.y, e.width-1, e.height-1);
			else gc.drawRectangle(e.x, e.y, size.x, size.y);
		}
		else{
			if (size==null)
				gc.drawRectangle(location.x,location.y, e.width-1, e.height-1);
			else gc.drawRectangle(location.x, location.y, size.x,size.y);
		}
		gc=null;
		
	}

}

