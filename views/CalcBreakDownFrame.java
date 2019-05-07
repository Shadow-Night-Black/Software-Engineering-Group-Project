package views;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.*;

import model.LogicalRunway;

public class CalcBreakDownFrame extends JFrame {

	public CalcBreakDownFrame(LogicalRunway runway) {
		super("Breakdown of Calculations");
		
		Container mainPanel = this.getContentPane();
		mainPanel.setLayout(new FlowLayout());

        JTextArea breakdown = new JTextArea();
        breakdown.setEditable(false);
        breakdown.setLineWrap(false);
        breakdown.setOpaque(false);
        breakdown.setBorder(BorderFactory.createEmptyBorder());

		breakdown.setText(runway.getSTora() +
                          "\n*****\n" +
                          "\n" + runway.getSToda() +
                          "\n*****\n" +
                          "\n" + runway.getSAsda() +
                          "\n*****\n" +
                          "\n" + runway.getSLda());
		mainPanel.add(breakdown);
		this.pack();
		this.setVisible(true);
	}
	
}
