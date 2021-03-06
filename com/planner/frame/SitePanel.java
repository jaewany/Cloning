package com.planner.frame;

import java.awt.*;

import javax.swing.*;

import com.planner.data.Enzymes;
import com.planner.utils.Finder;

import java.awt.event.*;
import java.util.ArrayList;

public class SitePanel extends JTabbedPane
{
	// Graphical representation
	private final int VECTOR_SEQUENCE = 1;
	private final int INSERT_SEQUENCE = 2;
	
	private InputPanel inputPanel = null;
	
	private JPanel vCuttingOptionPanel = null;
	private JPanel iCuttingOptionPanel = null;
	
	private SiteDrawingPanel panel1 = null;
	private SiteDrawingPanel panel2 = null;
	
	private JPanel vectorPanel = null;
	private JPanel insertPanel = null;
	
	// Text representation
	private JTextArea vectorSequence = null;
	private JTextArea insertSequence = null;
	
	private JPanel vectorTextPanel = null;
	private JPanel insertTextPanel = null;
	
	private ArrayList<Integer> vDisplayEnzymeID = null;
	private ArrayList<Integer> vDisplayIndex = null;
	private ArrayList<Integer> vEnzyme = null;
	private ArrayList<Integer> vIndex = null;
	
	private ArrayList<Integer> iDisplayEnzymeID = null;
	private ArrayList<Integer> iDisplayIndex = null;
	private ArrayList<Integer> iEnzyme = null;
	private ArrayList<Integer> iIndex = null;

	private JRadioButton vOneCut = null;
	private JRadioButton vTwoCut = null;
	private JRadioButton vThreeCut = null;
	private JRadioButton vAllCut = null;

	private JRadioButton iOneCut = null;
	private JRadioButton iTwoCut = null;
	private JRadioButton iThreeCut = null;
	private JRadioButton iAllCut = null;

	private int vectorSize;
	private int insertSize;
	
	private double vectorScale;
	private double insertScale;

	public SitePanel()
	{
		/*
		 * Graphical representation
		 */
		vCuttingOptionPanel = new JPanel(new FlowLayout());
		iCuttingOptionPanel = new JPanel(new FlowLayout());
		
		vectorPanel = new JPanel(new BorderLayout());
		insertPanel = new JPanel(new BorderLayout());
		
		vOneCut = new JRadioButton("Single cutters"); vOneCut.setSelected(true);
		vTwoCut = new JRadioButton("Double cutters");
		vThreeCut = new JRadioButton("Triple cutters");
		vAllCut = new JRadioButton("All cutters");
		
		ButtonGroup vGroupInsert = new ButtonGroup();
		vGroupInsert.add(vOneCut);
		vGroupInsert.add(vTwoCut);
		vGroupInsert.add(vThreeCut);
		vGroupInsert.add(vAllCut);
		
		iOneCut = new JRadioButton("Single cutters"); iOneCut.setSelected(true);
		iTwoCut = new JRadioButton("Double cutters");
		iThreeCut = new JRadioButton("Triple cutters");
		iAllCut = new JRadioButton("All cutters");
		
		ButtonGroup iGroupInsert = new ButtonGroup();
		iGroupInsert.add(iOneCut);
		iGroupInsert.add(iTwoCut);
		iGroupInsert.add(iThreeCut);
		iGroupInsert.add(iAllCut);
		
		vCuttingOptionPanel.add(vOneCut);
		vCuttingOptionPanel.add(vTwoCut);
		vCuttingOptionPanel.add(vThreeCut);
		vCuttingOptionPanel.add(vAllCut);
		
		iCuttingOptionPanel.add(iOneCut);
		iCuttingOptionPanel.add(iTwoCut);
		iCuttingOptionPanel.add(iThreeCut);
		iCuttingOptionPanel.add(iAllCut);
		
		panel1 = new SiteDrawingPanel(VECTOR_SEQUENCE);
		vectorPanel.add(panel1, BorderLayout.CENTER);
		vectorPanel.add(vCuttingOptionPanel, BorderLayout.SOUTH);
		addTab("Vector Visual", null, vectorPanel, "");
		setMnemonicAt(0, KeyEvent.VK_1);
		
		// Panel #2
		panel2 = new SiteDrawingPanel(INSERT_SEQUENCE);
		insertPanel.add(panel2, BorderLayout.CENTER);
		insertPanel.add(iCuttingOptionPanel, BorderLayout.SOUTH);
		addTab("Insert Visual", null, insertPanel, "");
		setMnemonicAt(1, KeyEvent.VK_2);
		
		/*
		 * Text representation
		 */
		vectorTextPanel = new JPanel(new BorderLayout());
		insertTextPanel = new JPanel(new BorderLayout());
		
		JLabel vectorLabel = new JLabel("Vector Sequence");
		JLabel insertLabel = new JLabel("Insert Sequence");
		
		vectorSequence = new JTextArea();
		vectorSequence.setFont(new Font("LucidaTypewriterRegular", Font.BOLD, 10));
		vectorSequence.setBorder(BorderFactory.createEtchedBorder());
		vectorSequence.setEditable(false);
		
		insertSequence = new JTextArea();
		insertSequence.setFont(new Font("LucidaTypewriterRegular", Font.BOLD, 10));
		insertSequence.setBorder(BorderFactory.createEtchedBorder());
		insertSequence.setEditable(false);
		
		JScrollPane scroll_1 = new JScrollPane (vectorSequence, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scroll_2 = new JScrollPane (insertSequence, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		vectorTextPanel.add(vectorLabel, BorderLayout.NORTH);
		vectorTextPanel.add(scroll_1, BorderLayout.CENTER);
		addTab("Vector Sequence", null, vectorTextPanel, "");
		
		insertTextPanel.add(insertLabel, BorderLayout.NORTH);
		insertTextPanel.add(scroll_2, BorderLayout.CENTER);
		addTab("Insert Sequence" , null, insertTextPanel, "");
	}
	
	
	public class SiteDrawingPanel extends JPanel
	{
		private int constant = 0;
		
		public SiteDrawingPanel(int constant)
		{
			super();
			setBackground(Color.GRAY);
			this.constant = constant;
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			g.setColor(Color.WHITE);
			
			g.drawLine(100, 300, 950, 300);
			g.drawString("0", 80, 300);

			if(constant == VECTOR_SEQUENCE)
			{
				g.drawString(String.valueOf(inputPanel.getVector().getText().length()), 960, 300);		
			}
			else if(constant == INSERT_SEQUENCE)
			{
				g.drawString(String.valueOf(inputPanel.getInsert().getText().length()), 960, 300);		
			}

			// Mark for graphical update
			vEnzyme = Finder.getEnzyme(inputPanel.getVector().getText());
			vIndex = Finder.getIndex(inputPanel.getVector().getText());
			iEnzyme = Finder.getEnzyme(inputPanel.getInsert().getText());
			iIndex = Finder.getIndex(inputPanel.getInsert().getText());
			
			// Update graphics
			vectorSize = inputPanel.getVector().getText().length();
			insertSize = inputPanel.getInsert().getText().length();

			vectorScale = (double) 850 / vectorSize;
			insertScale = (double) 850 / insertSize;
			
			if(constant == VECTOR_SEQUENCE)
			{
				MainPanel.println(String.valueOf(vEnzyme.size()));
				for(int j = 0; j < vEnzyme.size(); j++)
				{
					g.drawLine((int) (vIndex.get(j) * vectorScale) + 100, 290, (int) (vIndex.get(j) * vectorScale) + 100, 310);
				}				
			}
			
			else if(constant == INSERT_SEQUENCE)
			{
				for(int j = 0; j < iEnzyme.size(); j++)
				{
					g.drawLine((int) (iIndex.get(j) * insertScale) + 100, 290, (int) (iIndex.get(j) * insertScale) + 100, 310);
				}
			}
		}
	}
	
	public void getUpdateSource(InputPanel inputPanel)
	{
		this.inputPanel = inputPanel;
	}
	
	public void update()
	{
		update(inputPanel.getVector().getText(), inputPanel.getInsert().getText());
		
		// Finalize
		panel1.repaint();
		panel2.repaint();
	}
	
	public void update(String vectorText, String insertText)
	{
		// Vector
		StringBuilder vBuilder = new StringBuilder("0\t");
		
		vEnzyme = Finder.getEnzyme(inputPanel.getVector().getText());
		vIndex = Finder.getIndex(inputPanel.getVector().getText());
		
		int length = vectorText.length();
		
		for(int j = 0; j < vectorText.length(); j++)
		{
			MainPanel.println("Checking... " + String.valueOf(j) + " out of " + String.valueOf(length));
				
			while(vIndex.contains(j))
			{
				vBuilder.append("(" + vIndex.get(vIndex.indexOf(j)) + "/" + Enzymes.getName(vEnzyme.get(vIndex.indexOf(j))) + ") ");
				
				int temp = vIndex.indexOf(j);
				
				vIndex.set(temp, -1);
				vEnzyme.set(temp, -1);
			}
			
			if(j % 20 == 0 && j != 0)
			{
				vBuilder.append("\t  " + (j - 1) + "\n\n" + j + "\t");
			}
			
			vBuilder.append(String.valueOf(vectorText.charAt(j) + " "));
		}
		vectorSequence.setText(vBuilder.toString());
		
		MainPanel.println("Vector done.");
		
		// Insert
		StringBuilder iBuilder = new StringBuilder("0\t");
		
		iEnzyme = Finder.getEnzyme(inputPanel.getInsert().getText());
		iIndex = Finder.getIndex(inputPanel.getInsert().getText());
		
		length = insertText.length();
		
		for(int j = 0; j < insertText.length(); j++)
		{
			MainPanel.println("Checking... " + String.valueOf(j) + " out of " + String.valueOf(length));
			
			while(iIndex.contains(j))
			{
				iBuilder.append("(" + iIndex.get(iIndex.indexOf(j)) + "/" + Enzymes.getName(iEnzyme.get(iIndex.indexOf(j))) + ") ");
				
				int temp = iIndex.indexOf(j);
				
				iIndex.set(temp, -1);
				iEnzyme.set(temp, -1);
			}
			
			if(j % 20 == 0 && j != 0)
			{
				iBuilder.append("\t  " + (j - 1) + "\n\n" + j + "\t");
			}
			
			iBuilder.append(String.valueOf(insertText.charAt(j) + " "));
		}
		insertSequence.setText(iBuilder.toString());
		
		MainPanel.println("Insert done.");
	}
}
