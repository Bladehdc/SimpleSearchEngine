package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.alibaba.fastjson.JSON;

import javafx.util.Pair;

public class BuildIndex {
	private static String filePath = "resource/json/0.json";// 源文件所在位置  
    private static String indexDir = "C:\\Users\\blade\\Desktop\\搜索引擎\\SimpleSearchEngine\\SimpleSearch\\index";// 索引目录  
    private static final Version VERSION = Version.LUCENE_4_9;// lucene版本  
    public int totalnum = 0;
    public Map<String,String> map = null;
	public BufferedReader reader = null;
	
	public BuildIndex() {
		map = new HashMap<String, String>();
	}
	
	/*public void ReadJsonFile() {
		File file = new File(filePath);
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void jsonToMap(String value) {
		map.clear();
		map = JSON.parseObject(value, Map.class);
		return;
	}
    
    public void createIndex() throws IOException {  
        Directory director = FSDirectory.open(new File(indexDir));// 创建Directory关联源文件  
        Analyzer analyzer = new IKAnalyzer(false);// 语意分词 
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(VERSION, analyzer);// 创建索引的配置信息  
        IndexWriter indexWriter = new IndexWriter(director, indexWriterConfig);  
        Document doc = new Document();// 创建文档  
        Field field1 = new TextField("title", map.get("title"), Store.YES);
        Field field2 = new TextField("content", map.get("content"), Store.YES);
        doc.add(field1);// 添加field域到文档中  
        doc.add(field2);
        indexWriter.addDocument(doc);// 添加文本到索引中  
        indexWriter.close();// 关闭索引  
    }*/

    public List<Pair<String,String>> query(String searchstring) throws IOException, ParseException, InvalidTokenOffsetsException {  
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexDir)));// 索引读取类  
        IndexSearcher search = new IndexSearcher(reader);// 搜索入口工具类
        Analyzer analyzer = new IKAnalyzer(false);// 分词器  
        String queryStr = searchstring;// 搜索关键字  
        QueryParser queryParser = new QueryParser(VERSION, "content", analyzer);// 实例查询条件类  
        Query query = queryParser.parse(queryStr);  
        TopDocs topdocs = search.search(query, 100);// 查询前100条  
        System.out.println("查询结果总数---" + topdocs.totalHits);
        totalnum = topdocs.totalHits;
        ScoreDoc scores[] = topdocs.scoreDocs;// 得到所有结果集 
        Formatter formatter = new SimpleHTMLFormatter("<em>", "</em>");// 高亮html格式 
        Scorer score = new QueryScorer(query);// 检索评份  
        Fragmenter fragmenter = new SimpleFragmenter(100);// 设置最大片断为100  
        Highlighter highlighter = new Highlighter(formatter, score);// 高亮显示类  
        highlighter.setTextFragmenter(fragmenter);// 设置格式  
        TokenStream tokenc = null;
        String str1 = null;
        String str2 = null;
        List<Pair<String,String>> ans=new ArrayList<>();
        for (int i = 0; i < scores.length; i++) {  
            int num = scores[i].doc;// 得到文档id  
            Document document = search.doc(num);// 拿到指定的文档 
            String content = document.get("content"); 
            String title = document.get("title");
            if (content != null) {  
                tokenc = analyzer.tokenStream("content", new StringReader(content));
            	str2 = highlighter.getBestFragment(tokenc, content);
                tokenc = analyzer.tokenStream("title", new StringReader(title));
				str1 = highlighter.getBestFragment(tokenc, title);
            }else {
            	continue;
            }
            if(str1 == null) {
            	str1 = title;
            }
            Pair<String,String> p = new Pair(str1, str2+"...");// 由于内容没有存储所以执行结果为null  
            ans.add(p);
        }
        return ans;
    } 
    
    /*public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
    	BuildIndex ab = new BuildIndex();
    	String value = null;
    	ab.ReadJsonFile();
		while(true) {
			value = ab.reader.readLine();
			if(value == null) {
				break;
			}
			ab.jsonToMap(value);
			ab.createIndex();
			System.out.println("Done");
			break;
		}
		//List<Pair<String,String>> as = ab.query();
		ab.reader.close();
    }*/
}
