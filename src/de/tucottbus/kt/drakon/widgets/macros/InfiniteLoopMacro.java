package de.tucottbus.kt.drakon.widgets.macros;

import org.eclipse.swt.widgets.Control;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;
import de.tucottbus.kt.drakon.widgets.icons.WireIcon;

/**
 * An infinite loop macro.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class InfiniteLoopMacro extends DrakonMacro
{
  
  /**
   * The loop body macro.
   */
  private DrakonMacro body;  

  /**
   * Creates a new loop macro.
   * 
   * @param parent
   *          The parent macro.
   * @param style
   *          <table>
   *            <tr>
   *              <td>{@link DRAKON}<code>.INSERT_XXX</code>:</td>
   *              <td>Insert points.</td>
   *            </tr>
   *          </table>
   */
  public InfiniteLoopMacro(DrakonMacro parent, int style)
  {
    super(parent,style);
    body = new DrakonMacro(this,DRAKON.NONE);
    createContent();
  }
  
  @Override
  public boolean canCollapse()
  {
    return true;
  }
  
  /**
   * Returns the body of this loop.
   */
  public DrakonMacro getBody()
  {
    return body;
  }

  // -- Overrides --
  
  @Override
  protected void createContent()
  {
    for (Control child : getChildren())
      if (child!=body)
        child.dispose();

    int wReduced = getChart().getDefaultIconSize().y;    
    WireIcon icnWire;

    setNumColumns(2);
    int istyle = getStyle()&DRAKON.INSERT_TYPE;
    icnWire = new WireIcon(this,DRAKON.WIRE_LOOP|istyle,1,1);
    icnWire = new WireIcon(this,DRAKON.WIRE_S_W,1,1);
    icnWire.setIconSize(wReduced,-1);
    body.moveBelow(icnWire);
    icnWire = new WireIcon(this,DRAKON.WIRE_S_N,1,1);
    icnWire.setIconSize(wReduced,-1);
    icnWire = new WireIcon(this,DRAKON.WIRE_N_E,1,1);
    icnWire = new WireIcon(this,DRAKON.WIRE_W_N,1,1);
    icnWire.setIconSize(wReduced,-1);
  }

}
