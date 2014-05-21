package au.edu.jcu.it.appframework.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ServerCommFacade {

	public String response = null;

	public ServerCommFacade() {
	}

	public String getSubjects() {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_SUBJECTS"));
		return getServerResponse(pairs);
	}
	
	public String getRoster(String staffEmail) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_STAFF_ROSTER"));
		pairs.add(new BasicNameValuePair("STAFF_EMAIL", staffEmail));
		return getServerResponse(pairs);
	}
	
	public String getClasses() {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_CLASSES"));
		return getServerResponse(pairs);
	}

	public String currentTeachingSubjects(String string) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_STAFF_TEACHING_SUBJECTS"));
		pairs.add(new BasicNameValuePair("STAFF_EMAIL", "kim.ku@jcu.edu.au"));
		return getServerResponse(pairs);
	}
	
	public String getEnrolledStudentsInSubject(String subjectString) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_ENROLLED_STUDENTS_FOR_SUBJECT"));
		pairs.add(new BasicNameValuePair("SUBJECT_CODE", subjectString));
		return getServerResponse(pairs);
	}
	
	public String enrolStudentInClass(String subjectCode, String studentEmail) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "ENROLL_IN_SUBJECT"));
		pairs.add(new BasicNameValuePair("STUDENT_EMAIL", studentEmail));
		pairs.add(new BasicNameValuePair("SUBJECT_CODE", subjectCode));
		return getServerResponse(pairs);
	}
	
	public String addNewQuiz(String quizText, String quizAnswer, String userName) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "ADD_NEW_QUIZ"));
		pairs.add(new BasicNameValuePair("QUIZ_TEXT", quizText));
		pairs.add(new BasicNameValuePair("QUIZ_ANSWER", quizAnswer));
		pairs.add(new BasicNameValuePair("USER_NAME", userName));
		return getServerResponse(pairs);
	}
	
	public String getCurrentQuizes(String lecturerEmail) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_QUIZES"));
		pairs.add(new BasicNameValuePair("STAFF_EMAIL", lecturerEmail));
		return getServerResponse(pairs);
	}
	
	public String createNewAttendanceCode(String attendanceCodeNotes, String subjectCode, String lecturerEmail) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "CREATE_NEW_ATTENDANCE_CODE"));
		pairs.add(new BasicNameValuePair("ATTENDANCE_NOTE", attendanceCodeNotes));
		pairs.add(new BasicNameValuePair("SUBJECT_CODE", subjectCode));
		pairs.add(new BasicNameValuePair("LECTURER_EMAIL", lecturerEmail));
		return getServerResponse(pairs);
	}
	
	public String sendAttendanceCode(String qrCode, String studentEmail) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "PROCESS_ATTENDANCE_CODE"));
		pairs.add(new BasicNameValuePair("STUDENT_EMAIL", studentEmail));
		pairs.add(new BasicNameValuePair("QR_CODE", qrCode));
		return getServerResponse(pairs);
	}
	
	public String getLecturerAttendanceCodes(String staffEmail, String subjectCode) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_LECTURER_ATTENDANCE_RECORDS"));
		pairs.add(new BasicNameValuePair("STAFF_EMAIL", staffEmail));
		pairs.add(new BasicNameValuePair("SUBJECT_CODE", subjectCode));
		return getServerResponse(pairs);
	}
	
	public String getStudentAttendanceForRecord(String recordID) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("Request", "GET_ATTENDANCE_FOR_RECORD"));
		pairs.add(new BasicNameValuePair("RECORD_ID", recordID));
		return getServerResponse(pairs);
	}
	
	@SuppressWarnings("unchecked")
	private String getServerResponse(List<NameValuePair> pairs){
		String response = null;
		try {
			response = new ServerCommunication().execute(pairs).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			
			e.printStackTrace();
		}
		return response;
	}
}