package de.tucottbus.kt.drakon.widgets.icons;

import org.eclipse.swt.graphics.GC;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonIcon;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;

/**
 * The address icon.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class AddressIcon extends DrakonIcon
{

  /**
   * Creates a new address icon.
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
   */
  public AddressIcon(DrakonMacro parent, int style)
  {
    super(parent,style,1,1);
    setIconSize(-1,5*getDefaultIconSize().y/4);
  }

  @Override
  protected void drawIconOn(GC gc)
  {
    int w  = getIconSize().x;
    int h  = getDefaultIconSize().y;
    int hh = getActualSize().y;

    gc.drawLine(w/2,0,w/2,4);
    if (getText()==null || getText().length()==0)
    {
      int polygon[] = { 4,4+h/4 , w/2,4 , w-4,4+h/4 , w-4,h-4, 4,h-4 };
      gc.fillPolygon(polygon);
      gc.drawPolygon(polygon);
      gc.drawLine(w/2,h-4,w/2,hh);
    }
    else
    {
      int polygon[] = { 4,4+h/4 , w/2,4 , w-4,4+h/4 , w-4,5*h/4-4, 4,5*h/4-4 };
      gc.fillPolygon(polygon);
      gc.drawPolygon(polygon);
      gc.drawLine(w/2,5*h/4-4,w/2,hh);
    }
    
    drawCenteredStringOn(gc,getText(),w/2,3*h/4,w-10);
  }

  @Override
  public boolean isControlPath()
  {
    return true;
  }
}
