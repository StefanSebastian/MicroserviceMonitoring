for file in raw/*
do
    echo $file
    gunzip -c $file > ./temp

    grep ja.wikipedia.org ./temp >> result.txt
done