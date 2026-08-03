#!/usr/bin/env bash

set -euo pipefail


ls -lSR puzzle/sizes | while read -r line; do
    if [[ "$(cut -c 1-1 <<< "$line")" = "-" ]]; then
        if [[ $(cut -d " " -f 5 <<< $line | wc -w) != 0 ]]; then
            cut -d " " -f 5 <<< $line
        else
            cut -d " " -f 6 <<< $line
        fi
    fi
done > list.txt

size=$(cat list.txt | sort -n | tail -1)c

tail -1 $(find puzzle/sizes -size "$size")

rm list.txt
