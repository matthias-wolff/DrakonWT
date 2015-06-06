package de.tucottbus.kt.drakon.widgets.macros;

import org.eclipse.swt.widgets.Control;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;
import de.tucottbus.kt.drakon.widgets.icons.AddressIcon;
import de.tucottbus.kt.drakon.widgets.icons.HeadlineIcon;

/**
 * The skewer macro. A skewer is a sequence of Drakon widgets preceded by a 
 * {@link HeadlineIcon} and tailed by a {@link AddressIcon}.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class SkewerMacro extends DrakonMacro
{
  /**
   * The program body macro.
   */
  private DrakonMacro body;

  /**
   * The headline icon.
   */
  private HeadlineIcon icnHead;
  
  /**
   * The address icon.
   */
  private AddressIcon icnAddr;

  /**
   * Creates a new skewer macro.
   * 
   * @param parent
   *          The parent.
   * @param style
   *          <table>
   *            <tr>
   *              <td>{@link DRAKON}<code>.INSERT_XXX</code>:</td>
   *              <td>Insert points.</td>
   *            </tr>
   *          </table>
   */
  public SkewerMacro(DrakonMacro parent, int style)
  {
    super(parent, style);
    body = new DrakonMacro(this,DRAKON.NONE)
    {
      @Override
      public boolean canCollapse()
      {
        return false;
      }
    };
    createContent();
  }

  @Override
  protected void createContent()
  {
    // Remove all children except the bodies
    for (Control child : getChildren())
      if (child!=body)
        child.dispose();
    
    // Rebuild children structure
    setNumColumns(1);
    icnHead = new HeadlineIcon(this,DRAKON.INSERT_S|(getStyle()&DRAKON.INSERT_N));
    body.moveBelow(icnHead);
    icnAddr = new AddressIcon(this,DRAKON.INSERT_N|(getStyle()&DRAKON.INSERT_S));
  }
  
  @Override
  public void setText(String text)
  {
    icnHead.setText(text);
    super.setText(text);
  }

  /**
   * Returns the body of this program.
   */
  public DrakonMacro getBody()
  {
    return body;
  }

  /**
   * Returns the headline icon of this skewer.
   */
  public HeadlineIcon getHeadlineIcon()
  {
    return icnHead;
  }

  /**
   * Returns the address icon of this skewer.
   */
  public AddressIcon getAddressIcon()
  {
    return icnAddr;
  }
}
