/*----------------------------------------------------------------------------------------*
 * SurfaceModel.java                                                                      *
 *                                                                                        *
 * Surface Plotter   version 1.10    14 Oct 1996                                          *
 *                   version 1.20     8 Nov 1996                                          *
 *                   version 1.30b1  17 May 1997                                          *
 *                   bug fixed       21 May 1997                                          *
 *                   version 1.30b2  18 Oct 2001                                          *
 *                                                                                        *
 * Copyright (c) Yanto Suryono <yanto@fedu.uec.ac.jp>                                     *
 *                                                                                        *
 * This program is free software; you can redistribute it and/or modify it                *
 * under the terms of the GNU Lesser General Public License as published by the                  *
 * Free Software Foundation; either version 2 of the License, or (at your option)         *
 * any later version.                                                                     *
 *                                                                                        *
 * This program is distributed in the hope that it will be useful, but                    *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or          *
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for               *
 * more details.                                                                          *
 *                                                                                        *
 * You should have received a copy of the GNU Lesser General Public License along                *
 * with this program; if not, write to the Free Software Foundation, Inc.,                *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA                                  *
 *            
Modified : Eric : remove every graphical stuff to get rid of Frame
 *----------------------------------------------------------------------------------------*/
package com.googlecode.surfaceplotter;

 


/**
 * The model used to display any surface in JSurface
 */

public interface SurfaceModel
{
	public static final String X_MIN_PROPERTY = "XMin";
	public static final String Y_MIN_PROPERTY = "YMin";
	public static final String Z_MIN_PROPERTY = "ZMin";
	public static final String X_MAX_PROPERTY = "XMax";
	public static final String Y_MAX_PROPERTY = "YMax";
	public static final String Z_MAX_PROPERTY = "ZMax";
	public static final String EXPECT_DELAY_PROPERTY = "ExpectDelay";
	public static final String BOXED_PROPERTY = "Boxed";
	public static final String MESH_PROPERTY = "Mesh";
	public static final String SCALE_BOX_PROPERTY = "ScaleBox";
	public static final String DISPLAY_Z_PROPERTY = "DisplayZ";
	public static final String DISPLAY_GRIDS_PROPERTY = "DisplayGrids";
	public static final String PLOT_FUNCTION_1_PROPERTY = "PlotFunction1";
	public static final String PLOT_FUNCTION_2_PROPERTY = "PlotFunction2";
	public static final String DATA_AVAILABLE_PROPERTY = "DataAvailable";
	public static final String DISPLAY_X_Y_PROPERTY = "DisplayXY";
	public static final String CALC_DIVISIONS_PROPERTY = "CalcDivisions";
	public static final String CONTOUR_LINES_PROPERTY = "ContourLines";
	public static final String DISP_DIVISIONS_PROPERTY = "DispDivisions";
	public static final String SURFACE_VERTEX_PROPERTY = "SurfaceVertex";
	public static final String AUTO_SCALE_Z_PROPERTY = "AutoScaleZ";
	public static final String PLOT_TYPE_PROPERTY = "PlotType";
	public static final String PLOT_COLOR_PROPERTY = "PlotColor";
	public static final String COLOR_MODEL_PROPERTY = "ColorModel";

	//TODO replace with enum
	//plot type constant 
	public static final int PLOT_TYPE_SURFACE=0;
	public static final int PLOT_TYPE_WIREFRAME=1;
	public static final int PLOT_TYPE_DENSITY=2;
	public static final int PLOT_TYPE_CONTOUR=3;

	//TODO replace with enums
	// plot color constant
	public static final int PLOT_COLOR_OPAQUE   	 = 0;
	public static final int PLOT_COLOR_SPECTRUM    = 1;
	public static final int PLOT_COLOR_DUALSHADE   = 2;
	public static final int PLOT_COLOR_GRAYSCALE   = 3;
	public static final int PLOT_COLOR_FOG		 = 4;
	
	//events
	public void addPropertyChangeListener(java.beans.PropertyChangeListener listener);
	public void addPropertyChangeListener(String propertyName, java.beans.PropertyChangeListener listener);
	public void removePropertyChangeListener(java.beans.PropertyChangeListener listener);
	public void removePropertyChangeListener(String propertyName, java.beans.PropertyChangeListener listener);
	public void addChangeListener(javax.swing.event.ChangeListener listener);
	public void removeChangeListener(javax.swing.event.ChangeListener listener);
	
	
	
	public SurfaceVertex[][] getSurfaceVertex();
	
	public Projector getProjector(); //project is kind of "point of view"
	
	
	public boolean isAutoScaleZ();
	public int getPlotType();
	public int getPlotColor();
	public int getCalcDivisions();
	public int getContourLines();
	public int getDispDivisions(); 
	public float getXMin();
	public float getYMin();
	public float getZMin();
	public float getXMax();
	public float getYMax();
	public float getZMax();
	public SurfaceColor getColorModel(); // not the right place, but JSurface does not work with any colorset, should be removed lately

	/**
	 * Determines whether the delay regeneration checkbox is checked.
	 *
	 * @return <code>true</code> if the checkbox is checked, 
	 *         <code>false</code> otherwise
	 */
	public boolean isExpectDelay();
	
	/**
	 * Determines whether to show bounding box.
	 *
	 * @return <code>true</code> if to show bounding box
	 */
	public boolean isBoxed();
	
	/**
	 * Determines whether to show x-y mesh.
	 *
	 * @return <code>true</code> if to show x-y mesh
	 */
	public boolean isMesh();
	/**
	 * Determines whether to scale axes and bounding box.
	 *
	 * @return <code>true</code> if to scale bounding box
	 */
	
	public boolean isScaleBox();
	
	/**
	 * Determines whether to show x-y ticks.
	 *
	 * @return <code>true</code> if to show x-y ticks
	 */
	public boolean isDisplayXY();
	/**
	 * Determines whether to show z ticks.
	 *
	 * @return <code>true</code> if to show z ticks
	 */
	public boolean isDisplayZ();
	/**
	 * Determines whether to show face grids.
	 *
	 * @return <code>true</code> if to show face grids
	 */
	public boolean isDisplayGrids();
	/**
	 * Determines whether the first function is selected.
	 *
	 * @return <code>true</code> if the first function is checked, 
	 *         <code>false</code> otherwise
	 */
	public boolean isPlotFunction1();
	
	/**
	 * Determines whether the first function is selected.
	 *
	 * @return <code>true</code> if the first function is checked, 
	 *         <code>false</code> otherwise
	 */
	
	public boolean isPlotFunction2();
	
	/**
	 * Sets data availability flag
	 */
	public boolean isDataAvailable();
		
	
}
