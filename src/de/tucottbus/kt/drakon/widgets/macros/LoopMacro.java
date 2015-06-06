package de.tucottbus.kt.drakon.widgets.macros;

import org.eclipse.swt.widgets.Control;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;
import de.tucottbus.kt.drakon.widgets.icons.QuestionIcon;
import de.tucottbus.kt.drakon.widgets.icons.WireIcon;

/**
 * The do-check or check-do loop macro.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class LoopMacro extends DrakonMacro
{

  /**
   * The loop body macro.
   */
  private DrakonMacro body;  

  /**
   * The question icon.
   */
  private QuestionIcon icnQuestion;

  /**
   * Creates a new loop macro.
   * 
   * @param parent
   *          The parent macro.
   * @param style
   *          The loop style, {@link DRAKON#DOCHECK} or {@link DRAKON#CHECKDO}
   */
  public LoopMacro(DrakonMacro parent, int style)
  {
    super(parent, (style & DRAKON.LOOPTYPE)==0 ? style|=DRAKON.DOCHECK : style);
    body = new DrakonMacro(this,DRAKON.NONE);
    icnQuestion = new QuestionIcon(this,DRAKON.NONE);
    createContent();
  }
  
  @Override
  public boolean canCollapse()
  {
    return true;
  }

  /**
   * Returns the loop type.
   * 
   * @return {@link DRAKON#DOCHECK} or {@link DRAKON#CHECKDO}.
   */
  public int getType()
  {
    return getStyle()&DRAKON.LOOPTYPE;
  }

  /**
   * Changes the loop type.
   * 
   * @param type
   *          The new loop type; {@link LoopMacro#DOCHECK} or {@link #CHECKDO}.
   */
  public void setType(int type)
  {
    setStyle((getStyle()&~DRAKON.LOOPTYPE) | (type&DRAKON.LOOPTYPE));
    createContent();
  }
  
  /**
   * Returns the body of this loop.
   */
  public DrakonMacro getBody()
  {
    return body;
  }

  /**
   * Returns the Question icon of this loop.
   */
  public QuestionIcon getQustionIcon()
  {
    return icnQuestion;
  }

  // -- Overrides --
  
  @Override
  protected void createContent()
  {
    for (Control child : getChildren())
      if (child!=body && child!=icnQuestion)
        child.dispose();

    int w = getIconSize().x;
    int wReduced = getChart().getDefaultIconSize().y;
    int insnstyle = getStyle()&DRAKON.INSERT_N;
    int inssstyle = getStyle()&DRAKON.INSERT_S;
    int qstyle    = icnQuestion.getStyle()&~DRAKON.INSERT_TYPE;
    WireIcon icnWire;
    switch (getType())
    {
    case DRAKON.DOCHECK: {
      setNumColumns(2);
      icnWire = new WireIcon(this,DRAKON.WIRE_LOOP|insnstyle|DRAKON.INSERT_S,1,1);
      icnWire = new WireIcon(this,DRAKON.WIRE_S_W,1,1);
      icnWire.setIconSize(wReduced,-1);
      body.moveBelow(icnWire);
      icnWire = new WireIcon(this,DRAKON.WIRE_S_N,1,1);
      icnWire.setIconSize(wReduced,-1);
      icnQuestion.setLayoutData(getGridData(1,1));
      icnQuestion.moveBelow(icnWire);
      icnQuestion.setStyle(qstyle|DRAKON.INSERT_N|DRAKON.INSERT_S);
      icnWire = new WireIcon(this,DRAKON.WIRE_W_N,1,1);
      icnWire.setIconSize(wReduced,-1);
      break; }
    case DRAKON.CHECKDO: {
      setNumColumns(3);
      icnWire = new WireIcon(this,DRAKON.WIRE_LOOP|DRAKON.INSERT_N,1,1);
      icnWire = new WireIcon(this,DRAKON.WIRE_E_W,1,1);
      icnWire = new WireIcon(this,DRAKON.WIRE_S_W,1,1);
      icnWire.setIconSize(wReduced,-1);
      icnQuestion.setLayoutData(getGridData(1,3));
      icnQuestion.moveBelow(icnWire);
      icnQuestion.setStyle(qstyle|DRAKON.INSERT_S);
      icnWire = new WireIcon(this,DRAKON.WIRE_W_S|inssstyle,1,1);
      icnWire = new WireIcon(this,DRAKON.WIRE_S_N,1,1);
      icnWire.setIconSize(wReduced,-1);
      body.moveBelow(icnWire);
      icnWire = new WireIcon(this,DRAKON.WIRE_S_N,1,1);
      icnWire.setIconSize(wReduced,-1);
      icnWire = new WireIcon(this,DRAKON.WIRE_N_E|DRAKON.INSERT_N,1,1);
      icnWire = new WireIcon(this,DRAKON.WIRE_W_N,1,1);
      icnWire.setIconSize(wReduced,-1);
      break; }
    }
  }

}
