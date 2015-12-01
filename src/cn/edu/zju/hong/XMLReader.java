package cn.edu.zju.hong;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class XMLReader {
	SAXBuilder sb = new SAXBuilder();
    Document doc = null;
    
//    new the arraylist to store the element of xml
    ArrayList<String> id_list = new ArrayList<String>(); 
    ArrayList<String> title_list = new ArrayList<String>(); 
    ArrayList<String> content_list = new ArrayList<String>(); 
    ArrayList<String> user_name_list = new ArrayList<String>(); 
    ArrayList<String> user_sex_list = new ArrayList<String>(); 
    ArrayList<String> user_age_list = new ArrayList<String>();
    ArrayList<String> ask_time_list = new ArrayList<String>(); 
    ArrayList<String> ans1_name_list = new ArrayList<String>(); 
    ArrayList<String> ans1_job_title_list = new ArrayList<String>(); 
    ArrayList<String> ans1_answer_list = new ArrayList<String>(); 
    ArrayList<String> ans1_time_list = new ArrayList<String>(); 
    ArrayList<String> ans2_name_list = new ArrayList<String>(); 
    ArrayList<String> ans2_job_title_list = new ArrayList<String>(); 
    ArrayList<String> ans2_answer_list = new ArrayList<String>(); 
    ArrayList<String> ans2_time_list = new ArrayList<String>();
    ArrayList<String> ans3_name_list = new ArrayList<String>(); 
    ArrayList<String> ans3_job_title_list = new ArrayList<String>(); 
    ArrayList<String> ans3_answer_list = new ArrayList<String>(); 
    ArrayList<String> ans3_time_list = new ArrayList<String>();
    ArrayList<String> ans4_name_list = new ArrayList<String>(); 
    ArrayList<String> ans4_job_title_list = new ArrayList<String>(); 
    ArrayList<String> ans4_answer_list = new ArrayList<String>(); 
    ArrayList<String> ans4_time_list = new ArrayList<String>();
    ArrayList<String> ans5_name_list = new ArrayList<String>(); 
    ArrayList<String> ans5_job_title_list = new ArrayList<String>(); 
    ArrayList<String> ans5_answer_list = new ArrayList<String>(); 
    ArrayList<String> ans5_time_list = new ArrayList<String>();
    
    public void readXml(String filePath){
    	try{
    		
    		doc = sb.build(filePath);   // path
            Element root = doc.getRootElement();
                        
            List<Element> list = root.getChildren("问答信息");
            
            
            for (Element el : list) {
                String id = el.getAttributeValue("id");
                id_list.add(id);
                
                String title = el.getChildText("问题标题");
                title_list.add(title);
                
            	String content = el.getChildText("问题内容");
            	content_list.add(content);
            	
            	String user_name = el.getChildText("提问者用户名");
            	user_name_list.add(user_name);
            	
            	String user_sex = el.getChildText("提问者性别");
            	user_sex_list.add(user_sex);
            	
            	String user_age = el.getChildText("提问者年龄");
            	user_age_list.add(user_age);
            	
            	String ask_time = el.getChildText("提问时间");
            	ask_time_list.add(ask_time);
            	
            	String ans1_name = el.getChildText("回复人1姓名");
            	ans1_name_list.add(ans1_name);
            	
            	String ans1_job_title = el.getChildText("回复人1职称");
            	ans1_job_title_list.add(ans1_job_title);
            	
            	String ans1_answer = el.getChildText("回复人1分析");
            	ans1_answer_list.add(ans1_answer);
            	
            	String ans1_time = el.getChildText("回复人1回复时间");
            	ans1_time_list.add(ans1_time);
            	
            	String ans2_name = el.getChildText("回复人2姓名");
            	ans2_name_list.add(ans2_name);
            	
            	String ans2_job_title = el.getChildText("回复人2职称");
            	ans2_job_title_list.add(ans2_job_title);
            	
            	String ans2_answer = el.getChildText("回复人2分析");
            	ans2_answer_list.add(ans2_answer);
            	
            	String ans2_time = el.getChildText("回复人2回复时间");
            	ans2_time_list.add(ans2_time);
            	
            	String ans3_name = el.getChildText("回复人3姓名");
            	ans3_name_list.add(ans3_name);
            	
            	String ans3_job_title = el.getChildText("回复人3职称");
            	ans3_job_title_list.add(ans3_job_title);
            	
            	String ans3_answer = el.getChildText("回复人3分析");
            	ans3_answer_list.add(ans3_answer);
            	
            	String ans3_time = el.getChildText("回复人3回复时间");
            	ans3_time_list.add(ans3_time);
            	
            	String ans4_name = el.getChildText("回复人4姓名");
            	ans4_name_list.add(ans4_name);
            	
            	String ans4_job_title = el.getChildText("回复人4职称");
            	ans4_job_title_list.add(ans4_job_title);
            	
            	String ans4_answer = el.getChildText("回复人4分析");
            	ans4_answer_list.add(ans4_answer);
            	
            	String ans4_time = el.getChildText("回复人4回复时间");
            	ans4_time_list.add(ans4_time);
            	
            	String ans5_name = el.getChildText("回复人5姓名");
            	ans5_name_list.add(ans5_name);
            	
            	String ans5_job_title = el.getChildText("回复人5职称");
            	ans5_job_title_list.add(ans5_job_title);
            	
            	String ans5_answer = el.getChildText("回复人5分析");
            	ans5_answer_list.add(ans5_answer);
            	
            	String ans5_time = el.getChildText("回复人5回复时间");
            	ans5_time_list.add(ans5_time);
            }
    	}catch (Exception e) {
            e.printStackTrace();
    	}
    }
    
    
}


