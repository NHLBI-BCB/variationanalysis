package org.campagnelab.dl.somatic.mappers;

import org.campagnelab.dl.varanalysis.protobuf.BaseInformationRecords;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by fac2003 on 11/8/16.
 */
public class IsSomaticMutationMapper extends NoMasksLabelMapper<BaseInformationRecords.BaseInformation> {
    int[] indices = new int[]{0, 0};

    @Override
    public void mapLabels(BaseInformationRecords.BaseInformation record, INDArray labels, int indexOfRecord) {
        indices[0] = indexOfRecord;

        for (int labelIndex = 0; labelIndex < numberOfLabels(); labelIndex++) {
            indices[1] = labelIndex;
            labels.putScalar(indices, produceLabel(record, labelIndex));
        }
    }

    @Override
    public int numberOfLabels() {
        return 2;
    }


    @Override
    public float produceLabel(BaseInformationRecords.BaseInformation record, int labelIndex) {
        assert labelIndex == 0 || labelIndex == 1 : "only one label.";

        // first index is 1 when site is  mutated.
        if (labelIndex == 0) return record.getMutated() ? 1 : 0;
        // second index is 1 when site is not mutated.
        return record.getMutated() ? 0 : 1;
    }
}
