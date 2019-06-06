package com.yhml.bd.bd.hadoop.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 统计字数
 *
 * @author: Jfeng
 * @date: 2017/12/27
 */
public class WordCountMapReduce {

    public static void main(String[] args) throws Exception {

        // 配置对象
        Configuration conf = new Configuration();

        // job 对象
        Job job = Job.getInstance(conf, "wordcount");

        job.setJarByClass(WordCountMapReduce.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReduce.class);

        // mapper输出的key value
        job.setMapOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // reduce输出的key value 类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(""));
        FileOutputFormat.setOutputPath(job, new Path(""));

        // 提交任务
        boolean b = job.waitForCompletion(true);
        if (!b) {
            System.err.println("task error");
        }
    }
}
