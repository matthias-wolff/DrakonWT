package de.tucottbus.kt.drakon.widgets.icons;

import org.eclipse.swt.graphics.GC;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonIcon;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;

/**
 * The start timer icon.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class StartTimerIcon extends DrakonIcon
{

  /**
   * Creates a new start timer icon.
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
  public StartTimerIcon(DrakonMacro parent, int style)
  {
    super(parent,style,1,1);
  }

  @Override
  protected void drawIconOn(GC gc)
  {
    int w  = getIconSize().x;
    int h  = getIconSize().y;
    int hh = getActualSize().y;

    gc.drawLine(w/2,0,w/2,4);
    int[] trapez = { 4,4 , w-4,4 , w-4-h/4,h-4 , 4+h/4,h-4 };
    gc.fillPolygon(trapez);
    gc.drawPolygon(trapez);
    gc.drawLine(8,4,8+h/4,h-4);
    gc.drawLine(w-8,4,w-8-h/4,h-4);
    gc.drawLine(w/2,h-4,w/2,hh);

    drawCenteredStringOn(gc,getText(),w/2,h/2,w-8-h/2);
  }

}
