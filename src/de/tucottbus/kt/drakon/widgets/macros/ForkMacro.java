package de.tucottbus.kt.drakon.widgets.macros;

import org.eclipse.swt.widgets.Control;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;
import de.tucottbus.kt.drakon.widgets.icons.QuestionIcon;
import de.tucottbus.kt.drakon.widgets.icons.WireIcon;

/**
 * The fork macro. Fork macros provide if-else branches.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class ForkMacro extends DrakonMacro
{

  /**
   * The left body macro (primary answer).
   */
  private DrakonMacro leftBody;

  /**
   * The right body macro (secondary answer).
   */
  private DrakonMacro rightBody;

  /**
   * The question icon.
   */
  private QuestionIcon icnQuestion;

  private WireIcon icnWire1; 
  
  private WireIcon icnWire2; 

  /**
   * Creates a new fork macro.
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
  public ForkMacro(DrakonMacro parent, int style)
  {
    super(parent,style);
    leftBody    = new DrakonMacro(this,DRAKON.INSERT_N|DRAKON.INSERT_S);
    rightBody   = new DrakonMacro(this,DRAKON.INSERT_N|DRAKON.INSERT_S);
    icnQuestion = new QuestionIcon(this,DRAKON.NONE);
    createContent();
  }
  
  @Override
  public boolean canCollapse()
  {
    return true;
  }
  
  /**
   * Returns the fork type.
   * 
   * @return 
   *   <table>
   *     <tr>
   *       <td>{@link DRAKON#LEFT}</td>
   *       <td>if the fork has a primary but no secondary body,</td>
   *     </tr>
   *     <tr>
   *       <td>{@link DRAKON#RIGHT}</td>
   *       <td>if the fork has a secondary but no primary body, or</td>
   *    </tr>
   *     <tr>
   *       <td>{@link DRAKON#BOTH}</td>
   *       <td>otherwise.</td>
   *    </tr>
   *   </table>
   * @see #setType(int)
   */
  public int getType()
  {
    boolean bLeft = leftBody.getChildren().length>0;
    boolean bRight = rightBody.getChildren().length>0;
    if (bLeft==bRight) return DRAKON.BOTH;
    return bLeft ? DRAKON.LEFT : DRAKON.RIGHT;
  }

  /**
   * Swaps the left and right bodies.
   * 
   * @see #leftBody
   * @see #rightBody
   */
  public void swapBodies()
  {
    DrakonMacro save = leftBody;
    leftBody = rightBody;
    rightBody = save;
    icnQuestion.setLeftAnswer(!icnQuestion.getLeftAnswer());
    createContent();
  }
  
  /**
   * Returns the left body of this fork. The left body belongs to the primary
   * answer to the fork's question.
   * 
   * @see #getRightBody()
   */
  public DrakonMacro getLeftBody()
  {
    return leftBody;
  }
  
  /**
   * Returns the right body of this fork. The right body belongs to the 
   * secondary answer to the fork's question.
   * 
   * @see #getLeftBody()
   */
  public DrakonMacro getRightBody()
  {
    return rightBody;
  }

  /**
   * Returns the Question icon of this loop.
   */
  public QuestionIcon getQustionIcon()
  {
    return icnQuestion;
  }

  @Override
  protected void createContent()
  {
    // Remove all children except the bodies
    for (Control child : getChildren())
      if (child!=leftBody && child!=rightBody && child!=icnQuestion)
        child.dispose();
    
    // Rebuild children structure
    setNumColumns(2);
    icnQuestion.moveAbove(null);
    int qstyle = icnQuestion.getStyle()&~DRAKON.INSERT_TYPE;
    icnQuestion.setStyle(qstyle|(getStyle()&DRAKON.INSERT_N)|DRAKON.INSERT_S);
    icnWire1 = new WireIcon(this,DRAKON.WIRE_W_S|DRAKON.INSERT_S);
    leftBody.moveBelow(icnWire1);
    leftBody.setIconSize(-1,-1);
    rightBody.moveBelow(leftBody);
    new WireIcon(this,DRAKON.WIRE_JOIN|DRAKON.INSERT_N|(getStyle()&DRAKON.INSERT_S));
    icnWire2 = new WireIcon(this,DRAKON.WIRE_N_W|DRAKON.INSERT_N);
  }

  @Override
  public void optimizeLayout(boolean all)
  {
    super.optimizeLayout(all);
    if (rightBody.isEmpty())
    {
      int wReduced = getIconSize().y;
      if (icnWire1!=null) icnWire1.setIconSize(wReduced,-1);
      rightBody.setIconSize(wReduced,-1);
      if (icnWire2!=null) icnWire2.setIconSize(wReduced,-1);
    }
  }

}
