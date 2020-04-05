# extract only the timestamps
cat result.txt | awk '{print $2}' | grep -oh "^[0-9]\+" > requests.txt
