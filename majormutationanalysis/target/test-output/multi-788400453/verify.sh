# ====================================================
# A script to check the exactness of the output 
# produced by TimeBased test
# ====================================================
# 
end[0]=489
end[1]=479
end[2]=482
end[3]=492
end[4]=478
end[5]=487
end[6]=478
end[7]=509
end[8]=483
end[9]=492

rm aggregated
cat test* output.log >> aggregated

for t in $(seq 0 1 9)
do
  echo "Testing results of thread $t"
  grep "$t " aggregated | cut -d ' ' -f 2 > ${t}-sample
  for j in $(seq 1 1 ${end[$t]}); do echo $j; done > ${t}-witness
  diff -q -w ${t}-sample ${t}-witness;
  res=$?
  if [ $res != "0" ]; then
    echo "FAILED for $t"
    exit 1
  fi
done

exit 8
