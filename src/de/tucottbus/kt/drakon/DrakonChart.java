package de.tucottbus.kt.drakon;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.tucottbus.kt.drakon.widgets.DrakonMacro;
import de.tucottbus.kt.drakon.widgets.DrakonWidget;

public class DrakonChart extends ScrolledComposite
{

  private Timer setInsertingTimer;
  
  private Point defaultIconSize = new Point(160,40);
  
  private String[] questionAnswerTexts = { "N", "Y" };
  
  private float scale = 1.2f;
  
  private boolean insertingRq = false;
  
  private boolean inserting = false;

  private boolean gridVisible = false;
  
  private DrakonMacro body;
  
  public DrakonChart(Composite parent, int style)
  {
    super(parent,style);

    // Create contents
    body = new DrakonMacro(this,DRAKON.NONE);
    
    GridLayout layout = new GridLayout();
    layout.horizontalSpacing = 0;
    layout.verticalSpacing = 0;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.numColumns = 1;
    body.setLayout(layout);
    
    setContent(body);
    setMinSize(200,200);
    setExpandHorizontal(true);
    setExpandVertical(true);
    getVerticalBar().setIncrement(getVerticalBar().getIncrement()*5);
    getHorizontalBar().setIncrement(getHorizontalBar().getIncrement()*5);
    
    // Enable mouse wheel (activation listener enables the mouse wheel)
    addListener(SWT.Activate, new Listener()
    {
      public void handleEvent(Event e)
      {
        setFocus();
      }
    });
    addMouseWheelListener(new MouseWheelListener()
    {
      @Override
      public void mouseScrolled(MouseEvent e)
      {
        if ((e.stateMask&SWT.MODIFIER_MASK)==SWT.CTRL)
        {
          setScale(getScale()+(e.count>0?0.1f:-0.1f));
          pack();
        }
      }
    });

    // Register clean-up on dispose
    addDisposeListener(new DisposeListener()
    {
      @Override
      public void widgetDisposed(DisposeEvent e)
      {
        try
        {
          setInsertingTimer.cancel();
          setInsertingTimer.purge();
        }
        catch (Exception ex) {}
      }
    });
  }
  
  /**
   * Recursively optimizes the layout of the widgets in this chart, packs them
   * and adjusts the minimal size of the chart's scrolled composite. Equivalent
   * to  {@link #pack(boolean) pack(true)}.
   * 
   * <p><b>NOTE:</b> As a rule, this method should always be invoked when 
   * anything relevant to the chart's layout has been changed.</p>
   * 
   * @see #pack(boolean)
   */
  @Override
  public void pack()
  {
    checkWidget();
    pack(true);
  }

  /**
   * Recursively optimizes the layout of the widgets in this chart, packs them
   * and adjusts the minimal size of the chart's scrolled composite.
   * 
   * @param
   *    changed <em>ignored</em> (always assume <code>true</code>).
   * @see #pack()
   */
  @Override
  public void pack(boolean changed)
  {
    checkWidget();
    body.optimizeLayout(true);
    body.pack(true);
    setMinSize(body.computeSize(SWT.DEFAULT,SWT.DEFAULT,true));
  }
  
  public DrakonMacro getBody()
  {
    return body;
  }
  
  public void clear()
  {
    for (Control control : body.getChildren())
      control.dispose();
  }
  
  public void setInserting(boolean inserting)
  {
    try
    {
      setInsertingTimer.cancel();
      setInsertingTimer.purge();
    }
    catch (Exception e) {}
    insertingRq = inserting;
    setInsertingTimer = new Timer();
    setInsertingTimer.schedule(new TimerTask()
    {
      @Override
      public void run()
      {
        applySetInserting();
      }
    },100);
  }
  
  protected void applySetInserting()
  {
    if (inserting == insertingRq) return;
    inserting = insertingRq;
    Display.getDefault().syncExec(new Runnable()
    {
      @Override
      public void run()
      {
        body.redrawAll();
      }
    });
  }

  public boolean isInserting()
  {
    return inserting;
  }

  public void setScale(float scale)
  {
    if (scale<0.5f) return;
    this.scale = scale;
  }

  public float getScale()
  {
    return scale;
  }
  
  public void setDefaultIconSize(Point defaultIconSize)
  {
    this.defaultIconSize = defaultIconSize;
    pack();
  }
  
  /**
   * Returns the default icon size of this chart.
   * 
   * @return A point containing the default width and height in chart units.
   */
  public Point getDefaultIconSize()
  {
    return defaultIconSize;
  }

  /**
   * Set the labels of the answers to yes-no questions.
   * 
   * @param negative
   *          The negative answer's text (should be exactly one capital letter).
   * @param positive
   *          The positive answer's text (should be exactly one capital letter).
   */
  public void setQuestionAnswerTexts(String negative, String positive)
  {
    questionAnswerTexts = new String[2];
    questionAnswerTexts[0] = negative;
    questionAnswerTexts[1] = positive;
  }
  
  /**
   * Returns the labels of the answers to yes-no questions.
   * 
   * @return An array of two strings, first the negative answer string, then the
   *         positive answer string.
   */
  public String[] getQuestionAnswerTexts()
  {
    return questionAnswerTexts;
  }
  
  public void setGridVisible(boolean gridVisible)
  {
    if (this.gridVisible == gridVisible) return;
    this.gridVisible = gridVisible;
    body.redrawAll();
  }

  public boolean isGridVisible()
  {
    return gridVisible;
  }

  // -- Printing --
  
  /**
   * Prints this chart.
   * 
   * @param printer
   *          The (initialized) printer to print to.
   */
  public void print(Printer printer)
  {
    if (printer.startJob("DRAKON Chart"))
    {
      GC gc = new GC(printer);
      float scale = (float)printer.getDPI().x/(float)Display.getDefault().getDPI().x;

      Point size = new Point(0,0);
      Control[] children = body.getChildren();
      if (children.length>0)
        size = children[0].getSize();
      Color cLtGrey = new Color(gc.getDevice(),235,236,236);
      gc.setBackground(cLtGrey);
      gc.fillRectangle(0,0,(int)((size.x+20)*scale + .5),(int)((size.y+20)*scale + .5));
      
      Point origin = getOrigin();
      body.drawAllOn(gc,10+origin.x,10+origin.y,scale);
      printer.endJob();
      cLtGrey.dispose();
      gc.dispose();
    }

  }
  
  // -- DEBUG ---
  
  /**
   * @deprecated 
   */
  public void debugAction(String hint)
  {
    Control[] controls = body.getChildren();
    if (controls.length>0)
      ((DrakonWidget)controls[0]).debugAction(hint);
  }

}
