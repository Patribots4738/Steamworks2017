#!/bin/bash
git init
git add -u ./
echo "Enter a commit description. Short and simple is best"
read commit
git commit -m "'$commit'"