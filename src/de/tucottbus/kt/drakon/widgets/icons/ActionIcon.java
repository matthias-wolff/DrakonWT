package de.tucottbus.kt.drakon.widgets.icons;

import org.eclipse.swt.graphics.GC;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonIcon;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;

/**
 * The action icon.
 * 
 * <p><img src="doc-files/ActionIcon.png"></p>
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class ActionIcon extends DrakonIcon
{

  /**
   * Creates a new action icon.
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
  public ActionIcon(DrakonMacro parent, int style)
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
    gc.fillRectangle(4,4,w-8,h-8);
    gc.drawRectangle(4,4,w-8,h-8);
    gc.drawLine(w/2,h-4,w/2,hh);
    drawCenteredStringOn(gc,getText(),w/2,h/2,w-10);
  }

}
