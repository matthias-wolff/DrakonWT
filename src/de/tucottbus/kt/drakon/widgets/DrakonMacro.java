package de.tucottbus.kt.drakon.widgets;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.DrakonChart;

/**
 * The superclass of all Drakon macro icons. See the Drakon language
 * specification for the definition of the terms "icon" and "macro icon".
 * Technically, a Drakon macro icon is a composite with a grid layout which
 * contains {@link DrakonIcon}s and/or other Drakon macros icons.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class DrakonMacro extends DrakonWidget
{
  private GridLayout layout;
  
  /**
   * Creates a new Drakon macro.
   * 
   * @param parent
   *          The parent Drakon macro.
   * @param style
   *          The style, any bitwise combination of the following:
   *          <table>
   *            <tr>
   *              <td>{@link DRAKON}<code>.INSERT_XXX</code>:</td>
   *              <td>Insert points.</td>
   *            </tr>
   *          </table>
   * @throws IllegalArgumentException
   *          If the parent is a {@link DrakonIcon}.
   */
  public DrakonMacro(DrakonMacro parent, int style)
  {
    this((Composite)parent,style);
  }

  /**
   * Creates a new Drakon macro.
   * 
   * <p><b>IMPORTANT:</b> This constructor is <em>not</em> part of the Drakon 
   * public API. It is marked public only to allow the {@link DrakonChart}
   * class to instantiate the root widget.</p>
   */
  public DrakonMacro(Composite parent, int style)
  {
    super(parent,style);
    setLayoutData(getGridData(1,1));    
    
    layout = new GridLayout();
    layout.horizontalSpacing = 0;
    layout.verticalSpacing = 0;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.numColumns = 1;
    setLayout(layout);
  }
  
  /**
   * Sets the number of columns in this macro's grid layout. Derived classes may
   * invoke this method before adding their children.
   * 
   * @param numColumns
   *          The number of columns.
   */
  protected void setNumColumns(int numColumns)
  {
    layout.numColumns = numColumns;
    setLayout(layout);
  }

  /**
   * Determines if this macro is empty.
   */
  public boolean isEmpty()
  {
    return getChildren().length==0;
  }
  
  // -- Overridables --
  
  /**
   * (Re-)creates the macro content. The default implementation does nothing.
   * Subclasses usually override this method and
   * <ul>
   *   <li>adjust the number of grid columns in the macro layout by invoking
   *     {@link #setNumColumns(int)},</li>
   *   <li>add {@link DrakonWidget}s, and
   *   <li>define insert points by invoking {@link #addInsertPoint(Point)}.
   * </ul>
   * <p>The framework may call this method several times. Implementations must
   * therefore re-use or clear any already existing content.
   */
  protected void createContent()
  {
  }
  
  // -- Overrides --

  @Override
  public Point computeSize(int wHint, int hHint, boolean changed)
  {
    Point size = getIconSize();
    if (getChildren().length==0)
      return new Point(U2Px(size.x),0);
    else if (isCollapsed())
      return new Point(U2Px(size.x),U2Px(size.y));
    else
      return super.computeSize(wHint,hHint,changed);
  }
  
  /**
   * Determines if this macro visualizes a piece of the control path. This is
   * the case if and only if the macro is empty.
   */
  @Override
  public boolean isControlPath()
  {
    return getChildren().length==0;
  }

  @Override
  public boolean isComposite()
  {
    return !isCollapsed() && getChildren().length>0;
  }

  @Override
  public int hasTwister()
  {
    return isCollapsed()&&getChildren().length>1 ? +1 : 0;
  }
  
  /**
   * Determines whether this macro can be collapsed into an icon. The default 
   * implementation returns <code>true</code>. Derived classes may override 
   * this method in order to define other behavior.
   */
  @Override
  public boolean canCollapse()
  {
    return getChildren().length>1;
  }
  
  @Override
  protected void drawIconOn(GC gc)
  {
    Point iconSize = getIconSize();
    int w  = iconSize.x;
    int wHalf = w/2;
    int w8 = w-8;
    int h  = iconSize.y;
    int hh = getActualSize().y;
    int h4 = h-4;
    int h8 = h-8;
    if (isControlPath())
    {
      gc.drawLine(wHalf,0,wHalf,hh);
    }
    else
    {
      gc.drawLine(wHalf,0,wHalf,4);
      gc.fillRectangle(4,4,w8,h8);
      gc.drawRectangle(4,4,w8,h8);
      gc.drawLine(8,4,8,h4);
      gc.drawLine(w8,4,w8,h4);
      gc.drawLine(wHalf,h4,wHalf,hh);
    }
    drawCenteredStringOn(gc,getText(),wHalf,h/2,w-10);
  }
}
