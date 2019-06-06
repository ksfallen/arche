package com.yhml.bd.bd.hadoop.wc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author: Jfeng
 * @date: 2017/12/27
 */
public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable sum = new IntWritable();


    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        Integer count = 0;

        for (IntWritable value : values) {
            count += value.get()    ;
        }
        sum.set(count);

        context.write(key, sum);
    }
}
