package com.sist.lastproject.util;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.springframework.stereotype.Component;

import java.util.Comparator;
@Component
public class KeywordParser {
    public String parseWords(String comment) {
        // KeywordExtractor 초기화
        KeywordExtractor ke = new KeywordExtractor();
        // 키워드 추출
        KeywordList kl = ke.extractKeyword(comment, true);

        StringBuffer comments = new StringBuffer();
        // 키워드 & 사용 빈도 취합
        kl.stream().sorted(Comparator.comparing(Keyword::getCnt).reversed())
                   .limit(20)
                   .forEach(k -> comments.append(k.getString() + "%" + k.getCnt() + "/"));

        return comments.toString();
    }
}
