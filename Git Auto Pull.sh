#!/bin/bash
git init
read -r url<"gitData.txt"
git pull "$url" master
read -p " "