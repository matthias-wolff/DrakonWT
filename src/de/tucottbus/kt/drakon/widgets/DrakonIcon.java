package de.tucottbus.kt.drakon.widgets;

import org.eclipse.swt.graphics.Point;

/**
 * The abstract superclass of all Drakon icons. See the Drakon language
 * specification for the definition of the terms "icon" and "macro icon".
 * Technically, a Drakon icon is an atomic widget which can be placed in a
 * {@link DrakonMacro}.
 * 
 * @author Matthias Wolff, BTU Cottbus.
 */
public abstract class DrakonIcon extends DrakonWidget
{
  
  /**
   * Creates a new Drakon icon.
   * 
   * @param parent
   *          The parent Drakon macro icon.
   * @param style
   *          The style. See documentation of derived classes for applicable
   *          style constants.
   * @param spanColumns
   *          The number of columns to span in the parent's grid layout.
   * @param spanRows
   *          The number of rows to span in the parent's grid layout.
   */
  public DrakonIcon(DrakonMacro parent, int style, int spanColumns, int spanRows)
  {
    super(parent,style);
    setLayoutData(getGridData(spanColumns,spanRows));
  }

  /**
   * Determines if this icon visualizes a piece of the control path. The default
   * implementation returns <code>false</code>.
   */
  @Override
  public boolean isControlPath()
  {
    return false;
  }

  public Point computeSize(int wHint, int hHint, boolean changed)
  {
    Point size = getIconSize();
    return new Point(U2Px(size.x),U2Px(size.y));
  }
  
  @Override
  public final int hasTwister()
  {
    DrakonMacro parent = (DrakonMacro)getParent();
    if (parent.getChildren()[0]!=this) return 0;
    if (parent.canCollapse() && !parent.isCollapsed())
      return -1;
    return 0;
  }
  
  @Override
  public final boolean canCollapse()
  {
    return false;
  }
}
