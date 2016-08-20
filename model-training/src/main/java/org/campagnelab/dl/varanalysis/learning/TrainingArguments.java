package org.campagnelab.dl.varanalysis.learning;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fac2003 on 8/20/16.
 */
@Parameters(commandDescription = "Train a model given training files and a validation file.")

public class TrainingArguments {
    @Parameter(names = {"-t", "--training-sets"}, description = "Training sets, must be provided in .parquet/.info format. When more than one dataset is provided (multiple -t options), the " +
            "datasets are concatenated.")
    public List<String> trainingSets = new ArrayList<>();

    @Parameter(names = {"-v", "--validation-set"}, description = "Validation set, must be provided in .parquet/.info format.")
    public String validationSet = null;

    @Parameter(names = "--trio", description = "Use to train trio models. The training and validation datasets must have three samples, parents first, patient last.")
    public boolean isTrio = false;

    @Parameter(names = {"-n","--num-training"}, description = "The maximum number of training samples to train with. ")
    public int numTraining=Integer.MAX_VALUE;

    @Parameter(names = {"-x", "--num-validation"}, description = "The maximum number of training samples to train with. ")
    public int numValidation=Integer.MAX_VALUE;

    @Parameter(names = {"-s", "--random-seed"}, description = "The random seed to initialize network weights. ")
    public long seed=new Date().getTime();

    public String[] getTrainingSets() {
      return  this.trainingSets.toArray(new String[this.trainingSets.size()]);
    }

}