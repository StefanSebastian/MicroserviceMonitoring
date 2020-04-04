for file in raw/*
do
    echo $file
    gunzip -c $file > ./temp

    grep ro.wiki ./temp >> result.txt
done