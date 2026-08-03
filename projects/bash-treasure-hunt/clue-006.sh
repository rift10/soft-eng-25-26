#!/usr/bin/env bash

set -euo pipefail

tail -1 $(find puzzle/lots-of-files -size +0 -type f)
