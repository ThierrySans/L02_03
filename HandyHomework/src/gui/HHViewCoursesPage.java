package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import course.Course;
import course.SelectedCourse;

import dao.DbCourse;
import login.SelectedUser;
import login.UserLogin;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class HHViewCoursesPage extends JFrame {

	private Course selectedCourse;
	private JPanel contentPane;
	private JList<?> list;
	private List<Course> courses;
	private int selInd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HHViewCoursesPage frame = new HHViewCoursesPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HHViewCoursesPage() {
		SwitchForm sf = new SwitchForm();
		setTitle("HandyHomework - Courses");
		this.setName("viewCourse");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblCourses = new JLabel("Courses");
		lblCourses.setBounds(161, 20, 101, 30);
		lblCourses.setFont(new Font("Lucida Grande", Font.BOLD, 24));		

		DefaultListModel<String> lstCourses = new DefaultListModel<>();
		
		if (!SelectedUser.isSelected()) {
			JOptionPane.showMessageDialog(HHViewCoursesPage.this, "No user logged in");
		} else {
			UserLogin user = SelectedUser.getUser();
			int uid = SelectedUser.getUser().getId();
			DbCourse dbCourse = new DbCourse();
			courses = dbCourse.managedCourses(uid);
			
			for (Course cse : courses) {
				lstCourses.addElement(cse.getCourseCode() + ":" + cse.getTerm());
			}
		
		}
		
		JList listCourses = new JList<>(lstCourses);
		listCourses.setBounds(66, 68, 304, 119);
		listCourses.setName("courses");
		
		listCourses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList list = (JList) e.getSource();
				int index = list.getSelectedIndex();
				if (index != -1) {
					Course as = courses.get(list.getSelectedIndex());
					selectedCourse = as;
					selInd = index;
				}			
				
			}
		});
		
		listCourses.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JList listCourses = (JList)e.getSource();
				if (e.getClickCount() == 2) {
					SelectedCourse.setCourse(selectedCourse);
					
					HHSavedAssessments frame = new HHSavedAssessments();
					sf.switchForm(frame);
					if (frame.isShowing()){
						dispose();
					}
				}
			}
		});
		
		
		JButton btnSelectCourse = new JButton("Select Course");
		btnSelectCourse.setName("Select Course");
		btnSelectCourse.setBounds(300, 209, 129, 35);
		btnSelectCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listCourses.isSelectionEmpty()){
					JOptionPane.showMessageDialog(HHViewCoursesPage.this, "Please select a course.");
				} else {
					
					if (selectedCourse == null) {
						JOptionPane.showMessageDialog(HHViewCoursesPage.this, "Please select a course.");
					} else {
						SelectedCourse.setCourse(selectedCourse);
						
						HHSavedAssessments frame = new HHSavedAssessments();
						sf.switchForm(frame);
						if (frame.isShowing()){
							dispose();
						}
					}
				}
			}
		});
		
		JButton btnBack = new JButton("\u2190 Back");
		btnBack.setBounds(15, 20, 100, 35);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HandyHomeworkMainPage frame = new HandyHomeworkMainPage();
				sf.switchForm(frame);
				if (frame.isShowing()){
					dispose();
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnSelectCourse);
		contentPane.add(btnBack);
		contentPane.add(listCourses);
		contentPane.add(lblCourses);
		

		JButton btnRemove = new JButton("Remove Course");
		btnRemove.setName("removeCourse");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedCourse == null || selInd < 0 ) {
					JOptionPane.showMessageDialog(HHViewCoursesPage.this, "Please select a course to remove.");
				} else {
					DbCourse dbcourse = new DbCourse();
					dbcourse.removeManagedCourses(SelectedUser.getUser().getId(), selectedCourse.getcID());
					lstCourses.remove(selInd);
					courses.remove(selInd);
				}
			}
		});
		
		
		if (SelectedUser.getUser().isProf()) {
			JButton btnAddCourse = new JButton("Add Course");
			contentPane.getRootPane().setDefaultButton(btnAddCourse);
			btnAddCourse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					HHCreateCoursePage frame = new HHCreateCoursePage();
					sf.switchForm(frame);
					if (frame.isShowing()){
						dispose();
					}
				}
			});
			btnAddCourse.setBounds(163, 209, 131, 35);
			contentPane.add(btnAddCourse);
			
			btnRemove.setBounds(15, 209, 141, 35);
			contentPane.add(btnRemove);
		}
	}
}

