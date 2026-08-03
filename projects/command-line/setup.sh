#!/usr/bin/env bash

set -eou pipefail

sudo apt update
sudo apt install fd-find
sudo apt install ripgrep
mkdir -p /home/vscode/.local/bin
ln -sf $(which fdfind) /home/vscode/.local/bin/fd
