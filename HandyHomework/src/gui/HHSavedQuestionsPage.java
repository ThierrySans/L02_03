package gui;
import login.SelectedUser;
import question.Question;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import answer.TextAnswer;
import assessment.Assessment;
import assessment.SelectedAssessment;
import dao.DbQuestions;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.SystemColor;

import java.awt.Color;

public class HHSavedQuestionsPage extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField questionAnswerField;
	private JLabel ans;
	private TextAnswer questAnswer;
	
	private Question selQuestion;
	private int selInd;
	private String selected = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HHSavedQuestionsPage frame = new HHSavedQuestionsPage();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HHSavedQuestionsPage() {
		SwitchForm sf = new SwitchForm();
		setTitle("HandyHomework - Questions");
		setName("SavedQuestions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea lblAssessmentName = new JTextArea("Saved Questions");
		lblAssessmentName.setEditable(false);
		lblAssessmentName.setWrapStyleWord(true);
		lblAssessmentName.setLineWrap(true);
		lblAssessmentName.setBackground(SystemColor.window);
		lblAssessmentName.setMaximumSize(new Dimension(100, 30));
		lblAssessmentName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
		lblAssessmentName.setBounds(138, 30, 376, 40);
		contentPane.add(lblAssessmentName);
		
		JPanel panel = new JPanel();
		panel.setBounds(229, 84, 307, 205);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel questionTitle = new JLabel("");
		questionTitle.setBounds(0, 6, 237, 10);
		panel.add(questionTitle);
		questionTitle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		
		JTextArea questionText = new JTextArea("Select a Question");
		questionText.setBounds(0, 48, 307, 31);
		questionText.setFont(new Font("Monospaced", Font.PLAIN, 15));
		questionText.setWrapStyleWord(true);
		questionText.setEditable(false);
		questionText.setBackground(SystemColor.window);
		questionText.setLineWrap(true);
		panel.add(questionText);
		
		JLabel lblPts = new JLabel("");
		lblPts.setBounds(0, 109, 307, 82);
		panel.add(lblPts);
		lblPts.setAutoscrolls(true);
		lblPts.setVerticalAlignment(SwingConstants.TOP);
		
		JLabel lblAnswer = new JLabel("");
		lblAnswer.setBounds(0, 196, 307, 9);
		panel.add(lblAnswer);
		
			
		
		int aid;
		if (!SelectedAssessment.isSelected()) {	
			JOptionPane.showMessageDialog(HHSavedQuestionsPage.this, 
					"No Assessments have been selected, returning to login");
			HHLogin frame = new HHLogin();
			sf.switchForm(frame);
			if (frame.isShowing()){
				dispose();
			}
		}
		
		DefaultListModel<String> lstQuestion = new DefaultListModel<>();	
		Assessment as = SelectedAssessment.getAssess();
		DbQuestions dbQuest = new DbQuestions();
		aid = as.getAid();
		List<Question> questions = dbQuest.allQuestions(aid);
				
		lblAssessmentName.setText(as.getName());
		for (Question tq: questions ) {
			lstQuestion.addElement(tq.getName());
		}
			
		JButton btnback = new JButton("\u2190 Back");
		btnback.setBounds(15, 35, 115, 30);
		contentPane.add(btnback);
		btnback.setName("back");
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HHSavedAssessments frame = new HHSavedAssessments();
				sf.switchForm(frame);
				if (frame.isShowing()){
					dispose();
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 84, 188, 205);
		contentPane.add(scrollPane);
		JList<String> listQuestion = new JList<>(lstQuestion);
		scrollPane.setViewportView(listQuestion);
		listQuestion.setName("lstQuestion");
		
		listQuestion.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String res = "<html>This question is worth</html>";
					
				JList<?> list = (JList<?>) e.getSource();
				int index = list.getSelectedIndex();
				if (index != -1) {
					Question question = questions.get(list.getSelectedIndex());
					questionText.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
					questionText.setText("Q: " + question.getQuestion());
					questionText.setSize(questionText.getPreferredSize());
					res = "<html>This question is worth <html>" + new Integer(question.getPoints()).toString() + "<html> marks</html>" ;
					lblPts.setText(res);
					questionTitle.setText("Title: " + question.getName());
					questionTitle.setSize(questionTitle.getPreferredSize());
					selQuestion = question;
					questAnswer = selQuestion.getCorrectAnswer();
					
					
				}				
				
			}
			
		});
		
		String[] cBoxStrings = {"Text Question", "Multiple Choice Question"};
		JComboBox<Object> cBox = new JComboBox<Object>(cBoxStrings);
		cBox.setSelectedIndex(0);
		selected = (String) cBox.getSelectedItem();
		cBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = (String) cBox.getSelectedItem();
			}
		});
		cBox.setBounds(250, 300, 200, 30);
		contentPane.add(cBox);
		
		listQuestion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnRemove = new JButton("Remove Selected");
		btnRemove.setName("removeQuestion");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selInd = listQuestion.getSelectedIndex();
				if (selQuestion == null || selInd < 0 ) {
					JOptionPane.showMessageDialog(HHSavedQuestionsPage.this, "Please select an assessment to remove.");
				} else {
					DbQuestions dq = new DbQuestions();
					dq.removeQuestion(selQuestion.getQid());
					lstQuestion.remove(selInd);
					questions.remove(selInd);	
				}
			}
		});

		
		JButton btnAdd = new JButton("Add Question");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected.equals("Text Question")) {
					HHCreateTextQuestion frame = new HHCreateTextQuestion();
					sf.switchForm(frame);
					if (frame.isShowing()){
						dispose();
					}
				}
				else if (selected.equals("Multiple Choice Question")) {
					AddMultipleChoice frame = new AddMultipleChoice();
					sf.switchForm(frame);
					if (frame.isShowing()){
						dispose();
					}
				}
			}
		});
		btnAdd.setMaximumSize(new Dimension(139, 23));
		btnAdd.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				
		if (SelectedUser.getUser().isProf()){
			btnAdd.setBounds(58, 301, 133, 36);
			contentPane.add(btnAdd);
			
			btnRemove.setBounds(58, 342, 133, 35);
			contentPane.add(btnRemove);
		} else {
			questionAnswerField = new JTextField();
			contentPane.add(questionAnswerField);
			questionAnswerField.setBackground(Color.WHITE);
			questionAnswerField.setAlignmentY(Component.TOP_ALIGNMENT);
			questionAnswerField.setColumns(10);
			questionAnswerField.setBounds(29, 310, 247, 57);
			questionAnswerField.setFont(new Font("Dialog", Font.PLAIN, 14));
			questionAnswerField.setSelectedTextColor(Color.black);
			JButton btnView = new JButton("Submit Answer");
			btnView.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String answer = String.valueOf(questionAnswerField.getText());
					
					if (selQuestion == null) {
						JOptionPane.showMessageDialog(HHSavedQuestionsPage.this, "No questions selected.");
					} else if (answer.isEmpty()) {
						JOptionPane.showMessageDialog(HHSavedQuestionsPage.this, "Answer field is empty.");
					} else {
						questAnswer = selQuestion.getCorrectAnswer();
						if (questAnswer == null) {
							JOptionPane.showMessageDialog(HHSavedQuestionsPage.this, "Question doesn't have a corresponding answer.");
						} else {
							if (questAnswer.isCorrect(answer.toString())) {
								JOptionPane.showMessageDialog(HHSavedQuestionsPage.this, "Correct!");
							} else {
								JOptionPane.showMessageDialog(HHSavedQuestionsPage.this, "Incorrect!");
							}
							
						}
					}				
				}
			});
			btnView.setBounds(288, 325, 120, 31);
			contentPane.add(btnView);
		}
		
	}
}
