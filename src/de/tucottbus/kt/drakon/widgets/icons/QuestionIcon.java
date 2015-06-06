package de.tucottbus.kt.drakon.widgets.icons;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonIcon;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;

/**
 * The question icon.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class QuestionIcon extends DrakonIcon
{

  /**
   * The font for drawing the Yes and No labels.
   */
  private static Font fYn;
  
  /**
   * The answer of the left branch.
   */
  private boolean leftAnswer = true;
  
  /**
   * Creates a new question icon. This constructor is equivalent to {@link 
   * #QuestionIcon(DrakonMacro, int, int, int) QuestionIcon(parent,style,1,1)}.
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
  public QuestionIcon(DrakonMacro parent, int style)
  {
    super(parent,style,1,1);
    setIconSize(-1,getDefaultIconSize().y+8);
  }

  /**
   * The answer of the left branch.
   * 
   * @param leftAnswer
   *          The answer.
   */
  public void setLeftAnswer(boolean leftAnswer)
  {
    this.leftAnswer = leftAnswer;
  }
  
  /**
   * Returns the answer of the left branch.
   */
  public boolean getLeftAnswer()
  {
    return leftAnswer;
  }
  
  @Override
  protected void drawIconOn(GC gc)
  {
    int w  = getIconSize().x;
    int h  = getDefaultIconSize().y;
    int ww = getActualSize().x;
    int hh = getActualSize().y;
    gc.drawLine(w/2,0,w/2,8);
    int[] hexagon = { 
      8,h/2   , 8+(h-8)/2,4     , w-8-(h-8)/2,4 ,
      w-8,h/2 , w-8-(h-8)/2,h-4 , 8+(h-8)/2,h-4
    };
    gc.fillPolygon(hexagon);
    gc.drawPolygon(hexagon);
    gc.drawLine(w-8,h/2,ww,h/2);
    gc.drawLine(w/2,h-4,w/2,hh);
    drawCenteredStringOn(gc,getText(),w/2,h/2,w-8-h/2);

    if (fYn==null)
    {
      String fname = gc.getDevice().getSystemFont().getFontData()[0].getName();
      // FIXME: Font not released!
      fYn = new Font(Display.getDefault(),fname, 5, SWT.BOLD);
    }
    gc.setFont(fYn);
    String[] answers = getChart().getQuestionAnswerTexts();
    gc.drawString(leftAnswer?answers[1]:answers[0],w/2+3,h-3,true);
    gc.drawString(leftAnswer?answers[0]:answers[1],w-7,h/2,true);
  }

  @Override
  public boolean isControlPath()
  {
    return true;
  }

}
