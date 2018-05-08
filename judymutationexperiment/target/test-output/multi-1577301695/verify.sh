# ====================================================
# A script to check the exactness of the output 
# produced by SizeBased test
# ====================================================
# 
end[0]=1920
end[1]=1797
end[2]=1920
end[3]=1920
end[4]=1876
end[5]=1920
end[6]=1920
end[7]=1857
end[8]=1980
end[9]=1825

rm aggregated
for i in $(seq 0 -1 0); do cat test-$i.log >> aggregated; done
cat output.log >> aggregated


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
