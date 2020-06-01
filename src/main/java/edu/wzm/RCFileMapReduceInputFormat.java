package edu.wzm;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hive.serde2.columnar.BytesRefArrayWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hive.hcatalog.rcfile.RCFileMapReduceRecordReader;

public class RCFileMapReduceInputFormat<K extends LongWritable, V extends BytesRefArrayWritable>
    extends CombineFileInputFormat<LongWritable, BytesRefArrayWritable> {

    @Override
    public RecordReader<LongWritable, BytesRefArrayWritable> createRecordReader(InputSplit split,
                                                                                TaskAttemptContext context) throws IOException {

        context.setStatus(split.toString());
        return new RCFileMapReduceRecordReader<LongWritable, BytesRefArrayWritable>();
    }

    @Override
    public List<InputSplit> getSplits(JobContext job) throws IOException {

        job.getConfiguration().setLong("mapred.min.split.size", SequenceFile.SYNC_INTERVAL);
        return super.getSplits(job);
    }
}