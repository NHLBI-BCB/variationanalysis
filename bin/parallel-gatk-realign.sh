GATK_JAR=$1
MEMORY_PER_THREAD=$2
NUM_THREADS=$3
GENOME_FA=$4
BAM_INPUT=$5
BAM_OUTPUT=$6

DATE=`date +%Y-%m-%d`
echo ${DATE} >DATE.txt


echo "Using ${GATK_JAR} as gatk jar."
echo "Using ${MEMORY_PER_THREAD} memory per thread."
echo "Using ${NUM_THREADS} number of threads."
echo "Using ${GENOME_FA} fasta file as genome. (index file .fa.fai required)."
echo "Using ${BAM_INPUT} as bam input."
echo "Writing bam output to ${BAM_OUTPUT}."

if [ ! -f ${BAM_INPUT}.bai ]; then
    samtools index ${BAM_INPUT}
fi

samtools idxstats ${BAM_INPUT} | cut -f 1 | head -n -1 > refs.txt

rm -rf calmd-and-convert-commands.txt
nLine=0
cat refs.txt | while read -r line
    do
       echo "samtools view -u ${BAM_INPUT} ${line} > slice_${nLine}.bam ;\
         samtools calmd -E -u slice_${nLine}.bam ${GENOME_FA} > md_slice_${nLine}.bam ;\
         samtools index md_slice_${nLine}.bam &&\
         rm slice_${nLine}.bam ;\
         java -jar -Xmx${MEMORY_PER_THREAD} ${GATK_JAR} -R ${GENOME_FA} -ip 50 -T RealignerTargetCreator -I md_slice_${nLine}.bam  -o md_slice_${nLine}_realignment_targets.interval_list -mismatch 0.0 &&\
         java -jar -Xmx${MEMORY_PER_THREAD} ${GATK_JAR} -R ${GENOME_FA} -ip 50 -T IndelRealigner -I md_slice_${nLine}.bam -targetIntervals md_slice_${nLine}_realignment_targets.interval_list -o realigned_md_slice_${nLine}.bam &&\
         rm md_slice_${nLine}.bam &&\
         rm md_slice_${nLine}.bam.bai \
       " >> calmd-and-convert-commands.txt
       nLine=$((nLine+1))
done

parallel --bar -j${NUM_THREADS} --eta :::: calmd-and-convert-commands.txt

samtools merge ${BAM_OUTPUT} realigned_md_slice_*.bam &&

rm realigned_md_slice_*