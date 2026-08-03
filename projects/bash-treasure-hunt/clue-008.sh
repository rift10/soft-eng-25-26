#!/usr/bin/env bash

set -euo pipefail

head -1 $(grep -rIL "hay" puzzle/haystack)