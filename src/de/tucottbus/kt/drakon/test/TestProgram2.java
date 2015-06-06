package de.tucottbus.kt.drakon.test;

import de.tucottbus.kt.drakon.DRAKON;
import de.tucottbus.kt.drakon.widgets.DrakonMacro;
import de.tucottbus.kt.drakon.widgets.DrakonWidget;
import de.tucottbus.kt.drakon.widgets.icons.ActionIcon;
import de.tucottbus.kt.drakon.widgets.macros.ForkMacro;
import de.tucottbus.kt.drakon.widgets.macros.LoopMacro;
import de.tucottbus.kt.drakon.widgets.macros.ProgramMacro;

public class TestProgram2 extends DrakonMacro
{
  ProgramMacro prg;
  LoopMacro    loop1;
  LoopMacro    loop2;
  ForkMacro    fork1;

  public TestProgram2(DrakonWidget parent)
  {
    super(parent,DRAKON.NONE);
    
    getChart().setQuestionAnswerTexts("N", "J");

    prg = new ProgramMacro(this,DRAKON.NONE);
    prg.getBody().setText("Programm");
    prg.getTitleIcon().setText("Arbeit");
    prg.getEndIcon().setText("Ende");
    loop1 = new LoopMacro(prg.getBody(),DRAKON.CHECKDO|DRAKON.INSERT_N|DRAKON.INSERT_S);
    loop1.setText("Schleife 1");
    loop1.getQustionIcon().setText("Feiertag?");
    loop2 = new LoopMacro(loop1.getBody(),DRAKON.DOCHECK|DRAKON.INSERT_N|DRAKON.INSERT_S);
    loop2.setText("Schleife 2");
    loop2.getQustionIcon().setText("Feierabend?");
    fork1 = new ForkMacro(loop2.getBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    fork1.setText("Verzweigung 1");
    fork1.getQustionIcon().setText("zu faul?");
    fork1.getQustionIcon().setLeftAnswer(false);
    ActionIcon icnAction = new ActionIcon(fork1.getLeftBody(),DRAKON.INSERT_N|DRAKON.INSERT_S);
    icnAction.setText("arbeiten");
    
    //prg.optimizeLayout(true);
    getChart().pack();
  }
  
  @Override
  public void debugAction(String hint)
  {
    if ("Edit 1".equals(hint))
    {
      loop1.setType(loop1.getType() == DRAKON.DOCHECK ? DRAKON.CHECKDO : DRAKON.DOCHECK);
      loop1.layout(true);
      getChart().pack();
//      prg.optimizeLayout(true);
//      prg.pack();
    }
    else if ("Edit 2".equals(hint))
    {
      fork1.swapBodies();
      fork1.layout(true);
      getChart().pack();
//      prg.optimizeLayout(true);
//      prg.pack();
    }
  }
}
