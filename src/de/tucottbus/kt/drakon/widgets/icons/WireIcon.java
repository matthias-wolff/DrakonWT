package de.tucottbus.kt.drakon.widgets.icons;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonIcon;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;

/**
 * A wire icon. Wire icons display connecting lines between other icons.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class WireIcon extends DrakonIcon
{

  /**
   * Creates a new wire icon.
   * 
   * @param parent
   *          The parent Drakon macro icon.
   * @param style
   *          The style. A combination of the following constants:
   *          <table>
   *            <tr>
   *              <td>{@link DRAKON}<code>.WIRE_XXX</code>:</td>
   *              <td>The wire type. Omitting this style will create an empty 
   *                  icon which can be used to fill empty space in a chart</td>
   *            </tr>
   *            <tr>
   *              <td>{@link DRAKON}<code>.INSERT_XXX</code>:</td>
   *              <td>Insert points.</td>
   *            </tr>
   *          </table>
   * @param spanColumns
   *          The number of columns to span in the parent's grid layout.
   * @param spanRows
   *          The number of rows to span in the parent's grid layout.
   */
  public WireIcon(DrakonMacro parent, int style, int spanColumns, int spanRows)
  {
    super(parent,style,spanColumns,spanRows);
  }

  /**
   * Convenience constructor equivalent to {@link #WireIcon(DrakonMacro, int, 
   * int, int) WireIcon(parent,style,1,1)}.
   */
  public WireIcon(DrakonMacro parent, int style)
  {
    this(parent,style,1,1);
  }
  
  @Override
  protected void drawIconOn(GC gc)
  {
    int w  = getIconSize().x;
    int h  = getIconSize().y;
    int ww = getActualSize().x;
    int hh = getActualSize().y;
    switch (getStyle() & DRAKON.WIRETYPE)
    {
    case DRAKON.WIRE_N_E:
      gc.drawPolyline(new int[] { w/2,0 , w/2,h/2 , ww,h/2 });
      break;
    case DRAKON.WIRE_N_S:
      gc.drawLine( w/2,0 , w/2,hh);
      break;
    case DRAKON.WIRE_N_S_E:
      gc.drawLine( w/2,0 , w/2,hh);
      gc.drawLine( w/2,h/2 , ww,h/2);
      break;
    case DRAKON.WIRE_N_W:
      gc.drawPolyline(new int[] { w/2,0 , w/2,h/2 , 0,h/2 });
      break;
    case DRAKON.WIRE_E_W:
      gc.drawLine( ww,h/2 , 0,h/2);
      break;
    case DRAKON.WIRE_E_N_W:
      gc.drawLine( ww,h/2 , 0,h/2);
      gc.drawLine( w/2,0 , w/2,h/2);
      break;
    case DRAKON.WIRE_S_N:
      gc.drawLine( w/2,hh , w/2,0);
      break;
    case DRAKON.WIRE_S_W:
      gc.drawPolyline(new int[] { w/2,hh , w/2,h/2 , 0,h/2 });
      break;
    case DRAKON.WIRE_W_N:
      gc.drawPolyline(new int[] { 0,h/2 , w/2,h/2 , w/2,0 });
      break;
    case DRAKON.WIRE_W_E:
      gc.drawLine( 0,h/2 , ww,h/2 );
      break;
    case DRAKON.WIRE_W_E_S:
      gc.drawLine( 0,h/2 , ww,h/2 );
      gc.drawLine( w/2,h/2 , w/2,hh );
      break;
    case DRAKON.WIRE_W_S:
      gc.drawPolyline(new int[] { 0,h/2 , w/2,h/2 , w/2,hh });
      break;
    case DRAKON.WIRE_LOOP:
      gc.drawLine(w/2,0,w/2,hh);
      gc.drawLine(ww, h/2, w/2,h/2);
      if (getHover())
        gc.setBackground(cHoverFg);
      else
        gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
      gc.fillPolygon(new int[] { w/2,h/2 , w/2+12,h/2-4 , w/2+12,h/2+4 });
      break;
    case DRAKON.WIRE_JOIN:
      gc.drawLine(w/2,0,w/2,hh);
      gc.drawLine(ww,h/2,w/2,h/2);
      break;
    }
  }

  @Override
  public boolean isControlPath()
  {
    return true;
  }

}
