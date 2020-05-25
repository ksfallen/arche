package com.yhml.tools.ssr.baidu;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author: Jfeng
 * @date: 2019-06-18
 */
@Data
public class BaiduOcrResult {
    @JSONField(name = "words_result")
    private List<WordsResult> wordsResult;
}

