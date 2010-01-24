package com.googlecode.surfaceplotter;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.SwingPropertyChangeSupport;

/**

 */
public class AbstractSurfaceModel implements SurfaceModel
{
	
	protected java.beans.PropertyChangeSupport property;
	public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {property.addPropertyChangeListener(listener);}
	public void addPropertyChangeListener(String propertyName, java.beans.PropertyChangeListener listener){property.addPropertyChangeListener(propertyName, listener);}
	public void removePropertyChangeListener(java.beans.PropertyChangeListener listener){property.removePropertyChangeListener(listener);}
	public void removePropertyChangeListener(String propertyName, java.beans.PropertyChangeListener listener){property.removePropertyChangeListener(propertyName, listener);}
	
	
	
	
	
	private Image sourceimage = null;
	
	private static final int INIT_CALC_DIV   = 20;
	private static final int INIT_DISP_DIV   = 20;
	
	private static final int MODES_COUNT     = 5;
	
	
	protected boolean autoScaleZ = true;
	public boolean isAutoScaleZ()	{return autoScaleZ;}
	public void setAutoScaleZ(boolean v)
	{
		boolean o = this.autoScaleZ;
		this.autoScaleZ = v;
		if (autoScaleZ) doAutoScale();
		
		
		property.firePropertyChange(AUTO_SCALE_Z_PROPERTY,o,v);
	}
	public void doToggleAutoScaleZ(){setAutoScaleZ(!isAutoScaleZ());}
	
	public void doAutoScale()
	{
		//compute  auto scale and repaint
		if (plotFunction1 && plotFunction2)
		{
			setZMin(Math.min(z1Min,z2Min));
			setZMax(Math.max(z1Max,z2Max));
		}
		else
		{
			if (plotFunction1) 
			{
				setZMin(z1Min);
				setZMax(z1Max);
			}
			if (plotFunction2)
			{
				setZMin(z2Min);
				setZMax(z2Max);
			}
		}
	}
	
	
	
	protected int plotType=0;
	public int getPlotType()	{return plotType;}
	public void setPlotType(int v)
	{
		int o = this.plotType;
		this.plotType = v;
		property.firePropertyChange(PLOT_TYPE_PROPERTY,o,v);
	}
	
	
	protected int plotColor;
	public int getPlotColor()	{return plotColor;}
	public void setPlotColor(int v)
	{
		int o = this.plotColor;
		this.plotColor = v;
		property.firePropertyChange(PLOT_COLOR_PROPERTY,o,v);
	}
	
	
	public void doSpectrumColor() {setPlotColor(PLOT_COLOR_SPECTRUM);}
	public void doGrayScaleColor() {setPlotColor(PLOT_COLOR_GRAYSCALE);}
	public void doDualShadeColor() {setPlotColor(PLOT_COLOR_DUALSHADE);}
	public void doOpaqueColor() {setPlotColor(PLOT_COLOR_OPAQUE);}
	public void doFogColor() {setPlotColor(PLOT_COLOR_FOG);}
	
	
	
	public void doPlotSurface() {setPlotType( PLOT_TYPE_SURFACE);}
	public void doPlotWireframe() {setPlotType( PLOT_TYPE_WIREFRAME);}
	public void doPlotDensity() {setPlotType( PLOT_TYPE_DENSITY);}
	public void doPlotContour() {setPlotType( PLOT_TYPE_CONTOUR);}
	
	protected SurfaceVertex[][] vertex;
	public SurfaceVertex[][] getSurfaceVertex()	{return vertex;}
	public void setSurfaceVertex(SurfaceVertex[][] v)
	{
		SurfaceVertex[][] o = this.vertex;
		this.vertex = v;
		property.firePropertyChange(SURFACE_VERTEX_PROPERTY,o,v);
	}
	
	Projector projector;
	public Projector getProjector()
	{
		if (projector==null)
		{
			projector=new Projector();
			projector.setDistance(70);
			projector.set2DScaling(15);
			projector.setRotationAngle(125);
			projector.setElevationAngle(10);
		}
		return projector;
	}
	
	protected int calcDivisions=INIT_CALC_DIV;
	public int getCalcDivisions()	{return calcDivisions;}
	public void setCalcDivisions(int v)
	{
		int o = this.calcDivisions;
		this.calcDivisions = v;
		property.firePropertyChange(CALC_DIVISIONS_PROPERTY,o,v);
	}
	
	protected int contourLines;
	public int getContourLines()	{return contourLines;}
	public void setContourLines(int v)
	{
		int o = this.contourLines;
		this.contourLines = v;
		property.firePropertyChange(CONTOUR_LINES_PROPERTY,o,v);
	}
	
	protected int dispDivisions=INIT_DISP_DIV;
	public void setDispDivisions(int v)
	{
		int o = this.dispDivisions;
		this.dispDivisions = v;
		property.firePropertyChange(DISP_DIVISIONS_PROPERTY,o,v);
	}
	public int getDispDivisions() 
	{
		if (dispDivisions > calcDivisions) dispDivisions = calcDivisions;
		while ((calcDivisions % dispDivisions) != 0) dispDivisions++;
		return dispDivisions;
	}
	
	
	protected float xMin;
	public float getXMin()	{return xMin;}
	public void setXMin(float v)
	{
		float o = this.xMin;
		this.xMin = v;
		property.firePropertyChange(X_MIN_PROPERTY,new Float(o),new Float(v));
	}
	protected float yMin;
	public float getYMin()	{return yMin;}
	public void setYMin(float v)
	{
		float o = this.yMin;
		this.yMin = v;
		property.firePropertyChange(Y_MIN_PROPERTY,new Float(o),new Float(v));
	}
	
	protected float zMin;
	public float getZMin()	{return zMin;}
	public void setZMin(float v)
	{
		if (v>=zMax) return;
		float o = this.zMin;
		this.zMin = v;
		property.firePropertyChange(Z_MIN_PROPERTY,new Float(o),new Float(v));
	}
	
	protected float xMax;
	public float getXMax()	{return xMax;}
	public void setXMax(float v)
	{
		float o = this.xMax;
		this.xMax = v;
		property.firePropertyChange(X_MAX_PROPERTY,new Float(o),new Float(v));
	}
	
	protected float yMax;
	public float getYMax()	{return yMax;}
	public void setYMax(float v)
	{
		float o = this.yMax;
		this.yMax = v;
		property.firePropertyChange(Y_MAX_PROPERTY,new Float(o),new Float(v));
	}
	
	protected float z1Max;//the max computed
	protected float z1Min;//the min computed
	protected float z2Max;//the max computed
	protected float z2Min;//the min computed
	
	
	
	protected float zMax;
	public float getZMax()	{return zMax;}
	public void setZMax(float v)
	{
		if (v<=zMin) return;
		float o = this.zMax;
		this.zMax = v;
		property.firePropertyChange(Z_MAX_PROPERTY,new Float(o),new Float(v));
	}
	
	public AbstractSurfaceModel() 
	{
		super();
		property= new SwingPropertyChangeSupport(this);
		setColorModel(new ColorModelSet());
	}
	
	public ColorModelSet colorModel;
	public ColorModelSet getColorModel()	{return colorModel;}
	protected void setColorModel(ColorModelSet v)
	{
		ColorModelSet o = this.colorModel;
		this.colorModel = v;
		property.firePropertyChange(COLOR_MODEL_PROPERTY,o,v);
	}
	
  /**
	 * Sets the text of status line
	 *
	 * @param text new text to be displayed
	 */
	
	public void setMessage(String text) 
	{
		//@todo
		//System.out.println("Message"+text);
	}
	
  /**
	 * Called when automatic rotation starts.
	 */
	
	public void rotationStarts() {
		
		//setting_panel.rotationStarts();
	}
	
  /**
	 * Called when automatic rotation stops
	 */
	
	public void rotationStops() {
		
		//setting_panel.rotationStops();
	}
	
	
	public void doExportCSV(File file) throws IOException
	{
		
			if (file==null) return;
			java.io.FileWriter w= new java.io.FileWriter(file);
			float   stepx, stepy, x, y, v;
			float   xi,xx,yi,yx;
			float   min, max;
			boolean f1, f2;
			int     i,j,k,total;
			
			f1=true;f2=true; // until no method is defined to set functions ...
			// image conversion
			
			int[]   pixels = null; 
			int     imgwidth = 0;
			int     imgheight = 0;
			
			
			try {
				xi = getXMin();
				yi = getYMin();
				xx = getXMax();
				yx = getYMax();
				if ((xi >= xx) || (yi >= yx)) throw new NumberFormatException();
			}
			catch(NumberFormatException e) {
				setMessage("Error in ranges"); 
				return;
			}
			
			
			
			calcDivisions = getCalcDivisions();
			//func1calc = f1; func2calc = f2;
			
			stepx = (xx - xi) / calcDivisions; 
			stepy = (yx - yi) / calcDivisions;
			
			total = (calcDivisions+1)*(calcDivisions+1);
			if (vertex == null) return;
			
			max = Float.NaN;
			min = Float.NaN;
			
			//		canvas.destroyImage();
			i = 0; j = 0; k = 0; x = xi; y = yi; 
			
			
			float xfactor = 20/(xx-xi);
			float yfactor = 20/(yx-yi);
			
			w.write("X\\Y->Z;");
			while (j <= calcDivisions) 
			{
				
				w.write(Float.toString(y));
				if (j!=calcDivisions) w.write(';');
				j++; y += stepy;
				k++;
			}
			w.write("\n");
			//first line written
			i = 0; j = 0; k = 0; x = xi; y = yi; 
			
			
			while (i <= calcDivisions) 
			{
				w.write(Float.toString(x));
				w.write(';');
				while (j <= calcDivisions) 
				{
					w.write(Float.toString(vertex[0][k].z));
					if (j!=calcDivisions) w.write(';');
					j++; y += stepy;
					k++;
					//setMessage("Calculating : " + k*100/total + "% completed");
				}
				w.write('\n');
				//first line written
				j = 0; y = yi; i++; x += stepx;
			}
			w.flush();
			w.close();
		
		
	}
	
	public Plotter newPlotter(int calcDivisions) 
	{
		setCalcDivisions(calcDivisions);
		return new PlotterImpl();
	}
	
	/**
	 * Parses defined functions and calculates surface vertices 
	 */
	class PlotterImpl implements Plotter
	{
		float   stepx, stepy;
		float   xi,xx,yi,yx;
		float   min1, max1,min2,max2;
		boolean f1, f2;
		int     i,j,total;
		
		int[]   pixels = null; 
		int     imgwidth = 0;
		int     imgheight = 0;
		float xfactor;
		float yfactor;
		
		int calcDivisions;
		
		long lastUpdate=-1;
		
		public PlotterImpl()
		{
			//reads the calcDivision that will be used
			calcDivisions = getCalcDivisions();
			setDataAvailable(false); // clean space
			total = (calcDivisions+1)*(calcDivisions+1); // compute total size
			f1=hasFunction1;f2=hasFunction2; // define the size of the plot
			vertex = allocateMemory(f1,f2,total); // allocate vertex
			setSurfaceVertex(vertex); // define as the current vertex
			setDataAvailable(true);
			min1=max1=min2=max2=Float.NaN;
			getProjector();
			try {
				xi = getXMin();
				yi = getYMin();
				xx = getXMax();
				yx = getYMax();
				if ((xi >= xx) || (yi >= yx)) throw new NumberFormatException();
			}
			catch(NumberFormatException e) {
				setMessage("Error in ranges"); 
				return;
			}
			stepx = (xx - xi) / calcDivisions; 
			stepy = (yx - yi) / calcDivisions;
			xfactor = 20/(xx-xi);
			yfactor = 20/(yx-yi);
			
			//fill the surface vertex with NaN
			for(int i=0;i<=calcDivisions;i++)
				for(int j=0;j<=calcDivisions;j++)
				{
					int k=i*(calcDivisions+1)+j;
					
					float x=getX(i);
					float y=getY(j);
					if (f1)
					{
						vertex[0][k] = new SurfaceVertex(projector,(x-xi)*xfactor-10,
							(y-yi)*yfactor-10,Float.NaN);
					}
					if (f2)
					{
						vertex[1][k] = new SurfaceVertex(projector, (x-xi)*xfactor-10,
							(y-yi)*yfactor-10,Float.NaN);
					}
				}
			
		}
		
		public int getWidth(){return calcDivisions+1;}
		public int getHeight(){return calcDivisions+1;}
		
		
		/**
		 * Get the x float value that can be used to compute the fonction at position i.
		 * @param i index 0<=i<=calcDivisions.
	
		 * @author Eric 
		 * @date vendredi 9 avril 2004 13:26:14
		 */
		public float getX(int i)
		{
			return xi+i*stepx;
		}
		
		/**
		 * Get the x float value that can be used to compute the fonction at position i.
		 * @param j index 0<=j<=calcDivisions.
	
		 * @author Eric
		 * @date vendredi 9 avril 2004 13:26:14
		 */
		public float getY(int j)
		{
			return yi+j*stepy;
		}
		
		/**
		 * Short concise description.
		 * .
		 * @param i index 0<=i<=calcDivisions.
		 * @param j index 0<=j<=calcDivisions.
		 * @param v value at that point.
		 * @see package.class
		
		 *****************************************************
		 * @author Eric
		 * @date vendredi 9 avril 2004 13:26:14
		 *****************************************************
		 */
		public void setValue(int i,int j, float v1, float v2)
		{
			//v contains the value, and i, j the coordinate in the array
			float x=getX(i);
			float y=getY(j);
			int k=i*(calcDivisions+1)+j;
			if (f1) {
				
				//v = compute(x,y);
				if (Float.isInfinite(v1)) v1 = Float.NaN;
				if (!Float.isNaN(v1)) {
					if (Float.isNaN(max1) || (v1 > max1)) max1 = v1; else
						if (Float.isNaN(min1) || (v1 < min1)) min1 = v1;
				}
				vertex[0][k] = new SurfaceVertex(projector, (x-xi)*xfactor-10,
					(y-yi)*yfactor-10,v1);
			}
			if (f2) {
				//v = (float)parser2.evaluate();
				if (Float.isInfinite(v2)) v2 = Float.NaN;
				if (!Float.isNaN(v2)) {
					if (Float.isNaN(max2) || (v2 > max2)) max2 = v2; else
						if (Float.isNaN(min2) || (v2 < min2)) min2 = v2;
				}
				vertex[1][k] = new SurfaceVertex(projector, (x-xi)*xfactor-10,
					(y-yi)*yfactor-10,v2);
			}
			z1Min=(float) floor(min1,2);
			z1Max=(float) ceil(max1,2);
			z2Min=(float) floor(min2,2);
			z2Max=(float) ceil(max2,2);
			
			
			// temp: to create an event! future: add a specific event (StateChanged, or someting else
			/**/
			long time=System.currentTimeMillis();
			if (time-lastUpdate > 500)
			{
				fireStateChanged();
				lastUpdate=time;
			}
			/**/
			
			
			/*
			if (!expectDelay && j==calcDivisions) change.fireStateChanged();
			if (expectDelay && k==total) change.fireStateChanged();
			*/
		}
	}
	
	public static synchronized double floor(double d, int digits)
	{
		if (d == 0) return d;
		long og = (long) Math.ceil(( Math.log(Math.abs(d)) / Math.log(10) )); // donnne
																				// l'ordre
																				// de
																				// grandeur
																				// de d
		// if (og<=0) og--;
		double factor = Math.pow(10, digits - og); // correspond � la mantisse
													// (10^-og) * d�calage de
													// 'digit' vers la droite
													// (10^digit
		double res = Math.floor(( d * factor )) / factor; // res contient le
															// nombre le plus
															// proche de d �
															// significantDigit
															// chiffres
															// significatifs
		// System.out.print("\n->"+d+"~"+res);// og="+og+"\n");
		return res;
	}

	public static synchronized double ceil(double d, int digits)
	{
		if (d == 0) return d;
		long og = (long) Math.ceil(( Math.log(Math.abs(d)) / Math.log(10) )); // donnne
																				// l'ordre
																				// de
																				// grandeur
																				// de d
		// if (og<=0) og--;
		double factor = Math.pow(10, digits - og); // correspond � la mantisse
													// (10^-og) * d�calage de
													// 'digit' vers la droite
													// (10^digit
		double res = Math.ceil(( d * factor )) / factor; // res contient le
															// nombre le plus
															// proche de d �
															// significantDigit
															// chiffres
															// significatifs
		// System.out.print("\n->"+d+"~"+res);// og="+og+"\n");
		return res;
	}

	
	
	public void refresh()
	{
		doAutoScale();
		doUpdate();
	}
	
	public void doUpdate()
	{
		fireStateChanged();
	}
	/**/
	/*
	public void run() 
	{
		float   stepx, stepy, x, y, v;
		float   xi,xx,yi,yx;
		float   min, max;
		boolean f1, f2;
		int     i,j,k,total;
		
		f1=plotFunction1;f2=plotFunction2; 
		// image conversion
		
		int[]   pixels = null; 
		int     imgwidth = 0;
		int     imgheight = 0;
		
		
		try {
			xi = getXMin();
			yi = getYMin();
			xx = getXMax();
			yx = getYMax();
			if ((xi >= xx) || (yi >= yx)) throw new NumberFormatException();
		}
		catch(NumberFormatException e) {
			setMessage("Error in ranges"); 
			return;
		}
		Thread.yield();
		calcDivisions = getCalcDivisions();
		setDataAvailable(false); 
		//func1calc = f1; func2calc = f2;
		
		stepx = (xx - xi) / calcDivisions; 
		stepy = (yx - yi) / calcDivisions;
		
		total = (calcDivisions+1)*(calcDivisions+1);
		
		vertex = allocateMemory(f1,f2,total);
		setSurfaceVertex(vertex);
		if (vertex == null) return;
		
		max = Float.NaN;
		min = Float.NaN;
		
		//		canvas.destroyImage();
		
		i = 0; j = 0; k = 0; x = xi; y = yi; 
		
		float xfactor = 20/(xx-xi);
		float yfactor = 20/(yx-yi);
		
		
		while (i <= calcDivisions) {
			while (j <= calcDivisions) {
				Thread.yield();
				if (f1) {
					//v = (float)parser1.evaluate();
					v = compute(x,y);
					if (Float.isInfinite(v)) v = Float.NaN;
					if (!Float.isNaN(v)) {
						if (Float.isNaN(max) || (v > max)) max = v; else
							if (Float.isNaN(min) || (v < min)) min = v;
					}
					vertex[0][k] = new SurfaceVertex((x-xi)*xfactor-10,
						(y-yi)*yfactor-10,v);
				}
				if (f2) {
					//v = (float)parser2.evaluate();
					v = compute2(x,y);
					if (Float.isInfinite(v)) v = Float.NaN;
					if (!Float.isNaN(v)) {
						if (Float.isNaN(max) || (v > max)) max = v; else
							if (Float.isNaN(min) || (v < min)) min = v;
					}
					vertex[1][k] = new SurfaceVertex((x-xi)*xfactor-10,
						(y-yi)*yfactor-10,v);
				}
				j++; y += stepy;
				k++;
				//setMessage("Calculating : " + k*100/total + "% completed");
			}
			j = 0; y = yi; i++; x += stepx;
		}
		
		
		//setting_panel.setMinimumResult(Float.toString(min));
		//setting_panel.setMaximumResult(Float.toString(max));
		zMMin=(float) floor(min,2);
		zMMax=(float) ceil(max,2);
		if (autoScaleZ)
		{
			zMin=zMMin;
			zMax=zMMax;
		}
		
		//canvas.setValuesArray(vertex);
		setDataAvailable(true);
		//canvas.repaint(); 
	}
	/**/
	
	/**
	 * Determines whether the delay regeneration checkbox is checked.
	 *
	 * @return <code>true</code> if the checkbox is checked, 
	 *         <code>false</code> otherwise
	 */
	protected boolean expectDelay = false;
	public boolean isExpectDelay()	{return expectDelay;}
	public void setExpectDelay(boolean v)
	{
		boolean o = this.expectDelay;
		this.expectDelay = v;
		property.firePropertyChange(EXPECT_DELAY_PROPERTY,o,v);
	}
	public void doToggleExpectDelay(){setExpectDelay(!isExpectDelay());}
	
	
	/**
	 * Determines whether to show bounding box.
	 *
	 * @return <code>true</code> if to show bounding box
	 */
	protected boolean boxed;
	public boolean isBoxed()	{return boxed;}
	public void setBoxed(boolean v)
	{
		boolean o = this.boxed;
		this.boxed = v;
		property.firePropertyChange(BOXED_PROPERTY,o,v);
	}
	public void doToggleBoxed(){setBoxed(!isBoxed());}
	
	
	/**
	 * Determines whether to show x-y mesh.
	 *
	 * @return <code>true</code> if to show x-y mesh
	 */
	protected boolean mesh;
	public boolean isMesh()	{return mesh;}
	public void setMesh(boolean v)
	{
		boolean o = this.mesh;
		this.mesh = v;
		property.firePropertyChange(MESH_PROPERTY,o,v);
	}
	public void doToggleMesh(){setMesh(!isMesh() );}
	/**
	 * Determines whether to scale axes and bounding box.
	 *
	 * @return <code>true</code> if to scale bounding box
	 */
	
	protected boolean scaleBox;
	public boolean isScaleBox()	{return scaleBox;}
	public void setScaleBox(boolean v)
	{
		boolean o = this.scaleBox;
		this.scaleBox = v;
		property.firePropertyChange(SCALE_BOX_PROPERTY,o,v);
	}
	public void doToggleScaleBox(){setScaleBox(!isScaleBox() );}
	
	/**
	 * Determines whether to show x-y ticks.
	 *
	 * @return <code>true</code> if to show x-y ticks
	 */
	protected boolean displayXY;
	public boolean isDisplayXY()	{return displayXY;}
	public void setDisplayXY(boolean v)
	{
		boolean o = this.displayXY;
		this.displayXY = v;
		property.firePropertyChange(DISPLAY_X_Y_PROPERTY,o,v);
	}
	public void doToggleDisplayXY(){setDisplayXY(!isDisplayXY() );}
	/**
	 * Determines whether to show z ticks.
	 *
	 * @return <code>true</code> if to show z ticks
	 */
	protected boolean displayZ;
	public boolean isDisplayZ()	{return displayZ;}
	public void setDisplayZ(boolean v)
	{
		boolean o = this.displayZ;
		this.displayZ = v;
		property.firePropertyChange(DISPLAY_Z_PROPERTY,o,v);
	}
	public void doToggleDisplayZ(){setDisplayZ(!isDisplayZ() );}
	/**
	 * Determines whether to show face grids.
	 *
	 * @return <code>true</code> if to show face grids
	 */
	protected boolean displayGrids;
	public boolean isDisplayGrids()	{return displayGrids;}
	public void setDisplayGrids(boolean v)
	{
		boolean o = this.displayGrids;
		this.displayGrids = v;
		property.firePropertyChange(DISPLAY_GRIDS_PROPERTY,o,v);
	}
	public void doToggleDisplayGrids(){setDisplayGrids(!isDisplayGrids() );}
	/**
	 * Determines whether the first function is selected.
	 *
	 * @return <code>true</code> if the first function is checked, 
	 *         <code>false</code> otherwise
	 */
	
	
	protected boolean hasFunction1=true;
	protected boolean plotFunction1=hasFunction1;
	public boolean isPlotFunction1()	{return plotFunction1;}
	public void setPlotFunction1(boolean v)
	{
		boolean o = this.plotFunction1;
		this.plotFunction1 = hasFunction1&&v;
		property.firePropertyChange(PLOT_FUNCTION_1_PROPERTY,o,v);
	}
	
	public void doTogglePlotFunction1()
	{
		setPlotFunction1(!isPlotFunction1() );
		
	}
	public void doTogglePlotFunction2()
	{
		setPlotFunction2(!isPlotFunction2() );
		
	}
	
	public void doSwapFunctions()
	{
		setPlotFunction1(!isPlotFunction1() );
		setPlotFunction2(!isPlotFunction2() );
		doAutoScale();
		
	}
	
	/**
	 * Determines whether the first function is selected.
	 *
	 * @return <code>true</code> if the first function is checked, 
	 *         <code>false</code> otherwise
	 */
	protected boolean hasFunction2=true;
	protected boolean plotFunction2=hasFunction2;
	public boolean isPlotFunction2()	{return plotFunction2;}
	public void setPlotFunction2(boolean v)
	{
		boolean o = this.plotFunction2;
		this.plotFunction2 =hasFunction2 && v;
		property.firePropertyChange(PLOT_FUNCTION_2_PROPERTY,o,v);
	}
	
	
	/**
	 * Processes menu events
	 *
	 * @param item the selected menu item
	 */
	
	/*
	warning : pour faire la config (dans un menu ou dans un panel !! il y 
		a plein de cas et de sous cas !
	autour de ces trois actions l�
	
			canvas.setContour(false);
			canvas.setDensity(false);
			canvas.stopRotation();
	*/	
	
	/**
	 * Allocates Memory
	 */
	
	private SurfaceVertex[][] allocateMemory(boolean f1, boolean f2, int total) {
		SurfaceVertex[][] vertex = null;
		
		// Releases memory being used    
		//canvas.setValuesArray(null);
		
		/* The following program:
		
			SurfaceVertex[][] vertex = new SurfaceVertex[2][];
		
			if (f1) vertex[0] = new SurfaceVertex[total];
			if (f2) vertex[1] = new SurfaceVertex[total];
			
			
			Didn't work with my Microsoft Internet Explorer v3.0b2.
			It resulted in a "java.lang.ArrayStoreException"  :(
			
			*/
		
		try {
			vertex = new SurfaceVertex[2][total];
			if (!f1) vertex[0] = null;
			if (!f2) vertex[1] = null;
		}
		catch(OutOfMemoryError e) {
			setMessage("Not enough memory");
		}
		catch(Exception e) {
			setMessage("Error: " + e.toString());
		}
		return vertex;
	} 
	
	/**
	 * Sets file name
	 */
	
	/**
	 * Sets data availability flag
	 */
	protected boolean dataAvailable;
	public boolean isDataAvailable()	{return dataAvailable;}
	private void setDataAvailable(boolean v)
	{
		boolean o = this.dataAvailable;
		this.dataAvailable = v;
		property.firePropertyChange(DATA_AVAILABLE_PROPERTY,o,v);
	}
	
	

	javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	
	protected void fireStateChanged() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		ChangeEvent e=null;
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==ChangeListener.class) {
				// Lazily create the event:
				if (e == null)
					e= new ChangeEvent(this);
				((ChangeListener)listeners[i+1]).stateChanged(e);
			}
		}
	}
	
	
	
	public void addChangeListener(ChangeListener ol)
	{
		listenerList.add(ChangeListener.class, ol);
	}
	public void removeChangeListener(ChangeListener ol)
	{
		listenerList.remove(ChangeListener.class, ol);
	}


	
}//end of class