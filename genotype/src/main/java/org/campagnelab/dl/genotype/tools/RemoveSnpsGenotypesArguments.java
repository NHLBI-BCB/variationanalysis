package org.campagnelab.dl.genotype.tools;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.campagnelab.dl.framework.tools.arguments.ToolArguments;

/**
 * Arguments for AddTrueGenotypes.
 */
@Parameters(commandDescription = "Add calls from mapped vcf to sbi/sbip files.")

public class RemoveSnpsGenotypesArguments implements ToolArguments {
    @Parameter(required = true, names = {"-i", "--input-filename"}, description = "Input files in .bsi/.bsip format.")
    public String inputFile;

    @Parameter(required = true, names = {"-o", "--output-filename"}, description = "Output filename for annoated data.")
    public String outputFilename;

    @Parameter(required = true, names = {"-m", "--genotype-map"}, description = "Genotype map should have been generated with Goby's VCFToMapMode.")
    public String genotypeMap;

    @Parameter(required = false, names = {"-s", "--sample-index"}, description = "Remove snps to an alternative sample in the sbi file (default if first sample, index 0")
    public int sampleIndex = 0;

    @Parameter( names = { "--ref-sampling-rate"}, description = "Sampling rate for positions where the true genotype matches the reference.")
    public float referenceSamplingRate=1.0f;

    @Parameter( names = { "seed"}, description = "optional custom random seed.")
    public int seed=240965;

}

