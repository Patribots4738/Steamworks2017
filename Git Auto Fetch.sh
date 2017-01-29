read -r url<"gitData.txt"
git fetch "$url" master
echo "$url"
read -p " "