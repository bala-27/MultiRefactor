/***************************************************************************
                           GanttProject.java  -  description
                             -------------------
    begin                : dec 2002
    copyright            : (C) 2002 by Thomas Alexandre
    email                : alex.thomas@netcourrier.com
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.*;
import java.util.EventObject;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.util.ArrayList;
import java.lang.Runtime;
import java.awt.print.*;

/**
  * Main frame of the project
  */
public class GanttProject extends JFrame implements ActionListener
{
	/** The current version of ganttproject */
	public final String version = "1.9";
	
	/** The language use (english by default) */
	private GanttLanguage language = new GanttLanguageEnglish();


	
	/** The JTree part. */
	private GanttTree tree;

	/** GanttGraphicArea for the calendar with Gantt */
	private GanttGraphicArea area;

	/** GanttPeoplePanel to edit person that work on the project */
	private GanttPeoplePanel peop;


	
	/** The differetns menus */
	public JMenu mProject, mTask, mHuman, mLanguage, mHelp;

	/** The differetns menuitem */
	public JMenuItem miNew, miOpen, miSave, miSaveAs, miExport, miPrint, miPropProj, miQuit,
			  miNewTask, miDeleteTask, miPropertiesTask, miNotesTask, miUp, miDown,
			  miNewHuman, miDelHuman, miPropHuman,
			  miFrench, miEnglish, miEspanol, miPortugues, miGerman,
			  miWebPage, miAbout;

	/** The differents button of toolbar */
	public JButton bNew, bOpen, bSave, bSaveAs, bPrint, bQuit,
			bNewTask, bDelete, bProperties, bNotes,
			bUp, bDown, bPrev, bNext,
			bZoomIn, bZoomOut;
	
	/** The project filename */
	public String projectfile=new String("__nofilename__");
	
	/** The tabbed pane with the differents parts of the project*/
	JTabbedPane tabpane;
	
	
	/** The name of the project */
	public String projectName=new String();
	
	/** A short description of it */
	public String decription=new String();
	
	/** The name of the organisation */
	public String organization=new String();
	
	
	
	


	/** Constructor  */
	public GanttProject (String [] arg)
	{
		super("Diagramme de Gantt");
		setTitle(language.appliTitle());
		
		ImageIcon icon = new ImageIcon("icons/ganttproject2.gif");
		setIconImage(icon.getImage());
		
		//Creat the menus
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);

		//Allocation of the menus
		mProject  = new JMenu();
		mTask     = new JMenu();
		mHuman    = new JMenu();
		mLanguage = new JMenu();
		mHelp     = new JMenu();

		miNew=createNewItem("icons/new.gif");
		mProject.add(miNew);

		miOpen=createNewItem("icons/open.gif");
		mProject.add(miOpen);
		mProject.addSeparator();

		miSave=createNewItem("icons/save.gif");
		mProject.add(miSave);

		miSaveAs=createNewItem("icons/saveas.gif");
		mProject.add(miSaveAs);
		
		miExport=createNewItem("icons/export.gif");
		mProject.add(miExport);
		mProject.addSeparator();

		miPrint=createNewItem("icons/print.gif");
		mProject.add(miPrint);
		mProject.addSeparator();

		miPropProj=createNewItem("icons/settings.gif");
		mProject.add(miPropProj);
		mProject.addSeparator();
		
		miQuit=createNewItem("icons/exit.gif");
		mProject.add(miQuit);

		miNewTask=createNewItem("icons/insert.gif");
		mTask.add(miNewTask);

		miDeleteTask=createNewItem("icons/delete.gif");
		mTask.add(miDeleteTask);

		miPropertiesTask=createNewItem("icons/properties.gif");
		mTask.add(miPropertiesTask);

		miNotesTask=createNewItem("icons/notes.gif");
		mTask.add(miNotesTask);
		mTask.addSeparator();

		miUp=createNewItem("icons/up.gif");
		mTask.add(miUp);

		miDown=createNewItem("icons/down.gif");
		mTask.add(miDown);


		miNewHuman=createNewItem("icons/newhuman.gif");
		mHuman.add(miNewHuman);
		
		miDelHuman=createNewItem("icons/delhuman.gif");
		mHuman.add(miDelHuman);
		
		miPropHuman=createNewItem("icons/prophuman.gif");
		mHuman.add(miPropHuman);


		miEnglish=new JRadioButtonMenuItem("English",false);
		miEnglish.addActionListener(this);
		miEnglish.setSelected(true);
		mLanguage.add(miEnglish);


		miFrench=new JRadioButtonMenuItem("Fran�ais",true);
		miFrench.addActionListener(this);
		miFrench.setSelected(false);
		mLanguage.add(miFrench);

		miEspanol=new JRadioButtonMenuItem("Espa�ol",true);
		miEspanol.addActionListener(this);
		miEspanol.setSelected(false);
		mLanguage.add(miEspanol);
				
		miPortugues=new JRadioButtonMenuItem("Portugues",true);
		miPortugues.addActionListener(this);
		miPortugues.setSelected(false);
		mLanguage.add(miPortugues);

		miGerman=new JRadioButtonMenuItem("Deutsch",true);
		miGerman.addActionListener(this);
		miGerman.setSelected(false);
		mLanguage.add(miGerman);
		
		miWebPage=createNewItem("icons/network.gif");
		mHelp.add(miWebPage);

		miAbout=createNewItem("icons/about.gif");
		mHelp.add(miAbout);

		bar.add(mProject);
		bar.add(mTask);
		bar.add(mHuman);
		bar.add(mLanguage);
		bar.add(mHelp);

		setMemonic();

		//Create each objects
		tree = new GanttTree(this, language);
		area = new GanttGraphicArea(this,tree, language);
		peop = new GanttPeoplePanel(this,language);
		GanttImagePanel but = new GanttImagePanel ();
		tree.setGraphicArea(area);
		
		//to create a default project
		//createDefaultTree(tree);


		JPanel left = new JPanel(new BorderLayout());
		left.add(but, "North");
		left.add(tree,"Center");
		left.setPreferredSize(new Dimension(250, 600));

		//A splitpane is use
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(left);
        
		
		//JScrollPane sp = new JScrollPane(area);
		
		splitPane.setRightComponent(/*sp*/area);
		splitPane.setOneTouchExpandable(true);
		splitPane.setPreferredSize(new Dimension(800, 500));

       
	   
	   	tabpane = new JTabbedPane();
	    tabpane.add("Gantt", splitPane);
	    tabpane.add(language.human(), peop);
	   
	    //Add tabpan on the content pane
        getContentPane().add(tabpane, BorderLayout.CENTER);



		//Add toolbar
		JToolBar toolBar = new JToolBar();
		this.addButtons(toolBar);
		getContentPane().add(toolBar, BorderLayout.NORTH);


		//Set text on menus
		changeLanguageOfMenu();

		//Open the the project passed in argument
		if(arg.length!=0)
		{
			try {
				File file = new File (arg[0]);
				reallyOpen ( file );
			}catch (Exception ex) {
				/*JOptionPane.showConfirmDialog(this,
					language.msg2() + arg[0], language.error(),
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE );*/
				GanttDialogInfo gdi = new GanttDialogInfo(this,language, GanttDialogInfo.ERROR,
								GanttDialogInfo.YES_OPTION,language.msg2() + arg[0],language.error());
				gdi.show();
			}
		}



		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = getPreferredSize();
        // Put image at the middle of the screen
        setLocation(screenSize.width/2 - (windowSize.width/2),
                    screenSize.height/2 - (windowSize.height/2));

	}


	/** Create memonic for keyboard */
	public void setMemonic()
	{
		//--NEW----------------------------------
//		miNew.setMnemonic(KeyEvent.VK_N);
		miNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		//--OPEN----------------------------------
		miOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		//--SAVE----------------------------------
		miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		//--EXPORT----------------------------------
		miExport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		//--EXPORT----------------------------------
		miPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		//--QUIT----------------------------------
		miQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
	
		//--NEW TASK----------------------------------
		miNewTask.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		//--PROPERTIES TASK----------------------------------
		miPropertiesTask.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		//--DELETE TASK----------------------------------
		miDeleteTask.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		//--NOTES TASK----------------------------------
		miNotesTask.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));	
	}

	/** Create an item with an icon */
	public JMenuItem createNewItem(String icon)
	{
		JMenuItem item = new JMenuItem(new ImageIcon(icon));
		item.addActionListener(this);
		return item;
	}

	/** Create an item with a label and an icon */
	public JMenuItem createNewItem(String label, String icon)
	{
		JMenuItem item = new JMenuItem(label,new ImageIcon(icon));
		item.addActionListener(this);
		return item;
	}

	/** Function to change language of the project */
	public void changeLanguage()
	{
		changeLanguageOfMenu();
		area.setLanguage(language);
		tree.setLanguage(language);
		area.repaint();
		peop.refresh(language);
		miFrench.setSelected(language.getLanguage().equals("Fran�ais"));
		miEnglish.setSelected(language.getLanguage().equals("English"));
		miEspanol.setSelected(language.getLanguage().equals("Espa�ol"));
		miPortugues.setSelected(language.getLanguage().equals("Portugues"));
		miGerman.setSelected(language.getLanguage().equals("Deutsch"));
		
		tabpane.setTitleAt(1,language.human());
	}

	/** Set the menus language */
	public void changeLanguageOfMenu()
	{
		mProject.setText(language.project());
		mTask.setText(language.task());
		mHuman.setText(language.human());
		mLanguage.setText(language.language());
		mHelp.setText(language.help());

		miNew.setText(language.newProject());
		miOpen.setText(language.openProject());
		miSave.setText(language.saveProject());
		miSaveAs.setText(language.saveAsProject());
		miExport.setText(language.export());
		miPrint.setText(language.printProject());
		miPropProj.setText(language.ProjectProperties());
		miQuit.setText(language.quit());

		miNewTask.setText(language.createTask());
		miDeleteTask.setText(language.deleteTask());
		miPropertiesTask.setText(language.propertiesTask());
		miNotesTask.setText(language.notesTask());
		miUp.setText(language.upTask());
		miDown.setText(language.downTask());
		
		miNewHuman.setText(language.newHuman());
		miDelHuman.setText(language.deleteHuman());
		miPropHuman.setText(language.propertiesHuman());

		miFrench.setText("Fran�ais");
		miEnglish.setText("English");
		miEspanol.setText("Espa�ol");
		miPortugues.setText("Portugues");
		miGerman.setText("Deutsch");

		miWebPage.setText(language.webPage());
		miAbout.setText(language.about());

		////////////////////////////////////////////

		bNew.setToolTipText(getToolTip(language.newProject()));
		bOpen.setToolTipText(getToolTip(language.openProject()));
		bSave.setToolTipText(getToolTip(language.saveProject()));
		bSaveAs.setToolTipText(getToolTip(language.saveAsProject()));
		bPrint.setToolTipText(getToolTip(language.printProject()));
		bQuit.setToolTipText(getToolTip(language.quit()));
		bNewTask.setToolTipText(getToolTip(language.createTask()));
		bDelete.setToolTipText(getToolTip(language.deleteTask()));
		bProperties.setToolTipText(getToolTip(language.propertiesTask()));
		bNotes.setToolTipText(getToolTip(language.notesTask()));
		bUp.setToolTipText(getToolTip(language.upTask()));
		bDown.setToolTipText(getToolTip(language.downTask()));
		bPrev.setToolTipText(getToolTip(language.backDate()));
		bNext.setToolTipText(getToolTip(language.forwardDate()));
		bZoomIn.setToolTipText(getToolTip(language.zoomIn()));
		bZoomOut.setToolTipText(getToolTip(language.zoomOut()));
	}

	/** Return the tooltip in html (with yello bgcolor */
	public String getToolTip(String msg)
	{
		return "<html><body bgcolor=#FFFFBD>"+msg+"</body></html>";
	}


	/** Create the button on toolbar */
	public void addButtons(JToolBar toolBar)
	{

///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bNew = new JButton(new ImageIcon("icons/new.gif"));
        bNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               newProject();
            }
        });
        toolBar.add(bNew);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bOpen = new JButton(new ImageIcon("icons/open.gif"));
        bOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
					openProject();
				}catch (Exception ex) {

				}
            }
        });
        toolBar.add(bOpen);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bSave = new JButton(new ImageIcon("icons/save.gif"));
        bSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
					saveProject();
				}catch (Exception ex) {
					System.out.println(ex);
				}
            }
        });
        toolBar.add(bSave);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bSaveAs = new JButton(new ImageIcon("icons/saveas.gif"));
        bSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               try {
					saveAsProject();
				}catch (Exception ex) {
					System.out.println(ex);
				}
            }
        });
        toolBar.add(bSaveAs);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bPrint = new JButton(new ImageIcon("icons/print.gif"));
        bPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               printProject();
            }
        });
        toolBar.add(bPrint);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bQuit = new JButton(new ImageIcon("icons/exit.gif"));
        bQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               quitApplication();
            }
        });
        toolBar.add(bQuit);
		toolBar.addSeparator();
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bNewTask = new JButton(new ImageIcon("icons/insert.gif"));
        bNewTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               newTask();
            }
        });
        toolBar.add(bNewTask);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bDelete = new JButton(new ImageIcon("icons/delete.gif"));
        bDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               deleteTask();
            }
        });
        toolBar.add(bDelete);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bProperties = new JButton(new ImageIcon("icons/properties.gif"));
        bProperties.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              propertiesTask();
            }
        });
        toolBar.add(bProperties);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bNotes = new JButton(new ImageIcon("icons/notes.gif"));
        bNotes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              notesTask();
            }
        });
        toolBar.add(bNotes);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bUp = new JButton(new ImageIcon("icons/up.gif"));
        bUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             tree.upCurrentNode();
            }
        });
        toolBar.add(bUp);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bDown = new JButton(new ImageIcon("icons/down.gif"));
        bDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              tree.downCurrentNode();
            }
        });
        toolBar.add(bDown);
		toolBar.addSeparator();
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bPrev = new JButton(new ImageIcon("icons/prev.gif"));
        bPrev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              area.changeDate(false);
			  area.repaint();
            }
        });
        toolBar.add(bPrev);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bNext = new JButton(new ImageIcon("icons/next.gif"));
        bNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              area.changeDate(true);
			  area.repaint();
            }
        });
        toolBar.add(bNext);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bZoomOut = new JButton (new ImageIcon("icons/zoomm.gif"));
		bZoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              	if(area.getZoom()!=8) area.zoomMore();
				area.zoomToBegin();
				area.repaint();
				bZoomIn.setEnabled(true);
				bZoomOut.setEnabled(true);
				if(area.getZoom()==8) bZoomOut.setEnabled(false);
            }
        });
        toolBar.add(bZoomOut);

///////////////////////////////////////////////////////////////////////////////////////////////////////////
		bZoomIn = new JButton (new ImageIcon("icons/zoomp.gif"));
		bZoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(area.getZoom()!=0) area.zoomLess();
				area.zoomToBegin();
				area.repaint();
				bZoomIn.setEnabled(true);
				bZoomOut.setEnabled(true);
				if(area.getZoom()==0) bZoomIn.setEnabled(false);
            }
        });
        toolBar.add(bZoomIn);



	}

	/** A menu has been activate */
	public void actionPerformed (ActionEvent evt)
	{
		if(evt.getSource() instanceof JMenuItem)
		{
			String arg = evt.getActionCommand();

///////////////////////////////////////////////////////////////////////////////////////////////////////////
			if(arg.equals(language.newProject()))
			{
				newProject();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.openProject()))
			{
				try {
					openProject();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.saveProject()))
			{
				try {
					saveProject();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.saveAsProject()))
			{
				try {
					saveAsProject();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.export()))
			{
				export();
			}			
			
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.printProject()))
			{
				printProject();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.ProjectProperties()))
			{
				editSettings();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.quit()))
			{
				quitApplication();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.createTask()))
			{
				newTask();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.deleteTask()))
			{
				deleteTask();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.propertiesTask()))
			{
				propertiesTask();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.notesTask()))
			{
				notesTask();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.upTask()))
			{
				tree.upCurrentNode();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.downTask()))
			{
				tree.downCurrentNode();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.newHuman()))
			{
				tabpane.setSelectedIndex(1);
				peop.newHuman(this);
			}			
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.deleteHuman()))
			{
				tabpane.setSelectedIndex(1);
				peop.deleteHuman(this);
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.propertiesHuman()))
			{
				tabpane.setSelectedIndex(1);
				peop.propertiesHuman(this);
			}						
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.webPage()))
			{
				try {
					openWebPage();
				}catch (Exception e) {
					System.out.println(e);
				}
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals(language.about()))
			{
				/*JOptionPane.showConfirmDialog(this,
					"GanttProject (Java)\n"+
					"THOMAS Alexandre - alex.thomas@netcourrier.com\n"+
					"AUDRU Cedric - cedricaudru@hotmail.com\n"+
					"ganttproject.sourceforge.net\n"+
					"Version "+version, language.about(),
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);*/
				GanttDialogInfo gdi = new GanttDialogInfo(this,language, GanttDialogInfo.INFO,
								GanttDialogInfo.YES_OPTION,
								"GanttProject (Java)\n"+
								"THOMAS Alexandre - alex.thomas@netcourrier.com\n"+
								"AUDRU Cedric - cedricaudru@hotmail.com\n"+
								"ganttproject.sourceforge.net\n"+
								"Version "+version
								,language.about());
				gdi.show();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals("Fran�ais"))
			{
				language=new GanttLanguageFrench();
				changeLanguage();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals("English"))
			{
				language=new GanttLanguageEnglish();
				changeLanguage();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals("Espa�ol"))
			{
				language=new GanttLanguageSpanish();
				changeLanguage();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals("Portugues"))
			{
				language=new GanttLanguagePortugues();
				changeLanguage();
			}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if(arg.equals("Deutsch"))
			{
				language=new GanttLanguageGerman();
				changeLanguage();
			}			
		}
	}







	/** Create a default project*/
	public void createDefaultTree(GanttTree treePanel) {

		GanttTask T1 = new GanttTask ("Define Specifications",	new GanttCalendar(2003,0,7), 15);
		GanttTask T2 = new GanttTask ("Generate concepts",		new GanttCalendar(2003,0,19), 9);
		GanttTask T3 = new GanttTask ("Select top 2 concepts",	new GanttCalendar(2003,0,28), 13);
		GanttTask T4 = new GanttTask ("MQ Represented",			new GanttCalendar(2003,1,2), 1);
		GanttTask T5 = new GanttTask ("Profil motor power",		new GanttCalendar(2003,0,14), 23);

		DefaultMutableTreeNode tn1, tn2, tn3, tn4, tn5;

		tn1=treePanel.addObject(T1, null);
		tn2=treePanel.addObject(T2, null);
		tn3=treePanel.addObject(T3, null);
		tn4=treePanel.addObject(T4, null);
		tn5=treePanel.addObject(T5, null);

		GanttTask T11 = new GanttTask ("Identify customers",	new GanttCalendar(2003,0,7), 1);
		GanttTask T12 = new GanttTask ("Interview 10 customers",new GanttCalendar(2003,0,8), 3);
		GanttTask T13 = new GanttTask ("Interpret requirements",new GanttCalendar(2003,0,12), 2);
		GanttTask T14 = new GanttTask ("Benchmark products",	new GanttCalendar(2003,0,14), 4);
		GanttTask T15 = new GanttTask ("Define target PDS",		new GanttCalendar(2003,0,19), 2);
		GanttTask T16 = new GanttTask ("Target Pds Released",	new GanttCalendar(2003,0,21), 1);
        treePanel.addObject(T11, tn1);treePanel.addObject(T12, tn1);treePanel.addObject(T13, tn1);
		treePanel.addObject(T14, tn1);treePanel.addObject(T15, tn1);treePanel.addObject(T16, tn1);

		GanttTask T21 = new GanttTask ("Review Comp products",	new GanttCalendar(2003,0,19), 2);
		GanttTask T22 = new GanttTask ("Serch patents",			new GanttCalendar(2003,0,20), 2);
		GanttTask T23 = new GanttTask ("Brainstorm concepts",	new GanttCalendar(2003,0,20), 8);
        treePanel.addObject(T21, tn2);treePanel.addObject(T22, tn2);treePanel.addObject(T23, tn2);

		GanttTask T51 = new GanttTask ("Design test standard",	new GanttCalendar(2003,0,14), 13);
		GanttTask T52 = new GanttTask ("Build test standard",	new GanttCalendar(2003,0,28), 9);
        treePanel.addObject(T51, tn5);treePanel.addObject(T52, tn5);

		T11.setDepend("Interview 10 customers");
		T12.setDepend("Interpret requirements");
		T13.setDepend("Benchmark products");
		T14.setDepend("Define target PDS");
		T15.setDepend("Target Pds Released");
		T15.setDepend("Review Comp products");
		T15.setDepend("Serch patents");
		T15.setDepend("Brainstorm concepts");
		T23.setDepend("Select top 2 concepts");
		T3.setDepend("Profil motor power");
		T51.setDepend("Build test standard");

		T1.setPercent(69);
		T11.setPercent(100);
		T12.setPercent(100);
		T13.setPercent(100);
		T14.setPercent(75);
		T15.setPercent(30);
		T16.setPercent(10);

		T2.setPercent(16);
		T21.setPercent(40);
		T22.setPercent(10);
		T23.setPercent(13);

		T5.setPercent(38);
		T51.setPercent(66);

		T16.setBilan(true);
		T4.setBilan(true);
    }

	/** Create a new task */
	public void newTask()
	{
		tabpane.setSelectedIndex(0);
		GanttDialogNewTask nt = new GanttDialogNewTask(this,tree,area,language);
		nt.show();
	}

	/** Delete the currant task */
	public void deleteTask()
	{
		tabpane.setSelectedIndex(0);
		GanttTask selection= tree.getSelectedTask();
		if (selection ==null) return;
		/*int res = JOptionPane.showConfirmDialog(this, language.msg3()+selection.toString()+"??", language.warning(),
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);*/
		GanttDialogInfo gdi = new GanttDialogInfo(this,language, GanttDialogInfo.WARNING,
					GanttDialogInfo.YES_NO_OPTION,
					language.msg3()+selection.toString()+"??"
					,language.warning());
		gdi.show();					
		
		if(gdi.res==GanttDialogInfo/*JOptionPane*/.YES)
		{
			GanttTask ttask = tree.getSelectedTask();
			DefaultMutableTreeNode father = tree.getFatherNode(ttask);

			tree.removeCurrentNode();

			GanttTask taskFather = (GanttTask)father.getUserObject();
			taskFather.refreshDateAndAdvancement(tree);
			father.setUserObject(taskFather);
			area.repaint();
		}

	}
	
	
	/** Edit task parameters */
	public void propertiesTask()
	{
		tabpane.setSelectedIndex(0);
		GanttTask t = tree.getSelectedTask();
		if(t==null || tree.getNode(t.toString()).isRoot()) return;
		else
		{
			GanttDialogProperties pd = new GanttDialogProperties(this,tree,t,area,language);
			pd.show();
		}
	}

	/** Edit the notes of the task. */
	public void notesTask()
	{
		tabpane.setSelectedIndex(0);
		GanttTask t = tree.getSelectedTask();
		if(t==null || tree.getNode(t.toString()).isRoot()) return;
		else
		{
			GanttDialogNotes nd = new GanttDialogNotes(this,tree,t,language);
			nd.show();
		}
	}

	/** Export the calencar on a png file */
	public void export()
	{
		
		
		try{
			JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter(new GanttPNGFileFilter());
			fc.addChoosableFileFilter(new GanttHTMLFileFilter());
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
			   
				File file = fc.getSelectedFile();
				String type = fc.getFileFilter().getDescription();
				
				//Export in PNG file
				if(type.equals("PNG Images")) {
					GanttStoreBoolean bool= new GanttStoreBoolean();
					GanttDialogExport gde = new GanttDialogExport(this, bool, language);
					gde.show();
					if(bool.ok)
						area.export(file, bool.name, bool.percent, bool.depend);
						
				//Export in HTML
				} else if (type.equals("Web Pages")) {
					System.out.println("HTML Page export "+file.getName());
				
					GanttStoreBoolean bool= new GanttStoreBoolean();
					GanttDialogExport gde = new GanttDialogExport(this, bool, language);
					gde.show();
					if(bool.ok)
						GanttHTMLExport.save(file, projectName,decription,organization,this,tree,peop,area,bool);
				}
				
  			}		
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** Print the project */
	public void printProject()
	{
		GanttStoreBoolean bool= new GanttStoreBoolean();
		GanttDialogExport gde = new GanttDialogExport(this, bool, language);
		gde.show();
		if(bool.ok)
			area.printProject(bool.name, bool.percent, bool.depend);
	}
	
	
	/** Create a new project */
	public void newProject() 
	{
		/*int res = JOptionPane.showConfirmDialog(this, language.msg1(), language.warning(),
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);*/
		
		GanttDialogInfo gdi = new GanttDialogInfo(this,language, GanttDialogInfo.WARNING,
					GanttDialogInfo.YES_NO_CANCEL_OPTION,
					language.msg1(),language.warning());
		gdi.show();	
				
		if(gdi.res==GanttDialogInfo/*JOptionPane*/.YES)
			try {saveAsProject();}catch (Exception e) {System.out.println(e);}
		if(gdi.res==GanttDialogInfo/*JOptionPane*/.YES || gdi.res==GanttDialogInfo/*JOptionPane*/.NO)
		{
			//Clear the jtree
			tree.clearTree();
			//refresh graphic area
			area.repaint();
			
			
			projectName=new String();
			decription=new String();
			organization=new String();
			
			//reset people
			peop.reset();
			
			//change title of the frame
			this.setTitle(language.appliTitle());
			projectfile=new String("__nofilename__");
		}
	}

	/** Open a project with dialog box */
	public void openProject () throws IOException,ClassNotFoundException
	{
		//Creation d'un filechooser
		JFileChooser fc = new JFileChooser(new File(projectfile).getPath());
		
		fc.addChoosableFileFilter(new GanttXMLFileFilter());
		fc.addChoosableFileFilter(new GanttFileFilter());
		
		int returnVal = fc.showOpenDialog(GanttProject.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
		    reallyOpen (file);
		}		
	}

	/** Function to open a project from a file. */
	public void reallyOpen ( File file ) throws IOException,ClassNotFoundException
	{
		
		projectfile=file.toString();
		
		if(projectfile.endsWith(".gan")){
			GanttIO.load(file,this,tree,peop,area,language);
		} else if(projectfile.endsWith(".xml")) {
			tree.clearTree();
			GanttXMLOpen opener = new GanttXMLOpen(tree,this,peop,area);
			opener.load(projectfile);
		} else {
			GanttDialogInfo gdi = new GanttDialogInfo(this,language, GanttDialogInfo.ERROR,
				GanttDialogInfo.YES_OPTION,
				language.msg2()+projectfile,language.error());
			gdi.show();
			return;
		}
		
		bZoomIn.setEnabled(area.getZoom()==0?false:true);
		bZoomOut.setEnabled(area.getZoom()==8?false:true);
		
		
		this.setTitle(language.appliTitle()+" ["+ file.getName()+"]");
		
	}


	/** Save the project as (with a dialog file chooser) */
	public boolean saveAsProject () throws IOException
	{
		JFileChooser fc = new JFileChooser(new File(projectfile).getPath());
		
		fc.addChoosableFileFilter(new GanttXMLFileFilter());
		fc.addChoosableFileFilter(new GanttFileFilter());
		
		int returnVal = fc.showSaveDialog(GanttProject.this);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			projectfile = fc.getSelectedFile().toString();
			if(!fc.getFileFilter().accept(new File(projectfile)))
			{
				if(fc.getFileFilter().accept(new File(projectfile+".gan")))
					projectfile+=".gan";
				else if(fc.getFileFilter().accept(new File(projectfile+".xml")))
					projectfile+=".xml";
			}
			saveProject();
			return true;
		}
		
		return false;
	}



	/** Save the project on a file */
	public void saveProject () throws IOException
	{
		if((projectfile.equals("__nofilename__") && saveAsProject()) || !projectfile.equals("__nofilename__"))
		{
			if(projectfile.endsWith(".gan"))
				GanttIO.save(projectfile,projectName,decription,organization,tree,peop,area);
			else if(projectfile.endsWith(".xml"))
			{
				//System.out.println("ParserXML is in developpement");	
				
				GanttXMLSaver saver = new GanttXMLSaver(projectName, decription, organization, tree, peop,area)			;
				saver.save(projectfile, version);
			}
			

			//change title of the window
			this.setTitle(language.appliTitle()+" ["+ /*file.getName()*/new File(projectfile).getName()+"]");
		}
		
	}
	
	

	/** Function that launch the dialog to edit project properties */
	public void editSettings()
	{
		GanttDialogSettings ds = new GanttDialogSettings(this,language);
		ds.show();
	}


	/** Quit the application */
	public void quitApplication()
	{
		/*int res = JOptionPane.showConfirmDialog(this, language.msg1(), language.warning(),
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);*/
		GanttDialogInfo gdi = new GanttDialogInfo(this,language, GanttDialogInfo.WARNING,
						GanttDialogInfo.YES_NO_CANCEL_OPTION,
						language.msg1(),language.warning());
		gdi.show();
		
		if(gdi.res==GanttDialogInfo.YES)
		{
			try {
				saveAsProject();
			}catch (Exception e) {
				System.out.println(e);
			}
		}
		if(gdi.res==GanttDialogInfo.YES || gdi.res==GanttDialogInfo.NO)
		{
			setVisible(false);
			System. exit(0);
		}
		else setVisible(true);
	}

	/** Open the web page */
	public void openWebPage()throws IOException
	{
		try
		{
			Runtime.getRuntime().exec("netscape http://ganttproject.sourceforge.net");
		} catch (Exception e)
		{
			/*JOptionPane.showConfirmDialog(this,
						language.msg4() , language.error(),
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE );*/
			GanttDialogInfo gdi = new GanttDialogInfo(this,language, GanttDialogInfo.ERROR,
						GanttDialogInfo.YES_OPTION,
						language.msg4(),language.error());
			gdi.show();		
		}
	}
	
	/** The filter for png file */
	public class GanttPNGFileFilter extends FileFilter 
	{
 		public boolean accept(File f)
		{ return f.getName().toLowerCase().endsWith(".png") || f.isDirectory();	}
		public String getDescription()
		{ return "PNG Images"; }
 	}
	
	/** The filter for html file */
	public class GanttHTMLFileFilter extends FileFilter 
	{
 		public boolean accept(File f)
		{ return f.getName().toLowerCase().endsWith(".html") || f.getName().toLowerCase().endsWith(".htm") || f.isDirectory();	}
		public String getDescription()
		{ return "Web Pages"; }
 	}


	/** The main */
	public static void main (String [] arg)
	{
		/* Splash image */
		GanttSplash splash = new GanttSplash();
		splash.setVisible(true);
		

		/** Create main frame */
		JFrame ganttFrame = new GanttProject(arg);
       	ganttFrame.pack();
		ganttFrame.setVisible(true);
		splash.close();
		ganttFrame.addWindowListener(new GanttWindowCloser());
	}
}

