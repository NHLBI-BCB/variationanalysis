package org.campagnelab.dl.genotype.learning.domains.predictions;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import org.campagnelab.dl.framework.domains.prediction.Prediction;
import org.campagnelab.dl.varanalysis.protobuf.BaseInformationRecords;

import java.util.Set;

/**
 * Created by rct66 on 11/12/16.
 * Although this is called the homozygous prediction, the trueGenotypeFormat sting may be heterozygous
 * Homozygous refers only prediction, not the true label.
 */
public class HomozygousPrediction extends Prediction {

    //todo: fill these bools, they will be used for statistics like sensitivity.
    public boolean isVariant;
    public boolean isIndel;
    public Set<String> trueGenotype;
    public String trueGenotypeFormat;
    public String predictedHomozygousGenotype;
    public double probability;
    /**
     * True when the model predicts the site to be homozygous. False when the model predicts het.
     */
    public boolean isHomozygous;

    public void inspectRecord(BaseInformationRecords.BaseInformation currentRecord) {
        trueGenotype = getGenotype( currentRecord.getTrueGenotype());
        trueGenotypeFormat = (trueGenotype.size()==0)?"./.":currentRecord.getTrueGenotype();
        isVariant = currentRecord.getSamples(0).hasIsVariant() && currentRecord.getSamples(0).getIsVariant();
        isIndel = trueGenotype.contains("-");
        return;
    }

    public static Set<String> getGenotype(String genotype){
        Set<String> alleles = new ObjectArraySet<>();
        for (String allele : genotype.split("/")){
            alleles.add(allele);
        }
        alleles.remove("");
        alleles.remove("?");
        alleles.remove(".");
        return alleles;
    }
}