#!/usr/bin/env bash

set -euo pipefail

while read -r line; do
    if [[ "$(echo "$line" | sha256sum | cut -d " " -f 1)" = "$(cat puzzle/lines-sha.txt)" ]]; then
        echo $line
    fi
done < puzzle/lines.txt
