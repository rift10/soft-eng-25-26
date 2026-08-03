#!/usr/bin/env bash

set -euo pipefail

x=""

while IFS= read -r line; do
    x+="$(echo "$line" | cut -c 1-1)"
done < puzzle/columns.txt

echo "$x"
