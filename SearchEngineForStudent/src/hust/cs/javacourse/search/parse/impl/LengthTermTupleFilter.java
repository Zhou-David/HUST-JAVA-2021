package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 获得下一个三元组
     * 基于单词长度的过滤器
     * @return : 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple filter=input.next();
        //长度过滤，过滤掉长度小于3或长度大于20的单词
        while(filter!=null&&
                (filter.term.getContent().length()> Config.TERM_FILTER_MAXLENGTH||
                        filter.term.getContent().length()<Config.TERM_FILTER_MINLENGTH)){
            filter=input.next();
        }
        return filter;
    }
}
