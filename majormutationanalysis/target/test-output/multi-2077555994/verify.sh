# ====================================================
# A script to check the exactness of the output 
# produced by TimeBased test
# ====================================================
# 
end[0]=441
end[1]=442
end[2]=452
end[3]=438
end[4]=446
end[5]=450
end[6]=438
end[7]=462
end[8]=444
end[9]=460

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
