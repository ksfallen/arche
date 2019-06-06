package com.yhml.bd.bd.hadoop.wc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @author: Jfeng
 * @date: 2017/12/27
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private Text word = new Text();
    private IntWritable count = new IntWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 每一行数据
        String line = value.toString();

        String[] words = line.split(" ");

        for (String w : words) {
            word.set(w);
            count.set(1);

            context.write(word, count);
            
        }
    }
}
