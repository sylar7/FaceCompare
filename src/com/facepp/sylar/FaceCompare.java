package com.facepp.sylar;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import com.facepp.*;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class FaceCompare{
	private static String path1 = null;
	private static String path2 = null;
	private static JSONObject person = null;
	private static int age = 0;
	private static int variation = 0;
	private static String gender = null;
	private static String race = null;
	private static String face1 = null;
	private static String face2 = null;
	
	public static String getSimilarity(){
		JSONObject result = null;
		String similarity = null;
		try{
			HttpRequests httpRequests = new HttpRequests("12f38a60536d1b0a7cb8e363eb54928c", "JyXjW1IcaBqUdYhBWr6LKqB9Xx0JOQAQ");
			PostParameters postParameters = new PostParameters().setFaceId1(face1).setFaceId2(face2);
			
			result =  httpRequests.recognitionCompare(postParameters);
			similarity = result.get("similarity").toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return similarity;
	}
	
	public static String getImage(){
		System.out.println("Please input the image url:");
		Scanner scanner = new Scanner(System.in);
		String path = null;
		try{
			path = scanner.next();
		}catch(Exception e){
			e.printStackTrace();
		}
		return path;
	}
	
	public static JSONObject getPerson(String path){
		JSONObject result = null;
		try{
			HttpRequests httpRequests = new HttpRequests("12f38a60536d1b0a7cb8e363eb54928c", "JyXjW1IcaBqUdYhBWr6LKqB9Xx0JOQAQ");
			PostParameters postParameters = new PostParameters().setUrl(path).setAttribute("all");
			
			result = httpRequests.detectionDetect(postParameters);
			
//			System.out.print(result.getJSONArray("face").getJSONObject(0).getJSONObject("attribute").getJSONObject("age").get("value"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static int getPersonAge(JSONObject person){
		int age = 0;
		try{
			age = Integer.parseInt(person.getJSONArray("face").getJSONObject(0).getJSONObject("attribute").getJSONObject("age").get("value").toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return age;
	}
	
	public static String getPersonRace(JSONObject person){
		String race = null;
		try{
			race = person.getJSONArray("face").getJSONObject(0).getJSONObject("attribute").getJSONObject("race").get("value").toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		if (race.toString().equals(new String("White"))){
			return "Caucasian";
		}else if(race.toString().equals(new String("Asian"))){
			return "Asian";
		}else {
			return "Black";
		}
	}
	
	public static String getPersonGender(JSONObject person){
		String gender = null;
		try{
			gender = person.getJSONArray("face").getJSONObject(0).getJSONObject("attribute").getJSONObject("gender").get("value").toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return gender;
	}
	
	public static int getPersonAgeVariation(JSONObject person){
		int ageVariation = 0;
		try{
			ageVariation = Integer.parseInt(person.getJSONArray("face").getJSONObject(0).getJSONObject("attribute").getJSONObject("age").get("range").toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return ageVariation;
	}
	
	public static String getFaceId(JSONObject person){
		String faceId = null;
		try{
			faceId = person.getJSONArray("face").getJSONObject(0).get("face_id").toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return faceId;
	}
	
	public static void main(String args[]){
		path1 = getImage();
		person = getPerson(path1);
		age = getPersonAge(person);
		variation = getPersonAgeVariation(person);
		race = getPersonRace(person);
		gender = getPersonGender(person);
		face1 = getFaceId(person);
		System.out.println("Person 1 Detection Complete!");
		System.out.println("The person's age is estimated to be " + age + " years old");
		System.out.println("The upper bound of the person's age is " + (age + variation));
		System.out.println("The lower bound of the person's age is " + (age - variation));
		System.out.println("The person is probably a " + race);
		System.out.println("The person's gender is " + gender);
		System.out.println("================================================");
		
		path2 = getImage();
		person = getPerson(path2);
		age = getPersonAge(person);
		variation = getPersonAgeVariation(person);
		race = getPersonRace(person);
		gender = getPersonGender(person);
		face2 = getFaceId(person);
		System.out.println("Person 2 Detection Complete!");
		System.out.println("The person's age is estimated to be " + age + " years old");
		System.out.println("The upper bound of the person's age is " + (age + variation));
		System.out.println("The lower bound of the person's age is " + (age - variation));
		System.out.println("The person is probably a " + race);
		System.out.println("The person's gender is " + gender);
		System.out.println("================================================");
		System.out.println("Person 1 and Person 2's similarity is: " + getSimilarity());
	}
}
