package de.tucottbus.kt.drakon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.tucottbus.kt.drakon.test.TestProgram1;
import de.tucottbus.kt.drakon.test.TestProgram2;
import de.tucottbus.kt.drakon.widgets.macros.ForkMacro;
import de.tucottbus.kt.drakon.widgets.macros.LoopMacro;

// TODO: Revise insert points
// TODO: Title by timer
// TODO: Title with parameters?

/**
 * Drakon singleton and constants.
 * 
 * @author Matthias Wolff, BTU Cottbus
 */
public class DRAKON
{

  /**
   * The Drakon singleton.
   */
  private static DRAKON singleton;
  
  /**
   * Empty style.
   */
  public static final int NONE = 0x0000;
  
  /**
   * Do-check loop type.
   * 
   * @see LoopMacro
   */
  public static final int DOCHECK = 0x0000;
  
  /**
   * Check-do loop type.
   * 
   * @see LoopMacro
   */
  public static final int CHECKDO = 0x0100;
  /**
   * Loop type mask.
   * 
   * @see LoopMacro
   */
  public static final int LOOPTYPE = 0x0100;
  
  /**
   * Fork has a left body.
   * 
   * @see ForkMacro
   */
  public static final int LEFT = 0x0100;
  
  /**
   * Fork has a right body.
   * 
   * @see ForkMacro
   */
  public static final int RIGHT = 0x0200;
  
  /**
   * Fork has a left and a right body.
   * 
   * @see ForkMacro
   */
  public static final int BOTH = LEFT|RIGHT;

  /**
   * Fork type mask.
   * 
   * @see ForkMacro
   */
  public static final int FORKTYPE = 0x0300;

  /**
   * Icon has a left side condition.
   */
  public static final int BY = 0x0001;

  /**
   * Icon has right side parameters.
   */
  public static final int WITH = 0x0002;
  
  /**
   * Icon has an insertion point at the top.
   */
  public static final int INSERT_N = 0x0010;
  
  /**
   * Icon has an insertion point at the bottom.
   */
  public static final int INSERT_S = 0x0020;

  /**
   * Insert points mask.
   */
  public static final int INSERT_TYPE = 0x00F0;
  
  // WireIcon styles
  public static final int WIRE_N_E   = 0x0100;
  public static final int WIRE_N_S   = 0x0200;
  public static final int WIRE_N_S_E = 0x0300;
  public static final int WIRE_N_W   = 0x0400;
  public static final int WIRE_E_W   = 0x0500;
  public static final int WIRE_E_N_W = 0x0600;
  public static final int WIRE_S_N   = 0x0700;
  public static final int WIRE_S_W   = 0x0800;
  public static final int WIRE_W_N   = 0x0900;
  public static final int WIRE_W_E   = 0x0A00;
  public static final int WIRE_W_E_S = 0x0B00;
  public static final int WIRE_W_S   = 0x0C00;
  public static final int WIRE_JOIN  = 0x0D00;
  public static final int WIRE_LOOP  = 0x0E00;
  public static final int WIRETYPE   = 0X0F00;
  
  /**
   * Returns the DRAKON singleton.
   */
  public static DRAKON getInstance()
  {
    if (singleton==null)
      singleton = new DRAKON();
    return singleton;
  }
  
  /**
   * Creates the singleton.
   */
  private DRAKON()
  {
    // Empty constructor
  }
  
  @Override
  protected void finalize() throws Throwable
  {
    System.out.println("DRAKON.finalize()");
    super.finalize();
  }  
  // -- TEST SHELL --


  static DrakonChart drakonChart;
  static Button btnShowGrid;
  
  public static void main(String[] args)
  {
    Display display = new Display();
    final Shell shell = new Shell(display);
    shell.setText("Simplified Drakon - Test Shell");
    shell.setSize(1024,768);
    shell.setLayout(new GridLayout(1,false));
    
    Composite cmpsTools = new Composite(shell,SWT.NONE);
    cmpsTools.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false));
    RowLayout rl = new RowLayout(SWT.HORIZONTAL);
    rl.center = true;
    cmpsTools.setLayout(rl);
    Button btn = new Button(cmpsTools,SWT.PUSH);
    btn.setText("Chart 1");
    btn.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        drakonChart.clear();
        new TestProgram1(drakonChart.getBody());
        drakonChart.pack();
      }
      
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
      }
    });

    btn = new Button(cmpsTools,SWT.PUSH);
    btn.setText("Chart 2");
    btn.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        drakonChart.clear();
        new TestProgram2(drakonChart.getBody());
        drakonChart.pack();
      }
      
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
      }
    });

    btnShowGrid = new Button(cmpsTools,SWT.CHECK);
    btnShowGrid.setText("Show grid");
    btnShowGrid.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        drakonChart.setGridVisible(((Button)e.widget).getSelection());
      }
      
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
      }
    });

    btn = new Button(cmpsTools,SWT.PUSH);
    btn.setText("Edit 1");
    btn.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        drakonChart.debugAction(((Button)e.widget).getText());
      }
      
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
      }
    });
    
    btn = new Button(cmpsTools,SWT.PUSH);
    btn.setText("Edit 2");
    btn.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        drakonChart.debugAction(((Button)e.widget).getText());
      }
      
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
      }
    });
    
    btn = new Button(cmpsTools,SWT.PUSH);
    btn.setText("Zoom In");
    btn.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        drakonChart.setScale(drakonChart.getScale()+0.1f);
        drakonChart.pack();
      }
      
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
      }
    });
    
    btn = new Button(cmpsTools,SWT.PUSH);
    btn.setText("Zoom Out");
    btn.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        float scale = drakonChart.getScale();
        if (scale>0.2f)
        {
          drakonChart.setScale(drakonChart.getScale()-0.1f);
          drakonChart.pack();
        }
      }
      
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
      }
    });
    
    btn = new Button(cmpsTools,SWT.PUSH);
    btn.setText("Print");
    btn.addSelectionListener(new SelectionListener()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        PrintDialog dialog = new PrintDialog(shell, SWT.NULL);
        PrinterData data = dialog.open();
        if (data == null) return;
        if (data.printToFile)
          data.fileName = "print.out";
        
        final Printer printer = new Printer(data);
        drakonChart.print(printer);
        printer.dispose();
      }
      
      @Override
      public void widgetDefaultSelected(SelectionEvent e)
      {
      }
    });    
    
    drakonChart = new DrakonChart(shell,SWT.V_SCROLL|SWT.H_SCROLL);
    drakonChart.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
    new TestProgram1(drakonChart.getBody());
    shell.setSize(1024,768);
    shell.setMaximized(true);
    shell.open();
    while (!shell.isDisposed())
    {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }

}
