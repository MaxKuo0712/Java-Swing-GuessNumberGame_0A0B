package tw.Max.RunCode;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.System.Logger;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GuessNumberGame extends JFrame {
	private JTextField input;
	private JButton guess;
	private JTextArea log;
	private String answer, inputNum;
	private int counter, gameCount;
	
	public GuessNumberGame() {
		super("猜數字遊戲");
		setSize(640, 480);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null); // 打開後直接出現在螢幕正中央
		
		// 排版用Panel
		JPanel top = new JPanel(new BorderLayout());
		add(top, BorderLayout.NORTH);
		
		// 輸入欄位
		input = new JTextField();
		top.add(input, BorderLayout.CENTER);
		// 加入鍵盤監聽，讓User不能輸入0-9以外的東西，如果有，則顯示警語及無法輸入
		MyListener myListener = new MyListener();
		input.addKeyListener(myListener);
		
		// log視窗
		log = new JTextArea();
		log.setEditable(false); // 設定log視窗不能編輯
		log.setFont(new Font("Myriad Pro", Font.PLAIN, 20)); // 設定log視窗文字屬性，字體是Myriad Pro，風格普通，大小20
		add(log, BorderLayout.CENTER);

		// 猜的按鍵
		guess = new JButton("猜");
		top.add(guess, BorderLayout.EAST);
		guess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doGuess();
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// 監聽鍵盤事件
	private class MyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			//按下enter也能送出答案進行猜數字
			int keyChar = e.getKeyChar();
			if (keyChar == KeyEvent.VK_ENTER ) {
				doGuess();
			}
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			super.keyTyped(e);
			// 輸入只可以是 0-9，也解開delete, backspace, enter給user可以修改及輸入
			int keyChar = e.getKeyChar();
			if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) || keyChar == KeyEvent.VK_DELETE || 
					keyChar == KeyEvent.VK_BACK_SPACE || keyChar == KeyEvent.VK_ENTER ) {
				
			} else {
				e.consume();
				JOptionPane.showMessageDialog(null, "請輸入 0 ~ 9 的數字");
			}
		}
	}

	// 猜的按鍵觸發
	private void doGuess() {
		if (isInputFail(input.getText())) {
			JOptionPane.showMessageDialog(null, "只能輸入" + gameCount +"位數字哦！請重新輸入！"); // 提醒只剩一次機會
			input.setText("");
			input.requestFocusInWindow(); // 使用父類別Component的requestFocusInWindow，目的是輸入游標
		} else {
			counter++; // 計數器，計算猜第幾輪了
			inputNum = input.getText(); //取得input的字串
			log.append(String.format("第%d輪：%s => %s\n", counter, inputNum, cheakAB(inputNum))); // 比對解答與輸入的結果
			input.setText(""); // 清空輸入
			input.requestFocusInWindow(); // 使用父類別Component的requestFocusInWindow，使送出後可以回歸到輸入視窗，目的是輸入游標
			
			if (counter == 9) {
				JOptionPane.showMessageDialog(null, "只剩最後一次機會囉！"); // 提醒只剩一次機會
				input.requestFocusInWindow(); // 使用父類別Component的requestFocusInWindow，目的是輸入游標
			} else if (counter == 10) {
				showResultDialog(false); // 10次還沒猜到為輸
				isAgain();
			} else if (cheakAB(inputNum).equals(gameCount + "A0B")) {
				showResultDialog(true); // nA0B為贏
				isAgain();
			}
		}
	}
	
	// 產生不重複的數字
	public static String createAns(int dig) {
        // 撲克牌發牌
        int num = 9, randomNum = 0, temp = 0;
        int[] poker = new int[num];

        for (int i = 0; i < poker.length; i++) {
            poker[i] = i + 1;
        }
        
        // 洗牌
        while ((num - 1) >= 0) {
            randomNum = (int) (Math.random() * (num - 2));
            temp = poker[num - 1];
            poker[num - 1] = poker[randomNum];
            poker[randomNum] = temp;
            num--;
        }
		
        // 收入四張牌
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < dig; i++) {
			sb.append(poker[i]);
		}
		
		return sb.toString();
	}

	// 比對解答與輸入的結果
	private String cheakAB(String inputNum) {
		int A = 0, B = 0; // 幾A幾B 初始化
		
		// 針對這次遊戲的數字數量跑迴圈
		for (int i = 0; i < gameCount; i++) {
			// 用indexOf去看input的第i個字出現在answer字串的哪一個位置，大於0表示有出現在answer內
			if ( answer.indexOf(inputNum.charAt(i)) >= 0) {
				// 如果input的第i個字有出現在answer內，那i等於出現位置index的話，就是位置及數字相同 => A++，否則只有出現在answer內 => B++
				if (i == answer.indexOf(inputNum.charAt(i))) { 
					A++;
				} else {
					B++;
				}				
			}
		}
		return String.format("%dA%dB", A, B);
	}
	
	// 贏或是輸，告訴結果
	private void showResultDialog(boolean isWin) {
		if (isWin) {
			JOptionPane.showMessageDialog(null, String.format("Winner! 答案是：%s", answer));
		} else {
			JOptionPane.showMessageDialog(null, String.format("Loser! 答案是：%s", answer));
		}
	}
	
	// 要不要再玩一次
	private void isAgain() {
		int isAgain = JOptionPane.showConfirmDialog(null, "是否要再玩一次？", "就是問你", JOptionPane.YES_NO_OPTION);
		if (isAgain == 0) {
			newGame();
		} else {
			dispose();
		}
	}
	
	// 確認輸入的數字數量是不是大於遊戲設定
	private boolean isInputFail(String input) {
		if (input.length() == gameCount) {
			return false;
		} else {
			return true;
		}
	}
	
	// 產生新遊戲
	private void newGame() {
		int gameCount = gameInit();; // 遊戲初始化
		this.gameCount = gameCount; // 決定要玩幾位數的
		counter = 0; // 猜的次數歸零
		answer = createAns(gameCount); // 產生隨機答案
		input.setText(""); // input欄位清空
		log.setText(""); // log顯示清空
		System.out.println(answer);
	}
	
	// 設定遊戲要玩幾位數
	private int gameInit() {
		int gameCount = 0; // 幾位數
		String input = null; // showInputDialog
		
		// 如果為null或是""，會問你要不要結束遊戲
		try {
			input = JOptionPane.showInputDialog("客倌您好，想玩幾位數的？ (最多9位數)");
			gameCount = Integer.parseInt(input);
		} catch (Exception e) {
			System.out.println(e.toString());
			// 問你要不要結束遊戲
			if (isGameOver()) {
				dispose();
			} else {
				return gameInit();
			}
		}
		
		// 看是不是超過9位數，因為遊戲設定是每個數字不重複
		if (gameCount > 9) {
			JOptionPane.showMessageDialog(null, "客倌，最多9位數哦！");
			return gameInit(); // return回原方法再問一次
		} else {
			return gameCount;
		}

	}
	
	// 是否要結束遊戲
	public boolean isGameOver() {
		int confirm = JOptionPane.showConfirmDialog(null, "客倌，是否要離開遊戲？", "是否要離開？", 0);
		if (confirm == 0) {
			return true;
		} else {
			return false;
		}
	}

	// 程式進入點
	public static void main(String[] args) {
		GuessNumberGame game = new GuessNumberGame();
		game.newGame();
	}

}

