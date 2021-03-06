/***************************************************************************
                           GanttXMLOpen.java  -  description
                             -------------------
    begin                : feb 2003
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
import java.lang.String;
import java.util.ArrayList;
import java.lang.Exception;
import java.io.*;
import java.awt.event.*;
import javax.swing.tree.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;


import java.util.ArrayList;
import java.lang.Integer;
import javax.swing.tree.DefaultMutableTreeNode;


public class GanttXMLOpen 
{
	
	/** The tree that contains data */
	GanttTree treePanel;
	
	/** The main frame */
	GanttProject prj;
	
	/** The ressources */
	GanttPeoplePanel peop;
	
	/**0-->description of project, 1->note for task */
	int typeChar=-1;
	
	/** The resource panel */
	GanttPeople people=new GanttPeople();
	
	/** The graphic area */
	GanttGraphicArea area;
	
	/** A stack of all father */
	ArrayList lot = new ArrayList();
	
	/*List for depends */
	ArrayList lod = new ArrayList();
	
	/** The id of the current task*/
	int taskID;
	
	/** Constructor */
	public GanttXMLOpen(GanttTree tree, GanttProject appli, GanttPeoplePanel resources, GanttGraphicArea gra)
	{
		this.treePanel = tree;
		this.prj = appli;
		this.peop = resources;
		this.area = gra;
	}
	
	public void load(String filename)
	{
	                
	     // Use an instance of ourselves as the SAX event handler
	     DefaultHandler handler = new GanttXMLParser();

        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
           
            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( new File(filename), handler);

		
        } catch (SAXParseException spe) {
           // Error generated by the parser
           System.out.println("\n** Parsing error"
              + "!!!!, line " + spe.getLineNumber()
              + ",+++ uri " + spe.getSystemId());
           System.out.println(" ## " + spe.getMessage() );

           // Use the contained exception, if any
           Exception  x = spe;
           if (spe.getException() != null)
               x = spe.getException();
           x.printStackTrace();

        } catch (SAXException sxe) {
           // Error generated by this application
           // (or a parser-initialization error)
           Exception  x = sxe;
           if (sxe.getException() != null)
               x = sxe.getException();
           x.printStackTrace();

        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();

        } catch (IOException ioe) {
           // I/O error
           ioe.printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
 
	
	}


	class GanttXMLParser extends DefaultHandler
	{
	    StringBuffer textBuffer;
    
	
    	//===========================================================
	    // SAX DocumentHandler methods
	    //===========================================================

	
	    public void endDocument()
	    throws SAXException
	    {
	    	for(int i=0;i<lod.size();i++)
			{
				GanttDependStructure ds = (GanttDependStructure)lod.get(i);
				GanttTask task1 = treePanel.getTaskByNumber(ds.t1);
				GanttTask task2 = treePanel.getTaskByNumber(ds.t2);
				task1.setDepend(task2.toString());
			}
	    }
	
	    public void startElement(String namespaceURI,
	                             String sName, // simple name
	                             String qName, // qualified name
	                             Attributes attrs)
	    throws SAXException
	    {
	        
	        String eName = sName; // element name
	        if ("".equals(eName)) eName = qName; // not namespaceAware
	        
			
			if(eName.equals("description")) typeChar=0;
			if(eName.equals("notes")) typeChar=1;
			else if(eName.equals("resource")) people=new GanttPeople();
			
			GanttTask task=new GanttTask(new String(), new GanttCalendar(), 1);
					
	        if (attrs != null) {
	            for (int i = 0; i < attrs.getLength(); i++) {
	                String aName = attrs.getLocalName(i); // Attr name 
	                if ("".equals(aName)) aName = attrs.getQName(i);
	        		
					
					
					//The project part
					if(eName.equals("project"))
					{
						if(aName.equals("name"))
							prj.projectName=attrs.getValue(i);
						else if(aName.equals("company"))
							prj.organization=attrs.getValue(i);
						else if(aName.equals("view-date"))
							area.setDate(new GanttCalendar(attrs.getValue(i)));
						else if(aName.equals("view-zoom"))
							area.setZoom(new Integer(attrs.getValue(i)).hashCode());
					}
					
					
					else if(eName.equals("resource"))
					{
						if(aName.equals("name"))
							people.setName(attrs.getValue(i));
						else if(aName.equals("function"))
							people.setFunction(new Integer(attrs.getValue(i)).hashCode());
						else if(aName.equals("contacts"))
							people.setMail(attrs.getValue(i));										
					}
					
					else if(eName.equals("task"))
					{
						if(aName.equals("id"))
							taskID=new Integer(attrs.getValue(i)).hashCode();
						else if(aName.equals("name"))
							task.setName(attrs.getValue(i));
						else if(aName.equals("meeting"))
							task.setBilan(attrs.getValue(i).equals("true"));
						else if(aName.equals("start"))
							task.setStart(new GanttCalendar(attrs.getValue(i)));
						else if(aName.equals("duration"))
							task.setLength(new Integer(attrs.getValue(i)).hashCode());
						else if(aName.equals("complete"))
							task.setPercent(new Integer(attrs.getValue(i)).hashCode());
					}
					
					else if(eName.equals("depend"))
					{
						if(aName.equals("id"))
							lod.add(new GanttDependStructure(taskID, new Integer(attrs.getValue(i)).hashCode()));
					}
	            }
	        }
	       
			if(eName.equals("resource")) 
				peop.newHuman(people);
			else if(eName.equals("task"))
			{
				
				DefaultMutableTreeNode node=null;
				if(lot.size()!=0)
					node=treePanel.getNode(((GanttTask)(lot.get(lot.size()-1))).toString());
					
				lot.add(task);
				treePanel.addObject(task, node);
			}
	    }
	
	    public void endElement(String namespaceURI,
	                           String sName, 
	                           String qName  
	                          )
	    throws SAXException
	    {

			if(qName.equals("task"))
			{
				lot.remove(lot.size()-1);
			}
			
	    }
	
	    public void characters(char buf[], int offset, int len)
	    throws SAXException
	    {
	       	String s = new String(buf, offset, len);
			
			//desctiption of the project
			if(typeChar==0)
			{
				prj.decription=s.trim();
				typeChar=-1;
			}
			//note for a task
			else if(typeChar==1)
			{
				if(lot.size()!=0)
				{
					GanttTask task = (GanttTask)lot.get(lot.size()-1);
					task.setNotes(s.trim());
				}
				typeChar=-1;
			}
	    }
		   
	}
	
	
	
	class GanttDependStructure
	{
		public int t1, t2;
		public GanttDependStructure(int a, int b) { t1 = a; t2 = b;}
	}
}
	
	
	





























/**
  * Classe for save the project in a XML file
  */
/*public class GanttXMLSaver
{

	private GanttTree tree;
	private GanttPeoplePanel peop;
	private GanttGraphicArea area;
	private String name;
	private String desc;
	private String orga;

	ArrayList number= new ArrayList();
	
	int cpt;

	public GanttXMLSaver(String name, String desc, String orga, GanttTree tree, GanttPeoplePanel peop,GanttGraphicArea area)
	{
		this.tree = tree;
		this.peop = peop;
		this.area = area;
		this.name = name;
		this.desc = desc;
		this.orga = orga;
	}
	
	
	public String getMrProjectDate(GanttCalendar date, int type)
	{
		String s="";
		if (type==0) s="T000000Z";
		else if(type==1) s="T080000Z";
		else if(type==2) s="T170000Z";
		
		
		int month=(date.getMonth()+1);
		
		//System.out.println("Une data : "+date);
		
		return (date.getYear()+((month>=10)?"":"0")+month+""+date.getDate()+s);	
	}
	
	
	public void writeTask(DataOutputStream fout,ArrayList lot, int id, String space)
	{
		//<task id="2" name="Tache 2" note="" work="201600" start="20030218T080000Z" end="20030226T170000Z" percent-complete="0"/>
		try{
			if(id>=lot.size()) return;
			GanttTask task = (GanttTask) ((DefaultMutableTreeNode)lot.get(id)).getUserObject();
			boolean haschild=false;
		
			ArrayList child = tree.getAllChildTask(task.toString());
			if(child.size()!=0)
				haschild=true;
		
			//System.out.println(task);
			
			
			
			number.add(new Integer(id));
		
			cpt++;
			fout.writeBytes(space+"<task id=\""+id+"\" name=\""+task.toString()+"\" note=\""+task.getNotes()+"\" work=\""+ (task.getLength()*28800)+
				"\" start=\""+getMrProjectDate(task.getStart(),1)+"\" end=\""+getMrProjectDate(task.getEnd(),2)+"\" percent-complete=\""+task.getPercent()+
				"\""+((haschild)?"":"/")+">\n");	
			
			if(haschild)
			{
				for(int i=0;i<child.size();i++)
				{
					GanttTask task2 = (GanttTask) ((DefaultMutableTreeNode)child.get(i)).getUserObject();
					int newid = -1;//lot.lastIndexOf(task2);
					
					for(int j=0;j<lot.size();j++)
					{
						String a = task2.toString();
						String b = lot.get(j).toString();
						
						if(a.equals(b))
							newid=j;
					}
					writeTask(fout,lot,newid,space+"  ");
				}
			
				fout.writeBytes(space+"</task>\n");
			}
			
			
			//System.out.println("#####################################################");
			
			if(tree.getNode(task.toString()).isLeaf() && !tree.getFatherNode(task).isRoot())
				return;
			
			if(id==lot.size()-1) return;
			
			else
				writeTask(fout,lot,cpt,space);
			
		
		}catch(Exception e){System.out.println(e);}
	}
	
	
	public void save(String filename)
	{
		try{
			
			
			File file = new File (filename);			
			DataOutputStream fout = new DataOutputStream(new FileOutputStream(file));
			//String space="    ";
			number.clear();
			
			//write header
			fout.writeBytes("<?xml version=\"1.0\"?>\n");
			
			//<project name="" company="" manager="" project-start="20030213T000000Z" mrproject-version="2">
			
			fout.writeBytes("<project name=\""+name+"\" company=\""+orga+"\" manager=\"\" project-start=\""
						+getMrProjectDate(area.beg,0)+"\" mrproject-version=\"2\">\n");
		
			
		
			ArrayList lot = tree.getAllTasks();
			
			//begin of tasks
			fout.writeBytes("  <tasks>\n");
			
			cpt=1;
			writeTask(fout,lot,1,"    ");
			
			
			//end of tasks
			fout.writeBytes("  </tasks>\n");
			
			//Nothing for the moment
			fout.writeBytes("  <resource-groups/>\n");
			fout.writeBytes("  <resources/>\n");
			fout.writeBytes("  <allocations/>\n");
		
			//end of project
			fout.writeBytes("</project>\n");
			fout.close();
		
		
		}catch(Exception e)
		{
			System.out.println(e);
			System.out.println("Erreur dans la sauvegarde du fichier");
		}
	}

}*/


