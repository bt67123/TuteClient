package cn.edu.tute.tuteclient.domain;

import java.util.List;

import android.R.integer;

public class Course {
	public static final String[] DAY_OF_WEEK = {
		"星期一", "星期二", "星期三", "星期四", "星期五",
	};
	
	public static final String[] COURSE_OF_DAY = {
		"12节", "34节", "56节", "78节", "晚上", 
	};
	
	public class CourseTime {

		private int dayOfWeek;
		private int courseOfDay;
		public CourseTime(int dayOfWeek, int courseOfDay) {
			this.setDayOfWeek(dayOfWeek);
			this.setCourseOfDay(courseOfDay);
    	}
		public int getDayOfWeek() {
			return dayOfWeek;
		}
		public void setDayOfWeek(int dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}
		public int getCourseOfDay() {
			return courseOfDay;
		}
		public void setCourseOfDay(int courseOfDay) {
			this.courseOfDay = courseOfDay;
		}
		@Override
		public String toString() {
			return dayOfWeek + "," + courseOfDay;
		}
	}


	private String name;
	private Teacher teacher;
	private String classroom;
	private CourseTime courseTime;
	private List<Integer> weeks;
	
	public Course(String name, Teacher teacher, String classroom, String dayOfWeek, String courseOfDay, List<Integer> weeks) {
		this.setName(name);
		this.setTeacher(teacher);
		this.setClassroom(classroom);
		this.setCourseTime(dayOfWeek, courseOfDay);
		this.setWeeks(weeks);
	}
	
	public String getName() {
		return name;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public String getClassroom() {
		return classroom;
	}
	
	public CourseTime getCourseTime() {
		return courseTime;
	}
	
	public List<Integer> getWeeks() {
		return weeks;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	
	public void setCourseTime(String dayOfWeek, String courseOfDay) {
		int row = 0;
		int col = 0;
		for (int i = 0; i < DAY_OF_WEEK.length; i++) {
			if (dayOfWeek.equals(DAY_OF_WEEK[i])) {
				row = i;
    			for (int j = 0; j < COURSE_OF_DAY.length; j++) {
    				if (courseOfDay.equals(COURSE_OF_DAY[j])) {
    					col = j;
					}
	    		}
			}
		}
		this.courseTime = new CourseTime(row, col);
	}
	
	public void setWeeks(List<Integer> weeks) {
		this.weeks = weeks;
	}
}
