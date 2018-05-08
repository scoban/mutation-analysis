# ====================================================
# A script to check the exactness of the output 
# produced by TimeBased test
# ====================================================
# 
end[0]=177
end[1]=188
end[2]=188
end[3]=187
end[4]=176
end[5]=184
end[6]=184
end[7]=186
end[8]=192
end[9]=174

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
