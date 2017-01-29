#!/bin/bash
read -r url<"gitData.txt"
git push "$url" master
read -p " "