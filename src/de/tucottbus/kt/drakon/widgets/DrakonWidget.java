package de.tucottbus.kt.drakon.widgets;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.DrakonChart;

/**
 * The abstract superclass of all Drakon widgets.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public abstract class DrakonWidget extends Composite
{
  protected static final int LINE_WIDTH = 1;
  
  // FIXME: Colors not released!
  protected static final Color cGrid = new Color(Display.getDefault(),126,113,177);
  protected static final Color cHoverBg = new Color(Display.getDefault(),237,235,246);
  protected static final Color cHoverFg = new Color(Display.getDefault(),126,113,177);
  protected static Font  fText = null;

  private int style = DRAKON.NONE;
  
  private int width = -1;
  
  private int height = -1;
  
  private String text = null;
  
  private boolean hover = false;
  
  private boolean collapsed = false;
  
  /**
   * Creates a new Drakon widget.
   * 
   * @param parent
   *          The parent.
   * @param style
   *          The style. See documentation of derived classes for applicable
   *          style constants.
   * @throws IllegalArgumentException
   *          If the parent is a {@link DrakonIcon}.
   */
  public DrakonWidget(Composite parent, int style)
  {
    super(parent, DRAKON.SWT_COMPOSITE_STYLE);
    if (parent instanceof DrakonIcon)
      throw new IllegalArgumentException("A Drakon icon cannot have children.");

    this.style = style;
    
    addPaintListener(new PaintListener()
    {
      public void paintControl(PaintEvent e)
      {
        drawOn(e.gc,0,0,1);
      }
    });
    addMouseTrackListener(new MouseTrackListener()
    {
      @Override
      public void mouseHover(MouseEvent e)
      {
        // HACK: mouseEnter event sometimes missing when moving cursor slowly
        // -->   Wait for hover event to catch up
        setHover(true);
        getChart().setInserting(true);
      }
      
      @Override
      public void mouseExit(MouseEvent e)
      {
        setHover(false);
        getChart().setInserting(false);
      }
      
      @Override
      public void mouseEnter(MouseEvent e)
      {
        setHover(true);
        getChart().setInserting(true);
      }
    });
    addMouseListener(new MouseListener()
    {
      @Override
      public void mouseUp(MouseEvent e)
      {
        // Left button with no modifiers
        if (e.button==1 && (e.stateMask&SWT.MODIFIER_MASK)==0)
        {
          // Click on twister
          if (twisterHitTest(e.x,e.y))
          {
            if (hasTwister()>0)
              DrakonWidget.this.setCollapsed(false);
            else
              ((DrakonWidget)getParent()).setCollapsed(true);
          }
          // TODO: else if (insertPointHitTest())
          // Click elsewhere
          else
          {
            // TODO: setSelected(...)
          }
        }
      }
      
      @Override
      public void mouseDown(MouseEvent e)
      {
      }
      
      @Override
      public void mouseDoubleClick(MouseEvent e)
      {
      }
    });
  }
  
  // -- Getters and setters --

  @Override
  public int getStyle()
  {
    return style;
  }
  
  /**
   * Changes the style of this widget.
   * 
   * @param style
   *          The new style.
   */
  public void setStyle(int style)
  {
    this.style = style;
  }
  
  /**
   * Determines if this widget visualizes a piece of the control path.
   */
  public abstract boolean isControlPath();

  /**
   * Determines if this widget is a composite. A composite widget does no
   * drawing but acts a container for other widgets.  
   */
  public boolean isComposite()
  {
    return getChildren().length>0;
  }

  /**
   * Sets the widget text.
   * 
   * @param text
   *          The new text.
   */
  public void setText(String text)
  {
    if (this.text==null && text==null) return;
    if (this.text!=null && this.text.equals(text)) return;
    this.text = text;
    setToolTipText(text);
    redraw();
  }
  
  /**
   * Returns the widget text.
   */
  public String getText()
  {
    return text;
  }

  /**
   * Sets the icon size of this widget.
   * 
   * @param w
   *          The width in chart units, negative values set the default width
   *          {@link #getChart()}<code>.</code>{@link DrakonChart#getDefaultIconSize()
   *          getDefaultIconSize()}<code>.x</code>.
   * @param h
   *          -- <em>reserved</em> -- The height in chart units, negative values
   *          set the default height {@link #getChart()}<code>.</code>{@link
   *          DrakonChart#getDefaultIconSize() getDefaultIconSize()}<code>.y</code>.
   */
  public void setIconSize(int w, int h)
  {
    width  = w>=0?w:getChart().getDefaultIconSize().x;
    height = h>=0?h:getChart().getDefaultIconSize().y;
  }

  /**
   * Returns the icon size of this widget chart units. Icons shall draw 
   * their principal content within these dimension. Beyond that up to the
   * actual actual dimensions as returned by {@link #getActualSize()} only
   * drawing of connecting lines is allowed.
   * 
   * @see #getActualSize()
   */
  public Point getIconSize()
  {
    return new Point
    (
      width >=0?width :getChart().getDefaultIconSize().x,
      height>=0?height:getChart().getDefaultIconSize().y
    );
  }

  /**
   * Returns the default icon size. The method returns the result of
   * {@link #getChart()}<code>.</code>{@link DrakonChart#getDefaultIconSize() 
   * getDefaultIconSize()}.
   * 
   * @return A point containing the default width and height in chart units.
   */
  public Point getDefaultIconSize()
  {
    return getChart().getDefaultIconSize();
  }
  
  /**
   * Returns the actual size of this widget in chart units.
   * 
   * @see #getIconSize()
   */
  public Point getActualSize()
  {
    float chartScale = getChart().getScale();
    Rectangle clientArea = getClientArea();
    return new Point
    (
 
      (int)Math.ceil(clientArea.width / chartScale),
      (int)Math.ceil(clientArea.height / chartScale)
    );
  }
  
  /**
   * Sets the hovered state of this widget's select group.
   * 
   * @param hover
   *          The new hovered state.
   * @see #getSelectGroup()
   */
  public void setHover(boolean hover)
  {
    for (DrakonWidget widget : getSelectGroup())
      widget.setHoverInt(hover);
  }
  
  /**
   * Determines the hovered state of this widget.
   */
  public boolean getHover()
  {
    return hover;
  }
  
  /**
   * -- <em>internal use</em> -- Sets the hovered state of this widget alone.
   * 
   * @param hover
   *          The new hovered state.
   */
  private void setHoverInt(boolean hover)
  {
    if (this.hover==hover) return;
    this.hover = hover;
    redraw();
  }

  /**
   * Determines whether this widget can be collapsed into an icon.
   */
  public abstract boolean canCollapse();
  
  /**
   * Collapses this macro.
   * 
   * <p>NOTE: The method does nothing if {@link #canCollapse()} returns#
   * <code>false</code>.</p>
   * 
   * @param collapsed
   *          The new collapsed state.
   */
  public void setCollapsed(boolean collapsed)
  {
    if (!canCollapse()) collapsed = false;
    if (this.collapsed==collapsed) return;
    boolean visible = !(this.collapsed = collapsed);
    for (Control control : getChildren())
      control.setVisible(visible);
    getChart().pack();
  }
  
  /**
   * Determines whether this macro is collapsed.
   */
  public boolean isCollapsed()
  {
    return collapsed;
  }
  
  // -- Widget tree and layout --
  
  /**
   * Returns the root node of the DRAKON chart.
   */
  public final DrakonChart getChart()
  {
    if (getParent() instanceof DrakonWidget)
      return ((DrakonWidget)getParent()).getChart();
    return (DrakonChart)getParent();
  }
  
  /**
   * Returns the array of widgets to be selected and highlighted together with
   * this widget. Selection groups are used for GUI interactions with control
   * paths.
   * 
   * @return An array of {@link DrakonWidgets}s. Can be empty if this widget is
   * a composite (see {@link #isComposite()}). Otherwise the array contains at
   * least this widget.
   */
  public final DrakonWidget[] getSelectGroup()
  {
    if (isCollapsed() || !isControlPath())
      return new DrakonWidget[]{ this };
    
    ArrayList<DrakonWidget> group = new ArrayList<DrakonWidget>();
    for (Control control : getParent().getChildren())
    {
      if (!(control instanceof DrakonWidget)) continue;
      DrakonWidget widget = (DrakonWidget)control;
      if (widget.isControlPath())
        group.add(widget);
    }
    return group.toArray(new DrakonWidget[group.size()]);
  }

  /**
   * Determines if this widget has a collapse/expand twister.
   * 
   * @return -1: collapse twister, 1: expand twister, or 0: no twister.
   */
  public abstract int hasTwister();
  
  /**
   * Returns grid data to be used with icons (and also macros).
   * 
   * @param spanColumns
   *          Number of columns to span.
   * @param spanRows
   *          Number of rows to span.
   */
  public static GridData getGridData(int spanColumns, int spanRows)
  {
    GridData gd = new GridData();
    gd.horizontalSpan = spanColumns;
    gd.verticalSpan = spanRows;
    gd.horizontalAlignment = SWT.FILL;
    gd.verticalAlignment = SWT.FILL;
    gd.grabExcessVerticalSpace = true;
    return gd;
  }
  
  /**
   * Optimizes the chart layout.
   * 
   * @param all
   *          Optimize layout of this widget and all children.
   */
  public void optimizeLayout(boolean all)
  {
    if (all)
      for (Control control : getChildren())
        if (control instanceof DrakonWidget)
          ((DrakonWidget)control).optimizeLayout(all);
    layout();
    pack();
  }
  
  // -- Geometry --

  public int U2Px(float units)
  {
    return (int)(getChart().getScale()*units + .5f);
  }

  protected boolean twisterHitTest(int x, int y)
  {
    return hasTwister()!=0 && x>=0 && x<U2Px(12) && y>=0 && y<U2Px(12);
  }
  
  // -- Drawing --
  
  /**
   * Widget specific drawing to be implemented by subclasses.
   * 
   * @param gc
   *          The graphics context to draw on.
   */
  protected abstract void drawIconOn(GC gc);

  /**
   * Draws the widget.
   * 
   * @param gc
   *          The graphics context to draw on.
   */
  protected final void drawOn(GC gc, int offsetx, int offsety, float scale)
  {
    // TODO: Commit a transform as parameter?
    if (!isVisible() || (isComposite() && !isCollapsed())) return;
    if (hover)
    {
      gc.setBackground(cHoverBg);
      Rectangle clientArea = getClientArea();
      gc.fillRectangle(clientArea.x, clientArea.y,
          clientArea.width, clientArea.height);
    }
    DrakonChart chart = getChart();
    if (chart.isGridVisible())
    {
      gc.setForeground(cGrid);
      gc.setLineStyle(SWT.LINE_DOT);
      Rectangle clientArea = getClientArea();
      gc.drawRectangle(clientArea.x, clientArea.y,
          clientArea.width, clientArea.height);
    }
    
    Device device = gc.getDevice();
    
    final Color systemBlack = device.getSystemColor(SWT.COLOR_BLACK);
    final Color systemWhite = device.getSystemColor(SWT.COLOR_WHITE);
        
    gc.setForeground(hover ? cHoverFg : systemBlack);    
    gc.setBackground(systemWhite);
    gc.setAntialias(SWT.ON);
    gc.setLineCap(SWT.CAP_ROUND);
    gc.setLineJoin(SWT.JOIN_ROUND);
    gc.setLineStyle(SWT.LINE_SOLID);
    Transform t = new Transform(device);
    t.translate(offsetx*scale, offsety*scale);
    t.scale(chart.getScale()*scale,chart.getScale()*scale);
    gc.setTransform(t);
    gc.setLineWidth(LINE_WIDTH);
    
    // Draw icon & text
    DrakonWidget.this.drawIconOn(gc);

    // Draw expand/collapse button
    gc.setBackground(systemWhite);
    gc.setForeground(cHoverFg);
    gc.setLineWidth(LINE_WIDTH);
    int hasTwister = hasTwister();
    if (hasTwister>0 || (hover && hasTwister!=0))
    {
      gc.fillOval(0,0,12,12);
      gc.drawOval(2,2,8,8);
      gc.drawLine(2,6,10,6);
      if (hasTwister>0)
        gc.drawLine(6,2,6,10);
    }
    
    // Draw insert points
    if (getChart().isInserting())
    {
      gc.setBackground(cHoverFg);
      gc.setForeground(cHoverFg);
      int wHalf  = getIconSize().x/2;
      int hh = getActualSize().y;
      int style = getStyle();
      if ((style&DRAKON.INSERT_N)!=0)
        drawInsertPointOn(gc,wHalf,0);
      if ((style&DRAKON.INSERT_S)!=0)
        drawInsertPointOn(gc,wHalf,hh);
    }

    // Clean up
    gc.setTransform(null);
    t.dispose();
  }

  /**
   * Draws an insert point.
   * 
   * @param gc
   *          The graphics context.
   * @param x
   *          The x coordinate (in chart units) of the insert point.
   * @param y
   *          The y coordinate (in chart units) of the insert point.
   */
  protected final void drawInsertPointOn(GC gc, int x, int y)
  {
    gc.drawOval(x-3,y-3,6,6);
    gc.fillOval(x-3,y-3,6,6);
  }

  /**
   * Draws a string.
   * 
   * @param gc
   *          The graphics context.
   * @param s
   *          The string to draw.
   * @param x
   *          The x coordinate (in chart units) of the center of the rectangular
   *          area where the string is to be drawn.
   * @param y
   *          The y coordinate (in chart units) of the center of the rectangular
   *          area where the string is to be drawn.
   * @param maxWidth
   *          The maximal width of the rendered string in chart units.
   */
  protected void drawCenteredStringOn(GC gc, String s, int x, int y, int maxWidth)
  {
    if (s==null || s.length()==0) return;
        
    if (fText==null)
    {
      final String fname = gc.getDevice().getSystemFont().getFontData()[0].getName();
      // FIXME: Font not released!
      fText = new Font(Display.getDefault(),fname, 7, SWT.NORMAL);
    }
    gc.setFont(fText);
    final Color systemBlack = gc.getDevice().getSystemColor(SWT.COLOR_BLACK);
    gc.setForeground(hover ? cHoverFg : systemBlack);
    
    s = abbreviateString(gc,s,maxWidth);
    Point stringExtend = gc.stringExtent(s);
    gc.drawString(s,x-stringExtend.x/2,y-stringExtend.y/2,true);
  }

  /**
   * Recursively redraw this widget and all children.
   */
  public void redrawAll()
  {
    redraw();
    for (Control control : getChildren())
      if (control instanceof DrakonWidget)
        ((DrakonWidget)control).redrawAll();
  }

  /**
   * Recursively redraw this widget and all children.
   * 
   * @param gc
   *          The graphics context to draw on.
   * @param offsetx
   *          The offset of the x coordinate.
   * @param offsety
   *          The offset of the y coordinate.
   */
  public void drawAllOn(GC gc, int offsetx, int offsety, float scale)
  {
    Rectangle bounds = getBounds();
    offsetx += bounds.x;
    offsety += bounds.y;
    drawOn(gc,offsetx,offsety,scale);
    for (Control control : getChildren())
      if (control instanceof DrakonWidget)
        ((DrakonWidget)control).drawAllOn(gc,offsetx,offsety,scale);
  }

  /**
   * Abbreviates a string so that its rendering is not wider than <code>width</code>.
   * 
   * @param gc
   *          The graphics context.
   * @param s
   *          The string.
   * @param width
   *          The width to fit the string in.
   * @return The abbreviated string.
   */
  protected String abbreviateString(GC gc, String s, int width)
  {
    if (gc.stringExtent(s).x<=width) return s;
    
    for ( ; s.length()>0; s=s.substring(0,s.length()-1))
      if (gc.stringExtent(s+"...").x<=width)
        return s+"...";
    return "";
  }

  // -- DEBUG --
  
  /**
   * @deprecated
   */
  public void debugAction(String hint)
  {
  }
  
}
