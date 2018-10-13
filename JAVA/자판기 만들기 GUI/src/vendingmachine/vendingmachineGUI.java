package vendingmachine;

/*
밀크커피: 물2 커피1 밀크1  300원
설탕커피: 물2 커피1 설탕1  200원
블랙커피: 물2 커피2  100원

들어가는 재료랑 가격은 위와 같이 했습니다
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  // 자바 GUI, 이벤트 처리를 위한 삽입문

class MaterialContainer {  // 남아있는 재료, 재고등을 받는 클래스
	String Material;  // 재료이름을 받는 변수
	int amount;  // 남은 수량을 받는 변수 

	MaterialContainer(String materialIn, int amountIn) {  // 재료,남은수량을 받는 생성자
		Material = materialIn;  
		amount = amountIn;
	}

	void eject() {  // 재료를 쓰는 함수
		if (amount <= 0)  // 남은 수량이 다 떨어지면
			coffenone();  // 부족하다고 표시함
		amount--;  // 재료를 쓰면 수량을 감소시킴
	}

	boolean enoughAmount(int amount) {  // 남아있는 재료가 충분한 지 판단하는 블린 함수
		return this.amount >= amount;  // 남아있는 재료가 소진할 재료의 양보다 많거나 크면 참을 리턴함
	}

	String coffenone() {  //남은 재료를 표시하는 함수
		return Material;
	}
}

class CoffeeBox extends MaterialContainer {  // 남아있는 커피(재료)의 양을 넣는 클래스
	CoffeeBox() {
		super("커피", 100);  // 100으로 함
	}
}

class SugarBox extends MaterialContainer {  // 남아있는 설탕의 양을 넣는 클래스
	SugarBox() {
		super("설탕", 100);  // 100으로 함
	}
}

class MilkBottle extends MaterialContainer {  // 남아있는 우유의 양을 넣는 클래스
	MilkBottle() {
		super("우유", 100);  // 100으로 함
	}
}

class WaterBucket extends MaterialContainer {  // 남아있는 물의 양을 넣는 클래스
	WaterBucket() {
		super("물", 200);  // 200으로함
	}
}

class CupMaterialContainer extends MaterialContainer {  // 남아있는 종이컵의 갯수를 넣는 클래스
	public CupMaterialContainer() {
		super("컵", 200);  // 200으로 함
	}
}

class CasherBox {  // 잔액을 관리하는 클래스
	int balance = 0;  // 잔액은 0부터 시작함

	int addBalance(int k) {  // 잔액에 돈을 넣는 함수
		balance += k;
		return balance;
	}

	int getBalance() {  // 남은 잔액을 리턴해주는 함수
		return balance;
	}

	void order(int amount) {  // 커피를 주문하면 커피 금액 만큼 잔액이 줄어들게 하는 함수
		balance -= amount;
	}

	void ejectBalance() {  // 잔액을 반환하는 함수
		balance = 0;
	}
}

public class vendingmachineGUI extends JFrame{  //GUI를 구현하는 클래스
	JTextField ChangeView = new JTextField(10);  //   남은 잔액을 보여주는 택스트필드
	JTextField CashInjectView = new JTextField(10);  // 현금 투입 입력 받는 택스트 필드
	JLabel Guide = new JLabel("            커피자판기를 이용해 주셔서 감사합니다. ");  // 처음 밑에 보여주는 메시지
	
	JButton MilkButton = new JButton("<html>밀크 커피 <br/>&nbsp&nbsp(300원)<br/>(잔액부족)</html>");  // 커피 버튼들 생성
	JButton SugarButton = new JButton("<html>설탕 커피 <br/>&nbsp&nbsp(200원)<br/>(잔액부족)</html>");  // 맨처음에는 잔액이 0이므로 잔액부족 상태에서 시작함
	JButton BlackButton = new JButton("<html>블랙 커피 <br/>&nbsp&nbsp(100원)<br/>(잔액부족)</html>");
	
	
	
	CasherBox c = new CasherBox();  // 잔액을 받는 클래스를 찍어냄

	CoffeeBox coffee = new CoffeeBox();  // 재료들도 다 찍어냄
	SugarBox sugar = new SugarBox();
	MilkBottle milk = new MilkBottle();
	WaterBucket water = new WaterBucket();
	CupMaterialContainer cups = new CupMaterialContainer();
		
	int balance = c.getBalance();  // 잔액값을 쓸 수 있게 대입시켜줌
	vendingmachineGUI(){  // GUI 만드는 생성자

		setTitle("커피 자판기 GUI 구현예제");  // 프레임 제목
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();  // 컨텐트팬 찾음
		cp.setLayout(null);  // 배치관리자 없이 함
		
		MyActionListener listener = new MyActionListener();  // 액션 리스너 생성
		
		ChangeView.setText("0");  // 맨처음 잔액은 0으로 시작
		
		JButton Inject = new JButton("투입");  // 투입,반환 생성및 리스너 연결
		Inject.addActionListener(listener);
		JButton Return = new JButton("반환");
		Return.addActionListener(listener);

		
		MilkButton.addActionListener(listener);  // 커피 버튼 리스너 연결
		SugarButton.addActionListener(listener);
		BlackButton.addActionListener(listener);
		
		JLabel Change = new JLabel("잔액: ");  // 잔액, 현금 투입 메시지 표시
		JLabel CashInject = new JLabel("현금 투입: ");

		// 버튼,메시지 배치한 것
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
		
		setSize(500,400);  // 크기 설정
		setVisible(true);  // 화면에 프레임 출력
	}

	private class MyActionListener implements ActionListener{  // 리스너 클래스 생성
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();  // 입력 받을 버튼
				
				class Button {  // 버튼들을 관리하는 클래스
					boolean OnOff;  // 주문가능한 지 아닌지 판단하는 불린 

					void print() {  // 오버라이딩용
					};

					void pushMilk(boolean OnOff) {  // 주문 가능 한 지 아닌지 표시하는 함수
						if (OnOff)  // 참이면 
							MilkButton.setText("<html>밀크 커피 <br/>&nbsp&nbsp(300원)<br/>(주문가능)</html>"); 
						else // 아니면
							MilkButton.setText("<html>밀크 커피 <br/>&nbsp&nbsp(300원)<br/>(잔액부족)</html>");
					}
					void pushSugar(boolean OnOff) {  // 주문 가능 한 지 아닌지 표시하는 함수
						if (OnOff)  // 참이면 
							SugarButton.setText("<html>설탕 커피 <br/>&nbsp&nbsp(200원)<br/>(주문가능)</html>");
						else // 아니면
							SugarButton.setText("<html>설탕 커피 <br/>&nbsp&nbsp(200원)<br/>(잔액부족)</html>");
					}
					void pushBlack(boolean OnOff) {  // 주문 가능 한 지 아닌지 표시하는 함수
						if (OnOff)  // 참이면 
							BlackButton.setText("<html>블랙 커피 <br/>&nbsp&nbsp(100원)<br/>(주문가능)</html>");
						else // 아니면
							BlackButton.setText("<html>블랙 커피 <br/>&nbsp&nbsp(100원)<br/>(잔액부족)</html>");
					}
				}

				class milkbutton extends Button {  // 우유커피 버튼
					void print() {
						if (c.getBalance() >= 300) {  // 잔액이 300원보다 크면 참, 아님 거짓
							OnOff = true;
						} 
						else
							OnOff = false;
						super.pushMilk(OnOff);  // 그리고 그 뒤에 주문 가능한가 아닌가 표시
					}
				}
				class sugarbutton extends Button {  // 위와 방식이 같음
					void print() {
						if (c.getBalance() >= 200) {  // 200원 보다 큰지
							OnOff = true;
						} 
						else
							OnOff = false;
						super.pushSugar(OnOff);
					}
				}
				class blackbutton extends Button {
					void print() {
						if (c.getBalance() >= 100) {  // 100원 보다 큰 지
							OnOff = true;
						} 
						else
							OnOff = false;
						super.pushBlack(OnOff);
					}
				}
				
				milkbutton mButton = new milkbutton();  // 각 커피버튼 주문 가능여부 확인해 줄 객체 생성해줌
				sugarbutton sButton = new sugarbutton();
				blackbutton bButton = new blackbutton();
				
				if(b.getText().equals("투입")) {  // 투입 이벤트
					try{
					int n = Integer.parseInt(CashInjectView.getText().toString());  // 숫자인지 확인용
					c.addBalance(Integer.parseInt(CashInjectView.getText()));   // 현금투입 택스트필드에서 값을 가져와 함수에 넣음
					ChangeView.setText(Integer.toString(c.getBalance()));  // 그리고 잔액현황을 보여줌
					Guide.setText("                         현금 "+ CashInjectView.getText() + "원이 투입되었습니다.");  // 밑에 메시지 표시(중앙에 보이게 하기위해 처음부분 좀 띄움)
					mButton.print();  // 각 버튼 주문가능 여부 다시 확인
					sButton.print();
					bButton.print();
					}
					catch(Exception e1){  // 숫자가 아닌 경우 메시지 보여줌
						Guide.setText("                            숫자를 입력 해 주십시오.");
					}
				}
				if(b.getText().equals("반환")) {
					Guide.setText("                         잔액 " + c.getBalance() + "원이 반환되었습니다.");
					c.ejectBalance();  // 잔액을 0으로 만드는 함수
					ChangeView.setText(Integer.toString(balance));
					mButton.print();
					sButton.print();
					bButton.print();
				}
				
				if(b.getText().equals("<html>밀크 커피 <br/>&nbsp&nbsp(300원)<br/>(잔액부족)</html>" )) {  // 잔액부족 상태인 버튼을 눌럿을 때
						Guide.setText("           잔액이 모자랍니다. 현금을 투입해 주세요.");
					} 
				if(b.getText().equals("<html>밀크 커피 <br/>&nbsp&nbsp(300원)<br/>(주문가능)</html>" )){  // 잔액이 충분하면
						boolean nuffWater = water.enoughAmount(2);  // 커피를 만드는데, 남은 재료가 충분 한지 판단함
						boolean nuffCoffee = coffee.enoughAmount(1);  // 충분하면 참, 아님 거짓
						boolean nuffMilk = milk.enoughAmount(1);
						boolean nuffCups = cups.enoughAmount(1);
						if(nuffCoffee && nuffWater && nuffMilk) {  // 다 참이면 재료,잔액을 소진함
							water.eject();
							water.eject();
							coffee.eject();
							milk.eject();
							cups.eject();  // 재료들을 소진하는 함수들
							c.order(300);  // 잔액을 쓰는 함수
							mButton.print();  // 주문가능여부 확인하는 함수들
							sButton.print();
							bButton.print();
							ChangeView.setText(Integer.toString(c.getBalance()));  // 잔액현황을 최신화함
							Guide.setText("밀크 커피가 나옵니다. 맛있게 드시기 바랍니다.");  // 그리고 메시지 출력
						}

						if (!nuffCoffee)  // 만약에 재료가 부족하면 각각 알맞은 부족한 재료를 표시
							Guide.setText("재료(" +coffee.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
						if (!nuffWater)
							Guide.setText("재료(" +water.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
						if (!nuffMilk)
							Guide.setText("재료(" +milk.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
						if (!nuffCups)
							Guide.setText("재료(" +cups.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
					}
				if(b.getText().equals("<html>설탕 커피 <br/>&nbsp&nbsp(200원)<br/>(잔액부족)</html>")) {  // 나머지는 위의 방식과 같음
						Guide.setText("           잔액이 모자랍니다. 현금을 투입해 주세요.");
					}
				if(b.getText().equals("<html>설탕 커피 <br/>&nbsp&nbsp(200원)<br/>(주문가능)</html>" )){  
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
							Guide.setText("설탕 커피가 나옵니다. 맛있게 드시기 바랍니다.");
						}
						if (!nuffCoffee)  
							Guide.setText("재료(" +coffee.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
						if (!nuffWater)
							Guide.setText("재료(" +water.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
						if (!nuffSugar)  
							Guide.setText("재료(" +sugar.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
						if (!nuffCups)
							Guide.setText("재료(" +cups.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
					}
				if(b.getText().equals("<html>블랙 커피 <br/>&nbsp&nbsp(100원)<br/>(잔액부족)</html>")) { // 나머지는 위의 방식과 같음
						Guide.setText("           잔액이 모자랍니다. 현금을 투입해 주세요.");
					} 
				if(b.getText().equals("<html>블랙 커피 <br/>&nbsp&nbsp(100원)<br/>(주문가능)</html>" )){ 
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
							Guide.setText("블랙 커피가 나옵니다. 맛있게 드시기 바랍니다.");
						}

						if (!nuffCoffee)  
							Guide.setText("재료(" +coffee.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
						if (!nuffWater)
							Guide.setText("재료(" +water.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
						if (!nuffCups)
							Guide.setText("재료(" +cups.coffenone() + ")가 부족합니다. 죄송하지만 다음에 이용 부탁 드립니다");
					}			
				}	
			}
	public static void main(String[] args) {
		new vendingmachineGUI();  // GUI 객체 생성함
	}
}
