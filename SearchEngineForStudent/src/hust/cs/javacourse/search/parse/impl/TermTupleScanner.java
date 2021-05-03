package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TermTupleScanner extends AbstractTermTupleScanner {
    /**
     * 文件中的三元组，用队列Queue实现
     */
    private final Queue<AbstractTermTuple> tuples = new LinkedList<>();

    /**
     * 单词出现的位置
     */
    private int pos = 0;

    /**
     * 将字符串切分成单词时所需的正则表达式。
     * 例如根据中英文的逗号,分号,句号，问号，冒号,感叹号，中文顿号，空白分割符进行切分。
     */
    private final StringSplitter splitter ;

    /**
     * 构造函数
     * @param bufferedReader : 输入流对象
     */
    public TermTupleScanner(BufferedReader bufferedReader){
        super(bufferedReader);
        splitter = new StringSplitter();
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    /**
     * 获得下一个三元组
     * @return : 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        if(tuples.size()==0){
            try {
                String line=input.readLine();
                //读取到一个不为空行的行为止
                while(line!=null&& line.equals("")) line=input.readLine();
                if(line!=null){
                    //通过正则表达式得到单词
                    List<String> term= splitter.splitByRegex(line);
                    for(String i:term){
                        //将单词为i,位置为pos的三元组加入队列，且pos++

                        tuples.add(new TermTuple(new Term(i),pos++));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回队列中的第一个三元组
        return tuples.poll();
    }
}
