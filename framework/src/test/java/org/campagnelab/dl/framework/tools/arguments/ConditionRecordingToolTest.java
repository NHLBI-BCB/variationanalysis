package org.campagnelab.dl.framework.tools.arguments;

import org.apache.commons.io.FileUtils;
import org.campagnelab.dl.framework.tools.TrainingArguments;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by fac2003 on 11/5/16.
 */
public class ConditionRecordingToolTest {
// disable test for jenkins. We are still chaning the command line.
    public void tryTags() throws IOException {
        TrainingArguments args = new TrainingArguments() {
            @Override
            protected String defaultArchitectureClassname() {
                return "defaultArchitectureClassname";
            }

            @Override
            protected String defaultFeatureMapperClassname() {
                return "defaultFeatureMapperClassname";
            }
        };
        args.architectureClassname = "ABC";
        args.modelConditionFilename = "test-results/model-conditions/1.txt";
        args.learningRate = 1.0d;
        ConditionRecordingTool<TrainingArguments> tool = new ConditionRecordingTool<TrainingArguments>() {
            @Override
            public TrainingArguments createArguments() {
                return args;
            }

            @Override
            public TrainingArguments args() {
                return args;
            }

            @Override
            public void execute() {

            }
        };
        String commandLine = "--model-conditions test-results/model-conditions/1.txt -r 1.0 -t T -v V --random-seed 1478359791323 ";
        new File(args.modelConditionFilename).delete();
        tool.parseArguments(commandLine.split(" "), "name", args);
        tool.writeModelingConditions(args);
        assertEquals("Model condition file does not match expected.",
                "Tag|Results|Specified_Arguments|Default_Arguments|Classname\n" +
                        "YT5R9S||--learning-rate 1.0 --model-conditions test-results/model-conditions/1.txt --random-seed 1478359791323 --training-sets [T] --validation-set V|--build-cache-then-stop false --dropout-rate null --early-stopping-num-epochs 10 --eos-character null --error-enrichment false --experimental-condition not_specified --feature-mapper defaultFeatureMapperClassname --gpu-device null --ignore-cache false --learning-rate 1.0 --max-epochs 2147483647 --memory-cache validation --mini-batch-size 32 --model-conditions test-results/model-conditions/1.txt --net-architecture ABC --num-errors-added 16 --num-training 2147483647 --num-validation 2147483647 --parallel false --parameter-precision FP32 --previous-model-name bestAUC --previous-model-path null --previous-model-pretraining false --random-seed 1478359791323 --regularization-rate null --track PERFS --training-sets [T] --validate-every 1 --validation-set V|\n",
                FileUtils.readFileToString(new File(args.modelConditionFilename), "utf-8")
                );
    }
}