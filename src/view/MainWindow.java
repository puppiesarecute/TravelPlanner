package view;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import view.MyCanvas;
import fileprocessor.XlsReader;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTextField;

import dijkstra.TSP;

public class MainWindow extends JFrame
{

    /**
     * 
     */
    DefaultListModel<String> listModel = new DefaultListModel<String>();
    JList<String> cityList = new JList<String>(listModel);
    static final long serialVersionUID = 1L;
    JTextField textFieldSalesmanName;
    MyCanvas canvas;
    List<String> selectedCities = new ArrayList<String>();

    /**
     * Create the frame.
     */
    public MainWindow()
    {
	setTitle("Travel Planner");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 1380, 1034);
	getContentPane().setLayout(null);

	JPanel panel_left = new JPanel();
	panel_left.setBounds(10, 11, 426, 954);
	getContentPane().add(panel_left);
	panel_left.setLayout(null);

	JLabel lblSelectCitiesTo = new JLabel("Select cities to travel to:");
	lblSelectCitiesTo.setFont(new Font("Verdana", Font.BOLD, 13));
	lblSelectCitiesTo.setBounds(10, 33, 200, 14);
	panel_left.add(lblSelectCitiesTo);

	List<String> citiesFromFile = XlsReader.getCitiesFromFile();
	Collections.sort(citiesFromFile);
	JComboBox<?> cityComboBox = new JComboBox<Object>(citiesFromFile.toArray());
	cityComboBox.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
		String selected = (String) cityComboBox.getSelectedItem();
		listModel.addElement(selected);
		selectedCities.add(selected);
	    }
	});
	cityComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
	cityComboBox.setBounds(10, 74, 391, 32);
	panel_left.add(cityComboBox);

	JButton btnReset = new JButton("Reset");
	btnReset.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		listModel.clear();
		selectedCities.clear();
	    }
	});
	btnReset.setFont(new Font("Verdana", Font.BOLD, 13));
	btnReset.setBounds(300, 200, 101, 32);
	panel_left.add(btnReset);

	JLabel lblNoteAllPlanned = new JLabel("Note: All planned trips start from \r\nand end at ");
	lblNoteAllPlanned.setFont(new Font("Verdana", Font.ITALIC, 13));
	lblNoteAllPlanned.setBounds(10, 117, 391, 32);
	panel_left.add(lblNoteAllPlanned);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(10, 243, 391, 435);
	panel_left.add(scrollPane);

	// Create the list and put it in a scroll pane.

	cityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	cityList.setSelectedIndex(0);
	// JList cityList = new JList();
	scrollPane.setViewportView(cityList);

	JButton btnCalculate = new JButton("Calculate");
	btnCalculate.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		// call TSP algorithm to get the orders of cities
		List<String> citiesPlanned = new ArrayList<String>();
		citiesPlanned = TSP.greedyFriendlyOutput(selectedCities);

		// draw on canvas
		canvas.update(canvas.getGraphics());
		canvas.drawConnectorLine(citiesPlanned.get(0), citiesPlanned.get(1), Color.RED);
		for (int i = 1; i < citiesPlanned.size() - 1; i++)
		{
		    canvas.drawConnectorLine(citiesPlanned.get(i), citiesPlanned.get(i + 1), Color.BLACK);
		}
		canvas.drawConnectorLine(citiesPlanned.get(citiesPlanned.size() - 1), citiesPlanned.get(0), Color.BLUE);

		// re arrange the list on gui
		listModel.clear();
		for (String string : citiesPlanned)
		{
		    listModel.addElement(string);
		}
	    }
	});
	btnCalculate.setFont(new Font("Verdana", Font.BOLD, 13));
	btnCalculate.setBounds(232, 730, 169, 32);
	panel_left.add(btnCalculate);

	JButton btnRemove = new JButton("Remove");
	btnRemove.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		int index = cityList.getSelectedIndex();
		if (index > -1)
		{
		    listModel.remove(index);
		    selectedCities.remove(index);
		}
		else
		{
		    JOptionPane.showMessageDialog(scrollPane, "Select a city to remove");
		}
	    }
	});
	btnRemove.setFont(new Font("Verdana", Font.BOLD, 13));
	btnRemove.setBounds(189, 200, 101, 32);
	panel_left.add(btnRemove);

	JLabel lblCopenhagenCity = new JLabel("Copenhagen city");
	lblCopenhagenCity.setFont(new Font("Verdana", Font.ITALIC, 13));
	lblCopenhagenCity.setBounds(10, 145, 391, 32);
	panel_left.add(lblCopenhagenCity);

	JButton btnSave = new JButton("Save");
	btnSave.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		// TODO save the calculated cities
		XlsReader.saveFile(selectedCities);
	    }
	});
	btnSave.setFont(new Font("Verdana", Font.BOLD, 13));
	btnSave.setBounds(10, 730, 169, 32);
	panel_left.add(btnSave);

	textFieldSalesmanName = new JTextField();
	textFieldSalesmanName.setBounds(10, 689, 391, 32);
	panel_left.add(textFieldSalesmanName);
	textFieldSalesmanName.setColumns(10);
	textFieldSalesmanName.setText("Enter saleman's name");

	JPanel map_panel = new JPanel();
	map_panel.setBounds(446, 10, 899, 954);
	getContentPane().add(map_panel);

	canvas = new MyCanvas();
	canvas.setBounds(0, 0, 899, 954); // (468, 59, 899, 954);
	map_panel.add(canvas);

    }
}
