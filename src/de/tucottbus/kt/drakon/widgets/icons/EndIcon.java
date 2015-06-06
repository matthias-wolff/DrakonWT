package de.tucottbus.kt.drakon.widgets.icons;

import org.eclipse.swt.graphics.GC;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonIcon;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;

/**
 * The end icon.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class EndIcon extends DrakonIcon
{

  /**
   * Creates a new end icon.
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
  public EndIcon(DrakonMacro parent, int style)
  {
    super(parent,style,1,1);
  }
  
  @Override
  protected void drawIconOn(GC gc)
  {
    int w  = getIconSize().x;
    int h  = getIconSize().y;
    gc.drawLine(w/2,0,w/2,4);
    gc.fillRoundRectangle(24,4,w-52,h-8,h-8,h-8);
    gc.drawRoundRectangle(24,4,w-52,h-8,h-8,h-8);
    drawCenteredStringOn(gc,getText(),w/2,h/2,w-58);
  }

  @Override
  public boolean isControlPath()
  {
    return true;
  }

}
