package cn.edu.zju.hong;

import java.util.ArrayList;
import java.util.List;

import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.corpus.StopWords;

public class SegWords {
	
//	ArrayList<String> seg_title_list = new ArrayList<String>();
    ArrayList<String> seg_content_list = new ArrayList<String>();
    ArrayList<String> seg_ans1_answer_list = new ArrayList<String>();
    ArrayList<String> seg_ans2_answer_list = new ArrayList<String>();
    ArrayList<String> seg_ans3_answer_list = new ArrayList<String>();
    ArrayList<String> seg_ans4_answer_list = new ArrayList<String>();
    ArrayList<String> seg_ans5_answer_list = new ArrayList<String>();
    
//	ArrayList<String> non_stopwords_seg_title_list = new ArrayList<String>();
    ArrayList<String> non_stopwords_seg_content_list = new ArrayList<String>();
    ArrayList<String> non_stopwords_seg_ans1_answer_list = new ArrayList<String>();
    ArrayList<String> non_stopwords_seg_ans2_answer_list = new ArrayList<String>();
    ArrayList<String> non_stopwords_seg_ans3_answer_list = new ArrayList<String>();
    ArrayList<String> non_stopwords_seg_ans4_answer_list = new ArrayList<String>();
    ArrayList<String> non_stopwords_seg_ans5_answer_list = new ArrayList<String>();
    
//    
//    private XMLReader xmlReader;

    public void segWords(ArrayList<String> content_list, ArrayList<String> ans1_answer_list,
    		ArrayList<String> ans2_answer_list,ArrayList<String> ans3_answer_list,
    		ArrayList<String> ans4_answer_list,ArrayList<String> ans5_answer_list){
    	
//        seg_title_list = ChineseWordSegmentation(xmlReader.);   	
        seg_content_list = ChineseWordSegmentation(content_list);
        seg_ans1_answer_list = ChineseWordSegmentation(ans1_answer_list);
        seg_ans2_answer_list = ChineseWordSegmentation(ans2_answer_list);
        seg_ans3_answer_list = ChineseWordSegmentation(ans3_answer_list);
        seg_ans4_answer_list = ChineseWordSegmentation(ans4_answer_list);
        seg_ans5_answer_list = ChineseWordSegmentation(ans5_answer_list);
        
        StopWords stopWords = new StopWords("./models/stopwords/StopWords.txt"); 
        
        int lenArray = content_list.size();
        
        for (int i = 0; i< lenArray; i++){

        	ridStopWords(seg_content_list.get(i), stopWords,non_stopwords_seg_content_list);
        	ridStopWords(seg_ans1_answer_list.get(i), stopWords,non_stopwords_seg_ans1_answer_list);
        	ridStopWords(seg_ans2_answer_list.get(i), stopWords,non_stopwords_seg_ans2_answer_list);
        	ridStopWords(seg_ans3_answer_list.get(i), stopWords,non_stopwords_seg_ans3_answer_list);
        	ridStopWords(seg_ans4_answer_list.get(i), stopWords,non_stopwords_seg_ans4_answer_list);
        	ridStopWords(seg_ans5_answer_list.get(i), stopWords,non_stopwords_seg_ans5_answer_list);
        }
        
    }
    
    public List<String> segQuestionWords(String questionContent){
    	CWSTagger tag;
    	String seg_questionContent = null;
    	try{
			tag = new CWSTagger("./models/seg.m");
			
			seg_questionContent = tag.tag(questionContent);
		}catch (Exception e) {
            e.printStackTrace();
        }
    	
    	StopWords stopWords = new StopWords("./models/stopwords/StopWords.txt"); 
    	
    	String[] words = seg_questionContent.split("\\s+");		
		List<String> result = stopWords.phraseDel(words);
		
		return result;   	
    }
	
	
	public  ArrayList<String> ChineseWordSegmentation(ArrayList<String> sentense_list){		
		CWSTagger tag;
		ArrayList<String> result_list = new ArrayList<String>(); 
		try{
			tag = new CWSTagger("./models/seg.m");

			for(int i = 0; i < sentense_list.size(); i ++){
				String sentense = sentense_list.get(i);
				String seg_sentense = tag.tag(sentense);
				result_list.add(seg_sentense);
			}
		}catch (Exception e) {
            e.printStackTrace();
        }
		return result_list;
	}
	
	public void ridStopWords(String segSentense,StopWords stopWords, ArrayList<String> list){
		String[] words = segSentense.split("\\s+");		
		List<String> baseWords = stopWords.phraseDel(words);
		
		String result = listToString(baseWords);
		list.add(result);
	}
	
	public static String listToString(List<String> stringList){
		/**
		 *  turning a list to a string
		 */
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(" ");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }

}
