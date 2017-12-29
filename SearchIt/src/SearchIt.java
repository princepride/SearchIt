import java.awt.EventQueue;
import com.baidu.translate.demo.TransApi;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.JRadioButton;

public class SearchIt {

	private static final String APP_ID = "20170928000085675";
	private static final String SECURITY_KEY = "nSqxf1ZRNCNvkltE4Zgq";
	private JFrame frame;
	private JTextField vocabularyTextField;
	JDBC jdbc=new JDBC();
	static JRadioButton radioButton;
	static SearchIt window;
	static JTextArea inTextArea;
	static JTextArea webTextArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window=new SearchIt();
					window.frame.setVisible(true);
					window.jdbc.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SearchIt() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(925, 0, 614, 333);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u5355\u8BCD");
		label.setBounds(13, 33, 72, 18);
		frame.getContentPane().add(label);
		
		vocabularyTextField = new JTextField();
		vocabularyTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					TransApi api = new TransApi(APP_ID, SECURITY_KEY);
					Vocabulary vocabulary=jdbc.select(window.vocabularyTextField.getText());
					if(vocabulary.vocabulary=="") {
						window.inTextArea.setText("你的数据库中还没有这个单词");
					}
					else {
					window.inTextArea.setText(vocabulary.interpretation+"\n");
					}
					String query=window.vocabularyTextField.getText();
					String string=api.getTransResult(query, "auto", "zh");
					Pattern pattern=Pattern.compile("\\\\[0-9a-z]+");
			        Matcher matcher=pattern.matcher(string);
			        char ch;
			        String string2="";
			        while(matcher.find()){
			                char c=(char)Integer.parseInt(matcher.group(0).replaceAll("\\\\u", ""),16);
			                string2+=c;   
			        }
			        window.webTextArea.setText(string2);
				}
				if(e.getKeyCode()==KeyEvent.VK_DELETE) {
					window.vocabularyTextField.setText("");
					window.inTextArea.setText("");
					window.webTextArea.setText("");
				}
			}
		});
		vocabularyTextField.setBounds(73, 30, 405, 24);
		frame.getContentPane().add(vocabularyTextField);
		vocabularyTextField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u91CA\u4E49");
		label_1.setBounds(13, 64, 72, 18);
		frame.getContentPane().add(label_1);
		JScrollPane jScrollPane=new JScrollPane();
		jScrollPane.setBounds(73, 62, 440, 105);
		frame.getContentPane().add(jScrollPane);
		
		inTextArea = new JTextArea();
		jScrollPane.setViewportView(inTextArea);
		
		JButton insertButton = new JButton("\u5F55\u5165");
		insertButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				window.inTextArea.setText("");
				Vocabulary vocabulary=new Vocabulary();
				vocabulary.vocabulary=vocabularyTextField.getText();
				vocabulary.interpretation=webTextArea.getText();
				vocabulary.detailExplanation="";
				Vocabulary vocabulary2=jdbc.select(vocabulary.vocabulary);
				if(vocabulary2.vocabulary=="") {
					jdbc.insert(vocabulary);
				}
				else {
					window.webTextArea.setText("这个单词已经有啦");
				}
			}
		});
		insertButton.setBounds(527, 180, 72, 105);
		frame.getContentPane().add(insertButton);
		
		JButton searchButton = new JButton("\u67E5\u8BE2");
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TransApi api = new TransApi(APP_ID, SECURITY_KEY);
				Vocabulary vocabulary=jdbc.select(window.vocabularyTextField.getText());
				if(vocabulary.vocabulary=="") {
					window.inTextArea.setText("你的数据库中还没有这个单词");
				}
				else {
				window.inTextArea.setText(vocabulary.interpretation+"\n");
				}
				String query=window.vocabularyTextField.getText();
				String string=api.getTransResult(query, "auto", "zh");
				Pattern pattern=Pattern.compile("\\\\[0-9a-z]+");
		        Matcher matcher=pattern.matcher(string);
		        char ch;
		        String string2="";
		        while(matcher.find()){
		                char c=(char)Integer.parseInt(matcher.group(0).replaceAll("\\\\u", ""),16);
		                string2+=c;   
		        }
		        window.webTextArea.setText(string2);
			}
		});
		searchButton.setBounds(527, 64, 72, 103);
		frame.getContentPane().add(searchButton);
		
		JButton outPutTableButton = new JButton("\u5BFC\u51FA");
		outPutTableButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jdbc.outPutTable();
				if(window.radioButton.isSelected()) {
					Runtime runtime=Runtime.getRuntime();
					try {
						runtime.exec("D:\\BaiduNetdisk\\baidunetdisk.exe");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		outPutTableButton.setBounds(527, 29, 72, 27);
		frame.getContentPane().add(outPutTableButton);
		JScrollPane jScrollPane2=new JScrollPane();
		jScrollPane2.setBounds(73, 180, 440, 105);
		frame.getContentPane().add(jScrollPane2);
		
		webTextArea = new JTextArea();
		jScrollPane2.setViewportView(webTextArea);
		
		JLabel label_2 = new JLabel("\u7F51\u7EDC\u91CA\u4E49");
		label_2.setBounds(3, 180, 72, 18);
		frame.getContentPane().add(label_2);
		
		radioButton = new JRadioButton("");
		radioButton.setBounds(488, 33, 25, 21);
		frame.getContentPane().add(radioButton);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				jdbc.close();
				System.exit(0);
			}
			
		});
	}
}
