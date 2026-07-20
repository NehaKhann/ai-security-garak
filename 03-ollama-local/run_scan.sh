#!/usr/bin/env bash
# Runs a garak scan against a local Ollama model.
# Usage: ./run_scan.sh [model_name] [probe]
set -euo pipefail

MODEL="${1:-llama3}"
PROBE="${2:-dan.Dan_11_0}"
TARGET_TYPE="${3:-ollama}"   # ollama (completion) or ollama.OllamaGeneratorChat (chat template)

echo "== garak scan =="
echo "target_type = ${TARGET_TYPE}"
echo "target_name = ${MODEL}"
echo "probes      = ${PROBE}"
echo "================="
echo "(make sure 'ollama serve' is running and '${MODEL}' has been pulled)"
echo ""

python3 -m garak \
  --target_type "${TARGET_TYPE}" \
  --target_name "${MODEL}" \
  --probes "${PROBE}"
