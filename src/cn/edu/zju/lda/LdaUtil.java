package cn.edu.zju.lda;


import java.util.*;

/**
 * @author hty
 */
public class LdaUtil
{
    /**
     * To translate a LDA matrix to readable result
     * @param phi the LDA model
     * @param vocabulary
     * @param limit limit of max words in a topic
     * @return a map array
     */
    public static Map<String, Double>[] translate(double[][] phi, Vocabulary vocabulary, int limit)
    {
        limit = Math.min(limit, phi[0].length);
        @SuppressWarnings("unchecked")
		Map<String, Double>[] result = new Map[phi.length];
        for (int k = 0; k < phi.length; k++)
        {
            Map<Double, String> rankMap = new TreeMap<Double, String>(Collections.reverseOrder());
            for (int i = 0; i < phi[k].length; i++)
            {
                rankMap.put(phi[k][i], vocabulary.getWord(i));
            }
            Iterator<Map.Entry<Double, String>> iterator = rankMap.entrySet().iterator();
            result[k] = new LinkedHashMap<String, Double>();
            for (int i = 0; i < limit; ++i)
            {
                Map.Entry<Double, String> entry = iterator.next();
                result[k].put(entry.getValue(), entry.getKey());
            }
        }
        return result;
    }

    public static Map<String, Double> translate(double[] tp, double[][] phi, Vocabulary vocabulary, int limit)
    {
        Map<String, Double>[] topicMapArray = translate(phi, vocabulary, limit);
        double p = -1.0;
        int t = -1;
        for (int k = 0; k < tp.length; k++)
        {
            if (tp[k] > p)
            {
                p = tp[k];
                t = k;
            }
        }
        return topicMapArray[t];
    }

    /**
     * To print the result in a well formatted form
     * @param result
     */
    public static void explain(Map<String, Double>[] result, ArrayList<String> outputs)
    {
//    	outputs = new ArrayList<String> ();
        int i = 0;
        for (Map<String, Double> topicMap : result)
        {
            ArrayList<String> output = new ArrayList<String>();
            output.add("topic " + i +" :");
            String secquence = Integer.toString(i);
            
            
            if (i < 10){
                secquence = "0" + secquence;
            }
            
            System.out.printf("topic %d :\n", i++);
            explain(topicMap, output);
            System.out.println();            
            
            String outcomes;
            outcomes = join(output, "\n");            
            
            
            outputs.add(outcomes);
            
        }
    }

    public static void explain(Map<String, Double> topicMap, ArrayList<String> output)
    {
        for (Map.Entry<String, Double> entry : topicMap.entrySet())
        {
            System.out.println(entry);
            String result = entry.toString();
            output.add(result);            
            
        }
    }
    
    public static void explain2(Map<String, Double>[] result, ArrayList<String> outputs, ArrayList<Double> values){
    	for (Map<String,Double> topicMap : result){
    		explain2(topicMap,outputs,values);
    	}
    }
    
    public static void explain2(Map<String, Double> topicMap, ArrayList<String> outputs, ArrayList<Double> values){
    	for (Map.Entry<String, Double> entry : topicMap.entrySet()){
    		System.out.println(entry);
    		outputs.add(entry.getKey());
    		values.add(entry.getValue());
    	}
    }
    
    static public String join(List<String> list, String conjunction)
    {
       StringBuilder sb = new StringBuilder();
       boolean first = true;
       for (String item : list)
       {
          if (first)
             first = false;
          else
             sb.append(conjunction);
          sb.append(item);
       }
       return sb.toString();
    }
    
    
    
    
}