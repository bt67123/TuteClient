package cn.edu.tute.tuteclient.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;


import cn.edu.tute.tuteclient.domain.Course;
import cn.edu.tute.tuteclient.domain.News;
import cn.edu.tute.tuteclient.domain.Person;
import cn.edu.tute.tuteclient.domain.Teacher;
import cn.edu.tute.tuteclient.domain.Course.CourseTime;

public class JsonService {

	public static Person getPerson(String str) throws JSONException {
		JSONObject jsonObject = new JSONObject(str);
		String name      = jsonObject.getJSONArray("login").getJSONObject(0).getString("name");
		int    collegeID = jsonObject.getJSONArray("login").getJSONObject(0).getInt("collegeID");
		if (name.equals("")) {
			System.out.println("zmyw");
			throw new JSONException("账号或密码有误");
		}
		return new Person(name, collegeID);
	}
	
	public static List<Course> getCourse(String str) throws JSONException {
		JSONObject jsonObject = new JSONObject(str);
		JSONArray jsonArray = jsonObject.getJSONArray("course");
		int length = jsonObject.getJSONArray("course").length();
		List<Course> courses = new ArrayList<Course>();
		for (int i = 0; i < length; i++) {
			String code = jsonArray.getJSONObject(i).getString("code");
			code = code.substring(0, code.length()-1);
			code = code.replace("<A", "");
			String[] weeksStrings = code.split(">");
			List<Integer> weeks = new ArrayList<Integer>();
			for (String week : weeksStrings) {
				weeks.add(Integer.parseInt(week));
			}

			//拆分 description ，得到 courseName, teacher, classroom
			String description = jsonArray.getJSONObject(i).getString("Description");
			String splits[] = description.split("<br/>");
			String courseName = splits[0];
			String teacherSplits[] = splits[1].split("\\(");
			String teacherName = teacherSplits[0];
			String teacherID = teacherSplits[1].substring(0, teacherSplits[1].length()-1);
			Teacher teacher = new Teacher(teacherID, teacherName);
			String classroom = splits[2].split(",")[0];

			String courseTimeString = jsonArray.getJSONObject(i).getString("CourseTime");
			String courseTimeSplits[] = courseTimeString.split("&nbsp;&nbsp;");
			Course course = new Course(
					courseName, 
					teacher, 
					classroom, 
					courseTimeSplits[0], courseTimeSplits[1], 
					weeks);
			courses.add(course);
		}
		return courses;
	}
	
	
	public static List<News> getNews(String str) throws JSONException {
		JSONObject jsonObject = new JSONObject(str);
		JSONArray jsonArray = jsonObject.getJSONArray("news1");
		List<News> news = new ArrayList<News>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String id        = jsonArray.getJSONObject(i).getString("Id");
			String title     = jsonArray.getJSONObject(i).getString("NewsTitle");
			String type      = jsonArray.getJSONObject(i).getString("NewsType");
			String pubDate   = jsonArray.getJSONObject(i).getString("puDate");
			String pubPerson = jsonArray.getJSONObject(i).getString("personname");
			news.add(new News(id, title, type, pubDate, pubPerson));
		}
		System.out.println("getNews");
		return news;
	}
}
