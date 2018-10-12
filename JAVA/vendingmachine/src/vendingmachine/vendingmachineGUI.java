package vendingmachine;

/*
��ũĿ��: ��2 Ŀ��1 ��ũ1  300��
����Ŀ��: ��2 Ŀ��1 ����1  200��
��Ŀ��: ��2 Ŀ��2  100��

���� ���� ������ ���� ���� �߽��ϴ�
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  // �ڹ� GUI, �̺�Ʈ ó���� ���� ���Թ�

class MaterialContainer {  // �����ִ� ���, ������ �޴� Ŭ����
	String Material;  // ����̸��� �޴� ����
	int amount;  // ���� ������ �޴� ���� 

	MaterialContainer(String materialIn, int amountIn) {  // ���,���������� �޴� ������
		Material = materialIn;  
		amount = amountIn;
	}

	void eject() {  // ��Ḧ ���� �Լ�
		if (amount <= 0)  // ���� ������ �� ��������
			coffenone();  // �����ϴٰ� ǥ����
		amount--;  // ��Ḧ ���� ������ ���ҽ�Ŵ
	}

	boolean enoughAmount(int amount) {  // �����ִ� ��ᰡ ����� �� �Ǵ��ϴ� �� �Լ�
		return this.amount >= amount;  // �����ִ� ��ᰡ ������ ����� �纸�� ���ų� ũ�� ���� ������
	}

	String coffenone() {  //���� ��Ḧ ǥ���ϴ� �Լ�
		return Material;
	}
}

class CoffeeBox extends MaterialContainer {  // �����ִ� Ŀ��(���)�� ���� �ִ� Ŭ����
	CoffeeBox() {
		super("Ŀ��", 100);  // 100���� ��
	}
}

class SugarBox extends MaterialContainer {  // �����ִ� ������ ���� �ִ� Ŭ����
	SugarBox() {
		super("����", 100);  // 100���� ��
	}
}

class MilkBottle extends MaterialContainer {  // �����ִ� ������ ���� �ִ� Ŭ����
	MilkBottle() {
		super("����", 100);  // 100���� ��
	}
}

class WaterBucket extends MaterialContainer {  // �����ִ� ���� ���� �ִ� Ŭ����
	WaterBucket() {
		super("��", 200);  // 200������
	}
}

class CupMaterialContainer extends MaterialContainer {  // �����ִ� �������� ������ �ִ� Ŭ����
	public CupMaterialContainer() {
		super("��", 200);  // 200���� ��
	}
}

class CasherBox {  // �ܾ��� �����ϴ� Ŭ����
	int balance = 0;  // �ܾ��� 0���� ������

	int addBalance(int k) {  // �ܾ׿� ���� �ִ� �Լ�
		balance += k;
		return balance;
	}

	int getBalance() {  // ���� �ܾ��� �������ִ� �Լ�
		return balance;
	}

	void order(int amount) {  // Ŀ�Ǹ� �ֹ��ϸ� Ŀ�� �ݾ� ��ŭ �ܾ��� �پ��� �ϴ� �Լ�
		balance -= amount;
	}

	void ejectBalance() {  // �ܾ��� ��ȯ�ϴ� �Լ�
		balance = 0;
	}
}

public class vendingmachineGUI extends JFrame{  //GUI�� �����ϴ� Ŭ����
	JTextField ChangeView = new JTextField(10);  //   ���� �ܾ��� �����ִ� �ý�Ʈ�ʵ�
	JTextField CashInjectView = new JTextField(10);  // ���� ���� �Է� �޴� �ý�Ʈ �ʵ�
	JLabel Guide = new JLabel("            Ŀ�����Ǳ⸦ �̿��� �ּż� �����մϴ�. ");  // ó�� �ؿ� �����ִ� �޽���
	
	JButton MilkButton = new JButton("<html>��ũ Ŀ�� <br/>&nbsp&nbsp(300��)<br/>(�ܾ׺���)</html>");  // Ŀ�� ��ư�� ����
	JButton SugarButton = new JButton("<html>���� Ŀ�� <br/>&nbsp&nbsp(200��)<br/>(�ܾ׺���)</html>");  // ��ó������ �ܾ��� 0�̹Ƿ� �ܾ׺��� ���¿��� ������
	JButton BlackButton = new JButton("<html>�� Ŀ�� <br/>&nbsp&nbsp(100��)<br/>(�ܾ׺���)</html>");
	
	
	
	CasherBox c = new CasherBox();  // �ܾ��� �޴� Ŭ������ ��

	CoffeeBox coffee = new CoffeeBox();  // ���鵵 �� ��
	SugarBox sugar = new SugarBox();
	MilkBottle milk = new MilkBottle();
	WaterBucket water = new WaterBucket();
	CupMaterialContainer cups = new CupMaterialContainer();
		
	int balance = c.getBalance();  // �ܾװ��� �� �� �ְ� ���Խ�����
	vendingmachineGUI(){  // GUI ����� ������

		setTitle("Ŀ�� ���Ǳ� GUI ��������");  // ������ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();  // ����Ʈ�� ã��
		cp.setLayout(null);  // ��ġ������ ���� ��
		
		MyActionListener listener = new MyActionListener();  // �׼� ������ ����
		
		ChangeView.setText("0");  // ��ó�� �ܾ��� 0���� ����
		
		JButton Inject = new JButton("����");  // ����,��ȯ ������ ������ ����
		Inject.addActionListener(listener);
		JButton Return = new JButton("��ȯ");
		Return.addActionListener(listener);

		
		MilkButton.addActionListener(listener);  // Ŀ�� ��ư ������ ����
		SugarButton.addActionListener(listener);
		BlackButton.addActionListener(listener);
		
		JLabel Change = new JLabel("�ܾ�: ");  // �ܾ�, ���� ���� �޽��� ǥ��
		JLabel CashInject = new JLabel("���� ����: ");

		// ��ư,�޽��� ��ġ�� ��
		cp.add(Inject); 
		Inject.setSize(80,20);
		Inject.setLocation(230,200);
		cp.add(Return);
		Return.setSize(80,20);
		Return.setLocation(330,200);
		
		cp.add(MilkButton);
		MilkButton.setSize(100,80);
		MilkButton.setLocation(50,100);
		cp.add(SugarButton);
		SugarButton.setSize(100,80);
		SugarButton.setLocation(200,100);
		cp.add(BlackButton);
		BlackButton.setSize(100,80);
		BlackButton.setLocation(350,100);
		
		cp.add(ChangeView);
		ChangeView.setSize(350,20);
		ChangeView.setLocation(85,50);
		cp.add(CashInjectView);
		CashInjectView.setSize(100,20);
		CashInjectView.setLocation(125,200);
		
		cp.add(Change);
		Change.setSize(100,20);
		Change.setLocation(50,50);
		cp.add(CashInject);
		CashInject.setSize(100,20);
		CashInject.setLocation(60,200);
		cp.add(Guide);
		Guide.setSize(400,50);
		Guide.setLocation(80,250);
		
		setSize(500,400);  // ũ�� ����
		setVisible(true);  // ȭ�鿡 ������ ���
	}

	private class MyActionListener implements ActionListener{  // ������ Ŭ���� ����
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();  // �Է� ���� ��ư
				
				class Button {  // ��ư���� �����ϴ� Ŭ����
					boolean OnOff;  // �ֹ������� �� �ƴ��� �Ǵ��ϴ� �Ҹ� 

					void print() {  // �������̵���
					};

					void pushMilk(boolean OnOff) {  // �ֹ� ���� �� �� �ƴ��� ǥ���ϴ� �Լ�
						if (OnOff)  // ���̸� 
							MilkButton.setText("<html>��ũ Ŀ�� <br/>&nbsp&nbsp(300��)<br/>(�ֹ�����)</html>"); 
						else // �ƴϸ�
							MilkButton.setText("<html>��ũ Ŀ�� <br/>&nbsp&nbsp(300��)<br/>(�ܾ׺���)</html>");
					}
					void pushSugar(boolean OnOff) {  // �ֹ� ���� �� �� �ƴ��� ǥ���ϴ� �Լ�
						if (OnOff)  // ���̸� 
							SugarButton.setText("<html>���� Ŀ�� <br/>&nbsp&nbsp(200��)<br/>(�ֹ�����)</html>");
						else // �ƴϸ�
							SugarButton.setText("<html>���� Ŀ�� <br/>&nbsp&nbsp(200��)<br/>(�ܾ׺���)</html>");
					}
					void pushBlack(boolean OnOff) {  // �ֹ� ���� �� �� �ƴ��� ǥ���ϴ� �Լ�
						if (OnOff)  // ���̸� 
							BlackButton.setText("<html>�� Ŀ�� <br/>&nbsp&nbsp(100��)<br/>(�ֹ�����)</html>");
						else // �ƴϸ�
							BlackButton.setText("<html>�� Ŀ�� <br/>&nbsp&nbsp(100��)<br/>(�ܾ׺���)</html>");
					}
				}

				class milkbutton extends Button {  // ����Ŀ�� ��ư
					void print() {
						if (c.getBalance() >= 300) {  // �ܾ��� 300������ ũ�� ��, �ƴ� ����
							OnOff = true;
						} 
						else
							OnOff = false;
						super.pushMilk(OnOff);  // �׸��� �� �ڿ� �ֹ� �����Ѱ� �ƴѰ� ǥ��
					}
				}
				class sugarbutton extends Button {  // ���� ����� ����
					void print() {
						if (c.getBalance() >= 200) {  // 200�� ���� ū��
							OnOff = true;
						} 
						else
							OnOff = false;
						super.pushSugar(OnOff);
					}
				}
				class blackbutton extends Button {
					void print() {
						if (c.getBalance() >= 100) {  // 100�� ���� ū ��
							OnOff = true;
						} 
						else
							OnOff = false;
						super.pushBlack(OnOff);
					}
				}
				
				milkbutton mButton = new milkbutton();  // �� Ŀ�ǹ�ư �ֹ� ���ɿ��� Ȯ���� �� ��ü ��������
				sugarbutton sButton = new sugarbutton();
				blackbutton bButton = new blackbutton();
				
				if(b.getText().equals("����")) {  // ���� �̺�Ʈ
					try{
					int n = Integer.parseInt(CashInjectView.getText().toString());  // �������� Ȯ�ο�
					c.addBalance(Integer.parseInt(CashInjectView.getText()));   // �������� �ý�Ʈ�ʵ忡�� ���� ������ �Լ��� ����
					ChangeView.setText(Integer.toString(c.getBalance()));  // �׸��� �ܾ���Ȳ�� ������
					Guide.setText("                         ���� "+ CashInjectView.getText() + "���� ���ԵǾ����ϴ�.");  // �ؿ� �޽��� ǥ��(�߾ӿ� ���̰� �ϱ����� ó���κ� �� ���)
					mButton.print();  // �� ��ư �ֹ����� ���� �ٽ� Ȯ��
					sButton.print();
					bButton.print();
					}
					catch(Exception e1){  // ���ڰ� �ƴ� ��� �޽��� ������
						Guide.setText("                            ���ڸ� �Է� �� �ֽʽÿ�.");
					}
				}
				if(b.getText().equals("��ȯ")) {
					Guide.setText("                         �ܾ� " + c.getBalance() + "���� ��ȯ�Ǿ����ϴ�.");
					c.ejectBalance();  // �ܾ��� 0���� ����� �Լ�
					ChangeView.setText(Integer.toString(balance));
					mButton.print();
					sButton.print();
					bButton.print();
				}
				
				if(b.getText().equals("<html>��ũ Ŀ�� <br/>&nbsp&nbsp(300��)<br/>(�ܾ׺���)</html>" )) {  // �ܾ׺��� ������ ��ư�� ������ ��
						Guide.setText("           �ܾ��� ���ڶ��ϴ�. ������ ������ �ּ���.");
					} 
				if(b.getText().equals("<html>��ũ Ŀ�� <br/>&nbsp&nbsp(300��)<br/>(�ֹ�����)</html>" )){  // �ܾ��� ����ϸ�
						boolean nuffWater = water.enoughAmount(2);  // Ŀ�Ǹ� ����µ�, ���� ��ᰡ ��� ���� �Ǵ���
						boolean nuffCoffee = coffee.enoughAmount(1);  // ����ϸ� ��, �ƴ� ����
						boolean nuffMilk = milk.enoughAmount(1);
						boolean nuffCups = cups.enoughAmount(1);
						if(nuffCoffee && nuffWater && nuffMilk) {  // �� ���̸� ���,�ܾ��� ������
							water.eject();
							water.eject();
							coffee.eject();
							milk.eject();
							cups.eject();  // ������ �����ϴ� �Լ���
							c.order(300);  // �ܾ��� ���� �Լ�
							mButton.print();  // �ֹ����ɿ��� Ȯ���ϴ� �Լ���
							sButton.print();
							bButton.print();
							ChangeView.setText(Integer.toString(c.getBalance()));  // �ܾ���Ȳ�� �ֽ�ȭ��
							Guide.setText("��ũ Ŀ�ǰ� ���ɴϴ�. ���ְ� ��ñ� �ٶ��ϴ�.");  // �׸��� �޽��� ���
						}

						if (!nuffCoffee)  // ���࿡ ��ᰡ �����ϸ� ���� �˸��� ������ ��Ḧ ǥ��
							Guide.setText("���(" +coffee.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
						if (!nuffWater)
							Guide.setText("���(" +water.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
						if (!nuffMilk)
							Guide.setText("���(" +milk.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
						if (!nuffCups)
							Guide.setText("���(" +cups.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
					}
				if(b.getText().equals("<html>���� Ŀ�� <br/>&nbsp&nbsp(200��)<br/>(�ܾ׺���)</html>")) {  // �������� ���� ��İ� ����
						Guide.setText("           �ܾ��� ���ڶ��ϴ�. ������ ������ �ּ���.");
					}
				if(b.getText().equals("<html>���� Ŀ�� <br/>&nbsp&nbsp(200��)<br/>(�ֹ�����)</html>" )){  
						boolean nuffWater = water.enoughAmount(2);
						boolean nuffCoffee = coffee.enoughAmount(1);
						boolean nuffSugar = sugar.enoughAmount(1);
						boolean nuffCups = cups.enoughAmount(1);
						if (nuffCoffee && nuffWater && nuffSugar) {
							water.eject();
							water.eject();
							coffee.eject();
							sugar.eject(); 
							cups.eject();
							c.order(200);
							mButton.print();
							sButton.print();
							bButton.print();
							ChangeView.setText(Integer.toString(c.getBalance()));
							Guide.setText("���� Ŀ�ǰ� ���ɴϴ�. ���ְ� ��ñ� �ٶ��ϴ�.");
						}
						if (!nuffCoffee)  
							Guide.setText("���(" +coffee.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
						if (!nuffWater)
							Guide.setText("���(" +water.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
						if (!nuffSugar)  
							Guide.setText("���(" +sugar.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
						if (!nuffCups)
							Guide.setText("���(" +cups.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
					}
				if(b.getText().equals("<html>�� Ŀ�� <br/>&nbsp&nbsp(100��)<br/>(�ܾ׺���)</html>")) { // �������� ���� ��İ� ����
						Guide.setText("           �ܾ��� ���ڶ��ϴ�. ������ ������ �ּ���.");
					} 
				if(b.getText().equals("<html>�� Ŀ�� <br/>&nbsp&nbsp(100��)<br/>(�ֹ�����)</html>" )){ 
						boolean nuffWater = water.enoughAmount(2);
						boolean nuffCoffee = coffee.enoughAmount(2);
						boolean nuffCups = cups.enoughAmount(1);
						if (nuffCoffee && nuffWater) {
							water.eject();
							water.eject();
							coffee.eject();
							coffee.eject();
							cups.eject();
							c.order(100);
							mButton.print();
							sButton.print();
							bButton.print();
							ChangeView.setText(Integer.toString(c.getBalance()));
							Guide.setText("�� Ŀ�ǰ� ���ɴϴ�. ���ְ� ��ñ� �ٶ��ϴ�.");
						}

						if (!nuffCoffee)  
							Guide.setText("���(" +coffee.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
						if (!nuffWater)
							Guide.setText("���(" +water.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
						if (!nuffCups)
							Guide.setText("���(" +cups.coffenone() + ")�� �����մϴ�. �˼������� ������ �̿� ��Ź �帳�ϴ�");
					}			
				}	
			}
	public static void main(String[] args) {
		new vendingmachineGUI();  // GUI ��ü ������
	}
}
