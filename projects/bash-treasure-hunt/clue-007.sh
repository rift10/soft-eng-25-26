#!/usr/bin/env bash

set -euo pipefail

tail -1 $(grep -rI "needle" puzzle/needle | cut -d ":" -f 1)