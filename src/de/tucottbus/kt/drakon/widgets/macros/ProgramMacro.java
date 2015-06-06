package de.tucottbus.kt.drakon.widgets.macros;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;
import de.tucottbus.kt.drakon.widgets.icons.EndIcon;
import de.tucottbus.kt.drakon.widgets.icons.TitleIcon;

public class ProgramMacro extends DrakonMacro
{

  /**
   * The program body macro.
   */
  private DrakonMacro body;

  /**
   * The title icon.
   */
  private TitleIcon icnTitle;

  /**
   * The end icon.
   */
  private EndIcon icnEnd;

  /**
   * Creates a new program macro.
   * 
   * @param parent
   *          The parent.
   * @param style
   *          -- <em>reserved</em> --The style; must be {@link DRAKON#NONE}.
   */
  public ProgramMacro(DrakonMacro parent, int style)
  {
    super(parent,style);
    setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
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
    icnTitle = new TitleIcon(this,DRAKON.INSERT_S);
    body.moveBelow(icnTitle);
    icnEnd = new EndIcon(this,DRAKON.INSERT_N);
  }
  
  @Override
  public boolean canCollapse()
  {
    return false;
  }
  
  /**
   * Returns the body of this program.
   */
  public DrakonMacro getBody()
  {
    return body;
  }

  /**
   * Returns the title icon of this program.
   */
  public TitleIcon getTitleIcon()
  {
    return icnTitle;
  }

  /**
   * Returns the end icon of this program.
   */
  public EndIcon getEndIcon()
  {
    return icnEnd;
  }
}
