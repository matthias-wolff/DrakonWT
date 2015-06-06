package de.tucottbus.kt.drakon.widgets.icons;

import org.eclipse.swt.graphics.GC;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonIcon;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;

/**
 * The parallel process icon.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class ParallelProcessIcon extends DrakonIcon
{

  /**
   * The text of the lower section.
   */
  private String text2;
  
  /**
   * Creates a new parallel process icon.
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
  public ParallelProcessIcon(DrakonMacro parent, int style)
  {
    super(parent, style, 1, 1);
    setIconSize(-1,12*getDefaultIconSize().y/8);
  }

  /**
   * Sets the icon texts.
   * 
   * @param textUpper
   *          The text of the upper section.
   * @param textLower
   *          The text of the lower section.
   */
  public void setTexts(String textUpper, String textLower)
  {
    setText(textUpper);
    this.text2 = textLower;
  }
  
  @Override
  protected void drawIconOn(GC gc)
  {
    int w  = getIconSize().x;
    int h  = getDefaultIconSize().y;
    int ht = h/8;
    int hh = getActualSize().y;
    gc.drawLine(w/2,0,w/2,4);
    gc.fillRectangle(2*ht+4, 4, w-2*ht-8, h-ht-8);
    gc.drawRectangle(2*ht+4, 4, w-2*ht-8, h-ht-8);
    gc.fillRectangle(4, 5*ht+4, w-2*ht-8, h-ht-8);
    gc.drawRectangle(4, 5*ht+4, w-2*ht-8, h-ht-8);
    gc.drawLine(w/2,getIconSize().y-4,w/2,hh);
    drawCenteredStringOn(gc,getText(),w/2+ht,(h-ht)/2,w-2*ht-10);
    drawCenteredStringOn(gc,text2,w/2-ht,5*ht+4+(h-ht-8)/2,w-2*ht-10);
  }

}
