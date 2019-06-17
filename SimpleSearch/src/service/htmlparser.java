package service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.File;

import java.nio.file.FileSystems;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.tokenattributes.TermAttribute;


import org.wltea.analyzer.lucene.IKAnalyzer;
public class htmlparser {
    public static void queryIndex(String indexPath, String keyword) {
        try{
            Directory directory = FSDirectory.open(new File(indexPath));
            System.out.println(250);
            DirectoryReader reader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(reader);
            TermQuery query2 = new TermQuery(new Term("content",keyword));
            TopDocs topDocs = indexSearcher.search(query2, 100);
            System.out.println("共找到 " + topDocs.totalHits + " 处匹配");
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                Document doc = indexSearcher.doc(scoreDoc.doc);
                String fileName = doc.get("fileName");
                System.out.println("fileName: " + fileName);
            }
            if(reader != null) {
                reader.close();
            }

        }catch (Exception e){

        }



    }



    public static void main (String[] args) throws IOException{
//        File linkerdoc=new File("D:\\大三下\\搜索引擎技术基础\\大作业数据\\heritrix-1.14.4\\jobs\\6_15-20190615044445341\\logs\\html_linker.txt");
//        FileInputStream is = null;
//        StringBuilder stringBuilder = null;
//        try {
//                is = new FileInputStream(linkerdoc);
//                InputStreamReader streamReader = new InputStreamReader(is);
//                BufferedReader reader = new BufferedReader(streamReader);
//                String line;
//                stringBuilder = new StringBuilder();
//                while ((line = reader.readLine()) != null) {
//                    // stringBuilder.append(line);
//                    String path = "D:\\大三下\\搜索引擎技术基础\\大作业数据\\heritrix-1.14.4\\jobs\\6_15-20190615044445341\\mirror"+line.substring(6);
//
//                    String title="";
//                    String article="";
//                    if(path.endsWith(".pdf"))
//                    {
//                        article=gethtml.getpdf(path);
//                        if(article=="")
//                            continue;
//                    }
//                    else if(path.endsWith(".doc")||path.endsWith(".docx"))
//                    {
//                        article=gethtml.getdoc(path);
//                        if(article=="")
//                            continue;
//                    }
//                    else
//                    {
//                        title =gethtml.gettitle(path);
//                        article =gethtml.getarticle(path);
//                        if(title==""||article=="")
//                            continue;
//                    }
//
//
//                    String content=title+article;
//                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//                    Matcher m = p.matcher(content);
//                    Analyzer analyzer;;
//                    if(m.find())
//                        analyzer = new IKAnalyzer();
//                    else
//                        analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
//                    IndexWriter indexWriter=null;
//                    IndexWriterConfig indexWriterConfig;
//                    try{
//                        Directory directory = FSDirectory.open(new File("D:/suoyin"));
//                        indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_9,analyzer);
//                        indexWriter = new IndexWriter(directory, indexWriterConfig); // 清除以前的索引
//
//
//
//
//                    }catch (Exception e){
//
//                    }
//
//
//
//                    Document document = new Document();
//
//                    document.add(new Field("fileName", line, TextField.TYPE_STORED));
//                    document.add(new Field("content", content, TextField.TYPE_STORED)); /*
//                     *Step4：使用 IndexWrite对象将Document对象写入索引库，并进行索引。
//                     */
//                    try {
//                        indexWriter.addDocument(document);
//
//
//                    }catch (Exception e){}
//
//
//                if(indexWriter != null) {
//                    try{
//                        indexWriter.close();
//                    }catch (Exception e){}
//
//
//                    }
//
//
//                    }
//                            reader.close();
//                            is.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
            queryIndex("C:\\Users\\blade\\Desktop\\搜索引擎\\SimpleSearchEngine\\SimpleSearch\\index","校长");

            }

    }



