#!/usr/bin/env bash
# Runs a small garak scan against a local Hugging Face model (DistilGPT-2).
# Usage: ./run_scan.sh
set -euo pipefail

MODEL="${1:-distilgpt2}"
PROBE="${2:-dan.Dan_11_0}"

echo "== garak scan =="
echo "target_type = huggingface"
echo "target_name = ${MODEL}"
echo "probes      = ${PROBE}"
echo "================="

python3 -m garak \
  --target_type huggingface \
  --target_name "${MODEL}" \
  --probes "${PROBE}"
