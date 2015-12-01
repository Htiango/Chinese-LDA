package cn.edu.zju.lda;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * a set of documents
 * 
 *
 * @author hty
 */
public class Corpus
{
    List<int[]> documentList;
    Vocabulary vocabulary;
//    private static ArrayList<ArrayList <String>> list2;

    public Corpus()
    {
        documentList = new LinkedList<int[]>();
        vocabulary = new Vocabulary();
    }

    public int[] addDocument(List<String> document)
    {
        int[] doc = new int[document.size()];
        int i = 0;
        for (String word : document)
        {
            doc[i++] = vocabulary.getId(word, true);
        }
        documentList.add(doc);
        return doc;
    }

    public int[][] toArray()
    {
        return documentList.toArray(new int[0][]);
    }

    public int getVocabularySize()
    {
        return vocabulary.size();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        for (int[] doc : documentList)
        {
            sb.append(Arrays.toString(doc)).append("\n");
        }
        sb.append(vocabulary);
        return sb.toString();
    }

    /**
     * Load documents from disk
     *
     * @param folderPath is a folder, which contains text documents.
     * @return a corpus
     * @throws IOException
     */
    public static Corpus loading(ArrayList<String> segStuff) 
    {
        Corpus corpus = new Corpus();

        for (int i = 0; i < segStuff.size(); i ++)
        {
            
            String line;
            line = segStuff.get(i);
            List<String> wordList = new LinkedList<String>();
            if (line != null)
            {
                String[] words = line.split(" ");
                for (String word : words)
                {
                    if (word.trim().length() < 2) continue;
//                    if (wordList.contains(word)) continue;
                    wordList.add(word);
                }
            }
            corpus.addDocument(wordList);
        }
        if (corpus.getVocabularySize() == 0) return null;

        return corpus;
    }
    
    public static Corpus loadQuestionAnswer(ArrayList<String> question, ArrayList<String> ans1) 
    {
        Corpus corpus = new Corpus();
        
        ArrayList<String> ans = new ArrayList<String>();
        ans.addAll(question);
        ans.addAll(ans1);
        
        ans.remove(null);
        
        for (int i = 0; i < ans.size(); i ++)
        {
            
            String line;
            line = ans.get(i);
            List<String> wordList = new LinkedList<String>();
            if (line != null)
            {
                String[] words = line.split(" ");
                for (String word : words)
                {
                    if (word.trim().length() < 2) continue;
                    wordList.add(word);
                }
            }
            corpus.addDocument(wordList);
        }       
        if (corpus.getVocabularySize() == 0) return null;

        return corpus;
    }
    
    
    public Vocabulary getVocabulary()
    {
        return vocabulary;
    }

    public int[][] getDocument()
    {
        return toArray();
    }

    public static int[] loadDocument(String path, Vocabulary vocabulary) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        List<Integer> wordList = new LinkedList<Integer>();
        while ((line = br.readLine()) != null)
        {
            String[] words = line.split(" ");
            for (String word : words)
            {
                if (word.trim().length() < 2) continue;
                Integer id = vocabulary.getId(word);
                if (id != null)
                    wordList.add(id);
            }
        }
        br.close();
        int[] result = new int[wordList.size()];
        int i = 0;
        for (Integer integer : wordList)
        {
            result[i++] = integer;
        }
        return result;
    }
}
