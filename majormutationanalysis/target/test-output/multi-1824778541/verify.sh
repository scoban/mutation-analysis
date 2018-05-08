# ====================================================
# A script to check the exactness of the output 
# produced by TimeBased test
# ====================================================
# 
end[0]=560
end[1]=554
end[2]=562
end[3]=542
end[4]=563
end[5]=545
end[6]=563
end[7]=548
end[8]=566
end[9]=576

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
