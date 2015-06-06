package de.tucottbus.kt.drakon.test;

import org.eclipse.swt.graphics.Point;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;
import de.tucottbus.kt.drakon.widgets.DrakonWidget;
import de.tucottbus.kt.drakon.widgets.icons.ActionIcon;
import de.tucottbus.kt.drakon.widgets.macros.ForkMacro;
import de.tucottbus.kt.drakon.widgets.macros.ProgramMacro;

public class TestProgram1 extends DrakonMacro
{
  ProgramMacro prg;

  public TestProgram1(DrakonWidget parent)
  {
    super(parent,DRAKON.NONE);
    
    getChart().setDefaultIconSize(new Point(200,40));
    //getChart().setQuestionAnswerTexts("N", "J");
    prg = new ProgramMacro(this,DRAKON.NONE);
    prg.getBody().setText("[Body]");
    prg.getTitleIcon().setText("Check wallet");
    prg.getEndIcon().setText("End");
    ForkMacro fork1 = new ForkMacro(prg.getBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    fork1.getQustionIcon().setText("lost any money?");
    fork1.getQustionIcon().setLeftAnswer(false);
    ForkMacro fork2 = new ForkMacro(fork1.getLeftBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    fork2.getQustionIcon().setText("have money at all?");
    ActionIcon action1 = new ActionIcon(fork2.getLeftBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    action1.setText("fine!");
    ForkMacro fork3 = new ForkMacro(fork1.getRightBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    fork3.getQustionIcon().setText("lost>$50?");
    fork3.getQustionIcon().setLeftAnswer(false);
    ActionIcon action2 = new ActionIcon(fork3.getLeftBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    action2.setText("bad");
    ForkMacro fork4 = new ForkMacro(fork3.getRightBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    fork4.getQustionIcon().setText("lost>$1000?");
    fork4.getQustionIcon().setLeftAnswer(false);
    ActionIcon action3 = new ActionIcon(fork4.getLeftBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    action3.setText("very bad!");
    ActionIcon action4 = new ActionIcon(fork4.getRightBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    action4.setText("damn it!");
    
    getChart().pack();
  }
  
  @Override
  public void debugAction(String hint)
  {
    if ("Edit 1".equals(hint))
    {
    }
    else if ("Edit 2".equals(hint))
    {
    }
  }
}
