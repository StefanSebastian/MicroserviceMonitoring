for file in raw/*
do
    echo $file
    gunzip -c $file > ./temp

    grep de.wikipedia.org ./temp >> result.txt
done