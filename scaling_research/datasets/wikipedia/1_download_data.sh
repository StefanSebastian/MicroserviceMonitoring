# store description
curl http://www.wikibench.eu/wiki/2007-09/ > wikibench.repo

# download each file
cat wikibench.repo | grep -oh "wiki\.[0-9]\+\.gz" | xargs -I{} curl -O http://www.wikibench.eu/wiki/2007-09/{}

