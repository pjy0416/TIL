package seunghhon;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Question3 {
	public static void main(String[] args) {
		new VendingMachine();
	}
}

class VendingMachine extends JFrame implements ActionListener{
	private final static int[] prices = {1000, 800, 1200};		//coke, pepsi, juice ����
	private final static int[] preparedChange = {50000, 10000, 1000, 500, 100, 50, 10, 1};
	
	//Menu Btns
	private JButton cokeBtn;
	private JButton pepsiBtn;
	private JButton juiceBtn;
	
	//Charge Texts
	private JTextField chargeText;
	private JLabel chargeLabel;
	
	//Change Texts
	private JTextField[] changeText;
	private JLabel[] changeLabel;
	
	private int cokeCnt =0;
	private int pepsiCnt =0;
	private int juiceCnt =0;
	
	private int charge =0;
	private int change =0;
	
	public VendingMachine() {
		
	  super("���Ǳ�");	// "title ���Ǳ�" �� super
	  setSize(400, 500);	// Frame ������ ����
	  
	  setLocationRelativeTo(null);	// ȭ�� �߾� ����
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  Container contentPane = this.getContentPane();
	  
	  JPanel mainPanel = new JPanel();
	  JPanel btnPanel = new JPanel();
	  JPanel chargePanel = new JPanel();
	  JPanel changePanel = new JPanel();
	  
	  mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));		
	  changePanel.setLayout(new BoxLayout(changePanel, BoxLayout.Y_AXIS));
	  
	  makeBtns(btnPanel);
	  makeChargeText(chargePanel);
	  makeChangeText(changePanel);
	  setChangeLabel(changeLabel);
	  
	  setBtnSize();
	  
	  btnPanel.setBorder(BorderFactory.createEmptyBorder(5 , 15 , 15 , 15));
	  
	  mainPanel.add(btnPanel);
	  mainPanel.add(chargePanel);
	  mainPanel.add(changePanel);
	  
	  contentPane.add(mainPanel);	// ���������� container�� panel�� �߰�
	  
	  setVisible(true);
	 }
	
	private void setBtnSize() {
		Dimension dimen = new Dimension(100,50);
		cokeBtn.setPreferredSize(dimen);
		pepsiBtn.setPreferredSize(dimen);
		juiceBtn.setPreferredSize(dimen);
	}

	private void makeBtns(JPanel pane) {
		cokeBtn = new JButton("Coke-1000");
		pepsiBtn = new JButton("Pepsi-800");
		juiceBtn = new JButton("Juice-1200");
		
		cokeBtn.addActionListener(this);
		pepsiBtn.addActionListener(this);
		juiceBtn.addActionListener(this);
		
		pane.add(cokeBtn);
		pane.add(pepsiBtn);
		pane.add(juiceBtn);
	}
	
	private void makeChargeText(JPanel pane) {
		chargeText = new JTextField(10);
		chargeLabel = new JLabel("���� �ݾ� : ");
		chargeText.setToolTipText("100�� ������ ���ڸ� �Է��ϼ���");
		pane.add(chargeLabel);
		pane.add(chargeText);
	}
	
	private void makeChangeText(JPanel pane) {
		changeText = new JTextField[8];
		changeLabel = new JLabel[8];
		
		for(int i=0; i<8; i++) {
			changeText[i] = new JTextField(8);
			changeLabel[i] = new JLabel();
			changeLabel[i].setPreferredSize(new Dimension(50,50));
			JPanel tmpPan = new JPanel();
			tmpPan.add(changeLabel[i]);
			tmpPan.add(changeText[i]);
			pane.add(tmpPan);
		}
	}
	
	private void setChangeLabel(JLabel[] changeLabel) {
		changeLabel[0].setText("������ : ");
		changeLabel[1].setText("���� : ");
		changeLabel[2].setText("õ�� : ");
		changeLabel[3].setText("500�� : ");
		changeLabel[4].setText("100�� : ");
		changeLabel[5].setText("50�� : ");
		changeLabel[6].setText("10�� : ");
		changeLabel[7].setText("1�� : ");
		
		for(int i=0; i<8; i++) {
			changeLabel[i].setHorizontalAlignment(JLabel.RIGHT);
		}
	}
	
	void setChangeTexts(int[] changes) {
		int len = changes.length;
		
		for(int i=0; i<len; i++) {
			String changeStr = String.valueOf(changes[i]);
			changeText[i].setText(changeStr);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {	//�׼� ������ ������
		String chargeStr = chargeText.getText();
		
		try {
			int chargeInt = Integer.parseInt(chargeStr);
			
			if(chargeInt != charge) {		// �߰��� �Է±ݾ��� �ٲ�� count �ʱ�ȭ
				cokeCnt =0;
				pepsiCnt =0;
				juiceCnt =0;
			}
			
			charge = chargeInt;
			
			if (e.getSource().equals(cokeBtn)) {
				cokeCnt++;
			} else if(e.getSource().equals(pepsiBtn)) {
				pepsiCnt++;
			} else if(e.getSource().equals(juiceBtn)) {
				juiceCnt++;
			}
			
			change = totalChange(charge, cokeCnt, pepsiCnt, juiceCnt);
			
			if(change < 0) {
				JOptionPane.showMessageDialog(this, "�ܿ��ݾ��� �����մϴ�.");
				
			} else {
				setChangeTexts(calChange(change));
				JOptionPane.showMessageDialog(this, "��ī�ݶ� : " + cokeCnt + "\n��� : " + pepsiCnt + "\n�꽺 : " + juiceCnt + "\n�Ž����� : " + change);
			}

			
		}catch (Exception exception) {
			JOptionPane.showMessageDialog(this, "���ڿ� ',' ���� ������ ���ڸ� �Է����ּ���.");
		}
		
	}
	
	///////////////////////////////////////////////////////// cal methods ///////////////////////////////////////////////////////
	
	private static int totalChange(int charge, int cokeCnt, int pepCnt, int juiceCnt){
		int change = charge - (cokeCnt*prices[0] + pepCnt*prices[1] + juiceCnt*prices[2]);
		
		return change;
	}
	
	private static int[] calChange(int change) {
		int[] changes = new int[8];					// �Ž����� �迭
		
		int len = preparedChange.length;
		int tmp = change;
		
		for(int i=0; i<len; i++) {
			changes[i] = tmp/preparedChange[i];
			tmp = tmp % preparedChange[i];
		}
		
		return changes;
	}
	
}
